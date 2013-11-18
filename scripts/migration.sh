#!/bin/sh

SOURCE_DB="$1"
TARGET_HOST="$2"
TARGET_DB="$3"
TARGET_USER="$4"

psql -h "$TARGET_HOST" -d "$TARGET_DB" -U "$TARGET_USER" << DB
  truncate table play, round, game, player;
  
  alter table game
    add column dayplayed bigint, 
    add column monthplayed bigint, 
    add column weekplayed bigint,
    add column yearplayed bigint;
    
  alter table round add column counter_id bigint;
DB

pg_dump -a -t person -t game -t round -t play "$SOURCE_DB" | sed 's/person/player/g' | \
  psql -h "$TARGET_HOST" -U "$TARGET_USER" -d "$TARGET_DB"

psql -h "$TARGET_HOST" -d "$TARGET_DB" -U "$TARGET_USER" << DB
  alter table game
    drop column dayplayed, 
    drop column monthplayed, 
    drop column weekplayed,
    drop column yearplayed;

  alter table round drop column round_id;
  
  select setval('game_id_seq', (select max(id)+1 from game), false);
  select setval('play_id_seq', (select max(id)+1 from play), false);
  select setval('player_id_seq', (select max(id)+1 from player), false);
  select setval('round_id_seq', (select max(id)+1 from round), false);
DB
