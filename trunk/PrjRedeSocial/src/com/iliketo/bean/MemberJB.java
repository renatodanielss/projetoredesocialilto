package com.iliketo.bean;

/**
 * Classe javabean de modelo
 * Contém os dados de um Membro
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class MemberJB extends ContentILiketoJB {

	private String idMember;
	private String nickname;
	private String pathPhoto;
	
	public MemberJB(){
		
	}
	
	
	public String getIdMember() {
		return idMember;
	}
	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPathPhoto() {
		return pathPhoto;
	}
	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}
	
	
}
