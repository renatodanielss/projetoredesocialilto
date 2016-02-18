package com.iliketo.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.dao.AdvertisingDAO;
import com.iliketo.dao.AnnounceDAO;
import com.iliketo.dao.CollectionDAO;
import com.iliketo.dao.IliketoDAO;
import com.iliketo.dao.InterestDAO;
import com.iliketo.dao.StoreItemDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Advertising;
import com.iliketo.model.Announce;
import com.iliketo.model.Category;
import com.iliketo.model.Collection;
import com.iliketo.model.Event;
import com.iliketo.model.Interest;
import com.iliketo.model.StoreItem;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class AdvertiserController {

	
	static final Logger log = Logger.getLogger(AdvertiserController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	@RequestMapping(value={"/advertiser"})
	public String advertiser(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL() + " - Home [email: " + request.getSession().getAttribute("email") + "]");

		return "page.jsp?id=1022"; 	//Home advertiser
	}
	
	@RequestMapping(value={"/advertiser/myDirectedAds"})
	public String myDirectedAds(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL() + " - myDirectedAds [email: " + request.getSession().getAttribute("email") + "]");

		return "page.jsp?id=1031"; 	//advertiser - myDirectedAds
	}

	@RequestMapping(value={"/advertiser/myGlobalAds"})
	public String myGlobalAds(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL() + " - myGlobalAds [email: " + request.getSession().getAttribute("email") + "]");

		return "page.jsp?id=1042"; 	//advertiser - myGlobalAds
	}
	
	@RequestMapping(value={"/advertiser/directedAds/register"})
	public String directedAds(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("anuncio");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar imagem propaganda. Tela formulario registrar anuncio");
		}
		
		return "page.jsp?id=1024"; 	//page form register directed ads
	}
	
	@RequestMapping(value={"/advertiser/globalAds/register"})
	public String globalAds(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("anuncio");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar imagem propaganda. Tela formulario registrar anuncio");
		}
		
		return "page.jsp?id=1045"; 	//page form register global ads
	}
	
	@RequestMapping(value={"/advertiser/directedAds/payment"}, method=RequestMethod.POST)
	public String directedAdsPayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		
		AdvertisingDAO advDAO = new AdvertisingDAO(db, request);
		HttpSession session = request.getSession();		
		
		Advertising anuncio = (Advertising) cms.getObjectOfParameter(Advertising.class); 	//popula um objeto com dados do form
		
		String idCreated = "";
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.setUploadAdvertiser(true);			//define upload como foto para anunciante, 'pasta media advertiser'
			cms.processFileuploadImage(anuncio);	//salva arquivos
			
		} catch (StorageILiketoException e) {
			//return msg erro nao possui espaco de armazenamento suficiente
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/advertiser/directedAds/register");
		} catch (ImageILiketoException e) {
			//return msg erro formato de imagem invalido
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
			return model.redirectError("/ilt/advertiser/directedAds/register");
		}
		
		if(session.getAttribute("anuncio") == null){
			idCreated = advDAO.create(anuncio);	//salva novo anuncio no bd
		}else{
			advDAO.update(anuncio, true);		//salva edicao do anuncio no bd
			idCreated = anuncio.getIdAdvertising();
		}
		
		log.info("Advertising cadastrado com sucesso - Id: " + idCreated + " Status: Pendente pagamento! " +
				"[email: " + request.getSession().getAttribute("email") + "]");
		
		//set anuncio na session
		anuncio.setId(idCreated);
		anuncio.setIdAdvertising(idCreated);
		session.setAttribute("anuncio", anuncio);
		
		//model view jsp para binding do bean
		model.addAttribute("anuncio", anuncio);	
		
		return "page.jsp?id=1026"; 			//page form payment with paypal
	}
	
	@RequestMapping(value={"/advertiser/globalAds/payment"}, method=RequestMethod.POST)
	public String globalAdsPayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info(request.getRequestURL());
		
		//dao e cms
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);		
		AdvertisingDAO advDAO = new AdvertisingDAO(db, request);
		HttpSession session = request.getSession();		
		
		Advertising anuncio = (Advertising) cms.getObjectOfParameter(Advertising.class); 	//popula um objeto com dados do form
		
		String idCreated = "";
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.setUploadAdvertiser(true);			//define upload como foto para anunciante, 'pasta media advertiser'
			cms.processFileuploadImage(anuncio);	//salva arquivos
			
		} catch (StorageILiketoException e) {
			//return msg erro nao possui espaco de armazenamento suficiente
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/advertiser/globalAds/register");
		} catch (ImageILiketoException e) {
			//return msg erro formato de imagem invalido
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 													//msg erro
			return model.redirectError("/ilt/advertiser/globalAds/register");
		}
		
		if(session.getAttribute("anuncio") == null){
			idCreated = advDAO.create(anuncio);	//salva novo anuncio no bd
		}else{
			advDAO.update(anuncio, true);		//salva edicao do anuncio no bd
			idCreated = anuncio.getIdAdvertising();
		}
		
		log.info("Advertising cadastrado com sucesso - Id: " + idCreated + " Status: Pendente pagamento! " +
				"[email: " + request.getSession().getAttribute("email") + "]");
		
		//set anuncio na session
		anuncio.setId(idCreated);
		anuncio.setIdAdvertising(idCreated);
		session.setAttribute("anuncio", anuncio);
		
		//model view jsp para binding do bean
		model.addAttribute("anuncio", anuncio);	
		
		return "page.jsp?id=1047"; 			//page form payment with paypal
	}
	
	@RequestMapping(value={"/advertiser/paymentCompleted"})
	public String paymentCompleted(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		return "page.jsp?id=1027"; 			//page pagamento completo
	}
	
	@RequestMapping(value={"/advertiser/register/testStatus"})
	public String testCompleted(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		log.info("[TESTE] Mudar status teste anuncio");
		
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		if(id != null && !id.isEmpty() && status != null && !status.isEmpty()){
			//mudar status advertising
			DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
			AdvertisingDAO advDAO = new AdvertisingDAO(db, request);
			Advertising anuncio = (Advertising) advDAO.readById(id, Advertising.class);
			if(anuncio != null){
				anuncio.setStatus(status);
				advDAO.update(anuncio, false);
				log.info("[TESTE] Mudar status advertising id=" + id + " para status=" + status + " OK!");
			}
			return "page.jsp?id=1022"; 		//home advertiser
		}		
		log.info("[TESTE] Mudar status advertising id=" + id + " para status=" + status + " Erro nao atualizou!");
		return "page.jsp?id=1022"; 			//home advertiser
	}
		
	
	/**
	 * Pesquisa categoria e retorna uma lista das categorias
	 */
	@RequestMapping(value={"/advertiser/ajaxSearchCategory/listEntryAd"})
	public void searchCategoryListEntry(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
		
		log.info(request.getRequestURL());
		
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);	
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);		
		String name = request.getParameter("category").trim();
		
		if(!name.isEmpty()){
			while(name.contains("  ")){
				name = name.replaceAll("  ", " ");
			}		
			String[] array = name.split(" ");
			
			String SQLCategory = "select t1.id_category as id_category, t1.name_category as name_category, "
					+ "t1.date_created as date_created, "
					+ "(select count(t2.id_collection) from dbcollection t2 where t1.id_category = t2.fk_category_id) as total1, "
					+ "(select count(t3.id_interest) from dbinterest t3 where t1.id_category = t3.fk_category_id) as total2, "
					+ "case when t1.name_category ilike '" +name+ "%' then 1 "
					+ "     when t1.name_category ilike '% " +name+ "%' then 2 ";
					if(array.length >= 2){
						SQLCategory += " when (t1.name_category ilike '" +array[0]+ "%' or t1.name_category ilike '% " +array[0]+ "%') ";
						for(int i = 1; i < array.length; i++){
							SQLCategory +=  "and (t1.name_category ilike '" +array[i]+ "%' or t1.name_category ilike '% " +array[i]+ "%') ";
						}
						SQLCategory += " then 3 ";
					}
					SQLCategory += " else 4 end as prioridade "
					+ "from dbcategory t1 "
					+ "where t1.name_category ilike '%" +name+ "%' ";
					if(array.length >= 2){
						SQLCategory += " or (t1.name_category ilike '" +array[0]+ "%' or t1.name_category ilike '% " +array[0]+ "%') ";
						for(int i = 1; i < array.length; i++){
							SQLCategory +=  "and (t1.name_category ilike '" +array[i]+ "%' or t1.name_category ilike '% " +array[i]+ "%') ";
						}
					}
					SQLCategory +=  "order by prioridade, t1.name_category limit 5;";
			String[][] aliasCat = { {"dbcategory", "t1"}, {"dbcollection", "t2"}, {"dbinterest", "t3"} };
			SQLCategory = CS.transformSQLReal(SQLCategory, aliasCat);
	
			LinkedHashMap<String,HashMap<String,String>> recordsCategory = db.query_records(SQLCategory);
			ArrayList<Category> lista = new ArrayList<Category>();
	
			for(String rec : recordsCategory.keySet()){
				String idCategory = recordsCategory.get(rec).get("id_category");
				String nameCategory = recordsCategory.get(rec).get("name_category");
				String data = recordsCategory.get(rec).get("date_created");
				String total1 = recordsCategory.get(rec).get("total1");
				String total2 = recordsCategory.get(rec).get("total2");
				Category categoria = new Category();
				categoria.setIdCategory(idCategory);
				categoria.setNameCategory(nameCategory);
				categoria.setDateCreated(data);
				categoria.setTotal(Integer.toString(Integer.parseInt(total1) + Integer.parseInt(total2)));
				lista.add(categoria);
			}
						
			String listEntry = cms.getPageListEntry("1028");		//List Advertiser - Choose Category Ad Entry				
			StringBuffer resultHTML = new StringBuffer();
						
			//linguagem
			final String LING = request.getLocale().getLanguage();
			
			//valida se achou a categoria
			if(name.length() >= 3 && lista.isEmpty()){
				String s = listEntry;
				s = s.replaceAll("@@@id_category@@@", "");
				s = s.replaceAll("@@@name_category@@@", name);
				s = s.replaceAll("Created:", "");
				s = s.replaceAll("Criado:", "");
				s = s.replaceAll("@@@date_created@@@", "");
				s = s.replaceAll("Total collectors:", "");
				s = s.replaceAll("Total colecionadores:", "");
				s = s.replaceAll("@@@total@@@", "");
				s = s.replaceAll("@@@add@@@", "");						//valor botao oculo
				s = s.replaceAll("@@@hidden@@@", "hidden");				//botao oculto
				s = s.replaceAll("@@@info@@@", LING.equals("pt") ? "<br>Categoria não existe!" : "<br>Category does not exist!"); //info2 categoria nao existe
				s = s.replaceAll("@@@action@@@", "");					//funcao javascript
				resultHTML.append(s);
			}
			
			for(int i = 0; i < lista.size(); i++){
				String dataFormatada = "";
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date data = format.parse(lista.get(i).getDateCreated());
					dataFormatada = new SimpleDateFormat("dd-MM-yyyy").format(data);
				} catch (ParseException e) {
					log.warn("ERRO PARSE DATA/n" + e);
				}
				
				String s = listEntry;
				s = s.replaceAll("@@@id_category@@@", lista.get(i).getIdCategory());
				s = s.replaceAll("@@@name_category@@@", lista.get(i).getNameCategory());			
				s = s.replaceAll("@@@date_created@@@", dataFormatada);
				s = s.replaceAll("@@@total@@@", lista.get(i).getTotal());
				s = s.replaceAll("@@@action@@@", "addCategory('"+lista.get(i).getIdCategory()+"', '"+lista.get(i).getNameCategory()+"');");
				s = s.replaceAll("@@@info@@@", "");				
				//adicionar categoria para o anuncio
				s = s.replaceAll("@@@add@@@", LING.equals("pt") ? "Adicionar" : "Add");
				s = s.replaceAll("@@@hidden@@@", "button");				//botao add
					
				resultHTML.append(s);
			}
			
			response.setContentType("text/html");
			response.getWriter().write(resultHTML.toString());
		}else{
			response.setContentType("text/html");
			response.getWriter().write("");
		}
	}
	
	
	/**
	 * Metodo retorna uma String com uma lista em Html com valores default quando nao existir dados de eventos para mostrar
	 */
	public static StringBuilder getListDefaultEventsEntry(HttpServletRequest request){
		
		StringBuilder resultHTML = new StringBuilder("");		
		Event evento = new Event();
		evento.setIdEvent("");
		evento.setNameEvent("There are no events to show!");
		evento.setType("");
		evento.setPathPhoto("image_default.png");
		evento.setLocal("");
		evento.setDateEvent("");
		evento.setHour("");
	
		//Cms config para pages
		CmsConfigILiketo cms = new CmsConfigILiketo(request, null);

		//set objeto request para parse
		ModelILiketo model = new ModelILiketo(request, null);
		model.addAttribute("evento", evento);
		
		//retorna uma lista com parse ok do objeto no request
		resultHTML.append(cms.getPageBinding("1033"));		//list event - slide animate entry (There are no events)	
		return resultHTML;
	}
	
	/**
	 * Metodo retorna uma String com uma lista em Html com valores default quando nao existir dados de anuncios para mostrar
	 */
	public static StringBuilder getListDefaultAdsEntry(HttpServletRequest request){
		
		StringBuilder resultHTML = new StringBuilder("");		
		Advertising anuncio = new Advertising();
		anuncio.setIdAdvertising("");
		anuncio.setTitle("There are no ads to show!");
		anuncio.setDescription("");
		anuncio.setPathAd("image_default.png");
		anuncio.setLinkAd("");
	
		//Cms config para pages
		CmsConfigILiketo cms = new CmsConfigILiketo(request, null);

		//set objeto request para parse
		ModelILiketo model = new ModelILiketo(request, null);
		model.addAttribute("advertising", anuncio);
		
		//retorna uma lista com parse ok do objeto no request
		resultHTML.append(cms.getPageBinding("910"));		//list announce - slide animate entry		
		return resultHTML;
	}
	
	/**
	 * Metodo retorna uma String com uma lista em Html com todos anuncios globais
	 */
	public static StringBuilder getListGlobalAdsEntry(HttpServletRequest request){
		StringBuilder resultHTML = new StringBuilder("");
		
		//lista de todos anuncios globais
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLAdv = "select t1.id_advertising as id_advertising, t1.title_ad as title, t1.description_ad as description, "
				+ " t1.path_ad as path_ad, t1.link_ad as link_ad "
				+ " from dbadvertising t1 "
				+ " where t1.type = 'Global ad' and t1.status = 'Active' "
				+ " order by random() limit 10;";
		
		String[][] aliasAdv = { {"dbadvertising", "t1"} };
		SQLAdv = CS.transformSQLReal(SQLAdv, aliasAdv);
		
		LinkedHashMap<String,HashMap<String,String>> recordsAdv  = db.query_records(SQLAdv);
		ArrayList<Advertising> listaAnuncios = new ArrayList<Advertising>();
		
		for(String rec : recordsAdv.keySet()){
			Advertising anuncio = new Advertising();
			anuncio.setIdAdvertising(recordsAdv.get(rec).get("id_advertising"));
			anuncio.setTitle(recordsAdv.get(rec).get("title"));
			anuncio.setDescription(recordsAdv.get(rec).get("description"));
			anuncio.setPathAd(recordsAdv.get(rec).get("path_ad"));
			anuncio.setLinkAd(recordsAdv.get(rec).get("link_ad"));
			listaAnuncios.add(anuncio);
		}
		
		if(!listaAnuncios.isEmpty()){
			//Cms config para pages
			CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
			String listEntry = cms.getPageListEntry("1017");			//list advertiser - slide animate entry
			
			//lista objeto, lista entrada para cada tipo de objeto
			HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();		
			mapModelListEntry.put(Advertising.class, listEntry);
			
			//result parse objetos na lista de entrada
			resultHTML = cms.parseBindingModelBean(listaAnuncios, mapModelListEntry);	
		}
		
		if(resultHTML.toString().isEmpty()){
			resultHTML = getListDefaultAdsEntry(request);			//lista default quando nao existir anuncios para mostrar
		}		
		return resultHTML;
	}
	
	/**
	 * Metodo retorna uma String com uma lista em Html com todos anuncios direcionados para um grupo 
	 * ou todos os grupos que o membro participa.
	 * @return lista Html de entrada para parse na view jsp ${listDirectedAds}
	 */
	public static StringBuilder getListDirectedAdsEntry(HttpServletRequest request){
		StringBuilder resultHTML = new StringBuilder("");
		
		//verifica lista de anuncios direcionados somente para o grupo visualizado
		//lista eh pendurada no request pelos metodos de redirecionamento dos grupos
		if(request.getAttribute("listDirectedAds") != null){			
			resultHTML = (StringBuilder) request.getAttribute("listDirectedAds");
			if(resultHTML.toString().isEmpty()){
				resultHTML = getListDefaultAdsEntry(request);
			}
			return resultHTML;
		}
		
		//lista de anuncios direcionados para todos os grupos que o membro participa
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String myUserid = (String) request.getSession().getAttribute("userid");
		CollectionDAO collectionDAO = new CollectionDAO(db, null);
		InterestDAO interestDAO = new InterestDAO(db, null);
		
		List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
		String condicao = "";
		if(!listCollection.isEmpty()){
			condicao += " t1.fk_category_id = '" +listCollection.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listCollection.size(); i++){
				condicao += " or t1.fk_category_id = '" +listCollection.get(i).getIdCategory()+ "' ";
			}
		}
		if(!listInterest.isEmpty()){
			condicao += (condicao.equals("") ? "" : " or ") + " t1.fk_category_id = '" +listInterest.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listInterest.size(); i++){
				condicao += " or t1.fk_category_id = '" +listInterest.get(i).getIdCategory()+ "' ";
			}
		}
		//valida se existe algum grupo que o membro participa
		if(!condicao.isEmpty()){
			condicao = " and (" + condicao + ") ";	//select todos advertising que pertence aos grupos do membro
		
			String SQLAdv = "select t1.id_advertising as id_advertising, t1.title_ad as title, t1.description_ad as description, "
					+ " t1.path_ad as path_ad, t1.link_ad as link_ad "
					+ " from dbadvertising t1 "
					+ " where t1.type = 'Directed ad' and t1.status = 'Active' " + condicao
					+ " order by random() limit 10;";
			
			String[][] aliasAdv = { {"dbadvertising", "t1"} };
			SQLAdv = CS.transformSQLReal(SQLAdv, aliasAdv);
			//log.info("SQL condicao directed ads = " + SQLAdv);
			
			LinkedHashMap<String,HashMap<String,String>> recordsAdv  = db.query_records(SQLAdv);
			ArrayList<Advertising> listaAnuncios = new ArrayList<Advertising>();
			
			for(String rec : recordsAdv.keySet()){
				Advertising anuncio = new Advertising();
				anuncio.setIdAdvertising(recordsAdv.get(rec).get("id_advertising"));
				anuncio.setTitle(recordsAdv.get(rec).get("title"));
				anuncio.setDescription(recordsAdv.get(rec).get("description"));
				anuncio.setPathAd(recordsAdv.get(rec).get("path_ad"));
				anuncio.setLinkAd(recordsAdv.get(rec).get("link_ad"));
				listaAnuncios.add(anuncio);
			}
			
			if(!listaAnuncios.isEmpty()){
				//Cms config para pages
				CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
				String listEntry = cms.getPageListEntry("910");					//list announce - slide animate entry
				
				//lista objeto, lista entrada para cada tipo de objeto
				HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();
				mapModelListEntry.put(Advertising.class, listEntry);
				
				//result parse objetos na lista de entrada
				resultHTML = cms.parseBindingModelBean(listaAnuncios, mapModelListEntry);	
			}
		}
		
		if(resultHTML.toString().isEmpty()){
			resultHTML = getListDefaultAdsEntry(request);	//lista default quando nao existir anuncios para mostrar
		}
		return resultHTML;
	}
	
	/**
	 * Metodo retorna uma String com a lista html de entrada dos anuncios para um grupo
	 * @return lista HTML Entry
	 */
	public static StringBuilder getListDirectedAdsOneGroupEntry(String idCat, HttpServletRequest request){
		
		//busca lista de anuncios direcionados somente para o grupo visualizado
		log.info("Metodo getListDirectedAdsOneGroupEntry - [email: " + request.getSession().getAttribute("email") + "]");		
		
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQLAdv = "select t1.id_advertising as id_advertising, t1.title_ad as title, t1.description_ad as description, "
				+ " t1.path_ad as path_ad, t1.link_ad as link_ad "
				+ " from dbadvertising t1 "
				+ " where t1.type = 'Directed ad' and t1.fk_category_id = '" +idCat+ "' and t1.status = 'Active' "
				+ " order by random() limit 10;";
		
		String[][] aliasAdv = { {"dbadvertising", "t1"} };
		SQLAdv = CS.transformSQLReal(SQLAdv, aliasAdv);

		LinkedHashMap<String,HashMap<String,String>> recordsAdv  = db.query_records(SQLAdv);
		ArrayList<Advertising> listaAnuncios = new ArrayList<Advertising>();
		
		for(String rec : recordsAdv.keySet()){
			Advertising anuncio = new Advertising();
			anuncio.setIdAdvertising(recordsAdv.get(rec).get("id_advertising"));
			anuncio.setTitle(recordsAdv.get(rec).get("title"));
			anuncio.setDescription(recordsAdv.get(rec).get("description"));
			anuncio.setPathAd(recordsAdv.get(rec).get("path_ad"));
			anuncio.setLinkAd(recordsAdv.get(rec).get("link_ad"));
			listaAnuncios.add(anuncio);
		}
		
		StringBuilder resultHTML = new StringBuilder("");
		if(!listaAnuncios.isEmpty()){
			//Cms config para pages
			CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
			String listEntry = cms.getPageListEntry("910");					//list announce - slide animate entry
			
			//lista objeto, lista entrada para cada tipo de objeto
			HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();		
			mapModelListEntry.put(Advertising.class, listEntry);
			
			//result parse objetos na lista de entrada
			resultHTML = cms.parseBindingModelBean(listaAnuncios, mapModelListEntry);		
		}
		
		return resultHTML;
	}

	/**
	 * Metodo retorna uma String com uma lista em Html com todos eventos direcionados para um grupo 
	 * ou todos os grupos que o membro participa.
	 * @return lista Html de entrada para parse na view jsp ${listDirectedEvents}
	 */
	public static StringBuilder getListDirectedEventsEntry(HttpServletRequest request) {
		StringBuilder resultHTML = new StringBuilder("");
		
		//verifica lista de eventos direcionados somente para o grupo visualizado
		//lista eh pendurada no request pelos metodos de redirecionamento dos grupos
		if(request.getAttribute("listDirectedEvents") != null){			
			resultHTML = (StringBuilder) request.getAttribute("listDirectedEvents");
			if(resultHTML.toString().isEmpty()){
				resultHTML = getListDefaultEventsEntry(request);
			}
			return resultHTML;
		}
		
		//lista de anuncios direcionados para todos os grupos que o membro participa
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String myUserid = (String) request.getSession().getAttribute("userid");
		CollectionDAO collectionDAO = new CollectionDAO(db, null);
		InterestDAO interestDAO = new InterestDAO(db, null);
		
		List<Collection> listCollection = collectionDAO.listCollectionByUser(myUserid);
		List<Interest> listInterest = interestDAO.listInterestByUser(myUserid);
		String condicao = "";
		if(!listCollection.isEmpty()){
			condicao += " t1.fk_category_id = '" +listCollection.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listCollection.size(); i++){
				condicao += " or t1.fk_category_id = '" +listCollection.get(i).getIdCategory()+ "' ";
			}
		}
		if(!listInterest.isEmpty()){
			condicao += (condicao.equals("") ? "" : " or ") + " t1.fk_category_id = '" +listInterest.get(0).getIdCategory()+ "' ";
			for(int i = 1; i < listInterest.size(); i++){
				condicao += " or t1.fk_category_id = '" +listInterest.get(i).getIdCategory()+ "' ";
			}
		}
		//valida se existe algum grupo que o membro participa
		if(!condicao.isEmpty()){
			condicao = " (" + condicao + ") ";	//select todos eventos que pertence aos grupos do membro
		
			String SQL = "select t1.id_event as id_event, t1.name_event as name_event, t1.type_event as type_event, "
					+ " t1.path_photo_event as path_photo, t1.local_event as local_event, t1.date_event as date_event, t1.hour_event as hour_event "
					+ " from dbevent t1 "
					+ " where " + condicao
					+ " order by random() limit 10;";
			
			String[][] alias = { {"dbevent", "t1"} };
			SQL = CS.transformSQLReal(SQL, alias);
			
			LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQL);
			ArrayList<Event> listaEventos = new ArrayList<Event>();
			
			for(String rec : records.keySet()){
				Event evento = new Event();
				evento.setIdEvent(records.get(rec).get("id_event"));
				evento.setNameEvent(records.get(rec).get("name_event"));
				evento.setType(records.get(rec).get("type_event"));
				evento.setPathPhoto(records.get(rec).get("path_photo"));
				evento.setLocal(records.get(rec).get("local_event"));
				evento.setDateEvent(records.get(rec).get("date_event"));
				evento.setHour(records.get(rec).get("hour_event"));
				listaEventos.add(evento);
			}
			
			if(!listaEventos.isEmpty()){
				
				//importante, retira dados de eventos no request para evitar conflitos de parse	da lista dos eventos laterais			
				ModelILiketo model = new ModelILiketo(request, null);
				Event eventoParse = (Event) model.get("event");
				if(eventoParse != null){
					model.removeAttibute("event");
				}
				
				//Cms config para pages
				CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
				String listEntry = cms.getPageListEntry("909");					//list event - slide animate entry
				
				//put no model o evento novamente
				if(eventoParse != null){
					model.addAttribute("event", eventoParse);
				}
				
				//lista objeto, lista entrada para cada tipo de objeto
				HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();		
				mapModelListEntry.put(Event.class, listEntry);
				
				//result parse objetos na lista de entrada
				resultHTML = cms.parseBindingModelBean(listaEventos, mapModelListEntry);	
			}
		}
		
		if(resultHTML.toString().isEmpty()){
			resultHTML = getListDefaultEventsEntry(request);	//lista default quando nao existir eventos para mostrar
		}
		return resultHTML;
	}
	
	/**
	 * Metodo retorna uma String com a lista html de entrada dos eventos para um grupo
	 * @return lista HTML Entry
	 */
	public static StringBuilder getListDirectedEventsOneGroupEntry(String idCat, HttpServletRequest request){
		
		//busca lista de anuncios direcionados somente para o grupo visualizado
		log.info("Metodo getListDirectedEventsOneGroupEntry - [email: " + request.getSession().getAttribute("email") + "]");		
		
		DB db = (DB)request.getAttribute(Str.CONNECTION_DB);
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String SQL = "select t1.id_event as id_event, t1.name_event as name_event, t1.type_event as type_event, "
				+ " t1.path_photo_event as path_photo, t1.local_event as local_event, t1.date_event as date_event, t1.hour_event as hour_event "
				+ " from dbevent t1 "
				+ " where t1.fk_category_id = '" +idCat+ "' "
				+ " order by random() limit 10;";
		
		String[][] alias = { {"dbevent", "t1"} };
		SQL = CS.transformSQLReal(SQL, alias);

		LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQL);
		ArrayList<Event> listaEventos = new ArrayList<Event>();
		
		for(String rec : records.keySet()){
			Event evento = new Event();
			evento.setIdEvent(records.get(rec).get("id_event"));
			evento.setNameEvent(records.get(rec).get("name_event"));
			evento.setType(records.get(rec).get("type_event"));
			evento.setPathPhoto(records.get(rec).get("path_photo"));
			evento.setLocal(records.get(rec).get("local_event"));
			evento.setDateEvent(records.get(rec).get("date_event"));
			evento.setHour(records.get(rec).get("hour_event"));
			listaEventos.add(evento);
		}
		
		StringBuilder resultHTML = new StringBuilder("");
		if(!listaEventos.isEmpty()){
			//Cms config para pages
			CmsConfigILiketo cms = new CmsConfigILiketo(request, null);
			String listEntry = cms.getPageListEntry("909");					//list event - slide animate entry
			
			//lista objeto, lista entrada para cada tipo de objeto
			HashMap<Class, String> mapModelListEntry = new HashMap<Class, String>();
			mapModelListEntry.put(Event.class, listEntry);
			
			//result parse objetos na lista de entrada
			resultHTML = cms.parseBindingModelBean(listaEventos, mapModelListEntry);
		}
		
		return resultHTML;
	}
	
}
