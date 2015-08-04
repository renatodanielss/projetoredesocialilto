<%@page import="com.iliketo.dao.IliketoDAO"%>
<%@page import="com.iliketo.control.TimelineController"%>
<%@page import="com.iliketo.bean.ContentILiketoJB"%>
<%@page import="com.iliketo.bean.CollectionJB"%>
<%@page import="com.iliketo.bean.ItemJB"%>
<%@page import="com.iliketo.bean.VideoJB"%>
<%@page import="com.iliketo.bean.TopicJB"%>
<%@page import="com.iliketo.bean.CommentJB"%>
<%@page import="com.iliketo.bean.EventJB"%>
<%@page import="com.iliketo.bean.AnnounceJB"%>
<%@ include file="webadmin.jsp" %><%@ page import="HardCore.UCmaintainContent" %><%@ page import="HardCore.UCmaintainData" %><%@ page import="HardCore.Data" %><%@ page import="HardCore.Page" %><%@ page import="HardCore.html" %>
<%@ page import="HardCore.UCmaintainDataILiketo" %>

<%

String timeline = myrequest.getParameter("timeline"); //solicitação timeline
System.out.println("Log - execute home.jsp - " + timeline);

//redirecionamento para /home.jsp
String servletPath = request.getServletPath();
if(servletPath.equals("/home.jsp") && (timeline == null || timeline.equals(""))){
	RequestDispatcher rq = request.getRequestDispatcher("/page.jsp?id=160");
	rq.forward(request, response);
}


TimelineController controller = new TimelineController();
ArrayList<ContentILiketoJB> listTimeline = new ArrayList<ContentILiketoJB>();

if(timeline != null && timeline.equals("news")){	//solicitação novas atualizações
	listTimeline = controller.updateTimelineNewsOlds(db, myrequest, mysession, "news");
}else if(timeline != null && timeline.equals("olds")){ //solicitação atualizações antigas
	listTimeline = controller.updateTimelineNewsOlds(db, myrequest, mysession, "olds");
}


//Ler as paginas list entry de modelo do html
Page pageEntry = null; 
UCbrowseWebsite browseWebsite = new UCbrowseWebsite(mytext);

