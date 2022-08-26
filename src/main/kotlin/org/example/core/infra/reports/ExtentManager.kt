package org.example.core.infra.reports

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.reporter.ExtentSparkReporter

object ExtentManager {
    @get:Synchronized
    val extentReports = ExtentReports()
        get() {
            val reporter = ExtentSparkReporter("./extent-reports/extent-report.html")
            reporter.config().reportName = "Sample Extent Report"
            field.attachReporter(reporter)
            field.setSystemInfo("Blog Name", "SW Test Academy")
            field.setSystemInfo("Author", "Onur Baskirt")
            return field
        }
}
