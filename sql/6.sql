USE alch;

ALTER TABLE `unit` ADD COLUMN `definition_type` VARCHAR(255) NOT NULL;

INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('6.sql', NOW());