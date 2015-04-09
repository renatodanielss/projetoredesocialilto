package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Video
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class VideoJB extends ContentILiketoJB {

	private String idVideo;
	private String title;
	private String description;
	private String pathVideo;
	private MemberJB member;
	
	public VideoJB(){
		
	}

	public String getIdVideo() {
		return idVideo;
	}
	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPathVideo() {
		return pathVideo;
	}
	public void setPathVideo(String pathVideo) {
		this.pathVideo = pathVideo;
	}
	public MemberJB getMember() {
		return member;
	}
	public void setMember(MemberJB member) {
		this.member = member;
	}
	
	
}
