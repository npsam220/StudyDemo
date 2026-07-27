CREATE TABLE student (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '學號',

    student_name VARCHAR(100) NOT NULL COMMENT '學生姓名',

    gender CHAR(1),

    birthday DATE,

    email VARCHAR(100),

    phone VARCHAR(20),

    department VARCHAR(100),

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,

    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);