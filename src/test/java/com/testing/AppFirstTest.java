package com.testing;

import com.capabilityMain;
import com.sun.glass.events.KeyEvent;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeoutException;

public class AppFirstTest extends capabilityMain {

    public  static AndroidDriver driver ;

    public void killAllNodes() throws InterruptedException, IOException {
        //taskkill /F /IM node.exe
        Runtime.getRuntime().exec("taskkill /F /IM node.exe");
        Thread.sleep(3000);

    }


    @BeforeTest
    public void  BeforeClass() throws IOException, InterruptedException {
        killAllNodes();
        int port =4723;
        if(!checkIfServerIsRunning(port)) {
            service =  startdefaultServer(port);

            System.out.println("Appium App Launch Successful on Port: "+ port);
            Thread.sleep(15000);
        }
        else {
            System.out.println("Appium Server is already Running on port : "+ port );
            stopServer();
        }

        driver = capabilities();
        Thread.sleep(6000);
    }


    @AfterTest
    public void AfterClass() throws InterruptedException {
        this.driver.pressKeyCode(AndroidKeyCode.HOME);
        Thread.sleep(15000);
        System.out.println("Killing Emulator Service......");
        stopEmulator();
        Thread.sleep(15000);
        System.out.println("Killing Appium Service......");
        stopServer();


    }

    @Test
    public void Test1() throws InterruptedException {
        Thread.sleep(5000);
//        driver.pressKeyCode(AndroidKeyCode.HOME);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();
        Thread.sleep(5000);
        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
        Thread.sleep(5000);
        driver.findElementById("android:id/checkbox").click();
        Thread.sleep(5000);
        driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
        Thread.sleep(5000);
        driver.findElementByClassName("android.widget.EditText").sendKeys("Hello World");

        driver.findElementById("android:id/button1").click();
        System.out.println("#######  Test OK..#######");


    }

    @Test
    public void Test2() throws InterruptedException {
        Thread.sleep(5000);
//        driver.pressKeyCode(AndroidKeyCode.HOME);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Preference']")).click();
        Thread.sleep(5000);
        driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
        Thread.sleep(5000);
        driver.findElementById("android:id/checkbox").click();
        Thread.sleep(5000);
        driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
        Thread.sleep(5000);
        driver.findElementByClassName("android.widget.EditText").sendKeys("Hello World");

        driver.findElementById("android:id/button1").click();
        System.out.println("#######  Test OK..#######");


    }
}
