package com;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class capabilityMain  {

    public static AppiumDriverLocalService service;
    public static AppiumServiceBuilder builder;
    public static DesiredCapabilities cap;
    public static AndroidDriver driver;


    public AppiumDriverLocalService startdefaultServer(int port) throws InterruptedException {
        if (!checkIfServerIsRunning(port)) {
            service = AppiumDriverLocalService.buildDefaultService();
            service.start();
            System.out.println("##########  Appium Service Launch in progress on Port: "+ port + "  ##########");
            Thread.sleep(15000);
        }
        return service;
    }

    public AppiumDriverLocalService startServer(int port){

        //Set Capabilities
        cap = new DesiredCapabilities();
        cap.setCapability("automationName","Appium");
        cap.setCapability("noReset","false");
        cap.setCapability("platformName","Android");
        cap.setCapability("platformVersion","9.0");
        cap.setCapability("deviceName","host5556");

        //Build the Appium service
        builder = new AppiumServiceBuilder();
        builder.withIPAddress("0.0.0.0");
        builder.usingPort(port);
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

      //Start the Server with builder config
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        return service;
    }
    public void stopServer(){
        service.isRunning();
        service.stop();
    }

    public boolean checkIfServerIsRunning(int port){
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try{
            serverSocket=new ServerSocket(port);
            serverSocket.close();
        }
        catch (IOException e){
            isServerRunning = true;
        } finally {
            serverSocket=null;
        }

        return isServerRunning;
    }

    public static AndroidDriver capabilities() throws MalformedURLException {
      //  AndroidDriver<AndroidElement> driver;
        File appDir = new File("src");
        File app = new File(appDir, "ApiDemos-debug.apk");

        DesiredCapabilities cap = new DesiredCapabilities();
        launchEmulator("host5556");
//        cap.setCapability("avd","host5556");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME,"host5556");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,60);
        cap.setCapability(MobileCapabilityType.APP, app.getAbsoluteFile());
//        cap.setCapability("automationName","Appium");
//        cap.setCapability("deviceName","pixel_2");

        //Build the Appium service
//        builder = new AppiumServiceBuilder();
//        builder.withIPAddress("0.0.0.0");
//        builder.usingPort(4723);
//        builder.withCapabilities(cap);
//        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
//        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
//
//        //Start the Server with builder
//        service = AppiumDriverLocalService.buildService(builder);
//        service.start();
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static void launchEmulator(String namedAVD){
        String sdkPath = "C:/Users/flatWhite/AppData/Local/Android/Sdk";
        String emulatorPath = sdkPath+"/emulator"+File.separator+"emulator";
        String adbServerStart = "adb" + " "+ "start-server";
        String[] aCommand = new String[] { emulatorPath,"-avd", namedAVD};
        System.out.println("emulatorPath:"+emulatorPath);
        try{
            Process process = new ProcessBuilder(aCommand).start();
            System.out.println("########## Starting Emulator ##########");
            process.waitFor(10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static void stopEmulator(){
        String sdkPath = "C:/Users/flatWhite/AppData/Local/Android/Sdk";
        String emulatorPath = sdkPath+"/platform-tools/"+File.separator+"adb";
        String[] aCommand = new String[] { emulatorPath,"emu", "kill"};

        System.out.println(emulatorPath);

        try{
            Process process = new ProcessBuilder(aCommand).start();
            System.out.println("########## Killing Emulator ##########");
            process.waitFor(10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}