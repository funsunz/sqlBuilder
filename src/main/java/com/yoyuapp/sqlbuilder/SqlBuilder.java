package com.yoyuapp.sqlbuilder;




public class SqlBuilder{
	private SqlBuilder(){super();};

	
	public static SelectSegment select(SqlColumn... column){
		SelectSegment select = new SelectSegment();
		select.sb.append("SELECT ");
		
		if (column != null && column.length > 0){
			for (int i = 0; i<column.length; i++){
				select.sb.append(column[i].sb);

				String as = column[i].getAs();
				if (as != null && as.trim().length() > 0){
					select.sb.append(" AS '").append(column[i].getAs()).append("'");
				}
				if (i < column.length-1){
					select.sb.append(", ");
				}
				
				select.params.addAll(column[i].params);
			}
		}
		else {
			select.sb.append("*");
		}
		
		return select;
	}
	
	public static UpdateSegment update (SqlTable table){
		UpdateSegment update = new UpdateSegment();
		update.sb.append("UPDATE ").append(table.getName());
		return update;
	}
}
