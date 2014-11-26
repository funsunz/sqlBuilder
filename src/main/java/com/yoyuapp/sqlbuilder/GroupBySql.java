package com.yoyuapp.sqlbuilder;



public class GroupBySql extends WhereSql{
	public WhereSql having(WhereSegment having){
		sb.append(" HAVING ").append(having.sb);
		this.getParams().addAll(having.getParams());
		return this;
	}
}
