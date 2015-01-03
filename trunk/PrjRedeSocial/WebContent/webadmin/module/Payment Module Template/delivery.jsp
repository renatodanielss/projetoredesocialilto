<%@ include file="../../config.jsp" %>
<%@ page import="HardCore.Common" %>
<%@ page import="HardCore.Currency" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.UCbrowseWebsite" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.Date" %>
<%



// Order order = new Order(); order.read(db, ORDER_ID);



// Note: All order and orderitem attributes are returned as String data

// order.getId()							// unique order id in the web content management system

// order.getCreated()							// order created date/time - YYYY-MM-DD hh:mm:ss
// order.getCreatedBy()							// order created by web content management system username
// order.getUpdated()							// order last updated date/time - YYYY-MM-DD hh:mm:ss
// order.getUpdatedBy()							// order last updated by web content management system username
// order.getPaid()							// order paid date/time - YYYY-MM-DD hh:mm:ss
// order.getPublished()							// order closed date/time - YYYY-MM-DD hh:mm:ss
// order.getPublishedBy()						// order closed by web content management system username

// order.getStatus()							// order administration workflow state
// order.getStatusBy()							// order administration workflow state by web content management system administrator username
// order.getCheckedOut()						// order checked out by / assigned to web content management system administrator username
// order.getRevision()							// order notes

// order.getOrderCurrency()						// order currency symbol
//   Currency currency = new Currency();
//   currency.readSymbol(db, order.getOrderCurrency());
//   currency.getTitle()						// order currency name

// order.getOrderQuantity()						// order quantity

// order.getOrderSubtotal() + order.getDiscountTotal()			// order gross total before discounts, tax and shipping
// order.getDiscountDescription()					// order discounts description
// order.getDiscountTotal()						// order discounts total amount
// order.getOrderSubtotal()						// order subtotal after discounts, before tax and shipping
// order.getTaxDescription()						// order tax description
// order.getTaxTotal()							// order tax total amount
// order.getShippingDescription()					// order shipping description
// order.getShippingTotal()						// order shipping total amount
// order.getOrderTotal()						// order net total amount after discounts, tax and shipping

// order.getCardType()							// customer payment card type
// order.getCardNumber()						// customer payment card number
// order.getCardIssuedMonth()						// customer payment card issued month
// order.getCardIssuedYear()						// customer payment card issued year
// order.getCardExpiryMonth()						// customer payment card expiry month
// order.getCardExpiryYear()						// customer payment card expiry year
// order.getCardName()							// customer payment card name on card
// order.getCardPostalcode()						// customer payment card postal code
// order.getCardCVC()							// customer payment card cvc code/number
// order.getCardIssue()							// customer payment card issue code/number

// order.getInvoiceName()						// customer invoice address name
// order.getInvoiceOrganisation()					// customer invoice address organisation
// order.getInvoiceAddress()						// customer invoice address street address
// order.getInvoicePostalcode()						// customer invoice address postal code
// order.getInvoiceCity()						// customer invoice address city
// order.getInvoiceState()						// customer invoice address county/state
// order.getInvoiceCountry()						// customer invoice address country
// order.getInvoicePhone()						// customer invoice address phone number
// order.getInvoiceFax()						// customer invoice address fax number
// order.getInvoiceEmail()						// customer invoice email address
// order.getInvoiceWebsite()						// customer invoice website address

// order.getDeliveryName()						// customer delivery address name
// order.getDeliveryOrganisation()					// customer delivery address organisation
// order.getDeliveryAddress()						// customer delivery address street address
// order.getDeliveryPostalcode()					// customer delivery address postal code
// order.getDeliveryCity()						// customer delivery address city
// order.getDeliveryState()						// customer delivery address county/state
// order.getDeliveryCountry()						// customer delivery address country
// order.getDeliveryPhone()						// customer delivery address phone number
// order.getDeliveryFax()						// customer delivery address fax number
// order.getDeliveryEmail()						// customer delivery email address
// order.getDeliveryWebsite()						// customer delivery website address

//while (order.orderitems(db)) {					// for each order item

// order.getOrderitem().getProductId()					// unique product id in the web content management system

// order.getOrderitem().getTitle()					// order item product name
// order.getOrderitem().getProductCode()				// order item product code
// order.getOrderitem().getProductCurrency()				// order item product currency
// order.getOrderitem().getProductPrice()				// order item product price
// order.getOrderitem().getProductPeriod()				// order item product subscription period [daily|weekly|biweekly|monthly|bimonthly|quarterly|biquarterly|yearly]

// order.getOrderitem().getItemQuantity()				// order item product quantity
// order.getOrderitem().getItemSubtotal()				// order item gross total before discounts, tax and shipping
// order.getOrderitem().getDiscountDescription()			// order item discounts description
// order.getOrderitem().getDiscountTotal()				// order item discounts total amount
// order.getOrderitem().getItemTotal()					// order item subtotal after discounts, before tax and shipping
// order.getOrderitem().getTaxDescription()				// order item tax description
// order.getOrderitem().getTaxTotal()					// order item tax total amount
// order.getOrderitem().getShippingDescription()			// order item shipping description
// order.getOrderitem().getShippingTotal()				// order item shipping total amount
// order.getOrderitem().getItemTotal() + tax total + shipping total	// order item net total amount after discounts, tax and shipping

// order.getOrderitem().getProductWeight()				// order item product weight
// order.getOrderitem().getProductVolume()				// order item product volume
// order.getOrderitem().getProductWidth()				// order item product width
// order.getOrderitem().getProductHeight()				// order item product height
// order.getOrderitem().getProductDepth()				// order item product depth
// order.getOrderitem().getProductBrand()				// order item product brand
// order.getOrderitem().getProductColour()				// order item product colour
// order.getOrderitem().getProductSize()				// order item product size

//}



////// validate and handle the payment notification from the payment service provider for this order



////// if the payment succeeded you can/should set the order paid date/time - YYYY-MM-DD hh:mm:ss
// order.setPaid(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

////// if the web content management system order administration workflow functionality is used you can place the new order in the relevant workflow state depending on payment success/failure etc.
// if (PAYMENT SUCCEEDED) {
//   order.setStatus("ONE OF YOUR CONFIGURED ORDER ADMINISTRATION WORKFLOW STATES");
// } else if (PAYMENT FAILED) {
//   order.setStatus("ANOTHER ONE OF YOUR CONFIGURED ORDER ADMINISTRATION WORKFLOW STATES");
// }

////// if any order details for some reason such as for example clearing the payment card cvc code then the order data listed above can be changed
// order.setCardCVC("");

////// save the updated order paid date/time and workflow state and other data
// order.update(db);

////// if the payment succeeded and the web content management system automated product delivery functionality is used then the web content management system should be instructed to deliver this order
// UCbrowseWebsite browseWebsite = new UCbrowseWebsite(); browseWebsite.doDeliver(order, website, servletcontext, inifile, mysession, myrequest, myresponse, myconfig, db, original_database);

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>