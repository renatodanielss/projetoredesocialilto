package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um hobby
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Hobby extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_hobby")
	private String idHobby;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	@ColumnILiketo(name = "super_category")
	private String superCategory;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "idioma")
	private String idiom;
	
	@FileILiketo
	@ColumnILiketo(name = "photo_capa")
	private String fotoDeCapa;

	
	//notificacoes
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
		
	public Hobby(){
		
	}

	public String getIdHobby() {
		return idHobby;
	}

	public void setIdHobby(String idHobby) {
		this.idHobby = idHobby;
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

	public String getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getIdiom() {
		return idiom;
	}

	public void setIdiom(String idiom) {
		this.idiom = idiom;
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

	public String getFotoDeCapa() {
		return fotoDeCapa;
	}

	public void setFotoDeCapa(String fotoDeCapa) {
		this.fotoDeCapa = fotoDeCapa;
	}
	
}
