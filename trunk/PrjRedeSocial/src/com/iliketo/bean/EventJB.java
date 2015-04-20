package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Evento
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class EventJB extends ContentILiketoJB {

	private String idEvent;
	private String nameEvent;
	private String dateEvent;
	private String hourEvent;
	private String typeEvent;
	private String details;
	private String local;
	private String pathPhoto;
	private String nameCategory;
	private MemberJB member;
	
	public EventJB(){
		
	}

	public String getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}

	public String getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(String nameEvent) {
		this.nameEvent = nameEvent;
	}

	public String getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(String dateEvent) {
		this.dateEvent = dateEvent;
	}

	public String getHourEvent() {
		return hourEvent;
	}

	public void setHourEvent(String hourEvent) {
		this.hourEvent = hourEvent;
	}

	public String getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(String typeEvent) {
		this.typeEvent = typeEvent;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public MemberJB getMember() {
		return member;
	}

	public void setMember(MemberJB member) {
		this.member = member;
	}

	
}
