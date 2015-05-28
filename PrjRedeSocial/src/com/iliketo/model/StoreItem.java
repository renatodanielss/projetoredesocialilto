package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de item da Loja
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class StoreItem extends ContentILiketo {

	@IdILiketo
	@ColumnILiketo(name = "id_item")
	private String idItem;
	
	@ColumnILiketo(name = "fk_announce_id")
	private String idAnnounce;
	
	@FileILiketo
	@ColumnILiketo(name = "photo_store_item")
	private String photoStoreItem;
	
	
	public StoreItem(){
		
	}

	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	public String getIdAnnounce() {
		return idAnnounce;
	}

	public void setIdAnnounce(String idAnnounce) {
		this.idAnnounce = idAnnounce;
	}

	public String getPhotoStoreItem() {
		return photoStoreItem;
	}

	public void setPhotoStoreItem(String photoStoreItem) {
		this.photoStoreItem = photoStoreItem;
	}
	
	
}
