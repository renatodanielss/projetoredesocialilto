package com.iliketo.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import HardCore.DB;

import com.iliketo.dao.MemberDAO;
import com.iliketo.dao.NotificationDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Collection;
import com.iliketo.model.Comment;
import com.iliketo.model.ContentILiketo;
import com.iliketo.model.Event;
import com.iliketo.model.Item;
import com.iliketo.model.Likes;
import com.iliketo.model.Member;
import com.iliketo.model.MessageInbox;
import com.iliketo.model.Notification;
import com.iliketo.model.Topic;
import com.iliketo.model.Video;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.ColumnsSingleton;
import com.iliketo.util.Str;

/**
 * Classe de servicos, contem as regras de negocio para criar notificacao, verificar novas e listar notificacoes
 * @author OSVALDIMAR
 *
 */
public class NotificationService {

	static final Logger log = Logger.getLogger(NotificationService.class);
	
	/**
	 * Metodo cria uma nova notificacao no grupo/categoria
	 * @param db
	 * @param idCategory
	 * @param contentType
	 * @param idContent
	 * @param postType
	 * @param idMember
	 */
	public static void createNotification(DB db, String idCategory, String contentType, String idContent, String postType, String idMember){
		
		NotificationDAO dao = new NotificationDAO(db, null);
		Notification notific = new Notification();
		notific.setIdCategory(idCategory);
		notific.setContentType(contentType);
		notific.setIdContent(idContent);
		notific.setIdMember(idMember);
		notific.setPostType(postType);
		
		String idCreated = dao.create(notific);
		log.info("Log - Nova notificacao gerada >> id notific: " + idCreated + " / id publicacao: " + idContent + " - tipo post: " + contentType);
		
	}
	
