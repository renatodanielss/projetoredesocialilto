package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de item da Loja
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Comment extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_comment")
	private String idComment;
	
	@ColumnILiketo(name = "text_comment")
	private String comment;
	
	@ColumnILiketo(name = "fk_topic_id")
	private String idTopic;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	@ColumnILiketo(name = "fk_comment_id")	//id comentario de uma pergunta, se esse for um comment de resposta
	private String idQuestion;
	
	private Member member;
	
	private String idForum;
	
	public Comment(){ }

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(String idQuestion) {
		this.idQuestion = idQuestion;
	}

	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}

	public String getIdForum() {
		return idForum;
	}

	public void setIdForum(String idForum) {
		this.idForum = idForum;
	}
}
