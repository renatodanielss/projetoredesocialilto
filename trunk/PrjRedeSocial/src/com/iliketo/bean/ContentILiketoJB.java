package com.iliketo.bean;

/**
 * Super Classe javabean de modelo
 * Classe genérica de um conteúdo de dados/modelos ILiketo
 * Implementa interface Comparable, todas as classes que herdam possuem a comparação natural de updated
 * 
 * 
 * @author osvaldimar.costa
 *
 */
public class ContentILiketoJB implements Comparable<ContentILiketoJB>{
	
	private String dateCreated;
	private String dateUpdated;

	
	public ContentILiketoJB(){
		
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
	public int compareTo(ContentILiketoJB jb) {
		int i = this.dateUpdated.compareTo(jb.getDateUpdated());
		if(i >= 1){
			return -1;
		}else if(i <= -1){
			return 1;
		}
        return 0; 
    }


}
