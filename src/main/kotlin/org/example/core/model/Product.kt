package org.example.core.model

class Product private constructor(
    val name: String?,
    val cost: Double?,
    val description: String?

) {
    data class Builder(
        var name: String? = null,
        var cost: Double? = null,
        var description: String? = null
    ) {
        fun name(name: String) = apply { this.name = name }
        fun cost(cost: Double) = apply { this.cost = cost }
        fun description(description: String) = apply { this.description = description }
        fun build() = Product(name, cost, description)
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as Product
        
        if (name != other.name) return false
        if (cost != other.cost) return false
        if (description != other.description) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (cost?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
    
    override fun toString(): String {
        return """Product(
            |name=$name,
            |cost=$cost,
            |description=$description)""".trimMargin()
    }
}
