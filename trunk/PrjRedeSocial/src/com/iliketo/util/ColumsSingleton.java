package com.iliketo.util;

import java.util.HashMap;
import java.util.Iterator;

import com.sun.javafx.collections.MapAdapterChange;

import HardCore.Configuration;
import HardCore.DB;
import HardCore.Databases;
import HardCore.Text;

/**
 * Classe responsável por ter as referencias dos nomes reais dos campos/colunas da database
 * Ex: data7, data8...
 * Ex: col1, col2...
 * Obs: É carregado do banco os nomes uma única vez e gravado na variável estatica de classe 'instance'
 * @author osvaldimar.costa
 *
 */
public class ColumsSingleton {
	
	private static ColumsSingleton instance = null;
	private HashMap<String,HashMap<String,String>> COL_DBMEMBERS;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTION;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTIONITEM;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTIONVIDEO;
	private String DATA_DBMEMBERS;
	private String DATA_DBCOLLECTION;
	private String DATA_DBCOLLECTIONITEM;
	private String DATA_DBCOLLECTIONVIDEO;
	
	/**
	 * Classe instanciada uma única vez, contendo seus dados na variavel estatica 'instance' usada em escopo de aplicação.
	 * Propósito evitar vários selects para recupera o col+id do name da column real no banco de dados
	 * @param db
	 */
	private ColumsSingleton(DB db) {		
		initColumnsReal(db);			
	}
	
	/**
	 * Método inicializa o mapa de colunas que contem a referencia das colunas reais no banco de dados
	 * @param db
	 */
	private void initColumnsReal(DB db){
		Databases database = new Databases(new Text());
		
		//inicializa nome real das database no banco de dados > ex: data7, data8, data9
		//inicializa nome real das colunas no hashmap > ex: col1, col2
				
		database.read(db, new Configuration(), "dbmembers");			
		DATA_DBMEMBERS = database.getTable();
		COL_DBMEMBERS = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbcollection");			
		DATA_DBCOLLECTION = database.getTable();
		COL_DBCOLLECTION = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbcollectionitem");	
		DATA_DBCOLLECTIONITEM = database.getTable();
		COL_DBCOLLECTIONITEM = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbcollectionvideo");	
		DATA_DBCOLLECTIONVIDEO = database.getTable();
		COL_DBCOLLECTIONVIDEO = database.namedcolumns;
	}
	
	/**
	 * Método recupera a instancia da classe ColumsSingleton em escopo de aplicação
	 * contém o map de todas as colunas/campos das databases
	 * Propósito evitar vários selects para recupera o col+id do name da column real no banco de dados
	 * @param db
	 * @return
	 */
	public static ColumsSingleton getInstance(DB db) {
		if(instance == null) {
			instance = new ColumsSingleton(db);
		}
		return instance;
	}

		
	/**
	 * Método retorna a col+id real do banco de dados. Ex: col1, col2
	 * Passar o nome fantasia da coluna da database no parametro nameColumn
	 * Ex: nameColum = "title_video"
	 * @param nameColum
	 * @return
	 */
	public String getCOL(DB db, String nameDatabase, String nameColum) {
		HashMap<String,HashMap<String,String>> mapcolumns = null;
		//identifica tabela real no banco de dados
		if(nameDatabase.equals("dbmembers")){
			mapcolumns = COL_DBMEMBERS;
		}else if(nameDatabase.equals("dbcollection")){
			mapcolumns = COL_DBCOLLECTION;
		}else if(nameDatabase.equals("dbcollectionitem")){
			mapcolumns = COL_DBCOLLECTIONITEM;
		}else if(nameDatabase.equals("dbcollectionvideo")){
			mapcolumns = COL_DBCOLLECTIONVIDEO;
		}else{
			//tenta inicializar mapa das colunas novamente
			initColumnsReal(db);
			Databases database = new Databases(new Text());
			if(database.getTable() != null && !database.getTable().equals("")){
				mapcolumns = database.columns;
			}
		}
		if(mapcolumns != null){
			if(mapcolumns.get(nameColum).get("id") != null && !mapcolumns.get(nameColum).get("id").equals("")){					
				return "col" + mapcolumns.get(nameColum).get("id"); //retorna col+id
			}else if(nameColum.equals("id")){
				return "id";
			}
		}
		
		return null;
	}

