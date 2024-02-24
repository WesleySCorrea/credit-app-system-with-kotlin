package me.dio.credit.application.system.services

import me.dio.credit.application.system.entities.Custumer

interface ICustumerService {

    fun save(custumer: Custumer): Custumer

    fun findById(id: Long): Custumer

    fun delete(id: Long)
}