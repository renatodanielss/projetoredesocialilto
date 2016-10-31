<%@ include file="webadmin.jsp" %>
<%@ page import="com.iliketo.dao.MemberDAO" %>
<%@ page import="com.iliketo.model.Member" %>
<%@ page import="com.iliketo.dao.GenericDAO" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
<link rel="shortcut icon" href="/image/logo_aba.png"> 

<style type="text/css">
<!--
body{
	font: Ebrima;	
	margin: 0;
	padding: 0;

	overflow-x: hidden;
}

/* ~~ Seletores de elementos/tag ~~ */
ul, ol, dl { /* Devido a variações entre navegadores, é recomendado zerar o padding e a margem nas listas. É possível especificar as quantidades aqui ou nos itens da lista (LI, DT, DD) que eles contêm. Lembre-se: o que você fizer aqui ficará em cascata para a lista de navegação a não ser que você escreva outro seletor mais específico. */
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 /* ao remover a margem superior, as margens podem escapar das suas containing div. A margem inferior restante vai mantê-la afastada de qualquer elemento que se segue. */
	padding-right: 15px;
	padding-left: 15px; /* adicionando o padding para os lados dos elementos dentro dos divs, ao invés dos próprios divs o livra de qualquer combinação de modelo de caixa. Um div aninhado com padding lateral também pode ser usado como método alternativo. */
}
a img { /* esse seletor remove a borda azul padrão exibida em alguns navegadores ao redor de uma imagem circundada por um link. */
	border: none;
}

/* ~~ A estilização dos links do seu site deve permanecer nesta ordem – incluindo o grupo de seletores que criam o efeito hover. ~~ */
a:link {
	color:#414958;
	text-decoration: underline; /* a não ser que você estilize seus links para que pareçam extremamente únicos, é melhor utilizar links sublinhados para uma identificação visual mais rápida. */
}
a:visited {
	color: #4E5869;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* esse grupo de seletores dará ao navegador que estiver usando um teclado a mesma experiência de hover do que uma pessoa usando um mouse. */
	text-decoration: none;
}

/* ~ esse contêiner envolve todos os outros divs dando a eles uma largura com base em porcentagem ~~ */
.container {
	width: 80%;
	max-width: 1260px;/* uma largura máxima pode ser desejável para evitar que esse layout fique muito largo num monitor grande. Isso torna o comprimento da linha mais legível. O IE6 não concorda com essa declaração. */
	min-width: 250px;/* uma largura mínima pode ser desejável para evitar que esse layout fique muito estreito. Isso torna o comprimento da linha mais legível nas colunas laterais. O IE6 não concorda com essa declaração. */
	background-color: transparent;
	margin: 0 auto; /* o valor automático nos lados, combinado com a largura, centraliza o layout. Não é necessário definir a largura do contêiner para 100%. */
}

/* ~~o cabeçalho não tem uma largura. Ele pode ocupar toda a largura do layout. Possui um alocador de espaço de imagem que deve ser substituído pelo seu logotipo com link~~ */
.header {
	background-color:transparent;
	display: table;
}

/* ~~ Informações sobre o layout. ~~ 

1) O padding é posto somente na parte superior e inferior do div. Os elementos nesse div têm padding nos seus lados impedindo o modelo tipo caixa. Lembre-se: ao adicionar qualquer padding lateral ou  bordas no próprio div, ele será adicionado à largura que você define para criar a largura *total*. Também é necessário remover o padding no elemento do div e estabelecer um segundo div, sem largura e o padding necessário para o seu design.

*/
.content {
	padding: 10px 0;
}

/* ~~ Este seletor agrupado fornece as listas dentro do espaço da área de conteúdo ~~ */
.content ul, .content ol { 
	padding: 0 15px 15px 40px; /* esse padding espelha o padding direito nos cabeçalhos e regra de parágrafo acima. O padding foi colocado na parte inferior para obter espaço entre outros elementos das listas e à esquerda para criar o recuo. Estes podem ser ajustados como desejar. */
}

/* ~~ O rodapé ~~ */
.footer {
	padding: 10px 0;
	background-color: #6F7D94;
}

