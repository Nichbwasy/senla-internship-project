INSERT INTO authorizations.role (name) VALUES ('USER_ROLE');
INSERT INTO authorizations.role (name) VALUES ('ADMIN_ROLE');

INSERT INTO authorizations.authority (name) VALUES ('AUTHORIZATION_ROLES_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('AUTHORIZATION_AUTHORITIES_ACCESS');

INSERT INTO authorizations.authority (name) VALUES ('CARS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('CARS_REGISTRATIONS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('CARS_COLORS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('CARS_TYPES_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('CARS_STATUSES_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('CARS_CONDITIONS_ACCESS');

INSERT INTO authorizations.authority (name) VALUES ('RENTAL_BLACKLIST_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('RENTAL_CAR_REFUNDS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('RENTAL_REFUND_COMPENSATIONS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('RENTAL_REQUEST_REJECTION_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('RENTAL_REQUESTS_ACCESS');
INSERT INTO authorizations.authority (name) VALUES ('RENTAL_REQUESTS_STATUSES_ACCESS');

INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (1, 11);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (1, 13);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (1, 14);

INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 1);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 2);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 3);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 4);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 5);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 6);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 7);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 8);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 9);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 10);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 11);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 12);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 13);
INSERT INTO authorizations.role_authority (role_id, authority_id) VALUES (2, 14);
