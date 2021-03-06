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
import HardCore.Request;
import HardCore.User;

import com.iliketo.dao.MemberDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.ContentILiketo;
import com.iliketo.model.Event;
import com.iliketo.model.Member;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;


public class EmailController {
	
	static final Logger log = Logger.getLogger(EmailController.class);
	
	public enum tipoEmail {
		EMAIL_FINANCEIRO("SENHA_FINANCEIRO"), EMAIL_SUPORTE("SENHA_SUPORTE"),
		EMAIL_ANUNCIO("SENHA_ANUNCIO"), EMAIL_REPORT("SENHA_REPORT"), EMAIL_RECOVERY("SENHA_RECOVERY"),
		EMAIL_ACTIVATION("SENHA_ACTIVATION");
		public final String valor;
		tipoEmail(String valor){
			this.valor = valor;
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
	
	public void enviaEmailNovoAnuncioGenericoParaColecionadorLoja(ContentILiketo objeto, String idCategory, String myUserid, 
			DB db, HttpServletRequest request, Map<String, String> mapPages, String tipo){

		//coleta todos usuarios que participam do grupo/categoria do anuncio criado
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String SQL = 
				"select t1.id_member as id_member, t1.nickname as nickname, t1.email as email, t1.country as country "
				+ "from dbmembers as t1 "
				+ "where exists (select t2.fk_user_id from dbcollection t2 where t1.id_member != '" +myUserid+ "' and t1.id_member = t2.fk_user_id and t2.fk_category_id = '" +idCategory+ "' and t1.col19 = 'Yes') "
				+ "or exists (select t3.fk_user_id from dbinterest t3 where t1.id_member != '" +myUserid+ "' and t1.id_member = t3.fk_user_id and t3.fk_category_id = '" +idCategory+ "' and t1.col19 = 'Yes') "
				+ "or exists (select t4.fk_user_id from dbhobby t4 where t1.id_member != '" +myUserid+ "' and t1.id_member = t4.fk_user_id and t4.fk_category_id = '" +idCategory+ "' and t1.col19 = 'Yes') "
				+ ";";
				
		String[][] aliasSQL = { {"dbmembers", "t1"}, {"dbcollection", "t2"}, {"dbinterest", "t3"}, {"dbhobby", "t4"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		//log.info("SQL emails: " + SQL);
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

		Member vendedorDoAnuncio = (Member) new MemberDAO(db, null).readByColumn("id_member", myUserid, Member.class);
		if(vendedorDoAnuncio == null){
			return;
		}
		
		if(!lista.isEmpty()){
			if(tipo.equals("anuncio")){		
				this.construirLayoutNewsAnuncios(lista, (Announce)objeto, vendedorDoAnuncio, request, mapPages);
			}else{
				this.construirLayoutNewsEventos(lista, (Event)objeto, vendedorDoAnuncio, request, mapPages);
			}			
		}else{
			log.info("NAO ENVIOU EMAILS, LISTA MEMBROS VAZIA DA CATEGORIA: " + idCategory);
		}	
	}
	
	private void construirLayoutNewsAnuncios(ArrayList<Member> lista, Announce anuncio, Member vendedorDoAnuncio, 
			HttpServletRequest request, Map<String, String> mapPages){
		String assuntoPT = "Boas not�cias para voc� - " + anuncio.getTitle();
		String assuntoEN = "Good news for you - " + anuncio.getTitle();
		String htmlConteudoPT = "";
		String htmlConteudoEN = "";
		String msgTextoEN = "I LIKE TOO HAVE GOOD NEWS FOR YOU!";
		String msgTextoPT = "I LIKE TOO TEM NOVAS NOT�CIAS PARA VOC�!";
		
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
		
		if(anuncio.getTypeAnnounce().contains("Sell")){
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Pre�o: R$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Price: US$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getPriceFixed());
			
		}else if(anuncio.getTypeAnnounce().equals("Exchange")){
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Detalhes: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Details: ".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getDetails().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			
		}else if(anuncio.getTypeAnnounce().equals("Purchase")){
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", "Pre�o oferecido: R$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info1@@@", "Offered price: US$".replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$"));
			htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info2@@@", anuncio.getOfferedPrice());
		}		
		htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info3@@@", vendedorDoAnuncio.getNickname());
		htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info4@@@", vendedorDoAnuncio.getEmail());
		htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info3@@@", vendedorDoAnuncio.getNickname());
		htmlConteudoEN = htmlConteudoEN.replaceAll("@@@info4@@@", vendedorDoAnuncio.getEmail());
		
		log.info("ENVIA EMAILS DE ANUNCIOS PARA OS MEMBROS DA CATEGORIA: " + anuncio.getIdCategory() + " - Total de membros na categoria: " + lista.size());
		sendEmailDefault(lista, assuntoPT, assuntoEN, htmlConteudoPT, htmlConteudoEN, msgTextoPT, msgTextoEN, null);
	}
	
