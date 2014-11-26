# sqlBuilder

a friendly, simple and safe SQL builder written in Java, with no 3rd party dependencies

## simple example


```sql
SELECT name FROM member WHERE id = 20
```


in sqlBuilder:

```java
QuerySql sql = SqlBuilder
		.select(SqlColumn.of("name"))
		.from(SqlTable.of("member"))
		.where(
			SqlColumn.of("id").eq(20)
		);
		
System.out.println(sql.toString());
```

output:

```sql
	SELECT name FROM member WHERE id = ?
```

you can get the params List using `sql.getParams()`



## best practices

add some codes in your ORM objects like follows:

```java
@Entity
@Table(name="member")
public class Member {
	private int id;
	private String name;
	private String age;
	private Calendar registerTime;
	
	//... gets and sets here...
	
	
	/***** code follows could be generated by some code Generator *****/
	
	//table
	public static SqlTable _TABLE = SqlTable.of("member");
	
	//each column
	public static SqlColumn _ALL 			= SqlColumn.of(_TABLE, "*");
	public static SqlColumn ID 				= SqlColumn.of(_TABLE, "id");
	public static SqlColumn NAME 			= SqlColumn.of(_TABLE, "name");
	public static SqlColumn AGE 			= SqlColumn.of(_TABLE, "age");
	public static SqlColumn REGISTER_TIME 	= SqlColumn.of(_TABLE, "registerTime");
}
```

```java
@Entity
@Table(name="order")
public class Order {
	private int id;
	private String name;
	private int memberId;
	private BigDecimal amount;
	private Calendar createTime;
	
	//... gets and sets here...
	
	
	/***** code follows could be generated by some code generator *****/
	
	//table
	public static SqlTable _TABLE = SqlTable.of("order");
	
	//each column
	public static SqlColumn _ALL 			= SqlColumn.of(_TABLE, "*");
	public static SqlColumn ID 				= SqlColumn.of(_TABLE, "id");
	public static SqlColumn MEMBER_ID		= SqlColumn.of(_TABLE, "memberId");
	public static SqlColumn NAME 			= SqlColumn.of(_TABLE, "name");
	public static SqlColumn AMOUNT 			= SqlColumn.of(_TABLE, "amount");
	public static SqlColumn CREATE_TIME 	= SqlColumn.of(_TABLE, "createTime");
}

```

then:

### select all

```sql
SELECT
	*
FROM
	member
WHERE
	member.age > 20
	AND member.name LIKE 'T%'
ORDER BY
	member.name DESC
```

in sqlBuilder:

```java
QuerySql sql = SqlBuilder
	.select()								// SELECT
	.from(Member._TABLE)					// FROM
	.where(									// WHERE
		Member.AGE.gt(20)					// 	-CONDITION 1
		.AND(Member.NAME.like("T%"))		// 	-CONDITION 2
	)										//
	.orderBy(Member.NAME.desc());			// ORDERY BY
```

`gt(...)` means `>`, "greater than..." <br>
`lt(...)` means `<`, "less than..." <br>
`gt_eq(...)` means `>=`, "greater than or equals..." <br>
`lt_eq(...)` means `<=`, "less than or equals..." 

building SQL in this way is safe and simple because Java is a strongly typed language, which allows for extensive compile-time checking for potential type-mismatch problems. so that you can avoid making time-consuming corrections or maintaining complex SQL configuration files.

### select specified columns :

```sql
SELECT
	member.id,
	member.name,
	NOW() AS 'now',
	(member.age + 5) / 2 AS 'half_of_age_increased_5',
	member.age / (member.id * 2) AS 'age_double_id_rate'
FROM
	member
WHERE
	member.age > 20
	AND (
		member.name LIKE 'T%'
		OR member.name LIKE 'J%'
	)
ORDER BY
	member.name DESC
```

in sqlBuilder : 
```java
QuerySql sql = SqlBuilder
	.select(
		Member.ID, 
		Member.NAME, 
		SqlFunc.now().as("now"),
		Member.AGE.add(5).div(2).as("half_of_age_increased_5"),
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
```


### group by

```sql
SELECT
	member.age,
	COUNT(member.id) AS 'count'
FROM
	member
WHERE
	member.age > 20
	AND (
		member.name LIKE 'T%'
		OR member.name LIKE 'J%'
	)
GROUP BY
	member.age
HAVING
	count > 2
```

in sqlBuilder : 
```java
QuerySql sql = SqlBuilder
	.select(
		Member.AGE, 
		SqlFunc.count(Member.ID).as("count")
	)
	.from(Member._TABLE)
	.where(
		Member.AGE.gt(20)
		.AND(
			Member.NAME.like("T%").OR(Member.NAME.like("J%"))
		)
	)
	.groupBy(Member.AGE)
	.having(
		SqlColumn.of("count").gt(2)
	);
```

### join table
```sql
SELECT
	order.*,
	member.name AS 'member_name'
FROM
	order
LEFT JOIN 
	member ON order.memberId = member.id
WHERE
	(order.id * 2) <= member.id
```

in sqlBuilder : 
```java
QuerySql sql = SqlBuilder
	.select(
		Order._ALL,
		Member.NAME.as("member_name")
	)
	.from(Order._TABLE)
	.leftJoin(Member._TABLE, Order.MEMBER_ID, Member.ID)
	.where(
		Order.ID.mul(2).lt_eq(Member.ID)
	);
```

**sqlBuilder is not thread-safe.**