package me.dio.credit.application.system.entities

import jakarta.persistence.*
import me.dio.credit.application.system.enums.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "Credit")
data class Credit (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val creditCode: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val creditValue: BigDecimal = BigDecimal(0),

    @Column(nullable = false)
    val dayFirstInstallment: LocalDate,

    @Column(nullable = false)
    val numberOfInstallment: Int = 0,

    @Enumerated
    val status: Status = Status.IN_PROGRESS,

    @ManyToOne
    val custumer: Custumer? = null

)
