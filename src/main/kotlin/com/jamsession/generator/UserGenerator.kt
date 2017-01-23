package com.jamsession.generator

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.jamsession.ApiApplication
import com.jamsession.Musician
import com.jamsession.MusicianRepository
import org.elasticsearch.ElasticsearchException
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Future
import java.util.logging.Level
import java.util.logging.Logger

data class Name(val title:String, val first:String, val last:String)

data class Location(val street:String, val city:String, val state:String, val postcode:String)

data class Picture(val large: String, val medium: String, val thumbnail: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUser(val name:Name, val location:Location, val email:String, val picture: Picture)

data class RandomUserResults(val results: List<RandomUser>)

val styles = mutableListOf(
    "Rock",
    "Funk",
    "Jazz",
    "Hip-Hop",
    "House",
    "Country"
)

val instruments = mutableListOf(
    "Guitar",
    "Bass",
    "Drums",
    "Vocals",
    "Keyboard"
)

fun generateUsers(num: Int = 10): List<RandomUser> {
    val restTemplate = RestTemplate()
    val results:RandomUserResults = restTemplate.getForObject("https://randomuser.me/api/?results=$num", RandomUserResults::class.java)
    return results.results
}

fun randomUserToMusician(randomUser: RandomUser): Musician {
    return Musician(0L, randomUser.name.first + randomUser.name.last, styles, instruments)
}
//
//class GenerationResults(val total: Int)
//
//@Service
//class MusicianGeneratorService(val musicianRepository: MusicianRepository) {
//    private val log = Logger.getLogger(MusicianGeneratorService::class.java.name)
//
//    @Async
//    fun generateUsers(num: Int = 10): Future<GenerationResults> {
//        var total = 0
//        val restTemplate = RestTemplate()
//        val RANDOM_USER_API = "https://randomuser.me/api/?results=$num"
//        while(total < num) {
//            log.info { "Making request to $RANDOM_USER_API ..."}
//            val results:RandomUserResults = restTemplate.getForObject(RANDOM_USER_API, RandomUserResults::class.java)
//            total += results.results.size
//            val musicians = results.results.map { randomUserToMusician(it) }
//            log.info { "Received $total random users so far" }
//            try {
//                musicianRepository.save(musicians)
//            } catch(e: ElasticsearchException) {
//                log.log(Level.SEVERE, "$e")
//                break
//            }
//        }
//        return AsyncResult<GenerationResults>(GenerationResults(total))
//    }
//
//}