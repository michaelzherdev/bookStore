/********************************************************
* Create database named bookstore for BookStore project 
********************************************************/

DROP DATABASE IF EXISTS bookstore;

CREATE DATABASE bookstore;

USE bookstore;

CREATE TABLE User(
	UserId INT NOT NULL AUTO_INCREMENT,
	Email VARCHAR(50),
	FirstName VARCHAR(50),
	LastName VARCHAR(50),
	PRIMARY KEY(UserId)
);

INSERT INTO User(Email, FirstName, LastName)
VALUES
	('michaelzherdev@gmail.com', 'Michael', 'Zherdev'),
	('evgenkomarovsky@mail.ru', 'John', 'Komarovsky'),
	('jsmith@mail.com', 'John', 'Smith');
	
CREATE TABLE Download (
	DownloadID INT NOT NULL AUTO_INCREMENT,
	UserId INT NOT NULL,
	DownloadDate DATETIME NOT NULL,
	DownloadFilename VARCHAR(30) NOT NULL,
	ProductCode VARCHAR(10) NOT NULL,
	
	PRIMARY KEY(DownloadID),
	FOREIGN KEY(UserId) REFERENCES User (UserId)
);

INSERT INTO Download (UserId, DownloadDate, DownloadFilename, ProductCode)
VALUES
	(1, '2015-07-27', 'book1.doc', 'book1');
	
CREATE TABLE UserPass (
	Username VARCHAR(15) NOT NULL PRIMARY KEY,
	Password VARCHAR(15) NOT NULL
);

INSERT INTO UserPass
VALUES
	('michaelzherdev', 'admin'),
	('admin', 'admin');
	
CREATE TABLE UserRole(
	Username VARCHAR(15) NOT NULL,
	Rolename VARCHAR(15) NOT NULL,
	
	PRIMARY KEY(Username, Rolename)
);

INSERT INTO UserRole
VALUES
	('michaelzherdev', 'programmer'),
	('admin', 'programmer');
	
-- create book_user and grant privileges
DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
BEGIN
	DECLARE userCount BIGINT DEFAULT 0;
	
	SELECT COUNT(*) INTO userCount FROM mysql.user
	WHERE User = 'book_user' and Host = 'localhost';
	
	IF userCount > 0 THEN
		DROP USER book_user@localhost;
	END IF;
END; //
DELIMITER ;

CALL drop_user_if_exists();

CREATE USER book_user@localhost IDENTIFIED BY 'admin';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON bookstore.*
TO book_user@localhost;


/***********************************************************
* Create the database named bookstore_jpa
************************************************************/

DROP DATABASE IF EXISTS bookstore_jpa;

CREATE DATABASE bookstore_jpa;


/***********************************************************
* Create the database named book_jpa
************************************************************/

DROP DATABASE IF EXISTS book_jpa;

CREATE DATABASE book_jpa;

USE book_jpa;


/***********************************************************
* Following tables were generated by JPA and exported from 
* MySQL into this script
************************************************************/

CREATE TABLE DOWNLOAD (DOWNLOADID BIGINT NOT NULL, DOWNLOADDATE DATE, PRODUCTCODE VARCHAR(255), USER_USERID BIGINT, PRIMARY KEY (DOWNLOADID))
CREATE TABLE INVOICE (INVOICENUMBER BIGINT NOT NULL, INVOICEDATE DATE, ISPROCESSED TINYINT(1) default 0, USER_USERID BIGINT, PRIMARY KEY (INVOICENUMBER))
CREATE TABLE LINEITEM (LINEITEMID BIGINT NOT NULL, QUANTITY INTEGER, PRODUCT_PRODUCTID BIGINT, PRIMARY KEY (LINEITEMID))
CREATE TABLE PRODUCT (PRODUCTID BIGINT NOT NULL, ISBN VARCHAR(255), DESCRIPTION VARCHAR(255), PRICE DOUBLE, PRIMARY KEY (PRODUCTID))
CREATE TABLE USER (USERID BIGINT NOT NULL, ADDRESS VARCHAR(255), CITY VARCHAR(255), COMPANYNAME VARCHAR(255), COUNTRY VARCHAR(255), CREDITCARDEXPIRATION VARCHAR(255), CREDITCARDNUMBER VARCHAR(255), CREDITCARDTYPE VARCHAR(255), EMAIL VARCHAR(255), FIRSTNAME VARCHAR(255), LASTNAME VARCHAR(255), ST VARCHAR(255), ZIP VARCHAR(255), PRIMARY KEY (USERID))
CREATE TABLE INVOICE_LINEITEM (Invoice_INVOICENUMBER BIGINT NOT NULL, lineItems_LINEITEMID BIGINT NOT NULL, PRIMARY KEY (Invoice_INVOICENUMBER, lineItems_LINEITEMID))
ALTER TABLE DOWNLOAD ADD CONSTRAINT FK_DOWNLOAD_USER_USERID FOREIGN KEY (USER_USERID) REFERENCES USER (USERID)
ALTER TABLE INVOICE ADD CONSTRAINT FK_INVOICE_USER_USERID FOREIGN KEY (USER_USERID) REFERENCES USER (USERID)
ALTER TABLE LINEITEM ADD CONSTRAINT FK_LINEITEM_PRODUCT_PRODUCTID FOREIGN KEY (PRODUCT_PRODUCTID) REFERENCES PRODUCT (PRODUCTID)
ALTER TABLE INVOICE_LINEITEM ADD CONSTRAINT FK_INVOICE_LINEITEM_lineItems_LINEITEMID FOREIGN KEY (lineItems_LINEITEMID) REFERENCES LINEITEM (LINEITEMID)
ALTER TABLE INVOICE_LINEITEM ADD CONSTRAINT FK_INVOICE_LINEITEM_Invoice_INVOICENUMBER FOREIGN KEY (Invoice_INVOICENUMBER) REFERENCES INVOICE (INVOICENUMBER)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)

/***********************************************************
* The following data was originally inserted by the hand
************************************************************/

INSERT INTO product
VALUES
(1,'1430265175','Joseph Ottinger - Beginning Hibernate 3rd Edition',22.95),
(2,'0071808558','Herbert Schildt - Java: The Complete Reference (Complete Reference Series) 9th Edition',48.95),
(3,'0131872486','Bruce Eckel - Thinking in Java (4th Edition) 4th Edition',42.45),
(4,'1890774782','Joel Murach - Java Servlets and JSP, 3rd Edition',44.95),
(5,'161729120X','Craig Walls - Spring in action 4th Edition',36.95);
USE bookstore;