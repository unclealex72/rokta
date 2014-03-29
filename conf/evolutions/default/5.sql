# Avatar URLs.

# --- !Ups
alter table "player" add column avatarurl varchar(256);

# --- !Downs
alter table "player" drop column avatarurl;
