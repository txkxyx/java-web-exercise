INSERT INTO USERS(EMAIL, PASSWORD, NAME) VALUES('user1@okatter.com', 'user1', 'User First');
INSERT INTO USERS(EMAIL, PASSWORD, NAME) VALUES('user2@okatter.com', 'user2', 'User Second');
INSERT INTO USERS(EMAIL, PASSWORD, NAME) VALUES('user3@okatter.com', 'user3', 'User Third');

INSERT INTO TWEET(TITLE, BODY, USER_ID, UPDATE_AT) VALUES('今日の天気', '今日は晴れてとても暑いです。', 1 , now());
INSERT INTO TWEET(TITLE, BODY, USER_ID, UPDATE_AT) VALUES('今日のお昼ご飯', '今日はお昼にうどんを食べました。', 1, now());
INSERT INTO TWEET(TITLE, BODY, USER_ID, UPDATE_AT) VALUES('明日の予定', '明日は朝から大事な会議があります。', 3, now());