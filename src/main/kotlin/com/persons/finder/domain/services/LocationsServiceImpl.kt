package com.persons.finder.domain.services

import com.persons.finder.dao.LocationRepository
import com.persons.finder.data.Location
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.*

@Service
class LocationsServiceImpl(private val locationRepository: LocationRepository) : LocationsService {

    override fun updateLocation(location: Location) : Location {

        val existingLocation = findLocationByPersonId(location.referenceId)

        return if (existingLocation != null) {
            // Update existing location
            val updatedLocation = existingLocation.copy(latitude = location.latitude, longitude = location.longitude)
            locationRepository.save(updatedLocation)
        } else {
            locationRepository.save(location)
        }

    }

    private fun findLocationByPersonId(id: Long): Location? = this.locationRepository.findByIdOrNull(id)

    override fun removeLocation(locationReferenceId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPersonsByLocation(latitude: Double, longitude: Double, radiusInKm: Double): List<Long> {
        val minLatitude = latitude - (radiusInKm / 111.0)
        val maxLatitude = latitude + (radiusInKm / 111.0)
        val minLongitude = longitude - (radiusInKm / (111.0 * Math.cos(Math.toRadians(latitude))))
        val maxLongitude = longitude + (radiusInKm / (111.0 * Math.cos(Math.toRadians(latitude))))

        val locations = locationRepository.findByLatitudeBetweenAndLongitudeBetween(
                minLatitude, maxLatitude, minLongitude, maxLongitude )

        // Calculate distance for each location
        locations.forEach { location ->
            val distance = calculateDistance(latitude, longitude, location.latitude, location.longitude)
            location.distance = distance
        }

        // Sort locations by distance
         locations.sortedBy { it.distance }
        return locations.map { it.referenceId }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Earth radius in kilometers
        return acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon2-lon1))* R
    }

}