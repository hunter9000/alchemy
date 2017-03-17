USE alch;

DROP TABLE IF EXISTS `resource_inventory`;
DROP TABLE IF EXISTS `pipe`;
DROP TABLE IF EXISTS `unit`;
DROP TABLE IF EXISTS `grid`;

CREATE TABLE `grid` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `owner` BIGINT(20) NOT NULL,
    `width` INTEGER(10) NOT NULL,
    `height` INTEGER(10) NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE INDEX `FK_grid__user` (`owner`),
    CONSTRAINT `FK_grid__user` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `unit` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `grid` BIGINT(20) NOT NULL,
    `x_pos` INTEGER(10) DEFAULT NULL,
    `y_pos` INTEGER(10) DEFAULT NULL,
    `type` VARCHAR(255) NOT NULL,
    `output_type` VARCHAR(255) DEFAULT NULL,
    `input_type` VARCHAR(255) DEFAULT NULL,
    `purchased` BOOLEAN NOT NULL DEFAULT FALSE,
    `cost_amount` INTEGER(10) NOT NULL,
    `cost_resource_type` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_unit__grid` (`grid`),
    CONSTRAINT `FK_unit__grid` FOREIGN KEY (`grid`) REFERENCES `grid` (`id`)
) COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `pipe` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `grid` BIGINT(20) NOT NULL,
    `x_pos` INTEGER(10) DEFAULT NULL,
    `y_pos` INTEGER(10) DEFAULT NULL,
    `in_direction` VARCHAR(255) NOT NULL,
    `out_direction` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_pipe__grid` (`grid`),
    CONSTRAINT `FK_pipe__grid` FOREIGN KEY (`grid`) REFERENCES `grid` (`id`)
) COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `resource_inventory` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `grid` BIGINT(20) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `amount` INTEGER(10) DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK_resource_inventory__grid` (`grid`),
    CONSTRAINT `FK_resource_inventory__grid` FOREIGN KEY (`grid`) REFERENCES `grid` (`id`)
) COLLATE='utf8_general_ci' ENGINE=InnoDB;


INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('3.sql', NOW());