	/**
	 * Método retorna a table+id real no banco de dados. Ex: data7, data8
	 * @param db
	 * @param nameColum
	 * @return tabela real da 'dbmembers'
	 */
	public String getDATA(DB db, String nameDatabase) {
		if(nameDatabase.equals("dbmembers")){
			return DATA_DBMEMBERS;
		}else if(nameDatabase.equals("dbcollection")){
			return DATA_DBCOLLECTION;
		}else if(nameDatabase.equals("dbcollectionitem")){
			return DATA_DBCOLLECTIONITEM;
		}else if(nameDatabase.equals("dbcollectionvideo")){
			return DATA_DBCOLLECTIONVIDEO;
		}else{
			//tenta inicializar ler a database novamente para recuperar a tabela real
			initColumnsReal(db);
			Databases database = new Databases(new Text());
			if(database.getTable() != null && !database.getTable().equals("")){
				return database.getTable();
			}
		}
		return null;
	}
	
	
	/**
	 * Método genérico transforma o sql com nomes fantasia da coluna e tabela nos nomes reais que estão no banco de dados
	 * Passar no parametro o SQL comum Ex: 'select c1.id_collection, c1.name_collection from dbcollection c1';
	 * Passar no parametro o array[][] tableAlias
	 * Ex: tabela = tableAlias[0][0] = "dbcollection";
	 * Ex: apelido = tableAlias[0][1] = "c1";
	 * @param SQL comum
	 * @param tableAlias
	 * @return SQL real
	 */
	public String transformSQLReal(String SQL, String[][] tableAlias){
		String tableReal = "";
		HashMap<String,HashMap<String,String>> mapcolumns = null;
		
		//replace o nome da tabela e colunas reais do banco de dados
		for(int i = 0; i < tableAlias.length; i++){			
			//tabela e apelido
			String table = tableAlias[i][0];	//ex: dbcollection
			String as = tableAlias[i][1];		//ex: c1			
			//identifica tabela real do banco de dados
			if(table.equals("dbmembers")){
				mapcolumns = COL_DBMEMBERS;
				tableReal = DATA_DBMEMBERS;
			}else if(table.equals("dbcollection")){
				mapcolumns = COL_DBCOLLECTION;
				tableReal = DATA_DBCOLLECTION;
			}else if(table.equals("dbcollectionitem")){
				mapcolumns = COL_DBCOLLECTIONITEM;
				tableReal = DATA_DBCOLLECTIONITEM;
			}			
			//replace todos nomes da table para a tabela real no banco de dados
			if(SQL.contains(table)){
				SQL = SQL.replaceAll(table, tableReal);
			}			
			
			//Ler o map das colunas da database real no banco de dados
			if(mapcolumns != null){				
				Iterator nameColums = mapcolumns.keySet().iterator();
				while (nameColums.hasNext()) {
					String column = "" + nameColums.next();		
					
					//verifica se possui a coluna no comando sql seguida do apelido da tabela.  
					if(SQL.contains(as + "." + column)){	//Ex: c1.name_collection, t1.title_item
						
						//valida nome da coluna fantasia se existe a col+id real no banco de dados
						if(mapcolumns.get(column).get("id") != null && !mapcolumns.get(column).get("id").equals("")){
							
							SQL = SQL.replaceAll(as+"."+column, as+".col"+mapcolumns.get(column).get("id")); //retorna 'as.col+id' = 'c1.col1', 'c1.col2' ...
						
						}else if(column != null && column.equals("id")){ //coluna id da tabela							
							SQL = SQL.replaceAll(as + "." + column, as + "." + "id");							
						}else{
							System.out.println("SQL error: column " +as+"."+column+ " no find or no exists in table " +table+ "!");
						}
					}
				}
			}else{
				System.out.println("SQL error: table " +table+ " no find or no exists!");
			}			
		}
		return SQL;
	}
	
}
