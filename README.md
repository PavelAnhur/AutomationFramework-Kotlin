# automation_framework-kotlin

## UI automation tests for http://automationpractice.com/index.php

### Tool stack: Kotlin + Gradle + TestNG

 *********
_Tests execution using Intellij IDEA:_\
config `testng-all.xml` VM options `-Dbrowser=${browser}`
 *********
_Tests execution using command line:_\
`gradle test -Dbrowser=${browser}` or\
`gradle test -Dbrowser=${browser} -Dsuite=${suite}`\
`suite` is optional and if it's null, all tests will be executed using `testng-all.xml`
**********
_Available browsers for local run_: `chrome`, `firefox`, `edge`, `opera`
 *********
_Selenium Grid + Docker:_\
`docker-compose -f src/main/resources/docker-compose.yml up`\
   (all needed instructions in docker-compose.yml file)\
Available browsers: `remoteChrome`, `remoteFirefox`, `remoteEdge`
 *********
_Ktlint checks our code styling and also helps us to format our code and make it better for understanding_\
`./gradlew ktlintCheck` to actually check your code’s formatting\
`./gradlew ktlintFormat` to automatically fix any errors which are reported by ktlintCheck
**********
_PostgreSQL is added_\
all properties are stored in the `configuration.properties` file except the password ;)
