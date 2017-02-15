package morpion

import javafx.scene.control.Button;

class Square extends Button {
  
  var content = ""
  this setId "square"
	this setMinSize(150, 150)
	
	def set(who: String) = {
		if (content.equals("")) {
			content = who;
			this setText who
		}
	}

	def getContent() = {
		content
	}
  
}