	/**
	 * Metodo cria notificacao de aviso de uma hora antes do leilao iniciar.
	 */
	public static void createNotificationAuctionOneHour(DB db, String idCategory, String contentType, String idContent, String postType, String idMember, String dateInitial){
		
		NotificationDAO dao = new NotificationDAO(db, null);
		Notification notific = new Notification();
		notific.setIdCategory(idCategory);
		notific.setContentType(contentType);
		//notific.setIdContent(idContent); //nao mostrar notific
		notific.setIdMember(idMember);
		notific.setPostType(postType);
		String idCreated = dao.create(notific);
		
		//programa notificacao para uma hora antes do leilao iniciar		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
			long miliInicial = format.parse(dateInitial).getTime();
			long miliHora = ( miliInicial - (1000*60*60) );
			String dataLeilaoHora = format.format(new java.util.Date(miliHora));
			
			String nameDatabase = dao.getNameDatabase();
			ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
			
			String dataid = CS.getDATA(db, nameDatabase);
			String col1 = CS.getCOL(db, nameDatabase, "fk_content_id");
			String col2 = CS.getCOL(db, nameDatabase, "date_created");
			String SQLupdate = col1 + "='" + idContent + "', "
							+ col2 + "='" + dataLeilaoHora  + "'";
			
			db.updateSet(dataid, "id", idCreated, SQLupdate);
			System.out.println("Log - Nova notificacao = id notific: " + idCreated + " / id publicacao: " + idContent + " - tipo conteudo: " + contentType);
			
		} catch (ParseException e) {
			System.out.println("Erro criacao de notificacao leilao uma hora antes.");
			e.printStackTrace();
		}		
	}

	/**
	 * Metodo retorna um inteiro com total de notificacoes nao lida a partir da ultima data de visto do membro(last_seen_date).
	 * @param db
	 * @param member
	 * @return
	 */
	public static int totalNotifications(HttpServletRequest request){
		System.out.println("\n***TOTAL NOTIFICACOES DESKTOP***");
		try {
			JSONArray array = newsAndTotalNotifications(request, false);
			if(array != null){
				JSONObject obj = (JSONObject) array.get(0);
				return (int) obj.get("total");
			}
		} catch (JSONException e) {	e.printStackTrace(); }
		return 0;
	}
	
	/**
	 * Metodo retorna um array json de novas notificacoes a partir da ultima data de visto do membro(last_seen_date).
	 * @param db
	 * @param member
	 * @return
	 */
	public static JSONArray newsNotificationsMobile(HttpServletRequest request){
		System.out.println("\n***TOTAL NOTIFICACOES MOBILE***");
		return newsAndTotalNotifications(request, true);
	}
	
	private static JSONArray newsAndTotalNotifications(HttpServletRequest request, boolean isMobile){
		
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);				//db		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);				//auxiliar para o db

		Member member = (Member) request.getSession().getAttribute("member");	//member session		
		String myUserid = member.getIdMember();								//id membro
		String lastSeenDate = member.getLastSeenDate();						//ultima data de visto da notificacao
		
		String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()); //data de visto agora
		if(lastSeenDate == null || lastSeenDate.equals("")){
			MemberDAO dao = new MemberDAO(db, null);
			member.setLastSeenDate(dateNow);								//seta data ultimo visto			
			dao.updateLastDateNotification(member);							//salva dados no bd
			request.getSession().setAttribute("member", member);			//atualiza dados membro na sessao
			lastSeenDate = member.getLastSeenDate();
		}
		
		//sql para recuperar as colecoes e interesses do membro e identificar as configuracoes de notificacao para receber da categoria
		String SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbcollection as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		String[][] aliasSQL = { {"dbcollection", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		//System.out.println("SQL Collection: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatCollection  = db.query_records(SQL);

		SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbinterest as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		aliasSQL = new String[][] { {"dbinterest", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		//System.out.println("SQL Interest: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatInterest  = db.query_records(SQL);
		
		
		//verifica notificacao de mensagem de leilao encerrado
		//verificaStatusLeilaoEncerrado(request, db, CS, member);
		
		//registros de configuracao colecao e interesse
		Object[] recordsArray = {recordsCatCollection, recordsCatInterest};		
		List<Collection> listConfigNotificationCategory = new ArrayList<Collection>();
		
		for(int j = 0; j < 2; j++){
			LinkedHashMap<String,HashMap<String,String>> records = (LinkedHashMap<String,HashMap<String,String>>) recordsArray[j];
			for(String rec : records.keySet()){
				Collection col = new Collection();
				col.setIdCategory(records.get(rec).get("fk_category_id"));
				col.setNameCategory(records.get(rec).get("name_category"));
				
				//valida colecao/interesse em uma categoria
				if(!col.getIdCategory().equals("")){
					col.setNotificCollection(records.get(rec).get("notific_collection"));
					col.setNotificItem(records.get(rec).get("notific_item"));
					col.setNotificVideo(records.get(rec).get("notific_video"));
					col.setNotificEvent(records.get(rec).get("notific_event"));
					col.setNotificAnnounce(records.get(rec).get("notific_announce"));
					col.setNotificTopic(records.get(rec).get("notific_topic"));
					col.setNotificComment(records.get(rec).get("notific_comment"));
					
					//verifica se existe colecoes/interesses em uma mesma categoria, prevalece qual tiver mais configuracoes de notificacao
					boolean other = false;
					for(int i = 0; i < listConfigNotificationCategory.size(); i++){
						Collection c  = listConfigNotificationCategory.get(i);
						if(c.getIdCategory().equals(col.getIdCategory())){
							int totalConfigC = 0;
							int totalConfigCol = 0;
							if(c.getNotificCollection().equals("Activate")) totalConfigC++;
							if(c.getNotificItem().equals("Activate")) totalConfigC++;
							if(c.getNotificVideo().equals("Activate")) totalConfigC++;
							if(c.getNotificEvent().equals("Activate")) totalConfigC++;
							if(c.getNotificAnnounce().equals("Activate")) totalConfigC++;
							if(c.getNotificTopic().equals("Activate")) totalConfigC++;
							if(c.getNotificComment().equals("Activate")) totalConfigC++;						
							if(col.getNotificCollection().equals("Activate")) totalConfigCol++;
							if(col.getNotificItem().equals("Activate")) totalConfigCol++;
							if(col.getNotificVideo().equals("Activate")) totalConfigCol++;
							if(col.getNotificEvent().equals("Activate")) totalConfigCol++;
							if(col.getNotificAnnounce().equals("Activate")) totalConfigCol++;
							if(col.getNotificTopic().equals("Activate")) totalConfigCol++;
							if(col.getNotificComment().equals("Activate")) totalConfigCol++;
							if(totalConfigC >= totalConfigCol){ other = true;  
							}else{ listConfigNotificationCategory.remove(i); }
							break;
						}
					}
					//adiciona na lista de configuracoes de notificacao para cada categoria
					if(!other){
						listConfigNotificationCategory.add(col);
						System.out.println("Id Categoria: " + col.getIdCategory() + " - Nome categoria: " + col.getNameCategory());
					}
				}			
			}
		}
		
		
		ArrayList<ContentILiketo> listModelNotification = new ArrayList<ContentILiketo>();
		LinkedHashMap<String,HashMap<String,String>> recordsCollections = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsItems = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsVideos = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsEvent = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsAnnounce = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsTopic = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsComment = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsMessages = new LinkedHashMap<String,HashMap<String,String>>();
		LinkedHashMap<String,HashMap<String,String>> recordsLikes = new LinkedHashMap<String,HashMap<String,String>>();
		int totalNews = 0;
		
		
		//verifica se a lista possui configuracao de notificacao (colecao/interesse) em alguma categoria
		if(!listConfigNotificationCategory.isEmpty()){
			
			//logs
			System.out.println("\nReceber notificacoes -  Data ultima notificacao vista: " + lastSeenDate + " ate a data agora: " + dateNow);
			String collectionCats = "";
			String itemCats = "";
			String videoCats = "";
			String eventCats = "";
			String announceCats = "";
			String topicCats = "";
			String commentCats = "";
			for(Collection c : listConfigNotificationCategory){				
				if(c.getNotificCollection().equals("Activate")) collectionCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificItem().equals("Activate")) itemCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificVideo().equals("Activate")) videoCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificEvent().equals("Activate")) eventCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificAnnounce().equals("Activate")) announceCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificTopic().equals("Activate")) topicCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				if(c.getNotificComment().equals("Activate")) commentCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
			}			
			
			String SQLCollection = "select t1.id_collection as id_collection, t1.name_collection as name_collection, t1.name_category as name_category, t1.date_updated as date_updated, "
					+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollection as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_collection where n.fk_user_id != '"+myUserid+"' and n.content_type = 'collection' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLItem = "select t1.id_item as id_item, t1.title_item as title_item, t1.date_updated as date_updated,  "
					+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollectionitem as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_item where n.fk_user_id != '"+myUserid+"' and n.content_type = 'item' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLVideo = "select t1.id_video as id_video, t1.title_video as title_video, t1.date_updated as date_updated,  "
					+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollectionvideo as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_video where n.fk_user_id != '"+myUserid+"' and n.content_type = 'video' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLEvent = "select t1.id_event as id_event, t1.name_event as name_event, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbevent as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_event where n.fk_user_id != '"+myUserid+"' and n.content_type = 'event' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLAnnounce = "select t1.id_announce as id_announce, t1.title as title, t1.date_updated as date_updated, t1.type_announce as type_announce,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbannounce as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_announce where n.fk_user_id != '"+myUserid+"' and n.content_type = 'announce' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLTopic = "select t1.id_topic as id_topic, t1.subject as subject, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbforumtopic as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_topic where n.fk_user_id != '"+myUserid+"' and n.content_type = 'topic' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLComment = "select t1.id_comment as id_comment, t1.text_comment as text_comment, t1.date_updated as date_updated, t1.fk_topic_id as fk_topic_id,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbforumcomment as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_comment where n.fk_user_id != '"+myUserid+"' and n.content_type = 'comment' "
					+ "and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			
			//SQL para recuperar o total de mensagens novas da caixa de entrada do membro
			SQL = "select t1.id_msg as id_msg, t1.subject as subject, t1.date_updated as date_updated, m.nickname as nickname "
					+ "from dbmessageinbox as t1 join dbmembers as m on t1.sender_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_msg "
					+ "where t1.receiver_user_id = '"+myUserid+"' and n.content_type = 'message' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			aliasSQL = new String[][] { {"dbmessageinbox", "t1"}, {"dbgroupnotification", "n"}, {"dbmembers", "m"} };
			SQL = CS.transformSQLReal(SQL, aliasSQL);
			System.out.println("SQL Mensagens: " + SQL);
			recordsMessages = db.query_records(SQL);
			
			//SQL para recuperar curtidas
			SQL = "select t1.id_like as id_like, t1.title_post as title_post, t1.date_updated as date_updated, t1.nickname as nickname "
					+ "from dblikes as t1 "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_like "
					+ "where t1.fk_owner_id = '"+myUserid+"' and n.content_type = 'curtir' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			aliasSQL = new String[][] { {"dblikes", "t1"}, {"dbgroupnotification", "n"} };
			SQL = CS.transformSQLReal(SQL, aliasSQL);
			System.out.println("SQL Curtidas: " + SQL);
			recordsLikes = db.query_records(SQL);
			
			
			//Se a lista possui uma categoria com a configuracao de publicacao ativada, adiciona id categoria no sql para cada tipo de conteudo ativado.
			for(Collection c : listConfigNotificationCategory){
				String id = c.getIdCategory();				
				if(c.getNotificCollection().equals("Activate")){
					if(SQLCollection.contains("fk_category_id")){
						SQLCollection += " or n.fk_category_id='"+id+"'";
					}else{ 
						SQLCollection += " and (n.fk_category_id='"+id+"'";
					}
				}
				if(c.getNotificItem().equals("Activate")){
					if(SQLItem.contains("fk_category_id")){
						SQLItem += " or n.fk_category_id='"+id+"'";
					}else{
						SQLItem += " and (n.fk_category_id='"+id+"'";
					}					
				}
				if(c.getNotificVideo().equals("Activate")){
					if(SQLVideo.contains("fk_category_id")){
						SQLVideo += " or n.fk_category_id='"+id+"'";
					}else{
						SQLVideo += " and (n.fk_category_id='"+id+"'";
					}
				}
				if(c.getNotificEvent().equals("Activate")){
					if(SQLEvent.contains("fk_category_id")){
						SQLEvent += " or n.fk_category_id='"+id+"'";
					}else{
						SQLEvent += " and (n.fk_category_id='"+id+"'";
					}
				}
				if(c.getNotificAnnounce().equals("Activate")){
					if(SQLAnnounce.contains("fk_category_id")){
						SQLAnnounce += " or n.fk_category_id='"+id+"'";
					}else{
						SQLAnnounce += " and (n.fk_category_id='"+id+"'";
					}
				}
				if(c.getNotificTopic().equals("Activate")){
					if(SQLTopic.contains("fk_category_id")){
						SQLTopic += " or n.fk_category_id='"+id+"'";
					}else{
						SQLTopic += " and (n.fk_category_id='"+id+"'";
					}
				}
				if(c.getNotificComment().equals("Activate")){
					if(SQLComment.contains("fk_category_id")){
						SQLComment += " or n.fk_category_id='"+id+"'";
					}else{
						SQLComment += " and (n.fk_category_id='"+id+"'";
					}
				}
			}
			//pega o registro da data mais recente de cada pulicacao
			if(SQLCollection.contains("fk_category_id"))
				SQLCollection += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_collection = n.fk_content_id and n.content_type = 'collection');";
			if(SQLItem.contains("fk_category_id"))
				SQLItem += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_item = n.fk_content_id and n.content_type = 'item');";
			if(SQLVideo.contains("fk_category_id"))
				SQLVideo += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_video = n.fk_content_id and n.content_type = 'video');";
			if(SQLEvent.contains("fk_category_id"))
				SQLEvent += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_event = n.fk_content_id and n.content_type = 'event');";
			if(SQLAnnounce.contains("fk_category_id"))
				SQLAnnounce += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_announce = n.fk_content_id and n.content_type = 'announce');";
			if(SQLTopic.contains("fk_category_id"))
				SQLTopic += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_topic = n.fk_content_id and n.content_type = 'topic');";
			if(SQLComment.contains("fk_category_id"))
				SQLComment += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_comment = n.fk_content_id and n.content_type = 'comment');";
			
			
			//executa sql de registros de notificacoes para cada tipo de conteudo(publicacao)
			//valida se sql contem pelo menos um id_category de notificacao ativado para executar o sql
			
			String[][] alias;
			if(SQLCollection.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollection", "t1"} };		
				SQLCollection = CS.transformSQLReal(SQLCollection, alias);
				System.out.println("SQLCollection: " + SQLCollection);
				recordsCollections = db.query_records(SQLCollection);
			}
			if(SQLItem.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollectionitem", "t1"} };
				SQLItem = CS.transformSQLReal(SQLItem, alias);
				System.out.println("SQLItem: " + SQLItem);
				recordsItems = db.query_records(SQLItem);
			}
			if(SQLVideo.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollectionvideo", "t1"} };
				SQLVideo = CS.transformSQLReal(SQLVideo, alias);
				System.out.println("SQLVideo: " + SQLVideo);
				recordsVideos = db.query_records(SQLVideo); 
			}
			if(SQLEvent.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbevent", "t1"} };
				SQLEvent = CS.transformSQLReal(SQLEvent, alias);
				System.out.println("SQLEvent: " + SQLEvent);
				recordsEvent = db.query_records(SQLEvent); 
			}
			if(SQLAnnounce.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbannounce", "t1"} };
				SQLAnnounce = CS.transformSQLReal(SQLAnnounce, alias);
				System.out.println("SQLAnnounce: " + SQLAnnounce);
				recordsAnnounce = db.query_records(SQLAnnounce);
			}
			if(SQLTopic.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbforumtopic", "t1"} };
				SQLTopic = CS.transformSQLReal(SQLTopic, alias);
				System.out.println("SQLTopic: " + SQLTopic);
				recordsTopic = db.query_records(SQLTopic);
			}
			if(SQLComment.contains("fk_category_id")){
				alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbforumcomment", "t1"} };
				SQLComment = CS.transformSQLReal(SQLComment, alias);
				System.out.println("SQLComment: " + SQLComment);
				recordsComment = db.query_records(SQLComment);
			}			
			
			//set conteudo e add na listModelNotification			
			if(isMobile){
				//Mobile
				for(String rec : recordsCollections.keySet()){
					Collection col = new Collection();
					Member m = new Member();	
					col.setIdCollection(recordsCollections.get(rec).get("id_collection"));				
					col.setNameCollection(recordsCollections.get(rec).get("name_collection"));
					col.setNameCategory(recordsCollections.get(rec).get("name_category"));
					col.setDateUpdated(recordsCollections.get(rec).get("date_updated"));
					m.setNickname(recordsCollections.get(rec).get("nickname"));
					col.setMember(m);
					listModelNotification.add(col);
				}
				for(String rec : recordsItems.keySet()){			
					Item item = new Item();
					Member m = new Member();
					item.setIdItem(recordsItems.get(rec).get("id_item"));
					item.setTitle(recordsItems.get(rec).get("title_item"));
					item.setDateUpdated(recordsItems.get(rec).get("date_updated"));
					m.setNickname(recordsItems.get(rec).get("nickname"));
					item.setMember(m);
					listModelNotification.add(item);
				}
				for(String rec : recordsVideos.keySet()){			
					Video v = new Video();
					Member m = new Member();
					v.setIdVideo(recordsVideos.get(rec).get("id_video"));
					v.setTitle(recordsVideos.get(rec).get("title_video"));
					v.setDateUpdated(recordsVideos.get(rec).get("date_updated"));
					m.setNickname(recordsVideos.get(rec).get("nickname"));
					v.setMember(m);
					listModelNotification.add(v);
				}
				for(String rec : recordsEvent.keySet()){			
					Event e = new Event();
					Member m = new Member();
					e.setIdEvent(recordsEvent.get(rec).get("id_event"));
					e.setNameEvent(recordsEvent.get(rec).get("name_event"));
					e.setDateUpdated(recordsEvent.get(rec).get("date_updated"));
					m.setNickname(recordsEvent.get(rec).get("nickname"));
					e.setMember(m);
					listModelNotification.add(e);
				}
				for(String rec : recordsAnnounce.keySet()){			
					Announce a = new Announce();
					Member m = new Member();
					a.setIdAnnounce(recordsAnnounce.get(rec).get("id_announce"));
					a.setTitle(recordsAnnounce.get(rec).get("title"));
					a.setTypeAnnounce(recordsAnnounce.get(rec).get("type_announce"));
					a.setDateUpdated(recordsAnnounce.get(rec).get("date_updated"));
					m.setNickname(recordsAnnounce.get(rec).get("nickname"));
					a.setMember(m);
					listModelNotification.add(a);
				}
				for(String rec : recordsTopic.keySet()){			
					Topic t = new Topic();
					Member m = new Member();
					t.setIdTopic(recordsTopic.get(rec).get("id_topic"));
					t.setSubject(recordsTopic.get(rec).get("subject"));
					t.setDateUpdated(recordsTopic.get(rec).get("date_updated"));
					m.setNickname(recordsTopic.get(rec).get("nickname"));
					t.setMember(m);
					listModelNotification.add(t);
				}
				for(String rec : recordsComment.keySet()){			
					Comment c = new Comment();
					Member m = new Member();
					c.setIdComment(recordsComment.get(rec).get("id_comment"));
					c.setComment(recordsComment.get(rec).get("text_comment"));
					c.setIdTopic(recordsComment.get(rec).get("fk_topic_id"));
					c.setDateUpdated(recordsComment.get(rec).get("date_updated"));
					m.setNickname(recordsComment.get(rec).get("nickname"));
					c.setMember(m);
					listModelNotification.add(c);
				}
			}
			
			//total de notificacoes
			totalNews = recordsCollections.size() + recordsItems.size() + recordsVideos.size() + recordsEvent.size() + 
					recordsAnnounce.size() + recordsTopic.size() + recordsComment.size() + recordsMessages.size() + recordsLikes.size();			
		}		
		
		if(!isMobile){
			try {
				JSONArray array = new JSONArray();
				return ( array.put(new JSONObject().put("total", totalNews)) ); 	//retorna total notificacoes novas
			} catch (JSONException e) { e.printStackTrace(); }
			return null;	//nao ha notificacoes novas
		}else{
			//Mobile
			//notificacoes especificas da caixa de entrada
			if(!recordsMessages.isEmpty()){
				for(String rec : recordsMessages.keySet()){
					MessageInbox message = new MessageInbox();
					Member m = new Member();
					message.setIdMsg(recordsMessages.get(rec).get("id_msg"));
					message.setSubject(recordsMessages.get(rec).get("subject"));
					message.setDateUpdated(recordsMessages.get(rec).get("date_updated"));
					m.setNickname(recordsMessages.get(rec).get("nickname"));
					message.setMember(m);
					listModelNotification.add(message);
				}
			}
			//curtidas
			if(!recordsLikes.isEmpty()){
				for(String rec : recordsLikes.keySet()){
					Likes like = new Likes();
					like.setId(recordsMessages.get(rec).get("id_like"));
					like.setTitlePost(recordsMessages.get(rec).get("title_post"));
					like.setDateUpdated(recordsMessages.get(rec).get("date_updated"));
					like.setNickname(recordsMessages.get(rec).get("nickname"));
					listModelNotification.add(like);
				}
			}
			
			//linguagem
			final String LING = request.getLocale().getLanguage();
			
			//JSONArray armazena mensagens de notificacoes
			JSONArray array = new JSONArray();
			if(!listModelNotification.isEmpty()){				
				try{
					array.put(new JSONObject().put("total", Integer.toString(totalNews))); 		//primeiro json contem total de notificacoes
					array.put(new JSONObject().put("lastSeenDate", member.getLastSeenDate())); 	//segundo json contem data ultimo visto					
					Collections.sort(listModelNotification);
					for(int i = 0; i < listModelNotification.size(); i++){
						ContentILiketo bean = listModelNotification.get(i);
						if(bean instanceof Collection){
							Collection col = (Collection) bean;
							for(String rec : recordsCollections.keySet()){
								String id = recordsCollections.get(rec).get("id_collection");
								if(id.equals(col.getIdCollection())){
									String msg = "";
									if(recordsCollections.get(rec).get("post_type").equals(Str.JOINED)){
										msg = col.getMember().getNickname() + 
												(LING.equals("pt") ? " entrou para o grupo \"" : " has joined the group \"") + col.getNameCategory() + "\".";
									}else if(recordsCollections.get(rec).get("post_type").equals(Str.UPDATED)){
										msg = col.getMember().getNickname() + 
												(LING.equals("pt") ? " atualizou a sua cole��o \"" : " has updated his collection \"") + col.getNameCollection() + "\".";
									}
									JSONObject json = new JSONObject();
									json.put("msg", msg);
									array.put(json);
								}
							}					
						}
						if(bean instanceof Item){
							Item item = (Item) bean;
							for(String rec : recordsItems.keySet()){
								String id = recordsItems.get(rec).get("id_item");
								if(id.equals(item.getIdItem())){
									String msg = "";
									if(recordsItems.get(rec).get("post_type").equals(Str.INCLUDED)){
										msg = item.getMember().getNickname() + 
												(LING.equals("pt") ? " adicionou um novo item \"" : " has included a new item \"") + item.getTitle() + "\".";
									}else if(recordsItems.get(rec).get("post_type").equals(Str.UPDATED)){
										msg = item.getMember().getNickname() + 
												(LING.equals("pt") ? " atualizou o seu item \"" : " has updated his item \"") + item.getTitle() + "\".";
									}
									JSONObject json = new JSONObject();
									json.put("msg", msg);
									array.put(json);
								}
							}					
						}
						if(bean instanceof Video){
							Video video = (Video) bean;
							for(String rec : recordsVideos.keySet()){
								String id = recordsVideos.get(rec).get("id_video");
								if(id.equals(video.getIdVideo())){
									String msg = "";
									if(recordsVideos.get(rec).get("post_type").equals(Str.INCLUDED)){
										msg = video.getMember().getNickname() + 
												(LING.equals("pt") ? " adicionou um novo video \"" : " has included a new video \"") + video.getTitle() + "\".";
									}else if(recordsVideos.get(rec).get("post_type").equals(Str.UPDATED)){
										msg = video.getMember().getNickname() + 
												(LING.equals("pt") ? " atualizou o seu video \"" : " has updated his video \"") + video.getTitle() + "\".";
									}
									JSONObject json = new JSONObject();
									json.put("msg", msg);
									array.put(json);
								}
							}					
						}
						if(bean instanceof Event){
							Event event = (Event) bean;
							for(String rec : recordsEvent.keySet()){
								String id = recordsEvent.get(rec).get("id_event");
								if(id.equals(event.getIdEvent())){
									String msg = "";
									if(recordsEvent.get(rec).get("post_type").equals(Str.INCLUDED)){
										msg = event.getMember().getNickname() + 
												(LING.equals("pt") ? " criou o evento \"" + event.getNameEvent() + "\"." : " has created \"" + event.getNameEvent() + "\" event.");
									}else if(recordsEvent.get(rec).get("post_type").equals(Str.UPDATED)){
										msg = event.getMember().getNickname() + 
												(LING.equals("pt") ? " atualizou o evento \"" : " has updated his event \"") + event.getNameEvent() + "\".";
									}
									JSONObject json = new JSONObject();
									json.put("msg", msg);
									array.put(json);
								}
							}
						}
						if(bean instanceof Announce){
							Announce announce = (Announce) bean;
							for(String rec : recordsAnnounce.keySet()){
								String id = recordsAnnounce.get(rec).get("id_announce");
								if(id.equals(announce.getIdAnnounce())){
									String msg = "";
									if(recordsAnnounce.get(rec).get("post_type").equals(Str.INCLUDED)){
										if(announce.getTypeAnnounce().equals("Auction"))
											msg = announce.getMember().getNickname() + 
													(LING.equals("pt") ? " anunciou um item em leil�o \"" : " has announced a item in auction \"") + announce.getTitle() + "\".";
										else 
											msg = announce.getMember().getNickname() + 
													(LING.equals("pt") ? " anunciou o item \"" + announce.getTitle() + "\"." : " has announced the \"" + announce.getTitle() + "\" item.");
									}else if(recordsAnnounce.get(rec).get("post_type").equals(Str.AUCTION_HOUR)){
										msg = (LING.equals("pt") ? " Item em leil�o \""  + announce.getTitle() + "\", come�a em uma hora."
												: "Auction item \"" + announce.getTitle() + "\", starts in an hour.");
									}
									JSONObject json = new JSONObject();
									json.put("msg", msg);
									array.put(json);
								}
							}
						}
						if(bean instanceof Topic){
							Topic topic = (Topic) bean;
							String msg = topic.getMember().getNickname() + 
									(LING.equals("pt") ? " criou um novo t�pico no forum." : " has created a new topic in the forum.");
							JSONObject json = new JSONObject();
							json.put("msg", msg);
							array.put(json);
						}
						if(bean instanceof Comment){
							Comment comment = (Comment) bean;
							String msg = comment.getMember().getNickname() + 
									(LING.equals("pt") ? " respondeu no forum." : " has replied on forum.");
							JSONObject json = new JSONObject();
							json.put("msg", msg);
							array.put(json);
						}
						if(bean instanceof MessageInbox){
							MessageInbox message = (MessageInbox) bean;
							String msg = message.getMember().getNickname() + 
									(LING.equals("pt") ? " lhe enviou uma mensagem \"" : " sent you a message \"") + message.getSubject() + "\".";
							JSONObject json = new JSONObject();
							json.put("msg", msg);
							array.put(json);
						}
						if(bean instanceof Likes){
							Likes likes = (Likes) bean;
							String msg = likes.getNickname();
							switch (likes.getPostType().toCharArray()[0]){
								case 'C' : msg = (LING.equals("pt") ? " curtiu sua cole��o." : " has liked your collection.");
										break;
								case 'I' : msg = (LING.equals("pt") ? " curtiu seu item." : " has liked your item.");
										break;
								case 'V' : msg = (LING.equals("pt") ? " curtiu seu v�deo." : " has liked your video.");
										break;
								case 'E' : msg = (LING.equals("pt") ? " curtiu sua evento." : " has liked your event.");
										break;
								case 'M' : msg = (LING.equals("pt") ? " curtiu seu coment�rio." : " has liked your comment.");
										break;
							}
							//(LING.equals("pt") ? " curtiu sua publica��o \"" : " liked your post \"") + likes.getTitlePost() + "\".";
							JSONObject json = new JSONObject();
							json.put("msg", msg);
							array.put(json);
						}
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
				return array;
			}
			
			try {
				array.put(new JSONObject().put("total", Integer.toString(totalNews))); 		//primeiro json contem total de notificacoes
				array.put(new JSONObject().put("lastSeenDate", member.getLastSeenDate())); 	//segundo json contem data ultimo visto
			} catch (JSONException e) {
				e.printStackTrace();
			} 	
			return array;	//nao ha notificacoes novas	
		}		
		
	}
	
	/**
	 * Metodo retorna uma lista com historico de notificacao dos ultimos 10 dias ou todas notificacoes dependendo da pagina requisitada.
	 * @param cms
	 * @param member
	 * @param idPageListEntry
	 * @return
	 */
	public static String listHistoricNotification(CmsConfigILiketo cms, Member member, String idPageListEntry){
		
		System.out.println("\n***LISTA HISTORICO NOTIFICACOES***");
		
		DB db = (DB) cms.getMyrequest().getRequest().getAttribute(Str.CONNECTION_DB);	//db
		HttpSession session = cms.getMyrequest().getRequest().getSession();				//session
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);		
		String myUserid = (String) session.getAttribute("userid");
		
		
		String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()); //data de visto agora
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();		
		calendar.set(Calendar.DAY_OF_MONTH, -60);			//seta data 10 dias anterior
		Date date = calendar.getTime();					
		String dateDaysNotific = format.format(date);		//historico de notificacao a partir dos ultimos 10 dias
		
		
		//sql para recuperar as colecoes e interesses do membro e identificar as configuracoes de notificacao para receber da categoria
		String SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbcollection as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		String[][] aliasSQL = { {"dbcollection", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		//System.out.println("SQL Collection: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatCollection  = db.query_records(SQL);

		SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbinterest as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		aliasSQL = new String[][] { {"dbinterest", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		//System.out.println("SQL Interest: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatInterest  = db.query_records(SQL);
		
		
		//SQL para recuperar a lista de mensagens novas da caixa de entrada do membro
		SQL = "select t1.id_msg as id_msg, t1.subject as subject, t1.date_updated as date_updated, "
				+ "m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbmessageinbox as t1 join dbmembers as m on t1.sender_user_id = m.id_member "
				+ "join dbgroupnotification as n on n.fk_content_id = t1.id_msg "
				+ "where t1.receiver_user_id = '"+myUserid+"' and n.content_type = 'message' and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
		aliasSQL = new String[][] { {"dbmembers", "m"}, {"dbmessageinbox", "t1"}, {"dbgroupnotification", "n"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		System.out.println("SQL Mensagens: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsMessages = db.query_records(SQL);
		
		//SQL para recuperar curtidas
		SQL = "select t1.id_like as id_like, t1.post_type as post_type, t1.fk_post_id as fk_post_id, t1.fk_owner_id as fk_owner_id, "
				+ "t1.fk_user_id as fk_user_id, t1.nickname as nickname, "
				+ "t1.title_post as title_post, t1.date_updated as date_updated, "				
				+ "t1.path_photo as path_photo_member from dblikes as t1 "
				+ "join dbgroupnotification as n on n.fk_content_id = t1.id_like "
				+ "where t1.fk_owner_id = '"+myUserid+"' and n.content_type = 'curtir' and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
		
		aliasSQL = new String[][] { {"dblikes", "t1"}, {"dbgroupnotification", "n"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		System.out.println("SQL Curtidas: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsLikes = db.query_records(SQL);
		
				
		//registros de configuracao colecao e interesse
		Object[] recordsArray = {recordsCatCollection, recordsCatInterest};		
		List<Collection> listConfigNotificationCategory = new ArrayList<Collection>();
		
		for(int j = 0; j < 2; j++){
			LinkedHashMap<String,HashMap<String,String>> records = (LinkedHashMap<String,HashMap<String,String>>) recordsArray[j];
			for(String rec : records.keySet()){
				Collection col = new Collection();
				col.setIdCategory(records.get(rec).get("fk_category_id"));
				col.setNameCategory(records.get(rec).get("name_category"));
				
				//valida colecao/interesse em uma categoria
				if(!col.getIdCategory().equals("")){
					col.setNotificCollection(records.get(rec).get("notific_collection"));
					col.setNotificItem(records.get(rec).get("notific_item"));
					col.setNotificVideo(records.get(rec).get("notific_video"));
					col.setNotificEvent(records.get(rec).get("notific_event"));
					col.setNotificAnnounce(records.get(rec).get("notific_announce"));
					col.setNotificTopic(records.get(rec).get("notific_topic"));
					col.setNotificComment(records.get(rec).get("notific_comment"));
					
					//verifica se existe colecoes/interesses em uma mesma categoria, prevalece qual tiver mais configuracoes de notificacao
					boolean other = false;
					for(int i = 0; i < listConfigNotificationCategory.size(); i++){
						Collection c  = listConfigNotificationCategory.get(i);
						if(c.getIdCategory().equals(col.getIdCategory())){
							int totalConfigC = 0;
							int totalConfigCol = 0;
							if(c.getNotificCollection().equals("Activate")) totalConfigC++;
							if(c.getNotificItem().equals("Activate")) totalConfigC++;
							if(c.getNotificVideo().equals("Activate")) totalConfigC++;
							if(c.getNotificEvent().equals("Activate")) totalConfigC++;
							if(c.getNotificAnnounce().equals("Activate")) totalConfigC++;
							if(c.getNotificTopic().equals("Activate")) totalConfigC++;
							if(c.getNotificComment().equals("Activate")) totalConfigC++;						
							if(col.getNotificCollection().equals("Activate")) totalConfigCol++;
							if(col.getNotificItem().equals("Activate")) totalConfigCol++;
							if(col.getNotificVideo().equals("Activate")) totalConfigCol++;
							if(col.getNotificEvent().equals("Activate")) totalConfigCol++;
							if(col.getNotificAnnounce().equals("Activate")) totalConfigCol++;
							if(col.getNotificTopic().equals("Activate")) totalConfigCol++;
							if(col.getNotificComment().equals("Activate")) totalConfigCol++;
							if(totalConfigC >= totalConfigCol){ other = true;  
							}else{ listConfigNotificationCategory.remove(i); }
							break;
						}
					}
					//adiciona na lista de configuracoes de notificacao para cada categoria
					if(!other){
						listConfigNotificationCategory.add(col);
						System.out.println("Id Categoria: " + col.getIdCategory() + " - Nome categoria: " + col.getNameCategory());
					}
				}			
			}
		}
		
		StringBuffer div = new StringBuffer();		//div result html	
		
		//verifica se a lista possui configuracao de notificacao (colecao/interesse) em alguma categoria ou mensagens novas da caixa de entrada
		if(!listConfigNotificationCategory.isEmpty() || !recordsMessages.isEmpty() || !recordsLikes.isEmpty()){
			
			ArrayList<ContentILiketo> listModelNotification = new ArrayList<ContentILiketo>();
			LinkedHashMap<String,HashMap<String,String>> recordsCollections = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsItems = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsVideos = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsEvent = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsAnnounce = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsTopic = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsComment = new LinkedHashMap<String,HashMap<String,String>>();
			
			//notificacoes do grupo
			if(!listConfigNotificationCategory.isEmpty()){
				//logs
				System.out.println("\nReceber notificacoes -  Data notificacoes desde: " + dateDaysNotific + " ate a data agora: " + dateNow);
				String collectionCats = "";
				String itemCats = "";
				String videoCats = "";
				String eventCats = "";
				String announceCats = "";
				String topicCats = "";
				String commentCats = "";
				for(Collection c : listConfigNotificationCategory){				
					if(c.getNotificCollection().equals("Activate")) collectionCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificItem().equals("Activate")) itemCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificVideo().equals("Activate")) videoCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificEvent().equals("Activate")) eventCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificAnnounce().equals("Activate")) announceCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificTopic().equals("Activate")) topicCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
					if(c.getNotificComment().equals("Activate")) commentCats += " 'id=" +c.getIdCategory() + "-name=" + c.getNameCategory()+ "'";
				}
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de collection das categorias: " + collectionCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de item das categorias: " + itemCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de video das categorias: " + videoCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de event das categorias: " + eventCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de announce das categorias: " + announceCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de topic das categorias: " + topicCats);
				if(!collectionCats.equals("")) System.out.println("Receber notificacoes de comment das categorias: " + commentCats + "\n");
				
				
				String SQLCollection = "select t1.id_collection as id_collection, t1.name_collection as name_collection, t1.name_category as name_category, t1.date_updated as date_updated, "
						+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbcollection as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_collection where n.fk_user_id != '"+myUserid+"' and n.content_type = 'collection' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLItem = "select t1.id_item as id_item, t1.title_item as title_item, t1.date_updated as date_updated,  "
						+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbcollectionitem as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_item where n.fk_user_id != '"+myUserid+"' and n.content_type = 'item' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLVideo = "select t1.id_video as id_video, t1.title_video as title_video, t1.date_updated as date_updated,  "
						+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbcollectionvideo as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_video where n.fk_user_id != '"+myUserid+"' and n.content_type = 'video' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLEvent = "select t1.id_event as id_event, t1.name_event as name_event, t1.date_updated as date_updated,  "
						+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbevent as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_event where n.fk_user_id != '"+myUserid+"' and n.content_type = 'event' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLAnnounce = "select t1.id_announce as id_announce, t1.title as title, t1.date_updated as date_updated, t1.type_announce as type_announce,  "
						+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbannounce as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_announce where n.fk_user_id != '"+myUserid+"' and n.content_type = 'announce' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLTopic = "select t1.id_topic as id_topic, t1.subject as subject, t1.date_updated as date_updated, t1.fk_forum_id as fk_forum_id, "
						+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
						+ "from dbforumtopic as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_topic where n.fk_user_id != '"+myUserid+"' and n.content_type = 'topic' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				String SQLComment = "select t1.id_comment as id_comment, t1.text_comment as text_comment, t1.date_updated as date_updated, t1.fk_topic_id as fk_topic_id,  "
						+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type, t2.fk_forum_id as fk_forum_id "
						+ "from dbforumcomment as t1 join dbmembers as m on t1.fk_user_id = m.id_member join dbforumtopic as t2 on t2.id_topic = t1.fk_topic_id "
						+ "join dbgroupnotification as n on n.fk_content_id = t1.id_comment where n.fk_user_id != '"+myUserid+"' and n.content_type = 'comment' "
						+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
				
				
				
				//Se a lista possui uma categoria com a configuracao de publicacao ativada, adiciona id categoria no sql para cada tipo de conteudo ativado.
				for(Collection c : listConfigNotificationCategory){
					String id = c.getIdCategory();
					if(c.getNotificCollection().equals("Activate")){
						if(SQLCollection.contains("fk_category_id")){
							SQLCollection += " or n.fk_category_id='"+id+"'";
						}else{ 
							SQLCollection += " and (n.fk_category_id='"+id+"'";
						}
					}
					if(c.getNotificItem().equals("Activate")){
						if(SQLItem.contains("fk_category_id")){
							SQLItem += " or n.fk_category_id='"+id+"'";
						}else{
							SQLItem += " and (n.fk_category_id='"+id+"'";
						}					
					}
					if(c.getNotificVideo().equals("Activate")){
						if(SQLVideo.contains("fk_category_id")){
							SQLVideo += " or n.fk_category_id='"+id+"'";
						}else{
							SQLVideo += " and (n.fk_category_id='"+id+"'";
						}
					}
					if(c.getNotificEvent().equals("Activate")){
						if(SQLEvent.contains("fk_category_id")){
							SQLEvent += " or n.fk_category_id='"+id+"'";
						}else{
							SQLEvent += " and (n.fk_category_id='"+id+"'";
						}
					}
					if(c.getNotificAnnounce().equals("Activate")){
						if(SQLAnnounce.contains("fk_category_id")){
							SQLAnnounce += " or n.fk_category_id='"+id+"'";
						}else{
							SQLAnnounce += " and (n.fk_category_id='"+id+"'";
						}
					}
					if(c.getNotificTopic().equals("Activate")){
						if(SQLTopic.contains("fk_category_id")){
							SQLTopic += " or n.fk_category_id='"+id+"'";
						}else{
							SQLTopic += " and (n.fk_category_id='"+id+"'";
						}
					}
					if(c.getNotificComment().equals("Activate")){
						if(SQLComment.contains("fk_category_id")){
							SQLComment += " or n.fk_category_id='"+id+"'";
						}else{
							SQLComment += " and (n.fk_category_id='"+id+"'";
						}
					}
				}
				//pega o registro da data mais recente de cada pulicacao
				if(SQLCollection.contains("fk_category_id"))
					SQLCollection += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_collection = n.fk_content_id and n.content_type = 'collection');";
				if(SQLItem.contains("fk_category_id"))
					SQLItem += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_item = n.fk_content_id and n.content_type = 'item');";
				if(SQLVideo.contains("fk_category_id"))
					SQLVideo += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_video = n.fk_content_id and n.content_type = 'video');";
				if(SQLEvent.contains("fk_category_id"))
					SQLEvent += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_event = n.fk_content_id and n.content_type = 'event');";
				if(SQLAnnounce.contains("fk_category_id"))
					SQLAnnounce += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_announce = n.fk_content_id and n.content_type = 'announce');";
				if(SQLTopic.contains("fk_category_id"))
					SQLTopic += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_topic = n.fk_content_id and n.content_type = 'topic');";
				if(SQLComment.contains("fk_category_id"))
					SQLComment += ") and n.date_created = (select max(n.date_created) from dbgroupnotification n where t1.id_comment = n.fk_content_id and n.content_type = 'comment');";
				
				
				//executa sql de registros de notificacoes para cada tipo de conteudo(publicacao)
				//valida se sql contem pelo menos um id_category de notificacao ativado para executar o sql
	
				String[][] alias;
				if(SQLCollection.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollection", "t1"} };		
					SQLCollection = CS.transformSQLReal(SQLCollection, alias);
					System.out.println("SQLCollection: " + SQLCollection);
					recordsCollections = db.query_records(SQLCollection);
				}
				if(SQLItem.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollectionitem", "t1"} };
					SQLItem = CS.transformSQLReal(SQLItem, alias);
					System.out.println("SQLItem: " + SQLItem);
					recordsItems = db.query_records(SQLItem);
				}
				if(SQLVideo.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbcollectionvideo", "t1"} };
					SQLVideo = CS.transformSQLReal(SQLVideo, alias);
					System.out.println("SQLVideo: " + SQLVideo);
					recordsVideos = db.query_records(SQLVideo); 
				}
				if(SQLEvent.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbevent", "t1"} };
					SQLEvent = CS.transformSQLReal(SQLEvent, alias);
					System.out.println("SQLEvent: " + SQLEvent);
					recordsEvent = db.query_records(SQLEvent); 
				}
				if(SQLAnnounce.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbannounce", "t1"} };
					SQLAnnounce = CS.transformSQLReal(SQLAnnounce, alias);
					System.out.println("SQLAnnounce: " + SQLAnnounce);
					recordsAnnounce = db.query_records(SQLAnnounce);
				}
				if(SQLTopic.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbforumtopic", "t1"} };
					SQLTopic = CS.transformSQLReal(SQLTopic, alias);
					System.out.println("SQLTopic: " + SQLTopic);
					recordsTopic = db.query_records(SQLTopic);
				}
				if(SQLComment.contains("fk_category_id")){
					alias = new String[][] { {"dbmembers", "m"}, {"dbgroupnotification", "n"}, {"dbforumcomment", "t1"}, {"dbforumtopic", "t2"} };
					SQLComment = CS.transformSQLReal(SQLComment, alias);
					System.out.println("SQLComment: " + SQLComment);
					recordsComment = db.query_records(SQLComment);
				}
				
				
				//set conteudo e add na listModelNotification
				for(String rec : recordsCollections.keySet()){
					Collection col = new Collection();
					Member m = new Member();	
					col.setIdCollection(recordsCollections.get(rec).get("id_collection"));				
					col.setNameCollection(recordsCollections.get(rec).get("name_collection"));
					col.setNameCategory(recordsCollections.get(rec).get("name_category"));
					col.setDateUpdated(recordsCollections.get(rec).get("date_updated"));
					m.setIdMember(recordsCollections.get(rec).get("id_member"));
					m.setNickname(recordsCollections.get(rec).get("nickname"));
					m.setPathPhoto(recordsCollections.get(rec).get("path_photo_member"));
					col.setMember(m);
					listModelNotification.add(col);
				}
				for(String rec : recordsItems.keySet()){			
					Item item = new Item();
					Member m = new Member();
					item.setIdItem(recordsItems.get(rec).get("id_item"));
					item.setTitle(recordsItems.get(rec).get("title_item"));
					item.setDateUpdated(recordsItems.get(rec).get("date_updated"));
					m.setIdMember(recordsItems.get(rec).get("id_member"));
					m.setNickname(recordsItems.get(rec).get("nickname"));
					m.setPathPhoto(recordsItems.get(rec).get("path_photo_member"));
					item.setMember(m);
					listModelNotification.add(item);
				}
				for(String rec : recordsVideos.keySet()){			
					Video v = new Video();
					Member m = new Member();
					v.setIdVideo(recordsVideos.get(rec).get("id_video"));
					v.setTitle(recordsVideos.get(rec).get("title_video"));
					v.setDateUpdated(recordsVideos.get(rec).get("date_updated"));
					m.setIdMember(recordsVideos.get(rec).get("id_member"));
					m.setNickname(recordsVideos.get(rec).get("nickname"));
					m.setPathPhoto(recordsVideos.get(rec).get("path_photo_member"));
					v.setMember(m);
					listModelNotification.add(v);
				}
				for(String rec : recordsEvent.keySet()){			
					Event e = new Event();
					Member m = new Member();
					e.setIdEvent(recordsEvent.get(rec).get("id_event"));
					e.setNameEvent(recordsEvent.get(rec).get("name_event"));
					e.setDateUpdated(recordsEvent.get(rec).get("date_updated"));
					m.setNickname(recordsEvent.get(rec).get("nickname"));
					m.setPathPhoto(recordsEvent.get(rec).get("path_photo_member"));
					e.setMember(m);
					listModelNotification.add(e);
				}
				for(String rec : recordsAnnounce.keySet()){			
					Announce a = new Announce();
					Member m = new Member();
					a.setIdAnnounce(recordsAnnounce.get(rec).get("id_announce"));
					a.setTitle(recordsAnnounce.get(rec).get("title"));
					a.setTypeAnnounce(recordsAnnounce.get(rec).get("type_announce"));
					a.setDateUpdated(recordsAnnounce.get(rec).get("date_updated"));
					m.setNickname(recordsAnnounce.get(rec).get("nickname"));
					m.setPathPhoto(recordsAnnounce.get(rec).get("path_photo_member"));
					a.setMember(m);
					listModelNotification.add(a);
				}
				for(String rec : recordsTopic.keySet()){			
					Topic t = new Topic();
					Member m = new Member();
					t.setIdTopic(recordsTopic.get(rec).get("id_topic"));
					t.setSubject(recordsTopic.get(rec).get("subject"));
					t.setDateUpdated(recordsTopic.get(rec).get("date_updated"));
					t.setIdForum(recordsTopic.get(rec).get("fk_forum_id"));
					m.setNickname(recordsTopic.get(rec).get("nickname"));
					m.setPathPhoto(recordsTopic.get(rec).get("path_photo_member"));
					t.setMember(m);
					listModelNotification.add(t);
				}
				for(String rec : recordsComment.keySet()){			
					Comment c = new Comment();
					Member m = new Member();
					c.setIdComment(recordsComment.get(rec).get("id_comment"));
					c.setComment(recordsComment.get(rec).get("text_comment"));
					c.setDateUpdated(recordsComment.get(rec).get("date_updated"));
					c.setIdTopic(recordsComment.get(rec).get("fk_topic_id"));
					c.setIdForum(recordsComment.get(rec).get("fk_forum_id"));	//idQuestion = idForum
					m.setNickname(recordsComment.get(rec).get("nickname"));
					m.setPathPhoto(recordsComment.get(rec).get("path_photo_member"));
					c.setMember(m);
					listModelNotification.add(c);
				}
			}
		
			//notificacoes da caixa de entrada
			if(!recordsMessages.isEmpty()){
				for(String rec : recordsMessages.keySet()){
					MessageInbox message = new MessageInbox();
					Member m = new Member();
					message.setIdMsg(recordsMessages.get(rec).get("id_msg"));
					message.setSubject(recordsMessages.get(rec).get("subject"));
					message.setDateUpdated(recordsMessages.get(rec).get("date_updated"));
					m.setNickname(recordsMessages.get(rec).get("nickname"));
					m.setPathPhoto(recordsMessages.get(rec).get("path_photo_member"));
					message.setMember(m);
					listModelNotification.add(message);
				}
			}
			//curtidas
			if(!recordsLikes.isEmpty()){
				for(String rec : recordsLikes.keySet()){
					Likes like = new Likes();
					like.setIdLike(recordsLikes.get(rec).get("id_like"));
					like.setPostType(recordsLikes.get(rec).get("post_type"));
					like.setIdPost(recordsLikes.get(rec).get("fk_post_id"));
					like.setIdOwner(recordsLikes.get(rec).get("fk_owner_id"));
					like.setIdMember(recordsLikes.get(rec).get("fk_user_id"));
					like.setNickname(recordsLikes.get(rec).get("nickname"));
					like.setTitlePost(recordsLikes.get(rec).get("title_post"));
					like.setPathPhoto(recordsLikes.get(rec).get("path_photo_member"));
					like.setDateUpdated(recordsLikes.get(rec).get("date_updated"));
					listModelNotification.add(like);
				}
			}
			
			//verifica se tem alguma notificacao
			if(!listModelNotification.isEmpty()){
				//ordena lista de notificacoes das publicacoes(conteudo) por ordem updated
				Collections.sort(listModelNotification);
				
				String listEntryNotific = cms.getPageListEntry(idPageListEntry);	//page list notification (notificacao no template ou page more notifications)
				
				//linguagem
				final String LING = cms.getMyrequest().getLocale().getLanguage();
				
				int totalNotific = 5;								//mostrar ate 5 notificacoes no template
				if(idPageListEntry.equals("808")){
					totalNotific = listModelNotification.size();	//mostrar todas notificacao na page more notifications
				}
				System.out.println("\nTotal da lista de notificacoes: " +listModelNotification.size()+ "\n");
				for(int i = 0; i < totalNotific && i < listModelNotification.size(); i++){
					ContentILiketo bean = listModelNotification.get(i);
					if(bean instanceof Collection){
						Collection col = (Collection) bean;
						for(String rec : recordsCollections.keySet()){
							String id = recordsCollections.get(rec).get("id_collection");
							if(id.equals(col.getIdCollection())){
								String msg = "";
								if(recordsCollections.get(rec).get("post_type").equals(Str.JOINED)){
									msg = col.getMember().getNickname() + 
											(LING.equals("pt") ? " entrou para o grupo \"" : " has joined the group \"") + col.getNameCategory() + "\".";
								}else if(recordsCollections.get(rec).get("post_type").equals(Str.UPDATED)){
									msg = col.getMember().getNickname() + 
											(LING.equals("pt") ? " atualizou a sua cole��o \"" : " has updated his collection \"") + col.getNameCollection() + "\".";
								}
								String s = listEntryNotific;
								s = s.replaceAll("@@@message@@@", msg);									//mensagem post
								s = s.replaceAll("@@@pathPhoto@@@", col.getMember().getPathPhoto());	//foto membro
								s = s.replaceAll("@@@nickname@@@", col.getMember().getNickname());		//nickname
								s = s.replaceAll("@@@dateUpdated@@@", col.getDateUpdated());			//data publicacao
								s = s.replaceAll("@@@redirect@@@", "/ilt/collection/profile?id="+col.getIdCollection());
								div.append(s);
							}
						}					
					}
					if(bean instanceof Item){
						Item item = (Item) bean;
						for(String rec : recordsItems.keySet()){
							String id = recordsItems.get(rec).get("id_item");
							if(id.equals(item.getIdItem())){
								String msg = "";
								if(recordsItems.get(rec).get("post_type").equals(Str.INCLUDED)){
									msg = item.getMember().getNickname() + 
											(LING.equals("pt") ? " adicionou um novo item \"" : " has included a new item \"") + item.getTitle() + "\".";
								}else if(recordsItems.get(rec).get("post_type").equals(Str.UPDATED)){
									msg = item.getMember().getNickname() + 
											(LING.equals("pt") ? " atualizou o seu item \"" : " has updated his item \"") + item.getTitle() + "\".";
								}
								String s = listEntryNotific;
								s = s.replaceAll("@@@message@@@", msg);									//mensagem post
								s = s.replaceAll("@@@pathPhoto@@@", item.getMember().getPathPhoto());	//foto membro
								s = s.replaceAll("@@@nickname@@@", item.getMember().getNickname());		//nickname
								s = s.replaceAll("@@@dateUpdated@@@", item.getDateUpdated());			//data publicacao
								s = s.replaceAll("@@@redirect@@@", "/ilt/item/view?id="+item.getIdItem());
								div.append(s);
							}
						}					
					}
					if(bean instanceof Video){
						Video video = (Video) bean;
						for(String rec : recordsVideos.keySet()){
							String id = recordsVideos.get(rec).get("id_video");
							if(id.equals(video.getIdVideo())){
								String msg = "";
								if(recordsVideos.get(rec).get("post_type").equals(Str.INCLUDED)){
									msg = video.getMember().getNickname() + 
											(LING.equals("pt") ? " adicionou um novo video \"" : " has included a new video \"") + video.getTitle() + "\".";
								}else if(recordsVideos.get(rec).get("post_type").equals(Str.UPDATED)){
									msg = video.getMember().getNickname() + 
											(LING.equals("pt") ? " atualizou o seu video \"" : " has updated his video \"") + video.getTitle() + "\".";
								}
								String s = listEntryNotific;
								s = s.replaceAll("@@@message@@@", msg);									//mensagem post
								s = s.replaceAll("@@@pathPhoto@@@", video.getMember().getPathPhoto());	//foto membro
								s = s.replaceAll("@@@nickname@@@", video.getMember().getNickname());	//nickname
								s = s.replaceAll("@@@dateUpdated@@@", video.getDateUpdated());			//data publicacao
								s = s.replaceAll("@@@redirect@@@", "/ilt/video/view?id="+video.getIdVideo());
								div.append(s);
							}
						}					
					}
					if(bean instanceof Event){
						Event event = (Event) bean;
						for(String rec : recordsEvent.keySet()){
							String id = recordsEvent.get(rec).get("id_event");
							if(id.equals(event.getIdEvent())){
								String msg = "";
								if(recordsEvent.get(rec).get("post_type").equals(Str.INCLUDED)){
									msg = event.getMember().getNickname() + 
											(LING.equals("pt") ? " criou o evento \"" + event.getNameEvent() + "\"." : " has created \"" + event.getNameEvent() + "\" event.");
								}else if(recordsEvent.get(rec).get("post_type").equals(Str.UPDATED)){
									msg = event.getMember().getNickname() + 
											(LING.equals("pt") ? " atualizou o evento \"" : " has updated his event \"") + event.getNameEvent() + "\".";
								}
								String s = listEntryNotific;
								s = s.replaceAll("@@@message@@@", msg);									//mensagem post
								s = s.replaceAll("@@@pathPhoto@@@", event.getMember().getPathPhoto());	//foto membro
								s = s.replaceAll("@@@nickname@@@", event.getMember().getNickname());	//nickname
								s = s.replaceAll("@@@dateUpdated@@@", event.getDateUpdated());			//data publicacao
								s = s.replaceAll("@@@redirect@@@", "/ilt/event/view?id=" + event.getIdEvent());	//link da publicacao
								div.append(s);
							}
						}
					}
					if(bean instanceof Announce){
						Announce announce = (Announce) bean;
						for(String rec : recordsAnnounce.keySet()){
							String id = recordsAnnounce.get(rec).get("id_announce");
							if(id.equals(announce.getIdAnnounce())){
								String msg = "";
								if(recordsAnnounce.get(rec).get("post_type").equals(Str.INCLUDED)){
									if(announce.getTypeAnnounce().equals("Auction"))
										msg = announce.getMember().getNickname() + 
											(LING.equals("pt") ? " anunciou um item em leil�o \"" : " has announced a item in auction \"") + announce.getTitle() + "\".";
									else 
										msg = announce.getMember().getNickname() + 
											(LING.equals("pt") ? " anunciou o item \"" + announce.getTitle() + "\"." : " has announced the \"" + announce.getTitle() + "\" item.");
								}else if(recordsAnnounce.get(rec).get("post_type").equals(Str.AUCTION_HOUR)){
									msg = (LING.equals("pt") ? " Item em leil�o \""  + announce.getTitle() + "\", come�a em uma hora."
											: "Auction item \"" + announce.getTitle() + "\", starts in an hour.");
								}
								String s = listEntryNotific;
								s = s.replaceAll("@@@message@@@", msg);										//mensagem post
								s = s.replaceAll("@@@pathPhoto@@@", announce.getMember().getPathPhoto());	//foto membro
								s = s.replaceAll("@@@nickname@@@", announce.getMember().getNickname());		//nickname
								s = s.replaceAll("@@@dateUpdated@@@", announce.getDateUpdated());			//data publicacao
								s = s.replaceAll("@@@redirect@@@", "/ilt/ads?id=" + announce.getIdAnnounce());	//link da publicacao
								div.append(s);
							}
						}
					}
					if(bean instanceof Topic){
						Topic topic = (Topic) bean;
						String msg = topic.getMember().getNickname() + 
								(LING.equals("pt") ? " criou um novo t�pico no forum." : " has created a new topic in the forum.");
						String s = listEntryNotific;
						s = s.replaceAll("@@@message@@@", msg);										//mensagem post
						s = s.replaceAll("@@@pathPhoto@@@", topic.getMember().getPathPhoto());		//foto membro
						s = s.replaceAll("@@@nickname@@@", topic.getMember().getNickname());		//nickname
						s = s.replaceAll("@@@dateUpdated@@@", topic.getDateUpdated());				//data publicacao
						s = s.replaceAll("@@@redirect@@@", "/ilt/group/forum/topic?idTop=" + topic.getIdTopic() + "&idForum=" + topic.getIdForum());	//link da publicacao
						div.append(s);
					}
					if(bean instanceof Comment){
						Comment comment = (Comment) bean;
						String msg = comment.getMember().getNickname() + 
								(LING.equals("pt") ? " respondeu no forum." : " has replied on forum.");
						String s = listEntryNotific;
						s = s.replaceAll("@@@message@@@", msg);										//mensagem post
						s = s.replaceAll("@@@pathPhoto@@@", comment.getMember().getPathPhoto());	//foto membro
						s = s.replaceAll("@@@nickname@@@", comment.getMember().getNickname());		//nickname
						s = s.replaceAll("@@@dateUpdated@@@", comment.getDateUpdated());			//data publicacao
						s = s.replaceAll("@@@redirect@@@", "/ilt/group/forum/topic?idTop=" + comment.getIdTopic() + "&idForum=" + comment.getIdForum());	//link da publicacao
						div.append(s);
					}
					if(bean instanceof MessageInbox){
						MessageInbox message = (MessageInbox) bean;
						String msg = message.getMember().getNickname() + 
								(LING.equals("pt") ? " lhe enviou uma mensagem \"" : " sent you a message \"") + message.getSubject() + "\".";
						String s = listEntryNotific;
						s = s.replaceAll("@@@message@@@", msg);										//mensagem post
						s = s.replaceAll("@@@pathPhoto@@@", message.getMember().getPathPhoto());	//foto membro
						s = s.replaceAll("@@@nickname@@@", message.getMember().getNickname());		//nickname
						s = s.replaceAll("@@@dateUpdated@@@", message.getDateUpdated());			//data publicacao
						s = s.replaceAll("@@@redirect@@@", "/ilt/message/inbox/view?id=" + message.getIdMsg() + "&subject" + message.getSubject());	//link da publicacao
						div.append(s);
					}
					//curtidas
					if(bean instanceof Likes){
						Likes likes = (Likes) bean;
						String msg = likes.getNickname();								
						String s = listEntryNotific;
						s = s.replaceAll("@@@pathPhoto@@@", likes.getPathPhoto());		//foto membro
						s = s.replaceAll("@@@nickname@@@", likes.getNickname());		//nickname
						s = s.replaceAll("@@@dateUpdated@@@", likes.getDateUpdated());	//data publicacao
						String link = "";
						switch (likes.getPostType().toCharArray()[0]){
							case 'C' : link = "/ilt/collection/profile?id=" + likes.getIdPost(); 
									msg += (LING.equals("pt") ? " curtiu sua cole��o." : " has liked your collection.");
									break;
							case 'I' : link = "/ilt/item/view?id=" + likes.getIdPost(); 
									msg += (LING.equals("pt") ? " curtiu seu item." : " has liked your item.");
									break;
							case 'V' : link = "/ilt/video/view?id=" + likes.getIdPost(); 
									msg += (LING.equals("pt") ? " curtiu seu v�deo." : " has liked your video.");
									break;
							case 'E' : link = "/ilt/event/view?id=" + likes.getIdPost(); 
									msg += (LING.equals("pt") ? " curtiu seu evento." : " has liked your event.");
									break;
							case 'M' : link = "/ilt/group/forum/topic?idTop=" + likes.getIdPost().split(",")[0] 
									+ "&idForum=" + likes.getIdPost().split(",")[1]; 
									msg += (LING.equals("pt") ? " curtiu seu coment�rio." : " has liked your comment.");
									break;	//ex: 123,123 (idtop,idforum)
						}
						s = s.replaceAll("@@@message@@@", msg);							//mensagem post
						s = s.replaceAll("@@@redirect@@@", link);	//link da publicacao
						div.append(s);
					}
				}
			}
		}
		
		MemberDAO dao = new MemberDAO(db, null);
		member.setLastSeenDate(dateNow);					//seta data ultimo visto			
		dao.updateLastDateNotification(member);				//salva dados no bd
		cms.getMysession().getSession().setAttribute("member", member);			//atualiza dados membro na sessao
		
		if(!div.toString().equals("")){
			try {
				return new String(div.toString().getBytes("UTF-8"));		//retorna lista de notificacao
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return div.toString();
			}
		}else{
			return "No notifications!";	//nao ha notificacao
		}
	}
	
	
	
	private static void verificaStatusLeilaoEncerrado(HttpServletRequest request, DB db, ColumnsSingleton CS, Member member){
		
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	    		
		long miliInicial = format.parse(announce.getDateInitial()).getTime();	//milisegundos inicial
		long miliFinal = miliInicial + ( Integer.parseInt(announce.getLasting()) * (1000*60*60*24) );
		long miliAgora = new java.util.Date().getTime(); 								//milisegundos agora
		
		if(miliFinal <= miliAgora){
			
		}else{
			
		
		String SQLAnnounce = "select t1.id_announce as id_announce, t1.date_initial as date_initial "
				+ "from dbannounce t1 where t1.fk_user_id = '" +member.getIdMember()+ "' and t1.type_announce = 'Auction' and t1.status = 'Open'";
				
		String[][] aliasSQL = { {"dbannounce", "t1"} };
		SQLAnnounce = CS.transformSQLReal(SQLAnnounce, aliasSQL);
		LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQLAnnounce);
		}
		
		MessageInboxDAO messageDAO = new MessageInboxDAO(db, request);
		MessageInbox message = new MessageInbox();
		
		message.setMessage("");
		message.setSubject("");
		message.setReceiverIdMember("");
		message.setSenderIdMember("");
		message.setWasRead("n");
		message.setContentType("item");
		message.setIdContent("");
		message.setFkMsgId("0");
		message.setIdAnnounce("");		
		message.setSenderHidden("n");
		message.setReceiverHidden("n");
		
		String idCreate = messageDAO.create(message);				//cria e envia mensagem
		
		//cria notificacao de envio de mensagem
		String myUserid = (String) request.getSession().getAttribute("userid");
		NotificationService.createNotification(db, "", "message", idCreate, Str.INCLUDED, myUserid);
		
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
