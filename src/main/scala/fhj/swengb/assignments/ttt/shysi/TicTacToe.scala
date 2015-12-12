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
    TicTacToe(Map())
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
    for(x <- moves){
      if(t.nextPlayer == PlayerA) t.turn(x, PlayerB)
      else t.turn(x, PlayerA)
    }
    t
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
   * is true if the game is over.
   *
   * The game is over if either of a player wins or there is a draw.
   */
  val gameOver : Boolean = {
    if(remainingMoves.isEmpty) true
    else if(winner != None) true
    else false
  }

  /**
    * given a tic tac toe game, this function returns all
    * games which can be derived by making the next turn. that means one of the
    * possible turns is taken and added to the set.
    */
  // Player wird nicht gewechselt
  lazy val nextGames: Set[TicTacToe] = {
    var set: Set[TicTacToe] = Set(null)
    for(t <- remainingMoves){
      if (nextPlayer == PlayerA){
        set += turn(t, PlayerB)
      }
      else{
        set += turn(t, PlayerA)
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


    if(win(diag1)) Some((moveHistory(diag1.head), diag1.toSet))
    else if(win(diag2)) Some((moveHistory(diag2.head), diag2.toSet))
    else if(win(horiline1)) Some((moveHistory(horiline1.head), horiline1.toSet))
    else if(win(horiline2)) Some((moveHistory(horiline2.head), horiline2.toSet))
    else if(win(horiline3)) Some((moveHistory(horiline3.head), horiline3.toSet))
    else if(win(vertline1)) Some((moveHistory(vertline1.head), vertline1.toSet))
    else if(win(vertline2)) Some((moveHistory(vertline2.head), vertline2.toSet))
    else if(win(vertline3)) Some((moveHistory(vertline3.head), vertline3.toSet))

    // Kein Winner
    else None
  }

  // Testing if row has a winner
  def win(list: List[TMove]): Boolean = {
    if(moveHistory.contains(list(0)) && moveHistory.contains(list(1)) && moveHistory.contains(list(2))) {
      if (moveHistory.getOrElse(list(0), "").equals(moveHistory.getOrElse(list(1), None)) &&
        moveHistory.getOrElse(list(1), None).equals(moveHistory.getOrElse(list(2), None))) {
        true
      }
      else false
    }
    else false
  }

  /**
    * returns a copy of the current game, but with the move applied to the tic tac toe game.
    *
    * @param p to be played
    * @param player the player
    * @return
    */
  def turn(p: TMove, player: Player): TicTacToe = {
    if(player == PlayerA) TicTacToe(moveHistory + (p -> player),PlayerB)
    else TicTacToe(moveHistory + (p -> player),PlayerA)
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


