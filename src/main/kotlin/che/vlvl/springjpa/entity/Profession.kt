package che.vlvl.springjpa.entity

import javax.persistence.*

@Entity
@Table(name = "professions")
data class Profession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @Column(name = "profession")
    val name:String
)