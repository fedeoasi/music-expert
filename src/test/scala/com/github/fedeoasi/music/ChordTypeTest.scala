package com.github.fedeoasi.music

import org.scalatest.{FunSpec, Matchers}
import ChordType._

class ChordTypeTest extends FunSpec with Matchers {
  it("knows major chords") {
    val chords = Seq(Major, MajorNinth, MajorSeventh, MajorThirteenth)
    chords.foreach { chord =>
      chord.isMajor shouldBe true
      chord.isDominant shouldBe false
      chord.isMinor shouldBe false
      chord.isSemiDiminished shouldBe false
    }
  }

  it("knows minor chords") {
    val chords = Seq(m, m6, m7, m9, m13)
    chords.foreach { chord =>
      chord.isMajor shouldBe false
      chord.isDominant shouldBe false
      chord.isMinor shouldBe true
      chord.isSemiDiminished shouldBe false
    }
  }

  it("knows dominant chords") {
    val chords = Seq(Seventh, SeventhSharp5, Ninth, Thirteenth)
    chords.foreach { chord =>
      chord.isMajor shouldBe true
      chord.isDominant shouldBe true
      chord.isMinor shouldBe false
      chord.isSemiDiminished shouldBe false
    }
  }

  it("knows semi diminished chords") {
    val chords = Seq(m7Flat5, m9Flat5)
    chords.foreach { chord =>
      chord.isMajor shouldBe false
      chord.isDominant shouldBe false
      chord.isMinor shouldBe true
      chord.isSemiDiminished shouldBe true
    }
  }
}
