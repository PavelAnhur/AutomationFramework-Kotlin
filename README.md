# automation_framework-kotlin

## UI automation tests for http://automationpractice.com/index.php

### Tool stack: Kotlin + Gradle + TestNG

 *********
_Tests execution:_

config `testng-all.xml` VM options `-Dbrowser=${browser}`
 *********
_Available browsers for local run_: `chrome`, `firefox`, `edge`, `opera`
 *********
_Selenium Grid + Docker:_

`docker-compose -f src/main/resources/docker-compose.yml up`\
   (all needed instructions in docker-compose.yml file)

Available browsers: `remoteChrome`, `remoteFirefox`, `remoteEdge`
 *********
_Tests execution using command line:_\
gradle test -Dbrowser=${browser} --info

