package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um lance de leilao
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class AuctionBid extends ContentILiketo {

	@ColumnILiketo(name = "fk_announce_id")
	private String idAnnounce;
	
	@ColumnILiketo(name = "bid")			//valor lance
	private String bid;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	private Member member;
	
	public AuctionBid(){
		
	}

	public String getIdAnnounce() {
		return idAnnounce;
	}

	public void setIdAnnounce(String idAnnounce) {
		this.idAnnounce = idAnnounce;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
