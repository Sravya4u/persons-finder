package com.persons.finder.data

import javax.persistence.*

@Entity(name ="Location")
data class Location(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 1,

        val referenceId: Long = 1,

        val latitude: Double = 0.0,

        val longitude: Double = 0.0,

        var distance: Double = 0.0
)
