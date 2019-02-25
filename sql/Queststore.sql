CREATE TABLE "Codecooler" (
  "id" INTEGER,
  "classid" INTEGER,
  "teamid" INTEGER,
  "userid" INTEGER,
  "earnings" INTEGER,
  PRIMARY KEY ("id")
);

CREATE TABLE "Mentor" (
  "id" INTEGER,
  "email" TEXT,
  "classid" INTEGER,
  "userid" INTEGER,
  PRIMARY KEY ("id")
);

CREATE TABLE "User" (
  "id" INTEGER,
  "login" TEXT,
  "password" TEXT,
  "userType" TEXT,
  "name" TEXT,
  "surname" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "Class" (
  "id" INTEGER,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "Team" (
  "id" INTEGER,
  "name" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "Item" (
  "id" INTEGER,
  "name" TEXT,
  "description" TEXT,
  "price" REAL,
  PRIMARY KEY ("id")
);

CREATE TABLE "Quest" (
  "id" INTEGER,
  "name" TEXT,
  "description" TEXT,
  PRIMARY KEY ("id")
);

CREATE TABLE "Completed_quests" (
  "id" INTEGER,
  "codecoolerid" INTEGER,
  "questid" INTEGER,
  "date" DATE,
  PRIMARY KEY ("id")
);

CREATE TABLE "Bought_items" (
  "id" INTEGER,
  "coodecoolerid" INTEGER,
  "itemid" INTEGER,
  PRIMARY KEY ("id")
);
