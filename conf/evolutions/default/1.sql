# Schema creation
# --- !Ups
create table "game" (
    "dateplayed" timestamp not null,
    "id" bigint primary key not null,
    "instigator_id" bigint not null
  );  
create sequence "game_id_seq";
create unique index "idx2e2705e5" on "game" ("dateplayed");
create index "idx43760738" on "game" ("instigator_id");

create table "round" (
    "game_id" bigint not null,
    "counter_id" bigint not null,
    "id" bigint primary key not null,
    "round" integer not null
  );
create sequence "round_id_seq";
create index "idx2469051c" on "round" ("game_id");

create table "play" (
    "hand" varchar(128) not null,
    "id" bigint primary key not null,
    "round_id" bigint not null,
    "player_id" bigint not null
  );
create sequence "play_id_seq";
-- indexes on play
create index "idx11ba037f" on "play" ("hand");
create index "idx24a70538" on "play" ("round_id");

create table "player" (
    "name" varchar(128) not null,
    "email" varchar(128),
    "id" bigint primary key not null,
    "graphingcolour" varchar(128) not null
  );
create sequence "player_id_seq";
-- indexes on player
create unique index "idx1aae045c" on "player" ("name");
create unique index "idx1f5d04c3" on "player" ("email");
-- foreign key constraints :
alter table "game" add constraint "gameFK1" foreign key ("instigator_id") references "player"("id");
alter table "round" add constraint "roundFK2" foreign key ("game_id") references "game"("id");
alter table "play" add constraint "playFK3" foreign key ("round_id") references "round"("id");
alter table "round" add constraint "roundFK4" foreign key ("counter_id") references "player"("id");
alter table "play" add constraint "playFK5" foreign key ("player_id") references "player"("id");

# --- !Downs

drop sequence play_id_seq;
drop sequence player_id_seq;
drop sequence game_id_seq;
drop sequence round_id_seq;

drop table play;
drop table round;
drop table game;
drop table player;
