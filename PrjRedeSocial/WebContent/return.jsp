<%@ include file="webadmin.jsp" %>
<%@ page import="com.iliketo.control.EmailController.tipoEmail" %>
<%@ page import="com.iliketo.control.EmailController" %>
<%@ page import="com.iliketo.dao.GenericDAO" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.iliketo.dao.*" %>
<%@ page import="com.iliketo.model.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
 
 
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

-->
</style>
</head>

<body  >  
  
<div id="header2"> </div>
<div class="container">
  <div style="background-color:transparent;" class="header">
  <div id="logo">
  <img src="/image/logo6_transparent2.png" class="logo2"> </div>
 
    <!-- end .header --></div>

<%
	//CONSTANTES PAYMENT STATUS
	final String CANCELED_REVERSAL = "Canceled_Reversal";
	final String COMPLETED = "Completed";
	final String DECLINED = "Declined";
	final String EXPIRED = "Expired";
	final String FILED = "Filed";
	final String IN_PROGRESS = "In-Progress";
	final String PARTIALLY_REFUNDED = "Partially_Refunded";
	final String PENDING = "Pending";
	final String PROCESSED = "Processed";
	final String REFUNDED = "Refunded";
	final String REVERSED = "Reversed";
	final String VOIDED = "Voided";
	
	//variaveis para validar checkout do produto
	boolean validarCheckoutProdutoStorage = false;
	boolean validarCheckoutProdutoAnuncio = false;
	boolean validarCheckoutProdutoEvento = false;
	boolean validarCheckoutProdutoDestaque = false;
%>

<% if (request.getLocale().toString().equals("pt_BR")){ %>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Conclusão do Pedido</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
			<tr><td>
<% if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) && request.getAttribute("payment_method").equals("credit_card") ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
<%@ page import="org.apache.log4j.Logger" %>

<%

	final Logger log = Logger.getLogger("return.jsp");
	log.info(request.getRequestURL());

%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("credit_card_first_name")%>
    				<%=result.get("credit_card_last_name")%>, Obrigado por sua compra </h4>    			
    			<h4> Detalhes do Pagamento: </h4>
    			<p><%=result.get("L_PAYMENTREQUEST_FIRSTNAME")%> <%=result.get("L_PAYMENTREQUEST_LASTNAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Produto: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Descrição do Produto: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Total Pago: <%=result.get("AMT")%> </p>
    			<p>Moeda: <%=result.get("CURRENCYCODE")%> </p>
    			<p>Status do Pagamento: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>ID da Transação: <%=result.get("TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Clique <a href='/'>aqui </a> para retornar à página inicial</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>    		
    		<%    		
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>
    		
<% } else if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("FIRSTNAME")%>
    				<%=result.get("LASTNAME")%>, Obrigado por sua compra </h4>    			
    			<h4> Detalhes do Pagamento: </h4>
    			<p><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Produto: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Descrição do Produto: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Total Pago: <%=result.get("PAYMENTINFO_0_AMT")%> </p>
    			<p>Moeda: <%=result.get("PAYMENTINFO_0_CURRENCYCODE")%> </p>
    			<p>Status do Pagamento: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>ID da Transação : <%=result.get("PAYMENTINFO_0_TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Clique <a href="/">aqui </a> para retonar à página inicial</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>    		
    		<% 
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>

<% } else { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>

<div class="hero-unit">
    			<!-- Display the Transaction Details-->
    			<h4> Houve uma falha no pagamento feito feito em sua conta. Você pode modificar sua fonte de pagamento para resolver isto e realizar a compra novamente. </h4>
    			Status do Pagamento: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%>
    			
    			
    			<h3> Clique <a href='https://www.sandbox.paypal.com/'>aqui </a> para visitar o site do PayPal.</h3> <!--Change to live PayPal site for production-->
    			</div>

<% } %>	
		</td></tr>
		</tbody>
	</table>	
</div>
<br>
	<div class="span3">
	</div>
<% } else if (request.getLocale().toString().equals("es_ES")){%>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Conclusão do Pedido</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
			<tr><td>
<% if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) && request.getAttribute("payment_method").equals("credit_card") ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
<%@ page import="org.apache.log4j.Logger" %>

