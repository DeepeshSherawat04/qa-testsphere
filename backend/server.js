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
 * Body: { taskName, email }
 */
app.post("/api/tasks", async (req, res) => {
  const { taskName, email } = req.body;

  try {
    // 1️⃣ Find user
    const userResult = await pool.query(
      "SELECT user_id FROM users WHERE email = $1",
      [email]
    );

    if (userResult.rows.length === 0) {
      return res.status(404).json({ message: "User not found" });
    }

    const userId = userResult.rows[0].user_id;

    // 2️⃣ Insert task
    await pool.query(
  `INSERT INTO tasks (task_name, user_id)
   SELECT $1, user_id FROM users WHERE email='testuser@gmail.com'`,
  [taskName]
);

    res.status(201).json({ message: "Task created" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "DB error" });
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

    res.json({ message: "Task deleted" });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: "DB error" });
  }
});

app.listen(3000, () => {
  console.log("Backend running on http://localhost:3000");
});
