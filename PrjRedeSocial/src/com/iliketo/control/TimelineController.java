package com.iliketo.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import HardCore.DB;
import HardCore.Request;
import HardCore.Session;

import com.iliketo.bean.AnnounceJB;
import com.iliketo.bean.CollectionJB;
import com.iliketo.bean.ContentILiketoJB;
import com.iliketo.bean.EventJB;
import com.iliketo.bean.ItemJB;
import com.iliketo.bean.MemberJB;
import com.iliketo.bean.VideoJB;
import com.iliketo.util.ColumnsSingleton;

/**
 * Classe utilizada para o controle de dados na timeline ILiketo
 * Recupera valores do banco de dados
 * Armazena os valores nas classes javabean correspondente ao conteúdo
 * Retorna uma lista genérica com conteudos/dados para apresentar na timeline 
 * 
 * @author osvaldimar.costa
 *
 */
public class TimelineController {
	
	
	/**
	 *Conteudos timeline update news e olds
	 *Minhas colecoes e todas colecoes das categorias/grupos que pertenco
	 *Todos itens das colecoes das categorias/grupos que pertenco
	 *Todos videos das colecoes das categorias/grupos que pertenco
	 *Todos os eventos das categorias/grupos que pertenco
	 *Todos topicos dos forums que pertenco
	 *Todos comentarios dos forums que pertenco
	 *
	 *1 Identifica tipo de solicitação timeline news e olds
	 *2 Identifica o uso de paginação com offset para olds
	 *3 Monta querys de select e executa no banco de dados
	 *4 Recupera os valores e monta o conteudo em cada classe de modelo
	 *5 Adiciona conteudos na lista generica e retorna a lista
	 * 
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @return
	 */
	public ArrayList<ContentILiketoJB> updateTimelineNewsOlds(DB db, Request request, Session mysession, String typeTimeline){

		ArrayList<ContentILiketoJB> listTimeline = new ArrayList<ContentILiketoJB>();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		HashMap<String,String> mapOffset = null;
		
		//offset para controle de paginação da timeline de registros antigos
		mapOffset = (HashMap<String,String>) mysession.getSession().getAttribute("mapOffset");
		
		if(mapOffset == null || typeTimeline.equals("news")){
			//se não existir objeto mapOffset ou se for nova timeline 'news', seta na sessão mapOffset começando com zero para todos conteudos para mostrar novos registros
			mapOffset = new HashMap<String,String>();
			mapOffset.put("offsetCol", "0");
			mapOffset.put("offsetItem", "0");
			mapOffset.put("offsetVideo", "0");
			mapOffset.put("offsetEvent", "0");
			mapOffset.put("offsetAd", "0");
			//mapOffset.put("offsetTopic", "0");
			mysession.getSession().setAttribute("mapOffset", mapOffset);
		}
		
		System.out.println("\n\n***INIT TIMELINE "+typeTimeline+"***\n");

		//informações my user
		String myUserid = mysession.get("userid");
		
		//Comando sql para item
		String SQLCollection = 
				  "select c1.id_collection as id_collection, c1.name_collection as name_collection, c1.description as description, c1.name_category as name_category, c1.path_photo_collection as path_photo_collection, "
				 + "c1.date_created as date_created, c1.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				 + "from dbcollection c1 join dbmembers m on c1.fk_user_id = m.id_member "
				 + "where exists (select c2.fk_category_id from dbcollection c2 join dbinterest it on it.fk_user_id ='" +myUserid+ "' "
				+ "where (c1.fk_category_id = c2.fk_category_id and c2.fk_user_id ='" +myUserid+ "') or (c1.fk_category_id = it.fk_category_id)) "
				+ "order by c1.date_updated desc limit 2 offset '" +mapOffset.get("offsetCol")+ "';";
		
		System.out.println("SQLCollection Comum: " + SQLCollection);		
		String[][] aliasCollection = { {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"}, {"dbinterest", "it"} };		
		SQLCollection = CS.transformSQLReal(SQLCollection, aliasCollection);
		System.out.println("SQLCollection Real: " + SQLCollection);
		
		//Comando sql para item
		String SQLItem = 
				  "select i.id_item as id_item, i.title_item as title_item, i.path_photo_item as path_photo_item, "
				  + "i.date_created as date_created, i.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				  + "from dbcollectionitem i join dbmembers m on i.fk_user_id = m.id_member "
				  + "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 join dbinterest it on it.fk_user_id ='" +myUserid+ "' "
				+ "where ((c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "') or (c1.fk_category_id = it.fk_category_id)) and i.fk_collection_id = c1.id_collection)) "
				+ "order by i.date_updated desc limit 2 offset '" +mapOffset.get("offsetItem")+ "';";
		
		System.out.println("\nSQLItem Comum: " + SQLItem);
		String[][] aliasItem = { {"dbcollectionitem", "i"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"}, {"dbinterest", "it"} };
		SQLItem = CS.transformSQLReal(SQLItem, aliasItem);
		System.out.println("SQLItem Real: " + SQLItem);
		
		//Comando sql para video
		String SQLVideo =
				  "select v.id_video as id_video, v.title_video as title_video, v.path_file_video as path_file_video, "
				  + "v.date_created as date_created, v.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				  + "from dbcollectionvideo v join dbmembers m on v.fk_user_id = m.id_member "
				  + "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 join dbinterest it on it.fk_user_id ='" +myUserid+ "' "
				+ "where ((c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "') or (c1.fk_category_id = it.fk_category_id)) and v.fk_collection_id = c1.id_collection)) "
				+ "order by v.date_updated desc limit 2 offset '" +mapOffset.get("offsetVideo")+ "';";
		
		System.out.println("\nSQLVideo Comum: " + SQLVideo);
		String[][] aliasVideo = { {"dbcollectionvideo", "v"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"}, {"dbinterest", "it"} };
		SQLVideo = CS.transformSQLReal(SQLVideo, aliasVideo);
		System.out.println("SQLVideo Real: " + SQLVideo);
		
		/*Comando sql para topic
		String SQLTopic = 
				  "select t.id_topic as id_topic, t.subject as subject, t.date_created as date_created, t.date_updated as date_updated, "
				 + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				 + "from dbforumtopic t join dbmembers m on t.fk_user_id = m.id_member "
				 + "where exists (select f.id_forum from dbforum f "
				+ "where exists (select g.id_group from dbgroup g "
				+ "where exists (select c.id_collection from dbcollection c "
				+ "where g.fk_category_id = c.fk_category_id and c.fk_user_id = '" +myUserid+ "' and f.fk_group_id = g.id_group "
				+ "and t.fk_forum_id = f.id_forum))) order by t.date_updated desc limit 2 offset '" +mapOffset.get("offsetTopic")+ "';";
		
		System.out.println("\nSQLTopic Comum: " + SQLTopic);
		String[][] aliasTopic = { {"dbforumtopic", "t"}, {"dbmembers", "m"}, {"dbforum", "f"}, {"dbgroup", "g"}, {"dbcollection", "c"} };
		SQLTopic = CS.transformSQLReal(SQLTopic, aliasTopic);
		System.out.println("SQLTopic Real: " + SQLTopic);*/
		
		
		//Comando sql para event
		String SQLEvent = 
				"select e.id_event as id_event, e.name_event as name_event, e.details_event as details_event, e.path_photo_event as path_photo_event, "
			  + "e.date_event as date_event, e.hour_event as hour_event, e.type_event as type_event, e.local_event as local_event, "
			  + "e.date_created as date_created, e.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
			  + "from dbevent e join dbmembers m on e.fk_user_id = m.id_member "
			  + "where exists (select c1.id_collection from dbcollection c1 join dbinterest it on it.fk_user_id ='" +myUserid+ "' "
			  + "where (c1.fk_user_id = '" +myUserid+ "' and e.fk_category_id = c1.fk_category_id) or (e.fk_category_id = it.fk_category_id)) "
			  + "order by e.date_updated desc limit 2 offset '" +mapOffset.get("offsetEvent")+ "';";
		
		System.out.println("\nSQLEvent Comum: " + SQLEvent);
		String[][] aliasEvent = { {"dbevent", "e"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbinterest", "it"} };
		SQLEvent = CS.transformSQLReal(SQLEvent, aliasEvent);
		System.out.println("SQLEvent Real: " + SQLEvent);
		
		//Comando sql para anuncio
		String SQLAnnounce = 
				"select ad.id_announce as id_announce, ad.fk_item_id as fk_item_id, ad.fk_collection_id as fk_collection_id, ad.fk_category_id as fk_category_id, "
			  + "ad.name_category as name_category, ad.title as title, ad.description as description, ad.type_announce as type_announce, ad.price_fixed as price_fixed, "
			  + "ad.price_initial as price_initial, ad.date_initial as date_initial, ad.bid_actual as bid_actual, ad.lasting as lasting, ad.total_bids as total_bids, "
			  + "ad.date_created as date_created, ad.date_updated as date_updated, ad.path_photo_ad as path_photo_ad, "
			  + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
			  + "from dbannounce ad join dbmembers as m on ad.fk_user_id = m.id_member "
			  + "where exists (select c1.id_collection from dbcollection c1 join dbinterest it on it.fk_user_id ='" +myUserid+ "' "
			  + "where (c1.fk_user_id = '" +myUserid+ "' and ad.fk_category_id = c1.fk_category_id) or (ad.fk_category_id = it.fk_category_id)) "
			  + "order by ad.date_updated desc limit 2 offset '" +mapOffset.get("offsetAd")+ "';";
		
		System.out.println("\nSQLAnnounce Comum: " + SQLAnnounce);
		String[][] aliasAnnounce = { {"dbannounce", "ad"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbinterest", "it"} };
		SQLAnnounce = CS.transformSQLReal(SQLAnnounce, aliasAnnounce);
		System.out.println("SQLAnnounce Real: " + SQLAnnounce);
		
		//Chama classe DB.java para executar a query no banco de dados e retorna um HashMap com todos registros encontrados
		LinkedHashMap<String,HashMap<String,String>> recordsCollections  = db.query_records(SQLCollection); //map de registros collections
		LinkedHashMap<String,HashMap<String,String>> recordsItems  = db.query_records(SQLItem); //map de registros items
		LinkedHashMap<String,HashMap<String,String>> recordsVideos  = db.query_records(SQLVideo); //map de registros videos
		LinkedHashMap<String,HashMap<String,String>> recordsEvent  = db.query_records(SQLEvent); //map de registros videos
		LinkedHashMap<String,HashMap<String,String>> recordsAnnounce  = db.query_records(SQLAnnounce); //map de registros anuncio
		//LinkedHashMap<String,HashMap<String,String>> recordsTopic  = db.query_records(SQLTopic); //map de registros topicos

		
		//Popula conteudo ILiketo nas classes javabean e adiciona na listTimeline
		System.out.println("\nRegistros Colleções:");
		for(String rec : recordsCollections.keySet()){			
			CollectionJB collectionJB = new CollectionJB(); //coleção
			MemberJB memberJB = new MemberJB();				//membro dono da coleção
			
			collectionJB.setIdCollection(recordsCollections.get(rec).get("id_collection"));
			collectionJB.setNameCollection(recordsCollections.get(rec).get("name_collection"));
			collectionJB.setDescription(recordsCollections.get(rec).get("description"));
			collectionJB.setCategory(recordsCollections.get(rec).get("name_category"));
			collectionJB.setDateCreated(recordsCollections.get(rec).get("date_created"));
			collectionJB.setDateUpdated(recordsCollections.get(rec).get("date_updated"));
			collectionJB.setPathPhoto(recordsCollections.get(rec).get("path_photo_collection"));
			
			memberJB.setIdMember(recordsCollections.get(rec).get(("id_member")));
			memberJB.setNickname(recordsCollections.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsCollections.get(rec).get(("path_photo_member")));
			collectionJB.setMember(memberJB);	//seta informações do membro na coleção
			
			listTimeline.add(collectionJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsCollections.get(rec));
		}
		
		System.out.println("\nRegistros Items:");
		for(String rec : recordsItems.keySet()){			
			ItemJB itemJB = new ItemJB();			//item
			MemberJB memberJB = new MemberJB();		//membro dono do item
			
			itemJB.setIdItem(recordsItems.get(rec).get(("id_item")));
			itemJB.setTitle(recordsItems.get(rec).get(("title_item")));
			itemJB.setDateCreated(recordsItems.get(rec).get(("date_created")));
			itemJB.setDateUpdated(recordsItems.get(rec).get(("date_updated")));
			itemJB.setPathPhoto(recordsItems.get(rec).get(("path_photo_item")));
			
			memberJB.setIdMember(recordsItems.get(rec).get(("id_member")));
			memberJB.setNickname(recordsItems.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsItems.get(rec).get(("path_photo_member")));;
			itemJB.setMember(memberJB);	//seta informações do membro no item
			
			listTimeline.add(itemJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsItems.get(rec));
		}
		
		System.out.println("\nRegistros Videos:");
		for(String rec : recordsVideos.keySet()){			
			VideoJB videoJB = new VideoJB();		//video
			MemberJB memberJB = new MemberJB();		//membro dono do video
			
			videoJB.setIdVideo(recordsVideos.get(rec).get("id_video"));
			videoJB.setTitle(recordsVideos.get(rec).get("title_video"));
			videoJB.setDateCreated(recordsVideos.get(rec).get("date_created"));
			videoJB.setDateUpdated(recordsVideos.get(rec).get("date_updated"));
			videoJB.setPathVideo(recordsVideos.get(rec).get("path_file_video"));
			
			memberJB.setIdMember(recordsVideos.get(rec).get(("id_member")));
			memberJB.setNickname(recordsVideos.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsVideos.get(rec).get(("path_photo_member")));
			videoJB.setMember(memberJB);	//seta informações do membro no video
			
			listTimeline.add(videoJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsVideos.get(rec));
		}
		
		System.out.println("\nRegistros Eventos:");
		for(String rec : recordsEvent.keySet()){			
			EventJB eventJB = new EventJB();		//video
			MemberJB memberJB = new MemberJB();		//membro dono do video
			
			eventJB.setIdEvent(recordsEvent.get(rec).get("id_event"));
			eventJB.setNameEvent(recordsEvent.get(rec).get("name_event"));
			eventJB.setDateEvent(recordsEvent.get(rec).get("date_event"));
			eventJB.setHourEvent(recordsEvent.get(rec).get("hour_event"));
			eventJB.setDetails(recordsEvent.get(rec).get("details_event"));			
			eventJB.setLocal(recordsEvent.get(rec).get("local_event"));
			eventJB.setTypeEvent(recordsEvent.get(rec).get("type_event"));
			eventJB.setDateCreated(recordsEvent.get(rec).get("date_created"));
			eventJB.setDateUpdated(recordsEvent.get(rec).get("date_updated"));
			eventJB.setPathPhoto(recordsEvent.get(rec).get("path_photo_event"));
			
			memberJB.setIdMember(recordsEvent.get(rec).get(("id_member")));
			memberJB.setNickname(recordsEvent.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsEvent.get(rec).get(("path_photo_member")));
			eventJB.setMember(memberJB);	//seta informações do membro no evento
			
			listTimeline.add(eventJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsEvent.get(rec));
		}
		
		System.out.println("\nRegistros Anuncios:");
		for(String rec : recordsAnnounce.keySet()){			
			AnnounceJB announceJB = new AnnounceJB();		//anuncio
			MemberJB memberJB = new MemberJB();				//membro dono do anuncio
			
			announceJB.setIdAnnounce(recordsAnnounce.get(rec).get("id_announce"));
			announceJB.setIdItem(recordsAnnounce.get(rec).get("fk_item_id"));
			announceJB.setIdCollection(recordsAnnounce.get(rec).get("fk_collection_id"));
			announceJB.setIdCategory(recordsAnnounce.get(rec).get("fk_category_id"));
			//announceJB.setIdMember(recordsAnnounce.get(rec).get("fk_user_id"));
			announceJB.setNameCategory(recordsAnnounce.get(rec).get("name_category"));
			announceJB.setTitle(recordsAnnounce.get(rec).get("title"));
			announceJB.setDescription(recordsAnnounce.get(rec).get("description"));
			announceJB.setTypeAnnounce(recordsAnnounce.get(rec).get("type_announce"));
			announceJB.setPriceFixed(recordsAnnounce.get(rec).get("price_fixed"));
			announceJB.setPriceInitial(recordsAnnounce.get(rec).get("price_initial"));
			announceJB.setDateInitial(recordsAnnounce.get(rec).get("date_initial"));
			announceJB.setBidActual(recordsAnnounce.get(rec).get("bid_actual"));
			announceJB.setLasting(recordsAnnounce.get(rec).get("lasting"));
			announceJB.setTotalBids(recordsAnnounce.get(rec).get("total_bids"));
			announceJB.setDateCreated(recordsAnnounce.get(rec).get("date_created"));
			announceJB.setDateUpdated(recordsAnnounce.get(rec).get("date_updated"));
			announceJB.setPath_photo_ad(recordsAnnounce.get(rec).get("path_photo_ad"));
			
			memberJB.setIdMember(recordsAnnounce.get(rec).get("id_member"));
			memberJB.setNickname(recordsAnnounce.get(rec).get("nickname"));
			memberJB.setPathPhoto(recordsAnnounce.get(rec).get("path_photo_member"));
			announceJB.setMember(memberJB);	//seta informações do membro no evento
			
			listTimeline.add(announceJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsAnnounce.get(rec));
		}
		
		/*
		System.out.println("\nRegistros Topicos:");
		for(String rec : recordsTopic.keySet()){			
			TopicJB topicJB = new TopicJB();		//topico
			MemberJB memberJB = new MemberJB();		//membro dono do topico
			
			topicJB.setIdTopic(recordsTopic.get(rec).get(("id_topic")));
			topicJB.setSubject(recordsTopic.get(rec).get(("subject")));
			topicJB.setDateCreated(recordsTopic.get(rec).get(("date_created")));
			topicJB.setDateUpdated(recordsTopic.get(rec).get(("date_updated")));
			
			memberJB.setIdMember(recordsTopic.get(rec).get(("id_member")));
			memberJB.setNickname(recordsTopic.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsTopic.get(rec).get(("path_photo_member")));
			topicJB.setMember(memberJB);	//seta informações do membro no topico
			
			
			System.out.println("row:" + rec+ " - column/values: " + recordsTopic.get(rec)); //topico atual
			String idTopic = topicJB.getIdTopic(); //numero do id do topico pai do comentario
			
			
			//Comando sql para recuperar até 4 comentarios deste topico atual do laço do for
			String SQLComment = 
					  "select com.id_comment as id_comment, com.text_comment as text_comment, com.date_created as date_created, com.date_updated as date_updated, "
					+ "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
					+ "from dbforumcomment com join dbmembers m on com.fk_user_id = m.id_member "
					+ "where com.fk_topic_id = " +idTopic+ " order by com.date_updated desc limit 4;";
			
			System.out.println("\nSQLComment Comum: " + SQLComment);
			String[][] aliasComment = { {"dbforumcomment", "com"}, {"dbmembers", "m"} };
			SQLComment = CS.transformSQLReal(SQLComment, aliasComment);
			System.out.println("SQLComment Real: " + SQLComment);
			
			
			//executa query para comentarios do registro do topico atual do loop
			LinkedHashMap<String,HashMap<String,String>> recordsComment  = db.query_records(SQLComment); //map de registros comments
			ArrayList<CommentJB> listComment = new ArrayList<CommentJB>(); //lista de comentarios para setar no topico
			
			
			System.out.println("\nRegistros Comentários:");
			for(String recCom : recordsComment.keySet()){			
				CommentJB commentJB = new CommentJB();			//Comentario
				MemberJB memberJBComment = new MemberJB();		//membro dono do comentario
				
				commentJB.setIdComment(recordsComment.get(recCom).get("id_comment"));
				commentJB.setTextComment(recordsComment.get(recCom).get("text_comment"));
				commentJB.setDateCreated(recordsComment.get(recCom).get("date_created"));
				commentJB.setDateUpdated(recordsComment.get(recCom).get("date_updated"));
				
				memberJBComment.setIdMember(recordsComment.get(recCom).get(("id_member")));
				memberJBComment.setNickname(recordsComment.get(recCom).get(("nickname")));
				memberJBComment.setPathPhoto(recordsComment.get(recCom).get(("path_photo_member")));
				commentJB.setMember(memberJBComment);	//seta informações do membro no comentario
				
				listComment.add(commentJB);
				System.out.println("row:" + rec+ " - column/values: " + recordsComment.get(rec));
			}
			
			Collections.sort(listComment, Collections.reverseOrder());  //inverte ordem comentario do antigo para o mais recente
			topicJB.setListCommentJB(listComment); 	//add list de comment no topico
			listTimeline.add(topicJB);				//add topico na listTimeline
			
		}*/
		
		
		//offset para controle de paginação da timeline de registros antigos
		//recupera a quantidade que já foi exibida e soma com atual exibido
		int totalOffset = Integer.parseInt(mapOffset.get("offsetCol")) + recordsCollections.size();
		mapOffset.put("offsetCol", Integer.toString(totalOffset));
		
		totalOffset = Integer.parseInt(mapOffset.get("offsetItem")) + recordsItems.size();
		mapOffset.put("offsetItem", Integer.toString(totalOffset));
		
		totalOffset = Integer.parseInt(mapOffset.get("offsetVideo")) + recordsVideos.size();
		mapOffset.put("offsetVideo", Integer.toString(totalOffset));
		
		totalOffset = Integer.parseInt(mapOffset.get("offsetEvent")) + recordsEvent.size();
		mapOffset.put("offsetEvent", Integer.toString(totalOffset));
		
		totalOffset = Integer.parseInt(mapOffset.get("offsetAd")) + recordsAnnounce.size();
		mapOffset.put("offsetAd", Integer.toString(totalOffset));
		
		//totalOffset = Integer.parseInt(mapOffset.get("offsetTopic")) + recordsTopic.size();
		//mapOffset.put("offsetTopic", Integer.toString(totalOffset));
		
		mysession.getSession().setAttribute("mapOffset", mapOffset); //seta o mapa de offset para cada conteudo paginado
		
		//ordena a lista de objetos pela data_updated
		Collections.sort(listTimeline);
				
		System.out.println("\n***END TIMELINE "+typeTimeline+"***\n\n");
		
		return listTimeline;
		
	}
	
	
	//método só para testes e organização da nova coluna fk_user_id da dbcollectionitem
		private void organizeFk_user_idItem(DB db){
			
			//recupera todos itens
			String[][] tableAlias = { {"dbcollectionitem", "i"}, {"dbcollection", "c"}  };
			
			String SQLItem= "select i.id_item, c.fk_user_id, c.id_collection from dbcollectionitem i "
					+ "join dbcollection c on i.fk_collection_id = c.id_collection";
			
			ColumnsSingleton cs = ColumnsSingleton.getInstance(db);
			SQLItem = cs.transformSQLReal(SQLItem, tableAlias);
			System.out.println("\n\nOrganize fk_user_id do item sql: " + SQLItem +"\n");
			
			LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQLItem);

			//seta fk_user_id no item
			for(String rec : records.keySet()){			
				
				HashMap<String,String> mydata = new HashMap<String,String>();
				
				String idItem = records.get(rec).get(cs.getCOL(db, "dbcollectionitem", "id_item"));//value da coluna do item
				String idCollection = records.get(rec).get(cs.getCOL(db, "dbcollection", "id_collection"));//value da coluna da coleçao
				String value = records.get(rec).get(cs.getCOL(db, "dbcollection", "fk_user_id"));//value da coluna da coleção
				
				String tableItem = cs.getDATA(db, "dbcollectionitem");
				String colId_item  = cs.getCOL(db, "dbcollectionitem", "id_item");
				String colFk_user_id = cs.getCOL(db, "dbcollectionitem", "fk_user_id");		
				mydata.put(colFk_user_id, value);
				
				System.out.println("Update " + tableItem + " id_item: " + idItem + " fk_user_id: " 
										+ value + " pertence id_collection: " + idCollection);
				
				db.update(tableItem, colId_item, idItem, mydata);//update na data+id usando o id da tabela
				
			}
			System.out.println("\n\n");		
			
		}
	
	//método só para testes e organização da nova coluna fk_user_id da dbcollectionvideo
	private void organizeFk_user_idVideo(DB db){
		
		//recupera todos itens
		String[][] tableAlias = { {"dbcollectionvideo", "v"}, {"dbcollection", "c"}  };
		
		String SQLVideo = "select v.id_video, c.fk_user_id, c.id_collection from dbcollectionvideo v "
				+ "join dbcollection c on v.fk_collection_id = c.id_collection";
		
		ColumnsSingleton cs = ColumnsSingleton.getInstance(db);
		SQLVideo = cs.transformSQLReal(SQLVideo, tableAlias);
		System.out.println("\n\nOrganize fk_user_id do video sql: " + SQLVideo +"\n");
		
		LinkedHashMap<String,HashMap<String,String>> records = db.query_records(SQLVideo);

		//seta fk_user_id no video
		for(String rec : records.keySet()){			
			
			HashMap<String,String> mydata = new HashMap<String,String>();
			
			String idVideo = records.get(rec).get(cs.getCOL(db, "dbcollectionvideo", "id_video"));//value da coluna do video
			String idCollection = records.get(rec).get(cs.getCOL(db, "dbcollection", "id_collection"));//value da coluna da coleçao
			String value = records.get(rec).get(cs.getCOL(db, "dbcollection", "fk_user_id"));//value da coluna da coleção
			
			String tableVideo = cs.getDATA(db, "dbcollectionvideo");
			String colId_video  = cs.getCOL(db, "dbcollectionvideo", "id_video");
			String colFk_user_id = cs.getCOL(db, "dbcollectionvideo", "fk_user_id");		
			mydata.put(colFk_user_id, value);
			
			System.out.println("Update " + tableVideo + " id_video: " + idVideo + " fk_user_id: " 
									+ value + " pertence id_collection: " + idCollection);
			
			db.update(tableVideo, colId_video, idVideo, mydata);//update na data+id usando o id da tabela
			
		}
		System.out.println("\n\n");		
		
	}
	
}