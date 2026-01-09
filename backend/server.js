const express = require("express");
const cors = require("cors");
const { Pool } = require("pg");

const app = express();
app.use(cors());
app.use(express.json());

const pool = new Pool({
  user: "postgres",
  host: "localhost",
  database: "qa-testsphere",
  password: "postgres123",
  port: 5432,
});

/**
 * POST /api/tasks
 * Body: { taskName }
 * Assumes test user already exists in DB
 */
app.post("/api/tasks", async (req, res) => {
  const { taskName } = req.body;

  try {
    await pool.query(
      `INSERT INTO tasks (task_name, user_id)
       SELECT $1, user_id FROM users WHERE email = 'testuser@gmail.com'`,
      [taskName]
    );

    // ✅ SUCCESS response used by UI + Selenium
    res.status(200).json({
      success: true,
      taskName: taskName
    });

  } catch (err) {
    console.error("DB error:", err);
    res.status(500).json({
      success: false,
      message: "Backend error while saving task"
    });
  }
});


/**
 * DELETE /api/tasks/:taskName
 */
app.delete("/api/tasks/:taskName", async (req, res) => {
  const { taskName } = req.params;

  try {
    await pool.query(
      "DELETE FROM tasks WHERE task_name = $1",
      [taskName]
    );

    res.status(200).json({
      success: true,
      message: "Task deleted",
      taskName: taskName,
    });
  } catch (err) {
    console.error("DB ERROR:", err);
    res.status(500).json({ success: false, message: "Database error" });
  }
});

app.listen(3000, () => {
  console.log("✅ Backend running on http://localhost:3000");
});
