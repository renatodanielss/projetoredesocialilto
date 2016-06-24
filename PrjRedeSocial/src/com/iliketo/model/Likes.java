package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de curtidas
 * Dados/modelos ILiketoo
 * 
 * @author osvaldimar.costa
 *
 */
public class Likes extends ContentILiketo {
	
	@ColumnILiketo(name = "post_type")		//tipo conteudo = colecao, item, video, anuncio
	private String postType;
	
	@ColumnILiketo(name = "fk_post_id")		//id do conteudo
	private String idPost;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	public Likes(){
		
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}
	

}
