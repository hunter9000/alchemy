USE alch;

DELETE FROM `resource_inventory` WHERE 1=1;
DELETE FROM `pipe` WHERE 1=1;
DELETE FROM `unit` WHERE 1=1;
DELETE FROM `grid` WHERE 1=1;

ALTER TABLE `unit` DROP COLUMN `output_type`;
ALTER TABLE `unit` DROP COLUMN `input_type`;

CREATE TABLE `unit_connection` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `unit` BIGINT(20) NOT NULL,
    `resource_type` VARCHAR(255) NOT NULL,
    `direction_type` VARCHAR(255) NOT NULL,
    `is_input` BOOLEAN NOT NULL,

    PRIMARY KEY (`id`),
    INDEX `FK_unit_connection__unit` (`unit`),
    CONSTRAINT `FK_unit_connection__unit` FOREIGN KEY (`unit`) REFERENCES `unit` (`id`)
) COLLATE='utf8_general_ci' ENGINE=InnoDB;


INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('4.sql', NOW());