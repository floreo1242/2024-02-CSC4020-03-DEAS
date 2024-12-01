
CREATE TABLE answer
(
  id          INT                     NOT NULL AUTO_INCREMENT COMMENT '답변번호',
  apply_id    INT                     NOT NULL COMMENT '지원번호',
  question_id INT                     NOT NULL COMMENT '질문번호',
  type        ENUM('CHOICE', 'ESSAY') NOT NULL COMMENT '답변유형',
  choice_id   INT                     NULL     COMMENT '선택지번호',
  answer      TEXT                    NULL     COMMENT '답변내용',
  PRIMARY KEY (id)
) COMMENT '답변';

ALTER TABLE answer
  ADD CONSTRAINT UQ_apply_id_question_id UNIQUE (apply_id, question_id);

CREATE TABLE apply
(
  id         INT         NOT NULL AUTO_INCREMENT COMMENT '지원번호',
  member_id  VARCHAR(20) NOT NULL COMMENT '학번/교번',
  event_id   INT         NOT NULL COMMENT '행사번호',
  apply_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '지원일시',
  PRIMARY KEY (id)
) COMMENT '지원';

ALTER TABLE apply
  ADD CONSTRAINT UQ_member_id_event_id UNIQUE (member_id, event_id);

CREATE TABLE choice
(
  id          INT          NOT NULL AUTO_INCREMENT COMMENT '선택지번호',
  question_id INT          NOT NULL COMMENT '질문번호',
  content     VARCHAR(255) NOT NULL COMMENT '선택지내용',
  PRIMARY KEY (id)
) COMMENT '선택지';

CREATE TABLE event
(
  id               INT                                        NOT NULL AUTO_INCREMENT COMMENT '행사번호',
  creator_id       VARCHAR(20)                                NOT NULL COMMENT '학번/교번',
  name             VARCHAR(40)                                NOT NULL COMMENT '행사명',
  tag              ENUM('SESSION', 'SEMINAR', 'CLUB', 'MISC') NULL     COMMENT '태그',
  description      TEXT                                       NOT NULL COMMENT '행사내용',
  max_participant  INT                                        NOT NULL DEFAULT 0 COMMENT '모집인원',
  apply_start_time TIMESTAMP                                  NOT NULL COMMENT '모집시작일시',
  apply_end_time   TIMESTAMP                                  NOT NULL COMMENT '모집종료일시',
  organizer        VARCHAR(40)                                NOT NULL COMMENT '주최처',
  contact          VARCHAR(20)                                NOT NULL COMMENT '문의연락처',
  PRIMARY KEY (id)
) COMMENT '행사';

CREATE TABLE event_location
(
  event_id    INT         NOT NULL COMMENT '행사번호',
  location_id VARCHAR(20) NOT NULL COMMENT '호수',
  start_time  TIMESTAMP   NOT NULL COMMENT '시작시간',
  end_time    TIMESTAMP   NULL     COMMENT '종료시간',
  PRIMARY KEY (event_id, location_id)
) COMMENT '행사장소';

CREATE TABLE location
(
  id       VARCHAR(20) NOT NULL COMMENT '호수',
  capacity INT         NOT NULL COMMENT '수용인원',
  PRIMARY KEY (id)
) COMMENT '장소';

CREATE TABLE member
(
  id          VARCHAR(20)                       NOT NULL COMMENT '학번/교번',
  password    VARCHAR(255)                      NOT NULL COMMENT '비밀번호',
  name        VARCHAR(50)                       NOT NULL COMMENT '이름',
  affiliation VARCHAR(40)                       NOT NULL COMMENT '소속',
  contact     VARCHAR(40)                       NOT NULL COMMENT '연락처',
  type        ENUM('STUDENT', 'STAFF', 'ADMIN') NOT NULL COMMENT '유형',
  PRIMARY KEY (id)
) COMMENT '회원';

CREATE TABLE question
(
  id       INT                     NOT NULL AUTO_INCREMENT COMMENT '질문번호',
  event_id INT                     NOT NULL COMMENT '행사번호',
  content  VARCHAR(255)            NULL     COMMENT '질문내용',
  type     ENUM('CHOICE', 'ESSAY') NULL     COMMENT '유형',
  PRIMARY KEY (id)
) COMMENT '질문';

ALTER TABLE apply
  ADD CONSTRAINT FK_member_TO_apply
    FOREIGN KEY (member_id)
    REFERENCES member (id);

ALTER TABLE apply
  ADD CONSTRAINT FK_event_TO_apply
    FOREIGN KEY (event_id)
    REFERENCES event (id)
    ON DELETE CASCADE;

ALTER TABLE event_location
  ADD CONSTRAINT FK_location_TO_event_location
    FOREIGN KEY (location_id)
    REFERENCES location (id);

ALTER TABLE event_location
  ADD CONSTRAINT FK_event_TO_event_location
    FOREIGN KEY (event_id)
    REFERENCES event (id)
    ON DELETE CASCADE;

ALTER TABLE question
  ADD CONSTRAINT FK_event_TO_question
    FOREIGN KEY (event_id)
    REFERENCES event (id)
    ON DELETE CASCADE;

ALTER TABLE choice
  ADD CONSTRAINT FK_question_TO_choice
    FOREIGN KEY (question_id)
    REFERENCES question (id)
    ON DELETE CASCADE;

ALTER TABLE answer
  ADD CONSTRAINT FK_question_TO_answer
    FOREIGN KEY (question_id)
    REFERENCES question (id)
    ON DELETE CASCADE;

ALTER TABLE answer
  ADD CONSTRAINT FK_choice_TO_answer
    FOREIGN KEY (choice_id)
    REFERENCES choice (id)
    ON DELETE CASCADE;

ALTER TABLE answer
  ADD CONSTRAINT FK_apply_TO_answer
    FOREIGN KEY (apply_id)
    REFERENCES apply (id)
    ON DELETE CASCADE;

ALTER TABLE event
  ADD CONSTRAINT FK_member_TO_event
    FOREIGN KEY (creator_id)
    REFERENCES member (id);
