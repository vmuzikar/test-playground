package org.test;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * @author Vaclav Muzikar <vmuzikar@redhat.com>
 */
@RunWith(Arquillian.class)
public class ScreenTest {
    @Drone
    private WebDriver driver;

    @Page
    private TestPage testPage;

    @Test
    public void screenshot() throws Exception {
        driver.manage().window().maximize();
        driver.navigate().to("http://www.google.com");
        testPage.writeSomething();
        Thread.sleep(5000);
    }
}
