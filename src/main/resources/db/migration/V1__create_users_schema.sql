drop table if exists users;

CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    created_user_id BIGINT NOT NULL,
    last_updated_user_id BIGINT NOT NULL,
);

alter table users
    add constraint fk_created_user foreign key (created_user_id) references users (user_id);
alter table users
    add constraint fk_last_updated_user foreign key (last_updated_user_id) references users (user_id);