	private void construirLayoutNewsEventos(ArrayList<Member> lista, Event evento, Member vendedorDoAnuncio, 
			HttpServletRequest request, Map<String, String> mapPages){
		String assuntoPT = "Boas not�cias para voc� - " + evento.getNameEvent();
		String assuntoEN = "Good news for you - " + evento.getNameEvent();
		String htmlConteudoPT = "";
		String htmlConteudoEN = "";
		String msgTextoEN = "I LIKE TOO HAVE GOOD NEWS FOR YOU!";
		String msgTextoPT = "I LIKE TOO TEM NOVAS NOT�CIAS PARA VOC�!";
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
		String listEntryPT = cms.getPageListEntry("1178");
		String listEntryEN = cms.getPageListEntry("1179");
		
		htmlConteudoPT = cms.parseBindingModelBean(listEntryPT, evento).toString();
		htmlConteudoEN = cms.parseBindingModelBean(listEntryEN, evento).toString();
		htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info1@@@", vendedorDoAnuncio.getNickname());
		htmlConteudoPT = htmlConteudoPT.replaceAll("@@@info2@@@", vendedorDoAnuncio.getEmail());
		
		log.info("ENVIA EMAILS DE EVENTOS PARA OS MEMBROS DA CATEGORIA: " + evento.getIdCategory() + " - Total de membros na categoria: " + lista.size());
		sendEmailDefault(lista, assuntoPT, assuntoEN, htmlConteudoPT, htmlConteudoEN, msgTextoPT, msgTextoEN, null);
	}
	
	public void enviaEmailSobreStatusPagamento(Member member, HashMap<String, String> paymentInfo, String language, String custom, HttpServletRequest request, boolean returnPage){
		HashMap<String, String> subjectLanguage = new HashMap<String, String>();
		subjectLanguage.put("pt_BR-Ad", "Notifica��o - compra de An�ncio");
		subjectLanguage.put("en_US-Ad", "Notification - Buy of ad");
		subjectLanguage.put("pt_BR-Featured", "Notifica��o - compra de Destaque");
		subjectLanguage.put("en_US-Featured", "Notification - Buy of featured");
		subjectLanguage.put("pt_BR-Event", "Notifica��o - compra de Evento");
		subjectLanguage.put("en_US-Event", "Notification - Buy of event");
		
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "I LIKE TOO TEM BOAS NOVA PARA VOC�!");
		msgTextoLanguage.put("en_US", "I LIKE TOO HAVE GOOD NEWS FOR YOU!");
		
		String assunto = subjectLanguage.get(language+"-"+custom);
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
	
	public void enviaEmailPagamentoStorage(Member member, HashMap<String, String> paymentInfo, String language, HttpServletRequest request, boolean returnPage){
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
	
	public void reenviaEmailAtivacao(Member member, String language, Request request){
		HashMap<String, String> subjectLanguage = new HashMap<String, String>();
		subjectLanguage.put("pt_BR", "Confirma��o de Registro de Usu�rio");
		subjectLanguage.put("en_US", "User Registration Confirmation");
		
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "Email de Confirma��o de Conta");
		msgTextoLanguage.put("en_US", "Account Confirmation Email");
		
		String assunto = subjectLanguage.get(language);
		String htmlConteudo = "";
		String msgTexto = msgTextoLanguage.get(language);
		String msgConteudo = null;
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request.getRequest(), null);
		
		String emailPaymentStoragePage = cms.getPageListEntry("329");
		
		htmlConteudo = cms.parseBindingModelBean(emailPaymentStoragePage, member).toString();
		
