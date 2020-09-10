package com.crawler.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.msopentech.thali.java.toronionproxy.OnionProxyContext;
import com.msopentech.thali.java.toronionproxy.OnionProxyManager;
import com.msopentech.thali.java.toronionproxy.OnionProxyManagerEventHandler;

import net.sf.T0rlib4j.controller.network.JavaTorRelay;
import net.sf.T0rlib4j.controller.network.TorServerSocket;
import net.sf.T0rlib4j.freehaven.tor.control.TorControlCommands;

public class Main {

	public static boolean processIsRunning(String process) {
        boolean firefoxIsRunning = false;
        String line;
        try {
            Process proc = Runtime.getRuntime().exec("wmic.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            OutputStreamWriter oStream = new OutputStreamWriter(proc.getOutputStream());
            oStream.write("process where name='" + process + "'");
            oStream.flush();
            oStream.close();
            while ((line = input.readLine()) != null) {
                if (line.toLowerCase().contains("caption")) {
                    firefoxIsRunning = true;
                    break;
                }
            }
            input.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return firefoxIsRunning;
    }
	
	public static void killTor() {
        Runtime rt = Runtime.getRuntime();

        try {
            rt.exec("taskkill /F /IM firefox.exe");

            while (processIsRunning("firefox.exe")) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void runTor() throws InterruptedException {

		File torProfileDir = new File(
				"C:\\Users\\bruno\\Desktop\\Tor Browser\\Browser\\TorBrowser\\Data\\Browser\\profile.default");
		FirefoxBinary binary = new FirefoxBinary(
				new File("C:\\Users\\bruno\\Desktop\\Tor Browser\\Browser\\firefox.exe"));

		FirefoxProfile torProfile = new FirefoxProfile(torProfileDir);
		torProfile.setPreference("webdriver.load.strategy", "unstable");

		try {
			binary.startProfile(torProfile, torProfileDir, "");
			Thread.sleep(10000);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getch() {
		Scanner s = new Scanner(System.in);
		s.next();
		s.close();
	}
	
    private static final int hiddenservicedirport = 80;
    private static final int localport = 2096;
    private static CountDownLatch serverLatch = new CountDownLatch(2);
    private static String proxy;
	private static void startServer() throws Exception {
		File dir = new File("torfiles");
        JavaTorRelay node = new JavaTorRelay(dir);
        proxy = "socks5://127.0.0.1:" + node.getLocalPort();
//        TorServerSocket torServerSocket = node.createHiddenService(localport, hiddenservicedirport);
	}
	
	public static void main(String[] args) throws Exception {
		startServer();
        while (true) {
        	ChromeOptions options = new ChromeOptions();
    		options.addArguments("--proxy-server=" + proxy);
    		WebDriver driver = new ChromeDriver(options);
    		driver.get("http://check.torproject.org");
    		Thread.sleep(3000);
    		driver.close();
        }
	}
}
