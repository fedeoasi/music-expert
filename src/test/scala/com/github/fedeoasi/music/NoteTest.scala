package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class NoteTest extends FunSpec with Matchers {
  it("prints notes") {
    Note.A.getName shouldBe "A"
  }

  it("finds notes by name") {
    Note.fromName("A") shouldBe Note.A
    Note.fromName("B#") shouldBe Note.BSharp
    Note.fromName("B##") shouldBe Note.BDoubleSharp
    Note.fromName("Db") shouldBe Note.DFlat
    Note.fromName("Dbb") shouldBe Note.DDoubleFlat
  }
}