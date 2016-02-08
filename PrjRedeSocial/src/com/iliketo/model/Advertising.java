package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um Anuncio
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Advertising extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_advertising")
	private String idAdvertising;
	
	@ColumnILiketo(name = "type")			//(directed ad, global ad)
	private String type;
	
	@ColumnILiketo(name = "duration")		//tempo anuncio
	private String duration;	
	
	@ColumnILiketo(name = "status")			//status(Active, Expired)
	private String status;		
	
	@ColumnILiketo(name = "title")
	private String title;
	
	@ColumnILiketo(name = "description")
	private String description;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;	
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@FileILiketo
	@ColumnILiketo(name = "path_ad")			//caminho foto, video
	private String pathAd;

	@ColumnILiketo(name = "link_ad")			//url link anuncio
	private String linkAd;
	
	@ColumnILiketo(name = "payment_status")
	private String paymentStatus;
	
	@ColumnILiketo(name = "cost")
	private String cost;
	
	private Member member;
	
	public Advertising(){
		
	}

	public String getIdAdvertising() {
		return idAdvertising;
	}

	public void setIdAdvertising(String idAdvertising) {
		this.idAdvertising = idAdvertising;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getPathAd() {
		return pathAd;
	}

	public void setPathAd(String pathAd) {
		this.pathAd = pathAd;
	}

	public String getLinkAd() {
		return linkAd;
	}

	public void setLinkAd(String linkAd) {
		this.linkAd = linkAd;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
}