/* ~~ flutuações diversas/limpeza de classes ~~ */
.fltrt {  /* essa classe pode ser usada para flutuar um elemento à direita da página. O elemento flutuado deve preceder o elemento e ser o próximo da página. */
	float: right;
	margin-left: 8px;
}
.fltlft { /* essa classe pode ser usada para flutuar um elemento à esquerda da página. O elemento flutuado deve preceder o elemento e deve ser o próximo da página. */
	float: left;
	margin-right: 8px;
}
.clearfloat { /* essa classe pode ser colocada em um <br /> ou em um div vazio como o elemento final que segue o último div flutuado (no #contêiner) caso o rodapé seja removido ou retirado do contêiner. */
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}
#camp{
border: none;
}

#header2 {
	/*background-image: url(/image.jsp?id=619);*/
        background-image: url(/image/Rodape-v3_01.gif);
background-repeat: no-repeat;
background-size:cover;
height: 105px;
width: 100%;
	z-index: -500;
position:absolute;
}

#rodape {
	background-color:#0A065F;
height: 16px;
width:100%;
bottom:0;
color:#fff;
vertical-align:central;
margin: 0 auto;
position: fixed;
left:0;
font-size:1em;
font-family:ebrima;
font-weight:lighter;

}

#logo{ weight:auto;
display:block;
float:left;
}

.logo2{
	max-width:347px; max-height:82px; position: relative; top:9px;
}


#login
{
	display: table;
	float: right;
	top: 0px;	
	position:relative;
	left:45%;
}

#botao{

border-style: solid; 
	
	 border-color:transparent; 

		float:right;
		color:#fff;
		font-family:ebrima;
top:34px;
position:relative;


}

#botao2{

font-family: Ebrima;
font-size:14px;
color:#fff;
width: auto;
float: left;
padding-top: 2%;
padding-left:2%;
padding-right: 2%;
padding-bottom:2%;
border:0 none;
cursor: pointer;
 
background-color: #0090d9;
  font-weight: lighter;

}

#login2{
	border-style: solid; 
	weight:auto;
	width:auto;
		 border-color:transparent; 
	font-size:1.2em;
line-height:40px;
font-weight:lighter;


		float:left;
		color:#fff;
		font-family:ebrima;

			letter-spacing:2px;

}

#joinnow{
	 border-style: solid;
	 border-color:transparent; 
	display:compact;
	float:left;
	weight:auto;
	color:#bfbed0;
	font-size:0.9em;
		letter-spacing:2px;
font-family:ebrima;

}
#linked{
text-decoration:none; cursor:pointer;
	color:#bfbed0;
}

#lost{
	border-style: solid; 
	weight:auto;
	font-family:ebrima;

		 border-color:transparent; 
	font-size:0.9em;
	letter-spacing:2px;
position:relative;
		left:22%;
		top:-5px;
		color:#bfbed0;
}
.clearFull{
 display:block;
 clear:both;
}

#botao{
	float:right;
}

#framea1{
	float:left; margin-top:30px;  display:block; width:45%; font-size:2em; color:#160265; font-family: Verdana, Arial, Helvetica, sans-serif;
 font-weight:bold; 		
}
#framea2{
	float:left; margin-left:30px;  margin-top:60px;display:block; width:45%;  font-size:1.5em; color:#160265; font-family: Verdana, Arial, Helvetica, sans-serif;
bold; letter-spacing:0.5px;}
@media screen and (min-width:400px) {

  #frame0{ visibility:hidden;  display: none;}
 

}


@media screen and (max-width:338px) {

.logo2{
	 margin-left:-15% !important; float:center;
}

}



}


@media screen and (max-width:1200px) {
	

#login
{
	display: table;
	float: right;
	top: -5px;	
	position:relative;
	left:25%;
}
.logo2{
	width:260px; height:62px; top:13px;
}

	#header2 {
height: 90px;

}

	
	#framea1{
	width:90%;
}
#framea2{
	width:90%;
	margin-left:0px;
margin-top:0px;
}
  
  #frame0{ visibility:hidden; display: none;  }
	
}





