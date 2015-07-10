package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um Item
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Item extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_item")
	private String idItem;
	
	@ColumnILiketo(name = "title_item")
	private String title;
	
	@ColumnILiketo(name = "description_item")
	private String description;
	
	@FileILiketo
	@ColumnILiketo(name = "path_photo_item")
	private String pathPhoto;
	
	@ColumnILiketo(name = "fk_collection_id")
	private String idCollection;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	private Member member;
	
	public Item(){
		
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

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
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
	
	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}
	
}
