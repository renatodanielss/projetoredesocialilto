package com.iliketo.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.control.EmailController.tipoEmail;
import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.EventDAO;
import com.iliketo.dao.MemberDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Event;
import com.iliketo.model.Member;
import com.iliketo.service.NotificationService;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class PagamentosController {
	
	@Autowired HttpServletRequest httpRequest;
	static final Logger log = Logger.getLogger(PagamentosController.class);
	
	/**
	 * Mï¿½todo intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
		
	/** pagina de retorno de pagamento concluido com sucesso pelo paypal para anuncio */
	@RequestMapping(value={"/registerAnnounce/paymentCompleted"})
	public String paymentCompletedPayPalAnuncio(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		Announce anuncio = (Announce) request.getSession().getAttribute("announce");	 //recupera anuncio da sessao
		if(anuncio != null){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("announce", anuncio);
		}		
		return "page.jsp?id=992";
	}
	
	/** pagina de retorno de pagamento concluido com sucesso pelo paypal para armazenamento */
	@RequestMapping(value={"/storage/paymentCompleted"})
	public String paymentCompletedPayPalArmazenamento(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());		
		return "page.jsp?id=998";
	}
	
	/** pagina de retorno de pagamento concluido com sucesso pelo paypal para destaque de anuncio */
	@RequestMapping(value={"/featured/paymentCompleted"})
	public String paymentCompletedPayPalDestaque(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());	
		return "page.jsp?id=999";
	}
	
	
	/** metodo recebe as mensagens de notificacao NIP enviadas pelo paypal */
	@RequestMapping(value={"/pagamentos/notificacaoIPN"}, method=RequestMethod.POST)
	public void pagamentosNotificacaoNIP(HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*
		PROCEDIMENTOS NECESSARIOS:
		1. Um usuï¿½rio clica em um botï¿½o de pagamento, ou sua aplicaï¿½ï¿½o faz uma chamada para uma operaï¿½ï¿½o da API, ou um evento externo ocorre, como um pagamento de recorrï¿½ncia, ou uma disputa;
		2. O serviï¿½o no notificaï¿½ï¿½es da PayPal envia um HTTP POST para sua aplicaï¿½ï¿½o, contendo uma mensagem IPN;
		3. Seu manipulador de notificaï¿½ï¿½es retorna um status HTTP 200 sem conteï¿½do;
		4. Seu manipulador de notificaï¿½ï¿½es faz um HTTP POST, na mesma ordem, com os mesmos campos e codificaï¿½ï¿½o recebidos, de volta para a PayPal;
		5. PayPal retorna uma string simples, contendo apenas VERIFIED, caso a mensagem seja vï¿½lida, ou INVALID, caso a mensagem seja invï¿½lida;
		
		EXEMPLO E RESPOSTA:	
		residence_country=BR&
		invoice=abc1234&
		address_city=S%C3%A3o+Paulo&
		first_name=Fulano&
		payer_id=FULANO01&
		mc_fee=0.44&
		txn_id=892000279&
		receiver_email=empresa%40teste.com.br&
		custom=xyz123&
		payment_date=03%3A12%3A55+17+Dec+2013+PST&
		address_country_code=BR&
		address_zip=9513&
		item_name1=Produto&
		mc_handling=2.06&
		mc_handling1=1.67&
		tax=2.02&
		address_name=Fulano+de+Tal&
		last_name=de+Tal&
		receiver_id=ABC123TESTE&
		verify_sign=AFcWxV21C7fd0v3bYYYRCpSSRl31A4V7v9CCvz&
		address_country=Brazil&
		payment_status=Completed&
		address_status=confirmed&
		business=empresa%40teste.com.br&
		payer_email=fulano%40teste.com&
		notify_version=2.4&
		txn_type=cart&
		test_ipn=1&
		payer_status=verified&
		mc_currency=BRL&
		mc_gross=12.34&
		mc_shipping=3.02&
		mc_shipping1=1.02&
		item_number1=ID-Produto&
		quantity1=3&
		address_state=SP&
		mc_gross1=9.34&
		payment_type=instant&
		address_street=Rua+de+teste%2C+123
		
		STATUS EVENTOS:
		Canceled_Reversal ï¿½ Esse campo ocorre quando existe uma disputa e os valores que tinham sido revertidos anteriormente, voltam para sua conta.
		Completed ï¿½ Significa que a transaï¿½ï¿½o estï¿½ completa e o valor foi depositado em sua conta. Vocï¿½ pode entregar os produtos para o cliente ou liberar acesso ï¿½ alguma ï¿½rea exclusiva, ou conteï¿½do digital.
		Denied ï¿½ Significa que a transaï¿½ï¿½o foi negada. Esse valor apenas ocorre, caso o status anterior tenha sido Pending e o campo pending_reason tenha sido algum dos valores descritos no campo Fraud_Management_Filters_x.
		Expired ï¿½ Significa que a autorizaï¿½ï¿½o expirou e nï¿½o pode mais ser capturada.
		Failed ï¿½ Significa que o pagamento falhou. Esse valor apenas ocorre, caso o cliente tenha utilizado sua conta em bancï¿½ria para fazer o pagamento.
		Pending ï¿½ Significa que o pagamento estï¿½ pendente de revisï¿½o. Caso esse campo ocorra, verifique o campo pending_reason para mais detalhes sobre o motivo.
		Refunded ï¿½ Significa que um reembolso foi emitido.
		Reversed ï¿½ Significa que um pagamento foi revertido por causa de um chargeback ou qualquer outro motivo. O valor que havia sido pago foi removido da conta do vendedor e devolvido para a conta do cliente. O motivo da reversï¿½o pode ser encontrado no campo ReasonCode.
		Processed ï¿½ Significa que um pagamento foi aceito.
		Voided ï¿½ Significa que uma autorizaï¿½ï¿½o foi cancelada.
		
		AMBIENTES PAYPAL:
		Sandbox		https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		Produï¿½ï¿½o	https://www.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		*/
		
		log.info(request.getRequestURL());
		
		String address_country = request.getParameter("address_country");
		
		//constantes		
		final String Completed = "Completed";
		
		final String EMAIL_PAYPAL_ILIKETOO;
 
		//Produção
		if (address_country.equals("Brazil"))
			EMAIL_PAYPAL_ILIKETOO = "payment@iliketoo.com";
		else
			EMAIL_PAYPAL_ILIKETOO = "payment.us@iliketoo.com";
		
		//Desenvolvimento
		/*if (address_country.equals("Brazil"))
			EMAIL_PAYPAL_ILIKETOO = "contato.iliketo-facilitator@gmail.com";
		else
			EMAIL_PAYPAL_ILIKETOO = "payment.us-seller@iliketoo.com";*/
		
		String receiver_email= request.getParameter("receiver_email");
		String invoice= request.getParameter("invoice");
		String payment_status= request.getParameter("payment_status");
		String item_name= request.getParameter("item_name");
		String custom= request.getParameter("custom");
		String item_number = request.getParameter("item_number");
		
		log.info("Notificacao IPN - Parametros recebidos do Paypal");
		String parametros = "";
		Enumeration en = request.getParameterNames();	    
	    while(en.hasMoreElements()){
	    	String paramName = (String) en.nextElement();
	    	String paramValue = new String(request.getParameter(paramName).getBytes("UTF-8"), "UTF-8");	
	    	log.info(paramName+"="+paramValue);
	    	parametros += "&" + paramName + "=" + paramValue;	    	
	    }
	    
		
		//Response HTTP 200
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("");
		log.info("Notificacao IPN - RESPONSE CODE HTTP 200");		
		
		//valida mensagem IPN paypal
		if(!verificaNotificacaoIPNValida(request)){
			log.info("Notificacao IPN - Descartada, Paypal nao validou a notificacao.");
			return; //finaliza mensagem nao e valida
		}
		//valida destinatario vendedor I Like too
		if(receiver_email.equalsIgnoreCase(EMAIL_PAYPAL_ILIKETOO)){
			log.info("Notificacao IPN - NOTIFICACAO VALIDA!");
			log.info("Notificacao IPN - Gravar log no banco de dados");			
			
			//grava log no banco de dados
			//grava dados do cliente			
			//grava dados da transacao
			DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
			gravarDadosTransacaoPaypal(request, db);
			log.info("Notificacao IPN - Dados gravados no banco de dados");
			
			//regras de negocio
			log.info("Notificacao IPN - payment_status: " + payment_status);						
			if(item_number != null && !item_number.isEmpty() && custom != null && !custom.isEmpty()){
				if(payment_status.equals(Completed)){
					log.info("Notificacao IPN - Pagamento foi aprovado - PAYMENT_STATUS="+payment_status + " - CUSTOM="+custom + " - ITEM_NUMBER="+item_number);
					//transacao ok, o pagamento foi aprovado e completado
					if(custom.equals("Ad")){
						this.processaMensagemCompleted_IPNAnuncio(db, payment_status, item_number, item_name);
					}else if(custom.equals("Featured")){
						this.processaMensagemCompleted_IPNDestaque(db, payment_status, item_number, item_name);
					}else if(custom.equals("Event")){
						this.processaMensagemCompleted_IPNEvento(db, payment_status, item_number, item_name);
					}else if(custom.equals("Storage")){
						this.processaMensagemCompleted_IPNStorage(db, payment_status, item_number, item_name);
					}
					
				}else{
					//Outros status IPN >>> 1-Canceled_Reversal, 2-Completed, 3-Denied, 4-Expired, 5-Failed, 6-Pending, 7-Refunded, 8-Reversed, 9-Processed, 10-Voided
					log.info("Notificacao IPN - Pagamento ainda nao foi aprovado - PAYMENT_STATUS="+payment_status + " - CUSTOM="+custom + " - ITEM_NUMBER="+item_number + " - ITEM_NAME="+item_name);
					
					if(custom.equals("Ad")){
						AnnounceDAO dao = new AnnounceDAO(db, null);
						Announce anuncio = (Announce) dao.readById(item_number, Announce.class);
						if(anuncio != null){
							anuncio.setPaymentStatus(payment_status);
							dao.update(anuncio, false);
							
							HashMap<String, String> result = new HashMap<String, String>();
							result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
							result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
							result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
							Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", anuncio.getIdMember(), Member.class);
							String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
							
							//enviar email sobre andamento status do pagamento
							EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);		
							email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);
						}else{
							log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + item_number);
						}
						return;
					}else if(custom.equals("Featured")){
						AnnounceDAO dao = new AnnounceDAO(db, null);
						Announce anuncio = (Announce) dao.readById(item_number, Announce.class);
						if(anuncio != null){
							anuncio.setPaymentStatusDestaque(payment_status);
							dao.update(anuncio, false);
							
							HashMap<String, String> result = new HashMap<String, String>();
							result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
							result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
							result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
							Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", anuncio.getIdMember(), Member.class);
							String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
							
							//enviar email sobre andamento status do pagamento
							EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);		
							email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);
						}else{
							log.info("Notificacao IPN - Nao achou anuncio para destaque no banco de dados, id=" + item_number);
						}
						return;
					}else if(custom.equals("Event")){
						EventDAO dao = new EventDAO(db, null);
						Event evento = (Event) dao.readById(item_number, Event.class);
						if(evento != null){
							evento.setPaymentStatus(payment_status);
							dao.update(evento, false);
							
							HashMap<String, String> result = new HashMap<String, String>();
							result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
							result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
							result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
							Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", evento.getIdMember(), Member.class);
							String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
							
							//enviar email sobre andamento status do pagamento
							EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);		
							email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);
						}else{
							log.info("Notificacao IPN - Nao achou evento no banco de dados, id=" + item_number);
						}
						return;
					}else if(custom.equals("Storage")){
						MemberDAO dao = new MemberDAO(db, null);
						Member member = (Member) dao.readByColumn("id_member", item_number, Member.class);
						if(member != null){
							member.setPaymentStatus(payment_status);
							dao.update(member, false);
							
							HashMap<String, String> result = new HashMap<String, String>();
							result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
							result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
							result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
							String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
							
							//enviar email sobre andamento status do pagamento
							EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);
							email.enviaEmailPagamentoStorage(member, result, localeStr, httpRequest, false);
						}else{
							log.info("Notificacao IPN - Nao achou membro no banco de dados, id=" + item_number);
						}
						return;
					}
				}			
			}else{
				log.info("Notificacao IPN - item_number(id anuncio) ou custom(tipo conteudo) invalido!");
			}			
		}else{
			log.info("Notificacao IPN - Descartada, email vendedor invalido: " + receiver_email);
		}
	}
	
	/**
	 * Metodo trata mensagem IPN status Completed para "Storage"
	 */
	private void processaMensagemCompleted_IPNStorage(DB db, String payment_status, String item_number, String item_name){
		MemberDAO memberDao = new MemberDAO(db, null);
		Member member = new Member();
		member = (Member) memberDao.readByColumn("id_member", item_number, Member.class);
		
		if(member != null){
			if (payment_status.equals("Completed")){
				log.info("Antes do if:");
				log.info("Username:" + member.getUsername());
				
				SimpleDateFormat dueDate = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(new java.util.Date());
				
				if (item_name.equals("250 GB por Seis Meses") || item_name.equals("250 GB for Six Months")){
					member.setTotalSpace("268435456000");
					member.setStorageType(item_name);
					
					c.add(Calendar.MONTH, 6);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("500 GB por Seis Meses") || item_name.equals("500 GB for Six Months")){
					member.setTotalSpace("536870912000");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 6);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("750 GB por Seis Meses") || item_name.equals("750 GB for Six Months")){
					member.setTotalSpace("805306368000");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 6);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("1 TB por Seis Meses") || item_name.equals("1 TB for Six Months")){
					member.setTotalSpace("1099511627776");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 6);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("250 GB por Um Ano") || item_name.equals("250 GB for One Year")){
					member.setTotalSpace("268435456000");
					member.setStorageType(item_name);
					
					c.add(Calendar.MONTH, 12);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("500 GB por Um Ano") || item_name.equals("500 GB for One Year")){
					member.setTotalSpace("536870912000");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 12);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("750 GB por Um Ano") || item_name.equals("750 GB for One Year")){
					member.setTotalSpace("805306368000");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 12);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
				else if (item_name.equals("1 TB por Um Ano") || item_name.equals("1 TB for One Year")){
					member.setTotalSpace("1099511627776");
					member.setStorageType(item_name);

					c.add(Calendar.MONTH, 12);  // number of days to add
					member.setDueDate(dueDate.format(c.getTime()));
					
					log.info("Due Date: " + dueDate.format(c.getTime()));
					log.info("Entrou " + item_name);
				}
			}
			member.setPaymentStatus(payment_status);
			log.info("Setar paymentStatus: " + payment_status);
			
			memberDao.update(member, false);
			log.info("Notificacao IPN (update) - PAGAMENTO CONCLUIDO COM SUCESSO, ARMAZENAMENTO ATUALIZADO - ID MEMBRO=" + item_number);
			
			HashMap<String, String> result = new HashMap<String, String>();
			result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
			result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
			result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
			
			String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
			
			//enviar email sobre status do pagamento completo aqui
			EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);
			email.enviaEmailPagamentoStorage(member, result, localeStr, httpRequest, false);
			
		}else{
			log.info("Notificacao IPN - Nao achou membro no banco de dados, id=" + item_number);
		}					
	}
	
	/**
	 * Metodo trata mensagem IPN status Completed para "Anuncio"
	 */
	private void processaMensagemCompleted_IPNAnuncio(DB db, String payment_status, String item_number, String item_name){
		AnnounceDAO dao = new AnnounceDAO(db, null);
		Announce anuncio = (Announce) dao.readById(item_number, Announce.class);
		if(anuncio != null){
			anuncio.setStatus("For sale");
			anuncio.setPaymentStatus(payment_status);
			dao.update(anuncio, false);
			log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, STATUS ANUNCIO ATUALIZADO - ID ANUNCIO=" + item_number+" - ID MEMBRO=" + anuncio.getIdMember());
			
			//cria notificacao para o grupo da categoria
			String myUserid = anuncio.getIdMember();
			String idCategory = anuncio.getIdCategory();
			if(idCategory != null && !idCategory.equals("")){							
				NotificationService.createNotification(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.INCLUDED, myUserid);
			}			
			HashMap<String, String> result = new HashMap<String, String>();
			result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
			result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
			result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
			Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", anuncio.getIdMember(), Member.class);
			String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
			
			//enviar email sobre status do pagamento completo
			EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);		
			email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);			
			
			//envia email para todos usuarios que participam do grupo/categoria(Colecionador, interessado, hobby) do novo anuncio que foi criado
			email = new EmailController(tipoEmail.EMAIL_ANUNCIO);
			email.enviaEmailNovoAnuncioGenericoParaColecionadorLoja(anuncio, idCategory, myUserid, db, this.httpRequest, null, "anuncio");							
		}else{
			log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + item_number);
		}
	}
	
	/**
	 * Metodo trata mensagem IPN status Completed para "Destaque"
	 */
	private void processaMensagemCompleted_IPNDestaque(DB db, String payment_status, String item_number, String item_name){
		AnnounceDAO dao = new AnnounceDAO(db, null);
		Announce anuncio = (Announce) dao.readById(item_number, Announce.class);
		if(anuncio != null){
			anuncio.setFeatured("yes");
			dao.update(anuncio, false);
			log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, DESTAQUE ANUNCIO ATUALIZADO - ID ANUNCIO=" + item_number+" - ID MEMBRO=" + anuncio.getIdMember());

			HashMap<String, String> result = new HashMap<String, String>();
			result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
			result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
			result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
			Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", anuncio.getIdMember(), Member.class);			
			String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";

			//enviar email sobre status do pagamento completo
			EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);			
			email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);
			
		}else{
			log.info("Notificacao IPN - Nao achou anuncio para destaque no banco de dados, id=" + item_number);
		}
	}	
	
	/**
	 * Metodo trata mensagem IPN status Completed para "Evento"
	 */
	private void processaMensagemCompleted_IPNEvento(DB db, String payment_status, String item_number, String item_name){
		EventDAO dao = new EventDAO(db, null);
		Event evento = (Event) dao.readById(item_number, Event.class);
		if(evento != null){
			evento.setPaymentStatus(payment_status);
			dao.update(evento, false);
			log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, ANUNCIO DE EVENTO ATUALIZADO - ID EVENTO=" + item_number+" - ID MEMBRO=" +evento.getIdMember());
			
			//cria notificacao para o grupo da categoria
			String idCategory = evento.getIdCategory();
			if(idCategory != null && !idCategory.equals("")){
				NotificationService.createNotification(db, idCategory, "event", item_number, Str.INCLUDED, evento.getIdMember());
			}
			HashMap<String, String> result = new HashMap<String, String>();
			result.put("L_PAYMENTREQUEST_0_NAME0", item_name);
			result.put("PAYMENTINFO_0_PAYMENTSTATUS", payment_status);
			result.put("PAYMENTINFO_0_TRANSACTIONID", httpRequest.getParameter("txn_id"));
			Member member = (Member) new MemberDAO(db, null).readByColumn("id_member", evento.getIdMember(), Member.class);			
			String localeStr = member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")?"pt_BR":"en_US";
			
			//enviar email sobre status do pagamento completo
			EmailController email = new EmailController(tipoEmail.EMAIL_FINANCEIRO);			
			email.enviaEmailSobreStatusPagamento(member, result, localeStr, httpRequest.getParameter("custom"), httpRequest, false);
			
			//envia email marketing para membros do grupo do anuncio de evento
			email = new EmailController(tipoEmail.EMAIL_ANUNCIO);
			email.enviaEmailNovoAnuncioGenericoParaColecionadorLoja(evento, idCategory, evento.getIdMember(), db, this.httpRequest, null, "evento");
		}else{
			log.info("Notificacao IPN - Nao achou evento no banco de dados, id=" + item_number);
		}
	}
	
	
	/**
	 * Metodo responsavel por verificar e validar mensagem IPN enviada pelo PayPal
	 */
	private boolean verificaNotificacaoIPNValida(HttpServletRequest request){
		
		//constantes
		String VERIFIED = "VERIFIED";
		String INVALID = "INVALID";
		String fonte = "";
		
		if(request.getParameter("test_ipn") != null && request.getParameter("test_ipn").equals("1")){
			log.info("Notificacao IPN via AMBIENTE TESTE: https://www.sandbox.paypal.com");
			fonte = "https://www.sandbox.paypal.com";
		}else{
			log.info("Notificacao IPN via AMBIENTE PRODUCAO: https://www.paypal.com");
			fonte = "https://www.paypal.com";
		}
	    fonte += "/cgi-bin/webscr?cmd=_notify-validate";
		
	    //envia de volta os mesmo parametros do paypal para validacao
	    log.info("Notificacao IPN - Enviar parametros para confirmacao com Paypal");
	    String parametros = "";
	    Enumeration en = request.getParameterNames();	    
	    try {
		    while(en.hasMoreElements()){
		    	String paramName = (String) en.nextElement();
		    	String paramValue = new String(request.getParameter(paramName).getBytes("UTF-8"), "UTF-8");				
		    	log.info(paramName+"="+paramValue);
		    	if(parametros.equals("")){
		    		parametros += paramName + "=" + URLEncoder.encode(paramValue);
		    	}else{
		    		parametros += "&" + paramName + "=" + URLEncoder.encode(paramValue);
		    	}
		    }
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
		    URL url = new URL(fonte);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			conexao.setRequestMethod("POST");
			conexao.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conexao.setDoOutput(true);
			conexao.setDoInput(true);

			OutputStream out = conexao.getOutputStream();
			out.write(parametros.getBytes());
			out.close();
			
			//resposta paypal
	        BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
	        String resposta = in.readLine();
	        in.close();
	        
	        //valida notificacao IPN paypal
	        if(resposta != null && resposta.equals(VERIFIED)){
	        	log.info("Resposta PayPal: OK response=" + resposta);
	        	return true;
	        }else if(resposta != null && resposta.equals(INVALID)){
	        	log.info("Resposta PayPal: INVALID response=" + resposta);
	        }else{
	        	log.info("Resposta PayPal: ERROR response=" + resposta);
	        }
	        
	    } catch (IOException e) {
			e.printStackTrace();
		}
        return false;
	}
	
	private void gravarDadosTransacaoPaypal(HttpServletRequest request, DB db){
		
		//PARAMETROS DO PAYPAL EM PRODUCAO, TOTAL 38
		/*
		receiver_id=48X5Y6B9ZC3FQ
		receiver_email=contato.iliketo@gmail.com
		business=contato.iliketo@gmail.com
		residence_country=BR		
		txn_id=70X3412430903114H
		txn_type=web_accept		
		payer_email=osvaldimar1@gmail.com
		payer_id=JZ6HQYYKJQCHG
		payer_status=unverified
		first_name=Osvaldimar
		last_name=Costa
		
		custom=Ad
		item_name=Anuncio de Colecionador
		item_number=1
		mc_currency=BRL
		shipping=0.00
		mc_gross=0.50
		mc_fee=0.50
		tax=0.00
		invoice=97
		payment_date=19:45:30 Jan 25, 2016 PST
		payment_status=Completed
		payment_type=instant
		verify_sign=AIYGCrJbVJZh0zt6m9U5DVRceEfNATVfag0veiE2O-UfyYrzJLeO8Khn
		quantity=1
		notify_version=3.8
		ipn_track_id=def76a091a6bf
		
		//parametros sem necessidade de gravar na tabela
		handling_amount=0.00
		protection_eligibility=Ineligible
		charset=UTF-8
		btn_id=103486596
		payment_fee=
		insurance_amount=0.00
		discount=0.00
		shipping_discount=0.00
		shipping_method=Default
		transaction_subject=Ad
		payment_gross=
		*/
		
		//info vendedor
		String receiver_id= request.getParameter("receiver_id");				//NO
		String receiver_email= request.getParameter("receiver_email");
		String business= request.getParameter("business");
		String residence_country= request.getParameter("residence_country");	//NO
		
		//info transacao
		String test_ipn= request.getParameter("test_ipn");
		String txn_id= request.getParameter("txn_id");
		String txn_type= request.getParameter("txn_type");
		String ipn_track_id= request.getParameter("ipn_track_id");
		
		//info cliente
		String payer_email= request.getParameter("payer_email");
		String payer_id= request.getParameter("payer_id");
		String payer_status= request.getParameter("payer_status");				//NO
		String first_name= request.getParameter("first_name");
		String last_name= request.getParameter("last_name");
		String address_city= request.getParameter("address_city");					
		String address_country= request.getParameter("address_country");			
		String address_country_code= request.getParameter("address_country_code");	
		String address_name= request.getParameter("address_name");					
		String address_state= request.getParameter("address_state");				
		String address_status= request.getParameter("address_status");				
		String address_street= request.getParameter("address_street");				
		String address_zip= request.getParameter("address_zip");					
		
		//info pagamento
		String custom= request.getParameter("custom");
		String item_name= request.getParameter("item_name");
		String item_number= request.getParameter("item_number");
		String mc_currency= request.getParameter("mc_currency");
		String handling= request.getParameter("handling");
		String shipping= request.getParameter("shipping");
		String mc_gross= request.getParameter("mc_gross");
		String mc_fee= request.getParameter("mc_fee");
		String tax= request.getParameter("tax");
		String invoice= request.getParameter("invoice");
		String payment_date= request.getParameter("payment_date");
		String payment_status= request.getParameter("payment_status");
		String payment_type= request.getParameter("payment_type");
		String verify_sign= request.getParameter("verify_sign");				//NO
		String quantity= request.getParameter("quantity");		
		String pending_reason= request.getParameter("pending_reason");
		String reason_code= request.getParameter("reason_code");
		
		//outros
		String notify_version= request.getParameter("notify_version");			//NO
		
		//insert tabela log_ipn
		HashMap<String, String> mapLog = new HashMap<String, String>();
		if(txn_id != null) 			mapLog.put("txn_id", txn_id);
		if(txn_type != null) 		mapLog.put("txn_type", txn_type);
		if(test_ipn != null) 		mapLog.put("test_ipn", test_ipn);
		if(ipn_track_id != null) 	mapLog.put("ipn_track_id", ipn_track_id);
		if(receiver_email != null)	mapLog.put("receiver_email", receiver_email);
		if(payment_status != null) 	mapLog.put("payment_status", payment_status);
		if(custom != null) 			mapLog.put("custom", custom);
		if(invoice != null) 		mapLog.put("invoice", invoice);
		if(pending_reason != null) 	mapLog.put("pending_reason", pending_reason);
		if(reason_code != null) 	mapLog.put("reason_code", reason_code);
		db.insert("log_ipn", mapLog);
	    
		//insert tabela cliente
		HashMap<String, String> mapCli = new HashMap<String, String>();
		if(address_country != null) 		mapCli.put("address_country", address_country);
		if(address_city != null) 			mapCli.put("address_city", address_city);
		if(address_country_code != null) 	mapCli.put("address_country_code", address_country_code);
		if(address_name != null) 			mapCli.put("address_name", address_name);
		if(address_state != null) 			mapCli.put("address_state", address_state);
		if(address_status != null) 			mapCli.put("address_status", address_status);
		if(address_street != null) 			mapCli.put("address_street", address_street);
		if(address_zip != null) 			mapCli.put("address_zip", address_zip);
		if(first_name != null) 				mapCli.put("first_name", first_name);
		if(last_name != null) 				mapCli.put("last_name", last_name);
		if(business != null) 				mapCli.put("business_name", business);
		if(payer_email != null) 			mapCli.put("email", payer_email);
		if(payer_id != null) 				mapCli.put("paypal_id", payer_id);
	    db.insert("cliente", mapCli);
	    
	    //insert tabela transacao
	    HashMap<String, String> mapTransa = new HashMap<String, String>();
	    if(invoice != null) 		mapTransa.put("invoice", invoice);
	    if(custom != null) 			mapTransa.put("custom", custom);
	    if(txn_type != null) 		mapTransa.put("txn_type", txn_type);
	    if(txn_id != null) 			mapTransa.put("txn_id", txn_id);
	    if(payer_id != null) 		mapTransa.put("payer_id", payer_id);
	    if(mc_currency != null) 	mapTransa.put("currency", mc_currency);
	    if(mc_gross != null) 		mapTransa.put("mc_gross", mc_gross);
	    if(mc_fee != null) 			mapTransa.put("fee", mc_fee);
	    if(handling != null)	 	mapTransa.put("handling", handling);
	    if(shipping != null) 		mapTransa.put("shipping", shipping);
	    if(tax != null) 			mapTransa.put("tax", tax);
	    if(payment_status != null) 	mapTransa.put("payment_status", payment_status);
	    if(payment_type != null) 	mapTransa.put("payment_type", payment_type);
	    if(pending_reason != null) 	mapTransa.put("pending_reason", pending_reason);
	    if(reason_code != null) 	mapTransa.put("reason_code", reason_code);
	    if(payment_date != null) 	mapTransa.put("payment_date", payment_date);
	    if(quantity != null) 		mapTransa.put("quantity", quantity);
	    if(item_name != null) 		mapTransa.put("item_name", item_name);
	    if(item_number != null) 	mapTransa.put("item_number", item_number);
	    db.insert("transacao", mapTransa);
	    
	}

}
