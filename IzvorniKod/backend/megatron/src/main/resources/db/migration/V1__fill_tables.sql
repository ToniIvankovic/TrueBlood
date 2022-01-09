CREATE TABLE IF NOT EXISTS blood_supply
(
  blood_type CHAR(3) NOT NULL,
  number_of_units INT NOT NULL,
  max_units INT NOT NULL,
  min_units INT NOT NULL,
  PRIMARY KEY (blood_type)
);

CREATE TABLE IF NOT EXISTS user_account
(
  user_id BIGINT NOT NULL,
  user_role VARCHAR(20) NOT NULL,
  password VARCHAR(128) NOT NULL,
  acc_activated INT NOT NULL,
  perm_deactivated INT NOT NULL,
  opt_out INT NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS donor
(
  donor_id BIGINT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  oib CHAR(11) NOT NULL,
  gender CHAR(1) NOT NULL,
  birth_date DATE NOT NULL,
  birth_place VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  work_place VARCHAR(100),
  private_contact VARCHAR(20) NOT NULL,
  work_contact VARCHAR(20),
  email VARCHAR(50) NOT NULL,
  blood_type CHAR(3),
  perm_rejected_reason VARCHAR(100),
  PRIMARY KEY (donor_id),
  FOREIGN KEY (donor_id) REFERENCES user_account(user_id),
  FOREIGN KEY (blood_type) REFERENCES blood_supply(blood_type),
  UNIQUE (oib)
);

CREATE TABLE IF NOT EXISTS bank_worker
(
  bank_worker_id BIGINT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  oib CHAR(11) NOT NULL,
  birth_date DATE NOT NULL,
  birth_place VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  work_place VARCHAR(100) NOT NULL,
  private_contact VARCHAR(20) NOT NULL,
  work_contact VARCHAR(20) NOT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY (bank_worker_id),
  FOREIGN KEY (bank_worker_id) REFERENCES user_account(user_id),
  UNIQUE (oib)
);

CREATE TABLE IF NOT EXISTS donation_try
(
  donation_id BIGINT NOT NULL,
  reject_reason VARCHAR(100),
  donation_date DATE NOT NULL,
  donation_place VARCHAR(100) NOT NULL,
  donor_id BIGINT NOT NULL,
  bank_worker_id BIGINT NOT NULL,
  PRIMARY KEY (donation_id),
  FOREIGN KEY (donor_id) REFERENCES donor(donor_id),
  FOREIGN KEY (bank_worker_id) REFERENCES bank_worker(bank_worker_id)
);

CREATE TABLE IF NOT EXISTS secure_token
(
 token_id BIGINT NOT NULL,
 token VARCHAR(100) NOT NULL,
 time_stamp TIMESTAMP NOT NULL,
 expire_at DATE NOT NULL,
 user_id BIGINT,
 PRIMARY KEY (token_id),
 FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

CREATE SEQUENCE IF NOT EXISTS USER_SEQ START WITH 1000001;
CREATE SEQUENCE IF NOT EXISTS DONATION_SEQ;
CREATE SEQUENCE IF NOT EXISTS TOKEN_SEQ START WITH 81728;

INSERT INTO user_account (user_id, user_role, password, acc_activated, perm_deactivated, opt_out)
    VALUES (1000000, 'ADMIN', '$2a$10$jBBLYlPInYB.SvgIzenJge6TQLX3vAmBJA.j2C08rQCMeVJpBBvmC', 1, 0, 0);   -- password is adminpass

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('A+', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('B+', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('AB+', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('0+', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('A-', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('B-', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('AB-', 0, 1000000, 0);

INSERT INTO blood_supply (blood_type, number_of_units, max_units, min_units)
    VALUES ('0-', 0, 1000000, 0);