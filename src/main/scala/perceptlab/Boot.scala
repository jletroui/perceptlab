package perceptlab

import javax.sound.midi.{MidiChannel, Synthesizer, MidiSystem}

import scala.io.StdIn

object Boot extends App {
  private val C4 = 60
  private val C5 = 72
  private val E5 = 76
  private val MaxVolume = 127
  private val Flute = 73
  private val StringEnsemble1 = 48
  private val PanControl = 10

  using(MidiSystem.getSynthesizer) { synth =>
    synth.open()

    val chan1 = synth.getChannels()(0)

    chan1.programChange(0, Flute)
    chan1.controlChange(PanControl, 0)
    chan1.noteOn(C5, MaxVolume)
    chan1.noteOn(C5, 40) // don't change anything


    val chan2 = synth.getChannels()(1)
    chan2.programChange(0, Flute)
    chan2.controlChange(PanControl, 127)
    chan2.noteOn(E5, MaxVolume)

    StdIn.readLine()

    chan1.noteOff(C5)
    chan2.noteOff(E5)



  }

  def browseInstruments(synth: Synthesizer, chan: MidiChannel): Unit = {
    for(i <- 1 to 73) {
      val instrument = synth.getDefaultSoundbank.getInstruments()(i)
      println(s"Playing: ${instrument.getName} [$i]")
      chan.programChange(0, i)
      chan.noteOn(C5, MaxVolume)
      StdIn.readLine()

      chan.noteOff(C5)
    }

  }

}
