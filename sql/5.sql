USE alch;


ALTER TABLE `unit` CHANGE COLUMN `x_pos` `col` INTEGER(10) DEFAULT NULL;
ALTER TABLE `unit` CHANGE COLUMN `y_pos` `row` INTEGER(10) DEFAULT NULL;

ALTER TABLE `pipe` CHANGE COLUMN `x_pos` `col` INTEGER(10) DEFAULT NULL;
ALTER TABLE `pipe` CHANGE COLUMN `y_pos` `row` INTEGER(10) DEFAULT NULL;


INSERT INTO `sql_files` (`sql_file_name`, `run_date`) VALUES ('5.sql', NOW());