package me.dio.credit.application.system.services

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import me.dio.credit.application.system.entities.Address
import me.dio.credit.application.system.entities.Customer
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repositories.CustomerRepository
import me.dio.credit.application.system.services.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK lateinit var repository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun `should create customer`(){

        //given
        val fakeCustmer: Customer = buildCustomer()
        every { repository.save(any()) } returns fakeCustmer

        //when
        val actual: Customer = customerService.save(fakeCustmer)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustmer)
        verify(exactly = 1) { repository.save(fakeCustmer)}
    }

    @Test
    fun `should find customer by id`() {

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { repository.findById(fakeId) } returns Optional.of(fakeCustomer)

        //when
        val actual: Customer = customerService.findById(fakeId)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        verify(exactly = 1) { repository.findById(fakeId) }
    }

    @Test
    fun `should not find customer by id and throw a BusinessException`() {

        //given
        val fakeId: Long = Random().nextLong()
        every { repository.findById(fakeId) } returns Optional.empty()

        //when and then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("id $fakeId not found")
        verify(exactly = 1) { repository.findById(fakeId) }

    }

    @Test
    fun `should delete customer by id`() {

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { repository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { repository.delete(fakeCustomer) } just runs

        //when
        customerService.delete(fakeId)

        //then
        verify(exactly = 1) { repository.findById(fakeId) }
        verify(exactly = 1) { repository.delete(fakeCustomer) }
    }

    private fun buildCustomer(
        firstName: String = "Usuario",
        lastName: String = "Teste",
        cpf: String = "28475934625",
        email: String = "teste@email.com",
        password: String = "12345",
        zipCode: String = "12345",
        street: String = "Rua do Teste",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long = 1L
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
        id = id
    )
}