create table income (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                        amount INT NOT NULL ,
                        category VARCHAR(255) NOT NULL ,
                        categoryMemo VARCHAR(255) ,
                        date DATETIME NOT NULL
);

create table expenditure
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    amount INT NOT NULL ,
    category VARCHAR(255) NOT NULL ,
    categoryMemo VARCHAR(255) ,
    date DATETIME NOT NULL
);