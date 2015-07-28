CREATE TABLE DOWNLOAD (DOWNLOADID BIGINT NOT NULL, DOWNLOADDATE DATE, PRODUCTISBN VARCHAR(255), USER_USERID BIGINT, PRIMARY KEY (DOWNLOADID))
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
