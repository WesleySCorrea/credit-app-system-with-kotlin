package me.dio.credit.application.system.services.impl

import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.repositories.CreditRepository
import me.dio.credit.application.system.services.ICreditService
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class CreditService(
    private val repository: CreditRepository,
    private val custumerService: CustumerService
): ICreditService {

    override fun save(credit: Credit): Credit {
        credit.apply {
            custumerService.findById(credit.custumer?.id!!)
        }
        return this.repository.save(credit)
    }

    override fun findByCustumer(custumerId: Long): List<Credit> {
        return this.repository.findAllByCustumer(custumerId)
    }

    override fun findByCreditCode(custumerId: Long, creditCode: UUID): Credit {
        val credit: Credit = this.repository.findByCreditCode(creditCode)?: throw RuntimeException("Credid code $creditCode not found")
        return if (credit.custumer?.id == custumerId) credit else throw RuntimeException("This credit code not pertence at this client")
    }
}