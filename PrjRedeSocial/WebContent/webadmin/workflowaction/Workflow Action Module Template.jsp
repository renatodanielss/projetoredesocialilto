<%@ include file="../config.jsp" %>
<%@ page import="HardCore.Content" %>
<%@ page import="HardCore.Order" %>
<%@ page import="HardCore.Workflow" %>
<%



Workflow workflow = new Workflow();
if (request.getAttribute("workflow") != null) {
	workflow = (Workflow)request.getAttribute("workflow");			// the executed workflow action in the web content management system

	// workflow.getId();							// unique workflow action id in the web content management system
	// workflow.getTitle();							// workflow name
	// workflow.getAction();						// workflow action name
	// workflow.getFromState();						// workflow action from state
	// workflow.getToState();						// workflow action to state
}



Content content = new Content();
if (request.getAttribute("content") != null) {
	// content workflow action

	content = (Content)request.getAttribute("content");			// the created/updated content/product in the web content management system


	// Note: All content/product attributes are returned as String data
	
	// content.getId()							// unique content/product id in the web content management system

	// content.getCreated()							// content/product created date/time - YYYY-MM-DD hh:mm:ss
	// content.getCreatedBy()						// content/product created by web content management system username
	// content.getUpdated()							// content/product last updated date/time - YYYY-MM-DD hh:mm:ss
	// content.getUpdatedBy()						// content/product last updated by web content management system username
	// content.getPublished()						// content/product published date/time - YYYY-MM-DD hh:mm:ss
	// content.getPublishedBy()						// content/product published by web content management system username
	// content.getUnpublished()						// content/product unpublished date/time - YYYY-MM-DD hh:mm:ss
	// content.getUnpublishedBy()						// content/product unpublished by web content management system username
	// content.getRequestedPublish()					// content/product requested publish date/time - YYYY-MM-DD hh:mm:ss
	// content.getRequestedUnpublish()					// content/product requested unpublish date/time - YYYY-MM-DD hh:mm:ss
	// content.getScheduledPublish()					// content/product scheduled publish date/time - YYYY-MM-DD hh:mm:ss
	// content.getScheduledUnpublish()					// content/product scheduled unpublish date/time - YYYY-MM-DD hh:mm:ss
	// content.getStatus()							// content/product administration workflow state
	// content.getStatusBy()						// content/product administration workflow state by web content management system administrator username
	// content.getCheckedOut()						// content/product checked out by / assigned to web content management system administrator username
	// content.getRevision()						// content/product revision notes

	// content.getTitle()							// content/product title
	// content.getContent()							// content/product primary content
	// content.getSummary()							// content/product summary content
	// content.getServerFilename()						// content/product relative path/filename on server
	// content.getVersion()							// content/product version
	// content.getVersionMaster()						// content/product version of master content/product item id

	// content.getAuthor()							// content/product author
	// content.getDescription()						// content/product description
	// content.getKeywords()						// content/product keywords
	// content.getMetaInfo()						// content/product custom meta information

	// content.getTemplate()						// content/product template content item id
	// content.getStyleSheet()						// content/product style sheet content item ids
	// content.getScripts()							// content/product scripts content item ids
	// content.getDocType()							// content/product doctype
	// content.getHtmlHeader()						// content/product HTML HEAD code
	// content.getHtmlBodyOnload()						// content/product HTML NBODY ONLOAD code

	// content.getContentPackage()						// content/product package
	// content.getContentBundle()						// content/product bundle
	// content.getContentClass()						// content/product class
	// content.getContentGroup()						// content/product group
	// content.getContentType()						// content/product type

	// content.getPageTop()							// content/product page top content/product item id
	// content.getPageUp()							// content/product page up content/product item id
	// content.getPageFirst()						// content/product page first content/product item id
	// content.getPagePrevious()						// content/product page previous content/product item id
	// content.getPageNext()						// content/product page next content/product item id
	// content.getPageLast()						// content/product page last content/product item id
	// content.getRelated()							// content/product page related content/product item ids

	// content.getProductCode()						// product code
	// content.getProductCurrency()						// product price currency
	// content.getProductPrice()						// product price amount
	// content.getProductPeriod()						// product period (subscription)
	// content.getProductCost()						// product cost per item

	// content.getProductWeight()						// product weight
	// content.getProductWidth()						// product width
	// content.getProductHeight()						// product height
	// content.getProductDepth()						// product depth
	// content.getProductVolume()						// product volume
	// content.getProductBrand()						// product brand
	// content.getProductColour()						// product colour
	// content.getProductSize()						// product size
	// content.getProductInfo()						// custom product information
	// content.getProductOptions()						// product options

	// content.getProductEmail()						// product delivery email content item id
	// content.getProductPage()						// product delivery page content item id
	// content.getProductProgram()						// product delivery custom extension program
	// content.getProductUser()						// product delivery user template

	// content.getProductStockAmount(db)					// product current stock amount
	// content.getProductStockText()					// product in stock comment
	// content.getProductRestockedAmount()					// product ordered stock amount
	// content.getProductLowStockAmount()					// product low stock threshold
	// content.getProductLowStockText()					// product low stock comment
	// content.getProductNoStockOrder()					// product out of stock back/pre-ordering (enabled: "yes" / disabled: "no" / unlimited: "")
	// content.getProductNoStockText()					// product out of stock comment
	// content.getProductLocation()						// product stock location

	////// the content data listed above can be changed

	// content.setRevision("");

	////// save the updated content data

	// content.update(db, myconfig);

}



Order order = new Order();
if (request.getAttribute("order") != null) {
	// order workflow action

	order = (Order)request.getAttribute("order");				// the created/updated order in the web content management system

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
	// order.getRevision()							// order status notes

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

	////// the order data listed above can be changed

	// order.setCardCVC("");

	////// save the updated order data

	// order.update(db);

}



session.setAttribute("workflowaction", "");

%>