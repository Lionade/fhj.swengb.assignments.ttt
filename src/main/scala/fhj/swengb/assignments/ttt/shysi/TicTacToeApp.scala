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
    // Test Map
    val m: Map[TMove, Player] = Map(TopLeft -> PlayerA, TopCenter -> PlayerA, TopRight -> PlayerA)
   // val m: Map[TMove, Player] = Map(TopLeft -> PlayerA, TopCenter -> PlayerB, TopRight -> PlayerA,
                     //               MiddleLeft -> PlayerB, MiddleCenter -> PlayerA, MiddleRight -> PlayerB,
                     //               BottomLeft -> PlayerB, BottomCenter -> PlayerA, BottomRight -> PlayerB)
    val t = TicTacToe(m)
    //println(t.asString())
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

  @FXML var L_TopLeft: Button = _
  @FXML var L_TopCenter: Button = _
  @FXML var L_TopRight: Button = _
  @FXML var L_MiddleLeft: Button = _
  @FXML var L_MiddleCenter: Button = _
  @FXML var L_MiddleRight: Button = _
  @FXML var L_BottomLeft: Button = _
  @FXML var L_BottomCenter: Button = _
  @FXML var L_BottomRight: Button = _

  @FXML var L_Player: Label = _
  @FXML var P_DebugPane: ScrollPane = _
  @FXML var T_DebugArea: TextArea = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }

  //Shows informations about the current game
  def debug(): Unit = {
    if(t.nextPlayer == PlayerA) L_Player.setText("PlayerA") else L_Player.setText("PlayerB")
    outstring += t.asString()
    T_DebugArea.setText(outstring)
  }

  def submit(b: Button, move: TMove): Unit ={
    if(t.remainingMoves.contains(move)){
      if(t.nextPlayer == PlayerA) b.setText("O") else b.setText("X")
      t = t.turn(move, t.nextPlayer)
      // Check if gameover
      debug()
    }
  }

  // Resets the game
  def reset(): Unit ={
    t = TicTacToe(Map())
    T_DebugArea.clear()
  }

  @FXML def onTopLeft(): Unit = submit(L_TopLeft, TopLeft)
  @FXML def onTopCenter(): Unit = submit(L_TopCenter, TopCenter)
  @FXML def onTopRight(): Unit = submit(L_TopRight, TopRight)
  @FXML def onMiddleLeft(): Unit = submit(L_MiddleLeft, MiddleLeft)
  @FXML def onMiddleCenter(): Unit = submit(L_MiddleCenter, MiddleCenter)
  @FXML def onMiddleRight(): Unit = submit(L_MiddleRight, MiddleRight)
  @FXML def onBottomLeft(): Unit = submit(L_BottomLeft, BottomLeft)
  @FXML def onBottomCenter(): Unit = submit(L_BottomCenter, BottomCenter)
  @FXML def onBottomRight(): Unit = submit(L_BottomRight, BottomRight)

}