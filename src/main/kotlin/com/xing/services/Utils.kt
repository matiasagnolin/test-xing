package com.xing.services

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.xing.model.Genre
import java.io.File

fun readJsonFile(file: File): List<Genre> {
    val objectMapper = ObjectMapper()

    val json = objectMapper.readTree(file)
    val list =  objectMapper.convertValue(json, object : TypeReference<List<Genre>>() {})
    return list.map { Genre(it.id,it.genreName) }.toList()
}