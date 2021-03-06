package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}

import scala.collection.JavaConverters._

class ChordTest extends FunSpec with Matchers {
  it("understands a G major") {
    val gMajor = new Chord(Note.G, ChordType.Major)
    gMajor.getTonic shouldBe Note.G
    gMajor.getIntervals.asScala shouldBe Seq(0, 4, 3)
  }

  it("understands a B minor") {
    val gMajor = new Chord(Note.B, ChordType.m)
    gMajor.getTonic shouldBe Note.B
    gMajor.getIntervals.asScala shouldBe Seq(0, 3, 4)
  }
}
