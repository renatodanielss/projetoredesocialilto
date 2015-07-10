package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de Notificacao do grupo
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Notification extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_notification")
	private String idNotification;
	
	@ColumnILiketo(name = "fk_category_id")
	private String idCategory;
	
	@ColumnILiketo(name = "content_type")		//tipo conteudo = colecao, item, video, anuncio
	private String contentType;
	
	@ColumnILiketo(name = "fk_content_id")		//id do conteudo
	private String idContent;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "post_type")
	private String postType;
	
	public Notification(){
		
	}

	public String getIdNotification() {
		return idNotification;
	}

	public void setIdNotification(String idNotification) {
		this.idNotification = idNotification;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getIdContent() {
		return idContent;
	}

	public void setIdContent(String idContent) {
		this.idContent = idContent;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	

}
