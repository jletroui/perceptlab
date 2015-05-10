package perceptlab

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Dimension, Graphics}
import java.util.concurrent.atomic.AtomicReference
import javax.swing.{Timer, JFrame, JPanel}
import math._

class PerceptLabUI(player: AtomicReference[Player]) extends Runnable {
  private val Size = 400
  private val CircleRadius = 5
  private val Diameter = CircleRadius * 2
  private val ArrowLength = 10
  private val mainFrame = new JFrame("Perceptlab")
  private val timer = new Timer(20, new ActionListener {
    override def actionPerformed(e: ActionEvent) =
      mainFrame.repaint()
  })
  val keyboard = new Keyboard(mainFrame)

  override def run() = {
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    mainFrame.setLayout(new BorderLayout())
    mainFrame.setPreferredSize(new Dimension(Size, Size))
    mainFrame.setLocationByPlatform(true)

    val drawingPanel = new JPanel() {
      override def paintComponent(g: Graphics) {
        super.paintComponent(g)
        val p = player.get
        g.drawOval(
          p.integerPosition.x - CircleRadius,
          p.integerPosition.y - CircleRadius,
          Diameter,
          Diameter
        )
        g.drawLine(
          p.integerPosition.x,
          p.integerPosition.y,
          round(p.location.x + ArrowLength * cos(p.direction)).toInt,
          round(p.location.y - ArrowLength * sin(p.direction)).toInt
        )
      }
    }

    drawingPanel.setSize(Size, Size)

    mainFrame.getContentPane.add(drawingPanel, BorderLayout.CENTER)
    mainFrame.pack()
    mainFrame.setVisible(true)

    timer.start()
  }
}
