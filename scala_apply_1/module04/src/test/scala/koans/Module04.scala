
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */

package koans
import org.scalatest.Matchers
import org.scalatest.SeveredStackTraces
import support.BlankValues._
import support.KoanSuite
import java.io.{BufferedReader, File, FileNotFoundException, FileReader}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

class Module04 extends KoanSuite with Matchers with SeveredStackTraces {

  test ("Refactoring var to val") {

    // this method passes, but it's not very "scala-y", see if you can rid it of the need
    // to use a var but still pass the tests
    //
    // Hint: maybe recursion? List.head will give you the first value, List.tail will give you the rest
    @tailrec
    def max(numbers: List[Int]):Int = {
        if (numbers.size > 1 && numbers.head <= numbers.tail.max) max(numbers.tail)
        else if (numbers.isEmpty) Int.MinValue
        else numbers.head
    }

    max(List(1, 2, 3, 4, 5)) should be (5)
    max(List(5, 4, 3, 2, 1)) should be (5)
    max(List(-5, -1, -3)) should be (-1)
    max(Nil) should be (Int.MinValue)
  }


  // there are files in the "." directory that have the extension .shkspr, complete the function below
  // using a for to filter out all files with the .shkspr extension and return a list of their filenames
  // so that the tests below are satisfied
  def listShakespeareFiles(dirPath: String) : Array[String] = {
    val fileList = (new java.io.File(dirPath)).listFiles

    // you need to replace this line with the real implementation
    val result = for(a: File <- fileList
        if(a.getName.split("\\.").last == "shkspr")
        ) yield a.getName

    result
  }
  // question: can you guess why we define this method outside of the test below?

  test ("List Shakespeare files in the given directory") {
    val shakespeareFilenames = listShakespeareFiles(".").toList

    shakespeareFilenames.length should be (3)
    shakespeareFilenames should contain theSameElementsAs (List("caesar.shkspr", "hamlet.shkspr", "romeo.shkspr"))
  }

  // complete the method definition below to open a file, read the first line, close the file
  // and return the first line that was read. Make sure that the file is always closed, even
  // if there is an exception. new BufferedReader(new FileReader(filePath)) should give you
  // what you need
  def firstLineOfFile(filePath: String) : String = {
    // replace with the real implementation
    val bufferedSource = Source.fromFile(filePath)
    try {
      bufferedSource.getLines().find(_ => true).get
    } catch {
      case e: FileNotFoundException => "Failed"
    } finally {
      bufferedSource.close()
    }
  }

  test ("First line of file") {
    firstLineOfFile("caesar.shkspr") should be ("Beware the ides of March.")
    firstLineOfFile("romeo.shkspr") should be ("O Romeo, Romeo, wherefore art thou Romeo?")
    firstLineOfFile("hamlet.shkspr") should be ("The lady doth protest too much, methinks.")

    intercept[java.io.FileNotFoundException] { // nice, huh?
      firstLineOfFile("macbeth.shkspr") should be ("")
    }
  }

  // finish this method so that the string is first trimmed (get rid of leading and trailing space)
  // and then the last character is matched to either a '?' ("Question") or anything else ("Statement")
  // so that the test below passes
  def statementOrQuestion(str : String) : String =
    if (str.trim().takeRight(1) == "?") "Question" else "Statement"

  test ("statement or question?") {
    statementOrQuestion("hello") should be ("Statement")
    statementOrQuestion("hello.") should be ("Statement")
    statementOrQuestion("hello. ") should be ("Statement")
    statementOrQuestion("  hello?") should be ("Question")
    statementOrQuestion("hello? ") should be ("Question")
  }

  // extra credit
  test ("shakespeare files contain question or statement?") {
    // combine the methods you have created here to define and test a method that creates a map of
    // the shakespeare quotation file names in the working directory (".") as the key, and whether
    // they contain a statement or question, for example:
    // "caesar.shkspr" -> "Statement"
    // "romeo.shkspr" -> "Question"
    // "hamlet.shkspr" -> "Statement"
    // hint - consider using a scala.collection.immutable.HashMap[String, String] and a var
    // or a scala.collection.mutable.HashMap[String, String] and a val
    // can you add a test for a key of "macbeth.shkspr" that ensures a java.util.NoSuchElementException?
    // extra extra credit - can you find a way to do it without using either a var or a mutable Map?
    //var shksprMap = new scala.collection.immutable.HashMap[String, String]


    val shksprMap = {
      for {
        file <- listShakespeareFiles(".")
        ehh = statementOrQuestion(firstLineOfFile(file))
      } yield file -> ehh
    }.toMap

    shksprMap("caesar.shkspr") should be ("Statement")
    shksprMap("romeo.shkspr") should be ("Question")
    shksprMap("hamlet.shkspr") should be ("Statement")

  }
}
