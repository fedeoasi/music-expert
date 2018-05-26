package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class NoteTest extends FunSpec with Matchers {
  it("prints notes") {
    Note.A.getName shouldBe "A"
  }

  describe("isNatural") {
    it("is true for natural notes") {
      Note.A.isNatural() shouldBe true
      Note.G.isNatural() shouldBe true
      Note.C.isNatural() shouldBe true
    }

    it("is false for altered notes") {
      Note.CSharp.isNatural() shouldBe false
      Note.GFlat.isNatural() shouldBe false
      Note.CDoubleSharp.isNatural() shouldBe false
    }
  }

  it("finds notes by name") {
    Note.fromName("A") shouldBe Note.A
    Note.fromName("B#") shouldBe Note.BSharp
    Note.fromName("B##") shouldBe Note.BDoubleSharp
    Note.fromName("Db") shouldBe Note.DFlat
    Note.fromName("Dbb") shouldBe Note.DDoubleFlat
  }
}
