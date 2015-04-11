package perceptlab

import java.io.Closeable
import javax.sound.midi.MidiSystem

object SoundDisplay extends Closeable {
  private val Flute = 73
  private val StringEnsemble1 = 48
  private val VolumeControl = 7
  private val PanControl = 10
  private val MidPan = 64
  private val Cos45 = math.round((1.0 / math.sqrt(2)) * MidPan).toInt
  private val Pans = Seq[Int](0, Cos45, MidPan, MidPan + Cos45, MidPan * 2)
  private val synth = MidiSystem.getSynthesizer
  synth.open()
  private val sonarChannels = List.tabulate(5) { i =>
    val chan = synth.getChannels()(i)
    chan.programChange(0, Flute)
    chan.controlChange(PanControl, Pans(i))
    chan
  }.zipWithIndex

  def setSonarLevels(levels: Seq[Int]) =
    sonarChannels.foreach { case (chan, i) => chan.controlChange(VolumeControl, levels(i)) }

  def close() = synth.close()
}
