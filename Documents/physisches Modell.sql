--Datenbank Sporteventmanager

--Cleanup
--Drop Tables
DROP TABLE Account CASCADE CONSTRAINTS;
DROP TABLE Sportart CASCADE CONSTRAINTS;
DROP TABLE Ort CASCADE CONSTRAINTS;
DROP TABLE Veranstalter CASCADE CONSTRAINTS;
DROP TABLE Teilnehmer CASCADE CONSTRAINTS;
DROP TABLE Veranstaltung CASCADE CONSTRAINTS;
DROP TABLE Teilnahme CASCADE CONSTRAINTS;

--Drop Sequences
DROP SEQUENCE seq_account_id;
DROP SEQUENCE seq_ort_id;
DROP SEQUENCE seq_veranstaltung_id;

--Create Tables
--Account
CREATE SEQUENCE seq_account_id START WITH 0 INCREMENT BY 1 MINVALUE 0;
CREATE TABLE Account(
  id INTEGER,
  name VARCHAR2(100),
  email VARCHAR2(100),
  password VARCHAR2(100),
  isVerified  NUMBER(1,0) default 0 not null,
  timestamp DATE default sysdate,
  
  CONSTRAINT pk_account PRIMARY KEY(id),
  CONSTRAINT uq_account_email UNIQUE(email),
  CONSTRAINT ck_account_email_valid CHECK(email like '%___@___%'),
  CONSTRAINT ck_account_isVarifed_valid CHECK(isVerified = 1 or isVerified = 0)
);

CREATE TABLE Sportart(
  name VARCHAR2(100),
  
  CONSTRAINT pk_Sportart PRIMARY KEY(name)
);

CREATE SEQUENCE seq_ort_id START WITH 0 INCREMENT BY 1  MINVALUE 0;
CREATE TABLE Ort(
  id INTEGER,
  name VARCHAR2(200),
  latitude FLOAT,
  longitude FLOAT,
  
  CONSTRAINT pk_geodaten PRIMARY KEY(id),
  CONSTRAINT uq_gedaten UNIQUE(name, latitude, longitude)
);

--Veranstalter
CREATE TABLE Veranstalter(
  id_account INTEGER,
  
  CONSTRAINT pk_veranstalter PRIMARY KEY(id_account),
  CONSTRAINT fk_veranstalter_id_account FOREIGN KEY(id_account) REFERENCES Account(id)
);

--Teilnehmer
CREATE TABLE Teilnehmer(
  id_account INTEGER,
  score INTEGER DEFAULT 0,
  
  CONSTRAINT pk_teilnehmer PRIMARY KEY(id_account),
  CONSTRAINT fk_teilnehmer_id_account FOREIGN KEY(id_account) REFERENCES Account(id)
);

--Veranstaltung
CREATE SEQUENCE seq_veranstaltung_id START WITH 0 INCREMENT BY 1 MINVALUE 0;
CREATE TABLE Veranstaltung(
  id INTEGER,
  name VARCHAR2(100),
  sportart VARCHAR(100),
  id_veranstalter INTEGER,
  location INTEGER,
  datetime DATE,
  details VARCHAR2(1000),
  min_teilnehmer INTEGER,
  max_teilnehmer INTEGER,
  
  CONSTRAINT pk_veranstaltung PRIMARY KEY(id),
  CONSTRAINT fk_veranstaltung_sportart FOREIGN KEY(sportart) REFERENCES Sportart(name),
  CONSTRAINT fk_veranstaltung_id_veranst FOREIGN KEY(id_veranstalter) REFERENCES Veranstalter(id_account),
  CONSTRAINT fk_veranstaltung_location FOREIGN KEY(location) REFERENCES ort(id)
);

--Teilnahme
CREATE TABLE Teilnahme(
  id_veranstaltung INTEGER,
  id_teilnehmer INTEGER,
  starting_number INTEGER,
  score INTEGER,
  
  CONSTRAINT pk_teilnahme PRIMARY KEY(id_veranstaltung, id_teilnehmer),
  CONSTRAINT fk_teilnahme_id_veranstaltung FOREIGN KEY(id_veranstaltung) REFERENCES Veranstaltung(id),
  CONSTRAINT fk_teilnahme_id_teilnehmer FOREIGN KEY(id_teilnehmer) REFERENCES Teilnehmer(id_account)
);

--Insert Test Data
INSERT INTO Account VALUES(seq_account_id.NEXTVAL, 'NicoKandut', 'nico.kandut@gmail.com', 'nk', 0, sysdate);
INSERT INTO Account VALUES(seq_account_id.NEXTVAL, 'AngelikaBabin', 'babin.angelika@gmail.com', 'ab', 0, sysdate);
INSERT INTO Account VALUES(seq_account_id.NEXTVAL, 'ChristofKraschl', 'kraschlc@edu.htl-villach.at', 'ck', 0, sysdate);
INSERT INTO Account VALUES(seq_account_id.NEXTVAL, 'CoraKumnig', 'corakumnig@gmail.com', 'ck', 0, sysdate);
INSERT INTO Account VALUES(seq_account_id.NEXTVAL, 'KristianRajic', 'rajic-kristion59560@gmx.at', 'kr', 0, sysdate);

INSERT INTO Sportart VALUES('BALLSPORT');
INSERT INTO Sportart VALUES('RENNSPORT');
INSERT INTO Sportart VALUES('KAMPFSPORT');
INSERT INTO Sportart VALUES('KLETTERSPORT');
INSERT INTO Sportart VALUES('SCHWIMMSPORT');
INSERT INTO Sportart VALUES('BASKETBALL');
INSERT INTO Sportart VALUES('EXTREMSPORT');

INSERT INTO Ort VALUES(seq_ort_id.NEXTVAL, 'HTL Villach', 46.601109,13.8489569);
INSERT INTO Ort VALUES(seq_ort_id.NEXTVAL, 'Hauptplatz Feldkirchen', 46.7235859,14.0923111);

INSERT INTO Veranstalter VALUES(5);

INSERT INTO Teilnehmer VALUES(1, 0);
INSERT INTO Teilnehmer VALUES(2, 117);
INSERT INTO Teilnehmer VALUES(3, 17503);
INSERT INTO Teilnehmer VALUES(4, 1000000);

INSERT INTO Veranstaltung VALUES(seq_veranstaltung_id.NEXTVAL, 'Rote Nasen Lauf', 'RENNSPORT', 5, 1, DATE '2018-03-20', 'Ein Lauf.', null, null);
INSERT INTO Veranstaltung VALUES(seq_veranstaltung_id.NEXTVAL, 'Basketballspiel', 'BASKETBALL', 5, 2, DATE '2018-11-14', 'Ein spannedes Spiel!', 6, 20);
INSERT INTO Veranstaltung VALUES(seq_veranstaltung_id.NEXTVAL, 'Tauchen', 'EXTREMSPORT', 5, 1, DATE '2018-07-20', 'Ein Lauf.', null, null);
INSERT INTO Veranstaltung VALUES(seq_veranstaltung_id.NEXTVAL, 'Ballspiel', 'BALLSPORT', 5, 2, DATE '2018-08-08', 'Ein spannedes Spiel!', 6, 20);

INSERT INTO Teilnahme VALUES(1, 1, 1, 100);
INSERT INTO Teilnahme VALUES(1, 2, 2, 150);

INSERT INTO Teilnahme VALUES(1, 4, 1, 100);
INSERT INTO Teilnahme VALUES(2, 4, 2, 150);
INSERT INTO Teilnahme VALUES(3, 4, 17, NULL);
INSERT INTO Teilnahme VALUES(4, 4, 20, NULL);

COMMIT;