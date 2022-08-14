package org.example.core.model

import javax.persistence.Column

open class Product private constructor(
    @Column
    val name: String,
    @Column
    val price: Double,
    @Column
    val description: String,
) : java.io.Serializable {
    data class Builder(
        var name: String = "",
        var price: Double = 0.0,
        var description: String = "",
    ) {
        fun name(name: String) = apply { this.name = name }
        fun price(price: Double) = apply { this.price = price }
        fun description(description: String) = apply { this.description = description }
        fun build() = Product(name, price, description)
    }

    override fun toString(): String {
        return """|
            |Product(name=$name,
            |price=$price,
            |description=$description)""".trimMargin()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (name != other.name) return false
        if (price != other.price) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
