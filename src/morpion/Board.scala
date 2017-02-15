package morpion

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

class Board extends GridPane {
	
	var observers = Array[MainScene]()
	
	var board = Array ofDim[Square](3,3)
	var who = "X"
	
	initialize

	def initialize() = {
		this setHgap 0
    this setVgap 0

    for (i: Int <- 0 to 2) {
    	for (j: Int <- 0 to 2) {
    		board(i)(j) = new Square
    		board(i)(j) setOnAction(new EventHandler[ActionEvent] {
          override def handle(event: ActionEvent) = {
          	var square: Square = null
          	event.getSource() match {
              case square : Square => {
                var content: String = square getContent()
                square set who
                if (content equals "") {
                  if (who equals "X") {
                  	who = "O"
                  } else {
                  	who = "X"
                  }
                  notifyObservers(who)
                }
              }
            }
            checkWinning()
          }
    		})
    		this add(board(i)(j),i,j)
    	}
    }
	}
	
	def getWho() = {
		who
	}
	
	def addObserver(o: MainScene) = {
		observers :+ o
	}
	
	def notifyObservers(obj: Any) = {
	  for (o <- observers) {
	    o update(this, obj)
	  }
	}
	
	def checkWinning() = {
		var emptySquare = false
		for (i: Int <- 0 to 2) {
    	for (j: Int <- 0 to 2) {
    		if (board(i)(j) getContent() equals "") {
    			emptySquare = true
    		}
    	}
    }
		var winner = ""
		if ((board(0)(0).getContent().equals(board(0)(1).getContent()) && board(0)(1).getContent().equals(board(0)(2).getContent()) && !board(0)(0).getContent().equals(""))
				|| (board(0)(0).getContent().equals(board(1)(0).getContent()) && board(1)(0).getContent().equals(board(2)(0).getContent()) && !board(2)(0).getContent().equals(""))) {
			winner = board(0)(0) getContent()
			
		} else if ((board(2)(0).getContent().equals(board(2)(1).getContent()) && board(2)(1).getContent().equals(board(2)(2).getContent()) && !board(2)(2).getContent().equals(""))
				|| (board(0)(2).getContent().equals(board(1)(2).getContent()) && board(1)(2).getContent().equals(board(2)(2).getContent()) && !board(2)(2).getContent().equals(""))) {
			winner = board(2)(2) getContent()
			
		} else if (board(1)(0).getContent().equals(board(1)(1).getContent()) && board(1)(1).getContent().equals(board(1)(2).getContent()) && !board(1)(2).getContent().equals("")) {
			winner = board(1)(0) getContent()
			
		} else if (board(0)(1).getContent().equals(board(1)(1).getContent()) && board(1)(1).getContent().equals(board(2)(1).getContent()) && !board(2)(1).getContent().equals("")) {
			winner = board(0)(1) getContent()
			
		} else if (board(0)(0).getContent().equals(board(1)(1).getContent()) && board(1)(1).getContent().equals(board(2)(2).getContent()) && !board(2)(2).getContent().equals("")) {
			winner = board(1)(1).getContent()
			
		} else if (board(0)(2).getContent().equals(board(1)(1).getContent()) && board(1)(1).getContent().equals(board(2)(0).getContent()) && !board(2)(0).getContent().equals("")) {
			winner = board(1)(1).getContent()
			
		} else if (!emptySquare) {
			notifyObservers(false)
		}
		
		if (!(winner.equals(""))) {
			var alert = new Alert(AlertType.INFORMATION)
			alert setTitle "We have a winner!"
			alert setHeaderText winner+" won the game!"
			alert setContentText "Game will be initialized again."
			alert showAndWait
			
			notifyObservers(true)
		}
	}
	
}