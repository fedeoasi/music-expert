package com.github.fedeoasi.music

import java.util.Optional

import org.scalatest.{FunSpec, Inspectors, Matchers}

import scala.collection.JavaConverters._

class ChordScaleFinderTest extends FunSpec with Matchers with Inspectors {
  private val s = new Scales
  private val scaleFinder = new ChordScaleFinder

  val cMajor = new Chord(Note.C, ChordType.Major)
  val fMajor = new Chord(Note.F, ChordType.Major)
  val gMajor = new Chord(Note.G, ChordType.Major)

  it("finds all scales for a note") {
    scaleFinder.findScales(Note.C) should have size 21
  }

  it("finds all scales for a major chord") {
    val foundScales = scaleFinder.findScales(cMajor).asScala
    forAll(foundScales) { scale =>
      forAll(cMajor.getNotes.asScala) { note =>
        scale.getNotes.contains(note)
      }
    }
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.AFlat, Note.BFlat), //G melodic minor
      Array(Note.C, Note.D, Note.E, Note.FSharp, Note.G, Note.A, Note.BFlat), //G melodic minor
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B), //C major
      Array(Note.C, Note.D, Note.E, Note.FSharp, Note.G, Note.A, Note.B), //G major
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.BFlat), //F major
      Array(Note.C, Note.DFlat, Note.E, Note.F, Note.G, Note.AFlat, Note.BFlat), //F harmonic minor
      Array(Note.C, Note.DSharp, Note.E, Note.FSharp, Note.G, Note.A, Note.B) //E minor
    )
  }

  it("finds all scales for an F preceded by a C") {
    val foundScales = scaleFinder.findScales(Optional.of(cMajor), fMajor, Optional.empty()).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E), //C major
      Array(Note.F, Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E) //F major
    )
  }

  it("finds all scales for an F preceded by a C and followed by a G") {
    val foundScales = scaleFinder.findScales(Optional.of(cMajor), fMajor, Optional.of(gMajor)).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E) //C major
    )
  }

  it("finds all scales for an A followed by an F# minor") {
    val aMajor = new Chord(Note.A, ChordType.Major)
    val fSharpMinor = new Chord(Note.FSharp, ChordType.m)
    val foundScales = scaleFinder.findScales(Optional.empty(), aMajor, Optional.of(fSharpMinor)).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.G), //D major
      Array(Note.A, Note.B, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.G), //E mel inor
      Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.GSharp), //A major
      Array(Note.A, Note.B, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.GSharp), //E major
      Array(Note.A, Note.BSharp, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.GSharp) //B major
    )
  }
}
