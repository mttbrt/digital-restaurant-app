INSERT INTO public.tbl_category ("name") VALUES ('beverages');
INSERT INTO public.tbl_category ("name") VALUES ('main dishes');
INSERT INTO public.tbl_category ("name") VALUES ('desserts');

INSERT INTO public.tbl_icon ("title","description","image") VALUES ('spicy','Spicy food','chili.png');
INSERT INTO public.tbl_icon ("title","description","image") VALUES ('vegan','Vegan food','leaf.png');

INSERT INTO public.tbl_item ("name","description","price","image","category_id")
VALUES ('Mineral Water 0.5L','Mineral Water Sant Anna',1.5,'water.png',1);
INSERT INTO public.tbl_item ("name","description","price","image","category_id")
VALUES ('Pizza Margherita','Ingredients: flour, tomato,...',5.5,'pizza.png',2);
INSERT INTO public.tbl_item ("name","description","price","image","category_id")
VALUES ('Strawberry Cheesecake','Homemade Strawberry Cheesecake',1.5,'cheesecake.png',3);

INSERT INTO public.tbl_item_with_icon ("item_id", "icon_id") VALUES (1,2);
INSERT INTO public.tbl_item_with_icon ("item_id", "icon_id") VALUES (2,1);
INSERT INTO public.tbl_item_with_icon ("item_id", "icon_id") VALUES (2,2);
INSERT INTO public.tbl_item_with_icon ("item_id", "icon_id") VALUES (3,1);

INSERT INTO public.tbl_table (code) VALUES ('T1');
INSERT INTO public.tbl_table (code) VALUES ('T2');

INSERT INTO public.tbl_session ("token","table_id") VALUES ('63382b13-2e52-48c8-a3e7-e65af490f871',1);
INSERT INTO public.tbl_session ("token","table_id") VALUES ('8e30a016-d186-4d28-a9fd-63baa781c9e4',2);

INSERT INTO public.tbl_user ("username","password") VALUES ('admin','password');
INSERT INTO public.tbl_user ("username","password") VALUES ('user','password');

INSERT INTO public.tbl_authority ("name") VALUES ('ROLE_ADMIN');
INSERT INTO public.tbl_authority ("name") VALUES ('ROLE_STAFF');

INSERT INTO public.tbl_user_authority ("user_id","authority_id") VALUES (1,1);
INSERT INTO public.tbl_user_authority ("user_id","authority_id") VALUES (2,2);

INSERT INTO public.tbl_order ("taken_by","session_id") VALUES (2,1);
INSERT INTO public.tbl_order ("taken_by","session_id") VALUES (2,2);

INSERT INTO public.tbl_state ("title") VALUES ('ORDERED');
INSERT INTO public.tbl_state ("title") VALUES ('IN_PROGRESS');
INSERT INTO public.tbl_state ("title") VALUES ('COMPLETED');

INSERT INTO public.tbl_item_within_order ("order_id","item_id","state_id","fulfilled_by") VALUES (1,1,1,2);
INSERT INTO public.tbl_item_within_order ("order_id","item_id","state_id","fulfilled_by") VALUES (1,2,1,2);
INSERT INTO public.tbl_item_within_order ("order_id","item_id","state_id","fulfilled_by") VALUES (1,3,2,2);
INSERT INTO public.tbl_item_within_order ("order_id","item_id","state_id","fulfilled_by") VALUES (2,1,3,2);

