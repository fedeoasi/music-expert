package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ScalesTest extends FunSpec with Matchers {
  it("creates a major scale using sharp notes") {
    Scales.scala(Note.G, Scales.scalaMaggiore) shouldBe Array(
      Note.G, Note.A, Note.B, Note.C, Note.D, Note.E, Note.FSharp
    )
  }

  it("creates a major scale using flat notes") {
    Scales.scala(Note.BFlat, Scales.scalaMaggiore) shouldBe Array(
      Note.BFlat, Note.C, Note.D, Note.EFlat, Note.F, Note.G, Note.A
    )
  }

  it("creates a major scale using flat and sharp notes") {
    Scales.scala(Note.G, Scales.scalaMinMel) shouldBe Array(
      Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E, Note.FSharp
    )
  }

  it("creates a major scale using flat and double flat notes") {
    Scales.scala(Note.GFlat, Scales.scalaMinMel) shouldBe Array(
      Note.GFlat, Note.AFlat, Note.BDoubleFlat, Note.CFlat, Note.DFlat, Note.EFlat, Note.F
    )
  }
}
