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
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		log.info(request.getRequestURL() + " - Home <email: " + request.getSession().getAttribute("email") + ">");

		return "page.jsp?id=1022"; 	//Home advertiser
	}
	
	@RequestMapping(value={"/advertiser/directedAds/register"})
	public String announceStoreChoose(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		//remove atributos da sessao
		request.getSession().removeAttribute("anuncio");
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao adicionar imagem propaganda. Tela formulario registrar anuncio");
		}
		
		return "page.jsp?id=1024"; 	//page form register directed ads
	}
	
	@RequestMapping(value={"/advertiser/directedAds/payment"})
	public String announceStorePayment(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
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
				"<email: " + request.getSession().getAttribute("email") + ">");
		
		//set anuncio na session
		anuncio.setId(idCreated);
		anuncio.setIdAdvertising(idCreated);
		session.setAttribute("anuncio", anuncio);
		
		//model view jsp para binding do bean
		model.addAttribute("anuncio", anuncio);	
		
		return "page.jsp?id=1026"; 			//page form payment with paypal
	}
	
	@RequestMapping(value={"/advertiser/paymentCompleted"})
	public String paymentCompleted(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		
		return "page.jsp?id=1027"; 			//page pagamento completo
	}
	
	@RequestMapping(value={"/advertiser/register/testStatus"})
	public String testCompleted(HttpServletRequest request, HttpServletResponse response){
		
		log.info(request.getRequestURL());
		log.info("<TESTE> Mudar status teste anuncio");
		
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		if(id != null && !id.isEmpty() && status != null && !status.isEmpty()){
			//mudar status advertising
			DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
			AdvertisingDAO advDAO = new AdvertisingDAO(db, null);
			Advertising anuncio = (Advertising) advDAO.readById(id, Advertising.class);
			if(anuncio != null){
				anuncio.setStatus(status);
				advDAO.update(anuncio, false);
				log.info("<TESTE> Mudar status advertising id=" + id + " para status=" + status + " OK!");
			}
			return "page.jsp?id=1022"; 		//home advertiser
		}		
		log.info("<TESTE> Mudar status advertising id=" + id + " para status=" + status + " Erro nao atualizou!");
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
}
