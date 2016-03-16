package quoridorFE;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Viewer extends Application {
    //public static void main(String[] args) {
        //Application.launch(args);
    //}
    

    @Override
    public void start(Stage stage) {
        //Label nameLbl = new Label("Enter your name: ");
        //TextField nameFld = new TextField();

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: blue");

        Text text1 = new Text("Big italic red text\n");
        //Text text2 = new Text(" little bold blue text");

        // buttons
        Button sayHelloBtn = new Button("Say Hello");
        Button exitBtn = new Button("Exit");

        // BorderPane as root node
        BorderPane root = new BorderPane();

        // boardPane
        Pane boardPane = new Pane();
        //boardPane.setStyle("-fx-background-color: black;");
        boardPane.setPrefSize(600,600);
        
        //JGraphAdapterDemo applet = new JGraphAdapterDemo();
        //applet.init();
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        boardPane.getChildren().add(swingNode);
        
        
        

        // sideVBox
        VBox sideVBox = new VBox();
        sideVBox.setStyle("-fx-background-color: blue;");
        sideVBox.setPrefSize(200,600);
        sideVBox.getChildren().add(sayHelloBtn);
        




        // bottom console output
        TextFlow consolePane = new TextFlow();
        //consolePane.setPrefHeight(200);
        consolePane.getChildren().add(text1);

        // ScrollPane wrapper for text area
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefViewportHeight(100);
        scroll.setContent(consolePane);


        // even handler for button
        sayHelloBtn.setOnAction(e -> {
            /*
            String name = nameFld.getText();
            if (name.trim().length() > 0) {
                msg.setText("Hello " + name);
            } else {
                msg.setText("Hello there");
            }
            */
            consolePane.getChildren().add(new Text(" little bold blue text\n"));
        });

        // event handler for exit button
        exitBtn.setOnAction(e -> Platform.exit());

        // Create root node
        //HBox root = new HBox();

        // Set the vertical spacing between children to 5px
        //root.setSpacing(5);
         
        // Add children to the root node
        //root.getChildren().addAll(nameLbl, nameFld, msg, sayHelloBtn, exitBtn);
        //root.getChildren().addAll(boardPane, sideVBox);

        sideVBox.getChildren().add(consolePane);

        root.setCenter(boardPane);
        root.setRight(sideVBox);
        root.setBottom(consolePane);
         
        //Scene scene = new Scene(root, 800, 600);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Kill yourself");
        stage.show();


    }


	private void createAndSetSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel panel = new JPanel();
				GraphViewer applet = new GraphViewer();
		        applet.init();
				panel.add(applet);
				panel.setVisible(true);
				swingNode.setContent(panel);
			}
		});
		
	}

}


