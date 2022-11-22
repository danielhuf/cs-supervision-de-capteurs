CREATE SCHEMA IF NOT EXISTS Library_ST5;
USE Library_ST5;

SELECT * FROM Authors;
SELECT * FROM Authorships;
SELECT * FROM Books;
SELECT * FROM BorrowHistory;
SELECT * FROM Categories;
SELECT * FROM Editions;
SELECT * FROM Publishers;
SELECT * FROM RedListLog;
SELECT * FROM Users;
SELECT * FROM Works;

UPDATE borrowhistory SET BorrowingDate = '2015-11-08' WHERE ID = 1;

INSERT INTO RedListLog VALUES (1, 'put', '2022-09-25');
INSERT INTO RedListLog VALUES (1, 'removed', '2022-09-26');
INSERT INTO RedListLog VALUES (1, 'removed', '2022-09-27');
INSERT INTO RedListLog VALUES (2, 'put', '2022-09-25');
INSERT INTO RedListLog VALUES (2, 'removed', '2022-09-29');
INSERT INTO RedListLog VALUES (2, 'put', '2022-09-29');

INSERT INTO borrowhistory VALUES (1, 22, '2022-10-25', null);
INSERT INTO borrowhistory VALUES (1, 1, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 2, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 3, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 4, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 5, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 6, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 7, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 8, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 9, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 10, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 11, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 12, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 13, '2022-09-20', null);
INSERT INTO borrowhistory VALUES (1, 14, '2022-11-20', null);
INSERT INTO borrowhistory VALUES (1, 14, '2022-12-20', null);
INSERT INTO borrowhistory VALUES (1, 14, '2022-06-20', null);
INSERT INTO borrowhistory VALUES (1, 1, '2022-05-20', null);