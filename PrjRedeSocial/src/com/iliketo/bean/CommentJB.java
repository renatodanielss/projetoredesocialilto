package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Item
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class CommentJB extends ContentILiketoJB {

	private String idComment;
	private String textComment;
	private CommentJB commentJB;
	private TopicJB topic;
	private ForumJB forum;
	private MemberJB member;
	
	public CommentJB(){
		
	}
	

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public String getTextComment() {
		return textComment;
	}

	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	public CommentJB getCommentJB() {
		return commentJB;
	}

	public TopicJB getTopic() {
		return topic;
	}

	public void setTopic(TopicJB topic) {
		this.topic = topic;
	}


	public void setCommentJB(CommentJB commentJB) {
		this.commentJB = commentJB;
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
