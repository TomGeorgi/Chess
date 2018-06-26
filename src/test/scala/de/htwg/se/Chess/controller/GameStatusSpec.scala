package de.htwg.se.Chess.controller

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import de.htwg.se.Chess.controller.controllerComponent.GameStatus

@RunWith(classOf[JUnitRunner])
class GameStatusSpec extends WordSpec with Matchers {

  "A GameStatus" when {
    "used" should {
      "transforming string" in {
        GameStatus.fromString("") should be(None)
        GameStatus.fromString("MOVE_NOT_VALID") should be(Some(GameStatus.MOVE_NOT_VALID))
        GameStatus.fromString("CHECK") should be(Some(GameStatus.CHECK))
        GameStatus.fromString("CHECK_MATE") should be(Some(GameStatus.CHECK_MATE))
      }
    }
  }
}
