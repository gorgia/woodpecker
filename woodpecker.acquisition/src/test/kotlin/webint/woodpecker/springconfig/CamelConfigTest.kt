package webint.woodpecker.springconfig

import org.apache.camel.EndpointInject
import org.apache.camel.ProducerTemplate
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.test.spring.CamelSpringBootRunner
import org.apache.camel.test.spring.MockEndpoints
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertNotNull


@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner::class)
@SpringBootTest
@MockEndpoints
class CamelConfigTest {

    @Autowired
    lateinit var producerTemplate: ProducerTemplate

    @EndpointInject(uri = "mock:file:out")
    lateinit var  mockCamel: MockEndpoint

    @Test
    @Throws(InterruptedException::class)
    fun test() {
        assertNotNull(producerTemplate)
        assertNotNull(mockCamel)
        val body = "Camel"
        mockCamel.expectedMessageCount(1)
        producerTemplate.sendBody("mock:file:out", body)
        mockCamel.assertIsSatisfied()
    }





}
