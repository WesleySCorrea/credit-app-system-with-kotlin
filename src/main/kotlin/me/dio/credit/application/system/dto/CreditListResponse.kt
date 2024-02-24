package me.dio.credit.application.system.dto

import me.dio.credit.application.system.entities.Credit
import java.math.BigDecimal
import java.util.UUID

data class CreditListResponse(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int
) {
    constructor(credit: Credit): this (
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallment = credit.numberOfInstallment
    )
}
