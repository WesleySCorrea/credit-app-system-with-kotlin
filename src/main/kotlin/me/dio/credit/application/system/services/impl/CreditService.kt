package me.dio.credit.application.system.services.impl

import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repositories.CreditRepository
import me.dio.credit.application.system.services.ICreditService
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.*

@Service
class CreditService(
    private val repository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            customerService.findById(credit.customer?.id!!)
        }
        return this.repository.save(credit)
    }

    override fun findByCustumer(customerId: Long): List<Credit> {
        return this.repository.findAllByCustumer(customerId)
    }

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = this.repository.findByCreditCode(creditCode)
            ?: throw BusinessException("Credid code $creditCode not found")
        return if (credit.customer?.id == customerId) credit else throw IllegalArgumentException("This credit code not pertence at this client")
    }
}