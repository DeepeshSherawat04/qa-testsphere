package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.DatabaseUtils;

public class TaskDbIntegrationTest extends BaseTest {

    @Test
    public void verifyTaskCreationAndDeletionInDatabase() {

        String taskName = "DB Integration Task";

        // STEP 1: Add task using UI
        driver.findElement(By.id("taskInput")).sendKeys(taskName);
        driver.findElement(By.id("addTaskBtn")).click();

        // STEP 2: Handle alert safely (if present)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            // Alert not shown — safe to continue
        } catch (Exception e) {
            // Timeout waiting for alert — safe to continue
        }

        // STEP 3: Verify task exists in DB
        boolean taskExistsInDb = DatabaseUtils.waitForTask(taskName, 5);
        Assert.assertTrue(
                taskExistsInDb,
                "❌ Task was not found in DB after UI creation"
        );

        // STEP 4: Cleanup (DB-level delete)
        DatabaseUtils.deleteTask(taskName);

        // STEP 5: Verify task is deleted from DB
        boolean taskStillExists = DatabaseUtils.isTaskPresent(taskName);
        Assert.assertFalse(
                taskStillExists,
                "❌ Task still exists in DB after deletion"
        );
    }
}
