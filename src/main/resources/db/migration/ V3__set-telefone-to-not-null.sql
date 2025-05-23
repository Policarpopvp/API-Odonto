-- V3__set-telefone-to-not-null.sql

-- Atualiza os registros que têm telefone como NULL
UPDATE dentista SET telefone = '00000000000' WHERE telefone IS NULL;

-- Agora altera a coluna para ser NOT NULL
ALTER TABLE dentista ALTER COLUMN telefone SET NOT NULL;
