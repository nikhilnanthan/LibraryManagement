//$Id$
package com.libman.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Book;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.User;

public class LendBookService {
	public String lendBook(User user ,Book book , Lend lend,Organisation org)
	{
		int bookId = book.getBookId();
		int userId = user.getUserId();
		int orgId = org.getOrganisationId();
		String issuedDate = lend.getIssuedDate();
		String returnDate = lend.getReturnDate();
		Query insert = new Query(); 
		String table = "lend_detail";
		insert.setTable(table);
		ArrayList<String> columns = new ArrayList<>();
		columns.add("user_id");
		columns.add("book_id");
		columns.add("org_id");
		columns.add("issued_date");
		columns.add("return_date");
		insert.setConditionColumn(columns);
		ArrayList<Comparable> values = new ArrayList<>();
		values.add(userId);
		values.add(bookId);
		values.add(orgId);
		values.add(issuedDate);
		values.add(returnDate);
		insert.setConditionValue(values);
		int res1=QueryExecutor.insertQuery(insert);
		return "booked";
	}
}
