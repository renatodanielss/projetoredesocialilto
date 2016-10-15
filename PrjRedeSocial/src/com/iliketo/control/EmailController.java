package com.iliketo.control;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Member;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.Str;


public class EmailController {
	
	static final Logger log = Logger.getLogger(EmailController.class);
	
	public enum tipoEmail { 
		EMAIL_STORAGE("SENHA_STORAGE"), EMAIL_ANUNCIO("SENHA_ANUNCIO"), EMAIL_DENUNCIA("SENHA_DENUNCIA");		
		public final String valor;		
		tipoEmail(String valo){
			valor = valo;
		}
	}
	
	private String email;
	private String senha;
	
	public EmailController(tipoEmail tipo){
		try {
			Properties prop = new Properties();
			String filename = "config/config_iliketoo.properties";
			InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);  
			if(input==null){
		        log.error("Arquivo properties de configuracoes nao encontrado: " + filename);
			}else{
				prop.load(input);
				this.email = prop.getProperty(tipo.toString());
				this.senha = prop.getProperty(tipo.valor);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}

	@RequestMapping(value={"/teste/sendEmail"})
	public void testeSendEmail(HttpServletRequest req, HttpServletResponse res){
		
		String id = req.getParameter("id");
		
		if(id != null && !id.isEmpty()){
			DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
			AnnounceDAO dao = new AnnounceDAO(db, null);
			Announce anuncio = (Announce) dao.readById(id, Announce.class);
			
			//dados email
			//<img src=\"cid:"+cid+"\">
			String assunto = "Good news for you - " + anuncio.getTitle();
			String htmlConteudo = "";
			String msgTexto = "I LIKE TOO HAVE GOOD NEWS FOR YOU!";
			String msgConteudo = null;
			//htmlConteudo = "<html><body>The apache logo <br><br> link image direto <br>yahoo, gmail, hotmail embed  <br><br> <img src=\"http://www.apache.org/images/asf_logo_wide.gif\"> </body></html>";			
			//String [] user = {"osvaldimar1@hotmail.com", "osvaldimar1@gmail.com", "osvaldimar1@yahoo.com.br"};
			
			ArrayList<Member> lista = new ArrayList<Member>();
			if(req.getParameter("emails") != null){
				String emails = req.getParameter("emails");
				if(emails.equals("me")){
					Member m1 = new Member();
					m1.setIdMember("1");	
					m1.setEmail("osvaldimar1@hotmail.com");	
					m1.setNickname("osvaldo 1");
					Member m2 = new Member();	
					m2.setIdMember("2");	
					m2.setEmail("osvaldimar1@gmail.com");	
					m2.setNickname("osvaldo 2");
					lista.add(m1);
					lista.add(m2);
				}else if(emails.equals("socios")){
					Member m1 = new Member();	
					m1.setIdMember("1");	
					m1.setEmail("osvaldimar1@gmail.com");	
					m1.setNickname("teste 1");	
					Member m2 = new Member();	
					m2.setIdMember("2");	
					m2.setEmail("lfsuplicy@gmail.com");	
					m2.setNickname("teste 2");			
					Member m3 = new Member();	
					m3.setIdMember("3");	
					m3.setEmail("vtiezzi@hotmail.com");	
					m3.setNickname("teste 3");			
					Member m4 = new Member();	
					m4.setIdMember("4");		
					m4.setEmail("Renatodanielss@gmail.com");	
					m4.setNickname("teste 4");			
					Member m5 = new Member();	
					m5.setIdMember("5");		
					m5.setEmail("may.silva.nascimento@gmail.com");	
					m5.setNickname("teste 5");		
					lista.add(m1);
					lista.add(m2);
					lista.add(m3);
					lista.add(m4);
					lista.add(m5);
				}else{			
					Member m1 = new Member();
					m1.setIdMember("1");
					m1.setEmail(emails);
					m1.setNickname("teste 1");
					lista.add(m1);
				}
			}
			
			if(!lista.isEmpty()){			
				CmsConfigILiketo cms = new CmsConfigILiketo(req, null);
				String listEntry = cms.getPageListEntry("1092");
				htmlConteudo = cms.parseBindingModelBean(listEntry, anuncio).toString();
				//verifica tipo anuncio
				if(anuncio.getTypeAnnounce().contains("Sell")){
					htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Price: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
				}else if(anuncio.getTypeAnnounce().equals("Exchange")){
					htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Details: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				}else if(anuncio.getTypeAnnounce().equals("Auction")){
					htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Price initial: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getPriceInitial());
				}else if(anuncio.getTypeAnnounce().equals("Purchase")){
					htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Offered price: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
					htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
				}
				//envia emails
				log.info("ENVIA EMAILS PARA OS MEMBROS DA CATEGORIA: " + "testando...");
				sendEmailDefault(lista, assunto, htmlConteudo, msgTexto, msgConteudo);
			}else{
				log.info("NAO ENVIOU EMAILS, LISTA MEMBROS VAZIA DA CATEGORIA: " + "testando...");
			}
		}
	}
	
