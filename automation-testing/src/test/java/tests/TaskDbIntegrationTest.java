package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.DatabaseUtils;

public class TaskDbIntegrationTest extends BaseTest {

    @Test
    public void verifyTaskCreationAndDeletionInDatabase() throws InterruptedException {

        String taskName = "DB Integration Task";

        // STEP 1: Add task using UI
driver.findElement(By.id("taskInput")).sendKeys(taskName);
driver.findElement(By.id("addTaskBtn")).click();

// 🔥 ADD THIS BLOCK HERE 🔥
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//li[text()='" + taskName + "']")
));

// STEP 3: NOW verify DB
boolean taskExistsInDb = DatabaseUtils.waitForTask(taskName, 5);
Assert.assertTrue(taskExistsInDb, "Task was not found in DB after UI creation");


        // STEP 4: Cleanup — delete task at DB level
        DatabaseUtils.deleteTask(taskName);

        // STEP 5: Verify task is deleted from DB (eventual consistency)
        boolean exists = true;

        for (int i = 0; i < 5; i++) {
            exists = DatabaseUtils.isTaskPresent(taskName);
            if (!exists) {
                break; // task successfully deleted
            }
            Thread.sleep(1000); // wait 1 second before retry
        }

        Assert.assertFalse(
                exists,
                "Task was still present in DB after deletion"
        );
    }
}
