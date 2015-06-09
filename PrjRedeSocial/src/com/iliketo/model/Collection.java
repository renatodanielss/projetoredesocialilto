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
	
	
}
