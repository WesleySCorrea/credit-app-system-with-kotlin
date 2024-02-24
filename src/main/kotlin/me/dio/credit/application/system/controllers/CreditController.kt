package me.dio.credit.application.system.controllers

import me.dio.credit.application.system.dto.CreditDTO
import me.dio.credit.application.system.dto.CreditListResponse
import me.dio.credit.application.system.dto.CreditResponse
import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.services.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(
    private val creditService: CreditService
) {

    @PostMapping
    fun savaCredit(@RequestBody creditDTO: CreditDTO): ResponseEntity<String> {
        val newCredit: Credit = this.creditService.save(creditDTO.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${newCredit.creditCode} - Customer ${newCredit.customer?.firstName} created!")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditListResponse>> {

        return ResponseEntity.status(HttpStatus.OK).
        body(this.creditService.findByCustumer(customerId).stream()
            .map { credit: Credit -> CreditListResponse(credit)}.collect(Collectors.toList()))
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "costumerId") costumerId: Long,
                         @PathVariable creditCode: UUID): ResponseEntity<CreditResponse> {
        val credit: Credit = this.creditService.findByCreditCode(costumerId, creditCode)

        return ResponseEntity.status(HttpStatus.OK).body(CreditResponse(credit))
    }
}