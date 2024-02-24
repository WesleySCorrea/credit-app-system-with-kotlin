package me.dio.credit.application.system.services.impl

import me.dio.credit.application.system.entities.Custumer
import me.dio.credit.application.system.repositories.CustumerRepository
import me.dio.credit.application.system.services.ICustumerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustumerService(
    private val repository: CustumerRepository
): ICustumerService {

    override fun save(custumer: Custumer): Custumer {
        return this.repository.save(custumer)
    }

    override fun findById(id: Long): Custumer {
        return this.repository.findById(id).orElseThrow{
            throw RuntimeException("id $id not found")
        }
    }

    override fun delete(id: Long){
        return this.repository.deleteById(id)
    }
}