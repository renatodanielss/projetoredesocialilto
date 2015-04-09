package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Item
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class TopicJB extends ContentILiketoJB {

	private String idTopic;
	private String subject;
	private ForumJB forum;
	private MemberJB member;
	
	public TopicJB(){
		
	}

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

	public ForumJB getForum() {
		return forum;
	}

	public void setForum(ForumJB forum) {
		this.forum = forum;
	}

	public MemberJB getMember() {
		return member;
	}

	public void setMember(MemberJB member) {
		this.member = member;
	}


	
	
}
