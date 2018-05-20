package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ScaleTest extends FunSpec with Matchers {
  val s = new Scales

  it("generates a C major") {
    new Scale(Note.C, s.scalaMaggiore).getNotes shouldBe {
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)
    }
  }

  it("generates a D melodic minor") {
    new Scale(Note.D, s.scalaMinMel).getNotes shouldBe {
      Array(Note.D, Note.E, Note.F, Note.G, Note.A, Note.B, Note.CSharp)
    }
  }
}
