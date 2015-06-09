package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados do cartao do membro
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class UserCard extends ContentILiketo {

	
	@IdILiketo
	@ColumnILiketo(name = "id")
	private String id;
	
	@ColumnILiketo(name = "card_type")
	private String cardType;
	
	@ColumnILiketo(name = "card_number")
	private String cardNumber;
	
	@ColumnILiketo(name = "card_issuedmonth")
	private String cardIssuedmonth;
	
	@ColumnILiketo(name = "card_issuedyear")
	private String cardIssuedyear;
	
	@ColumnILiketo(name = "card_expirymonth")
	private String cardExpirymonth;
	
	@ColumnILiketo(name = "card_expiryyear")
	private String cardExpiryyear;
	
	@ColumnILiketo(name = "card_name")
	private String cardName;
	
	@ColumnILiketo(name = "card_cvc")
	private String cardcvc;
	
	@ColumnILiketo(name = "card_issue")
	private String cardIssue;
	
	@ColumnILiketo(name = "card_postalcode")
	private String cardPostalcode;
	
	
	public UserCard(){
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String getCardType() {
		return cardType;
	}


	public void setCardType(String cardType) {
		this.cardType = cardType;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getCardIssuedmonth() {
		return cardIssuedmonth;
	}


	public void setCardIssuedmonth(String cardIssuedmonth) {
		this.cardIssuedmonth = cardIssuedmonth;
	}


	public String getCardIssuedyear() {
		return cardIssuedyear;
	}


	public void setCardIssuedyear(String cardIssuedyear) {
		this.cardIssuedyear = cardIssuedyear;
	}


	public String getCardExpirymonth() {
		return cardExpirymonth;
	}


	public void setCardExpirymonth(String cardExpirymonth) {
		this.cardExpirymonth = cardExpirymonth;
	}


	public String getCardExpiryyear() {
		return cardExpiryyear;
	}


	public void setCardExpiryyear(String cardExpiryyear) {
		this.cardExpiryyear = cardExpiryyear;
	}


	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public String getCardcvc() {
		return cardcvc;
	}


	public void setCardcvc(String cardcvc) {
		this.cardcvc = cardcvc;
	}


	public String getCardIssue() {
		return cardIssue;
	}


	public void setCardIssue(String cardIssue) {
		this.cardIssue = cardIssue;
	}


	public String getCardPostalcode() {
		return cardPostalcode;
	}


	public void setCardPostalcode(String cardPostalcode) {
		this.cardPostalcode = cardPostalcode;
	}

	
	
}
