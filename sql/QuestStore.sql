CREATE TABLE "codecooler" (
  "id" SERIAL,
  "roomid" INTEGER,
  "teamid" INTEGER,
  "userid" INTEGER,
  "earnings" INTEGER,
  PRIMARY KEY ("id")
);

CREATE TABLE "mentor" (
  "id" SERIAL,
  "email" TEXT,
  "roomid" INTEGER,
  "userid" INTEGER,
  PRIMARY KEY ("id")
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
  PRIMARY KEY ("id")
);

CREATE TABLE "completed_quests" (
  "id" SERIAL,
  "codecoolerid" INTEGER,
  "questid" INTEGER,
  "date" DATE,
  PRIMARY KEY ("id")
);

CREATE TABLE "bought_items" (
  "id" SERIAL,
  "codecoolerid" INTEGER,
  "itemid" INTEGER,
  PRIMARY KEY ("id")
);
