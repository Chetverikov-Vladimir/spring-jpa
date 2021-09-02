package che.vlvl.springjpa.entity

import javax.persistence.*

@Entity
@Table(name = "persons")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // На основе автоинкрементного поля таблицы
    val id:Long,

    @Column(name="person_name")
    val name:String,

    @OneToOne(
        targetEntity = Avatar::class,
        cascade = [CascadeType.ALL], //Все операции каскадно влияют на связанные сущности
        fetch = FetchType.EAGER // Загрузка связанных сущностей вместе с родительской
    )
    @JoinColumn(
        name="avatar_id",
        foreignKey = ForeignKey(name = "FK_avatar") // Имя поля внешнего ключа в родительской таблице
    )
    val avatar: Avatar
)