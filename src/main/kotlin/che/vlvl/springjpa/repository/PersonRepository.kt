package che.vlvl.springjpa.repository

import che.vlvl.springjpa.entity.Person

interface PersonRepository {
    fun save(person: Person): Person
    fun getById(id: Long): Person?
    fun getByName(name: String): List<Person>
    fun deleteById(id: Long): Int

    fun getAll(): List<Person>
    fun getAllWithEntityGraph(): List<Person>
    fun getAllWithJoinFetch(): List<Person>
}