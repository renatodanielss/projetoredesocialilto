package com.iliketo.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import HardCore.DB;

import com.iliketo.model.Announce;
import com.iliketo.model.AuctionBid;
import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.util.ColumnsSingleton;

public class AuctionBidDAO extends GenericDAO{
	
	public AuctionBidDAO(DB db, HttpServletRequest request){
		super(db, "dbauctionbid", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AuctionBidDAO abaixo
	
	
	/**
	 * Metodo retorna uma lista de lances de leilao de um membro.
	 */
	public List<Object[]> listAuctionBidsByUser(String idMember){
		
		DB db = super.getDb();
		String database_bid = super.getNameDatabase();
		ColumnsSingleton CS = ColumnsSingleton.getInstance(db);
		String dataBid = CS.getDATA(db, database_bid);
		String dataAd = CS.getDATA(db, "dbannounce");
		
		String SQL = "SELECT t1.date_created as date_created, t1.bid as bid, t2.title as title, t2.id_announce as id_announce, "
					+ " t2.date_initial as date_initial, t2.price_initial as price_initial, t2.bid_actual as bid_actual, t2.bid_user_id as bid_user_id, "
					+ " t2.lasting as lasting, t2.total_bids as total_bids, t2.name_category as name_category, t2.path_photo_ad as path_photo_ad"
					+ " FROM dbauctionbid as t1 join dbannounce as t2 "
					+ " on t1.fk_announce_id = t2.id_announce "
					+ " WHERE t1.fk_user_id = '" + idMember +"' "
					+ " and t1.bid = "
					+ "(select max(t3.bid) from dbauctionbid as t3 "
					+ " WHERE t3.fk_user_id = '" + idMember +"' "
					+ " and t3.fk_announce_id = t1.fk_announce_id)"
					+ " order by t1.date_created desc;";		
		
		String[][] aliasSQL = { {"dbauctionbid", "t1"}, {"dbannounce", "t2"}, {"dbauctionbid", "t3"} };
		SQL = CS.transformSQLReal(SQL, aliasSQL);
		HashMap<String, HashMap<String, String>> rows = db.query_records(SQL);
		
		log.debug("SQL bids: " + SQL);
		
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			if(rows != null){
				for(String record : rows.keySet()){
					AuctionBid objBid = new AuctionBid();
					Announce objAd = new Announce();
					//objeto lance leilao
					for(Field atributo : objBid.getClass().getDeclaredFields()) {						
						atributo.setAccessible(true);
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
						if(coluna != null && !coluna.name().equals("")){
							//seta no objeto o valor do registro, recuperado pelo nome da coluna na anotacao do objeto
							atributo.set(objBid, rows.get(record).get(coluna.name()));
							log.debug("coluna: " + coluna.name() + " - valor: " + rows.get(record).get(coluna.name()));
						}
					}
					//objeto anuncio
					for(Field atributo : objAd.getClass().getDeclaredFields()) {						
						atributo.setAccessible(true);
						ColumnILiketo coluna = atributo.getAnnotation(ColumnILiketo.class);
						if(coluna != null && !coluna.name().equals("")){
							//seta no objeto o valor do registro, recuperado pelo nome da coluna na anotacao do objeto
							atributo.set(objAd, rows.get(record).get(coluna.name()));
							log.debug("coluna: " + coluna.name() + " - valor: " + rows.get(record).get(coluna.name()));
						}
					}
					//atributos da superclasse ContentILiketo
					Field f1 = objBid.getClass().getSuperclass().getDeclaredField("id");
					Field f2 = objBid.getClass().getSuperclass().getDeclaredField("dateCreated");
					//Field f3 = objBid.getClass().getSuperclass().getDeclaredField("dateUpdated");
					f1.setAccessible(true);
					f2.setAccessible(true);
					//f3.setAccessible(true);
					//f1.set(objBid, rows.get(record).get(CS.getCOL(db, database_bid, "id")));
					f2.set(objBid, rows.get(record).get("date_created"));
					//f3.set(objBid, rows.get(record).get(CS.getCOL(db, database_bid, "date_updated")));
					
					Field f1Ad = objAd.getClass().getSuperclass().getDeclaredField("id");
					Field f2Ad = objAd.getClass().getSuperclass().getDeclaredField("dateCreated");
					Field f3Ad = objAd.getClass().getSuperclass().getDeclaredField("dateUpdated");
					f1Ad.setAccessible(true);
					f2Ad.setAccessible(true);
					f3Ad.setAccessible(true);
					//f1Ad.set(objAd, rows.get(record).get(CS.getCOL(db, "dbannounce", "id")));
					//f2Ad.set(objAd, rows.get(record).get(CS.getCOL(db, "dbannounce", "date_created")));
					//f3Ad.set(objAd, rows.get(record).get(CS.getCOL(db, "dbannounce", "date_updated")));
					
					Object[] vetor = {objBid, objAd};
					list.add(vetor);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
