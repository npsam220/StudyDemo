CREATE TABLE teacher (
    teacher_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    teacher_no VARCHAR(20) NOT NULL UNIQUE COMMENT '教師編號',

    teacher_name VARCHAR(100) NOT NULL COMMENT '教師姓名',

    gender CHAR(1) COMMENT 'M/F',

    email VARCHAR(100),

    phone VARCHAR(20),

    office VARCHAR(100),

    title VARCHAR(50) COMMENT 'Professor、Associate Professor...',

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,

    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);