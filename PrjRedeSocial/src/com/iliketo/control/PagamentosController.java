package com.iliketo.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.MemberDAO;
import com.iliketo.dao.MessageInboxDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Member;
import com.iliketo.model.MessageInbox;
import com.iliketo.service.NotificationService;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class PagamentosController {
	
	
	static final Logger log = Logger.getLogger(PagamentosController.class);
	
	/**
	 * M�todo intercepta erros de Exception, salva no log e direciona para pagina de erro.
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
		1. Um usu�rio clica em um bot�o de pagamento, ou sua aplica��o faz uma chamada para uma opera��o da API, ou um evento externo ocorre, como um pagamento de recorr�ncia, ou uma disputa;
		2. O servi�o no notifica��es da PayPal envia um HTTP POST para sua aplica��o, contendo uma mensagem IPN;
		3. Seu manipulador de notifica��es retorna um status HTTP 200 sem conte�do;
		4. Seu manipulador de notifica��es faz um HTTP POST, na mesma ordem, com os mesmos campos e codifica��o recebidos, de volta para a PayPal;
		5. PayPal retorna uma string simples, contendo apenas VERIFIED, caso a mensagem seja v�lida, ou INVALID, caso a mensagem seja inv�lida;
		
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
		Canceled_Reversal � Esse campo ocorre quando existe uma disputa e os valores que tinham sido revertidos anteriormente, voltam para sua conta.
		Completed � Significa que a transa��o est� completa e o valor foi depositado em sua conta. Voc� pode entregar os produtos para o cliente ou liberar acesso � alguma �rea exclusiva, ou conte�do digital.
		Denied � Significa que a transa��o foi negada. Esse valor apenas ocorre, caso o status anterior tenha sido Pending e o campo pending_reason tenha sido algum dos valores descritos no campo Fraud_Management_Filters_x.
		Expired � Significa que a autoriza��o expirou e n�o pode mais ser capturada.
		Failed � Significa que o pagamento falhou. Esse valor apenas ocorre, caso o cliente tenha utilizado sua conta em banc�ria para fazer o pagamento.
		Pending � Significa que o pagamento est� pendente de revis�o. Caso esse campo ocorra, verifique o campo pending_reason para mais detalhes sobre o motivo.
		Refunded � Significa que um reembolso foi emitido.
		Reversed � Significa que um pagamento foi revertido por causa de um chargeback ou qualquer outro motivo. O valor que havia sido pago foi removido da conta do vendedor e devolvido para a conta do cliente. O motivo da revers�o pode ser encontrado no campo ReasonCode.
		Processed � Significa que um pagamento foi aceito.
		Voided � Significa que uma autoriza��o foi cancelada.
		
		AMBIENTES PAYPAL:
		Sandbox		https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		Produ��o	https://www.paypal.com/cgi-bin/webscr?cmd=_notify-validate
		*/
		
		log.info(request.getRequestURL());
		
		//constantes		
		final String Completed = "Completed";
		//final String EMAIL_PAYPAL_ILIKETOO = "payment@iliketoo.com";
		final String EMAIL_PAYPAL_ILIKETOO = "contato.iliketo-facilitator@gmail.com";
		
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
					//transacao ok, o pagamento foi depositado						
					AnnounceDAO dao = new AnnounceDAO(db, null);
					if(custom.equals("Ad")){
						Announce anuncio = (Announce) dao.readById(invoice, Announce.class);
						if(anuncio != null){
							if(anuncio.getTypeAnnounce().equals("Auction")){
								anuncio.setStatus("For auction");
							}else{
								anuncio.setStatus("For sale");
							}
							anuncio.setPaymentStatus(payment_status);	//status pagamento do anuncio
							dao.update(anuncio, false);
							log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, STATUS ANUNCIO ATUALIZADO - ID ANUNCIO=" + invoice+" - ID MEMBRO=" + anuncio.getIdMember());
							
							//cria notificacao para o grupo da categoria
							String myUserid = anuncio.getIdMember();
							String idCategory = anuncio.getIdCategory();
							if(idCategory != null && !idCategory.equals("")){							
								NotificationService.createNotification(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.INCLUDED, myUserid);
								if(anuncio.getTypeAnnounce().equals("Auction")){
									//notificacao aviso uma hora antes leilao
									NotificationService.createNotificationAuctionOneHour(db, idCategory, "announce", anuncio.getIdAnnounce(), Str.AUCTION_HOUR, myUserid, anuncio.getDateInitial());
								}
							}
							
							//cria notificacao e uma mensagem automatica do sistema para informar o usuario que o pagamento foi concluido
							MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);						
							MessageInbox message = new MessageInbox();
							message.setIdAnnounce(anuncio.getIdAnnounce());		//id anuncio
							message.setSubject(anuncio.getTitle());				//assunto
							message.setReceiverIdMember(myUserid);				//id destinatario
							message.setSenderIdMember("0");						//sender id "0" corresponde ao admin do sistema I Like Too
							message.setWasRead("n");							//nao lida
							message.setMessage("Completed_Ad");					//tipo mensagem automatica, status Completed pagamento
							message.setContentType("item");						//conteudo
							message.setIdContent(anuncio.getIdItem());			//id item
							message.setFkMsgId("0");							//sem id resposta
							message.setSenderHidden("n");						//nao oculta
							message.setReceiverHidden("n");						//nao oculta						
							String idCreate = messageDAO.create(message);		//cria e envia mensagem						
							//cria notificacao de envio de mensagem
							NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, myUserid);
							
							//envia email para todos usuarios que participam do grupo/categoria(Colecionador, interessado, hobby) do novo anuncio que foi criado
							EmailController.enviaEmailNovoAnuncioColecionadorLoja(anuncio, idCategory, myUserid, db, request);							
							return;
						}else{
							log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + invoice);
						}	
					}else if(custom.equals("Featured")){
						Announce anuncio = (Announce) dao.readById(invoice, Announce.class);
						if(anuncio != null){
							anuncio.setFeatured("yes");
							dao.update(anuncio, false);
							log.info("Notificacao IPN - PAGAMENTO CONCLUIDO COM SUCESSO, DESTAQUE ANUNCIO ATUALIZADO - ID ANUNCIO=" + invoice+" - ID MEMBRO=" + anuncio.getIdMember());
							
							String myUserid = anuncio.getIdMember();
							//cria notificacao e uma mensagem automatica do sistema para informar o usuario que o pagamento foi concluido
							MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);						
							MessageInbox message = new MessageInbox();
							message.setIdAnnounce(anuncio.getIdAnnounce());		//id anuncio
							message.setSubject(anuncio.getTitle());				//assunto
							message.setReceiverIdMember(myUserid);				//id destinatario
							message.setSenderIdMember("0");						//sender id "0" corresponde ao admin do sistema I Like Too
							message.setWasRead("n");							//nao lida
							message.setMessage("Completed_Featured");			//tipo mensagem automatica, status Completed pagamento
							message.setContentType("item");						//conteudo
							message.setIdContent(anuncio.getIdItem());			//id item
							message.setFkMsgId("0");							//sem id resposta
							message.setSenderHidden("n");						//nao oculta
							message.setReceiverHidden("n");						//nao oculta						
							String idCreate = messageDAO.create(message);		//cria e envia mensagem						
							//cria notificacao de envio de mensagem
							NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, myUserid);
							return;
						}else{
							log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + invoice);
						}
					}else if(custom.equals("Storage")){
						//atualiza conta premium para o membro

						MemberDAO memberDao = new MemberDAO(db, request);
						Member member = new Member();
						member = (Member) memberDao.readByColumn("id_member", item_number, Member.class);
						
						if(member != null){
							if (payment_status.equals("Completed")){
								log.info("Antes do if:");
								log.info("Username:" + member.getUsername());
								
								if (item_name.equals("Conta Prata - 1 GB")){
									member.setTotalSpace("1073741824");
									member.setStorageType(item_name);
									log.info("Entrou " + item_name);
								}
								else if (item_name.equals("Conta Ouro - 10 GB")){
									member.setTotalSpace("10737418240");
									member.setStorageType(item_name);
									log.info("Entrou " + item_name);
								}
								else if (item_name.equals("Conta Platina - Ilimitada")){
									member.setTotalSpace("0");
									member.setStorageType(item_name);
									log.info("Entrou " + item_name);
								}
							}
							member.setPaymentStatus(payment_status);
							log.info("Setar paymentStatus: " + payment_status);
							
							memberDao.update(member, false);
							log.info("Notificacao IPN (update) - PAGAMENTO CONCLUIDO COM SUCESSO, ARMAZENAMENTO ATUALIZADO - ID MEMBRO=" + item_number);
						}
						
						//cria notificacao e uma mensagem automatica do sistema para informar o usuario que o pagamento foi concluido						
						MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);						
						MessageInbox message = new MessageInbox();
						message.setIdAnnounce("");							//id anuncio, nao aplicavel
						message.setSubject(member.getStorageType());	//assunto
						message.setReceiverIdMember(member.getIdMember());				//id destinatario
						message.setSenderIdMember("0");						//sender id "0" corresponde ao admin do sistema I Like Too
						message.setWasRead("n");							//nao lida
						message.setMessage(member.getPaymentStatus());			//tipo mensagem automatica, status Completed pagamento
						message.setContentType("item");						//conteudo
						message.setIdContent("");							//id item
						message.setFkMsgId("0");							//sem id resposta
						message.setSenderHidden("n");						//nao oculta
						message.setReceiverHidden("n");						//nao oculta						
						String idCreate = messageDAO.create(message);		//cria e envia mensagem						
						//cria notificacao de envio de mensagem
						NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, member.getIdMember());						
						return;
					}
				}else{
					//1-Canceled_Reversal, 2-Completed, 3-Denied, 4-Expired, 5-Failed, 6-Pending, 7-Refunded, 8-Reversed, 9-Processed, 10-Voided
					if(custom.equals("Ad") || custom.equals("Featured")){
						AnnounceDAO dao = new AnnounceDAO(db, null);
						Announce anuncio = (Announce) dao.readById(invoice, Announce.class);
						if(anuncio != null){
							String myUserid = anuncio.getIdMember();
							//cria notificacao e uma mensagem automatica do sistema para informar o usuario qual status do pagamento
							MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);						
							MessageInbox message = new MessageInbox();
							message.setIdAnnounce(anuncio.getIdAnnounce());		//id anuncio
							message.setSubject(anuncio.getTitle());				//assunto
							message.setReceiverIdMember(myUserid);				//id destinatario
							message.setSenderIdMember("0");						//sender id "0" corresponde ao admin do sistema I Like Too
							message.setWasRead("n");							//nao lida
							if(custom.equals("Ad")){
								message.setMessage(payment_status + "_Ad");			//tipo mensagem automatica, status Completed pagamento
								log.info("Notificacao IPN - Pagamento anuncio nao foi concluido - ID ANUNCIO=" + invoice+" - ID MEMBRO=" + myUserid);
								//atualiza status pagamento do anuncio
								ColumnsSingleton CS = ColumnsSingleton.getInstance(db);			
								String tabela = CS.getDATA(db, "dbannounce");					//retorna tabela real
								String coluna = CS.getCOL(db, "dbannounce", "payment_status");	//retorna coluna real
								HashMap<String, String> mapData = new HashMap<String, String>();
								mapData.put(coluna, payment_status);							//coluna e valor
								db.updateWhere(tabela, "id=" + anuncio.getId(), mapData);		//update tabela anuncio
							}else{
								message.setMessage(payment_status + "_Featured");	//tipo mensagem automatica, status Completed pagamento
								log.info("Notificacao IPN - Pagamento destaque nao foi concluido - ID ANUNCIO=" + invoice+" - ID MEMBRO=" + myUserid);
							}
							message.setContentType("item");						//conteudo
							message.setIdContent(anuncio.getIdItem());			//id item
							message.setFkMsgId("0");							//sem id resposta
							message.setSenderHidden("n");						//nao oculta
							message.setReceiverHidden("n");						//nao oculta						
							String idCreate = messageDAO.create(message);		//cria e envia mensagem						
							//cria notificacao de envio de mensagem
							NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, myUserid);
							return;
						}else{
							log.info("Notificacao IPN - Nao achou anuncio no banco de dados, id=" + invoice);
						}
					}else if(custom.equals("Storage")){
						log.info("Notificacao IPN - Pagamento armazenamento nao foi concluido - ID MEMBRO=" + invoice);
						
						//cria notificacao e uma mensagem automatica do sistema para informar o usuario qual status do pagamento
						String myUserid = invoice;
						MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);						
						MessageInbox message = new MessageInbox();
						message.setIdAnnounce("");							//id anuncio, nao aplicavel
						message.setSubject("Premium Account - unlimited");	//assunto
						message.setReceiverIdMember(myUserid);				//id destinatario
						message.setSenderIdMember("0");						//sender id "0" corresponde ao admin do sistema I Like Too
						message.setWasRead("n");							//nao lida
						message.setMessage(payment_status + "_Storage");	//tipo mensagem automatica, status Completed pagamento
						message.setContentType("item");						//conteudo
						message.setIdContent("");							//id item
						message.setFkMsgId("0");							//sem id resposta
						message.setSenderHidden("n");						//nao oculta
						message.setReceiverHidden("n");						//nao oculta						
						String idCreate = messageDAO.create(message);		//cria e envia mensagem						
						//cria notificacao de envio de mensagem
						NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, myUserid);						
						return;
					}
				}			
			}else{
				log.info("Notificacao IPN - invoice(id anuncio) ou custom(tipo conteudo) invalido!");
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
