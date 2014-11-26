package com.yoyuapp.sqlbuilder;

import java.util.List;



/**
 * @author zfc
 *
 */
public class SqlColumn extends SqlSegment{
	
	private String as;
	protected boolean isCombContition;

	public static SqlColumn of(String column){
		SqlColumn con = new SqlColumn(column);
		return con;
	}
	
	public static SqlColumn of(SqlTable table, String column){
		SqlColumn con = new SqlColumn(table.getName()+"."+column);
		return con;
	}
	
	
	protected SqlColumn(StringBuilder column, String as, List<Object> params) {
		super();
		this.sb = column;
		this.as = as;
		this.params = params;
	}


	protected SqlColumn(String column) {
		super();
		this.sb = new StringBuilder(column);
	}
	
	public SqlColumn as(String name){
		SqlColumn c = clone(this);
		c.as = name;
		return c;
	}
	
	public SqlColumn cal(Object value, String symbol){
		SqlColumn c = clone(this);
		if (c.isCombContition){
			c.sb.insert(0, "(").append(")").append(symbol);
		}
		else {
			c.sb.append(symbol);
		}
		
		c.sb.append(" ").append("?");
		c.isCombContition = true;
		c.params.add(value);
		
		return c;
	}
	
	/** +
	 * @param value
	 * @return
	 */
	public SqlColumn add(Object value){
		return cal(value, " +");
	}
	
	/** -
	 * @param value
	 * @return
	 */
	public SqlColumn sub(Object value){
		return cal(value, " -");
	}
	
	/** *
	 * @param value
	 * @return
	 */
	public SqlColumn mul(Object value){
		return cal(value, " *");
	}
	
	/** \/
	 * @param value
	 * @return
	 */
	public SqlColumn div(Object value){
		return cal(value, " /");
	}
	
	
	
	public SqlColumn add(SqlColumn column){
		return cal(column, " +");
	}
	
	public SqlColumn sub(SqlColumn column){
		return cal(column, " -");
	}
	
	public SqlColumn mul(SqlColumn column){
		return cal(column, " *");
	}
	
	public SqlColumn div(SqlColumn column){
		return cal(column, " /");
	}
	
	private SqlColumn cal(SqlColumn column, String symbol){
		SqlColumn c = clone(this);
		if (c.isCombContition){
			c.sb.insert(0, "(").append(")").append(symbol);
		}
		else {
			c.sb.append(symbol);
		}
		
		if (column.isCombContition){
			c.sb.append(" (").append(column.sb).append(")");
		}
		else {
			c.sb.append(" ").append(column.sb);
		}
		c.isCombContition = true;
		
		return c;
	}
	
	
	private SqlColumn clone(SqlColumn sqlColumn){
		SqlColumn c = new SqlColumn(sqlColumn.sb.toString());
		c.as = sqlColumn.as;
		c.params.addAll(sqlColumn.params);
		c.isCombContition = sqlColumn.isCombContition;
		return c;
	}
	
	/** > greater than
	 * @param column
	 * @return
	 */
	public WhereSegment gt(SqlColumn column){
		return addCondition(column, ">");
	}
	
	/** < less than
	 * @param column
	 * @return
	 */
	public WhereSegment lt(SqlColumn column){
		return addCondition(column, "<");
	}
	
	/** = equals
	 * @param column
	 * @return
	 */
	public WhereSegment eq(SqlColumn column){
		return addCondition(column, "=");
	}
	
	/** >= greater than or equals
	 * @param column
	 * @return
	 */
	public WhereSegment gt_eq(SqlColumn column){
		return addCondition(column, ">=");
	}
	
	/** <= less than or equals
	 * @param column
	 * @return
	 */
	public WhereSegment lt_eq(SqlColumn column){
		return addCondition(column, "<=");
	}
	
	public WhereSegment like(SqlColumn column){
		return addCondition(column, "LIKE");
	}
	
	public WhereSegment notLike(SqlColumn column){
		return addCondition(column, "NOT LIKE");
	}
	
	
	
	/**  > greater than
	 * @param param
	 * @return
	 */
	public WhereSegment gt(Object param){
		return addCondition(param, ">");
	}
	
	/** < less than
	 * @param param
	 * @return
	 */
	public WhereSegment lt(Object param){
		return addCondition(param, "<");
	}
	
	public WhereSegment eq(Object param){
		return addCondition(param, "=");
	}
	
	public WhereSegment gt_eq(Object param){
		return addCondition(param, ">=");
	}
	
	public WhereSegment lt_eq(Object param){
		return addCondition(param, "<=");
	}
	
	public WhereSegment like(Object param){
		return addCondition(param, "LIKE");
	}
	
	public WhereSegment notLike(Object param){
		return addCondition(param, "NOT LIKE");
	}
	
	public WhereSegment notNull(){
		return addCondition("IS NOT NULL");
	}
	
	public WhereSegment isNull(){
		return addCondition("IS NULL");
	}
	
	private WhereSegment addCondition(String symbol){
		WhereSegment condition = new WhereSegment();
		if (isCombContition) {
			condition.sb.append("(").append(sb).append(") ").append(symbol);
		} else {
			condition.sb.append(sb).append(" ").append(symbol);
		}
		return condition;
	}
	
	private WhereSegment addCondition(SqlColumn column, String symbol){
		if (column != null){
			WhereSegment condition = new WhereSegment();
			if (this.isCombContition){
				condition.sb.append("(").append(sb).append(") ").append(symbol).append(" ").append(column.sb);
			}
			else {
				condition.sb.append(sb).append(" ").append(symbol).append(" ").append(column.sb);
			}
			
			condition.getParams().addAll(column.params);
			return condition;
		}
		
		return null;
	}
	
	private WhereSegment addCondition(Object param, String symbol){
		if (param != null){
			WhereSegment condition = new WhereSegment();
			if (this.isCombContition){
				condition.sb.append("(").append(sb).append(") ").append(symbol).append(" ?");
			}
			else {
				condition.sb.append(sb).append(" ").append(symbol).append(" ?");
			}
			
			condition.getParams().add(param);
			return condition;
		}
		
		return null;
	}

	public String getAs() {
		return as;
	}
	
	public SqlColumnSort desc(){
		SqlColumnSort c = new SqlColumnSort();
		c.column = sb.toString();
		c.desc = true;
		return c;
	}
	
	public SqlColumnSort asc(){
		SqlColumnSort c = new SqlColumnSort();
		c.column = sb.toString();
		c.desc = false;
		return c;
	}

}
