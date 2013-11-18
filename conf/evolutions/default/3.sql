# Date and time functions

# --- !Ups
alter table "round"
  drop constraint "roundFK4";
  
alter table "round"
  drop column "counter_id";

# --- !Downs
alter table "round"
  create column "counter_id" bigint;
  
alter table "round" add constraint "roundFK4" foreign key ("counter_id") references "player"("id");
