package quoridorFE;


// Main import
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.image.Image;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

public class Viewer extends Application {
	
    public static void main(String[] args) {
        Application.launch(args);
    } 

		Group root = new Group();
	    Scene theScene = new Scene( root );
	    theStage.setScene( theScene );
	         
	    Canvas canvas = new Canvas( 400, 200 );
	    root.getChildren().add( canvas );
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	         
	    gc.setFill( Color.RED );
	    gc.setStroke( Color.BLACK );
	    gc.setLineWidth(2);
	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
	    gc.setFont( theFont );
	    gc.fillText( "Hello, World!", 60, 50 );
	    gc.strokeText( "Hello, World!", 60, 50 );
	    
	    // Create an image that is found in src/main/resources/application/image.png
	    Image earth = new Image(getClass().getClassLoader().getResourceAsStream("application/earth.png"));
	    gc.drawImage( earth, 180, 100 );
	}
}