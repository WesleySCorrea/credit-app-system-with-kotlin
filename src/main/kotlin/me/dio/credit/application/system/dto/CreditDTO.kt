package me.dio.credit.application.system.dto

import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.entities.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDTO(

    val creditValue: BigDecimal,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallment: Int,
    var customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallment = this.numberOfInstallment,
        customer = Customer(id = this.customerId)
    )
}
