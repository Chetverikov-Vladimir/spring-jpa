package che.vlvl.springjpa.entity

import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@NamedEntityGraph( // позволяет включить в родительский запрос связанное поле avatar
    name = "avatar-entity-graph",
    attributeNodes = [NamedAttributeNode("avatar")]
)
@Table(name = "persons")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // На основе автоинкрементного поля таблицы
    val id: Long,

    @Column(name = "person_name")
    val name: String,

    @OneToOne(
        targetEntity = Avatar::class,
        cascade = [CascadeType.ALL], //Все операции каскадно влияют на связанные сущности
        fetch = FetchType.EAGER // Загрузка связанных сущностей вместе с родительской
    )
    @JoinColumn(
        name = "avatar_id",
        foreignKey = ForeignKey(name = "FK_avatar") // Имя поля внешнего ключа в родительской таблице
    )
    val avatar: Avatar,

    @ManyToOne(
        targetEntity = City::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "city_id", //Внешний ключ создается в таблице со стороны Many - в persons (дочерней сущности)
        foreignKey = ForeignKey(name = "FK_city")
    )
    val city: City,

    @OneToMany(
        targetEntity = Email::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 10) // Позволяет вытаскивать не по каждому человеку, а пачкой
    @Fetch(FetchMode.SELECT) // Для работы с Batch
    @JoinColumn(
        name = "person_id", //Внешний ключ создается в таблице со стороны Many - в emails (дочерней сущности)
        foreignKey = ForeignKey(name = "FK_person")
    )
    val emails: List<Email>,


    @ManyToMany(
        targetEntity = Profession::class,
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "person_professions",
        joinColumns = [JoinColumn(name = "person_id")], //Связь левой таблицы persons
        inverseJoinColumns = [JoinColumn(name = "profession_id")], // Связь правой таблицы professions
        foreignKey = ForeignKey(name = "FK_persons"),
        inverseForeignKey = ForeignKey(name = "FK_profession")
    )
    val professions: List<Profession>
)