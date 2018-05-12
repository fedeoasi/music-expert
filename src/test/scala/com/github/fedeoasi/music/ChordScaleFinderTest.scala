package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Inspectors, Matchers}

import scala.collection.JavaConverters._

class ChordScaleFinderTest extends FunSpec with Matchers with Inspectors {
  private val s = new Scales
  private val scaleFinder = new ChordScaleFinder

  it("finds all scales for a note") {
    scaleFinder.findScales("C") should have size 21
  }

  it("finds all scales for a major chord") {
    val cMajor = new Chord("C", "")
    val foundScales = scaleFinder.findScales(cMajor).asScala
    forAll(foundScales) { scale =>
      forAll(cMajor.getNotes.asScala) { note =>
        scale.contains(note)
      }
    }
    foundScales should contain theSameElementsAs List(
      Array("F", "G", "Ab", "Bb", "C", "D", "E"), //G melodic minor
      Array("G", "A", "Bb", "C", "D", "E", "F#"), //G melodic minor
      Array("C", "D", "E", "F", "G", "A", "B"), //C major
      Array("G", "A", "B", "C", "D", "E", "F#"), //G major
      Array("F", "G", "A", "Bb", "C", "D", "E"), //F major
      Array("F", "G", "Ab", "Bb", "C", "Db", "E"), //F harmonic minor
      Array("E", "F#", "G", "A", "B", "C", "D#") //E minor
    )
  }
}
