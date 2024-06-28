-- insert into role(id, name) values(1, 'admin');
-- insert into role(id, name) values(2, 'limited');
-- insert into role(id, name) values(3, 'full');
-- insert into role(id, name) values(4, 'blocked');

insert into user_account(id, firstname, lastname, username, email, password, age) values(111, 'Admin', 'Admin', 'admin', 'admin@admin.pl', '$2a$12$9SLQwaTspeT62QFfXgNC/.VXI6fritMzcSh.JnVyXBDJbP.ESHuxm', 32);

insert into user_accounts_roles(user_account_id, role_id) values(111, 1);
