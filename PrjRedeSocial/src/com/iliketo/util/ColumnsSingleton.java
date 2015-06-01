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
public class ColumnsSingleton {
	
	private static ColumnsSingleton instance = null;
	private HashMap<String,HashMap<String,String>> COL_DBMEMBERS;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTION;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTIONITEM;
	private HashMap<String,HashMap<String,String>> COL_DBCOLLECTIONVIDEO;
	private HashMap<String,HashMap<String,String>> COL_DBFORUM;
	private HashMap<String,HashMap<String,String>> COL_DBFORUMTOPIC;
	private HashMap<String,HashMap<String,String>> COL_DBFORUMCOMMENT;
	private HashMap<String,HashMap<String,String>> COL_DBGROUP;
	private HashMap<String,HashMap<String,String>> COL_DBCATEGORY;
	private HashMap<String,HashMap<String,String>> COL_DBEVENT;
	private HashMap<String,HashMap<String,String>> COL_DBINTEREST;
	private HashMap<String,HashMap<String,String>> COL_DBANNOUNCE;
	private HashMap<String,HashMap<String,String>> COL_DBSTOREITEM;
	private String DATA_DBMEMBERS;
	private String DATA_DBCOLLECTION;
	private String DATA_DBCOLLECTIONITEM;
	private String DATA_DBCOLLECTIONVIDEO;
	private String DATA_DBFORUM;
	private String DATA_DBFORUMTOPIC;
	private String DATA_DBFORUMCOMMENT;
	private String DATA_DBGROUP;
	private String DATA_DBCATEGORY;
	private String DATA_DBEVENT;
	private String DATA_DBINTEREST;
	private String DATA_DBANNOUNCE;
	private String DATA_DBSTOREITEM;
	
	/**
	 * Classe instanciada uma única vez, contendo seus dados na variavel estatica 'instance' usada em escopo de aplicação.
	 * Propósito evitar vários selects para recupera o col+id do name da column real no banco de dados
	 * @param db
	 */
	private ColumnsSingleton(DB db) {		
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
		
		database.read(db, new Configuration(), "dbforum");	
		DATA_DBFORUM = database.getTable();
		COL_DBFORUM = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbforumtopic");	
		DATA_DBFORUMTOPIC = database.getTable();
		COL_DBFORUMTOPIC = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbforumcomment");	
		DATA_DBFORUMCOMMENT = database.getTable();
		COL_DBFORUMCOMMENT = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbgroup");	
		DATA_DBGROUP = database.getTable();
		COL_DBGROUP = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbcategory");	
		DATA_DBCATEGORY = database.getTable();
		COL_DBCATEGORY = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbevent");	
		DATA_DBEVENT = database.getTable();
		COL_DBEVENT = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbinterest");	
		DATA_DBINTEREST = database.getTable();
		COL_DBINTEREST = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbannounce");	
		DATA_DBANNOUNCE = database.getTable();
		COL_DBANNOUNCE = database.namedcolumns;
		
		database.read(db, new Configuration(), "dbstoreitem");	
		DATA_DBSTOREITEM = database.getTable();
		COL_DBSTOREITEM = database.namedcolumns;
	}
	
