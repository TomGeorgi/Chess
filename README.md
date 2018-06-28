
[![Waffle.io - Columns and their card count](https://badge.waffle.io/TomGeorgi/Chess.svg?columns=all)](https://waffle.io/TomGeorgi/Chess) - Scrum

[![Build Status](https://travis-ci.org/TomGeorgi/Chess.svg?branch=Dev)](https://travis-ci.org/TomGeorgi/Chess) [![Coverage Status](https://coveralls.io/repos/github/TomGeorgi/Chess/badge.svg?branch=Dev)](https://coveralls.io/github/TomGeorgi/Chess?branch=Dev) - Dev

[![Build Status](https://travis-ci.org/TomGeorgi/Chess.svg?branch=Dev-TomGeorgi)](https://travis-ci.org/TomGeorgi/Chess) [![Coverage Status](https://coveralls.io/repos/github/TomGeorgi/Chess/badge.svg?branch=Dev-TomGeorgi)](https://coveralls.io/github/TomGeorgi/Chess?branch=Dev-TomGeorgi) - Tom

[![Build Status](https://travis-ci.org/TomGeorgi/Chess.svg?branch=Dev-RohloffLukas)](https://travis-ci.org/TomGeorgi/Chess) [![Coverage Status](https://coveralls.io/repos/github/TomGeorgi/Chess/badge.svg?branch=Dev-RohloffLukas)](https://coveralls.io/github/TomGeorgi/Chess?branch=Dev-RohloffLukas) - Lukas

# Chess in Scala 

This is a implementation of Chess in Scala for Software Engineering at the University of Applied Science HTWG Konstanz, Germany. (SS 18)

## Goals of this Project
* Learning Scala
* Learning Git
* Learning the test principles
* Using Scrum in a Team of two people
* Creating a TUI and a GUI and both are working prallel
* MVC Architecture
* Continious Integretion with Travis CI
* Design Patterns
  * Observer Pattern
  * Command Pattern (do, undo and redo)
  * Factory Pattern
  * Memento Pattern
* Components and Interfaces
* Dependency Injection
* File IO, Serialization in JSON and XML
* Logging

## Chess Rules
https://en.wikipedia.org/wiki/Rules_of_chess (All Rules)  
https://en.wikipedia.org/wiki/Chess (Some Rules, Notation of Recording which we used for the TUI input)  
https://de.wikipedia.org/wiki/Schach (In German)  
http://wiki-schacharena.de/index.php/Schachregeln_f%C3%BCr_Einsteiger (Standard rules for the individual figures in German)  

## Instructions
For the move rules of the individual figures use one of the above linked pages

* Instruction
  * TUI Command(s)
  * GUI Command(s)


* [For ingame help enter 'help' in the console]

* Leaving the Game
    * TUI
        * 'q' quits the Game
    * GUI
        * Edit -> Exit

* Start a new Game
    * TUI
        * 'n' for a Grid with names 'Player 1' and 'Player 2'
        * 'n name1 name2' for a Grid with names [name1 = Player 1] and [name2 = Player 2]
    * GUI
        * File -> New -> New Game
        * File -> New -> With name input -> name for Player 1 -> name for Player 2

* Start a new Game for testing
    * TUI
        * 'emp' for a Grid with names 'Player 1' and 'Player 2'
        * 'emp name1 name2' for a Grid with names [name1 = Player1] and [name2 = Player 2]
    * GUI
        * File -> Empty
    
* Make a turn (Row [A - H] - Column [1 - 8] -> 'A1' is the left Bottom 'H8' is the right Top
    * TUI
        * 'OldColumn OldRow newColumn newRow' -> If the move is valid, it will place the figure to the new Place
        * For example -> 'A 2 A 4' places The White Pawn from A2 to A4 (Double Jump)
    * GUI
        * Click on the Figure which you want to Move -> Click on the Place you want to place the Figure
        * For example -> Click on the White Pawn on 'A2' and try to Place it on 'A4' with a click

* Undo / Redo
    * TUI
        * 'z' -> undo the last move
        * 'y' -> redo the move
    * GUI
        * Edit -> Undo
        * Edit -> Redo

* Save / Load
    * TUI
        * 's' -> Saves the Game to Chess.[json/xml]
        * 'l' -> Loads the last Game from Chess.[json/xml]
    * GUI
        * File -> Save
        * File -> Load

* For Testing [Warning this Instrutcions are only for testing a move or a new rule!] [Admin Command]
    * TUI
        * 'set Column Row Figure Color' -> This Command will set a new Figure to the entered Place
        * For example -> 'set A 2 Pawn Black' is setting a new Pawn to A 2 with the color Black
        * 'set Column Row _ _' -> This Command will clear the Figure from the entered Place
        * For example -> 'set A 2 _ _' is clearing the Black Pawn from the example above
    * GUI
        * [Activate the Test-Mode]:
        * File -> Admin -> Set will popped up
        * [Placing a new Figure]
        * Set -> Figure[Pawn, Rook, Bishop, Knight, Queen, King] -> Color[Black, White] -> Click on the desired location
        * Set -> None -> Click the Figure you want to delete -> [Done with delete] -> None

## Game Over
The game is over when the king of the opposing player is in check and this can no longer free himself with the next turn.

* For Example
    * [Player 1, Color = White] is in Check -> next turn will be in check aswell -> Checkmate -> Player 2 Wins
    * [Player 2, Color = Black] is in Check -> next turn will be in check aswell -> Checkmate -> Player 1 Wins
    * [Player 1, Color = White] is in Check -> next turn will be out of in check -> Player 2 is at turn
    * [Player 2, Color = Black] is in Check -> next turn will be out of in check -> Player 1 is at turn

