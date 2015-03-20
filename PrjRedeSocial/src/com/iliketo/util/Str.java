package com.iliketo.util;

/**
 * Classe responsável por armazenar as String constantes
 * @author Osvaldimar
 *
 */
public class Str {
	
	/**Nome padrão da variavel usada na session para guardar o valor do diretorio raiz para upload de armazenamento imagens*/
	public static final String STORAGE = "storage";
	
	/**Nome do campo para upload da imagem da coleção*/
	public static final String PHOTO_COLLECTION = "photo_collection";
	
	/**Nome do campo da database dbcollection para armazenar o nome da imagem da coleção*/
	public static final String PATH_PHOTO_COLLECTION = "path_photo_collection";
	
	/**Nome do campo type=file do form para upload da imagem do membro*/
	public static final String PHOTO_MEMBER = "photo_member";
	
	/**Nome do campo da database dbmember para armazenar o nome da imagem de perfil do member*/
	public static final String PATH_PHOTO_MEMBER = "path_photo_member";
	
	/**Nome do campo para upload da imagem do item*/
	public static final String PHOTO_ITEM = "photo_item";
	
	/**Nome do campo da database dbcollectionitem para armazenar o nome da imagem do item*/
	public static final String PATH_PHOTO_ITEM = "path_photo_item";
	
	/**Nome do campo para upload do video da coleção*/
	public static final String FILE_VIDEO = "file_video";
	
	/**Nome do campo da database dbcollectionvideo para armazenar o nome do video adicionado*/
	public static final String PATH_FILE_VIDEO = "path_file_video";
	
	/**String name_collection é o nome do campo do form para registrar uma nova coleção*/
	public static final String NAME_COLLECTION = "name_collection";
	
	/**String s_collection é a variável usada na sessão para guardar o nome da coleção selecionada*/
	public static final String S_COLLECTION = "s_collection";
	
	/**String s_id_collection é a variável usada na sessão para guardar o id da coleção selecionada*/
	public static final String S_ID_COLLECTION = "s_id_collection";
	
	/**String s_id_register_collection é a variável usada na sessão para guardar o id da coleção nova criada*/
	public static final String S_ID_REGISTER_COLLECTION = "s_id_register_collection";
	
	/**String s_id_collector é a variável usada na sessão para guardar o id da coleção do colecionador*/
	public static final String S_ID_COLLECTOR = "s_id_collector";
	
	/**String s_id_collector é a variável usada na sessão para guardar o id do usuario colecionador*/
	public static final String S_ID_MEMBER_COLLECTOR = "s_id_member_collector";
	
	/**String s_id_item é a variável usada na sessão para guardar o id do item para visualizar*/
	public static final String S_ID_ITEM = "s_id_item";
	
	//construtor privado
	private Str(){
		
	}
	
}
