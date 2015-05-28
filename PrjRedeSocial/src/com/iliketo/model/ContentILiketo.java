package com.iliketo.model;

/**
 * Super Classe de modelo
 * Classe genérica de um conteúdo de dados/modelos ILiketo
 * Implementa interface Comparable, todas as classes que herdam possuem a comparação natural de updated
 * 
 * 
 * @author osvaldimar.costa
 *
 */
public class ContentILiketo implements Comparable<ContentILiketo>{
	
	private String id;				//idReal asbru
	private String dateCreated;		//data asbru
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
