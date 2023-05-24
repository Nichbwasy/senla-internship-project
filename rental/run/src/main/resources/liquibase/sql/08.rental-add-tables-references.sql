ALTER TABLE rental.request ADD FOREIGN KEY (requestrejection_id) REFERENCES rental.requestrejection (id) ON DELETE SET DEFAULT;

ALTER TABLE rental.request ADD FOREIGN KEY (requeststatus_id) REFERENCES rental.requeststatus (id) ON DELETE SET DEFAULT;

ALTER TABLE rental.carrefund ADD FOREIGN KEY (refundcompensation_id) REFERENCES rental.refundcompensation (id) ON DELETE SET DEFAULT;

ALTER TABLE rental.carrefund ADD FOREIGN KEY (request_id) REFERENCES rental.request (id) ON DELETE SET DEFAULT;

