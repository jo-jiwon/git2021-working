
-- column Ÿ�� ����
alter table air_sigungu_hour 
alter column pm10value type int using (nullif(pm10value, '')::integer);

alter table air_sigungu_hour 
alter column pm25value type int using (nullif(pm25value, '')::integer);



select * from air_sigungu_hour ash;


-- ���� pm10 ���, �ּ�, �ִ밪
-- ex) ������ 30 10 50
--     �߶��� 25  5 45

-- group by: Ư�� �÷����� �������� �׷�����
select city_name from air_sigungu_hour
group by city_name;


create table air_sigungu_hour (
	data_time varchar(255) not null,
	sido_name varchar(20) collate "ko_KR.utf8" not null,
	city_name varchar(20) collate "ko_KR.utf8" not null,
	pm10value int4, 
	pm25value int4, 
	primary key (data_time, sido_name, city_name)
);

select * 
from air_sigungu_hour 
order by data_time desc, city_name asc
limit 25;


-- group by �̿��� �÷��� ���谡 ������ �÷��� ����
select city_name, 
	avg(pm10value)::int pm10_avg, 
	min(pm10value) pm10_min, 
	max(pm10value) pm10_max 
from air_sigungu_hour
group by city_name
order by 2 desc, city_name asc;


select city_name, 
	avg(pm10value)::int pm10_avg, 
	min(pm10value) pm10_min, 
	max(pm10value) pm10_max 
from air_sigungu_hour
where city_name = '������'
group by city_name
order by 2 desc, city_name asc;


-- �ڷγ�
select * from covid_sido_daily csd;

create table covid_sido_daily (
	std_day varchar(255) not null,
	gubun varchar(20) collate "ko_KR.utf8" not null,
	over_flow_cnt int4, 
	local_occ_cnt int4, 
	primary key (gubun, std_day)
);