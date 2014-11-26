package com.yoyuapp.sqlbuilder;



public class SelectTableSegment extends QuerySql{
	private boolean joinedTable;
	
	public WhereSql where (WhereSegment conj){
		WhereSql orderBy = new WhereSql();
		orderBy.sb = this.sb;
		
		orderBy.sb.append(" WHERE ").append(conj.sb);
		orderBy.params = this.params;
		orderBy.params.addAll(conj.getParams());
		return orderBy;
	}
	
	public SelectTableSegment leftJoin(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" LEFT JOIN ", table, onColumn1, onColumn2);
	}
	
	public SelectTableSegment rightJoin(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" RIGHT JOIN ", table, onColumn1, onColumn2);
	}
	
	public SelectTableSegment join(SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		return toJoin(" JOIN ", table, onColumn1, onColumn2);
	}
	
	private SelectTableSegment toJoin(String joinSymbol, SqlTable table, SqlColumn onColumn1, SqlColumn onColumn2){
		if (joinedTable){
			sb.append(",");
		}
		sb.append(joinSymbol).append(table.getName()).append(" ON ").append(onColumn1.sb).append(" = ").append(onColumn2.sb);
		joinedTable = true;
		return this;
	}
}
