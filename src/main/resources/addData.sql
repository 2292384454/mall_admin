#username:admin
#password:admin123456
insert into admin_info (username, password)
values ('admin', '$2a$10$fIHNMqPI1J9TjA1AZT.49OLj9Rf7Wpj0YZ8xBEoLu67bici0O12X.');
insert into admin_info (username, password)
values ('productAdmin', '$2a$10$fIHNMqPI1J9TjA1AZT.49OLj9Rf7Wpj0YZ8xBEoLu67bici0O12X.');
insert into admin_info (username, password)
values ('orderAdmin', '$2a$10$fIHNMqPI1J9TjA1AZT.49OLj9Rf7Wpj0YZ8xBEoLu67bici0O12X.');
insert into admin_info (username, password)
values ('userAdmin', '$2a$10$fIHNMqPI1J9TjA1AZT.49OLj9Rf7Wpj0YZ8xBEoLu67bici0O12X.');


insert into user_info (username, password, real_name, sex, address, email, reg_date, status)
values ('user0', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user1', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user2', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user3', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user4', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user5', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user6', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user7', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user8', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user9', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user10', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user11', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user12', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user13', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user14', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user15', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user16', '123456', 'Jack', 'male', 'No. 1037, Luoyu Road, Hongshan District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1),
       ('user17', '654321', 'Rose', 'female', 'No. 299, Bayi Road, Wuchang District, Wuhan City, Hubei Province',
        'jack@hust.edu.cn', '2021-05-28',
        1);

insert into authorities(role, parent_id, url, is_leaf, node_order)
values ('ROLE_ADMIN', 0, '/admin', false, 1),
       ('ROLE_PRODUCT_ADMIN', 1, '/admin/product_admin', true, 2),
       ('ROLE_ORDER_ADMIN', 1, '/admin/order_admin', true, 3),
       ('ROLE_USER_ADMIN', 1, '/admin/user_admin', true, 4);

insert into admin_authority (admin_id, authority_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

insert into type(name)
values ('手机'),
       ('电脑'),
       ('扫地机器人');

insert into product_info (code, name, tid, brand, pic, num, price, intro, status)
values ('mobilephone00001', 'XIAOMI MIX FOLD', 1, '小米', 'product/img/mobilephone00001.jpg', 1000, 9999.99,
        '这是你的第一台小米折叠屏手机,是首个小米智能工厂独立研发制造的手机,是首个使用澎湃C1专业影像芯片的手机,这就是 MIX FOLD', 1),
       ('mobilephone00002', 'XIAOMI 11 PRO', 1, '小米', 'product/img/mobilephone00002.jpg', 10000, 4799.00,
        '前所未有的暗光拍摄,夜景惊艳显现,无线充电一骑绝尘,电池揭晓未来方向,音画双绝的视听,获得顶尖机构的认可', 1),
       ('mobilephone00003', 'HUAWEI Mate 40 Pro', 1, '华为', 'product/img/mobilephone00003.jpg', 10000, 6299.00,
        '5nm麒麟9000旗舰芯片,超感知徕卡电影影像,有线无线双超级快充1,EMUI11创新智慧体验,全新HUAWEI Mate 40 Pro,跃见非凡。', 1);