		htmlConteudo = htmlConteudo.replaceAll("@@@email@@@", member.getEmail());
		htmlConteudo = htmlConteudo.replaceAll("@@@validationlink@@@", request.getProtocol() + request.getServerName() + request.getServerPort() + "/activation.jsp?user=" + member.getUsername() + "&activationkey=" + member.getActivated());
		//htmlConteudo = htmlConteudo.replaceAll("@@@validationlink@@@", "https://www.iliketoo.com/activation.jsp?user=" + member.getUsername() + "&activationkey=" + member.getActivated());
		log.info("http://" + request.getServerName() + request.getServerPort() + "/activation.jsp?user=" + member.getUsername() + "&activationkey=" + member.getActivated());
		
		sendEmail(member, assunto, htmlConteudo, msgTexto, msgConteudo);
	}
	
	public void enviaEmailRecuperacaoSenha(Member member, User user, String language, Request request, String uuid){
		HashMap<String, String> subjectLanguage = new HashMap<String, String>();
		subjectLanguage.put("pt_BR", "Seu Usu�rio e Senha");
		subjectLanguage.put("en_US", "Your Username and Password");
		
		HashMap<String, String> msgTextoLanguage = new HashMap<String, String>();
		msgTextoLanguage.put("pt_BR", "Email de Recupera��o de Senha");
		msgTextoLanguage.put("en_US", "Retrieve Password Email");
		
		String assunto = subjectLanguage.get(language);
		String htmlConteudo = "";
		String msgTexto = msgTextoLanguage.get(language);
		String msgConteudo = null;
		
		CmsConfigILiketo cms = new CmsConfigILiketo(request.getRequest(), null);
		
		String emailPaymentStoragePage = cms.getPageListEntry("363");
		
		htmlConteudo = cms.parseBindingModelBean(emailPaymentStoragePage, member).toString();
		
		htmlConteudo = htmlConteudo.replaceAll("@@@email@@@", member.getEmail());
		htmlConteudo = htmlConteudo.replaceAll("@@@resetpasswordlink@@@", request.getProtocol() + request.getServerName() + request.getServerPort() + "/resetpassword.jsp?user=" + user.getUsername() + "&resetkey=" + uuid);
		//htmlConteudo = htmlConteudo.replaceAll("@@@validationlink@@@", "https://www.iliketoo.com/activation.jsp?user=" + member.getUsername() + "&activationkey=" + member.getActivated());
		//log.info("http://" + request.getServerName() + request.getServerPort() + "/activation.jsp?user=" + member.getUsername() + "&activationkey=" + member.getActivated());
		
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
				
				/** Par�metros de conex�o com servidor Gmail */
				HtmlEmail email = new HtmlEmail(); 
				
				//DNS
				email.setHostName("smtp.gmail.com"); 			//servidor SMTP para envio do e-mail
				//email.setHostName("br736.hostgator.com.br"); 	//servidor SMTP para envio do e-mail ou ns737.hostgator.com.br
				email.setFrom(this.email, "I Like Too"); 		// remetente
				//email.setAuthentication(usuario, senha);
				email.setSmtpPort(587);
				//email.setSmtpPort(465);
		        email.setAuthenticator(new DefaultAuthenticator(this.email, this.senha));  
		        email.setSSLOnConnect(true);	        
				email.setSSL(true);
				email.setTLS(true);			
				
				//destinat�rio
				email.addTo(membro.getEmail(), membro.getNickname());
				email.setSubject(assunto);
				String htmlResponse = htmlConteudo.replaceAll("nickname", membro.getNickname());	//define usuario ex: Hello alguem
				//log.info("htmlResponse Email = " + htmlResponse);
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
			
			//DNS
			email.setHostName("smtp.gmail.com"); 			//servidor SMTP para envio do e-mail
			//email.setHostName("br736.hostgator.com.br"); 	//servidor SMTP para envio do e-mail ou ns737.hostgator.com.br
			email.setFrom(this.email, "I Like Too"); 		// remetente				
			//email.setAuthentication(usuario, senha);
			email.setSmtpPort(587);
			//email.setSmtpPort(465);
	        email.setAuthenticator(new DefaultAuthenticator(this.email, this.senha));  
	        email.setSSLOnConnect(true);
			email.setSSL(true);
			email.setTLS(true);
			
			//destinat�rio
			email.setCharset("utf-8");
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