@media screen and (max-width:888px) {
	

#login
{
	display: table;
	float: right;
	top: -5px;	
	position:relative;
	left:5%;
}
.logo2{
	width:260px; height:62px; top:13px;
}

	#header2 {
height: 90px;

}

	
	#framea1{
	width:90%;
}
#framea2{
	width:90%;
	margin-left:0px;
margin-top:0px;
}
  
  #frame0{ visibility:hidden; display: none;  }
	
}




@media screen and (max-width:769px) {
	


#joinnow{color:#000;}
#lost{color:#000;}
#linked{

	color:#000;
}
#login
{
	display: table;
	float: left;
	top: 23px;	
	position:relative;
	left:10%;

}
#login1{color:#000;}
#login2{color:#000;}
.logo2{
	width:260px; height:62px; top:13px; left:%25; position:relative;
float:center;
}

	#header2 {
height: 90px;

}

	
	#framea1{
	width:90%;
}
#framea2{
	width:90%;
	margin-left:0px;
margin-top:0px;
}
  
  #frame0{ visibility:hidden; display: none;  }
	
}


@media screen and (max-width:480px) {

#login
{
	position: absolute;
	left:80% !important;
}

	#login1{	
	position: absolute;
	top: 70px;
left:-190px;
margin-left:0;
color:#000;
font-size:1em;
display:block;


}

#framea1{display:none;}

#framea2{display:none;}

#botao{
		position: absolute;
left:-100px; !important
width:100px;
float:left;
	top: 205px;

}
#botao2{
		width:60px; height:30px;
		display:block;



}


	#login2{	
	position: absolute;
	top: 130px;
left:-190px;
margin-left:0;
color:#000;
font-size:1em;
font-weight:lighter;
display:block;

}
#linked{

	color:#000;
}




#joinnow{
position: absolute;
	top: 250px;
left:-190px;
margin-left:0;
color:#000;
font-size:1em;
display:block;

}
.camp{
border-style:ridge;
border-color:#000;
border:ridge;
}

#lost{
position: absolute;
	top: 280px;
left:-190px;
margin-left:0;
color:#000;
font-size:1em;
display:block;
}




.logo2{
	width:210px; height:52px; position: relative; top:9px; margin-left:auto; float:center;
}




#header2 {
height: 65px;

}


#logo{	margin-left:20%;}
	#frame0{
float: inherit; width:100%; display:block; height:300px;}

	#framea1{
float: left; width:50%; }
#framea2{
float: left; width:55%; }
}

#title1{
	font-family: Ebrima;
	font-size:20px;
	color:#fff;
	width: 90%;
	float: left;
	padding-top: 2%;
	padding-left:2%;
	padding-right: 2%;
	padding-bottom:2%;
	background-color: #0090d9;
	font-weight: lighter;
	display: block;
}

#secondbox {
	background-color: #dddee0;
	font-family: Ebrima;
	font-size:18px;
	height: auto;
	font-weight: lighter; 
	width: 94%;
	padding-top: 2%;
	padding-left:2%;
	padding-right: 2%;
	padding-bottom:2%;
}

#first{
	background-color: #fff;
	font-family: Ebrima;
	font-size:14px;
	font-weight: lighter; 
	width: 96%;
	float: left;
	margin-left: 0%;
	position: relative;
	padding-left: 3%;
	padding-top:3%;
	padding-bottom:3%;
}

#branco_separador{
	background-color: #fff;
	width: 100%;
	height: 5px;
	clear: both;
}

.backbutton1 {
	font-family: Ebrima;
	font-size:14px;
	color:#fff;
	float: left;
	height:35px !important;
	width: 150px;
	padding-left:2%;
	padding-right: 2%;
	border: none;
	outline: none;
	cursor: pointer;
	background-color: #0090d9;
	font-weight: lighter;
}

-->
</style>
</head>

