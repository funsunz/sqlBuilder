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
			        Member.ID, 
			        Member.NAME, 
			        SqlFunc.now().as("now"),
			        Member.AGE.add(5).div(2).as("half_of_age_increase_5"),
			        Member.AGE.div(Member.ID.mul(2)).as("age_double_id_rate")
			    )
			    .from(Member._TABLE)
			    .where(
			        Member.AGE.gt(20)
			        .AND(
			            Member.NAME.like("T%").OR(Member.NAME.like("J%"))
			        )
			    )
			    .orderBy(Member.NAME.desc());
		
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
