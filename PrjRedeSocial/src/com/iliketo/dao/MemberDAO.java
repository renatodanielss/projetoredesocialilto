package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Member;
import com.iliketo.util.ColumnsSingleton;

public class MemberDAO extends GenericDAO{
	
	
	public MemberDAO(DB db, HttpServletRequest request){
		super(db, "dbmembers", request);
	}

	//metodos especificos da MemberDAO abaixo

	
	/**
	 * Salva novo total em bytes de espa�o utilizado do armazenamento
	 * @param member
	 * @param bytes = total utilizado + upload
	 */
	public void saveUsedSpace(Member member, long bytes) {
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String dataid = CS.getDATA(db, nameDatabase);
		String colid = CS.getCOL(db, nameDatabase, "used_space");
		String SQLupdate = colid + "='" + bytes + "'";
		
		super.getDb().updateSet(dataid, "id", member.getId(), SQLupdate);
		
	}
	
	/**
	 * Salva e atualiza o tipo de conta para armazenamento dos dados do membro
	 * @param member
	 * @param storageType - nome conta armazenamento
	 */
	public void saveStorageType(Member member, String storageType) {
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String dataid = CS.getDATA(db, nameDatabase);
		String colid = CS.getCOL(db, nameDatabase, "storage_type");
		String SQLupdate = colid + "='" + storageType + "'";
		
		super.getDb().updateSet(dataid, "id", member.getId(), SQLupdate);
		
	}
	
	/**
	 * Salva e atualiza o novo total de espaco da conta de armazenamento do membro em bytes.
	 * @param member
	 * @param bytes - total espaco da conta de armazenamento
	 */
	public void saveTotalSpace(Member member, long bytes) {
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String dataid = CS.getDATA(db, nameDatabase);
		String colid = CS.getCOL(db, nameDatabase, "total_space");
		String SQLupdate = colid + "='" + bytes + "'";
		
		super.getDb().updateSet(dataid, "id", member.getId(), SQLupdate);
		
	}
	
	/**
	 * Metodo atualiza a data de visto da ultima notificacao lida do membro
	 * @param member
	 */
	public void updateLastDateNotification(Member member) {
		
		DB db = super.getDb();
		String nameDatabase = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		
		String dataid = CS.getDATA(db, nameDatabase);
		String colid1 = CS.getCOL(db, nameDatabase, "last_seen_date");
		
		String SQLupdate = colid1 + "='" + member.getLastSeenDate() + "'";
		
		super.getDb().updateSet(dataid, "id", member.getId(), SQLupdate);
		
	}
	
}
