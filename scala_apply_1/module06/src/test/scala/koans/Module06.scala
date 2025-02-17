
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */

package koans
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

import org.scalatest.Matchers
import org.scalatest.SeveredStackTraces
import support.BlankValues._
import support.KoanSuite

import scala.annotation.tailrec
import scala.io.Source
import scala.runtime.Nothing$

class Module06 extends KoanSuite with Matchers with SeveredStackTraces {

  test ("Detect any odds/evens in a list") {
    // fix the two functions below so that they return true if the list of numbers passed in has any
    // odd numbers (for the first functions) and even numbers (for the second function) so that the
    // tests pass

    def containsOdd(nums: List[Int]): Boolean = if (nums == Nil) false else if (nums.head % 2 != 0) true else containsOdd(nums.tail)
    def containsEven(nums: List[Int]): Boolean = if (nums == Nil) false else if (nums.head % 2 == 0) true else containsEven(nums.tail)

    containsOdd(List(2,4,6)) should be (false)
    containsEven(List(1,3,5)) should be (false)

    containsOdd(List(1,2,3)) should be (true)
    containsEven(List(1,2,3)) should be (true)

    containsOdd(Nil) should be (false)
    containsEven(Nil) should be (false)
  }

  test ("With file contents control structure") {
    // uncomment the tests below, and then write a new function withFileContents that takes a file name
    // as input, opens the file, reads the first line in, and provides it to a function that does something
    // with it - the tests below will exercise a couple of options based on files in the working directory
    // to make sure your implementation works. Of course, your function should close the file after use.
    // For now, you can assume that the function passed in takes a String and returns another String.
    // Hint - to make the tests pass, you might need to clean up the string that is read in from the file,
    // try .trim()

     def withFileContents[A](filePath: String)(filter: String => A): A = {
       val openFile = Source.fromFile(filePath)
       try {
         openFile.getLines().toSeq.headOption.map {
           line => filter(line)
         }.get
       } finally {
         openFile.close()
       }
     }

    val palindrome = withFileContents("quote.txt") { str => str.reverse }
    palindrome should be ("Madam, I'm Adam")

    val total = withFileContents("sum.txt") { str =>
      str.split(",").map(_.toInt).reduceLeft(_ + _).toString   // make sure to understand what this is doing
    }
    total should be ("20")
  }

  test ("onlyIfTrue - your own predicate guard") {
    // This is a bit of a contrived exercise, but make a function onlyIfTrue that takes a predicate (by-name
    // function that returns a Boolean), and an operation to carry out if and only if the predicate
    // is true, otherwise do nothing. For now, assume that the operation takes no arguments and has no
    // return (Unit).

    def onlyIfTrue(pred: => Boolean)(externalVar: => Unit): Unit = {
        if (pred) externalVar
    }

    val numList = List (-1, 0, -2, 3, -4, 5)
    var numberBelowZero = 0

    numList.foreach { n => onlyIfTrue(n < 0) {numberBelowZero += 1 } }

    println(numList.count(item => item < 0))
    println(numList.filterNot(item => item < 0).size)
    numberBelowZero should be (3)
  }

  // extra credit - the above exercise is only present to get you used to by-name and curried functions
  // you could do the same using Scala's build in features, without using a var, and in a much more compact
  // way. Write a test to find the number of negative numbers in the list using a filter, and no vars.
  // Why is this a better solution? Where might you want to do as above anyway?

}
