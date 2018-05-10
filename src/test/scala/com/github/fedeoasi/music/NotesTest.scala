package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class NotesTest extends FunSpec with Matchers {
  val notes = new Notes

  describe("isNatural") {
    it("is true for natural notes") {
      notes.isNatural("A") shouldBe true
      notes.isNatural("G") shouldBe true
      notes.isNatural("C") shouldBe true
    }

    it("is false for altered notes") {
      notes.isNatural("C#") shouldBe false
      notes.isNatural("Gb") shouldBe false
      notes.isNatural("C##") shouldBe false
    }
  }

  describe("nextNatural") {
    it("finds the natural successor") {
      notes.nextNatural("B") shouldBe "C"
      notes.nextNatural("E") shouldBe "F"
      notes.nextNatural("C#") shouldBe "D"
      notes.nextNatural("E#") shouldBe "F"
      notes.nextNatural("Bb") shouldBe "C"
    }
  }

  describe("distance") {
    it("computes the distance between two notes") {
      notes.distance("C", "D") shouldBe 2
      notes.distance("Bb", "Bb") shouldBe 0
      notes.distance("C#", "Db") shouldBe 0
      notes.distance("Bb", "Ab") shouldBe 10
      notes.distance("D", "C") shouldBe 10
      notes.distance("F", "A") shouldBe 4
    }
  }

  describe("getIndex") {
    it("starts from A zero-indexed") {
      notes.getIndex("A") shouldBe 0
    }
  }
}
