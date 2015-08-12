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
public class Interest extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_interest")
	private String idInterest;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "name_category")
	private String nameCategory;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	private Member member;
	
	public Interest(){
		
	}

	public String getIdInterest() {
		return idInterest;
	}

	public void setIdInterest(String idInterest) {
		this.idInterest = idInterest;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
