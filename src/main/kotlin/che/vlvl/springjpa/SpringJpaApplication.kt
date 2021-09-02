package che.vlvl.springjpa

import org.h2.tools.Console
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringJpaApplication

fun main(args: Array<String>) {
	runApplication<SpringJpaApplication>(*args)

	Console.main(*args)
}
