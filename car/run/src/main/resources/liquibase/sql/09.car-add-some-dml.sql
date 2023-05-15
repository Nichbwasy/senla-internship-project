INSERT INTO car.color (name) VALUES ('BLACK');
INSERT INTO car.color (name) VALUES ('WHITE');
INSERT INTO car.color (name) VALUES ('RED');
INSERT INTO car.color (name) VALUES ('GREEN');
INSERT INTO car.color (name) VALUES ('BLUE');

INSERT INTO car.type (name) VALUES ('PASSENGER');
INSERT INTO car.type (name) VALUES ('TRUCK');

INSERT INTO car.condition (name) VALUES ('NEW');
INSERT INTO car.condition (name) VALUES ('USED');
INSERT INTO car.condition (name) VALUES ('CRUSHED');

INSERT INTO car.status (name) VALUES ('AVAILABLE');
INSERT INTO car.status (name) VALUES ('IN_USE');
INSERT INTO car.status (name) VALUES ('NOT_AVAILABLE');

INSERT INTO car.registration ("number", model, releaseYear, color_id, body_number, "type_id", allowed_max_mass, unladen_mass)
	VALUES ('1234 KJ-1', 'AUDI 1234', 2015, 1, '62E66484DWC7455', 1, 1600, 1000);
INSERT INTO car.registration ("number", model, releaseYear, color_id, body_number, "type_id", allowed_max_mass, unladen_mass)
	VALUES ('7456 TJ-3', 'BMW 544', 2019, 2, 'DEF54862D412W55', 2, 2400, 1400);
INSERT INTO car.registration ("number", model, releaseYear, color_id, body_number, "type_id", allowed_max_mass, unladen_mass)
	VALUES ('8456 SJ-8', 'KIA 7004', 2010, 4, '147S63QFF51FGBF', 1, 1400, 1000);
INSERT INTO car.registration ("number", model, releaseYear, color_id, body_number, "type_id", allowed_max_mass, unladen_mass)
    VALUES ('9846 DR-4', 'BMW 551', 2020, 3, '147WMKWF51FGBF', 2, 1800, 1100);
INSERT INTO car.registration ("number", model, releaseYear, color_id, body_number, "type_id", allowed_max_mass, unladen_mass)
    VALUES ('5471 RG-3', 'AUDI 104', 2014, 1, '651656SFF51FGBF', 1, 2000, 1250);

INSERT INTO car.car (description, mileage, registration_id, condition_id, status_id)
	VALUES ('Some description 1...', 120000, 1, 2, 1);
INSERT INTO car.car (description, mileage, registration_id, condition_id, status_id)
	VALUES ('Some description 2...', 85000, 2, 1, 2);
INSERT INTO car.car (description, mileage, registration_id, condition_id, status_id)
	VALUES ('Some description 3...', 210000, 3, 2, 1);
INSERT INTO car.car (description, mileage, registration_id, condition_id, status_id)
    VALUES ('Some description 4...', 58000, 4, 1, 2);
INSERT INTO car.car (description, mileage, registration_id, condition_id, status_id)
    VALUES ('Some description 5...', 320000, 5, 3, 3);