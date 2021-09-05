package che.vlvl.springjpa.controller

import che.vlvl.springjpa.entity.*
import che.vlvl.springjpa.repository.PersonRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController(
    val repository: PersonRepository
) {

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id: Long) = repository.getById(id)

    @GetMapping("/save")
    fun savePerson(): Person {
        val person = Person(
            id = 0,
            name = "My person",
            avatar = Avatar(id = 0, imageUrl = "image"),
            city = City(id = 0, name = "Lipetsk"),
            emails = listOf(Email(id = 0, address = "mail@mail.ru")),
            professions = listOf(Profession(id = 0, name = "Baker"))
        )
        return repository.save(person)
    }

    @GetMapping("/all")
    fun getAllPersons() = repository.getAll()

    @GetMapping("/allgraph")
    fun getAllPersonsWithGraph() = repository.getAllWithEntityGraph()

    @GetMapping("/allfetch")
    fun getAllPersonsWithFetch() = repository.getAllWithJoinFetch()

    @GetMapping("/delete/{id}")
    fun deleteById(@PathVariable id: Long) = repository.deleteById(id)
}