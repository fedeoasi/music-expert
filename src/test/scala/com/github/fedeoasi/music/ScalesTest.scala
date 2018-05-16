package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ScalesTest extends FunSpec with Matchers {
  val scales = new Scales

  it("creates a major scale using sharp notes") {
    scales.scala(Note.G, scales.scalaMaggiore) shouldBe Array(
      Note.G, Note.A, Note.B, Note.C, Note.D, Note.E, Note.FSharp
    )
  }

  it("creates a major scale using flat notes") {
    scales.scala(Note.BFlat, scales.scalaMaggiore) shouldBe Array(
      Note.BFlat, Note.C, Note.D, Note.EFlat, Note.F, Note.G, Note.A
    )
  }

  it("creates a major scale using flat and sharp notes") {
    scales.scala(Note.G, scales.scalaMinMel) shouldBe Array(
      Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E, Note.FSharp
    )
  }

  it("creates a major scale using flat and double flat notes") {
    scales.scala(Note.GFlat, scales.scalaMinMel) shouldBe Array(
      Note.GFlat, Note.AFlat, Note.BDoubleFlat, Note.CFlat, Note.DFlat, Note.EFlat, Note.F
    )
  }
}
