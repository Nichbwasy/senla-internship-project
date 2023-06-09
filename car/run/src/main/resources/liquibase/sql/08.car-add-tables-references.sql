ALTER TABLE car ADD FOREIGN KEY (registration_id) REFERENCES registration (id) ON DELETE CASCADE;

ALTER TABLE car ADD FOREIGN KEY (condition_id) REFERENCES condition (id) ON DELETE SET DEFAULT;

ALTER TABLE car ADD FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE SET DEFAULT;

ALTER TABLE registration ADD FOREIGN KEY (color_id) REFERENCES color (id) ON DELETE SET DEFAULT;

ALTER TABLE registration ADD FOREIGN KEY (type_id) REFERENCES type (id) ON DELETE SET DEFAULT;