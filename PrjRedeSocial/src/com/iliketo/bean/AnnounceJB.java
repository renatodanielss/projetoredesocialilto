package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Anuncio
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class AnnounceJB extends ContentILiketoJB {

	private String idAnnounce;
	private String typeAnnounce; 	//(sell, exchange, sell/exchange, auction)
	private String priceFixed;		//preco fixo
	private String priceInitial;	//preco inicial (auction)
	private String bidActual;		//lance atual (auction)
	private String lasting;			//duaracao (auction)
	private String totalBids;		//total lances (auction)
	private String title;
	private String description;
	private String nameCategory;
	private String idItem;
	private String idCollection;
	private String idMember;
	private String idCategory;
	private String path_photo_ad;
	private String dateInitial;
	private String offeredPrice;
	private String details;
	private MemberJB member;

	public AnnounceJB(){
		
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

	public void setPriceInitial(String priceInitial) {
		this.priceInitial = priceInitial;
	}

	public String getBidActual() {
		return bidActual;
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

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public String getPath_photo_ad() {
		return path_photo_ad;
	}

	public void setPath_photo_ad(String path_photo_ad) {
		this.path_photo_ad = path_photo_ad;
	}

	public MemberJB getMember() {
		return member;
	}

	public void setMember(MemberJB member) {
		this.member = member;
	}

	public String getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(String dateInitial) {
		this.dateInitial = dateInitial;
	}

	public String getOfferedPrice() {
		return offeredPrice;
	}

	public void setOfferedPrice(String offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	
	
}
