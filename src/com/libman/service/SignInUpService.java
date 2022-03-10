package com.libman.service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.libman.database.QueryExecutor;
import com.libman.database.Query;
import com.libman.model.Organisation;
import com.libman.model.User;

public class SignInUpService{
	
		public String signIn(User user,Organisation org)
		{
			String emailId=user.getEmailId();
			String passWord = user.getPassWord();
			System.out.println("Password"+passWord);
			String role=user.getRole();
			int orgId = 0;
		//get orgId from orgName
			Query select = new Query();
			ArrayList<String> column  = new ArrayList<String>();
			column.add("org_id");
			select.setColumn(column);
			String table = "organisation";
			select.setTable(table);
			ArrayList<String> conditionColumn = new ArrayList<String>();
			conditionColumn.add("org_name");
			select.setConditionColumn(conditionColumn);
			ArrayList<Comparable> conditionValue = new ArrayList<>();
			conditionValue.add(org.getOrganisationName());
			select.setConditionValue(conditionValue);
			ResultSet orgIDSet = QueryExecutor.selectQuery(select);
			try {
				if(orgIDSet.next())
					orgId = orgIDSet.getInt("org_id");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//apply it in user
			Query select1 = new Query();
			ArrayList<String> column1  = new ArrayList<String>();
			column1.add("password");
			column1.add("user_id");
			column1.add("org_id");
			select1.setColumn(column1);
			String table1 = "users";
			select1.setTable(table1);
			ArrayList<String> conditionColumn1 = new ArrayList<String>();
			conditionColumn1.add("email_id");
			conditionColumn1.add("org_id");
			conditionColumn1.add("role");
			select1.setConditionColumn(conditionColumn1);
			ArrayList<Comparable> conditionValue1 = new ArrayList<>();
			conditionValue1.add(emailId);
			conditionValue1.add(orgId);
			conditionValue1.add(role);
			select1.setConditionValue(conditionValue1);
			ResultSet emailExists = QueryExecutor.selectQuery(select1);
			try {
				if(!emailExists.next())
				{
					return "EmailId does not exists";
				}
				else
				{
					System.out.println(emailExists.getString("password"));
					String dbPassword=null;								//check credentials
					int dbUserId=0;
					dbPassword=emailExists.getString("password");
					System.out.println(dbPassword);
					if(dbPassword.equals(passWord))
					{
						dbUserId = emailExists.getInt("user_id");
						user.setUserId(dbUserId);
						org.setOrganisationId(emailExists.getInt("org_id"));
						return "success";
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Wrong Password";
		}
		
		public String signUp(User user,Organisation org) throws SQLException
		{
			//get orgId
			Query select = new Query();
			ArrayList<String> column  = new ArrayList<String>();
			column.add("org_id");
			select.setColumn(column);
			String table = "organisation";
			select.setTable(table);
			ArrayList<String> conditionColumn = new ArrayList<String>();
			conditionColumn.add("org_name");
			select.setConditionColumn(conditionColumn);
			ArrayList<Comparable> conditionValue = new ArrayList<>();
			conditionValue.add(org.getOrganisationName());
			select.setConditionValue(conditionValue);
			ResultSet orgIDSet = QueryExecutor.selectQuery(select);
			int orgId=0;
			try {
				if(orgIDSet.next())
					orgId = orgIDSet.getInt("org_id");
				else
					return "invalid Organisation";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			String firstName=user.getFirstName();
			String lastName=user.getLastName();
			Long phoneNo1=user.getPhoneNo1();
			Long phoneNo2=user.getPhoneNo2();
			int age=user.getAge();
			String address=user.getAddress();
			String emailId=user.getEmailId();
			String password=user.getPassWord();
			String role=user.getRole();
			
			//check email_id exists
			Query select2 = new Query();
			ArrayList<String> column2  = new ArrayList<String>();
			column2.add("user_id");
			select2.setColumn(column2);
			String table2 = "users";
			select2.setTable(table2);
			ArrayList<String> conditionColumn2 = new ArrayList<String>();
			conditionColumn2.add("email_id");
			conditionColumn2.add("org_id");
			select2.setConditionColumn(conditionColumn2);
			ArrayList<Comparable> conditionValue2 = new ArrayList<>();
			conditionValue2.add(emailId);
			conditionValue2.add(orgId);
			select2.setConditionValue(conditionValue2);
			ResultSet emailAlreadyExists = QueryExecutor.selectQuery(select2);
			if(emailAlreadyExists.next())
			{
				return "Emailid aleady exists";
			}
			//insert details
			Query insert = new Query();
			String tables = "users";
			insert.setTable(tables);
			ArrayList<String> columns = new ArrayList<>();
			columns.add("org_id");
			columns.add("firstname");
			columns.add("lastname");
			columns.add("age");
			columns.add("address");
			columns.add("email_id");
			columns.add("password");
			columns.add("role");
			insert.setConditionColumn(columns);
			ArrayList<Comparable> values = new ArrayList<>();
			values.add(orgId);
			values.add(firstName);
			values.add(lastName);
			values.add(age);
			values.add(address);
			values.add(emailId);
			values.add(password);
			values.add(role);
			insert.setConditionValue(values);
			int res1=QueryExecutor.insertQuery(insert);
			
			//get userId
			Query select1 = new Query();
			ArrayList<String> column1  = new ArrayList<String>();
			column1.add("user_id");
			select1.setColumn(column1);
			String table1 = "users";
			select1.setTable(table1);
			ArrayList<String> conditionColumn1 = new ArrayList<String>();
			conditionColumn1.add("email_id");
			conditionColumn1.add("org_id");
			select1.setConditionColumn(conditionColumn1);
			ArrayList<Comparable> conditionValue1 = new ArrayList<>();
			conditionValue1.add(emailId);
			conditionValue1.add(orgId);
			select1.setConditionValue(conditionValue1);
			ResultSet userIDSet = QueryExecutor.selectQuery(select1);
			int userId=0;
			try {
				if(userIDSet.next())
					userId = userIDSet.getInt("user_id");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			Query insert2 = new Query();
			String tables2="contact";
			insert2.setTable(tables2);
			ArrayList<String> columns2  = new ArrayList<String>();
			columns2.add("user_id");
			columns2.add("phone_no");
			columns2.add("org_id");
			insert2.setConditionColumn(columns2);
			ArrayList<Comparable> values2  = new ArrayList<Comparable>();
			values2.add(userId);
			values2.add(phoneNo1);
			values2.add(orgId);
			insert2.setConditionValue(values2);
			int res2 = QueryExecutor.insertQuery(insert2);
			if(phoneNo2!=0)
			{
				Query insert3 = new Query();
				String tables3="contact";
				insert3.setTable(tables3);
				ArrayList<String> columns3  = new ArrayList<String>();
				columns3.add("user_id");
				columns3.add("phone_no");
				columns3.add("org_id");
				insert3.setConditionColumn(columns3);
				ArrayList<Comparable> values3  = new ArrayList<Comparable>();
				values3.add(userId);
				values3.add(phoneNo2);
				values3.add(orgId);
				insert3.setConditionValue(values3);
				int res3 = QueryExecutor.insertQuery(insert3);
			}
			org.setOrganisationId(orgId);
			user.setUserId(userId);
			return "created";
		}
}
