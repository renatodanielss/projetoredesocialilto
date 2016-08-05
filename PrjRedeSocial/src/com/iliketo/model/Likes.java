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
	
	@IdILiketo
	@ColumnILiketo(name = "id_like") 
	private String idLike;
	
	@ColumnILiketo(name = "post_type") //colecao c, item i, video v, event e, comentario m
	private String postType;
	
	@ColumnILiketo(name = "fk_post_id")		//id do conteudo
	private String idPost;
	
	@ColumnILiketo(name = "fk_owner_id")	//proprietario do post
	private String idOwner;
	
	@ColumnILiketo(name = "fk_user_id")		//quem curtiu o post
	private String idMember;
	
	@ColumnILiketo(name = "nickname")
	private String nickname;
	
	@ColumnILiketo(name = "title_post")
	private String titlePost;
	
	@ColumnILiketo(name = "path_photo")		//foto quem curtiu
	private String pathPhoto;
	
	public Likes(){
		
	}

	public String getIdLike() {
		return idLike;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
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

	public String getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(String idOwner) {
		this.idOwner = idOwner;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitlePost() {
		return titlePost;
	}

	public void setTitlePost(String titlePost) {
		this.titlePost = titlePost;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}
	

}
