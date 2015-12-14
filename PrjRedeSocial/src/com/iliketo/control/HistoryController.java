package com.iliketo.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import HardCore.DB;

import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.AuctionBidDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.AuctionBid;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;

@Controller
public class HistoryController {

	private static final Logger log = Logger.getLogger(HistoryController.class);
	
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/history/myBids"})
	public String myBid(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);	
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		AuctionBidDAO dao = new AuctionBidDAO(db, request);
		List<Object[]> lista = dao.listAuctionBidsByUser(myUserid);
		List<Object[]> listaEncerrados = new ArrayList<Object[]>();
		List<Object[]> listaAndamento  = new ArrayList<Object[]>();
		
		try {
			long miliAgora = new java.util.Date().getTime(); 	//milisegundos agora				
			for(int i = 0; i < lista.size(); i++){
				Object[] array = lista.get(i);
				Announce anuncio = (Announce) array[1];
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
				long miliInicial = format.parse(anuncio.getDateInitial()).getTime();	//milisegundos inicial
				long miliFinal = miliInicial + ( Integer.parseInt(anuncio.getLasting()) * (1000*60*60*24) );
				//valida leilao encerrado
				if(miliFinal > miliAgora){
					listaAndamento.add(lista.get(i));
				}else{
					listaEncerrados.add(lista.get(i));
				}	
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//replaces		
		String listEntry = cms.getPageListEntry("906");		//List History - My Bids/Offers on Auction Entry
		StringBuffer resultHTMLLive = new StringBuffer();
		StringBuffer resultHTMLEnds = new StringBuffer();
		
		for(int i = 0; i < listaAndamento.size(); i++){
			AuctionBid bid = (AuctionBid) listaAndamento.get(i)[0];		//lances
			Announce anuncio = (Announce) listaAndamento.get(i)[1];		//anuncio
			String s = listEntry;
			s = s.replaceAll("@@@date_bid@@@", bid.getDateCreated());
			s = s.replaceAll("@@@bid@@@", bid.getBid());
			s = s.replaceAll("@@@title_ad@@@", anuncio.getTitle());
			s = s.replaceAll("@@@id_announce@@@", anuncio.getIdAnnounce());
			s = s.replaceAll("@@@date_initial@@@", anuncio.getDateInitial());
			s = s.replaceAll("@@@price@@@", anuncio.getPriceInitial());
			s = s.replaceAll("@@@bid_actual@@@", anuncio.getBidActual());
			s = s.replaceAll("@@@lasting@@@", anuncio.getLasting());
			s = s.replaceAll("@@@total@@@", anuncio.getTotalBids());
			s = s.replaceAll("@@@category@@@", anuncio.getNameCategory());
			s = s.replaceAll("@@@photo@@@", anuncio.getPathPhotoAd());
			s = s.replaceAll("@@@winner@@@", "");
			s = s.replaceAll("@@@you_win@@@", "");
			resultHTMLEnds.append(s);
		}
		for(int i = 0; i < listaEncerrados.size(); i++){
			AuctionBid bid = (AuctionBid) listaEncerrados.get(i)[0];		//lances
			Announce anuncio = (Announce) listaEncerrados.get(i)[1];		//anuncio
			String s = listEntry;
			s = s.replaceAll("@@@date_bid@@@", bid.getDateCreated());
			s = s.replaceAll("@@@bid@@@", bid.getBid());
			s = s.replaceAll("@@@id_announce@@@", anuncio.getIdAnnounce());
			s = s.replaceAll("@@@title_ad@@@", anuncio.getTitle());
			s = s.replaceAll("@@@date_initial@@@", anuncio.getDateInitial());
			s = s.replaceAll("@@@price@@@", anuncio.getPriceInitial());
			s = s.replaceAll("@@@bid_actual@@@", anuncio.getBidActual());
			s = s.replaceAll("@@@lasting@@@", anuncio.getLasting());
			s = s.replaceAll("@@@total@@@", anuncio.getTotalBids());
			s = s.replaceAll("@@@category@@@", anuncio.getNameCategory());
			s = s.replaceAll("@@@photo@@@", anuncio.getPathPhotoAd());
			String nickname = IliketoDAO.getValueOfDatabase(db, "nickname", "dbmembers", "id_member", anuncio.getBidUserId());
			if(nickname != null && !nickname.equals("")){
				s = s.replaceAll("@@@winner@@@", nickname);
			}else{
				s = s.replaceAll("@@@winner@@@", "Member deactivated");
			}
			if(anuncio.getBidUserId().equals(myUserid)){
				s = s.replaceAll("@@@you_win@@@", "You won the auction!");
			}
			resultHTMLEnds.append(s);
		}
		
		ModelILiketo model = new ModelILiketo(request, response);
		model.addAttribute("listHistoryMyBidsEntryLive", resultHTMLLive);	//recuperar list entry html na jsp
		model.addAttribute("listHistoryMyBidsEntryEnds", resultHTMLEnds);	//recuperar list entry html na jsp
		
		return "page.jsp?id=904";	//page My Bid		
	}	
	
	@RequestMapping(value={"/history/myShopping"})
	public String myShopping(){
		
		return "page.jsp?id=911";
	}	
	
	@RequestMapping(value={"/history/myTrade"})
	public String myTrade(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());		
		
		return "page.jsp?id=912";
	}
	
	@RequestMapping(value={"/history/myTrade/saveBuyer"})
	public String myTradeNegotiateSales(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		AnnounceDAO dao = new AnnounceDAO(db, request);
		String myUserid = (String) request.getSession().getAttribute("userid");
		
		String idAds = request.getParameter("idAds");
		String comprador = request.getParameter("idBuyer");
		
		//valida parametros
		if(idAds != null && !idAds.isEmpty() && comprador != null && !comprador.isEmpty()){
			//valida comprador
			if(!comprador.equals(myUserid)){
				Announce anuncio = (Announce) dao.readById(idAds, Announce.class);
				//valida anuncio
				if(anuncio != null){
					anuncio.setIdBuyer(comprador);
					anuncio.setStatus("Sold");
					dao.update(anuncio, false);		//atualiza anuncio
					return "redirect:/ilt/history/myTrade";
				}
			}			
		}

		//conteudo indisponivel/dados invalidos
		return "/page.jsp?id=invalid";
	}
	
	
}
