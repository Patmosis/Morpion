package morpion

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


class MainScene (stage: Stage, app: Runner) {
  var scene: Scene = null
  var mainLayout: BorderPane = null
	var crossLabel = new Label("X")
	var circleLabel = new Label("O")
  
  initialize
  
  def initialize() = {
    mainLayout = new BorderPane
    
    var vbCross: VBox = new VBox
		crossLabel setId "currentPlayer"
		vbCross setId "vbCross"
		vbCross getChildren() add crossLabel
		vbCross setAlignment Pos.CENTER
		mainLayout setLeft vbCross
		
		var vbCircle: VBox = new VBox
		circleLabel setId "player"
		vbCircle setId "vbCircle"
		vbCircle getChildren() add circleLabel
		vbCircle setAlignment Pos.CENTER
		mainLayout setRight vbCircle
		
		var vbBoard: VBox = new VBox
		vbBoard setPadding new Insets(25, 0, 25, 0)

    var board: Board = new Board
    board addObserver this
    vbBoard getChildren() add board
		
		mainLayout setCenter vbBoard
    
    scene = new Scene(mainLayout, 750, 500)
    scene getStylesheets() add(app.getClass().getResource("style.css").toExternalForm())

		stage setScene scene 
		stage show
  }
  
  def update(arg0: Board, arg1: Any) = {
		var player: String = null;
		arg1 match {
		  case player: String => 
		    if (player.equals("X")) {
  				crossLabel.setId("currentPlayer");
  				circleLabel.setId("player");
  			} else {
  				crossLabel.setId("player");
  				circleLabel.setId("currentPlayer");
  			}
		  case bool: Boolean =>
		    if (!(bool.booleanValue())) {
  				var alert = new Alert(AlertType.INFORMATION);
  				alert.setTitle("You both lose!");
  				alert.setHeaderText("There is no empty square left.");
  				alert.setContentText("Game will be initialized again.");
  				alert.showAndWait();
  			}
  			initialize();
	  }
	}
	
}