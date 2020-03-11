import day5.RockPaperScissors.UserInput
import org.mongodb.scala._

import scala.Helpers._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.model.Filters
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates._
import runner.collection



object runnerPerson extends App {
  val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY )
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("mydb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Person] = database.getCollection("day8/person")
  val fNameMsg = "Enter First Name:"
  val lNameMsg = "Enter Last Name "
  val running = new Running(true)

  while(running.isOpen) {
    val ui = UserInput.userInputString("What Function would you like to do (create, read, read allc, update, delete, close)")

    if (ui == "DELETE") {
      delete(UserInput.userInputStringNorm(fNameMsg), UserInput.userInputStringNorm(lNameMsg))
    } else if (ui == "UPDATE") {
      update(UserInput.userInputStringNorm(fNameMsg), UserInput.userInputStringNorm(lNameMsg),
        UserInput.userInputStringNorm(s"$lNameMsg NEW"))
    } else if (ui == "READ") {
      find(UserInput.userInputStringNorm("Enter Data Type"), UserInput.userInputStringNorm("Enter Value"))
    } else if (ui == "READ ALL") {
      findAll
    } else if (ui == "CREATE") {
      create(UserInput.userInputStringNorm(fNameMsg), UserInput.userInputStringNorm(lNameMsg))
    } else if(ui == "CLOSE") {
      running.close
    }

  }
  def create(fName :String, lName :String): Unit ={
    val person = Person(fName,lName)
    collection.insertOne(person).results
  }
  def update(fName :String, lName :String, lNameNew :String): Unit ={
    collection.updateOne(Filters and(Filters.eq("firstName" , fName), Filters.eq("lastName", lName))
    ,set("lastName", lNameNew)).printResults()

  }
  def delete(fName :String, lName :String): Unit ={
    collection.deleteOne(Filters.and(Filters.eq("firstName", fName), Filters.eq("lastName", lName))).printResults()
  }
  def find(dataType :String, value:String): Unit ={
    collection.find(equal(s"$dataType", s"$value")).printResults()
  }

  def findAll {
    collection.find.printResults()
  }

}
