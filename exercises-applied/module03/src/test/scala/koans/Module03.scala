
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */

package koans
import org.scalatest.Matchers
import support.BlankValues._
import support.KoanSuite
import org.scalatest.SeveredStackTraces
import scala.language.implicitConversions
import scala.math.abs

// Note, in the following exercise, to get the tests to even compile we had to
// comment out the test code. You will need to uncomment it first, then write
// the classes necessary to get it to compile, and then get the tests to pass.

class Module03 extends KoanSuite with Matchers with SeveredStackTraces {

  // Flight 03 exercise - uncomment the tests below, and then define a class
  // called ComplexNum that:
  class ComplexNum(val real: Double, val imaginary: Double) {
    def this(real: Double) = this(real, 0)

    override val toString: String = s"$real ${if (imaginary < 0) "-" else "+"} ${abs(imaginary)}i"

    def +(comNum: ComplexNum):ComplexNum = new ComplexNum(real + comNum.real, imaginary + comNum.imaginary)

//    def +(dbl: Double):ComplexNum = new ComplexNum(real + dbl, imaginary)
  }
  // UNCOMMENT BELOW
   test ("Create a new Complex number and check the values for the real/imaginary parts") {
    val complex = new ComplexNum(4, 2)

    complex.real should be (4)
    complex.imaginary should be (2)
  }

  test ("Create a new complex number with double values and check those values") {
    val complex = new ComplexNum(6.2, -1.5)

    complex.real should be (6.2)
    complex.imaginary should be (-1.5)
  }

  test ("Create a complex number from a real number, imaginary part should be 0") {
    val complex = new ComplexNum(-3.2)

    complex.real should be (-3.2)
    complex.imaginary should be (0)
  }

  test ("Complex number should be printed in the form R.R + I.Ii") {
    val complex = new ComplexNum(6, 3)
    val complex2 = new ComplexNum(5.4, 7.8)

    complex.toString should be ("6.0 + 3.0i")
    complex2.toString should be ("5.4 + 7.8i")
  }

  test ("Adding complex numbers") {
    val complex = new ComplexNum(6, 3)
    val complex2 = new ComplexNum(5.4, 7.8)
    val complex3 = complex + complex2

    complex3.real should be (11.4)
    complex3.imaginary should be (10.8)
  }

  test ("Adding complex number to a double") {
    val complex = new ComplexNum(6.5, 3.2) + 5.5

    complex.real should be (12)
    complex.imaginary should be (3.2)
  }

  // Extra credit - numbers with a negative imaginary part should be output
  // as 6.0 - 5.0i instead of 6.0 + -5.0i - if you have time, write a new test
  // for this outcome, and then adapt the toString in the class to work correctly
  // Hint: scala.math.abs will give the absolute value of a double

  // UNCOMMENT BELOW
  test ("Format for negative imaginary part should be R.R - I.Ii") {
    val complex = new ComplexNum(5, -6)
    val complex2 = new ComplexNum(5.5, -6.6)

    complex.toString should be ("5.0 - 6.0i")
    complex2.toString should be ("5.5 - 6.6i")
  }

  // Extra extra credit - adding a double to a complex works, but adding a complex
  // to a double does not. If you add an implicit conversion you can make this work
  // if you have time, write a test, and add an implicit to make it work

  // UNCOMMENT BELOW


//  implicit def implicitToDouble(dbl: Double): ComplexNum = new ComplexNum(dbl)
   test ("Add a complex to a double") {
    val complex = 5.6 + new ComplexNum(3.4, 4.5)

    complex.real should be (9)
    complex.imaginary should be (4.5)
  }


  // Extra, extra, extra credit, add a companion object and factory methods,
  // move the implicit conversion to the factory method from Double, and
  // re-write the tests above to make them work

  object ComplexNum {
    def apply(real: Double, imaginary: Double): ComplexNum = new ComplexNum(real, imaginary)

    implicit def apply(double: Double): ComplexNum = new ComplexNum(double)

  }
}

