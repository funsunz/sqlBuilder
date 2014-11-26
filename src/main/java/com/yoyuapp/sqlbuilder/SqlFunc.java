package com.yoyuapp.sqlbuilder;


public class SqlFunc{

	private SqlFunc() {
		super();
	}
	
	public static SqlColumn count(SqlColumn column){
		StringBuilder s = new StringBuilder("COUNT(").append(column.sb).append(")");
		SqlColumn c = new SqlColumn(s.toString());
		return c;
	}
	
	public static SqlColumn distinct(SqlColumn column){
		SqlColumn c = new SqlColumn(column.sb.insert(0, "DISTINCT(").append(")").toString());
		return c;
	}
	
	public static SqlColumn count(){
		SqlColumn c = new SqlColumn("COUNT(*)");
		return c;
	}
	
	public static SqlColumn now(){
		SqlColumn c = new SqlColumn("NOW()");
		return c;
	}

}
