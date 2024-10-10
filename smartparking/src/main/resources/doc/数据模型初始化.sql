CREATE DATABASE `parking`;

create table if not exists car_visitor
(
    id                      varchar(32)  not null comment 'ID'
    primary key,
    mainland_license_plates varchar(255) not null comment '内地车牌号码',
    other_license_plates    varchar(255) null comment '其他车牌号码，多个车牌号码英文逗号分隔',
    start_time              datetime     not null comment '免收费开始时间',
    end_time                datetime     not null comment '免收费结束时间',
    create_time             datetime     null comment '创建时间',
    update_time             datetime     null comment '修改时间'
    )
    comment '车辆管理-访客车登记管理表';

create table if not exists car_vpi_monthly_rent
(
    id                      varchar(32)  not null comment 'ID'
    primary key,
    mainland_license_plates varchar(255) not null comment '内地车牌号码',
    other_license_plates    varchar(255) null comment '其他车牌号码，多个车牌号码英文逗号分隔',
    create_time             datetime     null comment '登记时间',
    update_time             datetime     null comment '修改时间'
    )
    comment '车辆管理-vip月租车登记管理表';

create table if not exists merchant_reconciliation
(
    id                    varchar(32)  not null comment 'ID'
    primary key,
    user_id               varchar(255) null comment '用户ID',
    total_duration        varchar(255) null comment '总停车时长',
    total_amount          varchar(255) null comment '总计金额',
    total_discount_amount varchar(255) null comment '总计优惠金额',
    total_income_amount   varchar(255) null comment '总计收入金额',
    year_number           varchar(255) null comment '年份',
    month_number          varchar(255) null comment '月份',
    status                varchar(255) null comment '发放状态'
    )
    comment '商户-对账记录';

create table if not exists monthly_insurance_payment
(
    id                        varchar(32)   not null comment 'ID'
    primary key,
    mainland_license_plates   varchar(500)  not null comment '内地车牌号码',
    monthly_start_time        datetime      null comment '月保开始时间',
    monthly_end_time          datetime      null comment '月保结束时间',
    car_type_code             varchar(32)   null comment '车辆类型编码',
    car_type_name             varchar(32)   null comment '车辆类型名称',
    user_name                 varchar(500)  null comment '姓名',
    phone_number              varchar(13)   null comment '手机号码',
    card_id                   varchar(50)   null comment '身份证号码',
    parking_lot_code          varchar(13)   null comment '车位编号',
    monthly_free              varchar(35)   null comment '月保费用/月',
    payment_status            varchar(32)   null comment '缴费状态',
    payment_amount            varchar(32)   null comment '缴费金额',
    accumulate_payment_amount varchar(32)   null comment '累计缴费金额',
    long_term                 int default 0 not null comment '是否长期有效，0|否;1|是,默认否',
    create_time               datetime      not null comment '创建时间'
    )
    comment '月保-缴费记录';

create table if not exists order_paid_cat_outbound
(
    id                      varchar(32)   not null comment 'ID'
    primary key,
    user_id                 varchar(255)  not null comment '用户ID',
    mainland_license_plates varchar(255)  not null comment '车牌号/手机号码(无牌车)',
    start_time              datetime      not null comment '进场时间',
    end_time                datetime      not null comment '出场时间',
    total_duration          varchar(255)  not null comment '停车时长',
    play_time               datetime      not null comment '订单支付时间',
    order_number            varchar(255)  not null comment '订单编号',
    total_amount            varchar(255)  not null comment '总计金额',
    total_discount_amount   varchar(255)  not null comment '优惠金额',
    discount                varchar(255)  not null comment '优惠信息',
    total_income_amount     varchar(255)  not null comment '收入金额',
    type_code               varchar(255)  not null comment '类型编码',
    type_name               varchar(255)  not null comment '类型名称',
    play_status             varchar(32)   not null comment '支付状态',
    advance_payment         int default 0 not null comment '是否提前支付，0｜否；1｜是，默认为0',
    create_time             datetime      not null comment '订单创建时间',
    device_ip               varchar(35)   null comment '出口设备IP'
    )
    comment '订单-车辆出库已支付订单记录';

