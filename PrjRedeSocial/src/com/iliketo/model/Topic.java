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
public class Topic extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_topic")
	private String idTopic;
	
	@ColumnILiketo(name = "subject")
	private String subject;
	
	@ColumnILiketo(name = "fk_forum_id")
	private String idForum;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	private Member member;
	
	public Topic(){ }

	public String getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIdForum() {
		return idForum;
	}

	public void setIdForum(String idForum) {
		this.idForum = idForum;
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
