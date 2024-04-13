package com.persons.finder.dao

import com.persons.finder.data.Location
import org.springframework.data.repository.CrudRepository

interface LocationRepository : CrudRepository<Location, Long> {
    fun findByLatitudeBetweenAndLongitudeBetween(
            minLatitude: Double,
            maxLatitude: Double,
            minLongitude: Double,
            maxLongitude: Double
    ): List<Location>
}

