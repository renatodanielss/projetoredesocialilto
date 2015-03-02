package com.iliketo.util;


public class MapIdPageILiketo {
	
	private static int[] MAP_PAGE = {399, 285, 286, 160, 410, 90,48, 500, 501,502,503,504,505,506};

	
	private MapIdPageILiketo(){		
	}
	
	public static boolean containsPage(String parameters){	
		
		if(parameters != null){
			String paramId = "";
			int numID = 0;
			
			if(parameters.contains("&")){
				String token[] = parameters.split("&");				//separa os parametros
				paramId = token[0].substring(3, token[0].length());	//pega somente o numero do primeiro parametro 'id='
				
			}else{
				paramId = parameters.substring(3, parameters.length()); //elimina 'id=' e pega o numero do id
			}
			
			try{
				numID = Integer.parseInt(paramId);
				for(int i : MAP_PAGE){
					if(numID == i){					
						System.out.println("Security Page OK - ID = " + paramId);
						return true;	//found
					}
				}			
				System.out.println("Security Page Invalid - ID = " + paramId);
				return false;	//not found
				
			}catch(NumberFormatException e){
				//not number in parameter id
				System.out.println("Security Page Invalid - ID = " + paramId);
				return false;
			}
		}
		return true;
	}

}
