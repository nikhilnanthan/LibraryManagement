//$Id$
package com.libman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Book;
import com.libman.model.Organisation;
import com.libman.model.User;

public class BookService {
	
	public String createBook(Organisation org,Book book,User user)
	{
		int orgId = org.getOrganisationId();
		String bookTitle = book.getBookTitle();
		String authorName = book.getAuthorName();
		String category = book.getCategory();
		int editionNo = book.getEditionNo();
		long isbnNo = book.getIsbnNo();
		int publishedYear = book.getPublishedYear();
		int price = book.getPrice();
		
		
		//get PublisherId by emailId
		String emailId = user.getEmailId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("user_id");
		column.add("role");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("email_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(emailId);
		select.setConditionValue(conditionValue);
		int publisherId=0;
		ResultSet userIdSet = QueryExecutor.selectQuery(select);
		try {
			if(userIdSet.next())
			{
				String role = userIdSet.getString("role");
				if(!role.equals("publisher"))
				{
					System.out.println(role);
					return "No publisher found";
				}
				else
				{
					System.out.println("entered");
					publisherId=userIdSet.getInt("user_id");
				}
			}
			else {
				System.out.println("false else");
				return "No publisher found";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query insert = new Query();
		table = "book_detail";
		insert.setTable(table);
		ArrayList<String> columns = new ArrayList<>();
		columns.add("org_id");
		columns.add("book_title");
		columns.add("category");
		columns.add("edition_no");
		columns.add("isbn_no");
		columns.add("author_name");
		columns.add("publisher_id");
		columns.add("published_year");
		columns.add("price");
		insert.setConditionColumn(columns);
		ArrayList<Comparable> values = new ArrayList<>();
		values.add(orgId);
		values.add(bookTitle);
		values.add(category);
		values.add(editionNo);
		values.add(isbnNo);
		values.add(authorName);
		values.add(publisherId);
		values.add(publishedYear);
		values.add(price);
		insert.setConditionValue(values);
		int res1=QueryExecutor.insertQuery(insert);
		return "book added";
	}
	
	public String updateBook(Book book , User user)
	{
		String bookTitle = book.getBookTitle();
		String authorName = book.getAuthorName();
		String category = book.getCategory();
		int editionNo = book.getEditionNo();
		long isbnNo = book.getIsbnNo();
		int publishedYear = book.getPublishedYear();
		int price = book.getPrice();
		int bookId = book.getBookId();
		
		//get PublisherId by emailId
		String emailId = user.getEmailId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("user_id");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("email_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(emailId);
		select.setConditionValue(conditionValue);
		int publisherId=0;
		ResultSet lendDetailsSet = QueryExecutor.selectQuery(select);
		try {
			if(lendDetailsSet.next())
			{
				publisherId=lendDetailsSet.getInt("user_id");
			}
			else
			{
				return "No publisher found";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query update  = new Query();
		String table1 = "book_detail";
		update.setTable(table1);
		ArrayList<String> updateColumn = new ArrayList<String>();
		ArrayList<Comparable> updateValue = new ArrayList<>();
		updateColumn.add("book_title");
		updateValue.add(bookTitle);
		updateColumn.add("category");
		updateValue.add(category);
		updateColumn.add("edition_no");
		updateValue.add(editionNo);
		updateColumn.add("isbn_no");
		updateValue.add(isbnNo);
		updateColumn.add("author_name");
		updateValue.add(authorName);
		updateColumn.add("publisher_id");
		updateValue.add(publisherId);
		updateColumn.add("published_year");
		updateValue.add(publishedYear);
		updateColumn.add("price");
		updateValue.add(price);
		update.setUpdateColumn(updateColumn);
		update.setUpdateValue(updateValue);
		ArrayList<String> conditionColumn1 = new ArrayList<>();
		ArrayList<Comparable> conditionValue1 = new ArrayList<>();
		conditionColumn1.add("book_id");
		conditionValue1.add(bookId);
		update.setConditionColumn(conditionColumn1);
		update.setConditionValue(conditionValue1);
		int res = QueryExecutor.updateQuery(update);
		return "updated";
	}
	
	public String deleteBook(Organisation org, Book book)
	{
		int orgId = org.getOrganisationId();
		int bookId = book.getBookId();
		Query delete = new Query();
		String table = "book_detail";
		delete.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("book_id");
		delete.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		conditionValue.add(bookId);
		delete.setConditionValue(conditionValue);
		int res = QueryExecutor.deleteQuery(delete);
		return "Deleted";
	}
	
	public Book[] getAllBooks(Organisation org) throws SQLException
	{
		//to display all books;
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("book_id");
		column.add("book_title");
		select.setColumn(column);
		String table = "book_detail";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("org_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(org.getOrganisationId());
		select.setConditionValue(conditionValue);
		ResultSet bookDetail=QueryExecutor.selectQuery(select);
		int length=0;
		if(bookDetail.next())
		{
			bookDetail.last();
			length = bookDetail.getRow();
			bookDetail.beforeFirst();
		}
		Book book[] = new Book[length];
		try {
			System.out.println("ServiceBooks");
			int i=0;
			while(bookDetail.next()) {
				book[i] = new Book();
				book[i].setBookId(bookDetail.getInt("book_id"));
				book[i].setBookTitle(bookDetail.getString("book_title"));
				i++;
			}
			bookDetail.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return book;
	}
	public Book getBookDetail(Book book ,Organisation org)
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "book_detail";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("book_id");
		conditionColumn.add("org_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(book.getBookId());
		conditionValue.add(org.getOrganisationId());
		select.setConditionValue(conditionValue);
		ResultSet bookDetail = QueryExecutor.selectQuery(select);
		Book bookResult = new Book();
		try {
		if(bookDetail.next())
		{
			bookResult.setBookId(bookDetail.getInt("book_id"));
			bookResult.setBookTitle(bookDetail.getString("book_title"));
			bookResult.setCategory(bookDetail.getString("category"));         //details viewed by the user
			bookResult.setEditionNo(bookDetail.getInt("edition_no"));
			bookResult.setAuthorName(bookDetail.getString("author_name"));
			bookResult.setPublishedYear(bookDetail.getInt("published_year"));
			bookResult.setPrice(bookDetail.getInt("price"));
			System.out.println("Taken");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return bookResult;
	}
	
	public boolean isBookAvailable(Book book)
	{
		 // to check if the book is available
		Query select = new Query();
		int bookId = book.getBookId();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("isreturned");
		column.add("return_date");
		select.setColumn(column);
		String table = "lend_detail";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("book_id");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(bookId);
		select.setConditionValue(conditionValue);               
		ResultSet availableSet = QueryExecutor.selectQuery(select);
		String maxDate = "0";
		boolean isAvailable= true;
		try {
		while(availableSet.next())
		{
			boolean isReturned=availableSet.getBoolean("isreturned");
			Date date = availableSet.getDate("return_date");
			String findMaxDate = date.toString();
			if(maxDate.compareTo(findMaxDate)<1)
			{
				maxDate=findMaxDate;
				System.out.println(maxDate);
				if(isReturned == true)
					isAvailable = true;
				else
					isAvailable = false;
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return isAvailable;
	}
	public String getBookTitle(int bookId)
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("book_id");
		column.add("book_title");
		select.setColumn(column);
		String table = "book_detail";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("book_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(bookId);
		select.setConditionValue(conditionValue);
		ResultSet bookTitleSet=QueryExecutor.selectQuery(select);
		try {
			if(bookTitleSet.next())
				return bookTitleSet.getString("book_title");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public Book[] showBookByCategory(Organisation org, Book book) throws SQLException
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "book_detail";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("category");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(org.getOrganisationId());
		conditionValue.add(book.getCategory());
		select.setConditionValue(conditionValue);       
		ResultSet bookDetailSet=QueryExecutor.selectQuery(select);
		int length=0;
		if(bookDetailSet.next())
		{
			bookDetailSet.last();
			length = bookDetailSet.getRow();
			bookDetailSet.beforeFirst();
		}
		Book bookByCategory[] = new Book[length];
		try {
			int i=0;
			System.out.println("ServiceBooks");
			while(bookDetailSet.next()) {
				bookByCategory[i]= new Book();
				bookByCategory[i].setBookId(bookDetailSet.getInt("book_id"));
				bookByCategory[i].setBookTitle(bookDetailSet.getString("book_title"));
				i++;
			}
			bookDetailSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookByCategory;
	}
	
	public Book[] showBookByTitle(Organisation org, Book book)
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "book_detail where org_id="+org.getOrganisationId()+" and book_title like '"+book.getBookTitle()+"%'";
		select.setTable(table);
		ResultSet bookDetailSet=QueryExecutor.selectQuery(select);
		int length=0;
		try {
			if(bookDetailSet.next())
			{
				bookDetailSet.last();
				length = bookDetailSet.getRow();
				bookDetailSet.beforeFirst();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Book bookByTitle[] = new Book[length];
		int i=0;
		try {
			System.out.println("ServiceBooks");
			while(bookDetailSet.next()) {
				bookByTitle[i]= new Book();
				bookByTitle[i].setBookId(bookDetailSet.getInt("book_id"));
				bookByTitle[i].setBookTitle(bookDetailSet.getString("book_title"));
				i++;
			}
			bookDetailSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookByTitle;
	}
	
	public boolean isBookSameOrg(int orgId, int bookId) throws SQLException
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("org_id");
		select.setColumn(column);
		String table = "book_detail";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("book_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(bookId);
		select.setConditionValue(conditionValue);
		ResultSet bookOrgSet=QueryExecutor.selectQuery(select);
		int orgIdDB = 0;
		if(bookOrgSet.next())
			orgIdDB = bookOrgSet.getInt("org_id");
		if(orgId == orgIdDB)
			return true;
		else
			return false;
	}
	
	
}

