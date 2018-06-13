package models

import play.api.Application
import play.api.test.{PlaySpecification, WithApplication}

class PersonRepositorySpec extends PlaySpecification {


  "Person repository" should {

    def personRepository(implicit app: Application) = Application.instanceCache[PersonRepository].apply(app)

    "create person" in new WithApplication() {
      val result = await(personRepository.create("James", 25))
      result must equalTo(Person(3, "James", 25))

    }

    "get all person" in new WithApplication() {
      val result = await(personRepository.list())
      result must equalTo(Vector(Person(1, "Bob", 23), Person(2, "Rob", 21)))
    }

    "update person" in new WithApplication() {
      // updating age from 23 to 18
      val person = Person(1, "Bob", 18)
      val result = await(personRepository.update(person))
      result must equalTo(1)
    }

    "update person who does not exist" in new WithApplication() {
      // there is no person who have id 111
      val person = Person(111, "Joy", 18)
      val result = await(personRepository.update(person))
      result must equalTo(0)
    }

    "delete person by id" in new WithApplication() {
      val result = await(personRepository.delete(1))
      result must equalTo(1)
    }


    "delete person by id who does not exist" in new WithApplication() {
      // there is no person who have id 111
      val result = await(personRepository.delete(111))
      result must equalTo(0)
    }
  }


}