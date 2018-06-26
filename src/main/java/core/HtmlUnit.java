package core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HtmlUnit {

	public static void test() {

		WebDriver driver = new HtmlUnitDriver();
		((HtmlUnitDriver) driver).setJavascriptEnabled(true); // JavaScript Enable
		
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
