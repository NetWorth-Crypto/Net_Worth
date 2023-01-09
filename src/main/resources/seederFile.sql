USE net_worth;



insert into user(email,user_name, first_name, last_name, password,  profile_picture, user_title)
values ('aastevens1126@gmail.com', 'astevens09','Anthony','Stevens','$2a$10$GsOi9SscCwtgCSgf0D1AVeIlEORlgr8AEsUHoeFLkvv2qg849ZdIy','https://cdn.filestackcontent.com/2HFPxlUsQxu6uhgAoVNp','web-dev'),
       ('casanovageary@gmail.com', 'dogeking','Cas','Geary','$2a$10$GsOi9SscCwtgCSgf0D1AVeIlEORlgr8AEsUHoeFLkvv2qg849ZdIy','https://ca.slack-edge.com/T029BRGN0-U03QXQ9L54L-bf7b21a56847-512', 'gamestop'),
       ('amida@gmail.com', 'midostar','Amida','Fombutu','$2a$10$GsOi9SscCwtgCSgf0D1AVeIlEORlgr8AEsUHoeFLkvv2qg849ZdIy','https://ca.slack-edge.com/T029BRGN0-U03QNP4B8RL-6f9c5581b866-512','web-dev'),
       ('yogesh@gmail.com', 'yogesha','Yogesh','Adhikari','$2a$10$GsOi9SscCwtgCSgf0D1AVeIlEORlgr8AEsUHoeFLkvv2qg849ZdIy','https://ca.slack-edge.com/T029BRGN0-U03QV9Q0ZM1-8feba9612cf0-512','web-dev');



insert into user(id,email,user_name, first_name, last_name, password)
values (124,'mido@codeup','mido','mido','mido','$2a$10$TnLa1qY/rIQL/P5ur9ZA/uXDZWuisNJcV9oFX.oIn/NheyBNImvHe');


insert into follower(user_id,follower_user_id)
values (1,2),(2,1);

insert into portfolio(name,dollar_limit,is_default,is_private,user_id,available_balance)
values ('portfolio1',1000, true, false, 1,30000),
       ('portfolio2',1000,true, false,2,50000);

insert into asset(name,ticker,current_price)
values ('bitcoin','BTC',19000),
       ('ethereum','ETH',1200);

insert into portfolio_asset(portfolio_id, asset_id, quantity,purchase_price,purchase_date)
values (1,1,1.2,23000,CURRENT_DATE),(1,2,2.2,1200,CURRENT_DATE),
       (2,1,.8,17000,CURRENT_DATE),(2,2,4,1600,CURRENT_DATE);

insert into post(description,user_id,img_url,video_url)
values ('Privacy ledge arrives at Cardano', 1,'https://u.today/sites/default/files/styles/736x/public/2022-11/26126.jpg','null'),
       ('Jp makes the world go around',2, 'https://static.news.bitcoin.com/wp-content/uploads/2022/05/jpmorgan-bitcoin1.jpg','null'),
       ('Africa will lead the defi revolution!',3, 'https://adapulse.io/wp-content/uploads/2021/04/Screen-Shot-2021-04-30-at-8.00.21-AM-800x500.png','null'),
       ('Eth is King!!!',4, 'https://c8.alamy.com/comp/2FBK2EF/ethereum-with-golden-crown-3d-rendering-isolated-on-white-background-2FBK2EF.jpg','null');

insert into comment(message,user_id, post_id)
values ('I 100% agree!',1,2),
       ('Good luck!',2,1);

insert into post_like(post_id, user_id)
values (1,1),(1,2);

insert into post_dislike(post_id, user_id)
values (2,1), (2,2);

insert into role(id,type,details)
values  (1,'admin','edit user\'s info or delete other users'),
        (2,'super-admin','assign and revoke roles, edit user\'s info or delete other users');

insert into user_role(user_id,role_id)
values (124,2),(124,1)