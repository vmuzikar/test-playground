package org.test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
public class TestPage {
    @FindBy(className = "gsfi")
    private WebElement inputField;

    public void writeSomething() {
        inputField.sendKeys("Something to search");
    }
}
