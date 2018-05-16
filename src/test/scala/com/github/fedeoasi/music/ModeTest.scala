package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ModeTest extends FunSpec with Matchers {
  val scales = new Scales

  it("creates a major scale") {
    val mode = new Mode(Note.A, "M9", scales.scalaMaggiore, 1)
    mode.getNotes shouldBe Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.GSharp)
  }
}
