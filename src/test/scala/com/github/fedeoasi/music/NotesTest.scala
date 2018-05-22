package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class NotesTest extends FunSpec with Matchers {
  val notes = new Notes

  describe("isNatural") {
    it("is true for natural notes") {
      notes.isNatural(Note.A) shouldBe true
      notes.isNatural(Note.G) shouldBe true
      notes.isNatural(Note.C) shouldBe true
    }

    it("is false for altered notes") {
      notes.isNatural(Note.CSharp) shouldBe false
      notes.isNatural(Note.GFlat) shouldBe false
      notes.isNatural(Note.CDoubleSharp) shouldBe false
    }
  }

  describe("nextNatural") {
    it("finds the natural successor") {
      notes.nextNatural(Note.B) shouldBe Note.C
      notes.nextNatural(Note.E) shouldBe Note.F
      notes.nextNatural(Note.CSharp) shouldBe Note.D
      notes.nextNatural(Note.ESharp) shouldBe Note.F
      notes.nextNatural(Note.BFlat) shouldBe Note.C
      notes.nextNatural(Note.CDoubleSharp) shouldBe Note.D
      notes.nextNatural(Note.BDoubleFlat) shouldBe Note.C
      notes.nextNatural(Note.GSharp) shouldBe Note.A
    }
  }

  describe("distance") {
    it("computes the distance between two notes") {
      notes.distance(Note.C, Note.D) shouldBe 2
      notes.distance(Note.BFlat, Note.BFlat) shouldBe 0
      notes.distance(Note.CSharp, Note.DFlat) shouldBe 0
      notes.distance(Note.BFlat, Note.AFlat) shouldBe 10
      notes.distance(Note.D, Note.C) shouldBe 10
      notes.distance(Note.F, Note.A) shouldBe 4
    }
  }

  describe("getIndex") {
    it("starts from A zero-indexed") {
      notes.getIndex(Note.A) shouldBe 0
    }
  }

  describe("distances") {
    it("finds distances") {
      notes.distances(Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)) shouldBe Array(
        0, 2, 2, 1, 2, 2, 2, 1
      )
    }
  }
}
