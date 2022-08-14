package org.example.spring.entity

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
open class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    open var productId: Int? = null

    @Basic
    @Column(name = "name", nullable = false)
    open var name: String? = null

    @Basic
    @Column(name = "price", nullable = true)
    open var price: Double? = null

    @Basic
    @Column(name = "description", nullable = true)
    open var description: String? = null


    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "productId = $productId " +
                "name = $name " +
                "price = $price " +
                "description = $description " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ProductEntity

        if (productId != other.productId) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (description != other.description) return false

        return true
    }
}
