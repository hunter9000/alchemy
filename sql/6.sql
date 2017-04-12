USE alch;

ALTER TABLE `grid` ADD COLUMN `last_tick` TIMESTAMP NULL;

-- remove the unitconnection table and the unit fields that are defined in the unitdefinitiontype

INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('6.sql', NOW());