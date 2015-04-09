package com.iliketo.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import HardCore.DB;
import HardCore.Request;
import HardCore.Session;

import com.iliketo.bean.CollectionJB;
import com.iliketo.bean.CommentJB;
import com.iliketo.bean.ContentILiketoJB;
import com.iliketo.bean.ItemJB;
import com.iliketo.bean.MemberJB;
import com.iliketo.bean.TopicJB;
import com.iliketo.bean.VideoJB;
import com.iliketo.util.ColumsSingleton;

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
	 *Conteudos timeline update news
	 *Minhas colecoes e todas colecoes das categorias/grupos que pertenco
	 *Todos itens das colecoes relacionadas
	 *Todos videos das colecoes relacionadas
	 *Todos os eventos das categorias/grupos que pertenco
	 *Todos topicos dos forums que pertenco
	 *Todos comentarios dos forums que pertenco
	 * 
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @return
	 */
	public ArrayList<ContentILiketoJB> updateTimelineNews(DB db, Request myrequest, Session mysession){
		
		//executar uma vez para organizar o fk_user_id do item e retirar este método
		organizeFk_user_idItem(db);
		
		ArrayList<ContentILiketoJB> listTimeline = new ArrayList<ContentILiketoJB>();
		ColumsSingleton CS = ColumsSingleton.getInstance(db);
		
		System.out.println("***INIT TIMELINE NEWS***");

		//informações my user
		String myUserid = mysession.get("userid");
		
		//Comando sql para item
		String SQLCollection = 
				  "select c1.id_collection as id_collection, c1.name_collection as name_collection, c1.name_category as name_category, c1.path_photo_collection as path_photo_collection, "
				  + "c1.date_created as date_created, c1.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbcollection c1 join dbmembers m on c1.fk_user_id = m.id_member "
				+ "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id ='" +myUserid+ "') "
				+ "order by c1.date_updated desc limit 2;";
		
		System.out.println("SQLCollection Antes: " + SQLCollection);		
		String[][] aliasCollection = { {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };		
		SQLCollection = CS.transformSQLReal(SQLCollection, aliasCollection);
		System.out.println("SQLCollection: " + SQLCollection);
		
		//Comando sql para item
		String SQLItem = 
				  "select i.id_item as id_item, i.title_item as title_item, i.path_photo_item as path_photo_item, "
				  + "i.date_created as date_created, i.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbcollectionitem i join dbmembers m on i.fk_user_id = m.id_member "
				+ "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "' and i.fk_collection_id = c1.id_collection)) "
				+ "order by i.date_updated desc limit 2;";
		
		System.out.println("SQLItemTeste: " + SQLItem);
		String[][] aliasItem = { {"dbcollectionitem", "i"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };
		SQLItem = CS.transformSQLReal(SQLItem, aliasItem);
		System.out.println("SQLItem: " + SQLItem);
		
		//Comando sql para video
		String SQLVideo =
				  "select v.id_video as id_video, v.title_video as title_video, v.path_file_video as path_file_video, "
				  + "v.date_created as date_created, v.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbcollectionvideo v join dbmembers m on v.fk_user_id = m.id_member "
				+ "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "' and v.fk_collection_id = c1.id_collection)) "
				+ "order by v.date_updated desc limit 2;";
		
		System.out.println("SQLItemTeste: " + SQLVideo);
		String[][] aliasVideo = { {"dbcollectionvideo", "v"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };
		SQLVideo = CS.transformSQLReal(SQLVideo, aliasVideo);
		System.out.println("SQLItem: " + SQLVideo);
		
		//Comando sql para topic
		String SQLTopic = 
				  "select t.id_topic as id_topic, t.subject as subject, t.date_created as date_created, t.date_udpated as date_updated, "
				  + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbforumtopic t join dbmembers m on t.fk_user_id = m.id_member "
				+ "where exists (select f.id_forum from dbforum f "
				+ "where exists (select g.id_group from dbgroup g "
				+ "where exists (select c.id_collection from dbcollection c "
				+ "where g.fk_category_id = c.fk_category_id and c.fk_user_id = '" +myUserid+ "' and f.fk_group_id = g.id_group "
				+ "and t.fk_forum_id = f.id_forum))) order by t.date_updated desc limit 2;";
		
		System.out.println("SQLItemTeste: " + SQLTopic);
		String[][] aliasTopic = { {"dbforumtopic", "t"}, {"dbmembers", "m"}, {"dbforum", "f"}, {"dbgroup", "g"}, {"dbcollection", "c1"} };
		SQLTopic = CS.transformSQLReal(SQLTopic, aliasTopic);
		System.out.println("SQLItem: " + SQLTopic);
		
		//Comando sql para comment
		String SQLComment = 
				  "select com.id_comment as id_comment, com.text_comment as text_comment, com.date_created as date_created, com.date_udpated as date_updated, "
				  + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				+ "from dbforumcomment com join dbmembers m on com.fk_user_id = m.id_member "
				+ "where exists (select t.id_topic from dbtopic t "
				+ "where exists (select f.id_forum from dbforum f "
				+ "where exists (select g.id_group from dbgroup g "
				+ "where exists (select c.id_collection from dbcollection c "
				+ "where g.fk_category_id = c.fk_category_id and c.fk_user_id = '" +myUserid+ "' and f.fk_group_id = g.id_group "
				+ "and t.fk_forum_id = f.id_forum and com.fk_topic_id = t.id_topic))) order by t.date_updated desc limit 2;";
		
		System.out.println("SQLItemTeste: " + SQLComment);
		String[][] aliasComment = { {"dbforumcomment", "com"}, {"dbforumtopic", "t"}, {"dbmembers", "m"}, {"dbforum", "f"}, {"dbgroup", "g"}, {"dbcollection", "c1"} };
		SQLComment = CS.transformSQLReal(SQLComment, aliasComment);
		System.out.println("SQLItem: " + SQLComment);
		
		//Comando sql para event
		String SQLEvent = "";
		
		
		//Chama classe DB.java para executar a query no banco de dados e retorna um HashMap com todos registros encontrados
		LinkedHashMap<String,HashMap<String,String>> recordsCollections  = db.query_records(SQLCollection); //map de registros collections
		LinkedHashMap<String,HashMap<String,String>> recordsItems  = db.query_records(SQLItem); //map de registros items
		LinkedHashMap<String,HashMap<String,String>> recordsVideos  = db.query_records(SQLVideo); //map de registros videos
		LinkedHashMap<String,HashMap<String,String>> recordsTopic  = db.query_records(SQLTopic); //map de registros topicos
		LinkedHashMap<String,HashMap<String,String>> recordsComment  = db.query_records(SQLComment); //map de registros comments

		
		//Popula conteudo ILiketo nas classes javabean e adiciona na listTimeline
		System.out.println("\nColleções:");
		for(String rec : recordsCollections.keySet()){			
			CollectionJB collectionJB = new CollectionJB(); //coleção
			MemberJB memberJB = new MemberJB();				//membro dono da coleção
			
			collectionJB.setIdCollection(recordsCollections.get(rec).get("id_collection"));
			collectionJB.setNameCollection(recordsCollections.get(rec).get("name_collection"));
			collectionJB.setCategory(recordsCollections.get(rec).get("name_category"));
			collectionJB.setDateCreated(recordsCollections.get(rec).get("date_created"));
			collectionJB.setDateUpdated(recordsCollections.get(rec).get("date_updated"));
			collectionJB.setPathPhoto(recordsCollections.get(rec).get("path_photo_collection"));
					
			memberJB.setIdMember(recordsCollections.get(rec).get("id_member"));
			memberJB.setNickname(recordsCollections.get(rec).get("nickname"));
			memberJB.setPathPhoto(recordsCollections.get(rec).get("path_photo_member"));
			collectionJB.setMember(memberJB);	//seta informações do membro na coleção
			
			listTimeline.add(collectionJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsCollections.get(rec));
		}
		
		System.out.println("\nItems:");
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
		
		System.out.println("\nVideos:");
		for(String rec : recordsVideos.keySet()){			
			VideoJB videoJB = new VideoJB();		//video
			MemberJB memberJB = new MemberJB();		//membro dono do video
			
			videoJB.setIdVideo(recordsVideos.get(rec).get(("id_video")));
			videoJB.setTitle(recordsVideos.get(rec).get(("title_video")));
			videoJB.setDateCreated(recordsVideos.get(rec).get(("date_created")));
			videoJB.setDateUpdated(recordsVideos.get(rec).get(("date_updated")));
			videoJB.setPathVideo(recordsVideos.get(rec).get(("path_file_video")));
			
			memberJB.setIdMember(recordsVideos.get(rec).get(("id_member")));
			memberJB.setNickname(recordsVideos.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsVideos.get(rec).get(("path_photo_member")));
			videoJB.setMember(memberJB);	//seta informações do membro no video
			
			listTimeline.add(videoJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsVideos.get(rec));
		}
		
		System.out.println("\nTopicos:");
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
			
			listTimeline.add(topicJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsTopic.get(rec));
		}
		
		System.out.println("\nComentários:");
		for(String rec : recordsComment.keySet()){			
			CommentJB commentJB = new CommentJB();	//Comentario
			MemberJB memberJB = new MemberJB();		//membro dono do comentario
			
			commentJB.setIdComment(recordsComment.get(rec).get(("id_comment")));
			commentJB.setTextComment(recordsComment.get(rec).get(("text_comment")));
			commentJB.setDateCreated(recordsComment.get(rec).get(("date_created")));
			commentJB.setDateUpdated(recordsComment.get(rec).get(("date_updated")));
			
			memberJB.setIdMember(recordsComment.get(rec).get(("id_member")));
			memberJB.setNickname(recordsComment.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsComment.get(rec).get(("path_photo_member")));
			commentJB.setMember(memberJB);	//seta informações do membro no comentario
			
			listTimeline.add(commentJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsComment.get(rec));
		}
		
		//offset para controle de paginação da timeline de registros antigos
		//seta total de conteudos recuperados do banco no timelineOffset para controle de paginação de registros antigos
		int totalRecords = recordsCollections.size() + recordsItems.size() + recordsVideos.size()
				+ recordsTopic.size() + recordsComment.size();
		mysession.set("timelineOffset", Integer.toString(totalRecords)); 
		
		//ordena a lista de objetos pela data_updated
		Collections.sort(listTimeline);
				
		System.out.println("***END TIMELINE NEWS***");
		
		return listTimeline;
		
	}
	
	
	/**
	 *Conteudos timeline update olds
	 *Minhas colecoes e todas colecoes das categorias/grupos que pertenco
	 *Todos itens das colecoes relacionadas
	 *Todos videos das colecoes relacionadas
	 *Todos os eventos das categorias/grupos que pertenco
	 *Todos topicos dos forums que pertenco
	 *Todos comentarios dos forums que pertenco
	 * 
	 * @param db
	 * @param myrequest
	 * @param mysession
	 * @return
	 */
	public ArrayList<ContentILiketoJB> updateTimelineOlds(DB db, Request myrequest, Session mysession){
		
		ArrayList<ContentILiketoJB> listTimeline = new ArrayList<ContentILiketoJB>();
		ColumsSingleton CS = ColumsSingleton.getInstance(db);
		
		//offset para controle de paginação da timeline de registros antigos
		String totalOffset = (String) mysession.get("timelineOffset");
		
		System.out.println("***INIT TIMELINE OLDS***");

		//informações my user
		String myUserid = mysession.get("userid");
		
		//Comando sql para item
		String SQLCollection = 
				  "select c1.id_collection as id_collection, c1.name_collection as name_collection, c1.name_category as name_category, c1.path_photo_collection as path_photo_collection, "
				 + "c1.date_created as date_created, c1.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				 + "from dbcollection c1 join dbmembers m on c1.fk_user_id = m.id_member "
				 + "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id ='" +myUserid+ "') "
				+ "order by c1.date_updated desc limit 2 offset '" +totalOffset+ "';";
		
		System.out.println("SQLCollection Antes: " + SQLCollection);		
		String[][] aliasCollection = { {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };		
		SQLCollection = CS.transformSQLReal(SQLCollection, aliasCollection);
		System.out.println("SQLCollection: " + SQLCollection);
		
		//Comando sql para item
		String SQLItem = 
				  "select i.id_item as id_item, i.title_item as title_item, i.path_photo_item as path_photo_item, "
				  + "i.date_created as date_created, i.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				  + "from dbcollectionitem i join dbmembers m on i.fk_user_id = m.id_member "
				  + "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "' and i.fk_collection_id = c1.id_collection)) "
				+ "order by i.date_updated desc limit 2 offset '" +totalOffset+ "';";
		
		System.out.println("SQLItemTeste: " + SQLItem);
		String[][] aliasItem = { {"dbcollectionitem", "i"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };
		SQLItem = CS.transformSQLReal(SQLItem, aliasItem);
		System.out.println("SQLItem: " + SQLItem);
		
		//Comando sql para video
		String SQLVideo =
				  "select v.id_video as id_video, v.title_video as title_video, v.path_file_video as path_file_video, "
				  + "v.date_created as date_created, v.date_updated as date_updated, m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				  + "from dbcollectionvideo v join dbmembers m on v.fk_user_id = m.id_member "
				  + "where exists (select c1.id_collection from dbcollection c1 "
				+ "where exists (select c2.fk_category_id from dbcollection c2 "
				+ "where c1.fk_category_id = c2.fk_category_id and c2.fk_user_id = '" +myUserid+ "' and v.fk_collection_id = c1.id_collection)) "
				+ "order by v.date_updated desc limit 2 offset '" +totalOffset+ "';";
		
		System.out.println("SQLItemTeste: " + SQLVideo);
		String[][] aliasVideo = { {"dbcollectionvideo", "v"}, {"dbmembers", "m"}, {"dbcollection", "c1"}, {"dbcollection", "c2"} };
		SQLVideo = CS.transformSQLReal(SQLVideo, aliasVideo);
		System.out.println("SQLItem: " + SQLVideo);
		
		//Comando sql para topic
		String SQLTopic = 
				  "select t.id_topic as id_topic, t.subject as subject, t.date_created as date_created, t.date_udpated as date_updated, "
				 + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				 + "from dbforumtopic t join dbmembers m on t.fk_user_id = m.id_member "
				 + "where exists (select f.id_forum from dbforum f "
				+ "where exists (select g.id_group from dbgroup g "
				+ "where exists (select c.id_collection from dbcollection c "
				+ "where g.fk_category_id = c.fk_category_id and c.fk_user_id = '" +myUserid+ "' and f.fk_group_id = g.id_group "
				+ "and t.fk_forum_id = f.id_forum))) order by t.date_updated desc limit 2 offset '" +totalOffset+ "';";
		
		System.out.println("SQLItemTeste: " + SQLTopic);
		String[][] aliasTopic = { {"dbforumtopic", "t"}, {"dbmembers", "m"}, {"dbforum", "f"}, {"dbgroup", "g"}, {"dbcollection", "c1"} };
		SQLTopic = CS.transformSQLReal(SQLTopic, aliasTopic);
		System.out.println("SQLItem: " + SQLTopic);
		
		//Comando sql para comment
		String SQLComment = 
				  "select com.id_comment as id_comment, com.text_comment as text_comment, com.date_created as date_created, com.date_udpated as date_updated, "
				 + "m.id_member as id_member, m.nickname as nickname, m.path_photo_member as path_photo_member "
				 + "from dbforumcomment com join dbmembers m on com.fk_user_id = m.id_member "
				 + "where exists (select t.id_topic from dbtopic t "
				+ "where exists (select f.id_forum from dbforum f "
				+ "where exists (select g.id_group from dbgroup g "
				+ "where exists (select c.id_collection from dbcollection c "
				+ "where g.fk_category_id = c.fk_category_id and c.fk_user_id = '" +myUserid+ "' and f.fk_group_id = g.id_group "
				+ "and t.fk_forum_id = f.id_forum and com.fk_topic_id = t.id_topic))) order by t.date_updated desc limit 2 offset '" +totalOffset+ "';";
		
		System.out.println("SQLItemTeste: " + SQLComment);
		String[][] aliasComment = { {"dbforumcomment", "com"}, {"dbforumtopic", "t"}, {"dbmembers", "m"}, {"dbforum", "f"}, {"dbgroup", "g"}, {"dbcollection", "c1"} };
		SQLComment = CS.transformSQLReal(SQLComment, aliasComment);
		System.out.println("SQLItem: " + SQLComment);
		
		//Comando sql para event
		String SQLEvent = "";
		
		
		//Chama classe DB.java para executar a query no banco de dados e retorna um HashMap com todos registros encontrados
		LinkedHashMap<String,HashMap<String,String>> recordsCollections  = db.query_records(SQLCollection); //map de registros collections
		LinkedHashMap<String,HashMap<String,String>> recordsItems  = db.query_records(SQLItem); //map de registros items
		LinkedHashMap<String,HashMap<String,String>> recordsVideos  = db.query_records(SQLVideo); //map de registros videos
		LinkedHashMap<String,HashMap<String,String>> recordsTopic  = db.query_records(SQLTopic); //map de registros topicos
		LinkedHashMap<String,HashMap<String,String>> recordsComment  = db.query_records(SQLComment); //map de registros comments
		
		//Popula conteudo ILiketo nas classes javabean e adiciona na listTimeline
		System.out.println("\nColleções:");
		for(String rec : recordsCollections.keySet()){			
			CollectionJB collectionJB = new CollectionJB(); //coleção
			MemberJB memberJB = new MemberJB();				//membro dono da coleção
			
			collectionJB.setIdCollection(recordsCollections.get(rec).get("id_collection"));
			collectionJB.setNameCollection(recordsCollections.get(rec).get("name_collection"));
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
		
		System.out.println("\nItems:");
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
		
		System.out.println("\nVideos:");
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
		
		System.out.println("\nTopicos:");
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
			
			listTimeline.add(topicJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsTopic.get(rec));
		}
		
		System.out.println("\nComentários:");
		for(String rec : recordsComment.keySet()){			
			CommentJB commentJB = new CommentJB();	//Comentario
			MemberJB memberJB = new MemberJB();		//membro dono do comentario
			
			commentJB.setIdComment(recordsComment.get(rec).get("id_comment"));
			commentJB.setTextComment(recordsComment.get(rec).get("text_comment"));
			commentJB.setDateCreated(recordsComment.get(rec).get("date_created"));
			commentJB.setDateUpdated(recordsComment.get(rec).get("date_updated"));
			
			memberJB.setIdMember(recordsComment.get(rec).get(("id_member")));
			memberJB.setNickname(recordsComment.get(rec).get(("nickname")));
			memberJB.setPathPhoto(recordsComment.get(rec).get(("path_photo_member")));
			commentJB.setMember(memberJB);	//seta informações do membro no comentario
			
			listTimeline.add(commentJB);
			System.out.println("row:" + rec+ " - column/values: " + recordsComment.get(rec));
		}
		
		//offset para controle de paginação da timeline de registros antigos
		//seta total de conteudos recuperados do banco no timelineOffset para controle de paginação de registros antigos
		int totalRecords = recordsCollections.size() + recordsItems.size() + recordsVideos.size()
				+ recordsTopic.size() + recordsComment.size();
		
		int totalTimeActual = Integer.parseInt(mysession.get("timelineOffset")); //total de conteudos antigos já paginados		
		mysession.set("timelineOffset", Integer.toString(totalRecords + totalTimeActual)); //seta novo valor de offset
		
		//ordena a lista de objetos pela data_updated
		Collections.sort(listTimeline);
				
		System.out.println("***END TIMELINE OLDS***");
		
		return listTimeline;
		
	}
	
	
	//método só para testes e organização da nova coluna fk_user_id da dbcollectionitem 
	private void organizeFk_user_idItem(DB db){
		
		//recupera todos itens
		String[][] tableAlias = { {"dbcollectionitem", "i"}, {"dbcollection", "c"}  };
		
		String SQLItem = "select i.id_item, c.fk_user_id, c.id_collection from dbcollectionitem i "
				+ "join dbcollection c on i.fk_collection_id = c.id_collection";
		
		ColumsSingleton cs = ColumsSingleton.getInstance(db);
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

}
