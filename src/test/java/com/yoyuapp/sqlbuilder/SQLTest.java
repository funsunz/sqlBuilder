package com.yoyuapp.sqlbuilder;


import org.junit.Test;


public class SQLTest {
	@Test
	public void testSQL(){
		QuerySql sql = SqlBuilder.
				select(SqlColumn.of("name")).
				from(SqlTable.of("member")).
				where(
					SqlColumn.of("id").eq(20)
				);
		
		System.out.println(sql.toString());
	}
	
	@Test
	public void testCondition(){
		QuerySql sql = SqlBuilder
			    .select(
			        Order.ID,
			        Order.NAME,
			        Order.CREATE_TIME,
			        Member.NAME.as("member_name")
			    )
			    .from(Member._TABLE)
			    .leftJoin(Order._TABLE, Order.MEMBER_ID, Member.ID)
			    .where(
			        Order.ID.mul(2).lt_eq(Member.ID)
			    )
			    .orderBy(Order.CREATE_TIME.desc());
		
		System.out.println(sql);
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
