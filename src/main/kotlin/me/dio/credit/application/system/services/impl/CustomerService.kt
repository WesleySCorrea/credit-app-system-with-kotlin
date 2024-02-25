package me.dio.credit.application.system.services.impl

import me.dio.credit.application.system.entities.Customer
import me.dio.credit.application.system.repositories.CustomerRepository
import me.dio.credit.application.system.services.ICustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerService(
    private val repository: CustomerRepository
): ICustomerService {

    override fun save(customer: Customer): Customer {
        return this.repository.save(customer)
    }

    override fun findById(id: Long): Customer {
        return this.repository.findById(id).orElseThrow{
            throw RuntimeException("id $id not found")
        }
    }

    override fun delete(id: Long){
        println("Teste")
        return this.repository.deleteById(id)
    }
}