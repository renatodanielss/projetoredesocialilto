package com.iliketo.dao;

import javax.servlet.http.HttpServletRequest;

import com.iliketo.model.Announce;
import com.iliketo.util.ColumnsSingleton;

import HardCore.DB;

public class AuctionBidDAO extends GenericDAO{
	
	public AuctionBidDAO(DB db, HttpServletRequest request){
		super(db, "dbauctionbid", request);
	}
	
	//metodos CRUD declarados na classe pai GenericDAO
	//outros metodos especificos da AuctionBidDAO abaixo
	
}
