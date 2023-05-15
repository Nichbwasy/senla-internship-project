ALTER TABLE car.car ADD FOREIGN KEY (registration_id) REFERENCES car.registration (id) ON DELETE CASCADE;

ALTER TABLE car.car ADD FOREIGN KEY (condition_id) REFERENCES car.condition (id) ON DELETE SET DEFAULT;

ALTER TABLE car.car ADD FOREIGN KEY (status_id) REFERENCES car.status (id) ON DELETE SET DEFAULT;

ALTER TABLE car.registration ADD FOREIGN KEY (color_id) REFERENCES car.color (id) ON DELETE SET DEFAULT;

ALTER TABLE car.registration ADD FOREIGN KEY (type_id) REFERENCES car.type (id) ON DELETE SET DEFAULT;