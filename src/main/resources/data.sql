-- 清空现有数据（谨慎使用）
DELETE FROM orders;
DELETE FROM item;
DELETE FROM user;

-- 插入用户
INSERT INTO user VALUES('u001','ZhangSan','13800000001');
INSERT INTO user VALUES('u002','LiSi','13800000002');
INSERT INTO user VALUES('u003','WangWu','13800000003');
INSERT INTO user VALUES('u004','ZhaoLiu','13800000004');

-- 插入商品
INSERT INTO item VALUES('i001','CalculusBook','Book',20,0,'u001');
INSERT INTO item VALUES('i002','DeskLamp','DailyGoods',35,1,'u002');
INSERT INTO item VALUES('i003','Microcontroller','Electronics',80,0,'u001');
INSERT INTO item VALUES('i004','Chair','Furniture',50,1,'u003');
INSERT INTO item VALUES('i005','WaterBottle','DailyGoods',15,0,'u004');

-- 插入自定义商品（作业要求）
INSERT INTO item VALUES('i006','Notebook','DailyGoods',10,0,'u002');

-- 插入订单（已售商品）
INSERT INTO orders VALUES(1,'i002','u003','2025-04-01');
INSERT INTO orders VALUES(2,'i004','u002','2025-04-02');