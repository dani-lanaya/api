package com.jamsession

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jamsession.generator.generateUsers
import com.jamsession.generator.randomUserToMusician
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Logger

@SpringBootApplication
open class ApiApplication {
    private val log = Logger.getLogger(ApiApplication::class.java.name)

    // required for kotlin deserialization
    @Bean
    open fun kotlinModule() = KotlinModule()

    @Bean
    open fun init(musicianRepository: MusicianRepository) = CommandLineRunner {
        val start = System.currentTimeMillis()
        val numRandUsers = 100
        log.info { "Generating users" }
        try {
            musicianRepository.save(generateUsers(numRandUsers).map { randomUserToMusician(it) })
            log.info { "Generated $numRandUsers musicians in ${System.currentTimeMillis() - start} ms" }
        } catch(e: Exception) {

        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ApiApplication::class.java, *args)
}

data class HealthResponse(val message:String = "green")

@RestController
class HealthController {

    @GetMapping("/health")
    fun getHealth() = HealthResponse()
}

@Document(indexName="foo")
data class DummyData(val id:Long = 1)

interface TestRepository : ElasticsearchRepository<DummyData, Long> {
    fun findById(id: Long): List<DummyData>
}