create table if not exists park_collect_coupons
(
    id                      varchar(32)   not null comment 'ID'
    primary key,
    user_id                 varchar(255)  not null comment '商户ID',
    mainland_license_plates varchar(255)  not null comment '车牌号/手机号码(无牌车)',
    start_time              datetime      null comment '进场时间',
    end_time                datetime      null comment '出场时间',
    total_duration          varchar(255)  null comment '停车时长',
    total_amount            varchar(255)  null comment '总计金额',
    total_discount_amount   varchar(255)  null comment '优惠金额',
    discount                varchar(255)  null comment '优惠信息',
    total_income_amount     varchar(255)  null comment '需付金额',
    receive_time            datetime      null comment '领劵时间',
    is_it_over              int           null comment '是否已出场,0|未出场；1|已出场',
    is_play                 int default 0 not null comment '订单是否已支付,0|未支付；1|已支付',
    play_time               datetime      null comment '订单支付成功时间'
    )
    comment '商户-停车领劵';

create table if not exists record_car_enter
(
    id                      varchar(32)          not null comment 'ID'
    primary key,
    mainland_license_plates varchar(255)         not null comment '内地车牌号码',
    phone_number            varchar(13)          null comment '手机号码',
    other_license_plates    varchar(255)         null comment '其他车牌号码，多个车牌号码英文逗号分隔',
    start_camera_device_ip  varchar(32)          not null comment '进库摄像头IP',
    start_time              datetime             not null comment '进库时间',
    device_group_id         int                  not null comment '设备组号编码，1｜主库；2｜东库；3｜西库',
    device_group_name       varchar(32)          not null comment '设备组号名称',
    car_group_id            int                  not null comment '车辆分组编码，1｜临停车；2｜VIP月租车；3｜普通月租车；4｜访客车；5|无牌车；6｜其他',
    car_group_name          varchar(32)          not null comment '车辆分组名称',
    is_toll                 tinyint(1) default 0 not null comment '此处是否开始计费，0｜不计费; 1｜开始计费;默认不计费',
    status                  tinyint(1) default 0 not null comment '订单是否结束，0｜未结束；1｜已结束,默认未结束'
    )
    comment '记录-车辆进场记录表';

create table if not exists records_car_outbound
(
    id                      varchar(32)          not null comment 'ID'
    primary key,
    mainland_license_plates varchar(255)         not null comment '内地车牌号码',
    phone_number            varchar(13)          null comment '手机号码',
    other_license_plates    varchar(255)         null comment '其他车牌号码，多个车牌号码英文逗号分隔',
    end_camera_device_ip    varchar(32)          not null comment '出库摄像头IP',
    start_time              datetime             not null comment '进场时间',
    end_time                datetime             not null comment '出库时间',
    device_group_id         int                  not null comment '设备组号编码，1｜主库；2｜东库；3｜西库',
    device_group_name       varchar(32)          not null comment '设备组号名称',
    car_group_id            int                  not null comment '车辆分组编码，1｜临停车；2｜VIP月租车；3｜普通月租车；4｜访客车；5|无牌车；6｜其他',
    car_group_name          varchar(32)          not null comment '车辆分组名称',
    parking_duration        varchar(100)         not null comment '停车时长',
    is_toll                 tinyint(1) default 0 not null comment '此处是否结算计费，0｜不结算; 1｜结算计费;默认不结算'
    )
    comment '记录-车辆出场记录表';

create table if not exists system_camera_device
(
    id               varchar(32)          not null comment 'ID'
    primary key,
    device_ip        varchar(255)         not null comment '设备IP地址',
    device_port      varchar(255)         not null comment '设备IP端口',
    device_user_name varchar(255)         null comment '设备用户名',
    device_password  varchar(255)         null comment '设备密码',
    device_name      varchar(255)         null comment '设备名称',
    device_location  varchar(255)         null comment '设备所在位置',
    device_role      int                  not null comment '设备作用，1｜进；0｜出',
    is_toll          tinyint(1) default 0 not null comment '是否在此处计费，1｜计费；0｜不计费',
    create_time      datetime             null comment '创建时间',
    update_time      datetime             null comment '修改时间',
    group_id         int                  null comment '组号，进出为一组，1｜主库；2｜东库；3｜西库',
    user_id          int                  null comment '设备登陆句柄'
    )
    comment '系统管理-摄像头设备管理表';

