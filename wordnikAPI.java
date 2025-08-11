import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;

public class WordnikAPI {

    private static final String API_KEY = "092bu1k2ny44bfz1u6ivghxv4pazrnlhpd2a5i4pc7u8b9lr7";

    public static String getRandomWord(int length) throws Exception {

        for (int apiCalls = 0; apiCalls < 5; apiCalls++) {

            String url = String.format(
                "https://api.wordnik.com/v4/words.json/randomWord?minLength=%d&maxLength=%d&api_key=%s",
            length, length, API_KEY);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println("Raw JSON from API: " + json);

            String word = json.replaceAll(".*\"word\"\\s*:\\s*\"([^\"]+)\".*", "$1");
            String normalized = Normalizer.normalize(word, Normalizer.Form.NFD);
            String withoutAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String cleanWord = withoutAccents.replaceAll("[^a-zA-Z]", "");

            if (cleanWord.length() == length && !cleanWord.contains(" ") && !cleanWord.isEmpty()) {
                return cleanWord.toLowerCase();
            } else {
                System.out.println("Discarded word: " + cleanWord + " (wrong length or invalid)");
            }
        }
        return "Default";
    }

    public static void main(String[] args) throws Exception {
        String randomWord = getRandomWord(7);
        System.out.println("Random Word: " + randomWord);
    }
}
