package me.dio.credit.application.system.repositories

import me.dio.credit.application.system.entities.Custumer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustumerRepository: JpaRepository<Custumer, Long> {
}