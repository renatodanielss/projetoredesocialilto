<%@ include file="config.jsp" %>
<%@ page import="HardCore.UCaccessAdministration" contentType="text/html; charset=UTF-8" %>
<%@ page import="HardCore.MenuContent" %>
<%@ page import="com.iliketo.util.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="org.apache.log4j.Logger" %>

<%
	//valida usuario adm asbru
	UCaccessAdministration.doIndex(mytext, "", mysession, myrequest, myresponse, myconfig, db);

	//JSP forumulario para criar registro da categoria/grupo de um hobby
	final Logger log = Logger.getLogger(request.getRequestURL().toString());
	log.info(request.getRequestURL());
	
	ArrayList<Category> lista = new ArrayList<Category>();
	String country = request.getParameter("country");
	if(country != null && !country.isEmpty()){		
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);				//auxiliar para o db
		String novoStatus = "";
		
		String SQL = "select * from dbcategory t1 where t1.hobby = 'yes' and t1.country = '" +country+ "';";				
		String[][] aliasSQL = { {"dbcategory", "t1"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		LinkedHashMap<String,HashMap<String,String>> records  = db.query_records(SQL);
		
		for(String rec : records.keySet()){
			Category cat = new Category();
			cat.setNameCategory(records.get(rec).get(CS.getCOL(db, "dbcategory", "name_category")));			
			lista.add(cat);
		}
	}
%>

<html>
<head>
	<title>
		Criar Hobby
	</title>
</head>
<body>
<div style="width: 100%; text-align: center;">
<div style="width: 50%; float: left;">
	<div style="text-align: left;">
		<h3>Criar categoria/grupo do Hobby</h3>
		<br>
		<br>
		<form method="post" action="/webadmin/hobby_create.jsp">
			Nome do hobby*:<br>
			<input type="text" value="" name="name_category" required="true" size="40">
			<br>
			<br>
			País*:<br>
			<select name="country" id="country" required="true">
				<option value=""></option>
				<option value="BR">Brasil - BR</option>
				<option value="US">Estados Unidos - US</option>
			</select>
			<br>
			<br>
			<input type="submit" value="Adicionar hobby"><br>
			<br>
			<br>
			<span style="color:blue">${param.info}</span>
		</form>
		<br>
		<br>
	</div>
</div>
<div style="width: 50%; float: right;">
	<div style="text-align: left;">
		<h3>Lista de hobbies por país</h3>
		<br><br>
		País*:<br>
		<select name="country" id="country" style="width: 300px;" onchange="changeListaHobbies(this)">
			<option value=""></option>
			<option value="BR">Brasil - BR</option>
			<option value="US">Estados Unidos - US</option>
		</select>
		<br><br>
		Lista das categorias dos hobbies:
		<br>
		<div style="width: 300px; height: 300px; overflow-y: scroll; overflow-x: none; background-color: #dddee0; padding-left: 2%; padding-top: 2%">
			<%for(int i=0; i < lista.size(); i++){ %>				
				<%=i+1%> - <%=lista.get(i).getNameCategory()%><br>
			<%} %>
		</div>
	</div>
</div>
</div>
</body>
<script type="text/javascript">
	function changeListaHobbies(e){
		var url = "/webadmin/hobby_add.jsp?country=" + e.options[e.selectedIndex].value;;
		window.location.href = url;
	}
</script>
</html>


<% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>