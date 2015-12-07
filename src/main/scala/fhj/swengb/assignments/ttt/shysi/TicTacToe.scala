package fhj.swengb.assignments.ttt.shysi

import scala.collection.Set

/**
  * models the different moves the game allows
  *
  * each move is made by either player a or player b.
  */
sealed trait TMove {
  def idx: Int
}

case object TopLeft extends TMove {
  override def idx: Int = 0
}

case object TopCenter extends TMove {
  override def idx: Int = 1
}

case object TopRight extends TMove {
  override def idx: Int = 2
}

case object MiddleLeft extends TMove {
  override def idx: Int = 3
}

case object MiddleCenter extends TMove {
  override def idx: Int = 4
}

case object MiddleRight extends TMove {
  override def idx: Int = 5
}

case object BottomLeft extends TMove {
  override def idx: Int = 6
}

case object BottomCenter extends TMove {
  override def idx: Int = 7
}

case object BottomRight extends TMove {
  override def idx: Int = 8
}


/**
  * for a tic tac toe game, there are two players, player A and player B
  */
sealed trait Player

case object PlayerA extends Player

case object PlayerB extends Player

object TicTacToe {

  /**
    * creates an empty tic tac toe game
    * @return
    */
  def apply(): TicTacToe = {
    TicTacToe(null)
  }

  /**
    * For a given tic tac toe game, this function applies all moves to the game.
    * The first element of the sequence is also the first move.
    *
    * @param t
    * @param moves
    * @return
    */
  def play(t: TicTacToe, moves: Seq[TMove]): TicTacToe = {
    ???
  }

  /**
    * creates all possible games.
    * @return
    */
  def mkGames(): Map[Seq[TMove], TicTacToe] = {
    // Rekusiver aufruf von nextGames?
    ???
  }

}

/**
  * Models the well known tic tac toe game.
  *
  * The map holds the information which player controls which field.
  *
  * The nextplayer parameter defines which player makes the next move.
  */
