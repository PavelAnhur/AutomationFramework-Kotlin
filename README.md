# automation_framework-kotlin

## Automation tests for http://automationpractice.com/index.php

### Tool stack: Kotlin + Gradle + TestNG + SpringBoot + Postgresql + Docker

 *********
_UI tests execution using Intellij IDEA:_\
config `all-ui-tests.xml` VM options `-Dbrowser=${browser}`
 *********
_Tests execution using command line:_\
`gradle test -Dbrowser=${browser}` or\
`gradle test -Dbrowser=${browser} -Dsuite=${suite}`\
`suite` is optional and if it's null, all tests will be executed using `all-ui-tests.xml`
**********
_Available browsers for local run_: `chrome`, `firefox`, `edge`, `opera`
 *********
_Selenium Grid + Docker:_\
`docker-compose -f src/main/resources/docker-compose.yml up`\
   (all needed instructions in docker-compose.yml file)\
Available browsers: `remoteChrome`, `remoteFirefox`, `remoteEdge`
 *********
_Static code analysis Ktlint:_\
checks the code styling, also helps to format the code and make it better for understanding\
_run the command from the project directory_\
`gradlew ktlintCheck` to actually check your code’s formatting\
`gradlew ktlintFormat` to automatically fix any errors which are reported by ktlintCheck
**********
_PostgreSQL:_\
all properties are stored in the `configuration.properties` file except the password ;)
**********
_SpringBoot test:_\
environment variable with name `dbPassword` needed, this is your database connection password 
