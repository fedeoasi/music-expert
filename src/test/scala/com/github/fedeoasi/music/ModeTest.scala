package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ModeTest extends FunSpec with Matchers {
  it("creates a major scale") {
    val mode = new Mode(Note.A, "M9", Scales.scalaMaggiore, 1)
    mode.getNotes shouldBe Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.GSharp)
  }
}
