package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Cont�m os dados de um Anuncio
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Announce extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_announce")
	private String idAnnounce;
	
	@ColumnILiketo(name = "type_announce")	//(sell, exchange, sell/exchange, auction)
	private String typeAnnounce;
	
	@ColumnILiketo(name = "price_fixed")	//preco fixo
	private String priceFixed;		
	
	@ColumnILiketo(name = "price_initial")	//preco inicial (auction)
	private String priceInitial;	
	
	@ColumnILiketo(name = "date_initial")	//data inicial (auction)
	private String dateInitial;
	
	@ColumnILiketo(name = "bid_actual")		//lance atual (auction)
	private String bidActual;
	
	@ColumnILiketo(name = "bid_user_id")	//id membro lance atual (auction)
	private String bidUserId;
		
	@ColumnILiketo(name = "lasting")		//duaracao (auction)
	private String lasting;			
	
	@ColumnILiketo(name = "total_bids")		//total lances (auction)
	private String totalBids;		
	
	@ColumnILiketo(name = "title")
	private String title;
	
	@ColumnILiketo(name = "description")
	private String description;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	@ColumnILiketo(name = "fk_item_id")
	private String idItem;
	
	@ColumnILiketo(name = "fk_collection_id")
	private String idCollection;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@FileILiketo
	@ColumnILiketo(name = "path_photo_ad")	//somente nome da foto do item do colecionador
	private String pathPhotoAd;
	
	@ColumnILiketo(name = "offered_price")
	private String offeredPrice;
	
	@ColumnILiketo(name = "combine")
	private String combine;
	
	@ColumnILiketo(name = "account_type")	//tipo de conta do anuncio(Store ou Collector)
	private String accountType;

	@ColumnILiketo(name = "featured")		//destaque
	private String featured;
	
	@ColumnILiketo(name = "status")			//status(For sale, for auction, sold, canceled)
	private String status;
	
	@ColumnILiketo(name = "buyer_user_id")	//id do comprador/colecionador
	private String idBuyer;
	
	@ColumnILiketo(name = "rating")			//notas classificacao
	private String rating;
	
	@ColumnILiketo(name = "details")		//notas detalhes para troca
	private String details;
	
	@ColumnILiketo(name = "payment_status")		//notas detalhes para troca
	private String paymentStatus;
	
	@ColumnILiketo(name = "payment_status_destaque")
	private String paymentStatusDestaque;
	
	@ColumnILiketo(name = "adHobby")		//y | n
	private String adHobby;
	
	@ColumnILiketo(name = "fk_hobby_id")
	private String idHobby;
	
	private Member member;
	
	public Announce(){
		
	}

	
	public String getIdAnnounce() {
		return idAnnounce;
	}


	public void setIdAnnounce(String idAnnounce) {
		this.idAnnounce = idAnnounce;
	}


	public String getTypeAnnounce() {
		return typeAnnounce;
	}


	public void setTypeAnnounce(String typeAnnounce) {
		this.typeAnnounce = typeAnnounce;
	}


	public String getPriceFixed() {
		return priceFixed;
	}


	public void setPriceFixed(String priceFixed) {
		this.priceFixed = priceFixed;
	}


	public String getPriceInitial() {
		return priceInitial;
	}
	

	public String getDateInitial() {
		return dateInitial;
	}


	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}


	public void setPriceInitial(String priceInitial) {
		this.priceInitial = priceInitial;
	}


	public String getBidActual() {
		return bidActual;
	}
	

	public String getBidUserId() {
		return bidUserId;
	}


	public void setBidUserId(String bidUserId) {
		this.bidUserId = bidUserId;
	}


	public void setBidActual(String bidActual) {
		this.bidActual = bidActual;
	}


	public String getLasting() {
		return lasting;
	}


	public void setLasting(String lasting) {
		this.lasting = lasting;
	}


	public String getTotalBids() {
		return totalBids;
	}


	public void setTotalBids(String totalBids) {
		this.totalBids = totalBids;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNameCategory() {
		return nameCategory;
	}


	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}


	public String getIdItem() {
		return idItem;
	}


	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}


	public String getIdCollection() {
		return idCollection;
	}


	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}


	public String getIdMember() {
		return idMember;
	}


	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}


	public String getIdCategory() {
		return idCategory;
	}


	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}


	public String getPathPhotoAd() {
		return pathPhotoAd;
	}


	public void setPathPhotoAd(String pathPhotoAd) {
		this.pathPhotoAd = pathPhotoAd;
	}


	public String getOfferedPrice() {
		return offeredPrice;
	}


	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}


	public String getCombine() {
		return combine;
	}


	public void setCombine(String combine) {
		this.combine = combine;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getFeatured() {
		return featured;
	}


	public void setFeatured(String featured) {
		this.featured = featured;
	}


	public String getIdBuyer() {
		return idBuyer;
	}


	public void setIdBuyer(String idBuyer) {
		this.idBuyer = idBuyer;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public String getAdHobby() {
		return adHobby;
	}


	public void setAdHobby(String adHobby) {
		this.adHobby = adHobby;
	}


	public String getIdHobby() {
		return idHobby;
	}


	public void setIdHobby(String idHobby) {
		this.idHobby = idHobby;
	}


	public String getPaymentStatusDestaque() {
		return paymentStatusDestaque;
	}


	public void setPaymentStatusDestaque(String paymentStatusDestaque) {
		this.paymentStatusDestaque = paymentStatusDestaque;
	}
	
}
