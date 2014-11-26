package com.yoyuapp.sqlbuilder;



public class SelectSegment extends SqlSegment{
	
	protected SelectSegment() {
		super();
	}

	public SelectTableSegment from(SqlTable... tables){
		if (tables != null && tables.length > 0) {
			sb.append(" FROM ");
			for (int i = 0; i < tables.length; i++) {
				sb.append(tables[i].getName());
				if (i < tables.length - 1){
					sb.append(", ");
				}
			}
		}
		SelectTableSegment s = new SelectTableSegment();
		s.sb = sb;
		s.params = this.params;
		return s;
	}
}
