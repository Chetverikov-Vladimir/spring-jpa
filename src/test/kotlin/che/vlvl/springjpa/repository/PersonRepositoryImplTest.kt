package che.vlvl.springjpa.repository

import che.vlvl.springjpa.entity.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

/**
 * @DataJpaTest создает кусок контекста, ответственный за persistence layer
 * Добавляет в контекст TestEntityManager
 * Создает транзакцию в начале каждого теста и ее rollback в конце
 */
@DataJpaTest
@Import(PersonRepositoryImpl::class)
class PersonRepositoryImplTest {

    @Autowired
    lateinit var repository: PersonRepositoryImpl

    @Autowired
    lateinit var em: TestEntityManager

    @Test
    fun save() {

        val savedPerson = repository.save(
            Person(
                id = 0,
                name = "test",
                avatar = Avatar(id = 0, imageUrl = "test_avatar"),
                city = City(id = 0, name = "test_city"),
                emails = listOf(Email(id = 0, address = "test_address")),
                professions = listOf(Profession(id = 0, name = "test_profession"))
            )
        )

        val loadPerson = em.find(Person::class.java, savedPerson.id)

        assert(savedPerson == loadPerson)
        assert(loadPerson.id > 0)
    }

    @Test
    fun getById() {
        val entityList = insertData()
        assert(entityList[0] == repository.getById(entityList[0].id))
    }

    @Test
    fun getByName() {
        val entityList = insertData()
        val foundPersons = repository.getByName(entityList[0].name)

        assert(foundPersons.size == 1)
        assert(foundPersons[0] == entityList[0])
    }

    @Test
    fun deleteById() {
        val entityList = insertData()

        val personBeforeDelete: Person? = em.find(Person::class.java, entityList[0].id)
        assert(personBeforeDelete == entityList[0])

        repository.deleteById(entityList[0].id)
        em.clear()

        val personAfterDelete: Person? = em.find(Person::class.java, entityList[0].id)
        assert(personAfterDelete == null)
    }


    @Test
    fun getAll() {
        val entityList = insertData()
        val persons = repository.getAll()

        assert(persons.size == entityList.size)
        entityList.forEach { assert(persons.contains(it)) }
    }

    @Test
    fun getAllWithEntityGraph() {
        val entityList = insertData()
        val persons = repository.getAllWithEntityGraph()

        assert(persons.size == entityList.size)
        entityList.forEach { assert(persons.contains(it)) }
    }

    @Test
    fun getAllWithJoinFetch() {
        val entityList = insertData()
        val persons = repository.getAllWithJoinFetch()

        assert(persons.size == entityList.size)
        entityList.forEach { assert(persons.contains(it)) }
    }


    fun insertData(): MutableList<Person> {
        val entityList = mutableListOf<Person>()
        with(em) {
            entityList.add(
                persist(
                    Person(
                        id = 0,
                        name = "One",
                        avatar = Avatar(id = 0, imageUrl = "one_avatar"),
                        city = City(id = 0, name = "city_1"),
                        emails = listOf(Email(id = 0, address = "1@mail.ru")),
                        professions = listOf(Profession(id = 0, name = "profession_1"))
                    )
                )
            )

            entityList.add(
                persist(
                    Person(
                        id = 0,
                        name = "Two",
                        avatar = Avatar(id = 0, imageUrl = "two_avatar"),
                        city = City(id = 0, name = "city_2"),
                        emails = listOf(Email(id = 0, address = "2@mail.ru")),
                        professions = listOf(Profession(id = 0, name = "profession_2"))
                    )
                )
            )
        }
        return entityList
    }
}