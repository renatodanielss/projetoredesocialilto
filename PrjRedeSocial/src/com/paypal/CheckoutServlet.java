package com.paypal;


/*==================================================================
 PayPal Express Check out Call
 ===================================================================
*/


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.EventDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Announce;
import com.iliketo.model.Event;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;

public class CheckoutServlet  extends HttpServlet {

	static final Logger log = Logger.getLogger(CheckoutServlet.class);
	private CmsConfigILiketo cmsUtilsIliketoo;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2722761580200224133L;	
    
	public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {
		
		/** classe para auxiliar nos parametros do request*/
		cmsUtilsIliketoo = new CmsConfigILiketo(request, response);
		
		HttpSession session = request.getSession();
        PayPal paypal = new PayPal(cmsUtilsIliketoo.getMyrequest().getRequest());
        /*
        '------------------------------------
        ' The returnURL is the location where buyers return to when a
        ' payment has been successfully authorized.
        '------------------------------------
        */
        
        String returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/ilt/Return?page=review";
        log.info("returnURL review: " + returnURL);
        
        if(paypal.getUserActionFlag().equals("true")){
        	returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/ilt/Return?page=return";
        	log.info("returnURL return: " + returnURL);
        }
        /*
        '------------------------------------
        ' The cancelURL is the location buyers are sent to when they hit the
        ' cancel button during authorization of payment during the PayPal flow
        '------------------------------------
        */
        String cancelURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/cancel.jsp";
		Map<String,String> checkoutDetails = new HashMap<String, String>() ;
		checkoutDetails=setRequestParams(request);
        //Redirect to check out page for check out mark
        if(!isSet(request.getParameter("Confirm")) && isSet(request.getParameter("checkout"))){
        	session.setAttribute("checkoutDetails", checkoutDetails);

    		if(isSet(request.getParameter("checkout")) || isSet(session.getAttribute("checkout"))) {
    			session.setAttribute("checkout", StringEscapeUtils.escapeHtml4(request.getParameter("checkout")));
    		}
    		
	    	//Assign the Return and Cancel to the Session object for ExpressCheckout Mark
	    	session.setAttribute("EXPRESS_MARK", "ECMark");
	    	
	    	request.setAttribute("PAYMENTREQUEST_0_AMT", StringEscapeUtils.escapeHtml4(request.getParameter("PAYMENTREQUEST_0_AMT")));
	    	//redirect to check out page
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("checkout.jsp");
	    	if (dispatcher != null){
	    		dispatcher.forward(request, response);
	    	}
        }
        else{
        	Map<String, String> nvp=null;
        	if(isSet(session.getAttribute("EXPRESS_MARK")) && session.getAttribute("EXPRESS_MARK").equals("ECMark")){
        		checkoutDetails.putAll((Map<String, String>) session.getAttribute("checkoutDetails"));
        		checkoutDetails.putAll(setRequestParams(request));
        		if(isSet(checkoutDetails.get("shipping_method"))) {
	        		BigDecimal new_shipping = new BigDecimal(checkoutDetails.get("shipping_method")); //need to change this value, just for testing
	        		BigDecimal shippingamt = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_SHIPPINGAMT"));
	        		BigDecimal paymentAmount = new BigDecimal(checkoutDetails.get("PAYMENTREQUEST_0_AMT"));
	        		if(shippingamt.compareTo(new BigDecimal(0)) > 0){ 
	        			paymentAmount = paymentAmount.add(new_shipping).subtract(shippingamt) ;
	        		}
	        		//session.setAttribute("PAYMENTREQUEST_0_AMT",paymentAmount.toString());  //.replace(".00", "")
	        		//session.setAttribute("PAYMENTREQUEST_0_SHIPPINGAMT",new_shipping.toString());	
	        		//session.setAttribute("shippingAmt",new_shipping.toString());
	        		String val = checkoutDetails.put("PAYMENTREQUEST_0_AMT",paymentAmount.toString());  //.replace(".00", "")
	        		
	        		checkoutDetails.put("PAYMENTREQUEST_0_SHIPPINGAMT",new_shipping.toString());	
	        		checkoutDetails.put("shippingAmt",new_shipping.toString());
	        	}
		        	returnURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/lightboxreturn.jsp";
	        		nvp = paypal.callMarkExpressCheckout(checkoutDetails, returnURL, cancelURL);  
	        		session.setAttribute("checkoutDetails", checkoutDetails);
        	} else {
        		//session.invalidate(); //linha comentada porque estava derrubando a sessão do usuário no momento de fazer o checkout
        		
        		/** Valida checkout para Anuncios, destaques e eventos */
				this.validaCheckoutParaAnuncio(checkoutDetails);
				this.validaCheckoutParaEvento(checkoutDetails);
				this.validaCheckoutDestaqueDoAnuncio();
				
        		session = request.getSession();
        		nvp = paypal.callShortcutExpressCheckout (checkoutDetails, returnURL, cancelURL);
        		session.setAttribute("checkoutDetails", checkoutDetails);
        	}
	        
			String strAck = nvp.get("ACK").toString().toUpperCase();
	        if(strAck !=null && (strAck.equals("SUCCESS") || strAck.equals("SUCCESSWITHWARNING") ))
	        {
	            session.setAttribute("TOKEN", nvp.get("TOKEN").toString());
	            //Redirect to paypal.com
	            paypal.redirectURL(response, nvp.get("TOKEN").toString(),(isSet(session.getAttribute("EXPRESS_MARK")) && session.getAttribute("EXPRESS_MARK").equals("ECMark") || (paypal.getUserActionFlag().equalsIgnoreCase("true"))) );
 	        }
	        else
	        {
	            // Display a user friendly Error on the page using any of the following error information returned by PayPal
	            String ErrorCode = nvp.get("L_ERRORCODE0").toString();
	            String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
	            String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
	            String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
	            
	            String errorString = "SetExpressCheckout API call failed. "+
	           		"<br>Detailed Error Message: " + ErrorLongMsg +
			        "<br>Short Error Message: " + ErrorShortMsg +
			        "<br>Error Code: " + ErrorCode +
			        "<br>Error Severity Code: " + ErrorSeverityCode;
	            log.error("Erro na classe CheckoutServlet: " + errorString);
	            request.setAttribute("error", errorString);
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
	        	//session.invalidate();
	        	if (dispatcher != null){
	        		dispatcher.forward(request, response);
	        	}
	            
	        }
        }
	}

