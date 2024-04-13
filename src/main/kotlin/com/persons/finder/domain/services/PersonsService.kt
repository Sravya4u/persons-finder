package com.persons.finder.domain.services

import com.persons.finder.data.Person

interface PersonsService {
    fun getByPersonId(id: Long): Person?
    fun save(person: Person): Long
}