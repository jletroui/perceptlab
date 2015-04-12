package perceptlab

import java.io.Closeable
import javax.sound.midi.MidiSystem
import Game._

object SoundDisplay extends Closeable {
  private val C6 = 84
  private val Flute = 73
  private val StringEnsemble1 = 48
  private val VolumeControl = 7
  private val PanControl = 10
  private val MidPan = 64
  private val Pans = SonarAngleOffsets.map(angle => (MidPan - MidPan * math.sin(angle)).toInt)
  private val synth = MidiSystem.getSynthesizer
  synth.open()
  private val sonarChannels = List.tabulate(SonarNumber) { i =>
    val chan = synth.getChannels()(i)
    chan.programChange(0, Flute)
    chan.controlChange(PanControl, Pans(i))
    chan.noteOn(C6 - i * 4, 0) // Starts at C6, and then go third by third down for each sonar.
    chan
  }.zipWithIndex

  def setSonarLevels(levels: Seq[Int]) =
    sonarChannels.foreach { case (chan, i) => chan.controlChange(VolumeControl, levels(i)) }

  def close() =
    synth.close()
}
