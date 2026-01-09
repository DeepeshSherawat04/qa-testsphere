-- Pre-condition setup for database test execution
-- Ensures test data exists before UI/automation testing

INSERT INTO users (email, name)
SELECT 'testuser@gmail.com', 'Test User'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email='testuser@gmail.com'
);

INSERT INTO tasks (task_name, user_id)
SELECT 'New Task', user_id
FROM users
WHERE email='testuser@gmail.com'
AND NOT EXISTS (
    SELECT 1 FROM tasks WHERE task_name='New Task'
);

-- NOTE:
-- Multiple rows indicate repeated test runs without cleanup.
-- Test data setup is designed to be idempotent to avoid duplication.