<%

	final Logger log = Logger.getLogger("return.jsp");
	log.info(request.getRequestURL());

%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("credit_card_first_name")%>
    				<%=result.get("credit_card_last_name")%>, Thank you for your Order </h4>    			
    			<h4> Billing Details: </h4>
    			<p><%=result.get("L_PAYMENTREQUEST_FIRSTNAME")%> <%=result.get("L_PAYMENTREQUEST_LASTNAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Product: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Product Description: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Payment Total Amount: <%=result.get("AMT")%> </p>
    			<p>Currency: <%=result.get("CURRENCYCODE")%> </p>
    			<p>Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>Transaction ID: <%=result.get("TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Click <a href='/'>here </a> to return to Home Page</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>
    		<% 
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>
    		
<% } else if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("FIRSTNAME")%>
    				<%=result.get("LASTNAME")%> , Thank you for your Order </h4>    			
    			<h4> Billing Details: </h4>
    			<p><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Product: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Product Description: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Payment Total Amount: <%=result.get("PAYMENTINFO_0_AMT")%> </p>
    			<p>Currency: <%=result.get("PAYMENTINFO_0_CURRENCYCODE")%> </p>
    			<p>Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>Transaction ID: <%=result.get("PAYMENTINFO_0_TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Click <a href="/">here </a> to return to Home Page</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>    		
    		<% 
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>

<% } else { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>

<div class="hero-unit">
    			<!-- Display the Transaction Details-->
    			<h4> There is a Funding Failure in your account. You can modify your funding sources to fix it and make purchase later. </h4>
    			Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%>
    			
    			
    			<h3> Click <a href='https://www.sandbox.paypal.com/'>here </a> to go to PayPal site.</h3> <!--Change to live PayPal site for production-->
    			</div>

<% } %>	
		</td></tr>
		</tbody>
	</table>	
</div>
<br>
	<div class="span3">
	</div>
<% } else { %>
  <div class="content">
    <p>&nbsp;</p><br>

<div id="title1">Conclusão do Pedido</div>
<div id="branco_separador">&nbsp;</div>
		<table id="secondbox" border="0" cellpading="0" cellspacing="0">
			<tbody id="first">
			<tr><td>
<% if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) && request.getAttribute("payment_method").equals("credit_card") ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
<%@ page import="org.apache.log4j.Logger" %>

<%

	final Logger log = Logger.getLogger("return.jsp");
	log.info(request.getRequestURL());