	public void enviaEmailNovoAnuncioColecionadorLoja(Announce anuncio, String idCategory, String myUserid, DB db, HttpServletRequest request){

		//coleta todos usuarios que participam do grupo/categoria do anuncio criado
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);			
		String SQL = 
				"select t1.id_member as id_member, t1.nickname as nickname, t1.email as email "
				+ "from dbmembers as t1 "
				+ "where exists (select t2.fk_user_id from dbcollection t2 where t1.id_member != '" +myUserid+ "' and t1.id_member = t2.fk_user_id and t2.fk_category_id = '" +idCategory+ "') "
				+ "or exists (select t3.fk_user_id from dbinterest t3 where t1.id_member != '" +myUserid+ "' and t1.id_member = t3.fk_user_id and t3.fk_category_id = '" +idCategory+ "') "
				+ "or exists (select t4.fk_user_id from dbhobby t4 where t1.id_member != '" +myUserid+ "' and t1.id_member = t4.fk_user_id and t4.fk_category_id = '" +idCategory+ "') "
				+ ";";
				
		String[][] aliasSQL = { {"dbmembers", "t1"}, {"dbcollection", "t2"}, {"dbinterest", "t3"}, {"dbhobby", "t4"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		log.info("SQL emails: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> registros = db.query_records(SQL);
		
		//lista de usuarios para enviar email
		ArrayList<Member> lista = new ArrayList<Member>();
		for(String rec : registros.keySet()){
			Member m = new Member();	
			m.setIdMember(registros.get(rec).get("id_member"));				
			m.setNickname(registros.get(rec).get("nickname"));
			m.setEmail(registros.get(rec).get("email"));
			log.info("Membro: " + m.getNickname() + "- email: " + m.getEmail());
			lista.add(m);
		}
		
		//dados email
		//<img src=\"cid:"+cid+"\">
		String assunto = "Good news for you - " + anuncio.getTitle();
		String htmlConteudo = "";
		String msgTexto = "I LIKE TOO HAVE GOOD NEWS FOR YOU!";
		String msgConteudo = null;
		
		if(!lista.isEmpty()){
			CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
			String listEntry = cms.getPageListEntry("1092");
			htmlConteudo = cms.parseBindingModelBean(listEntry, anuncio).toString();
			
			//verifica tipo anuncio
			if(anuncio.getTypeAnnounce().contains("Sell")){
				htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Price: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
			}else if(anuncio.getTypeAnnounce().equals("Exchange")){
				htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Details: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			}else if(anuncio.getTypeAnnounce().equals("Auction")){
				htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Price initial: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getPriceInitial());
			}else if(anuncio.getTypeAnnounce().equals("Purchase")){
				htmlConteudo = htmlConteudo.replaceAll("@@@info1@@@", "Offered price: $".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudo = htmlConteudo.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
			}
			
			//envia emails
			log.info("ENVIA EMAILS PARA OS MEMBROS DA CATEGORIA: " + idCategory);
			sendEmailDefault(lista, assunto, htmlConteudo, msgTexto, msgConteudo);
			
		}else{
			log.info("NAO ENVIOU EMAILS, LISTA MEMBROS VAZIA DA CATEGORIA: " + idCategory);
		}	
	}
	
	public void enviaEmailPagamentoStorage(Member member, HashMap<String, String> paymentInfo, String language, HttpServletRequest request){
		HashMap<String, String> subjectLanguage = new HashMap<String, String>();
		subjectLanguage.put("pt_BR", "Email de Compra de Armazenamento");
		subjectLanguage.put("en_US", "Buy Storage Email");
		
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "I LIKE TOO TEM BOAS NOVA PARA VOC�!");
		msgTextoLanguage.put("en_US", "I LIKE TOO HAVE GOOD NEWS FOR YOU!");
		
		String assunto = subjectLanguage.get(language);
		String htmlConteudo = "";
		String msgTexto = msgTextoLanguage.get(language);
		String msgConteudo = null;
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
		String emailPaymentStoragePage = cms.getPageListEntry("1158"); //mudar p�gina
		htmlConteudo = cms.parseBindingModelBean(emailPaymentStoragePage, member).toString();
		
		htmlConteudo = htmlConteudo.replaceAll("@@@nickname@@@", member.getNickname());
		htmlConteudo = htmlConteudo.replaceAll("@@@product@@@", paymentInfo.get("L_PAYMENTREQUEST_0_NAME0"));
		htmlConteudo = htmlConteudo.replaceAll("@@@productDescription@@@", paymentInfo.get("L_PAYMENTREQUEST_0_DESC0"));
		htmlConteudo = htmlConteudo.replaceAll("@@@amount@@@", paymentInfo.get("PAYMENTINFO_0_AMT"));
		htmlConteudo = htmlConteudo.replaceAll("@@@currency@@@", paymentInfo.get("PAYMENTINFO_0_CURRENCYCODE"));
		htmlConteudo = htmlConteudo.replaceAll("@@@paymentStatus@@@", paymentInfo.get("PAYMENTINFO_0_PAYMENTSTATUS"));
		htmlConteudo = htmlConteudo.replaceAll("@@@transactionId@@@", paymentInfo.get("PAYMENTINFO_0_TRANSACTIONID"));
		
		sendEmail(member, assunto, htmlConteudo, msgTexto, msgConteudo);
	}
	
	private void sendEmailDefault(ArrayList<Member> listaEmails, String assunto, String htmlConteudo, String msgTexto, String msgConteudo){
		
		try {
			
			for(Member membro : listaEmails){
				log.info("Email [assunto: " +assunto+ " ] tentando... enviar para: " + membro.getEmail());
				
				/** Par�metros de conex�o com servidor Gmail */
				HtmlEmail email = new HtmlEmail(); 
				email.setHostName("smtp.gmail.com"); 		//servidor SMTP para envio do e-mail
				email.setFrom(this.email, "I Like Too"); 	// remetente				
				//email.setAuthentication(usuario, senha);
				//email.setSmtpPort(587);
				email.setSmtpPort(465);  
		        email.setAuthenticator(new DefaultAuthenticator(this.email, this.senha));  
		        email.setSSLOnConnect(true);	        
				email.setSSL(true);
				email.setTLS(true);			
				
				//destinat�rio
				email.addTo(membro.getEmail(), membro.getNickname());
				email.setSubject(assunto);
				String htmlResponse = htmlConteudo.replaceAll("nickname", membro.getNickname());	//define usuario ex: Hello alguem
				email.setHtmlMsg(htmlResponse); //mensagem para o formato HTML				
				email.setTextMsg(msgTexto);		//mensagem alternativa caso  n�o suporte HTML				
				//email.setMsg(msgConteudo); 	//conteudo do e-mail

				email.send();
				log.info("Email [assunto: " +assunto+ " ] enviado ok para: " + membro.getEmail());
			}
			
		} catch (EmailException e) {
			e.printStackTrace();
		}		
	}

	private void sendEmail(Member emailTo, String assunto, String htmlConteudo, String msgTexto, String msgConteudo){
		
		try {
			
			log.info("Email [assunto: " +assunto+ " ] tentando... enviar para: " + emailTo.getEmail());
			
			/** Par�metros de conex�o com servidor Gmail */
			HtmlEmail email = new HtmlEmail(); 
			email.setHostName("smtp.gmail.com"); 		//servidor SMTP para envio do e-mail
			email.setFrom(this.email, "I Like Too"); 	// remetente				
			//email.setAuthentication(usuario, senha);
			//email.setSmtpPort(587);
			email.setSmtpPort(465);  
	        email.setAuthenticator(new DefaultAuthenticator(this.email, this.senha));  
	        email.setSSLOnConnect(true);	        
			email.setSSL(true);
			email.setTLS(true);
			
			//destinat�rio
			email.addTo(emailTo.getEmail(), emailTo.getNickname());
			email.setSubject(assunto);
			String htmlResponse = htmlConteudo.replaceAll("nickname", emailTo.getNickname());	//define usuario ex: Hello alguem
			email.setHtmlMsg(htmlResponse); //mensagem para o formato HTML				
			email.setTextMsg(msgTexto);		//mensagem alternativa caso  n�o suporte HTML				
			//email.setMsg(msgConteudo); 	//conteudo do e-mail

			email.send();
			log.info("Email [assunto: " +assunto+ " ] enviado ok para: " + emailTo.getEmail());
			
		} catch (EmailException e) {
			e.printStackTrace();
		}		
	}
}
