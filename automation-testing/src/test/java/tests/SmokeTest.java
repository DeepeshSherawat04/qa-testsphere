package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class SmokeTest extends BaseTest {

    @Test
public void verifyPageLoads() {
    Assert.assertTrue(
        driver.findElement(By.id("taskInput")).isDisplayed(),
        "Page did not load correctly"
    );
}
}
