use sqldb;
select * 
from buytbl
where price >= 300;

 use sqldb;
 select distinct(userid)     -- 중복없이 출력
 from buytbl
 where amount>=3;
 
 use sqldb;
 select userid,num,amount   
 from buytbl
 where amount<=3 and amount >=1;
 
 select userid, name,birthYear,height
 from usertbl
 where birthYear >=1970 and height >=182;
 
 select name, height
 from usertbl
 where height between 180 and 183; 
 
 select name,height
 from usertbl
 where addr in("경남","전남","경북");
 
select a.name,a.height,a.addr
 from usertbl as a
 where addr like("_남%");
 
 select *
 from (
 select b.name,b.height,b.addr  -- 앨리야스(as)
 from usertbl as b
 where addr like("_남%")
 ) as b 
 where height <=175;
 
 select height
 from usertbl
 where name = "김경호";    -- 177이라는 단 하나의 값 
 
select name, height 
from usertbl 
where height > (
 select height
 from usertbl
 where name = "김경호"
);
 
 select * from usertbl;
 
 select * from buytbl;
 
 select userid
 from usertbl
 where birthYear > 1970;
 
select *
from buytbl 
where userid = any(select userid
 from usertbl
 where birthYear > 1970);  -- 1970년 이후에 태어난 사람들의 구매이력 
 
 select height 
 from usertbl
 where birthYear <1975;
 
 select height as '가장 큰 키'
 from usertbl
 where height > all(select height 
 from usertbl
 where birthYear <1975);  -- 1975년 이전 출생자 전부보다 키가 큰사람 
 
 
 select userid,prodname,amount
 from buytbl
 order by amount;
 
select *
 from usertbl
 where birthYear >= 1970  -- Date(1907-1-1)
 order by height desc
 limit 3;
 
 select distinct(userid)
 from buytbl;
 
 select *
 from usertbl
  limit 5;
  
  
 create table buytbl_backup
 select * from buytbl;
 
 select *from buytbl_backup;
 
 
-- select
-- from
-- where
-- group by  순서(group by 와 having은 세트)
-- having
-- order by

 select userid, sum(price*amount) as total
 from buytbl
 group by userid 
 having total>100
 order by total desc;
 
 select userid,avg(price*amount)
 from buytbl 
 group by userid;
 
 select prodname,count(prodName)
 from buytbl
 group by prodName;
 
 select * from usertbl;
 
 update usertbl 
 set mobile2 = '12341234'
 where userId = "BBK";
 
 
delete from usertbl 
where userId = "YJS"; 

-- drop table buytbl..

 select userid, sum(price*amount)
 from usertbl as u
 join buytbl as b
 on u.userid = b.userid
 where u.height <175
 group by userid;
 
 select userid
 from usertbl
 where height <175;
 
 select userid,sum(price*amount) as "총 구매액"
 from buytbl
 where userid = any(    
 select userid
 from usertbl
 where height <175)
 group by userid;  --  '=any' = in -- * 다나오게 하려면 하나하나 group by 해줘야됨
 
 select * from buytbl;
 
 select * from usertbl
  join buytbl
  using(userid);
 
 select * 
 from buytbl;
 
 select * 
 from usertbl;
 
 
  
 