%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("credit_card_first_name")%>
    				<%=result.get("credit_card_last_name")%> , Thank you for your Order </h4>
    			
    			<h4> Billing Details: </h4>
    			<p><%=result.get("L_PAYMENTREQUEST_FIRSTNAME")%> <%=result.get("L_PAYMENTREQUEST_LASTNAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Product: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Product Description: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Payment Total Amount: <%=result.get("AMT")%> </p>
    			<p>Currency: <%=result.get("CURRENCYCODE")%> </p>
    			<p>Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>Transaction ID: <%=result.get("TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Click <a href='/'>here </a> to return to Home Page</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>
    		<% 
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>
    		
<% } else if((request.getAttribute("ack").equals("SUCCESS") || request.getAttribute("ack").equals("SUCCESSWITHWARNING") ) ) { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>
			<span class="span4">
    		</span>
    		<span class="span5">
    			<div class="hero-unit">
    		<!-- Display the Transaction Details-->
    			<h4> <%=result.get("FIRSTNAME")%>
    				<%=result.get("LASTNAME")%> , Thank you for your Order </h4>
    			
    			<h4> Billing Details: </h4>
    			<p><%=result.get("PAYMENTREQUEST_0_SHIPTONAME")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTREET")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOCITY")%></p>
				<p><%=result.get("PAYMENTREQUEST_0_SHIPTOSTATE")%>- <%=result.get("PAYMENTREQUEST_0_SHIPTOZIP")%></p>
    			<!--  <p>Transaction Type: <%=result.get("PAYMENTINFO_0_TRANSACTIONTYPE")%> </p> -->
    			<br />
    			<p>Product: <%=result.get("L_PAYMENTREQUEST_0_NAME0")%></p>
    			<p>Product Description: <%=result.get("L_PAYMENTREQUEST_0_DESC0")%></p>
    			<p>Payment Total Amount: <%=result.get("PAYMENTINFO_0_AMT")%> </p>
    			<p>Currency: <%=result.get("PAYMENTINFO_0_CURRENCYCODE")%> </p>
    			<p>Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%> </p>
    			<p>Transaction ID: <%=result.get("PAYMENTINFO_0_TRANSACTIONID")%> </p>
    			<!--  <p>Payment Type: <%=result.get("PAYMENTINFO_0_PAYMENTTYPE")%> </p>  -->
    			<h3> Click <a href="/">here </a> to return to Home Page</h3>    		
    			</div>
    		</span>
    		<span class="span3">
    		</span>    		
    		<% 
	    		if("Storage".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto storage
	    			validarCheckoutProdutoStorage = true;
	    		}else if("Ad".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){	//valida produto anuncio
	    			validarCheckoutProdutoAnuncio = true;
	    		}else if("Event".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto evento
	    			validarCheckoutProdutoEvento = true;
	    		}else if("Featured".equals(result.get("PAYMENTREQUEST_0_CUSTOM"))){//valida produto destaque do anuncio
	    			validarCheckoutProdutoDestaque = true;
	    		}
    		%>

<% } else { 
	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
%>

<div class="hero-unit">
    			<!-- Display the Transaction Details-->
    			<h4> There is a Funding Failure in your account. You can modify your funding sources to fix it and make purchase later. </h4>
    			Payment Status: <%=result.get("PAYMENTINFO_0_PAYMENTSTATUS")%>
    			
    			
    			<h3> Click <a href='https://www.sandbox.paypal.com/'>here </a> to go to PayPal site.</h3> <!--Change to live PayPal site for production-->
    			</div>

<% } %>	
		</td></tr>
		</tbody>
	</table>	
</div>
<br>
	<div class="span3">
	</div>
<% } %>


