/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dean.code;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author minhh
 */
public class ClientBaiHat {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {

                    String songName = reader.readLine();

                String response = searchSong(songName);
                System.out.println("Thông tin bài hát:");
                System.out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String searchSong(String songName) throws IOException {
        URL url = new URL("http://localhost:8000/search");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String postData = "song_name=" + songName;
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(postData.getBytes());
        outputStream.flush();
        outputStream.close();

        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }
}
