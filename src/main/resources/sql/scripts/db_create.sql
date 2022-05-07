CREATE TABLE "tbl_category" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "tbl_item" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(100) UNIQUE NOT NULL,
  "description" varchar(500),
  "price" float NOT NULL,
  "image" varchar(200),
  "visible" boolean DEFAULT true,
  "category_id" int NOT NULL REFERENCES "tbl_category" ("id")
);

CREATE TABLE "tbl_icon" (
  "id" SERIAL PRIMARY KEY,
  "title" varchar(100) UNIQUE NOT NULL,
  "description" varchar(500),
  "image" varchar(200) NOT NULL
);

CREATE TABLE "tbl_item_with_icon" (
  "item_id" int REFERENCES "tbl_item" ("id"),
  "icon_id" int REFERENCES "tbl_icon" ("id") ON DELETE CASCADE,
  PRIMARY KEY ("item_id", "icon_id")
);

CREATE TABLE "tbl_table" (
  "id" SERIAL PRIMARY KEY,
  "code" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "tbl_session" (
  "id" SERIAL PRIMARY KEY,
  "token" varchar(250) UNIQUE NOT NULL,
  "creation_ts" timestamptz NOT NULL DEFAULT NOW()::TIMESTAMPTZ,
  "table_id" int NOT NULL REFERENCES "tbl_table" ("id")
);

CREATE TABLE "tbl_user" (
  "id" SERIAL PRIMARY KEY,
  "username" varchar(100) UNIQUE NOT NULL,
  "password" varchar(100) NOT NULL
);

CREATE TABLE "tbl_authority" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "tbl_user_authority" (
  "user_id" int REFERENCES "tbl_user" ("id"),
  "authority_id" int REFERENCES "tbl_authority" ("id"),
  PRIMARY KEY ("user_id", "authority_id")
);

CREATE TABLE "tbl_order" (
  "id" SERIAL PRIMARY KEY,
  "creation_ts" timestamptz NOT NULL DEFAULT NOW()::TIMESTAMPTZ,
  "taken_by" int REFERENCES "tbl_user" ("id"),
  "session_id" int NOT NULL REFERENCES "tbl_session" ("id")
);

CREATE TABLE "tbl_state" (
  "id" SERIAL PRIMARY KEY,
  "title" varchar(100) UNIQUE NOT NULL
);

CREATE TABLE "tbl_item_within_order" (
  "order_id" int REFERENCES "tbl_order" ("id"),
  "item_id" int REFERENCES "tbl_item" ("id"),
  "state_id" int NOT NULL REFERENCES "tbl_state" ("id"),
  "fulfilled_by" int REFERENCES "tbl_user" ("id"),
  PRIMARY KEY ("order_id", "item_id")
);
