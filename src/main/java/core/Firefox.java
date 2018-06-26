package core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Firefox {

	private static final String DRIVER_PATH = "./resources/windows/geckodriver.exe";

	public static void test() {

		System.setProperty("webdriver.gecko.driver", DRIVER_PATH);

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.webnotifications.enabled", false);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		FirefoxOptions options = new FirefoxOptions();
		options.merge(capabilities);

		WebDriver driver = new FirefoxDriver(options);
		driver.manage().window().maximize();

		for (String url : Main.URLS) {

			driver.get(url);

			final long start = System.currentTimeMillis();

			String strMonthlyPayment = driver.findElement(By.id("id_monthly_payment")).getText();
			String regex = "^" + "(?:\\$)?" + "(?:\\s*)?" + "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)"
					+ "$";

			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(strMonthlyPayment);
			matcher.find();

			double monthlyPayment = Double.parseDouble(matcher.group(1).replaceAll(",", ""));
			double annualPayment = new BigDecimal(monthlyPayment * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();

			DecimalFormat df = new DecimalFormat("0.00");
			String formattedAnnualPayment = df.format(annualPayment);

			driver.findElement(By.id("id_annual_payment")).sendKeys(String.valueOf(formattedAnnualPayment));
			driver.findElement(By.id("id_validate_button")).click();

			String actualResult = driver.findElement(By.id("id_result")).getText();

			final long finish = System.currentTimeMillis();

			System.out.println("String: \t" + matcher.group(0));
			System.out.println("Annual Payment: " + formattedAnnualPayment);
			System.out.println("Result: \t" + actualResult);
			System.out.println("Response time: \t" + (finish - start) + " milliseconds");
			System.out.println("______________________________________________________");
			System.out.println();
		}
		driver.quit();

	}
}
