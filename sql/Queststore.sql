CREATE TABLE "codecooler" (
  "id" INTEGER,
  "classid" INTEGER,
  "teamid" INTEGER,
  "userid" INTEGER,
  "earnings" INTEGER,
  PRIMARY KEY ("id")
);

CREATE TABLE "mentor" (
  "id" INTEGER,
  "email" TEXT,
  "classid" INTEGER,
  "usrlogin" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "user" (
  "id" INTEGER,
  "login" TEXT,
  "password" TEXT,
  "userType" TEXT,
  "name" TEXT,
  "surname" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "class" (
  "id" INTEGER,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "team" (
  "id" INTEGER,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "item" (
  "id" INTEGER,
  "name" TEXT,
  "description" TEXT,
  "itemType" TEXT,
  "price" REAL,
  PRIMARY KEY ("id")
);

CREATE TABLE "quest" (
  "id" INTEGER,
  "name" TEXT,
  "description" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "completed_quests" (
  "id" INTEGER,
  "codecoolerid" INTEGER,
  "questid" INTEGER,
  "date" DATE,
  PRIMARY KEY ("id")
);

CREATE TABLE "bought_items" (
  "id" INTEGER,
  "coodecoolerid" INTEGER,
  "itemid" INTEGER,
  PRIMARY KEY ("id")
);
