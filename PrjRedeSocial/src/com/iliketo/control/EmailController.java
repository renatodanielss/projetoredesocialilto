package com.iliketo.control;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import HardCore.DB;

import com.iliketo.dao.MemberDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Member;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;


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
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}

	public void enviaEmailNovoAnuncioColecionadorLoja(Announce anuncio, String idCategory, String myUserid, 
			DB db, HttpServletRequest request, Map<String, String> mapPages){

		//coleta todos usuarios que participam do grupo/categoria do anuncio criado
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);			
		String SQL = 
				"select t1.id_member as id_member, t1.nickname as nickname, t1.email as email, t1.country as country "
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
			m.setCountry(registros.get(rec).get("country"));
			log.info("Membro: " + m.getNickname() + "- email: " + m.getEmail());
			lista.add(m);
		}
		
		//dados email
		//<img src=\"cid:"+cid+"\">
		String assuntoPT = "Boas notícias para você - " + anuncio.getTitle();
		String assuntoEN = "Good news for you - " + anuncio.getTitle();
		String htmlConteudoPT = "";
		String htmlConteudoEN = "";
		String msgTextoEN = "I LIKE TOO HAVE GOOD NEWS FOR YOU!";
		String msgTextoPT = "I LIKE TOO TEM NOVAS NOTÍCIAS PARA VOCÊ!";
		String msgConteudo = null;
		//valida vendedor do anuncio
		Member vendedorDoAnuncio = (Member) new MemberDAO(db, null).readByColumn("id_member", myUserid, Member.class);
		if(vendedorDoAnuncio == null){
			return;
		}
		
		if(!lista.isEmpty()){
			CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
			String listEntryPT;
			String listEntryEN;
			if(mapPages != null){
				listEntryPT = mapPages.get("1162");
				listEntryEN = mapPages.get("1092");
			}else{
				listEntryPT = cms.getPageListEntry("1162");
				listEntryEN = cms.getPageListEntry("1092");
			}
			htmlConteudoPT = cms.parseBindingModelBean(listEntryPT, anuncio).toString();
			htmlConteudoEN = cms.parseBindingModelBean(listEntryEN, anuncio).toString();
			
			//verifica tipo anuncio
			if(anuncio.getTypeAnnounce().contains("Sell")){
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Preço: R$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Price: US$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
				
			}else if(anuncio.getTypeAnnounce().equals("Exchange")){
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Detalhes: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Details: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				
			}else if(anuncio.getTypeAnnounce().equals("Purchase")){
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Preço oferecido: R$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Offered price: US$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
				htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
			}
			
			//informacoes generica do vendedor do anuncio
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info3@@@", vendedorDoAnuncio.getNickname());
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info4@@@", vendedorDoAnuncio.getEmail());
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info3@@@", vendedorDoAnuncio.getNickname());
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info4@@@", vendedorDoAnuncio.getEmail());
			
			//envia emails
			log.info("ENVIA EMAILS PARA OS MEMBROS DA CATEGORIA: " + idCategory + " - Total de membros na categoria: " + lista.size());
			sendEmailDefault(lista, assuntoPT, assuntoEN, htmlConteudoPT, htmlConteudoEN, msgTextoPT, msgTextoEN, msgConteudo);			
		}else{
			log.info("NAO ENVIOU EMAILS, LISTA MEMBROS VAZIA DA CATEGORIA: " + idCategory);
		}	
	}
	
	public void enviaEmailPagamentoStorage(Member member, HashMap<String, String> paymentInfo, String language, HttpServletRequest request, boolean returnPage){
		HashMap<String, String> subjectLanguage = new HashMap<String, String>();
		subjectLanguage.put("pt_BR", "Email de Compra de Armazenamento");
		subjectLanguage.put("en_US", "Buy Storage Email");
		
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "I LIKE TOO TEM BOAS NOVA PARA VOCÊ!");
		msgTextoLanguage.put("en_US", "I LIKE TOO HAVE GOOD NEWS FOR YOU!");
		
		String assunto = subjectLanguage.get(language);
		String htmlConteudo = "";
		String msgTexto = msgTextoLanguage.get(language);
		String msgConteudo = null;
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
		
		String emailPaymentStoragePage;
		if (returnPage)
			emailPaymentStoragePage = cms.getPageListEntry("1158");
		else
			emailPaymentStoragePage = cms.getPageListEntry("1160");
		
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
	
	private void sendEmailDefault(ArrayList<Member> listaEmails, String assuntoPT, String assuntoEN, 
			String htmlConteudoPT, String htmlConteudoEN, String msgTextoPT, String msgTextoEN, String msgConteudo){		
		try {			
			for(Member membro : listaEmails){
				String assunto = "Brazil".equals(membro.getCountry()) ? assuntoPT : assuntoEN;
				String htmlConteudo = "Brazil".equals(membro.getCountry()) ? htmlConteudoPT : htmlConteudoEN;
				String msgTexto = "Brazil".equals(membro.getCountry()) ? msgTextoPT : msgTextoEN;
				
				log.info("Email [assunto: " +assunto+ " ] tentando... enviar para: " + membro.getEmail());
				
				/** Parâmetros de conexão com servidor Gmail */
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
				
				//destinatário
				email.addTo(membro.getEmail(), membro.getNickname());
				email.setSubject(assunto);
				String htmlResponse = htmlConteudo.replaceAll("nickname", membro.getNickname());	//define usuario ex: Hello alguem
				//log.info("htmlResponse Email = " + htmlResponse);
				email.setHtmlMsg(htmlResponse); //mensagem para o formato HTML				
				email.setTextMsg(msgTexto);		//mensagem alternativa caso  não suporte HTML				
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
			
			/** Parâmetros de conexão com servidor Gmail */
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
			
			//destinatário
			email.addTo(emailTo.getEmail(), emailTo.getNickname());
			email.setSubject(assunto);
			String htmlResponse = htmlConteudo.replaceAll("nickname", emailTo.getNickname());	//define usuario ex: Hello alguem
			email.setHtmlMsg(htmlResponse); //mensagem para o formato HTML				
			email.setTextMsg(msgTexto);		//mensagem alternativa caso  não suporte HTML				
			//email.setMsg(msgConteudo); 	//conteudo do e-mail

			email.send();
			log.info("Email [assunto: " +assunto+ " ] enviado ok para: " + emailTo.getEmail());
			
		} catch (EmailException e) {
			e.printStackTrace();
		}		
	}
}
