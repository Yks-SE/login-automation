package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    public static String takeScreenshot(WebDriver driver, String name) throws Exception {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dirPath = System.getProperty("user.dir") + "/target/screenshots";
        new File(dirPath).mkdirs();
        String destPath = dirPath + "/" + name + "_" + timestamp + ".png";
        FileUtils.copyFile(src, new File(destPath));
        return destPath;
    }
}
