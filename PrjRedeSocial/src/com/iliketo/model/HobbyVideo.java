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
public class HobbyVideo extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_video")
	private String idVideo;
	
	@ColumnILiketo(name = "descricao")
	private String descricao;

	@FileILiketo
	@ColumnILiketo(name = "pathVideo")
	private String pathVideo;
	
	@ColumnILiketo(name = "fk_hobby_id")
	private String idHobby;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	public HobbyVideo(){
		
	}

	public String getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPathVideo() {
		return pathVideo;
	}

	public void setPathVideo(String pathVideo) {
		this.pathVideo = pathVideo;
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
