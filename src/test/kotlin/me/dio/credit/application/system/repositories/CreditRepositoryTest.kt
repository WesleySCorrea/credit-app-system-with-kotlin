package me.dio.credit.application.system.repositories

import io.mockk.verify
import me.dio.credit.application.system.entities.Address
import me.dio.credit.application.system.entities.Credit
import me.dio.credit.application.system.entities.Customer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.UUID

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired
    lateinit var repository: CreditRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach
    fun setup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit(customer = customer))
        credit2 = testEntityManager.persist(buildCredit(customer = customer))
    }

    @Test
    fun `should find credit by credit code`() {

        //given
        val crediCode1 = UUID.fromString("f7b30a77-edd1-4ca5-a23f-12e637e97d6")
        val crediCode2 = UUID.fromString("51f3a6d9-3b46-4836-b9a6-4517f61a710c")
        credit1.creditCode = crediCode1
        credit2.creditCode = crediCode2

        //when
        val creditPersisted1: Credit? = repository.findByCreditCode(crediCode1)
        val creditPersisted2: Credit? = repository.findByCreditCode(crediCode2)

        //then
        Assertions.assertThat(creditPersisted1).isNotNull
        Assertions.assertThat(creditPersisted2).isNotNull
        Assertions.assertThat(creditPersisted1).isSameAs(credit1)
        Assertions.assertThat(creditPersisted2).isSameAs(credit2)
    }

    @Test
    fun `should find all credits by customer id`() {

        //given
        val customerId: Long = 1L

        //when
        val creditList: List<Credit> = repository.findAllByCustumer(customerId)

        //then
        Assertions.assertThat(creditList).isNotNull
        Assertions.assertThat(creditList.size).isEqualTo(2)
        Assertions.assertThat(creditList).contains(credit1, credit2)

    }

    private fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2024, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
        customer: Customer
    ): Credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallment = numberOfInstallments,
        customer = customer
    )
    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        ),
        income = income,
    )

}