package com.yoyuapp.sqlbuilder;


import org.junit.Test;


public class SQLTest {
	@Test
	public void testSQL(){
		QuerySql sql = SqlBuilder.select(SqlColumn.of("a").as("a_")).from(SqlTable.of("u_member"));
		
		System.out.println(sql);
	}
	
	@Test
	public void testCondition(){
		WhereSegment s = SqlColumn.of("a").gt(5).AND(SqlColumn.of("b").isNull().OR(SqlColumn.of("c").eq(5)));
		QuerySql sql = SqlBuilder
				.select(SqlFunc.count().as("dsaf"), SqlColumn.of("b").add(SqlColumn.of("h")).mul(6))
				.from(SqlTable.of("u_member"), SqlTable.of("b_event"))
				.where(s)
				.orderBy(SqlColumn.of("b").asc(), SqlColumn.of("a").desc())
				.groupBy(SqlColumn.of("d"));
		
		System.out.println(sql);
		
		for (Object o : sql.getParams()){
			System.out.println(o);
		}
	}
	
	@Test
	public void testUpdate(){
		UpdateSql sql = SqlBuilder
				.update(SqlTable.of("u_member"))
				.set(SqlColumn.of("nickname"), "wang")
				.set(SqlColumn.of("age"), 5)
				.where(
						SqlColumn.of("id").eq(335)
						.AND(
							SqlColumn.of("age").add(9).gt(20)
						)
						.AND(SqlColumn.of("createTime").lt(SqlFunc.now()))
				);
		System.out.println(sql);
		for (Object o : sql.getParams()){
			System.out.println(o);
		}
	}
}
