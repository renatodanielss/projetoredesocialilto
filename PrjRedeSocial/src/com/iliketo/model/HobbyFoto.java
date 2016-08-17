package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados da foto do hobby
 * Dados/modelos ILiketoo
 * 
 * @author osvaldimar.costa
 *
 */
public class HobbyFoto extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_foto")
	private String idFoto;
	
	@ColumnILiketo(name = "descricao")
	private String descricao;

	@FileILiketo
	@ColumnILiketo(name = "foto")
	private String foto;
	
	@ColumnILiketo(name = "fk_hobby_id")
	private String idHobby;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	public HobbyFoto(){
		
	}

	public String getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(String idFoto) {
		this.idFoto = idFoto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIdHobby() {
		return idHobby;
	}

	public void setIdHobby(String idHobby) {
		this.idHobby = idHobby;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}
	
}
