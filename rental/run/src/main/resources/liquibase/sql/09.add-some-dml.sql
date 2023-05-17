INSERT INTO rental.requeststatus (name) VALUES ('CREATED');
INSERT INTO rental.requeststatus (name) VALUES ('IN_PROCESS');
INSERT INTO rental.requeststatus (name) VALUES ('ACCEPTED');
INSERT INTO rental.requeststatus (name) VALUES ('DENIED');
INSERT INTO rental.requeststatus (name) VALUES ('CANCELLED');
INSERT INTO rental.requeststatus (name) VALUES ('CLOSED');

INSERT INTO rental.request (user_id, car_id, start_time, end_time, requestrejection_id, requeststatus_id)
	VALUES (1, 1, '01-01-2023', '03-01-2023', null, 1);
INSERT INTO rental.request (user_id, car_id, start_time, end_time, requestrejection_id, requeststatus_id)
	VALUES (1, 2, '02-01-2023', '08-01-2023', null, 2);
INSERT INTO rental.request (user_id, car_id, start_time, end_time, requestrejection_id, requeststatus_id)
	VALUES (1, 3, '03-01-2023', '07-01-2023', null, 3);
	
INSERT INTO rental.carrefund (user_id, car_id, start_using_time, end_using_time, refund_time, request_id, refundcompensation_id)
	VALUES (1, 1, '01-01-2023', '02-01-2023', '04-01-2023', 1, null);
INSERT INTO rental.carrefund (user_id, car_id, start_using_time, end_using_time, refund_time, request_id, refundcompensation_id)
	VALUES (1, 3, '02-01-2023', '03-01-2023', '04-01-2023', 2, null);