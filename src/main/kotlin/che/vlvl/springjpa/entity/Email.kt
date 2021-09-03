package che.vlvl.springjpa.entity

import javax.persistence.*

@Entity
@Table(name = "emails")
data class Email(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @Column(name = "email")
    val address:String
)