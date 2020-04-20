
/* Copyright (C) 2010-2018 Escalate Software, LLC. All rights reserved. */
package koans

package laser {
  class Gun(wattage: Int) {
    def shoot(): Beam = new Beam(lumens = wattage * 10)
    class Beam (val lumens: Int){}
  }
}