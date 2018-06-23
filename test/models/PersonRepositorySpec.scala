package models

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication}

class PersonRepositorySpec extends PlaySpec with GuiceOneAppPerTest {


  "Person repository" should {

    "create person" in new WithPersonRepository {
      val result = await(personRepository.create("James", 25))
      result mustBe Person(3, "James", 25)
    }

    "get all person" in new WithPersonRepository {
      val result = await(personRepository.list())
      result mustBe Vector(Person(1, "Bob", 23), Person(2, "Rob", 21))
    }

    "update person" in new WithPersonRepository {
      // updating age from 23 to 18
      val person = Person(1, "Bob", 18)
      val result = await(personRepository.update(person))
      result mustBe 1
    }

    "update person who does not exist" in new WithPersonRepository {
      // there is no person who have id 111
      val person = Person(111, "Joy", 18)
      val result = await(personRepository.update(person))
      result mustBe 0
    }

    "delete person by id" in new WithPersonRepository {
      val result = await(personRepository.delete(1))
      result mustBe 1
    }


    "delete person by id who does not exist" in new WithPersonRepository {
      // there is no person who have id 111
      val result = await(personRepository.delete(111))
      result mustBe 0
    }
  }

}

trait WithPersonRepository extends WithApplication with Injecting {

  val personRepository = inject[PersonRepository]
}