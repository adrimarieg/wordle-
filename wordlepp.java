import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class wordlepp extends Application {

    int wordLength;

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
        
        /**** Logo image + imageView ****/
        Image wordleppLogo = new Image("file:wordlepp-logo.png");
        ImageView logoView = new ImageView(wordleppLogo);
        logoView.setFitWidth(400);
        logoView.setPreserveRatio(true);

        Label introText = new Label(
            "We all love our daily NYTimes Wordle, but why only play once a day?\n" +
            "Wordl++ not only allows you to play as much as you'd like, \n" +
            "but also take your skill up a notch by selecting your word length!"
        );

        introText.setWrapText(true); 
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setAlignment(Pos.CENTER);

        Label disclaimer = new Label("++We are not associated with the NYTimes Wordle game.");
        disclaimer.setWrapText(true); 
        disclaimer.setTextAlignment(TextAlignment.CENTER);
        disclaimer.setAlignment(Pos.CENTER);
        

        /**** SLIDER - WORD LENGTH VBOX ****/
        Label wordLengthTitle = new Label("Select the word length:");
        wordLengthTitle.setStyle("-fx-font-weight: bold;");
        
        Slider wordLengthSlider = new Slider(4, 8, 6);
        wordLengthSlider.setShowTickMarks(true);
        wordLengthSlider.setShowTickLabels(true);
        wordLengthSlider.setMajorTickUnit(1);
        wordLengthSlider.setMinorTickCount(0);
        wordLengthSlider.setSnapToTicks(true);
        wordLengthSlider.setMaxWidth(300);

        wordLengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            wordLength = newValue.intValue();
            System.out.println("Word length selected: " + wordLength);
        });

        /**** START BUTTON ****/
        Button startButton = new Button("Start");
        startButton.setAlignment(Pos.CENTER);
        startButton.getStyleClass().add("startButton");
        startButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox wordLengthVbox = new VBox(20, wordLengthTitle, wordLengthSlider);
        wordLengthVbox.setAlignment(Pos.CENTER);

        /**** Window's title ****/
        primaryStage.setTitle("Wordl++");

        /**** SCENE/STAGE VBOX ****/
        VBox root = new VBox(50, logoView, introText, wordLengthVbox, startButton, disclaimer);
        root.setAlignment(Pos.CENTER);

        //Set Scene size
        Scene mainScreen = new Scene(root, 600, 600);
        primaryStage.setScene(mainScreen);

        /**** ACTION HANDLERS ****/
        startButton.setOnAction(e -> {
            GameScreen gameScreen = new GameScreen();
            Scene gameScene = gameScreen.getScene(primaryStage, wordLength, mainScreen);
            primaryStage.setScene(gameScene);
        });
        
        //Show the window
        primaryStage.show();
    }
}
