-- persistenceLogin  테이블 생성
CREATE TABLE IF NOT EXISTS persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);