pageEntry = browseWebsite.getPageById("665", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryCollection = pageEntry.getBody();

pageEntry = browseWebsite.getPageById("666", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryItem = pageEntry.getBody();

pageEntry = browseWebsite.getPageById("669", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryVideo = pageEntry.getBody();

pageEntry = browseWebsite.getPageById("701", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryEvent = pageEntry.getBody();

pageEntry = browseWebsite.getPageById("734", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryAnnounceAuction = pageEntry.getBody();

pageEntry = browseWebsite.getPageById("735", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
String listEntryAnnounceFixed = pageEntry.getBody();

//pageEntry = browseWebsite.getPageById("667", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
//String listEntryTopic = pageEntry.getBody();

//pageEntry = browseWebsite.getPageById("668", servletcontext, mysession, myrequest, myresponse, myconfig, db, website);
//String listEntryComment = pageEntry.getBody();


StringBuilder div = new StringBuilder();	//armazena todos os conteudos na String div para retornar do server para o ajax

if(!listTimeline.isEmpty()){
	for(ContentILiketoJB jb : listTimeline){
		
		if(jb instanceof CollectionJB){
			CollectionJB collectionJB = (CollectionJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String total_items = IliketoDAO.getValueCountDatabase(db, "id_item", "dbcollectionitem", "fk_collection_id", collectionJB.getIdCollection());	
			String s = listEntryCollection;
			s = s.replaceAll("@@@total_items@@@", total_items);
			s = s.replaceAll("@@@id_collection@@@", collectionJB.getIdCollection());
			s = s.replaceAll("@@@name_collection@@@", collectionJB.getNameCollection());
			s = s.replaceAll("@@@description_collection@@@", collectionJB.getDescription());
			s = s.replaceAll("@@@name_category@@@", collectionJB.getCategory());
			s = s.replaceAll("@@@path_photo_collection@@@", collectionJB.getPathPhoto());
			s = s.replaceAll("@@@date_created@@@", collectionJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", collectionJB.getDateUpdated());
			s = s.replaceAll("@@@nickname@@@", collectionJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", collectionJB.getMember().getPathPhoto());
			s = s.replaceAll("@@@id_member@@@", collectionJB.getMember().getIdMember());
			div.append(s);	
			
		}else if(jb instanceof ItemJB){
			ItemJB itemJB = (ItemJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String s = listEntryItem;
			s = s.replaceAll("@@@id_item@@@", itemJB.getIdItem());
			s = s.replaceAll("@@@title_item@@@", itemJB.getTitle());
			s = s.replaceAll("@@@path_photo_item@@@", itemJB.getPathPhoto());
			s = s.replaceAll("@@@date_created@@@", itemJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", itemJB.getDateUpdated());
			s = s.replaceAll("@@@id_member@@@", itemJB.getMember().getIdMember());
			s = s.replaceAll("@@@nickname@@@", itemJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", itemJB.getMember().getPathPhoto());
			div.append(s);
			
		}else if(jb instanceof VideoJB){
			VideoJB videoJB = (VideoJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String s = listEntryVideo;
			s = s.replaceAll("@@@id_video@@@", videoJB.getIdVideo());
			s = s.replaceAll("@@@title_video@@@", videoJB.getTitle());
			s = s.replaceAll("@@@path_file_video@@@", videoJB.getPathVideo());
			s = s.replaceAll("@@@date_created@@@", videoJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", videoJB.getDateUpdated());
			s = s.replaceAll("@@@id_member@@@", videoJB.getMember().getIdMember());
			s = s.replaceAll("@@@nickname@@@", videoJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", videoJB.getMember().getPathPhoto());
			div.append(s);
		
		}else if(jb instanceof EventJB){
			EventJB eventJB = (EventJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String s = listEntryEvent;
			s = s.replaceAll("@@@id_event@@@", eventJB.getIdEvent());
			s = s.replaceAll("@@@name_event@@@", eventJB.getNameEvent());
			s = s.replaceAll("@@@date_event@@@", eventJB.getDateEvent());
			s = s.replaceAll("@@@hour_event@@@", eventJB.getHourEvent());
			s = s.replaceAll("@@@details@@@", eventJB.getDetails());
			s = s.replaceAll("@@@local_event@@@", eventJB.getLocal());
			s = s.replaceAll("@@@type_event@@@", eventJB.getTypeEvent());
			s = s.replaceAll("@@@path_photo_event@@@", eventJB.getPathPhoto());
			s = s.replaceAll("@@@date_created@@@", eventJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", eventJB.getDateUpdated());
			s = s.replaceAll("@@@id_member@@@", eventJB.getMember().getIdMember());
			s = s.replaceAll("@@@nickname@@@", eventJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", eventJB.getMember().getPathPhoto());
			div.append(s);
		
		}else if(jb instanceof AnnounceJB){
			AnnounceJB announceJB = (AnnounceJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String s = "";
			if(announceJB.getTypeAnnounce().equalsIgnoreCase("Auction")){
				s = listEntryAnnounceAuction;
			}else{
				s = listEntryAnnounceFixed;
			}
			if(announceJB.getTypeAnnounce().equalsIgnoreCase("Auction")){
				s = s.replaceAll("@@@price_initial@@@", announceJB.getPriceInitial());
				s = s.replaceAll("@@@bid_actual@@@", announceJB.getBidActual());
				s = s.replaceAll("@@@lasting@@@", announceJB.getLasting());
				s = s.replaceAll("@@@total_bids@@@", announceJB.getTotalBids());				
			}else{
				s = s.replaceAll("@@@price_fixed@@@", announceJB.getPriceFixed());				
			}
			s = s.replaceAll("@@@id_announce@@@", announceJB.getIdAnnounce());
			s = s.replaceAll("@@@type_announce@@@", announceJB.getTypeAnnounce());
			s = s.replaceAll("@@@ads_title@@@", announceJB.getTitle());
			s = s.replaceAll("@@@description@@@", announceJB.getDescription());
			s = s.replaceAll("@@@name_category@@@", announceJB.getNameCategory());
			s = s.replaceAll("@@@fk_collection_id@@@", announceJB.getIdCollection());
			s = s.replaceAll("@@@fk_item_id@@@", announceJB.getIdItem());
			s = s.replaceAll("@@@fk_user_id@@@", announceJB.getIdMember());
			s = s.replaceAll("@@@fk_category_id@@@", announceJB.getIdCategory());
			s = s.replaceAll("@@@path_photo_ad@@@", announceJB.getPath_photo_ad());
			s = s.replaceAll("@@@id_member@@@", announceJB.getMember().getIdMember());
			s = s.replaceAll("@@@nickname@@@", announceJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", announceJB.getMember().getPathPhoto());
			s = s.replaceAll("@@@date_created@@@", announceJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", announceJB.getDateUpdated());
			div.append(s);
		%>
		<!-- 
		}else if(jb instanceof TopicJB){
			TopicJB topicJB = (TopicJB) jb;
			//replace o @@@ das informações da list html entry utilizando a classe javabean de modelo 
			String s = listEntryTopic;
			s = s.replaceAll("@@@id_topic@@@", topicJB.getIdTopic());
			s = s.replaceAll("@@@subject@@@", topicJB.getSubject());
			s = s.replaceAll("@@@date_created@@@", topicJB.getDateCreated());
			s = s.replaceAll("@@@date_updated@@@", topicJB.getDateUpdated());
			s = s.replaceAll("@@@nickname@@@", topicJB.getMember().getNickname());
			s = s.replaceAll("@@@path_photo_member@@@", topicJB.getMember().getPathPhoto());
			
			//monta lista do comentario do topico
			StringBuilder strComment = new StringBuilder();
			
			for(CommentJB commentJB : topicJB.getListCommentJB()){
				String l = listEntryComment;
				l = l.replaceAll("@@@id_comment@@@", commentJB.getIdComment());
				l = l.replaceAll("@@@text_comment@@@", commentJB.getTextComment());
				l = l.replaceAll("@@@date_created@@@", commentJB.getDateCreated());
				l = l.replaceAll("@@@date_updated@@@", commentJB.getDateUpdated());
				l = l.replaceAll("@@@nickname@@@", commentJB.getMember().getNickname());
				l = l.replaceAll("@@@path_photo_member@@@", commentJB.getMember().getPathPhoto());
				//adiciona na String cada comentario deste topico que foi adicionado na lista
				strComment.append(l);
			}
			//Obs Colocar no html da listEntryTopic a expressão @@@td_html_list_comment@@@ será o local que a lista de comentarios irá aparecer no 'modelo entry do topico'
			s = s.replaceAll("@@@html_list_comment@@@", strComment.toString());			
			div.append(s);
			 -->
		<%
		}
		
	}
}else{
	//if listTimeline empty
	HashMap<String,String> mapOffset = (HashMap<String,String>) request.getSession().getAttribute("mapOffset");
	if(mapOffset != null){
		if(mapOffset.get("offsetCol").equals("0") &&
				mapOffset.get("offsetItem").equals("0") &&
				mapOffset.get("offsetVideo").equals("0") &&
				mapOffset.get("offsetEvent").equals("0") &&
				mapOffset.get("offsetAd").equals("0")){
			
			div = null;
			div = new StringBuilder();
			div.append("No updates available!");
		}else{
			div.append("No more updates!");
		}				
	}else{
		div = null;
		div = new StringBuilder();
		div.append("No updates available!");
	}
}

response.setContentType("text/html"); 			//set type conteudo resposta
response.getWriter().write(div.toString());		//write conteudo div, finaliza jsp e retorna resposta para o ajax


%>

<% if (db != null) db.close(); %><% if (logdb !=
null) logdb.close(); %>
