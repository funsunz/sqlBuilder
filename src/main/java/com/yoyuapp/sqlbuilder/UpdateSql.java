package com.yoyuapp.sqlbuilder;

import java.util.List;


public class UpdateSql extends SqlSegment{
	public List<Object> getParams(){
		return this.params;
	}
}
