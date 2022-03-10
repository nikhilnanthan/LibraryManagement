//$Id$
package com.libman.database;

import java.util.ArrayList;

public class Query {
	private ArrayList<String> column = new ArrayList<>();
	private String table;
	private ArrayList<String> conditionColumn=new ArrayList<>();
	private ArrayList<Comparable> conditionValue=new ArrayList<>();
	private ArrayList<String> groupBy = new ArrayList<>();
	private ArrayList<String> updateColumn = new ArrayList<>();
	private ArrayList<Comparable> updateValue = new ArrayList<>();
	public ArrayList<String> getUpdateColumn() {
		return updateColumn;
	}
	public void setUpdateColumn(ArrayList<String> updateCondition) {
		this.updateColumn = updateCondition;
	}
	public ArrayList<Comparable> getUpdateValue() {
		return updateValue;
	}
	public void setUpdateValue(ArrayList<Comparable> updateValue) {
		this.updateValue = updateValue;
	}
	public void setGroupBy(ArrayList<String> groupBy) {
		this.groupBy = groupBy;
	}
	public ArrayList<String> getGroupBy() {
		return groupBy;
	}
	public void setGroupby(ArrayList<String> groupBy) {
		this.groupBy = groupBy;
	}
	public ArrayList<String> getColumn() {
		return column;
	}
	public void setColumn(ArrayList<String> column) {
		this.column = column;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public ArrayList<String> getConditionColumn() {
		return conditionColumn;
	}
	public void setConditionColumn(ArrayList<String> condtionColumn) {
		this.conditionColumn = condtionColumn;
	}
	public ArrayList<Comparable> getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(ArrayList<Comparable> conditonValue) {
		this.conditionValue = conditonValue;
	}
}
