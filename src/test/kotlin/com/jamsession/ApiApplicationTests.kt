package com.jamsession

import com.jamsession.generator.generateUsers
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ApiApplicationTests() {

    @Autowired
    lateinit var health:HealthController

    @Test
    fun contextLoads() {

    }

    @Test
    fun testHealthController() {
        assert(health.getHealth().message.equals("green"))
    }

    @Test
    fun testGenerateUsers() {
        assert(generateUsers().isNotEmpty())
    }

}
