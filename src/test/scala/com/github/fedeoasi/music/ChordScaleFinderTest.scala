package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Inspectors, Matchers}

import scala.collection.JavaConverters._

class ChordScaleFinderTest extends FunSpec with Matchers with Inspectors {
  private val s = new Scales
  private val scaleFinder = new ChordScaleFinder

  it("finds all scales for a note") {
    scaleFinder.findScales(Note.C) should have size 21
  }

  it("finds all scales for a major chord") {
    val cMajor = new Chord(Note.C, ChordType.Major)
    val foundScales = scaleFinder.findScales(cMajor).asScala
    forAll(foundScales) { scale =>
      forAll(cMajor.getNotes.asScala) { note =>
        scale.contains(note)
      }
    }
    foundScales should contain theSameElementsAs List(
      Array(Note.F, Note.G, Note.AFlat, Note.BFlat, Note.C, Note.D, Note.E), //G melodic minor
      Array(Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E, Note.FSharp), //G melodic minor
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B), //C major
      Array(Note.G, Note.A, Note.B, Note.C, Note.D, Note.E, Note.FSharp), //G major
      Array(Note.F, Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E), //F major
      Array(Note.F, Note.G, Note.AFlat, Note.BFlat, Note.C, Note.DFlat, Note.E), //F harmonic minor
      Array(Note.E, Note.FSharp, Note.G, Note.A, Note.B, Note.C, Note.DSharp) //E minor
    )
  }
}
