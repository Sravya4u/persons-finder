package com.persons.finder.domain.services

import com.persons.finder.data.Person
import com.persons.finder.dao.PersonRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonsServiceImpl(private val personRepository: PersonRepository) : PersonsService {

    override fun getByPersonId(id: Long): Person? {
        return personRepository.findByIdOrNull(id)
       // return persons;
    }

    override fun save(person: Person): Long {
        return personRepository.save(person).id;
    }

}