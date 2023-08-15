
create table if not exists accounts
(
    accountid int primary key auto_increment,
    balance numeric(10,2) check(balance>0)

)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET COMMENT='账户表';


create table if not exists oprecord
(
    id int primary key auto_increment,
    accountid int references accounts(accountid),
    opmoney numeric (10,2),
    optime datetime,
    optype enum('deposite','withdraw','transfer') not null,
    transferid varchar (50)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='操作记录表'

commit;