case class TicTacToe(moveHistory: Map[TMove, Player],
                     nextPlayer: Player = PlayerA) {

  /**
    * outputs a representation of the tic tac toe like this:
    *
    * |---|---|---|
    * | x | o | x |
    * |---|---|---|
    * | o | x | x |
    * |---|---|---|
    * | x | o | o |
    * |---|---|---|
    *
    *
    * @return
    */
  // Automatisch leer machen und falls Wert vorhanden den entsprechenden Index-Wert ändern
  // 16,20,24
  // 44,48,52
  // 72,76,80
  def asString(): String = {
    val indexs = Map (0->16, 1->20, 2->24,
                      3->44, 4->48, 5->52,
                      6->72, 7->76, 8->80)
    var s: String =
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n"


    for((k,v) <- moveHistory){
      if(v == PlayerA){
        s = s.updated(indexs(k.idx),"O").mkString
      }
      else{
        s = s.updated(indexs(k.idx),"X").mkString
      }
    }
    s
  }

  /**
    * is true if the game is over.
    *
    * The game is over if either of a player wins or there is a draw.
    */
  val gameOver : Boolean = {
    if(winner == None) false
    else true
  }

  /**
    * the moves which are still to be played on this tic tac toe.
    */
  val remainingMoves: Set[TMove] = {
    // Diff moveHistory with Set (all moves)
    val set: Set[TMove] = Set(TopLeft, TopCenter, TopRight,
      MiddleLeft, MiddleCenter, MiddleRight,
      BottomLeft, BottomCenter, BottomRight)
    set -- moveHistory.keySet
  }

  /**
    * given a tic tac toe game, this function returns all
    * games which can be derived by making the next turn. that means one of the
    * possible turns is taken and added to the set.
    */
  // Player wird nicht gewechselt
  // turn-function anstatt selber
  lazy val nextGames: Set[TicTacToe] = {
    var set: Set[TicTacToe] = Set(null)
    for(t <- remainingMoves){
      if (nextPlayer == PlayerA){
        set += TicTacToe(moveHistory + (t -> PlayerA), PlayerB)
      }
      else{
        set += TicTacToe(moveHistory + (t -> PlayerB), PlayerA)
      }
    }
    set
  }

  /**
    * Either there is no winner, or PlayerA or PlayerB won the game.
    *
    * The set of moves contains all moves which contributed to the result.
    */
  def winner: Option[(Player, Set[TMove])] = {
    // zuerste überprüfen ob positionen besetzt dann if(moveHistory(set(0)) == moveHistory(set(1)) = moveHistory(set(2)))
    //
    if(moveHistory.getOrElse(diag1(0), "").equals(moveHistory.getOrElse(diag1(1), None)) &&
      moveHistory.getOrElse(diag1(1), None).equals(moveHistory.getOrElse(diag1(2), None)))
      Some((moveHistory(diag1.head), diag1.toSet))

    else if(moveHistory.getOrElse(diag2(0), "").equals(moveHistory.getOrElse(diag2(1), None)) &&
      moveHistory.getOrElse(diag2(1), None).equals(moveHistory.getOrElse(diag2(2), None)))
      Some((moveHistory(diag2.head), diag2.toSet))

    else if(moveHistory.getOrElse(horiline1(0), "").equals(moveHistory.getOrElse(horiline1(1), None)) &&
      moveHistory.getOrElse(horiline1(1), None).equals(moveHistory.getOrElse(horiline1(2), None)) )
      Some((moveHistory(horiline1.head), horiline1.toSet))

    else if(moveHistory.getOrElse(horiline2(0), "").equals(moveHistory.getOrElse(horiline2(1), None)) &&
      moveHistory.getOrElse(horiline2(1), None).equals(moveHistory.getOrElse(horiline2(2), None)) )
      Some((moveHistory(horiline2.head), horiline2.toSet))

    else if(moveHistory.getOrElse(horiline3(0), "").equals(moveHistory.getOrElse(horiline3(1), None)) &&
      moveHistory.getOrElse(horiline3(1), None).equals(moveHistory.getOrElse(horiline3(2), None)) )
      Some((moveHistory(horiline3.head), horiline3.toSet))

    else if(moveHistory.getOrElse(vertline1(0), "").equals(moveHistory.getOrElse(vertline1(1), None)) &&
      moveHistory.getOrElse(vertline1(1), None).equals(moveHistory.getOrElse(vertline1(2), None)) )
      Some((moveHistory(vertline1.head), vertline1.toSet))

    else if(moveHistory.getOrElse(vertline2(0), "").equals(moveHistory.getOrElse(vertline2(1), None)) &&
      moveHistory.getOrElse(vertline2(1), None).equals(moveHistory.getOrElse(vertline2(2), None)) )
      Some((moveHistory(vertline2.head), vertline2.toSet))

    else if(moveHistory.getOrElse(vertline3(0), "").equals(moveHistory.getOrElse(vertline3(1), None)) &&
      moveHistory.getOrElse(vertline3(1), None).equals(moveHistory.getOrElse(vertline3(2), None)) )
      Some((moveHistory(vertline3.head), vertline3.toSet))

      //Draw
    else if(remainingMoves.isEmpty){
      Some((null, moveHistory.keySet))
    }

    else None
  }

  /**
    * returns a copy of the current game, but with the move applied to the tic tac toe game.
    *
    * @param p to be played
    * @param player the player
    * @return
    */
  def turn(p: TMove, player: Player): TicTacToe = {
    moveHistory + (p -> player)
    this
  }

  lazy val diag1: List[TMove] = List(TopLeft, MiddleCenter, BottomRight)
  lazy val diag2: List[TMove] = List(TopRight, MiddleCenter, BottomLeft)
  lazy val horiline1: List[TMove] = List(TopLeft, TopCenter, TopRight)
  lazy val horiline2: List[TMove] = List(MiddleLeft, MiddleCenter, MiddleRight)
  lazy val horiline3: List[TMove] = List(BottomLeft, BottomCenter, BottomRight)
  lazy val vertline1: List[TMove] = List(TopLeft, MiddleLeft, BottomLeft)
  lazy val vertline2: List[TMove] = List(TopCenter, MiddleCenter, BottomCenter)
  lazy val vertline3: List[TMove] = List(TopRight, MiddleRight, BottomRight)


}


