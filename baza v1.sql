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
  password VARCHAR(128),
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
  rejected_reason VARCHAR(100),
  blood_type CHAR(3) NOT NULL,
  donor_id BIGINT NOT NULL,
  bank_worker_id BIGINT NOT NULL,
  PRIMARY KEY (donation_id),
  FOREIGN KEY (blood_type) REFERENCES blood_supply(blood_type),
  FOREIGN KEY (donor_id) REFERENCES donor(donor_id),
  FOREIGN KEY (bank_worker_id) REFERENCES bank_worker(bank_worker_id)
);