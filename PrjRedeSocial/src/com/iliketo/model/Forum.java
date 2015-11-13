package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados do forum
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Forum extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_forum")
	private String idForum;	
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	
	public Forum(){ }


	public String getIdForum() {
		return idForum;
	}


	public void setIdForum(String idForum) {
		this.idForum = idForum;
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

	
}
