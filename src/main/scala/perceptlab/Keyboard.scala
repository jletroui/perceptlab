package perceptlab

import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean

import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.{NativeKeyEvent, NativeKeyListener}

import scala.util.Try

object Keyboard extends NativeKeyListener with Closeable {
  GlobalScreen.registerNativeHook()
  GlobalScreen.getInstance().addNativeKeyListener(this)

  private case class Key(code: Int) {
    private val pressedFlag = new AtomicBoolean(false)

    def checkPressed(pressedCode: Int) =
      if (pressedCode == code) pressedFlag.set(true)

    def checkReleased(pressedCode: Int) =
      if (pressedCode == code) pressedFlag.set(false)

    def isPressed = pressedFlag.get
  }

  private val upKey = Key(NativeKeyEvent.VK_UP)
  private val leftKey = Key(NativeKeyEvent.VK_LEFT)
  private val rightKey = Key(NativeKeyEvent.VK_RIGHT)
  private val escapeKey = Key(NativeKeyEvent.VK_ESCAPE)
  private val keys = List(upKey, leftKey, rightKey, escapeKey)

  def isUpPressed = upKey.isPressed
  def isLeftPressed = leftKey.isPressed
  def isRightPressed = rightKey.isPressed
  def isEscapePressed = escapeKey.isPressed

  override def nativeKeyPressed(nativeKeyEvent: NativeKeyEvent) =
    keys.foreach(_.checkPressed(nativeKeyEvent.getKeyCode))

  override def nativeKeyReleased(nativeKeyEvent: NativeKeyEvent) =
    keys.foreach(_.checkReleased(nativeKeyEvent.getKeyCode))

  override def nativeKeyTyped(nativeKeyEvent: NativeKeyEvent) = {}

  override def close() = {
    Try(GlobalScreen.getInstance().removeNativeKeyListener(this))
    GlobalScreen.unregisterNativeHook()
  }
}
