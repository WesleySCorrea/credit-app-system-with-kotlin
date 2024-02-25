package me.dio.credit.application.system.dto

import jakarta.validation.constraints.*
import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(

    @field:NotNull(message = "Invalid input")
    val creditValue: BigDecimal,

    @field:Future
    val dayFirstInstallment: LocalDate,

    @field:Min(value = 1) @field:Max(value = 48)
    val numberOfInstallment: Int,

    @field:NotNull(message = "Invalid input")
    var customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallment = this.numberOfInstallment,
        customer = Customer(id = this.customerId)
    )
}
