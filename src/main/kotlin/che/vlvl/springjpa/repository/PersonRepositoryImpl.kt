package che.vlvl.springjpa.repository

import che.vlvl.springjpa.entity.Person
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
//Без @Transactional выдает ошибку
class PersonRepositoryImpl(
    @PersistenceContext
    private val entityManager: EntityManager
) : PersonRepository {

    @Transactional
    override fun save(person: Person): Person = if (person.id == 0L) {
        entityManager.persist(person)
        person
    } else {
        entityManager.merge(person)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Person? =
        entityManager.find(Person::class.java, id)

    @Transactional(readOnly = true)
    override fun getByName(name: String): List<Person> {
        val query = entityManager.createQuery("select p from Person p where p.name = :name", Person::class.java)
        query.setParameter("name", name)
        return query.resultList
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<Person> {
        val query = entityManager.createQuery("select p from Person p", Person::class.java)
        return query.resultList
    }

    /**
     * Позволяет объединить получение связанной сущности avatar в один запрос с родительским
     * Одно из решений проблемы N+1 запросов
     * Нужна аннотация @NamedEntityGraph в родительской сущности
     */
    @Transactional
    override fun getAllWithEntityGraph(): List<Person> {
        val entityGraph = entityManager.getEntityGraph("avatar-entity-graph")
        val query = entityManager.createQuery(
            "select p from Person p",
            Person::class.java
        )
        query.setHint("javax.persistence.fetchgraph", entityGraph)
        return query.resultList
    }

    /**
     * Второе решение N+1 проблемы. Для полей OneToMany и ManyToMany в сущности нужно
     * играться с аннотацией hibernate @Fetch(FetchMode.SUBSELECT)
     */
    @Transactional
    override fun getAllWithJoinFetch(): List<Person> {
        val query = entityManager.createQuery(
            "select p from Person p " +
                    "join fetch p.avatar " +
                    "join fetch p.city ",
            Person::class.java
        )
        return query.resultList
    }

    @Transactional
    override fun deleteById(id: Long) {
        val query = entityManager.createQuery("delete from Person p where p.id = :id")
        query.setParameter("id", id)
        query.executeUpdate()
    }


}