<body  >  
<%
	Map<String, String> countries = new HashMap<>();
	for (String iso : Locale.getISOCountries()) {
    	Locale l = new Locale("en_US", iso);
    	countries.put(l.getDisplayCountry(), iso);
	}

	HashMap result = (HashMap) request.getAttribute("result");
	
	final Logger logger = Logger.getLogger("review.jsp");
	
	logger.info("HashMap result vindo do PAYPAL");
	for(Object key : result.keySet()){
		logger.info("Parametro nome: " + (String)key + " - valor: " + (String)result.get(key));
	}
	
	MemberDAO memberDao = new MemberDAO(db, request);
	Member member = new Member();
	member = (Member) memberDao.readByColumn("id_member", request.getSession().getAttribute("userid").toString(), Member.class);

	final Logger log = Logger.getLogger("review.jsp");
	log.info("Member Country: " + member.getCountry());
	log.info("Paypal Country: " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString());
	log.info("Member Country ISO Code: " + countries.get(member.getCountry()));
	log.info("Paypal Country = Member Country ISO Code? " + countries.get(member.getCountry()) + " = " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString());
	
	Locale l = new Locale("", result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString());
	
	if (member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("")){
		log.info("Payment Country to Update Vazio: " + l.getDisplayCountry());
		member.setPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE(l.getDisplayCountry());
		memberDao.update(member, false);
	
		//if 1
		if (!result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString().equals(countries.get(member.getCountry()))){
			log.info("if 1: " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString() + " = " + countries.get(member.getCountry()));
			//if 2
			if (member.getCountry().equals("Brazil")){
				log.info("if 2: " + member.getCountry() + " = Brazil");
				//redirect United States
				response.sendRedirect("/page.jsp?id=1149&paymentRedirect=1");
				//if 3
			} else if (result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString().equals("BR")){
				log.info("if 3: " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString() + " = BR");
				//redirect Brazil
				response.sendRedirect("/page.jsp?id=1149&paymentRedirect=1");
			}
		}
	}
	//if 4
	else if (!result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString().equals(countries.get(member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE()))){
		log.info("if 4: " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString() + " = " + countries.get(member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE()));
		//if 5
		if (result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString().equals("BR")){
			log.info("if 5: " + result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE").toString() + " = BR");
			log.info("Payment Country to Update Não Vazio: " + l.getDisplayCountry());
			
			member.setPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE(l.getDisplayCountry());
			memberDao.update(member, false);
			// redirect Brazil
			response.sendRedirect("/page.jsp?id=1149&paymentRedirect=1");
		}
		//if 6
		else if (member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE().equals("Brazil")){
			log.info("if 6: " + member.getPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE() + " = Brazil");
			log.info("Payment Country to Update Não Vazio: " + l.getDisplayCountry());
			
			member.setPAYMENTREQUEST_0_SHIPTOCOUNTRYCODE(l.getDisplayCountry());
			memberDao.update(member, false);
			//redirect United States
			response.sendRedirect("/page.jsp?id=1149&paymentRedirect=1");
		}
	}
%>

<div id="header2"> </div>
<div class="container">
  <div style="background-color:transparent;" class="header">
  <div id="logo">
  <img src="/image/logo6_transparent2.png" class="logo2"> </div>
 
    <!-- end .header --></div>
    
    <!-- if locale portuguese -->
    <% if (request.getLocale().toString().equals("pt_BR")){ %>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Confirmação de Compra</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
				<tr><td><h4 style="margin-left: -16px;">Endereço de Cobrança</h4></td></tr>
				<% //HashMap result = (HashMap) request.getAttribute("result");  %>
				<tr><td style="width: auto;">Nome: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></td></tr>
				<tr><td style="width: auto;">Endereço: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></td></tr>
				<tr><td style="width: auto;">Cidade: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></td></tr>
				<tr><td style="width: auto;">Estado: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%></td></tr>
				<tr><td style="width: auto;">País: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE")%></td></tr>
				<tr><td style="width: auto;">CEP: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr><td style="width: auto;">Produto: </td><td><%=result.get("L_PAYMENTREQUEST_0_NAME0")%></td></tr>
				<tr><td style="width: auto;">Descrição do Produto: </td><td><%=result.get("L_PAYMENTREQUEST_0_DESC0")%></td></tr>
				<tr><td style="width: auto;">Valor Total: </td><td id='amount'><%=result.get("PAYMENTREQUEST_0_AMT")%></td></tr>
				<tr><td style="width: auto;">Moeda: </td><td><%=result.get("CURRENCYCODE").equals("BRL")?"Real":""%></td></tr>
				<tr><td>&nbsp;</td></tr>
				<form action="Return?page=return" name="order_confirm" method="POST">
				<input name="PAYMENTREQUEST_0_SHIPPINGAMT" value="0.00" type="hidden"></input>
					<tr><td><input id="submitbutton" type="Submit" name="confirm" alt="Check out with PayPal" class="backbutton1" value="Confirmar Compra"></td></tr>
				</form>
			</tbody>
		</table>
	</div>
	<br>
	<div class="span3">
	</div>
	
	<!-- if locale spanish -->
	<% } else if (request.getLocale().toString().equals("es_ES")){%>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Confirmación de la Compra</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
				<tr><td><h4 style="margin-left: -16px;">Billing Address</h4></td></tr>
				<% //HashMap result = (HashMap) request.getAttribute("result");  %>
				<tr><td style="width: auto;">Name: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></td></tr>
				<tr><td style="width: auto;">Address: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></td></tr>
				<tr><td style="width: auto;">City: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></td></tr>
				<tr><td style="width: auto;">State: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%></td></tr>
				<tr><td style="width: auto;">Country: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE")%></td></tr>
				<tr><td style="width: auto;">Zip Code: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr><td style="width: auto;">Product: </td><td><%=result.get("L_PAYMENTREQUEST_0_NAME0")%></td></tr>
				<tr><td style="width: auto;">Description: </td><td><%=result.get("L_PAYMENTREQUEST_0_DESC0")%></td></tr>
				<tr><td style="width: auto;" >Total Amount: </td><td id='amount'><%=result.get("PAYMENTREQUEST_0_AMT")%></td></tr>
				<tr><td style="width: auto;">Currency: </td><td><%=result.get("CURRENCYCODE").equals("EUR")?"Euro":""%></td></tr>
				<tr><td>&nbsp;</td></tr>
				<form action="Return?page=return" name="order_confirm" method="POST">
				<input name="PAYMENTREQUEST_0_SHIPPINGAMT" value="0.00" type="hidden"></input>
					<tr><td><input id="submitbutton" type="Submit" name="confirm" alt="Check out with PayPal" class="backbutton1" value="Confirm Order"></td></tr>
				</form>
			</tbody>
		</table>
	</div>
	<br>
	<div class="span3">
	</div>	
	
	<!-- if locale english -->
	<% } else { %>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Confirmation</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
				<tr><td><h4 style="margin-left: -16px;">Billing Address</h4></td></tr>
				<% //HashMap result = (HashMap) request.getAttribute("result");  %>
				<tr><td style="width: auto;">Name: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></td></tr>
				<tr><td style="width: auto;">Address: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></td></tr>
				<tr><td style="width: auto;">City: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></td></tr>
				<tr><td style="width: auto;">State: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%></td></tr>
				<tr><td style="width: auto;">Country: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE")%></td></tr>
				<tr><td style="width: auto;">Zip Code: </td><td><%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				
				<tr><td style="width: auto;">Product: </td><td><%=result.get("L_PAYMENTREQUEST_0_NAME0")%></td></tr>
				<tr><td style="width: auto;">Description: </td><td><%=result.get("L_PAYMENTREQUEST_0_DESC0")%></td></tr>
				<tr><td style="width: auto;">Total Amount: </td><td id='amount'><%=result.get("PAYMENTREQUEST_0_AMT")%></td></tr>
				<tr><td style="width: auto;">Currency:</td><td><%=result.get("CURRENCYCODE").equals("USD")?"Dollar":""%></td></tr>
				<tr><td>&nbsp;</td></tr>
				<form action="Return?page=return" name="order_confirm" method="POST">
				<input name="PAYMENTREQUEST_0_SHIPPINGAMT" value="0.00" type="hidden"></input>
					<tr><td><input id="submitbutton" type="Submit" name="confirm" alt="Check out with PayPal" class="backbutton1" value="Confirm Order"></td></tr>
				</form>
			</tbody>
		</table>
	</div>
	<br>
	<div class="span3">
	</div>	
	<% } %>
				
</div><!-- end .container -->
  <div id="rodape" align="center" style="font-family: Ebrima; font-size: 12px;">Copyright © 2015-2016 I Like Too! All rights reserved. </div>
  </body>
</html>