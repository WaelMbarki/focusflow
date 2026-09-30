ALTER TABLE focus_sessions ADD COLUMN last_resumed_at TIMESTAMP;

UPDATE focus_sessions SET last_resumed_at = start_time WHERE status = 'ACTIVE';