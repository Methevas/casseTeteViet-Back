DROP TABLE IF EXISTS solutions;


CREATE TABLE solutions
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    numerotation VARCHAR(250) NOT NULL
);

INSERT INTO solutions (numerotation) VALUES ('1,2,3,4,5,6,7,8,9');
INSERT INTO solutions (numerotation) VALUES ('1,6,5,9,3,2,7,8,4');
INSERT INTO solutions (numerotation) VALUES ('9,2,3,8,7,6,5,4,1');
