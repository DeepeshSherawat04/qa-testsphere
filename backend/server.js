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

  if (!taskName) {
    return res.status(400).json({ success: false, message: "Task name required" });
  }

  try {
    // ✅ Insert task ONLY after user is confirmed
    const result = await pool.query(
      `
      INSERT INTO tasks (task_name, user_id)
      SELECT $1, user_id
      FROM users
      WHERE email = 'testuser@gmail.com'
      RETURNING task_id
      `,
      [taskName]
    );

    if (result.rowCount === 0) {
      return res.status(404).json({
        success: false,
        message: "User not found for task creation",
      });
    }

    // ✅ IMPORTANT: Return success ONLY after DB commit
    res.status(200).json({
      success: true,
      taskName: taskName,
      taskId: result.rows[0].task_id,
    });

  } catch (err) {
    console.error("DB ERROR:", err);
    res.status(500).json({ success: false, message: "Database error" });
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
