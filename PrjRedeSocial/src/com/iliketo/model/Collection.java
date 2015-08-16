package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de uma Coleção
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Collection extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_collection")
	private String idCollection;
	
	@ColumnILiketo(name = "name_collection")
	private String nameCollection;
	
	@ColumnILiketo(name = "description")
	private String description;
	
	@ColumnILiketo(name = "tag")
	private String tag;
	
	@FileILiketo
	@ColumnILiketo(name = "path_photo_collection")
	private String pathPhoto;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "collection_year")
	private String collectionYear;
	
	@ColumnILiketo(name = "total_items")
	private String totalItems;
	
	//notificacoes
	@ColumnILiketo(name = "notific_collection")
	private String notificCollection;
	
	@ColumnILiketo(name = "notific_item")
	private String notificItem;
	
	@ColumnILiketo(name = "notific_video")
	private String notificVideo;
	
	@ColumnILiketo(name = "notific_event")
	private String notificEvent;
	
	@ColumnILiketo(name = "notific_announce")
	private String notificAnnounce;
	
	@ColumnILiketo(name = "notific_topic")
	private String notificTopic;
	
	@ColumnILiketo(name = "notific_comment")
	private String notificComment;
	
	private Member member;
	
	public Collection(){
		
	}


	public String getIdCollection() {
		return idCollection;
	}


	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}


	public String getNameCollection() {
		return nameCollection;
	}


	public void setNameCollection(String nameCollection) {
		this.nameCollection = nameCollection;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getPathPhoto() {
		return pathPhoto;
	}


	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
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


	public String getIdMember() {
		return idMember;
	}


	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}


	public String getNotificCollection() {
		return notificCollection;
	}


	public void setNotificCollection(String notificCollection) {
		this.notificCollection = notificCollection;
	}


	public String getNotificItem() {
		return notificItem;
	}


	public void setNotificItem(String notificItem) {
		this.notificItem = notificItem;
	}


	public String getNotificVideo() {
		return notificVideo;
	}


	public void setNotificVideo(String notificVideo) {
		this.notificVideo = notificVideo;
	}


	public String getNotificEvent() {
		return notificEvent;
	}


	public void setNotificEvent(String notificEvent) {
		this.notificEvent = notificEvent;
	}


	public String getNotificAnnounce() {
		return notificAnnounce;
	}


	public void setNotificAnnounce(String notificAnnounce) {
		this.notificAnnounce = notificAnnounce;
	}


	public String getNotificTopic() {
		return notificTopic;
	}


	public void setNotificTopic(String notificTopic) {
		this.notificTopic = notificTopic;
	}


	public String getNotificComment() {
		return notificComment;
	}


	public void setNotificComment(String notificComment) {
		this.notificComment = notificComment;
	}
	
	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public String getCollectionYear() {
		return collectionYear;
	}


	public void setCollectionYear(String collectionYear) {
		this.collectionYear = collectionYear;
	}


	public String getTotalItems() {
		return totalItems;
	}


	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}
	
}
