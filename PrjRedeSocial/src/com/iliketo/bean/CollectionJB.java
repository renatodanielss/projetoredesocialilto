package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de uma Coleção
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class CollectionJB extends ContentILiketoJB {

	private String idCollection;
	private String nameCollection;
	private String description;
	private String pathPhoto;
	private String category;
	private MemberJB member;
	
	public CollectionJB(){
		
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
	public String getPathPhoto() {
		return pathPhoto;
	}
	public void setPathPhoto(String path_photo) {
		this.pathPhoto = path_photo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public MemberJB getMember() {
		return member;
	}
	public void setMember(MemberJB member) {
		this.member = member;
	}
	
	
	
}
