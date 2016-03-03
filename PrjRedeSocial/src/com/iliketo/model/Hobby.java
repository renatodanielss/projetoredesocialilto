package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
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
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "country")
	private String country;
	
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

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
