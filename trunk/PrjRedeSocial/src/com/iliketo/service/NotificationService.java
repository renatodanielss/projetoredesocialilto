package com.iliketo.service;

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

import HardCore.DB;

import com.iliketo.dao.MemberDAO;
import com.iliketo.dao.NotificationDAO;
import com.iliketo.model.Announce;
import com.iliketo.model.Collection;
import com.iliketo.model.Comment;
import com.iliketo.model.ContentILiketo;
import com.iliketo.model.Event;
import com.iliketo.model.Item;
import com.iliketo.model.Member;
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
		System.out.println("Log - Nova notificacao = id notific: " + idCreated + " / id publicacao: " + idContent + " - tipo conteudo: " + contentType);
		
	}
	
	/**
	 * Metodo retorna um inteiro com total de notificacoes nao lida a partir da ultima data de visto do membro(last_seen_date).
	 * @param db
	 * @param member
	 * @return
	 */
	public static int totalNotifications(HttpServletRequest request){
		
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
		System.out.println("SQL Collection: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatCollection  = db.query_records(SQL);

		SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbinterest as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		aliasSQL = new String[][] { {"dbinterest", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		System.out.println("SQL Interest: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatInterest  = db.query_records(SQL);
		
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
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de collection das categorias: " + collectionCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de item das categorias: " + itemCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de video das categorias: " + videoCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de event das categorias: " + eventCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de announce das categorias: " + announceCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de topic das categorias: " + topicCats);
			if(!collectionCats.equals("")) System.out.println("Receber notificacoes de comment das categorias: " + commentCats + "\n");
			
			
			String SQLCollection = "select t1.id_collection as id_collection "
					+ "from dbcollection as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_collection "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'collection' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLItem = "select t1.id_item as id_item "
					+ "from dbcollectionitem as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_item "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'item' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLVideo = "select t1.id_video as id_video "
					+ "from dbcollectionvideo as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_video "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'video' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLEvent = "select t1.id_event as id_event "
					+ "from dbevent as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_event "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'event' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLAnnounce = "select t1.id_announce as id_announce "
					+ "from dbannounce as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_announce "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'announce' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLTopic = "select t1.id_topic as id_topic "
					+ "from dbforumtopic as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_topic "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'topic' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLComment = "select t1.id_comment as id_comment "
					+ "from dbforumcomment as t1 join dbgroupnotification as n on n.fk_content_id = t1.id_comment "
					+ "where n.fk_user_id != '"+myUserid+"' and n.content_type = 'comment' and n.date_created > '"+lastSeenDate+"' and n.date_created <= '"+dateNow+"'";
			
			
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

			LinkedHashMap<String,HashMap<String,String>> recordsCollections = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsItems = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsVideos = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsEvent = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsAnnounce = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsTopic = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsComment = new LinkedHashMap<String,HashMap<String,String>>();
			
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
			
			//total de notificacoes
			int totalNews = recordsCollections.size() + recordsItems.size() + recordsVideos.size() + recordsEvent.size() + 
					recordsAnnounce.size() + recordsTopic.size() + recordsComment.size();
			
			return totalNews; 	//retorna total notificacoes novas
		}
		return 0;			//nao ha notificacoes novas
	}
	
	/**
	 * Metodo retorna uma lista com historico de notificacao dos ultimos 10 dias ou todas notificacoes dependendo da pagina requisitada.
	 * @param cms
	 * @param member
	 * @param idPageListEntry
	 * @return
	 */
	public static String listHistoricNotification(CmsConfigILiketo cms, Member member, String idPageListEntry){
		
		DB db = (DB) cms.getMyrequest().getRequest().getAttribute(Str.CONNECTION_DB);	//db
		HttpSession session = cms.getMyrequest().getRequest().getSession();				//session
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);		
		String myUserid = (String) session.getAttribute("userid");
		
		
		String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()); //data de visto agora
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();		
		calendar.set(Calendar.DAY_OF_MONTH, -10);			//seta data 10 dias anterior
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
		System.out.println("SQL Collection: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatCollection  = db.query_records(SQL);

		SQL = 
				"select t1.fk_category_id as fk_category_id, t1.name_category as name_category, "
				+ "t1.notific_collection as notific_collection, t1.notific_item as notific_item, "
				+ "t1.notific_video as notific_video, t1.notific_event as notific_event, t1.notific_announce as notific_announce, "
				+ "t1.notific_topic as notific_topic, t1.notific_comment as notific_comment "
				+ "from dbinterest as t1 where t1.fk_user_id = '" +myUserid+ "';";
				
		aliasSQL = new String[][] { {"dbinterest", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		System.out.println("SQL Interest: " + SQL);
		LinkedHashMap<String,HashMap<String,String>> recordsCatInterest  = db.query_records(SQL);
		
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
		
		//verifica se a lista possui configuracao de notificacao (colecao/interesse) em alguma categoria
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
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollection as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_collection where n.fk_user_id != '"+myUserid+"' and n.content_type = 'collection' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLItem = "select t1.id_item as id_item, t1.title_item as title_item, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollectionitem as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_item where n.fk_user_id != '"+myUserid+"' and n.content_type = 'item' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLVideo = "select t1.id_video as id_video, t1.title_video as title_video, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbcollectionvideo as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_video where n.fk_user_id != '"+myUserid+"' and n.content_type = 'video' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLEvent = "select t1.id_event as id_event, t1.name_event as name_event, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbevent as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_event where n.fk_user_id != '"+myUserid+"' and n.content_type = 'event' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLAnnounce = "select t1.id_announce as id_announce, t1.title as title, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbannounce as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_announce where n.fk_user_id != '"+myUserid+"' and n.content_type = 'announce' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLTopic = "select t1.id_topic as id_topic, t1.subject as subject, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbforumtopic as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
					+ "join dbgroupnotification as n on n.fk_content_id = t1.id_topic where n.fk_user_id != '"+myUserid+"' and n.content_type = 'topic' "
					+ "and n.date_created > '"+dateDaysNotific+"' and n.date_created <= '"+dateNow+"'";
			
			String SQLComment = "select t1.id_comment as id_comment, t1.text_comment as text_comment, t1.date_updated as date_updated,  "
					+ "m.nickname as nickname, m.path_photo_member as path_photo_member, n.post_type as post_type "
					+ "from dbforumcomment as t1 join dbmembers as m on t1.fk_user_id = m.id_member "
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

			ArrayList<ContentILiketo> listModelNotification = new ArrayList<ContentILiketo>();
			LinkedHashMap<String,HashMap<String,String>> recordsCollections = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsItems = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsVideos = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsEvent = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsAnnounce = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsTopic = new LinkedHashMap<String,HashMap<String,String>>();
			LinkedHashMap<String,HashMap<String,String>> recordsComment = new LinkedHashMap<String,HashMap<String,String>>();

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
			for(String rec : recordsCollections.keySet()){
				Collection col = new Collection();
				Member m = new Member();	
				col.setIdCollection(recordsCollections.get(rec).get("id_collection"));				
				col.setNameCollection(recordsCollections.get(rec).get("name_collection"));
				col.setNameCategory(recordsCollections.get(rec).get("name_category"));
				col.setDateUpdated(recordsCollections.get(rec).get("date_updated"));
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
				m.setNickname(recordsComment.get(rec).get("nickname"));
				m.setPathPhoto(recordsComment.get(rec).get("path_photo_member"));
				c.setMember(m);
				listModelNotification.add(c);
			}
			
			//ordena lista de notificacoes das publicacoes(conteudo) por ordem updated
			Collections.sort(listModelNotification);
			
			String listEntryNotific = cms.getPageListEntry(idPageListEntry);	//page list notification (notificacao no template ou page more notifications)
			StringBuffer div = new StringBuffer();								//div result html			
			
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
								msg = col.getMember().getNickname() + " has joined the group - \"" + col.getNameCategory() + "\".";
							}else if(recordsCollections.get(rec).get("post_type").equals(Str.UPDATED)){
								msg = col.getMember().getNickname() + " has updated his collection - \"" + col.getNameCollection() + "\".";
							}
							String s = listEntryNotific;
							s = s.replaceAll("@@@message@@@", msg);									//mensagem post
							s = s.replaceAll("@@@pathPhoto@@@", col.getMember().getPathPhoto());	//foto membro
							s = s.replaceAll("@@@nickname@@@", col.getMember().getNickname());		//nickname
							s = s.replaceAll("@@@dateUpdated@@@", col.getDateUpdated());			//data publicacao
							s = s.replaceAll("@@@redirect@@@", "");									//link da publicacao
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
								msg = item.getMember().getNickname() + " has included a new item - \"" + item.getTitle() + "\".";
							}else if(recordsItems.get(rec).get("post_type").equals(Str.UPDATED)){
								msg = item.getMember().getNickname() + " has updated his item - \"" + item.getTitle() + "\".";
							}
							String s = listEntryNotific;
							s = s.replaceAll("@@@message@@@", msg);									//mensagem post
							s = s.replaceAll("@@@pathPhoto@@@", item.getMember().getPathPhoto());	//foto membro
							s = s.replaceAll("@@@nickname@@@", item.getMember().getNickname());		//nickname
							s = s.replaceAll("@@@dateUpdated@@@", item.getDateUpdated());			//data publicacao
							s = s.replaceAll("@@@redirect@@@", "");									//link da publicacao
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
								msg = video.getMember().getNickname() + " has included a new video - \"" + video.getTitle() + "\".";
							}else if(recordsVideos.get(rec).get("post_type").equals(Str.UPDATED)){
								msg = video.getMember().getNickname() + " has updated his video - \"" + video.getTitle() + "\".";
							}
							String s = listEntryNotific;
							s = s.replaceAll("@@@message@@@", msg);									//mensagem post
							s = s.replaceAll("@@@pathPhoto@@@", video.getMember().getPathPhoto());	//foto membro
							s = s.replaceAll("@@@nickname@@@", video.getMember().getNickname());	//nickname
							s = s.replaceAll("@@@dateUpdated@@@", video.getDateUpdated());			//data publicacao
							s = s.replaceAll("@@@redirect@@@", "");									//link da publicacao
							div.append(s);
						}
					}					
				}
				if(bean instanceof Event){
					Event event = (Event) bean;
					String msg = event.getMember().getNickname() + " has created \"" + event.getNameEvent() + "\" event.";
					String s = listEntryNotific;
					s = s.replaceAll("@@@message@@@", msg);									//mensagem post
					s = s.replaceAll("@@@pathPhoto@@@", event.getMember().getPathPhoto());	//foto membro
					s = s.replaceAll("@@@nickname@@@", event.getMember().getNickname());	//nickname
					s = s.replaceAll("@@@dateUpdated@@@", event.getDateUpdated());			//data publicacao
					s = s.replaceAll("@@@redirect@@@", "");									//link da publicacao
					div.append(s);
				}
				if(bean instanceof Announce){
					Announce announce = (Announce) bean;
					String msg = announce.getMember().getNickname() + " has announced the \"" + announce.getTitle() + "\" item.";
					String s = listEntryNotific;
					s = s.replaceAll("@@@message@@@", msg);										//mensagem post
					s = s.replaceAll("@@@pathPhoto@@@", announce.getMember().getPathPhoto());	//foto membro
					s = s.replaceAll("@@@nickname@@@", announce.getMember().getNickname());		//nickname
					s = s.replaceAll("@@@dateUpdated@@@", announce.getDateUpdated());			//data publicacao
					s = s.replaceAll("@@@redirect@@@", "");										//link da publicacao
					div.append(s);
				}
				if(bean instanceof Topic){
					Topic topic = (Topic) bean;
					String msg = topic.getMember().getNickname() + " has created a new topic in the forum.";
					String s = listEntryNotific;
					s = s.replaceAll("@@@message@@@", msg);										//mensagem post
					s = s.replaceAll("@@@pathPhoto@@@", topic.getMember().getPathPhoto());		//foto membro
					s = s.replaceAll("@@@nickname@@@", topic.getMember().getNickname());		//nickname
					s = s.replaceAll("@@@dateUpdated@@@", topic.getDateUpdated());				//data publicacao
					s = s.replaceAll("@@@redirect@@@", "");										//link da publicacao
					div.append(s);
				}
				if(bean instanceof Comment){
					Comment comment = (Comment) bean;
					String msg = comment.getMember().getNickname() + " has replied on forum.";
					String s = listEntryNotific;
					s = s.replaceAll("@@@message@@@", msg);										//mensagem post
					s = s.replaceAll("@@@pathPhoto@@@", comment.getMember().getPathPhoto());	//foto membro
					s = s.replaceAll("@@@nickname@@@", comment.getMember().getNickname());		//nickname
					s = s.replaceAll("@@@dateUpdated@@@", comment.getDateUpdated());			//data publicacao
					s = s.replaceAll("@@@redirect@@@", "");										//link da publicacao
					div.append(s);
				}
			}			
						
			MemberDAO dao = new MemberDAO(db, null);
			member.setLastSeenDate(dateNow);				//seta data ultimo visto			
			dao.updateLastDateNotification(member);			//salva dados no bd
			cms.getMysession().getSession().setAttribute("member", member);		//atualiza dados membro na sessao
			
			if(!div.toString().equals("")){
				return div.toString();		//retorna lista de notificacao
			}else{
				return "No notifications!";	//nao ha notificacao
			}
		}
		
		MemberDAO dao = new MemberDAO(db, null);
		member.setLastSeenDate(dateNow);					//seta data ultimo visto			
		dao.updateLastDateNotification(member);				//salva dados no bd
		cms.getMysession().getSession().setAttribute("member", member);			//atualiza dados membro na sessao
		
		return "No notifications!";	//nao ha notificacao
	}
	
}
