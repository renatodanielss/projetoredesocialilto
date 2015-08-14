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
public class Interest extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_interest")
	private String idInterest;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
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
	
	public Interest(){
		
	}

	public String getIdInterest() {
		return idInterest;
	}

	public void setIdInterest(String idInterest) {
		this.idInterest = idInterest;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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
	
}
