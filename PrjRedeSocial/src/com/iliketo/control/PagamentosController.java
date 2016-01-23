package com.iliketo.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.VideoDAO;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.exception.VideoILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.Collection;
import com.iliketo.model.Video;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class PagamentosController {
	
	
	static final Logger log = Logger.getLogger(PagamentosController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	
	/** pagina de retorno de pagamento concluido com sucesso pelo paypal */
	@RequestMapping(value={"/pagamentos/paymentCompleted"})
	public String paymentCompletedPayPal(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Announce anuncio = (Announce) request.getSession().getAttribute("announce");	 //recupera anuncio da sessao
		if(anuncio != null){
			ModelILiketo model = new ModelILiketo(request, response);
			model.addAttribute("announce", anuncio);
		}		
		return "page.jsp?id=992";
	}
	
	
	/** metodo recebe as mensagens de notificacao NIP enviadas pelo paypal */
	@RequestMapping(value={"/pagamentos/notificacaoIPN"}, method=RequestMethod.POST)
	public void pagamentosNotificacaoNIP(HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*
		PROCEDIMENTOS NECESSARIOS:
		1. Um usuário clica em um botão de pagamento, ou sua aplicação faz uma chamada para uma operação da API, ou um evento externo ocorre, como um pagamento de recorrência, ou uma disputa;
		2. O serviço no notificações da PayPal envia um HTTP POST para sua aplicação, contendo uma mensagem IPN;
		3. Seu manipulador de notificações retorna um status HTTP 200 sem conteúdo;
		4. Seu manipulador de notificações faz um HTTP POST, na mesma ordem, com os mesmos campos e codificação recebidos, de volta para a PayPal;
		5. PayPal retorna uma string simples, contendo apenas VERIFIED, caso a mensagem seja válida, ou INVALID, caso a mensagem seja inválida;
		
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
		Canceled_Reversal – Esse campo ocorre quando existe uma disputa e os valores que tinham sido revertidos anteriormente, voltam para sua conta.
		Completed – Significa que a transação está completa e o valor foi depositado em sua conta. Você pode entregar os produtos para o cliente ou liberar acesso à alguma área exclusiva, ou conteúdo digital.
		Denied – Significa que a transação foi negada. Esse valor apenas ocorre, caso o status anterior tenha sido Pending e o campo pending_reason tenha sido algum dos valores descritos no campo Fraud_Management_Filters_x.
		Expired – Significa que a autorização expirou e não pode mais ser capturada.
		Failed – Significa que o pagamento falhou. Esse valor apenas ocorre, caso o cliente tenha utilizado sua conta em bancária para fazer o pagamento.
		Pending – Significa que o pagamento está pendente de revisão. Caso esse campo ocorra, verifique o campo pending_reason para mais detalhes sobre o motivo.
		Refunded – Significa que um reembolso foi emitido.
		Reversed – Significa que um pagamento foi revertido por causa de um chargeback ou qualquer outro motivo. O valor que havia sido pago foi removido da conta do vendedor e devolvido para a conta do cliente. O motivo da reversão pode ser encontrado no campo ReasonCode.
		Processed – Significa que um pagamento foi aceito.
		Voided – Significa que uma autorização foi cancelada.
		
		AMBIENTES PAYPAL:
		Sandbox		https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		Produção	https://www.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		*/
		
		log.info(request.getRequestURL());
		
		//constantes		
		final String Completed = "Completed";
		final String EMAIL_PAYPAL_ILIKETOO = "contato.iliketo@gmail.com";
		
		String receiver_email= request.getParameter("receiver_email");
		String invoice= request.getParameter("invoice");
		String payment_status= request.getParameter("payment_status");
		
		log.info("Notificacao IPN - Parametros recebidos do Paypal");
		String parametros = "";
		Enumeration en = request.getParameterNames();	    
	    while(en.hasMoreElements()){
	    	String paramName = (String) en.nextElement();
	    	String paramValue = (String) request.getParameter(paramName);
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
			if(payment_status.equals(Completed)){
				//transacao ok, o pagamento foi depositado				
				if(invoice != null && !invoice.isEmpty()){					
					AnnounceDAO dao = new AnnounceDAO(db, null);
					Announce anuncio = (Announce) dao.readById(invoice, Announce.class);
					if(anuncio != null){
						if(anuncio.getTypeAnnounce().equals("Auction")){
							anuncio.setStatus("For auction");
						}else{
							anuncio.setStatus("For sale");
						}
						dao.update(anuncio, false);
						log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, STATUS ANUNCIO ATUALIZADO - ID ANUNCIO=" + invoice+" - ID MEMBRO=" + anuncio.getIdMember());
						
						//cria notificacao para o grupo da categoria
						String idCategory = anuncio.getIdCategory();
						if(idCategory != null && !idCategory.equals("")){
							String myUserid = anuncio.getIdMember();
							NotificationService.createNotification(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.INCLUDED, myUserid);
							if(anuncio.getTypeAnnounce().equals("Auction")){
								//notificacao aviso uma hora antes leilao
								NotificationService.createNotificationAuctionOneHour(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.AUCTION_HOUR, myUserid, anuncio.getDateInitial());
							}
						}						
					}else{
						log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + invoice);
					}					
				}else{
					log.info("Notificacao IPN - invoice(id anuncio) invalido!");
				}
			}else{
				log.info("Notificacao IPN - Novo evento da notificacao!");
			}
		}else{
			log.info("Notificacao IPN - Descartada, email vendedor invalido: " + receiver_email);
		}
	}
	
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
	    while(en.hasMoreElements()){
	    	String paramName = (String) en.nextElement();
	    	String paramValue = (String) request.getParameter(paramName);	
	    	log.info(paramName+"="+paramValue);
	    	if(parametros.equals("")){
	    		parametros += paramName + "=" + URLEncoder.encode(paramValue);
	    	}else{
	    		parametros += "&" + paramName + "=" + URLEncoder.encode(paramValue);
	    	}
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
		
		//info vendedor
		String receiver_id= request.getParameter("receiver_id");
		String receiver_email= request.getParameter("receiver_email");
		String business= request.getParameter("business");
		String residence_country= request.getParameter("residence_country");
		
		//info transacao
		String test_ipn= request.getParameter("test_ipn");
		String txn_id= request.getParameter("txn_id");
		String txn_type= request.getParameter("txn_type");
		
		//info cliente
		String payer_email= request.getParameter("payer_email");
		String payer_id= request.getParameter("payer_id");
		String payer_status= request.getParameter("payer_status");
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
		String item_name1= request.getParameter("item_name1");
		String item_number1= request.getParameter("item_number1");
		String mc_currency= request.getParameter("mc_currency");
		String mc_handling= request.getParameter("mc_handling");
		String mc_handling1= request.getParameter("mc_handling1");
		String mc_shipping= request.getParameter("mc_shipping");
		String mc_shipping1= request.getParameter("mc_shipping1");
		String mc_gross= request.getParameter("mc_gross");
		String mc_gross1= request.getParameter("mc_gross1");
		String mc_fee= request.getParameter("mc_fee");
		String tax= request.getParameter("tax");
		String invoice= request.getParameter("invoice");
		String payment_date= request.getParameter("payment_date");
		String payment_status= request.getParameter("payment_status");
		String payment_type= request.getParameter("payment_type");
		String verify_sign= request.getParameter("verify_sign");
		String quantity1= request.getParameter("quantity1");
		String pending_reason= request.getParameter("pending_reason");
		String reason_code= request.getParameter("reason_code");
		
		//outros
		String notify_version= request.getParameter("notify_version");
		
		//insert tabela log_ipn
		HashMap<String, String> mapLog = new HashMap<String, String>();
		if(txn_id != null) 			mapLog.put("txn_id", txn_id);
		if(txn_type != null) 		mapLog.put("txn_type", txn_type);
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
	    if(mc_gross != null) 		mapTransa.put("gross", mc_gross);
	    if(mc_fee != null) 			mapTransa.put("fee", mc_fee);
	    if(mc_handling != null)	 	mapTransa.put("handling", mc_handling);
	    if(mc_shipping != null) 	mapTransa.put("shipping", mc_shipping);
	    if(tax != null) 			mapTransa.put("tax", tax);
	    if(payment_status != null) 	mapTransa.put("payment_status", payment_status);
	    if(pending_reason != null) 	mapTransa.put("pending_reason", pending_reason);
	    if(reason_code != null) 	mapTransa.put("reason_code", reason_code);
	    if(payment_date != null) 	mapTransa.put("payment_date", payment_date);
	    db.insert("transacao", mapTransa);
	    
	}

}
