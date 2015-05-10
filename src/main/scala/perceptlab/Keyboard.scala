package perceptlab

import java.awt.event.{KeyEvent, KeyListener}
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JFrame

class Keyboard(frame: JFrame) extends KeyListener {
  frame.addKeyListener(this)

  private case class Key(code: Int) {
    private val pressedFlag = new AtomicBoolean(false)

    def checkPressed(pressedCode: Int) =
      if (pressedCode == code) pressedFlag.set(true)

    def checkReleased(pressedCode: Int) =
      if (pressedCode == code) pressedFlag.set(false)

    def isPressed = pressedFlag.get
  }

  private val upKey = Key(KeyEvent.VK_UP)
  private val leftKey = Key(KeyEvent.VK_LEFT)
  private val rightKey = Key(KeyEvent.VK_RIGHT)
  private val escapeKey = Key(KeyEvent.VK_ESCAPE)
  private val keys = List(upKey, leftKey, rightKey, escapeKey)

  def isUpPressed = upKey.isPressed
  def isLeftPressed = leftKey.isPressed
  def isRightPressed = rightKey.isPressed
  def isEscapePressed = escapeKey.isPressed

  def keyPressed(keyEvent: KeyEvent) =
    keys.foreach(_.checkPressed(keyEvent.getKeyCode))

  def keyReleased(keyEvent: KeyEvent) =
    keys.foreach(_.checkReleased(keyEvent.getKeyCode))

  def keyTyped(nativeKeyEvent: KeyEvent) = {}
}
