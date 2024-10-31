CREATE TABLE token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    token_type VARCHAR(20) DEFAULT 'BEARER',
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT,
);

alter table token
    add constraint fk_user foreign key (user_id) references users (id) on delete cascade;