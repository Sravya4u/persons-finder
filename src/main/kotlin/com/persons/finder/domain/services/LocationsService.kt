package com.persons.finder.domain.services

import com.persons.finder.data.Location

interface LocationsService {
    fun updateLocation(location: Location): Location
    fun removeLocation(locationReferenceId: Long)
    fun getPersonsByLocation(latitude: Double, longitude: Double, radiusInKm: Double) : List<Long>
}