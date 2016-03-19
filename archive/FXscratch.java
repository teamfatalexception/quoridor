import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
	
	//Setting up board pane
        StackPane board = new StackPane();
        Canvas boardCanvas = new Canvas(400,  300);
        
        // Setting up info pane
        StackPane info = new StackPane();
        Canvas infoCanvas = new Canvas(200, 300);

        // tying canvases to panes for board and info
        board.getChildren().add(boardCanvas);
        info.getChildren().add(infoCanvas);
        
        // board and info become children of the root
        root.getChildren().addAll(board, info);

        // Setting background colors
        board.setStyle("-fx-background-color: #3388FF");
        info.setStyle("-fx-background-color: #cc0011");
        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}