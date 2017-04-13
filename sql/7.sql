USE alch;

ALTER TABLE `unit` DROP COLUMN `type`;
ALTER TABLE `unit` DROP COLUMN `cost_amount`;
ALTER TABLE `unit` DROP COLUMN `cost_resource_type`;

ALTER TABLE `unit_connection` DROP FOREIGN KEY `FK_unit_connection__unit`;
DROP TABLE `unit_connection`;

INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('7.sql', NOW());