<!-- VALIDA TIPO DE PRODUTO(Ad, Storage, Featured) DO CHECKOUT, VALIDA O PAYMENT_STATUS E ATUALIZA INFORMACOES NO BD -->
<%
	final Logger log = Logger.getLogger("return.jsp");   	
    					
    if(validarCheckoutProdutoStorage){
    	HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
    	MemberDAO memberDao = new MemberDAO(db, request);
		Member member = new Member();
		member = (Member) memberDao.readByColumn("username", mysession.get("username"), Member.class);
		
		log.info("Consultar Membro na base :" + member.toString() + " e " + member.getNickname());
		if(member != null){
			if(result.get("PAYMENTINFO_0_PAYMENTSTATUS").equals("Completed")){
				log.info("Antes do if:");
				log.info("mysession.get(username): " + mysession.get("username"));
				log.info("Username:" + member.getUsername());
				
				if (result.get("L_PAYMENTREQUEST_0_NAME0").equals("Conta Prata - 1 GB")){
					member.setTotalSpace("1073741824");
					member.setStorageType(result.get("L_PAYMENTREQUEST_0_NAME0"));
					log.info("Entrou " + result.get("L_PAYMENTREQUEST_0_NAME0"));
				}
				else if (result.get("L_PAYMENTREQUEST_0_NAME0").equals("Conta Ouro - 10 GB")){
					member.setTotalSpace("10737418240");
					member.setStorageType(result.get("L_PAYMENTREQUEST_0_NAME0"));
					log.info("Entrou " + result.get("L_PAYMENTREQUEST_0_NAME0"));
				}
				else if (result.get("L_PAYMENTREQUEST_0_NAME0").equals("Conta Platina - Ilimitada")){
					member.setTotalSpace("0");
					member.setStorageType(result.get("L_PAYMENTREQUEST_0_NAME0"));
					log.info("Entrou " + result.get("L_PAYMENTREQUEST_0_NAME0"));
				}
			}
			member.setPaymentStatus(result.get("PAYMENTINFO_0_PAYMENTSTATUS"));
			log.info("Setar paymentStatus: " + result.get("PAYMENTINFO_0_PAYMENTSTATUS"));
			
			memberDao.update(member, false);
			log.info("Update Concluído");
			
			EmailController email = new EmailController(tipoEmail.EMAIL_ANUNCIO);
			email.enviaEmailPagamentoStorage(member, result, request.getLocale().toString(), request, true);
		}
    }else if (validarCheckoutProdutoAnuncio){
		HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
	   	if(result != null){
	   		String paymentStatus = result.get("PAYMENTINFO_0_PAYMENTSTATUS");
			log.info("Comprovante operacao pagamento Pay Pal - Produto Anuncio");
			log.info("Comprovante operacao pagamento Pay Pal - PAYMENTINFO_0_PAYMENTSTATUS: " + paymentStatus);
			log.info("Comprovante operacao pagamento Pay Pal - NOSHIPPING: " + result.get("NOSHIPPING"));
			Announce anuncio = (Announce)session.getAttribute("anuncioCheckout");
			if(anuncio != null){
				anuncio.setPaymentStatus(paymentStatus);
				if(paymentStatus.equals(COMPLETED)){
					anuncio.setStatus("For sale");
				}			
				AnnounceDAO anuncioDAO = new AnnounceDAO(db, request);
				anuncioDAO.update(anuncio, false);
				session.removeAttribute("anuncioCheckout");
				log.info("Comprovante operacao pagamento Pay Pal - Anuncio atualizado id: " + anuncio.getIdAnnounce());
			}
	   	}
   	}else if (validarCheckoutProdutoEvento){
		HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
	   	if(result != null){
	   		String paymentStatus = result.get("PAYMENTINFO_0_PAYMENTSTATUS");
			log.info("Comprovante operacao pagamento Pay Pal - Produto Anuncio de Evento");
			log.info("Comprovante operacao pagamento Pay Pal - PAYMENTINFO_0_PAYMENTSTATUS: " + paymentStatus);
			Event evento = (Event)session.getAttribute("eventoCheckout");
			if(evento != null){
				evento.setPaymentStatus(paymentStatus);
				EventDAO eventoDAO = new EventDAO(db, request);
				eventoDAO.update(evento, false);
				session.removeAttribute("eventoCheckout");
				log.info("Comprovante operacao pagamento Pay Pal - Produto Anuncio de Evento atualizado id: " + evento.getIdEvent());
			}
	   	}
   	}else if(validarCheckoutProdutoDestaque){
   		HashMap<String,String> result = (HashMap<String,String>) request.getAttribute("result");
	   	if(result != null){
	   		String paymentStatus = result.get("PAYMENTINFO_0_PAYMENTSTATUS");
			log.info("Comprovante operacao pagamento Pay Pal - Produto Destaque de Anuncio");
			log.info("Comprovante operacao pagamento Pay Pal - PAYMENTINFO_0_PAYMENTSTATUS: " + paymentStatus);
			Announce anuncio = (Announce)session.getAttribute("anuncioDestaqueCheckout");
			if(anuncio != null){
				anuncio.setPaymentStatusDestaque(paymentStatus);
				if(paymentStatus.equals(COMPLETED)){
					anuncio.setFeatured("yes");
				}	
				new AnnounceDAO(db, request).update(anuncio, false);
				session.removeAttribute("anuncioDestaqueCheckout");
				log.info("Comprovante operacao pagamento Pay Pal - Compra de Destaque de Anuncio atualizado id: " + anuncio.getIdAnnounce());
			}
	   	}
   	}
%>


</div><!-- end .container -->
  <div id="rodape" align="center" style="font-family: Ebrima; font-size: 12px;">Copyright © 2015-2016 I Like Too! All rights reserved. </div></body></html>
		