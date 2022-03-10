//$Id$
package com.libman.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class QueryExecutor {
	
	public static ResultSet selectQuery(Query select)
	{
		String finalQuery="select ";
		ArrayList<String> column = select.getColumn();
		
		if(column.size()==1) {
			finalQuery = finalQuery + column.get(0);
		}
		else
		{
			for(int i=0;i<((column.size())-1);i++)
			{
				finalQuery = finalQuery+column.get(i)+",";
			}
			finalQuery = finalQuery+column.get(column.size()-1);
		}
		if(select.getTable()!=null)
			finalQuery = finalQuery+" from "+select.getTable()+" ";
		
		ArrayList<String> conditionColumn = select.getConditionColumn();
		ArrayList<Comparable> conditionValue = select.getConditionValue();
		
		if(conditionValue.size()!=0) {
			if(conditionColumn.size()==1) {
				finalQuery = finalQuery + "where ";
				if(conditionValue.get(0) instanceof String)
					finalQuery = finalQuery + conditionColumn.get(0)+"="+"'"+conditionValue.get(0)+"'";
				else
					finalQuery = finalQuery + conditionColumn.get(0)+"="+conditionValue.get(0);
			}
			else {
				finalQuery = finalQuery + "where ";
				for(int i=0;i<((conditionColumn.size())-1);i++)
				{
					if(conditionValue.get(i) instanceof String)
						finalQuery = finalQuery+conditionColumn.get(i)+"="+"'"+conditionValue.get(i)+"'"+" and ";
					else
						finalQuery = finalQuery + conditionColumn.get(i)+"="+conditionValue.get(i)+" and ";
				}
				System.out.println(finalQuery);
				if(conditionValue.get(conditionValue.size()-1) instanceof String)
					finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+"'"+conditionValue.get(conditionValue.size()-1)+"'";
				else
					finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+conditionValue.get(conditionValue.size()-1);
			}
		}
		Connection connection = DatabaseConnection.createConnection();
		Statement smt=null;
		ResultSet resultSet=null;
		try {
			System.out.println(finalQuery);
			smt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			resultSet = smt.executeQuery(finalQuery);
//			System.out.println("Query executed Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
	
	public static int insertQuery(Query insert)
	{
		String tables = insert.getTable();
		String finalQuery="insert into "+tables+"(";
		ArrayList<String> columns = insert.getConditionColumn();
		if(columns.size()!=1) {
			for(int i=0;i<((columns.size())-1);i++)
			{
			    finalQuery = finalQuery+columns.get(i)+",";
			}
		}
		finalQuery = finalQuery+columns.get(columns.size()-1);
		finalQuery=finalQuery+") values(";
		ArrayList<Comparable> values = insert.getConditionValue();
		if((values).size()!=1)
			for(int i=0;i<(values.size()-1);i++)
			{
				if(values.get(i) instanceof String)
					finalQuery = finalQuery+"'"+values.get(i)+"'"+",";
				else
					finalQuery = finalQuery+values.get(i)+",";
			}
		if(values.get(values.size()-1) instanceof String)
			finalQuery = finalQuery+"'"+values.get(values.size()-1)+"'";
		else
			finalQuery = finalQuery+values.get(values.size()-1);
		finalQuery =  finalQuery+")";
		System.out.println(finalQuery);
		Connection connection = DatabaseConnection.createConnection();
		Statement smt=null;
		int res=0;
		try {
			System.out.println(finalQuery);
			smt = connection.createStatement();
			res = smt.executeUpdate(finalQuery);
	//		System.out.println("Query executed Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return res;
	}
	
	
	public static int updateQuery(Query update)
	{
		String finalQuery ="update";
		String table = update.getTable();
		finalQuery = finalQuery+" "+ table +" set ";
		ArrayList<String> updateColumn = update.getUpdateColumn();
		ArrayList<Comparable> updateValue = update.getUpdateValue();
		if(updateColumn.size()==1) {
			if(updateValue.get(0) instanceof String)
				finalQuery = finalQuery + updateColumn.get(0)+"="+"'"+updateValue.get(0)+"'";
			else
				finalQuery = finalQuery + updateColumn.get(0)+"="+updateValue.get(0);
		}
		else {
			for(int i=0;i<((updateColumn.size())-1);i++)
			{
				if(updateValue.get(i) instanceof String)
					finalQuery = finalQuery+updateColumn.get(i)+"="+"'"+updateValue.get(i)+"'"+",";
				else
					finalQuery = finalQuery + updateColumn.get(i)+"="+updateValue.get(i)+",";
			}
			if(updateValue.get(updateColumn.size()-1) instanceof String)
				finalQuery = finalQuery+updateColumn.get(updateColumn.size()-1)+"="+"'"+updateValue.get(updateValue.size()-1)+"'";
			else
				finalQuery = finalQuery+updateColumn.get(updateColumn.size()-1)+"="+updateValue.get(updateValue.size()-1);
		}
		ArrayList<String> conditionColumn = update.getConditionColumn();
		ArrayList<Comparable> conditionValue = update.getConditionValue();
		if(conditionColumn.size()==1) {
			finalQuery = finalQuery + " where ";
			if(conditionValue.get(0) instanceof String)
				finalQuery = finalQuery + conditionColumn.get(0)+"="+"'"+conditionValue.get(0)+"'";
			else
				finalQuery = finalQuery + conditionColumn.get(0)+"="+conditionValue.get(0);
		}
		else {
			finalQuery = finalQuery + " where ";
			for(int i=0;i<((conditionColumn.size())-1);i++)
			{
				if(conditionValue.get(i) instanceof String)
					finalQuery = finalQuery+conditionColumn.get(i)+"="+"'"+conditionValue.get(i)+"'"+" and ";
				else
					finalQuery = finalQuery + conditionColumn.get(i)+"="+conditionValue.get(i)+" and ";
				System.out.println(i);
			}
			if(conditionValue.get(conditionColumn.size()-1) instanceof String)
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+"'"+conditionValue.get(conditionValue.size()-1)+"'";
			else
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+conditionValue.get(conditionValue.size()-1);
		}
		System.out.println(finalQuery);
		Connection connection = DatabaseConnection.createConnection();
		Statement smt=null;
		int res=0;
		try {
			System.out.println(finalQuery);
			smt = connection.createStatement();
			res = smt.executeUpdate(finalQuery);
	//		System.out.println("Query executed Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = null;
		conditionValue.clear();
		conditionColumn.clear();
		updateValue.clear();
		updateColumn.clear();
		return res;
	}
	
	public static int deleteQuery(Query delete)
	{
		String finalQuery = "delete from "+delete.getTable();
		ArrayList<String> conditionColumn = delete.getConditionColumn();
		ArrayList<Comparable> conditionValue = delete.getConditionValue();
		if(conditionColumn.size()==1) {
			finalQuery = finalQuery + " where ";
			if(conditionValue.get(0) instanceof String)
				finalQuery = finalQuery + conditionColumn.get(0)+"="+"'"+conditionValue.get(0)+"'";
			else
				finalQuery = finalQuery + conditionColumn.get(0)+"="+conditionValue.get(0);
		}
		else {
			finalQuery = finalQuery + " where ";
			for(int i=0;i<((conditionColumn.size())-1);i++)
			{
				if(conditionValue.get(i) instanceof String)
					finalQuery = finalQuery+conditionColumn.get(i)+"="+"'"+conditionValue.get(i)+"'"+" and ";
				else
					finalQuery = finalQuery + conditionColumn.get(i)+"="+conditionValue.get(i)+" and ";
			}
			if(conditionValue.get(conditionColumn.size()-1) instanceof String)
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+"'"+conditionValue.get(conditionValue.size()-1)+"'";
			else
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-1)+"="+conditionValue.get(conditionValue.size()-1);
		}
		Connection connection = DatabaseConnection.createConnection();
		Statement smt=null;
		int res=0;
		try {
			System.out.println(finalQuery);
			smt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			res = smt.executeUpdate(finalQuery);
//			System.out.println("Query executed Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static ResultSet groupByQuery(Query selectGroup)
	{

		String finalQuery="select ";
		ArrayList<String> column = selectGroup.getColumn();
		if(column.size()==1) {
			finalQuery = finalQuery + column.get(0);
		}
		else
		{
			for(int i=0;i<((column.size())-1);i++)
			{
				finalQuery = finalQuery+column.get(i)+",";
			}
			finalQuery = finalQuery+column.get(column.size()-1);
		}
		finalQuery = finalQuery+" from "+selectGroup.getTable()+" ";
		ArrayList<String> conditionColumn = selectGroup.getConditionColumn();
		ArrayList<Comparable> conditionValue = selectGroup.getConditionValue();
		ArrayList<String> groupBy = selectGroup.getGroupBy();
		finalQuery = finalQuery+ " group by ";
		if(groupBy.size()==1) {
			finalQuery = finalQuery + groupBy.get(0);
		}
		else
		{
			for(int i=0;i<((groupBy.size())-1);i++)
			{
				finalQuery = finalQuery+groupBy.get(i)+",";
			}
			finalQuery = finalQuery+groupBy.get(groupBy.size()-1);
		}
		if(conditionColumn.size()==1) {
			finalQuery = finalQuery + " having ";
			if(conditionValue.get(0) instanceof String)
				finalQuery = finalQuery + conditionColumn.get(0)+"="+"'"+conditionValue.get(0)+"'";
			else
				finalQuery = finalQuery + conditionColumn.get(0)+"="+conditionValue.get(0);
		}
		else {
			finalQuery = finalQuery + " having ";
			for(int i=0;i<((conditionColumn.size())-2);i++)
			{
				if(conditionValue.get(i) instanceof String)
					finalQuery = finalQuery+conditionColumn.get(i)+"="+"'"+conditionValue.get(i)+"'"+",";
				else
					finalQuery = finalQuery + conditionColumn.get(i)+"="+conditionValue.get(i);
			}
			if(conditionValue.get(conditionColumn.size()-2) instanceof String)
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-2)+"="+"'"+conditionValue.get(conditionValue.size()-2)+"'";
			else
				finalQuery = finalQuery+conditionColumn.get(conditionColumn.size()-2)+"="+conditionValue.get(conditionValue.size()-2);
		}
		Connection connection = DatabaseConnection.createConnection();
		Statement smt=null;
		ResultSet resultSet=null;
		try {
			System.out.println(finalQuery);
			smt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			resultSet = smt.executeQuery(finalQuery);
//			System.out.println("Query executed Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}
}
