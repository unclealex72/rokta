# More than one email address per player.

# --- !Ups
create table "email" (
    "email" varchar(128) not null,
    "id" bigint primary key not null,
    "player_id" bigint not null
  );

create sequence "email_id_seq";

alter table "email" add constraint "emailFK1" foreign key ("player_id") references "player"("id");

insert into email (id, email, player_id)
select
	nextval('email_id_seq') as id,
	email as email,
	id as player_id from player where email is not null;

drop index "idx1f5d04c3";

alter table "player" drop column email;

# --- !Downs
alter table "player" add column email varchar(128);

update player set email = email.email from email
  where player.id = email.player_id;

drop sequence "email_id_seq";

drop table "email";