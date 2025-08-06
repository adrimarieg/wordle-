import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GameScreen {

    int maxAttempts = 5;

    /* temporary word, replace with API */
        String wordle = "ghoul";

    public Scene getScene(Stage stage, int wordLength, Scene mainScreen) {

        Label lengthLabel = new Label("Word Length: " + wordLength);

        Button restartButton = new Button("Play Again");
        restartButton.setOnAction(e -> stage.setScene(mainScreen));
        restartButton.setDisable(true);

        //Build gridpane: rows = guesses, columns = letters 
        GridPane guessGrid = new GridPane();
        guessGrid.setHgap(10);
        guessGrid.setVgap(10);
        guessGrid.setAlignment(Pos.CENTER);
        guessGrid.setPadding(new Insets(20));

        TextField[][] guessFields = new TextField[maxAttempts][wordLength];

        //int guessCount = 0;
        Label guessInstruction = new Label("");

        for (int row = 0; row < maxAttempts; row++) {
            for (int col = 0; col < wordLength; col++) {
                TextField guessField = new TextField();
                guessField.setStyle("-fx-border-color: blue; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px;");
                guessField.setPrefWidth(40);
                guessField.setPrefColumnCount(1);
                guessFields[row][col] = guessField;
                guessGrid.add(guessField, col, row);

                int currentRow = row;
                int currentCol = col;

                // Limit to 1 character and auto-tab
                guessField.textProperty().addListener((obs, oldText, newText) -> {
                    if (newText.length() > 1 ) {
                        guessField.setText(newText.substring(0, 1));
                    }

                // Optional: Only allow letters
                if (!guessField.getText().matches("[a-zA-Z]?")) {
                    guessField.setText("");
                }

                // Move to the next field if one character entered
                if (newText.length() == 1 && currentCol + 1 < wordLength) {
                 guessFields[currentRow][currentCol + 1].requestFocus();
                }
            });
        }
        }

        final int[] currentAttempt = {0};

        Label statusLabel = new Label("");

        /* Submit each guess button */ 
        Button submitGuess = new Button("Submit");
        submitGuess.setDisable(false);

        submitGuess.setOnAction(e -> {
            if (currentAttempt[0] >= maxAttempts) {
                statusLabel.setText("No more attempts!");
                return;
            }

            StringBuilder guessBuilder = new StringBuilder();
            for (int col = 0; col < wordLength; col++) {
                String input = guessFields[currentAttempt[0]][col].getText();
                if(input.isEmpty()) {
                    statusLabel.setText("Your word must be " + wordLength + " letters long.");
                    return;
                }
                guessBuilder.append(input.toLowerCase());
            }

            String guess = guessBuilder.toString();
            System.out.println("Guess: " + guess);

            if (guess.equals(wordle)) {
                statusLabel.setText("You guess it!");
                submitGuess.setDisable(true);
                restartButton.setDisable(false);
                return;
            }

            //Each character in the attempted guess must be checked
            for (int guessIndex = 0; guessIndex < wordLength; guessIndex++) {
                
                char c = guess.charAt(guessIndex);

                if (c == wordle.charAt(guessIndex)) {
                   System.out.println(c + " is in the right location.");
                } else if (wordle.indexOf(c) >= 0) {
                    System.out.println(c + " is in the wrong location.");
                } else {                       
                    System.out.println(c + " is not included.");
                }
            }
            currentAttempt[0]++;
        });

        VBox root = new VBox(20, lengthLabel, guessInstruction, guessGrid, statusLabel, submitGuess, restartButton);
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 700, 700);
    }
}



/* //User has five total attemps to guess the wordle correctly. 
        for(int attempt = 1; attempt <= maxAttempts; attempt++) {
            Label guessIntruction = new Label("Guess " + attempt + " out of 5");
            String guess = TextField guessField = new TextField();
            Button submitGuess = new Button("Submit");
            
            //check that if guess is equal to wordle
            if (wordle.equals(guess)) {
                System.out.println("You got it!");
                keyboard.close();
                break;
            }

            //Each character in the attempted guess must be checked
            for (int guessIndex = 0; guessIndex < wordLength; guessIndex++) {
                
                char c = guess.charAt(guessIndex);

                if (c == wordle.charAt(guessIndex)) {
                    System.out.println(c + " is in the right location.");
                } else if (wordle.indexOf(c) >= 0) {
                    System.out.println(c + " is in the wrong location.");
                } else {
                    System.out.println(c + " is not included.");
                }

            }
        } */