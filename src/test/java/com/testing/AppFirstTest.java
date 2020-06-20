package com.testing;

import com.capabilityMain;
import com.sun.glass.events.KeyEvent;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import okio.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AppFirstTest extends capabilityMain {

    public void killAllNodes() throws InterruptedException, IOException {
        //taskkill /F /IM node.exe
        Runtime.getRuntime().exec("taskkill /F /IM node.exe");
        Thread.sleep(3000);
    }

    @BeforeTest
    public void beforeTest() throws IOException, InterruptedException {
        killAllNodes();
        int port = 4723;
        if (!checkIfServerIsRunning(port)) {
            service = startServer(port);

            System.out.println("########## Appium Server Launch Successful on Port: " + port + "  ##########");
            Thread.sleep(15000);
        } else {
            System.out.println("########## Appium Server is already Running on port: " + port + "  ##########");
            stopServer();
        }
        driver = capabilities();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        Thread.sleep(6000);
    }

    @AfterTest
    public void afterTest() throws InterruptedException {
        this.driver.pressKeyCode(AndroidKeyCode.HOME);
        Thread.sleep(15000);
        System.out.println("########## Killing Emulator Service ##########");
        stopEmulator();
        Thread.sleep(15000);
        System.out.println("########## Killing Appium Service ##########");
        stopServer();
    }

    @AfterMethod
    public void afterMethod() throws InterruptedException {
        driver.resetApp();
        System.out.println("##########  Test Complete. ##########");
        Thread.sleep(3000);
    }


    @Test
    public void Test1()  {
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.pressKeyCode(AndroidKeyCode.HOME);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();

        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();

        driver.findElementById("android:id/checkbox").click();

        driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();

        driver.findElementByClassName("android.widget.EditText").sendKeys("Hello World");
        driver.findElementById("android:id/button1").click();
    }

     @Test
    public void Test2() {

        //driver.pressKeyCode(AndroidKeyCode.HOME);
       // driver.launchApp();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();

        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();

        driver.findElementById("android:id/checkbox").click();

        driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();

        driver.findElementByClassName("android.widget.EditText").sendKeys("Hello World");

        driver.findElementById("android:id/button1").click();
        driver.resetApp();
    }

     @Test
    public void Test3() {

       // driver.launchApp();

        driver.findElementByXPath("//android.widget.TextView[@text='Accessibility']").click();
        //driver.findElement(By.xpath("//android.widget.TextView[@text='Accessibility']")).click();

        driver.findElement(By.xpath("//android.widget.TextView[@text='Accessibility Node Provider']")).click();

        driver.navigate().back();

        driver.findElement(By.xpath("//android.widget.TextView[@text='Accessibility Node Querying']")).click();

         driver.pressKeyCode(AndroidKeyCode.BACK);

        driver.findElement(By.xpath("//android.widget.TextView[@text='Accessibility Service']")).click();

         driver.pressKeyCode(AndroidKeyCode.BACK);

        driver.findElement(By.xpath("//android.widget.TextView[@text='Custom View']")).click();

    }

     @Test
    public void Test4() {

        driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        System.out.println(driver.findElementsByAndroidUIAutomator("new UiSelector().clickable(true)").size());
        System.out.println(driver.findElementsByAndroidUIAutomator("new UiSelector().clickable(false)").size());
    }

    @Test
    public void Test5() {

        TouchAction touch = new TouchAction(driver);


        driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
        WebElement expandList = driver.findElementByXPath("//android.widget.TextView[@text='Expandable Lists']");
        touch.tap(tapOptions().withElement(element(expandList))).perform();

        driver.findElementByXPath("//android.widget.TextView[@text='1. Custom Adapter']").click();

        //### LONG PRESS ACTION IN MOBILE TOUCH/TAP ACTIONS   ##

        WebElement pN = driver.findElementByXPath("//android.widget.TextView[@text='People Names']");

        touch.longPress(longPressOptions().withElement(element(pN)).withDuration(ofSeconds(2))).release().perform();

        System.out.println(driver.findElementByXPath("//android.widget.TextView[@text='Sample menu']").isDisplayed());
    }


    @Test
    public void swipeTest() {
        driver.findElementByXPath("//android.widget.TextView[@text='Views']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='Date Widgets']").click();
        driver.findElementByXPath("//android.widget.TextView[@text='2. Inline']").click();
        driver.findElementByXPath("//*[@content-desc='9']").click();

        TouchAction touch = new TouchAction(driver);
        WebElement source = driver.findElement(By.xpath("//*[@content-desc='15']"));
        WebElement destination = driver.findElement(By.xpath("//*[@content-desc='45']"));
        touch.longPress(longPressOptions().withElement(element(source)).withDuration(ofSeconds(2))).moveTo(element(destination)).release().perform();
    }


    @Test
    public void scrollingTest() {
        driver.findElement(By.xpath("//*[@text='Views']")).click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Tabs\"));");
    }

    @Test
    public void dragAndDropTest(){
        driver.findElement(By.xpath("//*[@text='Views']")).click();
        driver.findElement(By.xpath("//*[@text='Drag and Drop']")).click();

        WebElement source = (WebElement) driver.findElements(By.className("android.view.View")).get(0);
        WebElement dest1 = (WebElement) driver.findElements(By.className("android.view.View")).get(1);
        WebElement dest2 = (WebElement) driver.findElements(By.className("android.view.View")).get(2);
      //  WebElement dest3 = (WebElement) driver.findElements(By.className("android.view.View")).get(3);
        TouchAction touch = new TouchAction(driver);
        touch.longPress(longPressOptions().withElement(element(source))).moveTo(element(dest1)).release().perform();
        touch.longPress(element(source)).moveTo(element(dest1)).release().perform();
        touch.longPress(element(source)).moveTo(element(dest2)).release().perform();
        WebElement dest3 = (WebElement) driver.findElements(By.className("android.view.View")).get(3);
        touch.longPress(element(source)).moveTo(element(dest3)).release().perform();
    }
}