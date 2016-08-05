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
	
	@ColumnILiketo(name = "hobby")		//'no' ou 'yes'
	private String hobby;
	
	@ColumnILiketo(name = "idioma")	
	private String idioma;
	
	@ColumnILiketo(name = "super_category")
	private String superCategory;
	
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


	public String getHobby() {
		return hobby;
	}


	public void setHobby(String hobby) {
		this.hobby = hobby;
	}


	public String getIdioma() {
		return idioma;
	}


	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}


	public String getSuperCategory() {
		return superCategory;
	}


	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}
	
	
}
