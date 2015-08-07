package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados das mensagens de negociacao
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class MessageInbox extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_msg")
	private String idMsg;

	@ColumnILiketo(name = "subject")
	private String subject;
	
	@ColumnILiketo(name = "receiver_user_id")
	private String receiverIdMember;
	
	@ColumnILiketo(name = "sender_user_id")
	private String senderIdMember;
	
	@ColumnILiketo(name = "was_read")
	private String wasRead;
	
	@ColumnILiketo(name = "message")
	private String message;
	
	@ColumnILiketo(name = "content_type")
	private String contentType;
	
	@ColumnILiketo(name = "fk_content_id")
	private String idContent;
	
	@ColumnILiketo(name = "receiver_hidden")
	private String receiverHidden;
	
	@ColumnILiketo(name = "sender_hidden")
	private String senderHidden;

	public MessageInbox(){ }


	public String getIdMsg() {
		return idMsg;
	}
	

	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public void setIdMsg(String idMsg) {
		this.idMsg = idMsg;
	}


	public String getReceiverIdMember() {
		return receiverIdMember;
	}


	public void setReceiverIdMember(String receiverIdMember) {
		this.receiverIdMember = receiverIdMember;
	}


	public String getSenderIdMember() {
		return senderIdMember;
	}


	public void setSenderIdMember(String senderIdMember) {
		this.senderIdMember = senderIdMember;
	}


	public String getWasRead() {
		return wasRead;
	}


	public void setWasRead(String wasRead) {
		this.wasRead = wasRead;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
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


	public String getReceiverHidden() {
		return receiverHidden;
	}


	public void setReceiverHidden(String receiverHidden) {
		this.receiverHidden = receiverHidden;
	}


	public String getSenderHidden() {
		return senderHidden;
	}


	public void setSenderHidden(String senderHidden) {
		this.senderHidden = senderHidden;
	}
	

}
