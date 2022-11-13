package org.example.core.model

import java.time.Month

class Account private constructor(
    val gender: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDay: String,
    val birthMonth: Month,
    val birthYear: String
) {
    data class Builder(
        var gender: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var password: String? = null,
        var birthDay: Int? = null,
        var birthMonth: Month? = null,
        var birthYear: Int? = null
    ) {
        fun gender(gender: String) = apply { this.gender = gender }
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun email(email: String) = apply { this.email = email }
        fun password(password: String) = apply { this.password = password }
        fun dayOfBirth(birthDay: Int) = apply { this.birthDay = birthDay }
        fun monthOFBirth(birthMonth: Month) = apply { this.birthMonth = birthMonth }
        fun yearOfBirth(birthYear: Int) = apply { this.birthYear = birthYear }
        fun build() = birthMonth?.let {
            Account(
                gender.toString(),
                firstName.toString(),
                lastName.toString(),
                email.toString(),
                password.toString(),
                birthDay.toString(),
                it,
                birthYear.toString()
            )
        }
    }

    override fun toString(): String {
        return """Account(
            | gender='$gender',
            | firstName='$firstName',
            | lastName='$lastName',
            | email='$email',
            | password='$password',
            | birthDay='$birthDay',
            | birthMonth=$birthMonth,
            | birthYear='$birthYear')""".trimMargin()
    }
}
