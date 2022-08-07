package org.example.core.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class ProductService(
    @Autowired
    private val repository: ProductRepository
) : IProductService {

    override fun findAll(): List<Product> {
        val productList = ArrayList<Product>()
        val products = repository.findAll()
        for (product in products) {
            productList.add(product)
        }
        return productList
    }
}
