package com.libman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Lend;
import com.libman.model.Organisation;
import com.libman.model.User;

public class UserService {
	
	public String deleteUser(User user , Organisation org) throws SQLException 
	{
			int orgId = org.getOrganisationId();
			int userId = user.getUserId();
			UserService userService = new UserService();
			if(!userService.isSameOrg(orgId, userId))
				return "Error";			
			Query delete = new Query();
			String table = "users";
			delete.setTable(table);
			ArrayList<String> conditionColumn = new ArrayList<String>();
			conditionColumn.add("org_id");
			conditionColumn.add("user_id");
			delete.setConditionColumn(conditionColumn);
			ArrayList<Comparable> conditionValue = new ArrayList<>();
			conditionValue.add(orgId);
			conditionValue.add(userId);
			delete.setConditionValue(conditionValue);
			int res = QueryExecutor.deleteQuery(delete);
			return "Deleted";
	}
	
	public User[] getAllUsers(Organisation org,User user) throws SQLException
	{
		int orgId = org.getOrganisationId();
		String role = user.getRole();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("role");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		conditionValue.add(role);
		select.setConditionValue(conditionValue);
		ResultSet userDetailSet = QueryExecutor.selectQuery(select);
		int length=0;
		if(userDetailSet.next())
		{
			userDetailSet.last();
			length = userDetailSet.getRow();
			userDetailSet.beforeFirst();
		}
		User[] userDetail = new User[length];
		try {
			System.out.println("ServiceBooks");
			int i=0;
			while(userDetailSet.next()) {
				userDetail[i] = new User();
				int userId = userDetailSet.getInt("user_id");
				userDetail[i].setUserId(userDetailSet.getInt("user_id"));
				userDetail[i].setFirstName(userDetailSet.getString("firstname"));
				userDetail[i].setLastName(userDetailSet.getString("lastname"));
				userDetail[i].setEmailId(userDetailSet.getString("email_id"));
				userDetail[i].setIsBanned(userDetailSet.getBoolean("isbanned"));
				User user1 = getSingleUserPenalty(userId);
				userDetail[i].setPenalty(user1.getPenalty());			
				i++;
			}
			userDetailSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDetail;
	}
	
	
	public String getUserName(int userId)
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("firstname");
		column.add("lastname");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("user_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		select.setConditionValue(conditionValue);
		ResultSet userNameSet=QueryExecutor.selectQuery(select);
		try {
			if(userNameSet.next())
				return userNameSet.getString("firstname") + userNameSet.getString("lastname");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	
	public User getUserDetail(Organisation org , User user)
	{
		
		int userId = user.getUserId();
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("user_id");
		condtionColumn.add("org_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		conditionValue.add(orgId);
		select.setConditionValue(conditionValue);
		ResultSet userSet=QueryExecutor.selectQuery(select);
		User userDetail = new User();
		try {
			if(userSet.next())
			{
				userDetail.setFirstName(userSet.getString("firstname"));
				userDetail.setLastName(userSet.getString("lastname"));
				userDetail.setAddress(userSet.getString("address"));
				userDetail.setAge(userSet.getInt("age"));
				userDetail.setEmailId(userSet.getString("email_id"));
				userDetail.setIsBanned(userSet.getBoolean("isBanned"));
				userDetail.setRole(userSet.getString("role"));
				User user1= getSingleUserPenalty(userId);
				User user2=getUserContact(userId);
				userDetail.setPhoneNo1(user2.getPhoneNo1());
				userDetail.setPhoneNo2(user2.getPhoneNo2());
				userDetail.setPenalty(user1.getPenalty());
				return userDetail;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public User getSingleUserPenalty(int userId) {
		Query selectGroupBy = new Query();
		ArrayList<String> column1  = new ArrayList<String>();
		column1.add("sum(paybill) as \"Total Penalty\" ");
		selectGroupBy.setColumn(column1);
		String table1 = "lend_detail";
		selectGroupBy.setTable(table1);
		ArrayList<String> conditionColumn1 = new ArrayList<String>();
		conditionColumn1.add("user_id");
		selectGroupBy.setConditionColumn(conditionColumn1);
		ArrayList<Comparable> conditionValue1 = new ArrayList<>();
		conditionValue1.add(userId);
		selectGroupBy.setConditionValue(conditionValue1);
		ArrayList<String> groupBy = new ArrayList<>();
		groupBy.add("user_id");
		groupBy.add("org_id");
		selectGroupBy.setGroupby(groupBy);
		ResultSet userPenaltySet = QueryExecutor.groupByQuery(selectGroupBy);
		User user = new User();
		try {
			if(userPenaltySet.next())
				user.setPenalty(userPenaltySet.getInt("Total Penalty"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public Lend[] getUserPenalty(Organisation org) throws SQLException
	{
		int orgId = org.getOrganisationId();
		Query selectGroupBy = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("user_id");
		column.add("sum(paybill) as \"Total Penalty\" ");
		selectGroupBy.setColumn(column);
		String table = "lend_detail";
		selectGroupBy.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("org_id");
		selectGroupBy.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		selectGroupBy.setConditionValue(conditionValue);
		ArrayList<String> groupBy = new ArrayList<>();
		groupBy.add("user_id");
		groupBy.add("org_id");
		selectGroupBy.setGroupby(groupBy);
		UserService serviceUser = new UserService();
		ResultSet userPenaltySet = QueryExecutor.groupByQuery(selectGroupBy);
		int length=0;
		if(userPenaltySet.next())
		{
			userPenaltySet.last();
			length = userPenaltySet.getRow();
			userPenaltySet.beforeFirst();
		}
		Lend[] penaltyDetail = new Lend[length];
		try {
			System.out.println("ServiceBooks");
			int i=0;
			while(userPenaltySet.next()) {
				penaltyDetail[i]=new Lend();
				int userId = userPenaltySet.getInt("user_id");
				penaltyDetail[i].setUserName(serviceUser.getUserName(userId));
				penaltyDetail[i].setPaybill(userPenaltySet.getInt("Total Penalty"));
				i++;
			}
			userPenaltySet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return penaltyDetail;
	}
	
	
	public User getUserContact(int userId)
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("phone_no");
		select.setColumn(column);
		String table = "contact";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("user_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		select.setConditionValue(conditionValue);
		ResultSet contactSet=QueryExecutor.selectQuery(select);
		User user = new User();
		try {
			if(contactSet.next()) {
				user.setPhoneNo1(contactSet.getLong("phone_no"));}
			if(contactSet.next()) {
				user.setPhoneNo2(contactSet.getLong("phone_no"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	
	public String updatePenalty(Organisation org)
	{

		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("lend_id");
		column.add("return_date");
		select.setColumn(column);
		String table = "lend_detail";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("org_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		select.setConditionValue(conditionValue);
		ResultSet lendDetailSet=QueryExecutor.selectQuery(select);
		
		Query select1 = new Query();
		ArrayList<String> column1  = new ArrayList<String>();
		column1.add("current_date as \"cur_date\"");
		select1.setColumn(column1);
		ResultSet Set=QueryExecutor.selectQuery(select1);
		try {
			if(Set.next())
			{
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while(lendDetailSet.next()) {
				Query update  = new Query();
				String table1 = "lend_detail";
				update.setTable(table1);
				ArrayList<String> updateColumn = new ArrayList<String>();
				ArrayList<Comparable> updateValue = new ArrayList<>();
				updateColumn.add("paybill");
				Date returnDate = lendDetailSet.getDate("return_date");
				Date currentDate = Set.getDate("cur_date");
				int lendId = lendDetailSet.getInt("lend_id");
				System.out.print(returnDate+" "+currentDate+" ");
				long difference_In_Time
	                = currentDate.getTime() - returnDate.getTime();
	            long days
	                = TimeUnit.MILLISECONDS.toDays(difference_In_Time)% 365;
	            if(days<0)
	            {
	            	days=0;
	            }
				updateValue.add(days*100);
				System.out.println(days);
				update.setUpdateColumn(updateColumn);
				update.setUpdateValue(updateValue);
				ArrayList<String> conditionColumn1 = new ArrayList<>();
				ArrayList<Comparable> conditionValue1 = new ArrayList<>();
				conditionColumn1.add("org_id");
				conditionValue1.add(orgId);
				conditionColumn1.add("isreturned");
				conditionValue1.add(false);
				conditionColumn1.add("lend_id");
				conditionValue1.add(lendId);
				update.setConditionColumn(conditionColumn1);
				update.setConditionValue(conditionValue1);
				int res = QueryExecutor.updateQuery(update);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "updated";
	}
	
	public String setBookReturned(Organisation org,Lend lend)
	{
		int orgId = org.getOrganisationId();
		int lendId = lend.getLendId();
		String returnDate = lend.getReturnDate();
		Query update  = new Query();
		String table1 = "lend_detail";
		update.setTable(table1);
		ArrayList<String> updateColumn = new ArrayList<String>();
		ArrayList<Comparable> updateValue = new ArrayList<>();
		updateColumn.add("isreturned");
		updateValue.add(true);
		updateColumn.add("return_date");
		updateValue.add(returnDate);
		update.setUpdateColumn(updateColumn);
		update.setUpdateValue(updateValue);
		ArrayList<String> conditionColumn1 = new ArrayList<>();
		ArrayList<Comparable> conditionValue1 = new ArrayList<>();
		conditionColumn1.add("lend_id");
		conditionValue1.add(lendId);
		conditionColumn1.add("org_id");
		conditionValue1.add(orgId);
		update.setConditionColumn(conditionColumn1);
		update.setConditionValue(conditionValue1);
		int res = QueryExecutor.updateQuery(update);
		return "updated";
	}
	
	public String setPenalty(Organisation org,User user)
	{
		int orgId = org.getOrganisationId();
		int userId = user.getUserId();
		Query update  = new Query();
		String table1 = "lend_detail";
		update.setTable(table1);
		ArrayList<String> updateColumn = new ArrayList<String>();
		ArrayList<Comparable> updateValue = new ArrayList<>();
		updateColumn.add("paybill");
		updateValue.add(0);
		update.setUpdateColumn(updateColumn);
		update.setUpdateValue(updateValue);
		ArrayList<String> conditionColumn1 = new ArrayList<>();
		ArrayList<Comparable> conditionValue1 = new ArrayList<>();
		conditionColumn1.add("user_id");
		conditionValue1.add(userId);
		conditionColumn1.add("org_id");
		conditionValue1.add(orgId);
		update.setConditionColumn(conditionColumn1);
		update.setConditionValue(conditionValue1);
		int res = QueryExecutor.updateQuery(update);
		return "updated";
	}
	
	public User[] getUserByName(Organisation org,User user) {
		String firstName = user.getFirstName();
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "users where org_id="+orgId+" and firstName like '"+firstName+"%' or lastName like '"+firstName+"%'";
		select.setTable(table);
		ResultSet userDetailSet=QueryExecutor.selectQuery(select);
		int length=0;
		try {
			if(userDetailSet.next())
			{
				userDetailSet.last();
				length = userDetailSet.getRow();
				userDetailSet.beforeFirst();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User[] userDetail = new User[length];
		try {
			System.out.println("ServiceBooks");
			int i=0;
			while(userDetailSet.next()) {
				userDetail[i] = new User();
				int userId = userDetailSet.getInt("user_id");
				userDetail[i].setUserId(userDetailSet.getInt("user_id"));
				userDetail[i].setFirstName(userDetailSet.getString("firstname"));
				userDetail[i].setLastName(userDetailSet.getString("lastname"));
				userDetail[i].setEmailId(userDetailSet.getString("email_id"));
				userDetail[i].setIsBanned(userDetailSet.getBoolean("isbanned"));
				User user1 = getSingleUserPenalty(userId);
				userDetail[i].setPenalty(user1.getPenalty());			
				i++;
			}
			userDetailSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userDetail;
	}
	
	public User[] getBannedUser(Organisation org) throws SQLException
	{
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("*");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("role");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(orgId);
		conditionValue.add("user");
		select.setConditionValue(conditionValue);
		ResultSet userBannedSet = QueryExecutor.selectQuery(select);
		int length=0;
		if(userBannedSet.next())
		{
			userBannedSet.last();
			length = userBannedSet.getRow();
			userBannedSet.beforeFirst();
		}
		User[] userBanned = new User[length];
		try {
			System.out.println("ServiceBooks");
			int i=0;
			while(userBannedSet.next()) {
				boolean isBanned = userBannedSet.getBoolean("isbanned");
				if(isBanned) {
					userBanned[i] = new User();
					userBanned[i].setUserId(userBannedSet.getInt("user_id"));
					userBanned[i].setFirstName(userBannedSet.getString("firstname"));
					userBanned[i].setLastName(userBannedSet.getString("lastname"));
					userBanned[i].setIsBanned(isBanned);
					i++;
				}
			}
			userBannedSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userBanned;
	}
	
	public boolean banOrUnBanUser(User user , Organisation org) throws SQLException
	{
		int orgId = org.getOrganisationId();
		int userId = user.getUserId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("isBanned");
		select.setColumn(column);
		String table1 = "users";
		select.setTable(table1);
		ArrayList<String> conditionColumn1 = new ArrayList<String>();
		conditionColumn1.add("user_id");
		select.setConditionColumn(conditionColumn1);
		ArrayList<Comparable> conditionValue1 = new ArrayList<>();
		conditionValue1.add(userId);
		select.setConditionValue(conditionValue1);
		ResultSet userBanSet=QueryExecutor.selectQuery(select);
		Boolean isBanned = null;
		try {
			if(userBanSet.next())
				isBanned = userBanSet.getBoolean("isBanned");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Query update  = new Query();
		String table = "users";
		update.setTable(table);
		ArrayList<String> updateColumn = new ArrayList<String>();
		ArrayList<Comparable> updateValue = new ArrayList<>();
		updateColumn.add("isBanned");
		updateValue.add(!isBanned);
		update.setUpdateColumn(updateColumn);
		update.setUpdateValue(updateValue);
		ArrayList<String> conditionColumn = new ArrayList<>();
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionColumn.add("user_id");
		conditionValue.add(userId);
		update.setConditionColumn(conditionColumn);
		update.setConditionValue(conditionValue);
		int res = QueryExecutor.updateQuery(update);
		return true;
	}
	
	public boolean isUserRole(int userId,String role) throws SQLException
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("role");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("user_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		select.setConditionValue(conditionValue);
		ResultSet userRoleSet=QueryExecutor.selectQuery(select);
		String roleDB=null;
		if(userRoleSet.next())
			roleDB = userRoleSet.getString("role");
		if(role.equals(roleDB))
			return true;
		else
			return false;
	}
	
	public boolean isSameOrg(int orgId, int userId) throws SQLException
	{
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("org_id");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> condtionColumn = new ArrayList<String>();
		condtionColumn.add("user_id");
		select.setConditionColumn(condtionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(userId);
		select.setConditionValue(conditionValue);
		ResultSet userOrgSet=QueryExecutor.selectQuery(select);
		int orgIdDB = 0;
		if(userOrgSet.next())
			orgIdDB = userOrgSet.getInt("org_id");
		if(orgId == orgIdDB)
			return true;
		else
			return false;
	}
	
	
	public User[] getNoReadersPublisher(Organisation org) throws SQLException
	{
		int orgId = org.getOrganisationId();
		Query select = new Query();
		ArrayList<String> column  = new ArrayList<String>();
		column.add("user_id");
		select.setColumn(column);
		String table = "users";
		select.setTable(table);
		ArrayList<String> conditionColumn = new ArrayList<String>();
		conditionColumn.add("org_id");
		conditionColumn.add("role");
		select.setConditionColumn(conditionColumn);
		ArrayList<Comparable> conditionValue = new ArrayList<>();
		conditionValue.add(org.getOrganisationId());
		conditionValue.add("publisher");
		select.setConditionValue(conditionValue);
		ResultSet publisherIdSet=QueryExecutor.selectQuery(select);
		int length=0;
		if(publisherIdSet.next())
		{
			publisherIdSet.last();
			length = publisherIdSet.getRow();
			publisherIdSet.beforeFirst();
		}
		int publisherId[] = new int[length];
		int i=0;
		while(publisherIdSet.next())
		{
			publisherId[i++] = publisherIdSet.getInt("user_id");
		}
		ArrayList<Integer> noReadersPublisherList = new ArrayList<>();
		for(int ind=0;ind<i;ind++)
		{
			Query select1 = new Query();
			ArrayList<String> column1  = new ArrayList<String>();
			column1.add("book_id");
			select1.setColumn(column1);
			String table1 = "book_detail";
			select1.setTable(table1);
			ArrayList<String> conditionColumn1 = new ArrayList<String>();
			conditionColumn1.add("org_id");
			conditionColumn1.add("publisher_id");
			select1.setConditionColumn(conditionColumn1);
			ArrayList<Comparable> conditionValue1 = new ArrayList<>();
			conditionValue1.add(org.getOrganisationId());
			conditionValue1.add(publisherId[ind]);
			select1.setConditionValue(conditionValue1);
			ResultSet bookDetailSet=QueryExecutor.selectQuery(select1);
			while(bookDetailSet.next())
			{
				int bookId = bookDetailSet.getInt("book_id");
				Query select2 = new Query();
				ArrayList<String> column2  = new ArrayList<String>();
				column2.add("lend_id");
				select2.setColumn(column2);
				String table2 = "lend_detail";
				select2.setTable(table2);
				ArrayList<String> conditionColumn2 = new ArrayList<String>();
				conditionColumn2.add("org_id");
				conditionColumn2.add("book_id");
				select2.setConditionColumn(conditionColumn2);
				ArrayList<Comparable> conditionValue2 = new ArrayList<>();
				conditionValue2.add(org.getOrganisationId());
				conditionValue2.add(bookId);
				select2.setConditionValue(conditionValue2);
				ResultSet lendDetailSet=QueryExecutor.selectQuery(select2);
				if(lendDetailSet.next())
				{
					break;
				}
				else
				{
					noReadersPublisherList.add(publisherId[ind]);
				}
			}
		}
		User[] noReadersPublisherObj = new User[noReadersPublisherList.size()];
		for(int ind=0;ind<noReadersPublisherList.size();ind++)
		{
			noReadersPublisherObj[ind] = new User();
			noReadersPublisherObj[ind].setUserId(noReadersPublisherList.get(ind));
		}
		return noReadersPublisherObj;
	}
}
