package che.vlvl.springjpa.entity

import javax.persistence.*

@Entity
@Table(name = "avatars")
data class Avatar(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "image_url")
    val imageUrl: String
)



