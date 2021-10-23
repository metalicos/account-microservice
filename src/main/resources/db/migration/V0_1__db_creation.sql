USE `account-micro`;

CREATE TABLE IF NOT EXISTS `account-micro`.`invalid_tokens`
(
    `id`                BIGINT        NOT NULL AUTO_INCREMENT,
    `account_id`        BIGINT        NOT NULL,
    `invalid_token`     VARCHAR(3000) NOT NULL,
    `expiration_date`   DATE          NOT NULL,
    `created_timestamp` DATETIME(6)   NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account`
(
    `id`                         BIGINT       NOT NULL AUTO_INCREMENT,
    `first_name`                 VARCHAR(25)  NOT NULL,
    `last_name`                  VARCHAR(25)  NOT NULL,
    `patronymic`                 VARCHAR(25)  NOT NULL,
    `username`                   VARCHAR(100) NOT NULL UNIQUE,
    `password`                   VARCHAR(300) NOT NULL,
    `photo`                      LONGBLOB     NULL DEFAULT NULL,
    `is_credentials_non_expired` BIT(1)       NULL DEFAULT NULL,
    `is_enabled`                 BIT(1)       NULL DEFAULT NULL,
    `is_non_expired`             BIT(1)       NULL DEFAULT NULL,
    `is_non_locked`              BIT(1)       NULL DEFAULT NULL,
    `created_timestamp`          DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_username` (`username` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_detail`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `account_id`        BIGINT       NOT NULL,
    `gender`            VARCHAR(255) NULL DEFAULT NULL,
    `birth_date`        DATE         NULL DEFAULT NULL,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_account_id` (`account_id` ASC) VISIBLE,
    CONSTRAINT `FK_ACCOUNT_DETAILS_account_id_ACCOUNT_id`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_role`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `role`              VARCHAR(200) NOT NULL UNIQUE,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_role` (`role` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`account_has_role`
(
    `account_id` BIGINT NOT NULL,
    `role_id`    BIGINT NOT NULL,
    PRIMARY KEY (`account_id`, `role_id`),
    INDEX `FK_role_id_ACCOUNT_ROLE_id` (`role_id` ASC) VISIBLE,
    CONSTRAINT `FK_role_id_ACCOUNT_ROLE_id`
        FOREIGN KEY (`role_id`)
            REFERENCES `account-micro`.`account_role` (`id`),
    CONSTRAINT `FK_account_id_ACCOUNT_id`
        FOREIGN KEY (`account_id`)
            REFERENCES `account-micro`.`account` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`permission`
(
    `id`                BIGINT       NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(300) NOT NULL UNIQUE,
    `value`             VARCHAR(300) NOT NULL UNIQUE,
    `created_timestamp` DATETIME(6)  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_name` (`name` ASC) VISIBLE,
    UNIQUE INDEX `UK_value` (`name` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account-micro`.`role_has_permission`
(
    `role_id`       BIGINT NOT NULL,
    `permission_id` BIGINT NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`),
    INDEX `FK_role_id_PERMISSION_id` (`permission_id` ASC) VISIBLE,
    CONSTRAINT `FK_role_id_PERMISSION_id`
        FOREIGN KEY (`permission_id`)
            REFERENCES `account-micro`.`permission` (`id`),
    CONSTRAINT `FK_permission_id_ACCOUNT_ROLE_id`
        FOREIGN KEY (`role_id`)
            REFERENCES `account-micro`.`account_role` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
