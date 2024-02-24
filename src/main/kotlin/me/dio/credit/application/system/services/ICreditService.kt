package me.dio.credit.application.system.services

import me.dio.credit.application.system.entities.Credit
import java.util.*

interface ICreditService {

    fun save(credit: Credit): Credit

    fun findByCustumer(id: Long): List<Credit>

    fun findByCreditCode(custumerId: Long, creditCode: UUID): Credit
}