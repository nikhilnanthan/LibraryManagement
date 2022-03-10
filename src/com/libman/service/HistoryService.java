package com.libman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.User;

public class HistoryService {
	public Lend[] getHistory(User user,Organisation org) throws SQLException
	{
		int orgId = org.getOrganisationId();
		BookService serviceBook= new BookService();
		int userId = user.getUserId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "lend_detail";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("user_id");
		conditionColumn.add("org_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		conditionValue.add(orgId);
		select.setConditionValue(conditionValue);                            //history of the books lent by the users
		ResultSet lendDetailsSet = QueryExecutor.selectQuery(select);
		int length=0;
		if(lendDetailsSet.next())
		{
			lendDetailsSet.last();
			length = lendDetailsSet.getRow();
			lendDetailsSet.beforeFirst();
		}
		Lend[] lendDetail = new Lend[length];
			int i=0;
	    	 while(lendDetailsSet.next()) {
	    		 	System.out.println("serviceUser");
	    		 	lendDetail[i] = new Lend();
					int bookId =  lendDetailsSet.getInt("book_id");
					lendDetail[i].setLendId(lendDetailsSet.getInt("lend_id"));
					lendDetail[i].setBookTitle(serviceBook.getBookTitle(bookId));
					lendDetail[i].setBookId(bookId);
					lendDetail[i].setIssuedDate(lendDetailsSet.getDate("issued_date").toString());
					lendDetail[i].setReturnDate(lendDetailsSet.getDate("return_date").toString());
					lendDetail[i].setIsReturned(lendDetailsSet.getBoolean("isreturned"));
					lendDetail[i].setPaybill(lendDetailsSet.getInt("paybill"));
					i++;
	    	 }
		return lendDetail;
	}
}
