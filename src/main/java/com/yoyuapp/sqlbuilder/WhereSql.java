package com.yoyuapp.sqlbuilder;



public class WhereSql extends QuerySql{
	public WhereSql orderBy(SqlColumnSort... column){
		if (column != null && column.length > 0) {
			this.sb.append(" ORDER BY ");
			for (int i = 0; i < column.length; i++) {
				sb.append(column[i].column);
				if (column[i].desc) {
					this.sb.append(" DESC");
				}
				else {
					this.sb.append(" ASC");
				}
				if (i < column.length - 1){
					this.sb.append(", ");
				}
			}
		}
		return this;
	}
	
	public GroupBySql groupBy(SqlColumn... columns){
		if (columns != null && columns.length > 0) {
			this.sb.append(" GROUP BY ");
			for (int i = 0; i < columns.length; i++) {
				sb.append(columns[i].sb);
				if (i < columns.length - 1){
					this.sb.append(", ");
				}
			}
		}
		
		GroupBySql g = new GroupBySql();
		g.sb = sb;
		g.setParams(getParams());
		return g;
	}
	
}
