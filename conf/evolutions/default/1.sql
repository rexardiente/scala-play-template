# --- !Ups

create table "USER_ACCOUNT_INFO" ("ID" UUID NOT NULL,"USERNAME" VARCHAR NOT NULL,"PASSWORD" VARCHAR NOT NULL,"EMAIL" VARCHAR NOT NULL,"CREATE_AT" timestamp NOT NULL);



# --- !Downs

drop table "USER_ACCOUNT_INFO";
