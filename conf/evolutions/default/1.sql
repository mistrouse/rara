# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table person (
  id                            bigint not null,
  name                          varchar(255),
  email                         varchar(255),
  pseudo                        varchar(255),
  siret                         varchar(255),
  password                      varchar(255),
  role                          integer,
  token                         varchar(255),
  constraint uq_person_email unique (email),
  constraint pk_person primary key (id)
);
create sequence person_seq;

create table task (
  id                            bigint not null,
  name                          varchar(255),
  creator_id                    bigint,
  constraint pk_task primary key (id)
);
create sequence task_seq;

alter table task add constraint fk_task_creator_id foreign key (creator_id) references person (id) on delete restrict on update restrict;
create index ix_task_creator_id on task (creator_id);


# --- !Downs

alter table task drop constraint if exists fk_task_creator_id;
drop index if exists ix_task_creator_id;

drop table if exists person;
drop sequence if exists person_seq;

drop table if exists task;
drop sequence if exists task_seq;

