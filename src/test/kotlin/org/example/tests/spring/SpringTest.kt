package org.example.tests.spring

import org.example.core.model.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test

@SpringBootTest
@AutoConfigureDataJdbc
class SpringTest(
    @Autowired val productRepository: ProductRepository
): AbstractTestNGSpringContextTests() {

    @Test
    fun springTest() {
        val products = productRepository.findAll()
        logger.info("Product list:\n$products")

        Assert.assertTrue(true)
    }
}