-- =========================================================
-- DATABASE TEST CASES
-- Project: QA-TestSphere
-- Type: Assertion-style SQL validation
-- Database: PostgreSQL
-- =========================================================

-- =========================================================
-- TC_DB_01: Verify user exists
-- Expected Result: Exactly 1 row
-- =========================================================
SELECT
  CASE
    WHEN COUNT(*) = 1 THEN 'PASS: User exists'
    ELSE 'FAIL: User does not exist or duplicate users found'
  END AS tc_db_01_result
FROM users
WHERE email = 'testuser@gmail.com';

-- =========================================================
-- TC_DB_02: Verify task creation
-- Expected Result: At least 1 row
-- =========================================================
SELECT
  CASE
    WHEN COUNT(*) >= 1 THEN 'PASS: Task created successfully'
    ELSE 'FAIL: Task not created'
  END AS tc_db_02_result
FROM tasks
WHERE task_name = 'New Task';

-- =========================================================
-- TC_DB_03: Verify task deletion
-- Expected Result: 0 rows
-- =========================================================
SELECT
  CASE
    WHEN COUNT(*) = 0 THEN 'PASS: Task deleted successfully'
    ELSE 'FAIL: Task still exists'
  END AS tc_db_03_result
FROM tasks
WHERE task_id = 101;

-- =========================================================
-- END OF TEST CASES
-- =========================================================
