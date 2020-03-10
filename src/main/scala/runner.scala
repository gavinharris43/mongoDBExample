import org.mongodb.scala._

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object runner extends App {

  // To directly connect to the default server localhost on port 27017
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("mydb")
  val collection: MongoCollection[Document] = database.getCollection("test");

  val doc: Document = Document("_id" -> 1000, "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  val observable: Observable[Completed] = collection.insertOne(doc)

  observable.subscribe(new Observer[Completed] {

    override def onNext(result: Completed): Unit = println("Inserted")

    override def onError(e: Throwable): Unit = println("Failed")

    override def onComplete(): Unit = println("Completed")
  })

  val documents = (1 to 100) map { i: Int => Document("i" -> i) }
  val insertObservable = collection.insertMany(documents)

  val insertAndCount = for {
    insertResult <- insertObservable
    countResult <- collection.countDocuments()
  } yield countResult

 // println(s"total # of documents after inserting 100 small ones (should be 101):  ${insertAndCount.head()}")

  insertAndCount.head().onComplete {
    case Success(posts) => println(posts)
    case Failure(t) => println("An error has occurred: " + t.getMessage)
  }


  collection.find.first.head().onComplete({
    case Success(posts) => println(posts)
    case Failure(t) => println("An error has occurred: " + t.getMessage)
  })

 Thread.sleep(30000)
}
