import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class wordnikAPI {
    public static void main(String[] args) throws Exception {
        //String apiKey = "ADD KEY";
        int length = wordLength; //accept value from other file

        String url = String.format(
            "https://api.wordnik.com/v4/words.json/randomWord?minLength=%d&maxLength=%d&api_key=%s", 
            length, 
            length, 
            apiKey
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpClient request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
