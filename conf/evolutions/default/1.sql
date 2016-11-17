# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table diary (
  id                            bigint not null,
  title                         varchar(255),
  description                   varchar(255),
  date_diary                    varchar(255),
  user_id                       bigint,
  constraint pk_diary primary key (id)
);
create sequence diary_seq;

create table person (
  id                            bigint not null,
  name                          varchar(255),
  email                         varchar(255),
  pseudo                        varchar(255),
  siret                         varchar(255),
  password                      varchar(255),
  role                          integer,
  number_address                varchar(255),
  street_address                varchar(255),
  city_address                  varchar(255),
  post_code_address             varchar(255),
  token                         varchar(255),
  constraint uq_person_email unique (email),
  constraint pk_person primary key (id)
);
create sequence person_seq;

create table product (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  price                         double,
  quantity                      integer,
  seller_id                     bigint,
  constraint pk_product primary key (id)
);
create sequence product_seq;

create table task (
  id                            bigint not null,
  name                          varchar(255),
  creator_id                    bigint,
  constraint pk_task primary key (id)
);
create sequence task_seq;

alter table diary add constraint fk_diary_user_id foreign key (user_id) references person (id) on delete restrict on update restrict;
create index ix_diary_user_id on diary (user_id);

alter table product add constraint fk_product_seller_id foreign key (seller_id) references person (id) on delete restrict on update restrict;
create index ix_product_seller_id on product (seller_id);

alter table task add constraint fk_task_creator_id foreign key (creator_id) references person (id) on delete restrict on update restrict;
create index ix_task_creator_id on task (creator_id);


# --- !Downs

alter table diary drop constraint if exists fk_diary_user_id;
drop index if exists ix_diary_user_id;

alter table product drop constraint if exists fk_product_seller_id;
drop index if exists ix_product_seller_id;

alter table task drop constraint if exists fk_task_creator_id;
drop index if exists ix_task_creator_id;

drop table if exists diary;
drop sequence if exists diary_seq;

drop table if exists person;
drop sequence if exists person_seq;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists task;
drop sequence if exists task_seq;

