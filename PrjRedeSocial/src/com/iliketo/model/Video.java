package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um Video
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Video extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_video")
	private String idVideo;
	
	@ColumnILiketo(name = "title_video")
	private String title;
	
	@ColumnILiketo(name = "description_video")
	private String description;
	
	@FileILiketo
	@ColumnILiketo(name = "path_file_video")
	private String pathVideo;
	
	@ColumnILiketo(name = "fk_collection_id")
	private String idCollection;
	
	@ColumnILiketo(name = "fk_user_id")
	private String idMember;
	
	private Member member;
	
	public Video(){
		
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


	public String getIdCollection() {
		return idCollection;
	}


	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
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
