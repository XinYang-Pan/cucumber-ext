package cn.nextop.thorin.test.util;

import java.util.Properties;

public class TestEnvUtils {

	public static void setCucumberEnvVar() {
		Properties props = new Properties();
		putValueWithDefault(props, "java.net.preferIPv4Stack", "true");
		putValueWithDefault(props, "APP_NAME", "cucumber");
		putValueWithDefault(props, "APP_ID", "124");
		putValueWithDefault(props, "env", "test");
		putValueWithDefault(props, "APP_HOME", "C:/ProgramData/Nextop/thorin/cucumber");
		// 
		System.getProperties().putAll(props);
		System.out.printf("Setting cucumber env. Properties=%s %n", props);
	}

	public static void setJunitEnvVar() {
		Properties props = new Properties();
		putValueWithDefault(props, "java.net.preferIPv4Stack", "true");
		putValueWithDefault(props, "APP_NAME", "junit");
		putValueWithDefault(props, "APP_ID", "123");
		putValueWithDefault(props, "env", "test");
		putValueWithDefault(props, "APP_HOME", "C:/ProgramData/Nextop/thorin/junit_test");
		// 
		System.getProperties().putAll(props);
		System.out.printf("Setting junit env. Properties=%s %n", props);
	}

	public static void setWebEnvVar() {
		Properties props = new Properties();
		putValueWithDefault(props, "java.net.preferIPv4Stack", "true");
		putValueWithDefault(props, "APP_NAME", "test_web");
		putValueWithDefault(props, "APP_ID", "122");
		putValueWithDefault(props, "env", "test");
		putValueWithDefault(props, "APP_HOME", "C:/ProgramData/Nextop/thorin/web_test");
		// 
		System.getProperties().putAll(props);
		System.out.printf("Setting junit env. Properties=%s %n", props);
	}

	public static void setShellEnvVar() {
		Properties props = new Properties();
		putValueWithDefault(props, "java.net.preferIPv4Stack", "true");
		putValueWithDefault(props, "APP_NAME", "shell");
		putValueWithDefault(props, "APP_ID", "121");
		putValueWithDefault(props, "env", "test");
		putValueWithDefault(props, "APP_HOME", "C:/ProgramData/Nextop/thorin/shell");
		// 
		System.getProperties().putAll(props);
		System.out.printf("Setting junit env. Properties=%s %n", props);
	}

	private static void putValueWithDefault(Properties props, String key, String defaultValue) {
		props.put(key, System.getProperty(key, defaultValue));
	}

}
