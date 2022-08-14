package org.example.tests.spring

import org.example.spring.SpringConfiguration
import org.example.spring.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ImportResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test

@ImportResource("classpath:beans.xml")
@ComponentScan("org.example.spring")
@EntityScan("org.example.spring.entity")
@EnableJpaRepositories("org.example.spring.repository")
@ContextConfiguration(classes = [SpringConfiguration::class])
@SpringBootTest
open class SpringTest: AbstractTestNGSpringContextTests() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun springTest() {
        val products = productRepository.findAll()
        logger.info("Product list:\n$products")

        Assert.assertTrue(true)
    }
}