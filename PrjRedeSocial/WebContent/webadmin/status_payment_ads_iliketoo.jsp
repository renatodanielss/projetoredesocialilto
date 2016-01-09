<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="com.iliketo.util.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);
%>

<%
	//
	final Logger log = Logger.getLogger("/webadmin/status_payment_ads_iliketoo.jsp");
	log.info("/webadmin/status_payment_ads_iliketoo.jsp");
	
	ColumnsSingleton CS = ColumnsSingleton.getInstance(db);				//auxiliar para o db
	ArrayList<Announce> lista = new ArrayList<Announce>();
	String novoStatus = "";
	
	String SQL = "select t1.id_announce as id_announce, t1.title as title, t1.date_created as date_created, t1.date_updated as date_updated, "
				+"t1.type_announce as type_announce, t1.price_fixed as price_fixed, t1.bid_actual as bid_actual, t1.buyer_user_id as buyer_user_id, "
				+"t1.status as status, t1.fk_user_id as fk_user_id, t1.rating as rating, t1.featured as featured "
				+ "from dbannounce as t1 order by t1.date_updated desc;";				
	String[][] aliasSQL = { {"dbannounce", "t1"} };
	SQL = CS.transformSQLReal(SQL, aliasSQL);
	LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQL);
	
	for(String rec : records.keySet()){
		Announce anuncio = new Announce();
		//anuncio.setNumPayPal(records.get(rec).get("date_created"));
		anuncio.setDateCreated(records.get(rec).get("date_created"));
		anuncio.setIdAnnounce(records.get(rec).get("id_announce"));
		anuncio.setTitle(records.get(rec).get("title"));
		anuncio.setPriceFixed(records.get(rec).get("price_fixed"));
		anuncio.setIdMember(records.get(rec).get("fk_user_id"));
		anuncio.setRating(records.get(rec).get("rating"));
		if(anuncio.getPriceFixed() == null || anuncio.getPriceFixed().equals("")){
			anuncio.setPriceFixed(records.get(rec).get("bid_actual"));
		}
		anuncio.setTypeAnnounce(records.get(rec).get("type_announce"));
		anuncio.setStatus(records.get(rec).get("status"));
		anuncio.setIdBuyer(records.get(rec).get("buyer_user_id"));
		anuncio.setFeatured(records.get(rec).get("featured"));
		lista.add(anuncio);
	}
	
%>

<html>
<head>
	<title>
		Change status ads
	</title>
</head>
<body>
	<h3>Pagina para mudar status e comprador dos anúncios!</h3>
	
	<table border="1">
	<tbody>
	<tr>
		<td>N° OS PayPal</td>
		<td>Data Anuncio</td>
		<td>ID Anuncio</td>
		<td>Titulo</td>
		<td>Id user</td>
		<td>Preço</td>
		<td>Tipo</td>
		<td>Status atual</td>
		<td>Novo status</td>
		<td>Comprador</td>
		<td>Votos</td>
		<td>Destaque</td>
	</tr>
	<%for(int i=0; i < lista.size(); i++){ %>
	<tr>
		<td>null</td>
		<td><%=lista.get(i).getDateCreated()%></td>
		<td><%=lista.get(i).getIdAnnounce()%></td>
		<td><%=lista.get(i).getTitle()%></td>
		<td><%=lista.get(i).getIdMember()%></td>
		<td><%=lista.get(i).getPriceFixed()%></td>
		<td><%=lista.get(i).getTypeAnnounce()%></td>
		<td><%=lista.get(i).getStatus()%></td>
		<td>
		<select name="status" id="status<%=i%>">
			<option value=""></option>
			<option value="For sale">For sale</option>
			<option value="For auction">For auction</option>
			<option value="Pending pay">Pending pay</option>
			<option value="Canceled">Canceled</option>
			<option value="Sold">Sold</option>
		</select>
		</td>
		<%
		int voto = 0;
		try{
			voto = (int) Double.parseDouble(lista.get(i).getRating());
		}catch(NumberFormatException e){
			//ignore
		}
		%>
		<td><input type="text" value="<%=lista.get(i).getIdBuyer()%>" id="comprador<%=i%>" size="8"></td>
		<td><input type="text" value="<%=voto%>" id="votos<%=i%>" size="6"></td>
		<td>
		<select name="destaque" id="destaque<%=i%>" style="width: 100%">
			<option value="<%=lista.get(i).getFeatured()%>"><%=lista.get(i).getFeatured()%></option>
			<option value="no">no</option>
			<option value="yes">yes</option>
		</select>
		</td>
		<td>
			<input type="button" value="Atualizar" onclick="changeStatus('<%=lista.get(i).getIdAnnounce()%>', 'status<%=i%>', 'comprador<%=i%>', 
																			'votos<%=i%>', 'destaque<%=i%>')">
		</td>
	</tr>
	<%} %>
	</tbody>
	</table>
	<br><br>
</body>
<script type="text/javascript">

function changeStatus(id, status_id, comprador_id, votos, destaque_id){
	var e = document.getElementById(status_id);
	var status = e.options[e.selectedIndex].value;
	var buyer = document.getElementById(comprador_id).value;
	var votos = document.getElementById(votos).value;
	var destaque = document.getElementById(destaque_id).value;
	if(status != ""){
	  	var url = '/webadmin/status_payment_ok.jsp?idAnnounce=' +id+ '&status=' + status + "&idBuyer=" + buyer + "&votos=" + votos + "&destaque=" + destaque;
	  	window.location.href = url;
	}else{
		alert("Favor preencher o status!");
	}
}

</script>
</html>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>