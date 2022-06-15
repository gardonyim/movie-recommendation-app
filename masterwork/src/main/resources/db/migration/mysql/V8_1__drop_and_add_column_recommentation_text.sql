ALTER TABLE recommendations DROP COLUMN recommendation_text;

ALTER TABLE recommendations ADD COLUMN recommendation_text VARCHAR(500);