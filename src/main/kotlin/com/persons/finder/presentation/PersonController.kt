package com.persons.finder.presentation

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/persons")
class PersonController(private val personService: PersonsService, private val locationService: LocationsService) {

    /**
      PUT API to update/create someone's location using latitude and longitude
        (JSON) Body
     **/
    @PutMapping("/updateLocation")
    fun updateLocation(
            @RequestParam personId: Long,
            @RequestParam latitude: Double,
            @RequestParam longitude: Double
    ): Location {
        val location = Location(null, personId, latitude, longitude)
        return locationService.updateLocation(location)
    }

    /**
         POST API to create a 'person'
        (JSON) Body and return the id of the created entity
    **/
    @PostMapping("/create")
    fun createPerson(@RequestParam name: String): ResponseEntity<Any> {
        return try {
            val person = Person(name = name)
            ResponseEntity.ok(personService.save(person));
        }catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating person: ${e.message}")
        }
    }

    /**
         GET API to retrieve people around query location with a radius in KM, Use query param for radius.
         API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
     **/
    @GetMapping("/getByLocation")
    fun getPersonsByLocation(
            @RequestParam latitude: Double,
            @RequestParam longitude: Double,
            @RequestParam radius: Double
    ): ResponseEntity<Any> {
        try {
            val nearbyPersons = locationService.getPersonsByLocation(latitude, longitude, radius)
            return ResponseEntity.ok(nearbyPersons)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving nearby locations: ${e.message}")
        }
    }

    /**
         GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     **/
    @GetMapping("/getPersons")
    fun getPersonsById(@RequestParam ids: List<Long>): ResponseEntity<Any> {
        val personResponseMap = mutableMapOf<Long, Any>()
        for (id in ids) {
            try {
                val person = personService.getByPersonId(id)
                if (person != null) {
                    personResponseMap[id] = person
                } else {
                    personResponseMap[id] = "Person not found with id: $id"
                }
            } catch (e: Exception) {
                personResponseMap[id] = "Error retrieving person: ${e.message}"
            }
        }
        return ResponseEntity.ok(personResponseMap)
    }

    @GetMapping("")
    fun getExample(): String {
        return "Hello Example"
    }

}