package com.yoyuapp.sqlbuilder;


import java.util.List;


public class QuerySql extends SqlSegment{

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
	
	public QuerySql union(QuerySql querySQL){
		this.sb.append(" UNION ").append(querySQL.sb);
		this.params.addAll(querySQL.getParams());
		return this;
	}
	
	public QuerySql unionAll(QuerySql querySQL){
		this.sb.append(" UNION ALL ").append(querySQL.sb);
		this.params.addAll(querySQL.getParams());
		return this;
	}

}
