package fhj.swengb.assignments.ttt.shysi

import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.{Label, TextField}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.util.control.NonFatal

/**
  * Implement here your TicTacToe JavaFX App.
  */

object ttt {
  def main (args: Array[String]){
    // Test Map
    val m: Map[TMove, Player] = Map(TopLeft -> PlayerA, TopCenter -> PlayerB, TopRight -> PlayerA)
    val t = TicTacToe(m)
    println(t.asString())
   // Application.launch(classOf[ttt], args: _*)
  }
}

class ttt extends javafx.application.Application{

  val Fxml = "/main/resources/fhj/swengb/assignments/ttt/TicTacToeApp.fxml"
  val Css = "/main/resources/fhj/swengb/assignments/ttt/TicTacToeApp.css"

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
  @FXML var L_TopLeft: Label = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }

  @FXML def testClick(): Unit = L_TopLeft.setText("O")
}