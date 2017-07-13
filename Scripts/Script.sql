create table tbl_board (
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key (bno));
	
select * from tbl_board;

insert into tbl_board(title, content, writer) (select title, content, writer from tbl_board);

select count(*) from tbl_board;

select bno, title, writer, regdate, viewcnt from tbl_board where bno > 0 order by bno desc, regdate desc limit 0, 10;

-- 업로드 경로 저장하는 곳 따로 저장
create table tbl_attach( 
	fullname varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key(fullname)
);

alter table tbl_attach add constraint fk_board_attach foreign key(bno) references tbl_board(bno);