	/**
	 * Método recupera a instancia da classe ColumsSingleton em escopo de aplicação
	 * contém o map de todas as colunas/campos das databases
	 * Propósito evitar vários selects para recupera o col+id do name da column real no banco de dados
	 * @param db
	 * @return
	 */
	public static ColumnsSingleton getInstance(DB db) {
		if(instance == null) {
			instance = new ColumnsSingleton(db);
		}
		return instance;
	}

		
	/**
	 * Método retorna a col+id real do banco de dados. Ex: col1, col2
	 * Passar o nome fantasia da coluna da database no parametro nameColumn
	 * Passar no parametro DB e nameDatabase
	 * Ex: nameDatabase = "dbcollectionvideo"
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
		}else if(nameDatabase.equals("dbforum")){
			mapcolumns = COL_DBFORUM;
		}else if(nameDatabase.equals("dbforumtopic")){
			mapcolumns = COL_DBFORUMTOPIC;
		}else if(nameDatabase.equals("dbforumcomment")){
			mapcolumns = COL_DBFORUMCOMMENT;
		}else if(nameDatabase.equals("dbgroup")){
			mapcolumns = COL_DBGROUP;
		}else if(nameDatabase.equals("dbcategory")){
			mapcolumns = COL_DBCATEGORY;
		}else if(nameDatabase.equals("dbevent")){
			mapcolumns = COL_DBEVENT;
		}else if(nameDatabase.equals("dbinterest")){
			mapcolumns = COL_DBINTEREST;
		}else if(nameDatabase.equals("dbannounce")){
			mapcolumns = COL_DBANNOUNCE;
		}else if(nameDatabase.equals("dbstoreitem")){
			mapcolumns = COL_DBSTOREITEM;
		}else{
			//tenta inicializar mapa das colunas novamente
			initColumnsReal(db);
			Databases database = new Databases(new Text());
			if(database.getTable() != null && !database.getTable().equals("")){
				mapcolumns = database.columns;
			}
		}
		if(mapcolumns != null){
			if(nameColum.equals("id")){
				return "id";
			}else if(mapcolumns.get(nameColum).get("id") != null && !mapcolumns.get(nameColum).get("id").equals("")){					
				return "col" + mapcolumns.get(nameColum).get("id"); //retorna col+id
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
		}else if(nameDatabase.equals("dbforum")){
			return DATA_DBFORUM;
		}else if(nameDatabase.equals("dbforumtopic")){
			return DATA_DBFORUMTOPIC;
		}else if(nameDatabase.equals("dbforumcomment")){
			return DATA_DBFORUMCOMMENT;
		}else if(nameDatabase.equals("dbgroup")){
			return DATA_DBGROUP;
		}else if(nameDatabase.equals("dbcategory")){
			return DATA_DBCATEGORY;
		}else if(nameDatabase.equals("dbevent")){
			return DATA_DBEVENT;
		}else if(nameDatabase.equals("dbinterest")){
			return DATA_DBINTEREST;
		}else if(nameDatabase.equals("dbannounce")){
			return DATA_DBANNOUNCE;
		}else if(nameDatabase.equals("dbstoreitem")){
			return DATA_DBSTOREITEM;
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
			}else if(table.equals("dbcollectionvideo")){
				mapcolumns = COL_DBCOLLECTIONVIDEO;
				tableReal = DATA_DBCOLLECTIONVIDEO;
			}else if(table.equals("dbforum")){
				mapcolumns = COL_DBFORUM;
				tableReal = DATA_DBFORUM;
			}else if(table.equals("dbforumtopic")){
				mapcolumns = COL_DBFORUMTOPIC;
				tableReal = DATA_DBFORUMTOPIC;
			}else if(table.equals("dbforumcomment")){
				mapcolumns = COL_DBFORUMCOMMENT;
				tableReal = DATA_DBFORUMCOMMENT;
			}else if(table.equals("dbgroup")){
				mapcolumns = COL_DBGROUP;
				tableReal = DATA_DBGROUP;
			}else if(table.equals("dbcategory")){
				mapcolumns = COL_DBCATEGORY;
				tableReal = DATA_DBCATEGORY;
			}else if(table.equals("dbevent")){
				mapcolumns = COL_DBEVENT;
				tableReal = DATA_DBEVENT;
			}else if(table.equals("dbinterest")){
				mapcolumns = COL_DBINTEREST;
				tableReal = DATA_DBINTEREST;
			}else if(table.equals("dbannounce")){
				mapcolumns = COL_DBANNOUNCE;
				tableReal = DATA_DBANNOUNCE;
			}else if(table.equals("dbstoreitem")){
				mapcolumns = COL_DBSTOREITEM;
				tableReal = DATA_DBSTOREITEM;
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
