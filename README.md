# Cucumber Extension

An Java Extension Util for Cucumber Framework

# Maven

```
<dependency>
 <groupId>io.github.xinyang-pan</groupId>
  <artifactId>cucumber-ext</artifactId>
 <version>1.0.0</version>
</dependency>
```

# Overview
* Cucumber is BBD testing framework, Documents
  * https://cucumber.io/
  * https://sukesh15.gitbooks.io/cucumber-jvm-test-framework-/content/
  * https://zsoltfabok.com/blog/2012/09/cucumber-jvm-web-with-spring-mvc/
* For assertion framework, we use assertj - http://joel-costigliola.github.io/assertj/index.html
* We also use guava/apache commons/spring



# Features

DataTable Features

1. Support custom Class converter
1. Support POJO partial fields' value assertion
1. Support to ignore blank value
1. Support to use function
1. Support to ignore entire row
1. Support row level expected failed assersion

Other Features

1. Support expected failed assersion
1. Support scenario variables(incubation)

# Examples
More to come

* [DataTable Features' Examples](https://github.com/XinYang-Pan/cucumber-ext/tree/master/src/test/java/example/test/feature/%241_datatable)
* [Other Features' Examples](https://github.com/XinYang-Pan/cucumber-ext/tree/master/src/test/java/example/test/feature/%242_misc)
