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
public class Category extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_category")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	private String total;
	
	public Category(){ }
	

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


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}

	
}
