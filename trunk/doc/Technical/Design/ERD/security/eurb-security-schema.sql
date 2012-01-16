create table users(
  username varchar(50) not null primary key,
  password varchar(50) not null,
  enabled tinyint(1) not null) engine = InnoDb;

create table authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  constraint fk_authorities_users foreign key(username) references users(username)) engine = InnoDb;
  create unique index ix_auth_username on authorities (username,authority);

create table groups (
  id bigint unsigned not null auto_increment primary key,
  group_name varchar(50) not null);

create table group_authorities (
  group_id bigint unsigned not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id)) engine = InnoDb;

create table group_members (
  id bigint unsigned not null auto_increment primary key,
  username varchar(50) not null,
  group_id bigint unsigned not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id)) engine = InnoDb;

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null) engine = InnoDb;

create table acl_sid (
  id bigint unsigned not null auto_increment primary key,
  principal tinyint(1) not null,
  sid varchar(100) not null,
  constraint unique_uk_1 unique(sid,principal) ) engine = InnoDb;

create table acl_class (
  id bigint unsigned not null auto_increment primary key,
  class varchar(100) not null,
  constraint unique_uk_2 unique(class) ) engine = InnoDb;

create table acl_object_identity (
  id bigint unsigned not null auto_increment primary key,
  object_id_class bigint unsigned not null,
  object_id_identity bigint unsigned not null,
  parent_object bigint unsigned,
  owner_sid bigint unsigned not null,
  entries_inheriting tinyint(1) not null,
  constraint unique_uk_3 unique(object_id_class,object_id_identity),
  constraint foreign_fk_1 foreign key(parent_object)references acl_object_identity(id),
  constraint foreign_fk_2 foreign key(object_id_class)references acl_class(id),
  constraint foreign_fk_3 foreign key(owner_sid)references acl_sid(id) ) engine = InnoDb;

create table acl_entry (
  id bigint unsigned not null auto_increment primary key,
  acl_object_identity bigint unsigned not null,
  ace_order int unsigned not null,
  sid bigint unsigned not null,
  mask int not null,
  granting tinyint(1) not null,
  audit_success tinyint(1) not null,
  audit_failure tinyint(1) not null,
  constraint unique_uk_4 unique(acl_object_identity,ace_order),
  constraint foreign_fk_4 foreign key(acl_object_identity)
      references acl_object_identity(id),
  constraint foreign_fk_5 foreign key(sid) references acl_sid(id) ) engine = InnoDb;
	