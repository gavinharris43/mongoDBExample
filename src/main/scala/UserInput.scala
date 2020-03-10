package day5.RockPaperScissors

import scala.io.StdIn
import scala.util.{Try, Success, Failure}

object UserInput {

  val errorMsg = "Invalid Input, Try Again"

  def userInputChar: Char = {
    Try(StdIn.readLine().toUpperCase().charAt(0))
    match {
      case Success(line) => line
      case Failure(f) => println(f, errorMsg)
        userInputChar
    }
  }

  def userInputString: String = {
    Try(StdIn.readLine().toUpperCase()) match {
      case Success(line) => line
      case Failure(f) => println(f, errorMsg)
        userInputString
    }
  }
  def userInputStringNorm(msg :String): String = {
    Try(StdIn.readLine(msg)) match {
      case Success(line) => line
      case Failure(f) => println(f, errorMsg)
        userInputString
    }
  }
  def userInputString(msg: String): String = {
    Try(StdIn.readLine(msg).toUpperCase()) match {
      case Success(line) => line
      case Failure(f) => println(f, errorMsg)
        userInputString
    }
  }
}