	private Map<String,String> setRequestParams(HttpServletRequest request){
		Map<String, String> requestMap = new HashMap<String, String>();

		/** Verifica se request eh MultipartContent enctype. Chama metodo para extrair e recuperar os dados no parametro */
		if(ServletFileUpload.isMultipartContent(request)){
			requestMap = cmsUtilsIliketoo.recuperarDadosMultipartContent();
		}else{
			for (String key : request.getParameterMap().keySet()) {
				requestMap.put(key, StringEscapeUtils.escapeHtml4(request
						.getParameterMap().get(key)[0]));
			}
		}
		
		return requestMap;
	}
	
	private boolean isSet(Object value){
		return (value !=null && value.toString().length()!=0);
	}
	
	/**
	 * Metodo valida se eh um checkout para anuncios de evento, salva no bd e faz upload da imagem do evento.
	 */
	private void validaCheckoutParaEvento(Map<String, String> checkoutDetails){
		HttpServletRequest req = cmsUtilsIliketoo.getMyrequest().getRequest();
		HttpServletResponse res = cmsUtilsIliketoo.getMyresponse().getResponse();
		ModelILiketo model = new ModelILiketo(req, res);	
		try {
			if(req.getRequestURI().contains("CheckoutEvent")){				
				//operacao de checkout para criar novo anuncio de um evento e fazer upload da imagem
				DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
				Event evento = (Event) cmsUtilsIliketoo.getObjectOfParameter(Event.class);
				cmsUtilsIliketoo.processFileuploadImagemAnuncio(evento);
	
				EventDAO dao = new EventDAO(db, req);
				String idRegistro = dao.create(evento);
				evento.setIdEvent(idRegistro);
				evento.setId(idRegistro);
				checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", idRegistro);	//put no map o id do novo registro do evento
				
				req.getSession().setAttribute("eventoCheckout", evento);
				log.info("Servlet Paypal Novo Checkout para Anuncio de Evento - Anuncio salvo como Pendente pagamento... id evento=" + idRegistro);
				
			}else if(req.getRequestURI().contains("CheckoutContinueEvent")){				
				//operacao de checkout para continuar um anuncio de evento pendente com pagamento nao concluido		
				DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
				String idEvento = req.getParameter("id_event");
				Event evento = (Event) new EventDAO(db, req).readById(idEvento, Event.class);
				req.getSession().setAttribute("eventoCheckout", evento);	
				log.info("Servlet Paypal Continuar Checkout para Anuncio de Evento - Anuncio salvo como Pendente pagamento... id evento=" + idEvento);
			}
			
		} catch (StorageILiketoException e) {
			e.printStackTrace();
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			model.redirectError("/ilt/event/newEvent");
		}
	}
	
