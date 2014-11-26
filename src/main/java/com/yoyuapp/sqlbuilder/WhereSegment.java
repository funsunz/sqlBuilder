package com.yoyuapp.sqlbuilder;


import java.util.List;


public class WhereSegment extends SqlSegment{
	private boolean isCombContition;
	
	
	
	protected WhereSegment() {
		super();
	}


	public WhereSegment AND(WhereSegment and){
		return concat(and, "AND");
	}
	
	
	public WhereSegment OR(WhereSegment or){
		return concat(or, "OR");
	}
	
	
	private WhereSegment concat(WhereSegment seg, String conj){
		if (seg != null) {
			this.sb.append(" ").append(conj);
			if (seg.isCombContition) {
				this.sb.append(" (").append(seg.sb).append(") ");
			} else {
				this.sb.append(" ").append(seg.sb);
			}
			this.isCombContition = true;
			this.params.addAll(seg.params);
		}
		return this;
	}


	public List<Object> getParams() {
		return params;
	}
	
}
