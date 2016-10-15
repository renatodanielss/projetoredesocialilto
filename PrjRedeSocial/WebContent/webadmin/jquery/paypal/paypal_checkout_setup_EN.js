
//CONFIGURACOES CHECKOUT PAYPAL - IDIOMA EN

window.paypalCheckoutReady = function () {
	var shipToCountryCode = "@@@include:database=dbmembers:id_member=@@@user_id@@@:PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE@@@";
	var country = "@@@include:database=dbmembers:id_member=@@@user_id@@@:country@@@";
	var email = "";
	if(shipToCountryCode == ""){
		if(country == "Brazil"){
			email = '${MSG:EMAIL_PAYPAL_CHECKOUT_SETUP_BR}';	//msgs.properties
		}else{
			email = '${MSG:EMAIL_PAYPAL_CHECKOUT_SETUP_US}';
		}
	}else{
		if(shipToCountryCode == "Brazil"){
			email = '${MSG:EMAIL_PAYPAL_CHECKOUT_SETUP_BR}';
		}else{
			email = '${MSG:EMAIL_PAYPAL_CHECKOUT_SETUP_US}';
		}
	}
	paypal.checkout.setup(email, {
		container: 'myContainer', //{String|HTMLElement|Array} where you want the PayPal button to reside
		environment: '${MSG:PAYPAL_CHECKOUT_ENVIRONMENT}' //'sandbox' or 'production' depending on your environment
	});
};