-- set timezone to UTC vietnam
-- Đặt múi giờ thành Vietnam
SET TIME ZONE 'Asia/Ho_Chi_Minh';
SHOW TIMEZONE;


insert into roles(name)
values (N'ROLE_MANAGER'),
       (N'ROLE_CASHIER'),
       (N'ROLE_ORDER');

insert into users(fullname, username, password)
values (N'Admin', 'administrator', '$2a$10$tFOnpXe7xqUO1EhbbvzC9.cllX86l7UOHfuGYMAKcOsFsrnnhW40K');

insert into user_roles(user_id, role_id)
values (1, 1),
       (1, 2),
       (1, 3);

insert into areas(name)
values (N'A'),
       (N'B'),
       (N'C'),
       (N'D');

insert into rooms(name, active, area_id)
values (N'Bàn 1', true, 1),
       (N'Bàn 2', true, 1),
       (N'Bàn 3', true, 1),
       (N'Bàn 4', true, 1),
       (N'Bàn 5', true, 1),
       (N'Bàn 6', true, 2),
       (N'Bàn 7', true, 2),
       (N'Bàn 8', true, 2),
       (N'Bàn 9', true, 2),
       (N'Bàn 10', true, 2),
       (N'Bàn 11', true, 3),
       (N'Bàn 12', true, 3),
       (N'Bàn 13', true, 3),
       (N'Bàn 14', true, 3),
       (N'Bàn 15', true, 3),
       (N'Bàn 16', true, 4),
       (N'Bàn 17', true, 4),
       (N'Bàn 18', true, 4),
       (N'Bàn 19', true, 4),
       (N'Bàn 20', true, 4);

insert into categories(name)
values (N'Tiền giờ'),
       (N'Nước ngọt'),
       (N'Bia'),
       (N'Đồ ăn'),
       (N'Đồ uống'),
       (N'Mồi nhắm'),
       (N'Khác');

insert into products(active, deleted, hourly, name, price, type, unit, category_id)
values (true, false, true, N'Tiền giờ líp', 10000, 1, N'Giờ', 1),
       (true, false, true, N'Tiền giờ lỗ', 10000, 1, N'Giờ', 1),
       (true, false, false, N'Coca', 10000, 1, N'Lon', 2),
       (true, false, false, N'Pepsi', 10000, 1, N'Lon', 2),
       (true, false, false, N'7up', 10000, 1, N'Lon', 2),
       (true, false, false, N'Fanta', 10000, 1, N'Lon', 2),
       (true, false, false, N'Nước suối', 10000, 1, N'Chai', 5),
       (true, false, false, N'Nước khoáng', 10000, 1, N'Chai', 5),
       (true, false, false, N'Bia Tiger', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia Larue', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia Hà Nội', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia Sài Gòn', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia 333', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia Huda', 13000, 1, N'Lon', 3),
       (true, false, false, N'Bia Heineken', 13000, 1, N'Lon', 3),
       (true, false, false, N'Khoai tây chiên', 20000, 1, N'Dĩa', 4),
       (true, false, false, N'Trái cây', 25000, 1, N'Dĩa', 4),
       (true, false, false, N'Nem chả', 25000, 1, N'Dĩa', 4),
       (true, false, false, N'Đậu phộng', 10000, 1, N'Dĩa', 6),
       (true, false, false, N'Mực nướng', 10000, 1, N'Dĩa', 7);

