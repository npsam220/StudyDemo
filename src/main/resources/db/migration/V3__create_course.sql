CREATE TABLE course (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    course_no VARCHAR(20) NOT NULL UNIQUE COMMENT '課程代號',

    course_name VARCHAR(100) NOT NULL COMMENT '課程名稱',

    credit INT NOT NULL COMMENT '學分',

    semester VARCHAR(20) COMMENT '2026-1',

    teacher_id BIGINT NOT NULL,

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,

    update_time DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_course_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teacher(teacher_id)
);