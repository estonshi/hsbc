CREATE TABLE transactions (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    tid VARCHAR(255) NOT NULL UNIQUE,
    from_user VARCHAR(255),
    to_user VARCHAR(255),
    value_curr DECIMAL(10, 2),
    type VARCHAR(255),
    create_time BIGINT
);