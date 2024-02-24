package me.dio.credit.application.system.controllers

import me.dio.credit.application.system.dto.CustomerDTO
import me.dio.credit.application.system.dto.CustomerResponse
import me.dio.credit.application.system.dto.CustomerUpdateDTO
import me.dio.credit.application.system.entities.Customer
import me.dio.credit.application.system.services.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(
    private val custumerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): ResponseEntity<String> {
        val newCustomer = this.custumerService.save(customerDTO.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED).body("Customer ${newCustomer.email} saved!")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer: Customer = this.custumerService.findById(id)

        return ResponseEntity.status(HttpStatus.OK).body(CustomerResponse(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long) {
        this.deleteCustomer(id)
    }

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "id") id: Long,
                       @RequestBody customerUpdateDTO: CustomerUpdateDTO): ResponseEntity<CustomerResponse> {
        var customer: Customer = this.custumerService.findById(id)
        var customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        var custumerUpdated: Customer = this.custumerService.save(customerToUpdate)

        return ResponseEntity.status(HttpStatus.OK).body(CustomerResponse(custumerUpdated))
    }
}