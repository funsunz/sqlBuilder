package com.yoyuapp.sqlbuilder;


public class _Order implements STable{
	private static final String TABLE_NAME = "order";
	
	public static SqlColumn id = SqlColumn.of(TABLE_NAME, "id");
	public static SqlColumn name = SqlColumn.of(TABLE_NAME, "name");
	public static SqlColumn age = SqlColumn.of(TABLE_NAME, "age");
	public static SqlColumn registerTime = SqlColumn.of(TABLE_NAME, "registerTime");
	public static SqlTable TABLE = SqlTable.of(TABLE_NAME);
	
}
