CREATE TABLE "session" (
  "id" SERIAL,
  "sessionid" TEXT,
  "userid" INTEGER,
  PRIMARY KEY ("id")
);

CREATE TABLE "codecooler" (
  "roomid" INTEGER,
  "teamid" INTEGER,
  "userid" INTEGER,
  "earnings" INTEGER
);

CREATE TABLE "mentor" (
  "email" TEXT,
  "roomid" INTEGER,
  "userid" INTEGER
);

CREATE TABLE "users" (
  "id" SERIAL,
  "login" TEXT,
  "password" TEXT,
  "usertype" TEXT,
  "name" TEXT,
  "surname" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "room" (
  "id" SERIAL,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "team" (
  "id" SERIAL,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "item" (
  "id" SERIAL,
  "name" TEXT,
  "description" TEXT,
  "itemtype" TEXT,
  "price" REAL,
  PRIMARY KEY ("id")
);

CREATE TABLE "quest" (
  "id" SERIAL,
  "name" TEXT,
  "description" TEXT,
  "coins" INTEGER,
  "quest_type" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "completed_quests" (
  "id" SERIAL,
  "userid" INTEGER,
  "questid" INTEGER,
  "date" DATE,
  PRIMARY KEY ("id")
);

CREATE TABLE "bought_items" (
  "id" SERIAL,
  "userid" INTEGER,
  "itemid" INTEGER,
  "date" DATE,
  PRIMARY KEY ("id")
);
