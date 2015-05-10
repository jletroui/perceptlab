package perceptlab

import java.io.Closeable
import javax.sound.midi.MidiSystem
import Game._

object SoundDisplay extends Closeable {
  private val C6 = 84
  private val Flute = 73
  private val StringEnsemble1 = 48
  private val PanControl = 10
  private val MidPan = 64
  private val Pans = SonarAngleOffsets.map(angle => (MidPan - MidPan * math.sin(angle)).toInt)
  private val synth = MidiSystem.getSynthesizer
  synth.open()
  private val sonarChannels = List.tabulate(SonarNumber)(Note(_))

  def setSonarLevels(levels: Seq[Int]) =
    sonarChannels.foreach { note =>
      note.adjustVolume(levels(note.sonarIndex))
    }

  def close() = {
    sonarChannels.foreach(_.close())
    synth.close()
  }

  private case class Note(sonarIndex: Int) {
    private val note = C6 - sonarIndex * 4
    private val chan = synth.getChannels()(sonarIndex)
    chan.programChange(0, Flute)
    chan.controlChange(PanControl, Pans(sonarIndex))
    chan.noteOn(note, 0) // Starts at C6, and then go third by third down for each sonar.
    private var previousVolume = 0

    def adjustVolume(newVolume: Int) =
      if (newVolume != previousVolume) {
        chan.noteOff(note)
        chan.noteOn(note, newVolume)
        previousVolume = newVolume
      }

    def close() =
      chan.allSoundOff()
  }
}
