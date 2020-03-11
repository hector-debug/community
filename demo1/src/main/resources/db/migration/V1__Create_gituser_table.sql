create table table_name
(
	id VARCHAR(100) auto_increment,
	account_id VARCHAR(100),
	name VARCHAR(50),
	token char(36),
	gmt_create BIGINT,
	gmt_modified BIGINT,
	constraint table_name_pk
		primary key (id)
);

