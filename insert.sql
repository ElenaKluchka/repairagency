INSERT INTO `repair_agency`.`customer` (`name`, `email`, `password`,phone) VALUES ('user', 'user@gmail.com', 'user123','111');
INSERT INTO `repair_agency`.`customer` (`name`, `email`, `password`,phone) VALUES ('asd', 'asd@gmail.com', 'asd123','333');

INSERT INTO `repair_agency`.`orders` (`customer_id`, `description`, `name`) VALUES ('2', 'asdsdfdf', 'refrigerator');


INSERT INTO `repair_agency`.`employee` (`name`, `email`, `password`, `role`,phone) VALUES ('Petr Petrov', 'petr@gmail.com', 'petr123', 'MASTER','123');
INSERT INTO `repair_agency`.`employee` (`name`, `email`, `password`, `role`,phone) VALUES ('Ivan Ivanov', 'm@gmail.com', 'ivan123', 'MASTER','222');
INSERT INTO `repair_agency`.`employee` (`id`, `name`, `email`, `password`, `role`, `phone`) VALUES ('5', 'qwe', 'qwe@gmail.com', 'qwe123', 'MANAGER', '233');

INSERT INTO `repair_agency`.`orders` (`customer_id`, `description`, `name`,managment_state) VALUES ('1', 'xxx', 'xxx','WAIT_FOR_PAYMENT');
INSERT INTO `repair_agency`.`orders` (`customer_id`, `description`, `name`,work_state,managment_state) VALUES ('2', 'y', 'y','IN_WORK','WAIT_FOR_PAYMENT');
INSERT INTO `repair_agency`.`orders` (`customer_id`, `description`, `name`,managment_state) VALUES ('2', '1', '1','PAYED');

INSERT INTO `repair_agency`.`order_master` (`master_id`, `order_id`) VALUES ('1', '1');
INSERT INTO `repair_agency`.`order_master` (`master_id`, `order_id`) VALUES ('2', '2');
INSERT INTO `repair_agency`.`order_master` (`master_id`, `order_id`) VALUES ('2', '3');