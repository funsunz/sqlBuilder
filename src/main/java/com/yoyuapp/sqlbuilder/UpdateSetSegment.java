package com.yoyuapp.sqlbuilder;


public class UpdateSetSegment extends UpdateSql{
	public UpdateSetSegment set(SqlColumn column, Object value){
		UpdateSetSegment up = new UpdateSetSegment();
		up.sb = this.sb;
		up.params = this.params;
		
		up.sb.append(", ");
		sb.append(column.sb).append(" = ?");
		
		up.params.add(value);
		return up;
	}
	
	public UpdateSql where(WhereSegment conj){
		this.sb.append(" WHERE ").append(conj.toString());
		this.params.addAll(conj.params);
		return this;
	}
}
