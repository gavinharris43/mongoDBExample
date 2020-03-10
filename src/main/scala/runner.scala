import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.Helpers._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.{BulkWriteOptions, DeleteOneModel, InsertOneModel, ReplaceOneModel, UpdateOneModel, WriteModel}
import org.mongodb.scala.model.Updates._

object runner extends App {

  // To directly connect to the default server localhost on port 27017
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("mydb")
  val collection: MongoCollection[Document] = database.getCollection("test");

  val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
    "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

  val observable: Observable[Completed] = collection.insertOne(doc)

  observable.subscribe(new Observer[Completed] {

    override def onNext(result: Completed): Unit = println("Inserted")

    override def onError(e: Throwable): Unit = println("Failed")

    override def onComplete(): Unit = println("Completed")
  })
//
//  val documents = (1 to 100) map { i: Int => Document("i" -> i) }
//  val insertObservable = collection.insertMany(documents)
//
//  val insertAndCount = for {
//    insertResult <- insertObservable
//    countResult <- collection.countDocuments
//  } yield countResult

 // println(s"total # of documents after inserting 100 small ones (should be 101):  ${insertAndCount.head()}")
//
//  insertAndCount.head().onComplete {
//    case Success(posts) =>  println(posts)
//    case Failure(t) => println("An error has occurred: " + t.getMessage)
//  }

  //collection.find.printResults()
//

//
//
//
//  collection.find(equal("i", 71)).first().printHeadResult()

//  collection.find(and(gt("i", 50), lte("i", 100))).printResults()
//  collection.deleteOne(equal("i", 110)).printHeadResult("Delete Result: ")
//  collection.deleteMany(gte("i", 0)).printResults()
//  collection.drop().printResults()

//  collection.find(exists("i")).sort(descending("i")).first().printHeadResult()
//  collection.find().projection(excludeId()).first().printHeadResult()
  collection.updateOne(equal("i", 10), set("i", 110)).printHeadResult("Update Result: ")
  //collection.updateMany(lt("i", 100), inc("i", 100)).printHeadResult("Update Result: ")

//  val writes: List[WriteModel[_ <: Document]] = List(
//    InsertOneModel(Document("_id" -> 4)),
//    InsertOneModel(Document("_id"-> 5)),
//    InsertOneModel(Document("_id" -> 6)),
//    UpdateOneModel(Document("_id" -> 1), set("x", 2)),
//    DeleteOneModel(Document("_id" -> 2)),
//    ReplaceOneModel(Document("_id" -> 3), Document("_id" -> 3, "x" -> 4))
//  )
//
//  // 1. Ordered bulk operation - order is guaranteed
//  collection.bulkWrite(writes).printHeadResult("Bulk write results: ")

  // 2. Unordered bulk operation - no guarantee of order of operation
  //collection.bulkWrite(writes, BulkWriteOptions().ordered(false)).printHeadResult("Bulk write results (unordered): ")




  collection.find.printResults()

 Thread.sleep(3000)


}
