#初始化创建数据表

#客户信息表user_info
create table if not exists user_info
(
    id        bigint primary key auto_increment not null, #客户id标识，主键，自增
    username  varchar(16) unique                not null, #登录名
    password  varchar(1024)                     not null, #登录密码
    real_name varchar(8)                        not null, #真实姓名
    sex       varchar(10)                       not null, #性别
    address   varchar(255)                      not null, #联系地址
    email     varchar(50)                       not null, #电子邮件
    reg_date  date                              not null, #注册时间
    status    int(4)                            not null  #客户状态
);

#管理员信息表 admin_info
create table if not exists admin_info
(
    id       bigint primary key auto_increment not null, #管理员id标识，主键，自增
    username varchar(16) unique                not null, #登录名
    password varchar(1024)                     not null  #登录密码
);

#商品类型表type
create table if not exists type
(
    id   int primary key auto_increment not null,#类型id标识，主键，自增
    name varchar(20) unique             not null #类型名称
);

#商品信息表product _info
create table if not exists product_info
(
    id     bigint primary key auto_increment not null, #商品id标识，主键，自增
    code   varchar(16)                       not null, #商品编号
    name   varchar(255)                      not null, #商品名称
    tid    int(4)                            not null, #商品类别id（外键）
    brand  varchar(20)                       not null, #品牌
    pic    varchar(255)                      not null, #商品图片
    num    int                               not null, #商品数量
    price  decimal(19, 4)                    not null, #商品价格
    intro  longtext                          not null, #商品描述
    status int(4)                            not null  #商品状态
);
alter table product_info
    add foreign key (tid) references type (id);

#订单信息表order_info
create table if not exists order_info
(
    id          bigint primary key auto_increment not null, #订单id标识，主键，自增
    uid         bigint                            not null, #客户id（外键）
    status      int(4)                            not null, #订单状态
    order_time  datetime                          not null, #订单下单时间
    order_price decimal(19, 4)                    not null  #订单金额
);
alter table order_info
    add foreign key (uid) references user_info (id);

#订单明细表order_detail
create table if not exists order_detail
(
    id  bigint primary key auto_increment not null, #订单明细id，主键，自增
    oid bigint                            not null, #订单id（外键）
    pid bigint                            not null, #商品id（外键）
    num int                               not null  #购买数量
);
alter table order_detail
    add foreign key (oid) references order_info (id),
    add foreign key (pid) references product_info (id);

#系统权限表authorities
create table if not exists authorities
(
    id         int primary key auto_increment not null, #系统功能（权限） id，主键，自增
    role       varchar(20) unique             not null, #功能菜单（权限）名称
    parent_id  int                            not null, #父结点id
    url        varchar(50)                    not null, #功能页面
    is_leaf    bit(1)                         not null, #是否叶子结点
    node_order int                            not null  #结点顺序
);

#权限表powers
create table if not exists admin_authority
(
    admin_id     bigint not null, #管理员 id（外键）
    authority_id int    not null  #系统功能 id（外键）
);
alter table admin_authority
    add foreign key (admin_id) references admin_info (id),
    add foreign key (authority_id) references authorities (id);


