CREATE TABLE IF NOT EXISTS bloodSupply
(
  bloodType CHAR(2) NOT NULL,
  numberOfUnits INT NOT NULL,
  maxUnits INT NOT NULL,
  minUnits INT NOT NULL,
  PRIMARY KEY (bloodType)
);

CREATE TABLE IF NOT EXISTS userAccount
(
  userId INT NOT NULL,
  role VARCHAR(20) NOT NULL,
  password VARCHAR(20),
  accActivated INT NOT NULL,
  permDeactivated INT NOT NULL,
  optOut INT NOT NULL,
  PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS donor
(
  donorId INT NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  OIB CHAR(11) NOT NULL,
  birthDate DATE NOT NULL,
  birthPlace VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  workPlace VARCHAR(100),
  privateContact VARCHAR(20) NOT NULL,
  workContact VARCHAR(20),
  email VARCHAR(50) NOT NULL,
  bloodType CHAR(2) NOT NULL,
  permRejectedReason VARCHAR(100),
  lastDonationPlace VARCHAR(100),
  donationCount INT NOT NULL,
  PRIMARY KEY (donorId),
  FOREIGN KEY (donorId) REFERENCES userAccount(userId),
  FOREIGN KEY (bloodType) REFERENCES bloodSupply(bloodType),
  UNIQUE (OIB)
);

CREATE TABLE IF NOT EXISTS bankWorker
(
  bankWorkerId INT NOT NULL,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  OIB CHAR NOT NULL,
  birthDate DATE NOT NULL,
  birthPlace VARCHAR(100) NOT NULL,
  address VARCHAR(100) NOT NULL,
  workPlace VARCHAR(100) NOT NULL,
  privateContact VARCHAR(20) NOT NULL,
  workContact VARCHAR(20) NOT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY (bankWorkerId),
  FOREIGN KEY (bankWorkerId) REFERENCES userAccount(userId),
  UNIQUE (OIB)
);

CREATE TABLE IF NOT EXISTS donationTry
(
  donationId INT NOT NULL,
  rejectedReason VARCHAR(100),
  bloodType CHAR(2) NOT NULL,
  donorId INT NOT NULL,
  bankWorkerId INT NOT NULL,
  PRIMARY KEY (donationId),
  FOREIGN KEY (bloodType) REFERENCES bloodSupply(bloodType),
  FOREIGN KEY (donorId) REFERENCES donor(donorId),
  FOREIGN KEY (bankWorkerId) REFERENCES bankWorker(bankWorkerId)
);

--INSERT INTO userAccount (userId, role, password, accActivated, permDeactivated, optOut)
--    VALUES (10000000, ADMIN, 'admin', 100000000, 0, 0);