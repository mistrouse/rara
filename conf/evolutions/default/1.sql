# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table diary (
  id                            bigint not null,
  title                         varchar(255),
  description                   varchar(255),
  date_diary                    varchar(255),
  user_id                       bigint,
  objective_id                  bigint,
  constraint pk_diary primary key (id)
);
create sequence diary_seq;

create table objective (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  simple_user_id                bigint,
  constraint pk_objective primary key (id)
);
create sequence objective_seq;

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

create table product_in_basket (
  id                            bigint not null,
  quantity                      integer,
  ref_person_id                 bigint,
  ref_product_id                bigint,
  constraint pk_product_in_basket primary key (id)
);
create sequence product_in_basket_seq;

alter table diary add constraint fk_diary_user_id foreign key (user_id) references person (id) on delete restrict on update restrict;
create index ix_diary_user_id on diary (user_id);

alter table diary add constraint fk_diary_objective_id foreign key (objective_id) references objective (id) on delete restrict on update restrict;
create index ix_diary_objective_id on diary (objective_id);

alter table objective add constraint fk_objective_simple_user_id foreign key (simple_user_id) references person (id) on delete restrict on update restrict;
create index ix_objective_simple_user_id on objective (simple_user_id);

alter table product add constraint fk_product_seller_id foreign key (seller_id) references person (id) on delete restrict on update restrict;
create index ix_product_seller_id on product (seller_id);

alter table product_in_basket add constraint fk_product_in_basket_ref_person_id foreign key (ref_person_id) references person (id) on delete restrict on update restrict;
create index ix_product_in_basket_ref_person_id on product_in_basket (ref_person_id);

alter table product_in_basket add constraint fk_product_in_basket_ref_product_id foreign key (ref_product_id) references product (id) on delete restrict on update restrict;
create index ix_product_in_basket_ref_product_id on product_in_basket (ref_product_id);


# --- !Downs

alter table diary drop constraint if exists fk_diary_user_id;
drop index if exists ix_diary_user_id;

alter table diary drop constraint if exists fk_diary_objective_id;
drop index if exists ix_diary_objective_id;

alter table objective drop constraint if exists fk_objective_simple_user_id;
drop index if exists ix_objective_simple_user_id;

alter table product drop constraint if exists fk_product_seller_id;
drop index if exists ix_product_seller_id;

alter table product_in_basket drop constraint if exists fk_product_in_basket_ref_person_id;
drop index if exists ix_product_in_basket_ref_person_id;

alter table product_in_basket drop constraint if exists fk_product_in_basket_ref_product_id;
drop index if exists ix_product_in_basket_ref_product_id;

drop table if exists diary;
drop sequence if exists diary_seq;

drop table if exists objective;
drop sequence if exists objective_seq;

drop table if exists person;
drop sequence if exists person_seq;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists product_in_basket;
drop sequence if exists product_in_basket_seq;

