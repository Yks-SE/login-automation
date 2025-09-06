package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        String reportPath = System.getProperty("user.dir") + "/target/extent-report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Framework", "Selenium + TestNG");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(java.lang.reflect.Method method) {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = System.getenv("CI") != null; // run headless on CI

        switch (browser) {
            case "firefox":
                FirefoxOptions ffOpts = new FirefoxOptions();
                if (headless) ffOpts.addArguments("--headless=new");
                driver = new FirefoxDriver(ffOpts);
                break;
            case "edge":
                EdgeOptions edgeOpts = new EdgeOptions();
                if (headless) edgeOpts.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOpts);
                break;
            case "chrome":
            default:
                ChromeOptions chOpts = new ChromeOptions();
                if (headless) chOpts.addArguments("--headless=new");
                driver = new ChromeDriver(chOpts);
        }

        driver.manage().window().maximize();
        test = extent.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String path = utils.ScreenshotUtils.takeScreenshot(driver, result.getName());
                test.fail("Test failed: " + result.getThrowable());
                test.addScreenCaptureFromPath(path);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("Skipped");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) extent.flush();
    }
}
