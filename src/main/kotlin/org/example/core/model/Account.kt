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
        private var gender: String? = null,
        private var firstName: String? = null,
        private var lastName: String? = null,
        private var email: String? = null,
        private var password: String? = null,
        private var birthDay: Int? = null,
        private var birthMonth: Month? = null,
        private var birthYear: Int? = null
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
