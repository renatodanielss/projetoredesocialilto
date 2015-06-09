package com.iliketo.dao;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iliketo.model.UserCard;
import com.iliketo.util.CmsConfigILiketo;

import HardCore.DB;
import HardCore.Request;

public class UserCardDAO{
	
	private DB db;
	
	public UserCardDAO(DB db){
		this.db = db;
	}

	//metodos especificos da UserCardDAO abaixo
	
	/**
	 * Recupera informacoes dos dados do cartao do membro na tabela users
	 * @param myUserId
	 */
	public UserCard readInfoCard(String myUserId){

		//recupera informacoes do cartao do membro na tabela users
		String SQL = 
			    "select "
			  + "card_type, "
			  + "card_number, "
			  + "card_issuedmonth, "
			  + "card_issuedyear, "
			  + "card_expirymonth, "
			  + "card_expiryyear, "
			  + "card_name, "
			  + "card_cvc, "
			  + "card_issue, "
			  + "card_postalcode "
			  + "from users "			  
			  + "where id = '" +myUserId+ "';";
	
		HashMap<String,String> record  = db.query_record(SQL);
		UserCard userCard = new UserCard();
		
		if(record != null){
			userCard.setId(myUserId);
			userCard.setCardType(record.get("card_type"));
			userCard.setCardNumber(record.get("card_number"));
			userCard.setCardIssuedmonth(record.get("card_issuedmonth"));
			userCard.setCardIssuedyear(record.get("card_issuedyear"));
			userCard.setCardExpirymonth(record.get("card_expirymonth"));
			userCard.setCardExpiryyear(record.get("card_expiryyear"));
			userCard.setCardName(record.get("card_name"));
			userCard.setCardcvc(record.get("card_cvc"));
			userCard.setCardIssue(record.get("card_issue"));
			userCard.setCardPostalcode(record.get("card_postalcode"));
			
			return userCard;
		}
		
		return null;		
		
	}
	
	/**
	 * Salva dados do cartao do membro na tabela users
	 * @param myUserId
	 */
	public void saveInfoCard(UserCard userCard) {
		
		String SQLupdate = 
			    "card_type='" + userCard.getCardType() + "', "
			  + "card_number='" + userCard.getCardNumber() + "', "
			  + "card_issuedmonth='" + userCard.getCardIssuedmonth() + "', "
			  + "card_issuedyear='" + userCard.getCardIssuedyear() + "', "
			  + "card_expirymonth='" + userCard.getCardExpirymonth() + "', "
			  + "card_expiryyear='" + userCard.getCardExpiryyear() + "', "
			  + "card_name='" + userCard.getCardName() + "', "
			  + "card_cvc='" + userCard.getCardcvc() + "', "
			  + "card_issue='" + userCard.getCardIssue() + "', "
			  + "card_postalcode='" + userCard.getCardPostalcode() + "' ";
		
		db.updateSet("users", "id", userCard.getId(), SQLupdate);
		
	}
	
}
