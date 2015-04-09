package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Item
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class ItemJB extends ContentILiketoJB {

	private String idItem;
	private String title;
	private String description;
	private String pathPhoto;
	private MemberJB member;
	
	public ItemJB(){
		
	}


	public String getIdItem() {
		return idItem;
	}
	public void setIdItem(String idItem) {
		this.idItem = idItem;
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
	public String getPathPhoto() {
		return pathPhoto;
	}
	public void setPathPhoto(String path_photo) {
		this.pathPhoto = path_photo;
	}
	public MemberJB getMember() {
		return member;
	}
	public void setMember(MemberJB member) {
		this.member = member;
	}
	
	
	
}