create table if not exists system_charge_rules
(
    id                                varchar(32)  not null comment 'ID'
    primary key,
    free_duration                     int          null comment '进场免费时长，单位：分钟',
    toll_standard                     varchar(255) null comment '临保收费标准，单位：元/小时',
    fee_cap                           varchar(32)  null comment '临保收费上限。单位：元/24小时',
    monthly_internal_car              varchar(255) null comment '月保收费标准-内部车辆，单位：元/月',
    monthly_enterprise_car            varchar(255) null comment '月保收费标准-所属企业公车，单位：元/月',
    monthly_external_car_machinery    varchar(255) null comment '月保收费标准-外部车辆(机械车位)，单位：元/月',
    monthly_internal_car_no_machinery varchar(255) null comment '月保收费标准-外部车辆(非机械车位)，单位：元/月'
    )
    comment '系统管理-收费规则表';

create table if not exists system_parking
(
    id                varchar(32) not null comment 'ID'
    primary key,
    parking_number    int         null comment '车位总数量',
    disposable_number int         null comment '可分配车位总数量'
    )
    comment '系统管理-车位数量表';

create table if not exists system_parking_detail
(
    id              varchar(32) not null comment 'ID'
    primary key,
    user_id         varchar(32) not null comment '用户ID',
    assigned_number int         not null comment '租赁车位数量',
    start_time      datetime    null comment '开始时间',
    end_time        datetime    null comment '结束时间'
    )
    comment '系统管理-车位数量明细表';

create table if not exists system_role
(
    id            varchar(32)                  not null comment 'ID'
    primary key,
    role_name     varchar(32)                  null comment '角色名称',
    is_deactivate tinyint(1) unsigned zerofill null comment '是否停用 1｜停用 0｜正常',
    role_sort     int                          not null comment '显示顺序, 数值越小，排序越靠前',
    permissions   varchar(32)                  not null comment '用户权限,1|增，2｜改，3｜删，4｜查;多个权限以逗号拼接'
    )
    comment '系统管理-角色表';

create table if not exists system_users
(
    id                        varchar(32)  not null comment 'ID',
    account                   varchar(32)  not null comment '登录账号',
    password                  varchar(32)  not null comment '密码',
    head_sculpture            varchar(500) null comment '头像',
    user_name                 varchar(255) null comment '用户名称',
    create_time               datetime     null comment '创建时间',
    free_time                 int          null comment '免费时长，单位:分钟',
    phone_number              varchar(32)  null comment '联系电话',
    front_business_license    varchar(500) null comment '营业执照正面',
    opposite_business_license varchar(500) null comment '营业执照反面',
    status                    tinyint      null comment '状态，1|正常;2|锁定;3|禁用',
    unit_address              varchar(500) null,
    primary key (id, account)
    )
    comment '系统管理-用户表';

create table if not exists system_user_role
(
    user_id     varchar(32) not null comment '用户ID',
    role_id     varchar(32) not null comment '角色ID',
    create_time datetime    null comment '创建时间',
    primary key (user_id, role_id),
    constraint roleid
    foreign key (role_id) references system_role (id),
    constraint userid
    foreign key (user_id) references system_users (id)
    )
    comment '系统管理-用户和角色关联表';

create table if not exists system_we_chat_jsapi_pay
(
    id               varchar(32)  not null comment 'ID'
        primary key,
    app_id           varchar(32)  not null comment '公众号appId',
    secret           varchar(55)  not null comment '公众号密钥',
    currency         varchar(12)  not null comment '币种：CNY',
    mac_id           varchar(32)  not null comment '商户号',
    private_key_path varchar(255) not null comment '商户私钥文件地址',
    api_v3_key       varchar(255) not null comment '商户APIv3密钥',
    mac_serial_no    varchar(255) not null comment '商户API证书序列号'
)
    comment '系统管理-微信JSAPI支付配置';

## 初始化管理员用户数据
insert into system_users (
   id,
   account,
   password,
   head_sculpture,
   user_name,
   create_time,
   free_time,
   phone_number,
   front_business_license,
   opposite_business_license,
   status,
   unit_address
) values (
   '1',
   'admin',
   '14e1b600b1fd579f47433b88e8d85291',
   null,
   '超级管理员',
   NOW(),
   15,
   null,
   null,
   null,
   1,
   null
);

## 初始化角色
INSERT INTO system_role (id, role_name, is_deactivate, role_sort, permissions)
VALUES
(1,'酒店用户',000,0,"1"),
(2,'商户用户',000,1,"1"),
(3,'管理员',000,2,"1,2,3,4"),
(4,'超级管理员',000,3,"1,2,3,4");

## 初始化用户角色关联
INSERT INTO system_user_role (user_id, role_id, create_time) VALUES
('1', '4', NOW());

## 数据库初始化完毕