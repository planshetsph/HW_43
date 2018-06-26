package core;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static final String[] URLS = new String[] { "http://alex.academy/exe/payment/index.html",
			"http://alex.academy/exe/payment/index2.html", "http://alex.academy/exe/payment/index3.html",
			"http://alex.academy/exe/payment/index4.html", "http://alex.academy/exe/payment/indexE.html" };

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		System.out.println("Browser: Edge");
		Edge.test();

		System.out.println("Browser: Chrome");
		Chrome.test();

		System.out.println("Browser: Firefox");
		Firefox.test();

		System.out.println("Browser: HtmlUnit");
		HtmlUnit.test();

	}

}
