package com.jamsession

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Document(indexName = "userdata")
data class Musician(@Id val id: Long, var name: String, var styles: List<String>, var instruments: List<String>)

interface MusicianRepository : ElasticsearchRepository<Musician, Long>

@RestController
open class MusicianController(val musicianRepository: MusicianRepository) {
    @GetMapping("/musicians")
    fun getAllMusicians(): Iterable<Musician> = musicianRepository.findAll()
}