	/**
	 * Metodo valida se eh um checkout de anuncios, salva no bd e faz upload da imagem
	 */
	private void validaCheckoutParaAnuncio(Map<String, String> checkoutDetails) {

		HttpServletRequest req = cmsUtilsIliketoo.getMyrequest().getRequest();
		HttpServletResponse res = cmsUtilsIliketoo.getMyresponse().getResponse();
		ModelILiketo model = new ModelILiketo(req, res);
		String idDaColecao = "";
		
		try {
			if(req.getRequestURI().contains("CheckoutAd")){
				
				//operacao de checkout para criar novo anuncio e fazer upload da imagem
				DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
				Announce anuncio = (Announce) cmsUtilsIliketoo.getObjectOfParameter(Announce.class);
				idDaColecao = anuncio.getIdCollection();
				
				if(anuncio.getIdItem() != null && !anuncio.getIdItem().isEmpty()){
					//anuncio do item que foi adicionado na colecao - copia imagem do item existente
					String imagemItem = cmsUtilsIliketoo.processFileuploadCopiarImagemAnuncio(anuncio.getPathPhotoAd());
					anuncio.setPathPhotoAd(imagemItem);
				}else{
					//anuncio de um item que nao foi adicionado na colecao - salva nova imagem
					cmsUtilsIliketoo.processFileuploadImagemAnuncio(anuncio);
				}
				anuncio.setStatus("Pending pay");
	
				AnnounceDAO dao = new AnnounceDAO(db, req);
				String idRegistro = dao.create(anuncio);
				anuncio.setIdCollection(idRegistro);
				anuncio.setId(idRegistro);
				checkoutDetails.put("L_PAYMENTREQUEST_0_NUMBER0", idRegistro);	//put no map o id do novo registro do anuncio
				
				req.getSession().setAttribute("anuncioCheckout", anuncio);
				log.info("Servlet Paypal Novo Checkout para anuncio - Anuncio salvo como Pendente pagamento... id=" + idRegistro);
				
			}else if(req.getRequestURI().contains("CheckoutContinueAd")){
				
				//operacao de checkout para continuar um anuncio pendente com pagamento	nao concluido		
				DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
				AnnounceDAO dao = new AnnounceDAO(db, req);

				String idAnuncio = req.getParameter("id_announce");
				Announce anuncio = (Announce) dao.readById(idAnuncio, Announce.class);
				
				req.getSession().setAttribute("anuncioCheckout", anuncio);	
				log.info("Servlet Paypal Continuar Checkout do anuncio - Anuncio salvo como Pendente pagamento... id="+idAnuncio);
			}
			
		} catch (StorageILiketoException e) {
			e.printStackTrace();
		} catch (ImageILiketoException e) {
			model.addMessageError("imageFormat", "Upload only Image in jpg format.");
			model.redirectError("/ilt/registerAnnounce/collector/itemOfCollection/" + idDaColecao);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo valida se eh um checkout para comprar destaques de anuncios.
	 */
	private void validaCheckoutDestaqueDoAnuncio() {
		HttpServletRequest req = cmsUtilsIliketoo.getMyrequest().getRequest();
		if(req.getRequestURI().contains("CheckoutFeatured")){
			//operacao de checkout para comprar destaque de anuncios
			DB db = (DB) req.getAttribute(Str.CONNECTION_DB);
			String idAnuncio = req.getParameter("id_announce");
			if(idAnuncio != null && !idAnuncio.isEmpty()){
				Announce anuncio = (Announce) new AnnounceDAO(db, req).readById(idAnuncio, Announce.class);
				if(anuncio != null){
					req.getSession().setAttribute("anuncioDestaqueCheckout", anuncio);
					log.info("Servlet Paypal Novo Checkout para anuncio - Processo de compra de destaque do anuncio... idAnuncio=" + idAnuncio);
				}else{
					log.info("Servlet Paypal Novo Checkout para destaque de anuncios - registro do anuncio nao encontrado no bd... idAnuncio=" + idAnuncio);
				}
			}
		}
	}
	
}