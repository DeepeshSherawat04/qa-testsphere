package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class DatabaseUtils {

    private static final String DB_URL =
            "jdbc:postgresql://localhost:5432/qa-testsphere";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres123";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    /**
     * Waits until a task appears in DB or timeout is reached
     */
    public static boolean waitForTask(String taskName, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);

        while (System.currentTimeMillis() < endTime) {
            if (isTaskPresent(taskName)) {
                return true;
            }
            try {
                Thread.sleep(500); // polling interval
            } catch (InterruptedException ignored) {
            }
        }
        return false;
    }

    /**
     * Deletes task from DB
     */
    public static void deleteTask(String taskName) {
    try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = con.createStatement()) {

        stmt.executeUpdate(
            "DELETE FROM tasks WHERE task_name = '" + taskName + "'"
        );

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    /**
     * Checks if task exists in DB
     */
    public static boolean isTaskPresent(String taskName) {
        String query = "SELECT COUNT(*) FROM tasks WHERE task_name = ?";

        try (
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setString(1, taskName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
