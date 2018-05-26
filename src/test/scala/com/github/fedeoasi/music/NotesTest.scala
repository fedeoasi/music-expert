package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class NotesTest extends FunSpec with Matchers {
  describe("isNatural") {
    it("is true for natural notes") {
      Notes.isNatural(Note.A) shouldBe true
      Notes.isNatural(Note.G) shouldBe true
      Notes.isNatural(Note.C) shouldBe true
    }

    it("is false for altered notes") {
      Notes.isNatural(Note.CSharp) shouldBe false
      Notes.isNatural(Note.GFlat) shouldBe false
      Notes.isNatural(Note.CDoubleSharp) shouldBe false
    }
  }

  describe("nextNatural") {
    it("finds the natural successor") {
      Notes.nextNatural(Note.B) shouldBe Note.C
      Notes.nextNatural(Note.E) shouldBe Note.F
      Notes.nextNatural(Note.CSharp) shouldBe Note.D
      Notes.nextNatural(Note.ESharp) shouldBe Note.F
      Notes.nextNatural(Note.BFlat) shouldBe Note.C
      Notes.nextNatural(Note.CDoubleSharp) shouldBe Note.D
      Notes.nextNatural(Note.BDoubleFlat) shouldBe Note.C
      Notes.nextNatural(Note.GSharp) shouldBe Note.A
    }
  }

  describe("distance") {
    it("computes the distance between two notes") {
      Notes.distance(Note.C, Note.D) shouldBe 2
      Notes.distance(Note.BFlat, Note.BFlat) shouldBe 0
      Notes.distance(Note.CSharp, Note.DFlat) shouldBe 0
      Notes.distance(Note.BFlat, Note.AFlat) shouldBe 10
      Notes.distance(Note.D, Note.C) shouldBe 10
      Notes.distance(Note.F, Note.A) shouldBe 4
    }
  }

  describe("getIndex") {
    it("starts from A zero-indexed") {
      Notes.getIndex(Note.A) shouldBe 0
    }
  }

  describe("distances") {
    it("finds distances") {
      Notes.distances(Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)) shouldBe Array(
        0, 2, 2, 1, 2, 2, 2, 1
      )
    }
  }
}
