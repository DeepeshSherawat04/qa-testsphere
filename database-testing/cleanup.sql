-- Post-condition cleanup
-- Ensures database is reset after test execution

DELETE FROM tasks WHERE task_name='New Task';
DELETE FROM users WHERE email='testuser@gmail.com';
