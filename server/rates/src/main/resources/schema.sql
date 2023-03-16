DROP TABLE IF EXISTS counter;
CREATE TABLE IF NOT EXISTS counter (
  id BIGSERIAL PRIMARY KEY,
  currency VARCHAR NOT NULL,
  date DATE NOT NULL,
  counter BIGINT NOT NULL DEFAULT 0,
  UNIQUE (currency, date)
);

DROP TABLE IF EXISTS rates;
CREATE TABLE IF NOT EXISTS rates (
  id BIGSERIAL PRIMARY KEY,
  base VARCHAR NOT NULL,
  currency VARCHAR NOT NULL,
  date DATE NOT NULL,
  rate NUMERIC(25, 15) NOT NULL
);
CREATE INDEX base_currency ON rates (base, currency);
CREATE INDEX base_currency_date ON rates (base, currency, date);

DROP TABLE IF EXISTS spread;
CREATE TABLE IF NOT EXISTS spread (
  currency VARCHAR PRIMARY KEY,
  spread NUMERIC(9, 8) NOT NULL
);