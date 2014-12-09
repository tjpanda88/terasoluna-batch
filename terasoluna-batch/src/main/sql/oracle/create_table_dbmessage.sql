-- 
-- TABLE: DBMESSAGES
--
DROP TABLE DBMESSAGES;

CREATE TABLE DBMESSAGES (
    ID             NUMBER(5)  NOT NULL,
    CODE           VARCHAR2(32)  NOT NULL,
    LANGUAGES      VARCHAR2(32),
    CONTRY         VARCHAR2(32),
    VARIANT        VARCHAR2(32),   
    MESSAGE        VARCHAR2(256),
    CONSTRAINT PK_DBMESSAGES PRIMARY KEY (ID)
);

quit;

