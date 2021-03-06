package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

class ScaleTest extends FunSpec with Matchers {
  it("generates a C major") {
    new Scale(Note.C, Scales.scalaMaggiore).getNotes shouldBe {
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)
    }
  }

  it("generates a D melodic minor") {
    new Scale(Note.D, Scales.scalaMinMel).getNotes shouldBe {
      Array(Note.D, Note.E, Note.F, Note.G, Note.A, Note.B, Note.CSharp)
    }
  }

  it("generates an E harmonic minor") {
    new Scale(Note.E, Scales.scalaMinArm).getNotes shouldBe {
      Array(Note.E, Note.FSharp, Note.G, Note.A, Note.B, Note.C, Note.DSharp)
    }
  }

  it("rotates a scale") {
    new Scale(Note.C, Scales.scalaMaggiore).from(Note.C).getNotes shouldBe {
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)
    }
    new Scale(Note.C, Scales.scalaMaggiore).from(Note.F).getNotes shouldBe {
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E)
    }
    new Scale(Note.C, Scales.scalaMaggiore).from(Note.G).getNotes shouldBe {
      Array(Note.G, Note.A, Note.B, Note.C, Note.D, Note.E, Note.F)
    }
    new Scale(Note.C, Scales.scalaMaggiore).from(Note.E).getNotes shouldBe {
      Array(Note.E, Note.F, Note.G, Note.A, Note.B, Note.C, Note.D)
    }
  }

  it("does not mutate the original note array") {
    val scale = new Scale(Note.C, Scales.scalaMaggiore)
    scale.from(Note.F).getNotes shouldBe {
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E)
    }
    scale.getNotes shouldBe {
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B)
    }
  }
}
