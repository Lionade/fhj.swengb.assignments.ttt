package fhj.swengb.assignments.ttt.shysi

import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.{Label, Button, ScrollPane, TextArea}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.collection.Set
import scala.util.control.NonFatal

/**
  * Implement here your TicTacToe JavaFX App.
  */

object ttt {
  def main (args: Array[String]){
    Application.launch(classOf[ttt], args: _*)
  }
}

class ttt extends javafx.application.Application{

  val Fxml = "/fhj/swengb/assignments/ttt/TicTacToeApp.fxml"
  val Css = "/fhj/swengb/assignments/ttt/TicTacToeApp.css"

  def mkFxmlLoader(fxml: String): FXMLLoader = {
    new FXMLLoader(getClass.getResource(fxml))
  }

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("TicTacToe")
      setSkin(stage, Fxml, Css)
      stage.show()
      stage.setMinWidth(stage.getWidth)
      stage.setMinHeight(stage.getHeight)
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

  def setSkin(stage: Stage, fxml: String, css: String): Boolean = {
    val scene = new Scene(mkFxmlLoader(fxml).load[Parent]())
    stage.setScene(scene)
    stage.getScene.getStylesheets.clear()
    stage.getScene.getStylesheets.add(css)
  }

}

class tttController extends Initializable{
  var t = TicTacToe()
  var outstring: String = ""

  @FXML var B_TopLeft: Button = _
  @FXML var B_TopCenter: Button = _
  @FXML var B_TopRight: Button = _
  @FXML var B_MiddleLeft: Button = _
  @FXML var B_MiddleCenter: Button = _
  @FXML var B_MiddleRight: Button = _
  @FXML var B_BottomLeft: Button = _
  @FXML var B_BottomCenter: Button = _
  @FXML var B_BottomRight: Button = _

  @FXML var L_Player: Label = _
  @FXML var P_DebugPane: ScrollPane = _
  @FXML var T_DebugArea: TextArea = _
  @FXML var L_GameOver: Label = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    L_Player.setText("PlayerA")
  }

  //Shows informations about the current game
  def debug(): Unit = {
    if(t.nextPlayer == PlayerA) L_Player.setText("PlayerA") else L_Player.setText("PlayerB")
    outstring += t.asString()
    T_DebugArea.setText(outstring)
  }

  def submit(b: Button, move: TMove): Unit ={
    if(t.remainingMoves.contains(move) && !t.gameOver){
      if(t.nextPlayer == PlayerA) b.setText("O") else b.setText("X")
      t = t.turn(move, t.nextPlayer)
      // Check if gameover
      if(t.gameOver) L_GameOver.setText("Game Over")
      debug()
    }
  }

  // Resets the game
  def reset(): Unit ={
    // Noch random machen
    t = TicTacToe(Map(), PlayerA)
    T_DebugArea.clear(); outstring = ""
    clearbuttons()
  }

  def clearbuttons(): Unit = {
    B_TopLeft.setText(""); B_TopCenter.setText(""); B_TopRight.setText("")
    B_BottomLeft.setText(""); B_BottomCenter.setText(""); B_BottomRight.setText("")
    B_MiddleLeft.setText(""); B_MiddleCenter.setText(""); B_MiddleRight.setText("")
  }

  @FXML def onNewGame(): Unit = {reset(); L_GameOver.setText("")}

  @FXML def onTopLeft(): Unit = submit(B_TopLeft, TopLeft)
  @FXML def onTopCenter(): Unit = submit(B_TopCenter, TopCenter)
  @FXML def onTopRight(): Unit = submit(B_TopRight, TopRight)
  @FXML def onMiddleLeft(): Unit = submit(B_MiddleLeft, MiddleLeft)
  @FXML def onMiddleCenter(): Unit = submit(B_MiddleCenter, MiddleCenter)
  @FXML def onMiddleRight(): Unit = submit(B_MiddleRight, MiddleRight)
  @FXML def onBottomLeft(): Unit = submit(B_BottomLeft, BottomLeft)
  @FXML def onBottomCenter(): Unit = submit(B_BottomCenter, BottomCenter)
  @FXML def onBottomRight(): Unit = submit(B_BottomRight, BottomRight)

}