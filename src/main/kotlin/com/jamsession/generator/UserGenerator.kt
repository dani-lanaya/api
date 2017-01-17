package com.jamsession.generator

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.web.client.RestTemplate

data class Name(val title:String, val first:String, val last:String)

data class Location(val street:String, val city:String, val state:String, val postcode:Integer)

data class Picture(val large: String, val medium: String, val thumbnail: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUser(val name:Name, val location:Location, val email:String, val picture: Picture)

data class RandomUserResults(val results: List<RandomUser>)

fun generateUsers(): List<RandomUser> {
    val restTemplate = RestTemplate()
    val results:RandomUserResults = restTemplate.getForObject("https://randomuser.me/api/", RandomUserResults::class.java)
    return results.results
}