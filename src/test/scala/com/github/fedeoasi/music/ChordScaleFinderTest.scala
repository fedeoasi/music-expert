package com.github.fedeoasi.music

import java.util.Optional

import org.scalatest.{FunSpec, Inspectors, Matchers}

import scala.collection.JavaConverters._

class ChordScaleFinderTest extends FunSpec with Matchers with Inspectors {
  private val scaleFinder = new ChordScaleFinder

  val cMajor = new Chord(Note.C, ChordType.Major)
  val fMajor = new Chord(Note.F, ChordType.Major)
  val gMajor = new Chord(Note.G, ChordType.Major)

  it("finds all scales for a note") {
    scaleFinder.findScales(Note.C) should have size 21
  }

  it("finds all scales for a major chord") {
    val foundScales = scaleFinder.findModes(cMajor).asScala
    forAll(foundScales) { scale =>
      forAll(cMajor.getNotes.asScala) { note =>
        scale.getNotes.contains(note)
      }
    }
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.AFlat, Note.BFlat), //F melodic minor
      Array(Note.C, Note.D, Note.E, Note.FSharp, Note.G, Note.A, Note.BFlat), //G melodic minor
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.B), //C major
      Array(Note.C, Note.D, Note.E, Note.FSharp, Note.G, Note.A, Note.B), //G major
      Array(Note.C, Note.D, Note.E, Note.F, Note.G, Note.A, Note.BFlat), //F major
      Array(Note.C, Note.DFlat, Note.E, Note.F, Note.G, Note.AFlat, Note.BFlat), //F harmonic minor
      Array(Note.C, Note.DSharp, Note.E, Note.FSharp, Note.G, Note.A, Note.B) //E minor
    )
  }

  it("finds all scales for an F preceded by a C") {
    val foundScales = scaleFinder.findModes(Optional.of(cMajor), fMajor, Optional.empty()).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E), //C major
      Array(Note.F, Note.G, Note.A, Note.BFlat, Note.C, Note.D, Note.E) //F major
    )
  }

  it("finds all scales for an F preceded by a C and followed by a G") {
    val foundScales = scaleFinder.findModes(Optional.of(cMajor), fMajor, Optional.of(gMajor)).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.F, Note.G, Note.A, Note.B, Note.C, Note.D, Note.E) //C major
    )
  }

  it("finds all scales for an A followed by an F# minor") {
    val aMajor = new Chord(Note.A, ChordType.Major)
    val fSharpMinor = new Chord(Note.FSharp, ChordType.m)
    val foundScales = scaleFinder.findModes(Optional.empty(), aMajor, Optional.of(fSharpMinor)).asScala
    foundScales.map(_.getNotes) should contain theSameElementsAs List(
      Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.G), //D major
      Array(Note.A, Note.B, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.G), //E mel inor
      Array(Note.A, Note.B, Note.CSharp, Note.D, Note.E, Note.FSharp, Note.GSharp), //A major
      Array(Note.A, Note.B, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.GSharp), //E major
      Array(Note.A, Note.BSharp, Note.CSharp, Note.DSharp, Note.E, Note.FSharp, Note.GSharp) //B major
    )
  }

  it("finds two possible for a D preceded by a G (D major) and followed by a C7") {
    val dMajor = new Chord(Note.D, ChordType.Major)
    val c7 = new Chord(Note.C, ChordType.Seventh)
    val gMajor = new Chord(Note.G, ChordType.Major)
    scaleFinder.findScales(Optional.of(gMajor), dMajor, Optional.of(c7)) should contain theSameElementsAs Seq(
      new Scale(Note.D, Scales.scalaMaggiore), new Scale(Note.G, Scales.scalaMaggiore)
    )
  }

  it("finds one scale for an F# minor preceded by a D major and followed by a G") {
    val fSharpMinor = new Chord(Note.FSharp, ChordType.m)
    val dMajor = new Chord(Note.D, ChordType.Major)
    val gMajor = new Chord(Note.G, ChordType.Major)
    scaleFinder.findScales(Optional.of(dMajor), fSharpMinor, Optional.of(gMajor)) should contain theSameElementsAs Seq(
      new Scale(Note.D, Scales.scalaMaggiore)
    )
  }

  describe("Scale Ranking") {
    it("ranks scales for a single chord") {
      scaleFinder.rankScales(Seq(cMajor).asJava).asScala should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.G, Scales.scalaMaggiore), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMinMel), 1),
        new ScoredScale(new Scale(Note.E, Scales.scalaMinArm), 1),
        new ScoredScale(new Scale(Note.G, Scales.scalaMinMel), 1),
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMaggiore), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMinArm), 1)
      )
    }

    it("ranks scales for two chords") {
      scaleFinder.rankScales(Seq(cMajor, fMajor).asJava).asScala should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 2),
        new ScoredScale(new Scale(Note.F, Scales.scalaMaggiore), 2)
      )
    }

    it("ranks scales for three chords") {
      scaleFinder.rankScales(Seq(cMajor, fMajor, gMajor).asJava).asScala should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 3)
      )
    }
  }

  describe("Progression") {
    it("returns scales for a single chord") {
      scaleFinder.progression(Seq(cMajor).asJava).asScala.head should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.G, Scales.scalaMaggiore).from(Note.C), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMinMel).from(Note.C), 1),
        new ScoredScale(new Scale(Note.E, Scales.scalaMinArm).from(Note.C), 1),
        new ScoredScale(new Scale(Note.G, Scales.scalaMinMel).from(Note.C), 1),
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMaggiore).from(Note.C), 1),
        new ScoredScale(new Scale(Note.F, Scales.scalaMinArm).from(Note.C), 1)
      )
    }

    it("returns scales for a two chords") {
      val Seq(first, second) = scaleFinder.progression(Seq(cMajor, fMajor).asJava).asScala
      first should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 2),
        new ScoredScale(new Scale(Note.F, Scales.scalaMaggiore).from(Note.C), 2)
      )
      second should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore).from(Note.F), 2),
        new ScoredScale(new Scale(Note.F, Scales.scalaMaggiore), 2)
      )
    }

    it("returns scales for three chords") {
      val Seq(first, second, third, fourth) = scaleFinder.progression(Seq(cMajor, cMajor, fMajor, gMajor).asJava).asScala
      first should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 3)
      )
      second should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore), 3)
      )
      third should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore).from(Note.F), 3)
      )
      fourth should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.C, Scales.scalaMaggiore).from(Note.G), 3)
      )
    }

    it("returns scales for another three chord progression") {
      val aMajor = new Chord(Note.A, ChordType.Major)
      val fSharpMinor = new Chord(Note.FSharp, ChordType.m)
      val dMajor = new Chord(Note.D, ChordType.Major)
      val Seq(first, second, third, fourth) = scaleFinder.progression(Seq(aMajor, aMajor, fSharpMinor, dMajor).asJava).asScala
      first should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.A, Scales.scalaMaggiore), 3),
        new ScoredScale(new Scale(Note.D, Scales.scalaMaggiore).from(Note.A), 3)
      )
      second should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.A, Scales.scalaMaggiore), 3),
        new ScoredScale(new Scale(Note.D, Scales.scalaMaggiore).from(Note.A), 3)
      )
      third should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.A, Scales.scalaMaggiore).from(Note.FSharp), 3),
        new ScoredScale(new Scale(Note.D, Scales.scalaMaggiore).from(Note.FSharp), 3)
      )
      fourth should contain theSameElementsAs Seq(
        new ScoredScale(new Scale(Note.A, Scales.scalaMaggiore).from(Note.D), 3),
        new ScoredScale(new Scale(Note.D, Scales.scalaMaggiore), 3)
      )
    }
  }
}
