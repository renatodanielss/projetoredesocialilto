package com.iliketo.model;

import com.iliketo.model.annotation.ColumnILiketo;

/**
 * Super Classe de modelo
 * Classe gen�rica de um conte�do de dados/modelos ILiketo
 * Implementa interface Comparable, todas as classes que herdam possuem a compara��o natural de updated
 * 
 * 
 * @author osvaldimar.costa
 *
 */
public class ContentILiketo implements Comparable<ContentILiketo>{
	
	@ColumnILiketo(name = "id")
	private String id;				//idReal asbru
	
	@ColumnILiketo(name = "date_created")
	private String dateCreated;		//data asbru
	
	@ColumnILiketo(name = "date_updated")
	private String dateUpdated;		//data asbru

	
	public ContentILiketo(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String date_created) {
		this.dateCreated = date_created;
	}
	public String getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(String date_updated) {
		this.dateUpdated = date_updated;
	}
	
	@Override
	public int compareTo(ContentILiketo jb) {
		int i = this.dateUpdated.compareTo(jb.getDateUpdated());
		if(i >= 1){
			return -1;
		}else if(i <= -1){
			return 1;
		}
        return 0; 
    }


}
