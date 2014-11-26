package com.yoyuapp.sqlbuilder;


public class UpdateSegment extends UpdateSql{
	private boolean joinedTable;

	public UpdateSegment leftJoin(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" LEFT JOIN ", table, onColumn1, onColumn2);
	}
	
	public UpdateSegment rightJoin(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" RIGHT JOIN ", table, onColumn1, onColumn2);
	}
	
	public UpdateSegment join(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" JOIN ", table, onColumn1, onColumn2);
	}
	
	private UpdateSegment toJoin(String joinSymbol, SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		if (joinedTable){
			sb.append(",");
		}
		sb.append(joinSymbol).append(table.getName()).append(" ON ").append(onColumn1.sb).append(" = ").append(onColumn2.sb);
		joinedTable = true;
		return this;
	}
	
	public UpdateSegment and(WhereSegment condition){
		sb.append(condition);
		params.addAll(condition.getParams());
		return this;
	}
	
	public UpdateSetSegment set(SqlColumn column, SqlColumn columnRef){
		UpdateSetSegment up = new UpdateSetSegment();
		up.sb = this.sb;
		up.params = this.params;
		
		up.sb.append(" set ");
		sb.append(column.sb).append(" = ").append(columnRef.sb);
		
		return up;
	}
	
	public UpdateSetSegment set(SqlColumn column, Object value){
		UpdateSetSegment up = new UpdateSetSegment();
		up.sb = this.sb;
		up.params = this.params;
		
		up.sb.append(" SET ");
		sb.append(column.sb).append(" = ?");
		
		up.params.add(value);
		return up;
	}
}
