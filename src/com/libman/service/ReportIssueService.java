//$Id$
package com.libman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Book;
import com.libman.model.Issue;
import com.libman.model.Organisation;
import com.libman.model.User;

public class ReportIssueService {
	public String reportIssue(User user ,Book book , Issue issue,Organisation org)
	{
		int userId = user.getUserId();
		int bookId = book.getBookId();
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("lend_id");
		select.setColumn(column);
		String table = "lend_detail";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("user_id");
		conditionColumn.add("book_id");
		conditionColumn.add("org_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		conditionValue.add(bookId);
		conditionValue.add(orgId);
		select.setConditionValue(conditionValue);
		ResultSet isBookLent = QueryExecutor.selectQuery(select);
		try {
			if(!isBookLent.next())
			{
				return "Cannot Report";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	
		String report = issue.getReport();
		Query insert = new Query();
		String table1 = "issue";
		insert.setTable(table1);
		ArrayList<String> columns1 = new ArrayList<>();
		columns1.add("user_id");
		columns1.add("book_id");
		columns1.add("issue");
		columns1.add("org_id");
		insert.setConditionColumn(columns1);
		ArrayList<Comparable> values1 = new ArrayList<>();
		values1.add(userId);
		values1.add(bookId);
		values1.add(report);
		values1.add(orgId);
		insert.setConditionValue(values1);
		int res1=QueryExecutor.insertQuery(insert);
		return "reported";
	}
	
	public Issue[] getAllReports(Organisation org) throws SQLException{
		BookService serviceBook= new BookService();
		UserService serviceUser = new UserService();
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "issue";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		select.setConditionValue(conditionValue);                        
		ResultSet reportSet = QueryExecutor.selectQuery(select);
		int length=0;
		if(reportSet.next())
		{
			reportSet.last();
			length = reportSet.getRow();
			reportSet.beforeFirst();
		}
		Issue[] issueDetail = new Issue[length];
		try {
			int i=0;
	    	 while(reportSet.next()) {
	    		 	System.out.println("serviceUser");
					int bookId = reportSet.getInt("book_id");
					int userId = reportSet.getInt("user_id"); 
					issueDetail[i] = new Issue();
					issueDetail[i].setUserId(userId);
					issueDetail[i].setBookId(bookId);
					issueDetail[i].setUserName(serviceUser.getUserName(userId));
					issueDetail[i].setBookTitle(serviceBook.getBookTitle(bookId));
					issueDetail[i].setReport(reportSet.getString("issue"));
					i++;
	    	 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  issueDetail;
	
	}
	
	public Issue[] getSingleUserReports(Organisation org , User user) throws SQLException{
		BookService serviceBook= new BookService();
		UserService serviceUser = new UserService();
		int orgId = org.getOrganisationId();
		int userId = user.getUserId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "issue";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("user_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		conditionValue.add(userId);
		select.setConditionValue(conditionValue);                        
		ResultSet reportSet = QueryExecutor.selectQuery(select);
		int length=0;
		if(reportSet.next())
		{
			reportSet.last();
			length = reportSet.getRow();
			reportSet.beforeFirst();
		}
		Issue[] issueDetail = new Issue[length];
		try {
			int i=0;
	    	 while(reportSet.next()) {
	    		 	System.out.println("serviceUser");
					int bookId = reportSet.getInt("book_id");
					issueDetail[i] = new Issue();
					issueDetail[i].setBookId(bookId);
					issueDetail[i].setBookTitle(serviceBook.getBookTitle(bookId));
					issueDetail[i].setReport(reportSet.getString("issue"));
					i++;
	    	 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  issueDetail;
	}
}
