/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dean.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

public class ServerBaiHat {

    private static final int SERVER_PORT = 2905;
    private static final String CLIENT_ID = "tYCjXNdxZd4oVYgrpHjo5kaQmpQnRAvs";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server đã khởi động và đang lắng nghe trên cổng " + SERVER_PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + clientSocket.getInetAddress().getHostAddress());

                handleClientRequest(clientSocket);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String query = reader.readLine();
            System.out.println("Yêu cầu từ client: " + query);

            String apiResponse = searchSoundCloud(query);

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            writer.println(apiResponse); // Gửi kết quả dưới dạng chuỗi JSON hợp lệ đến client

            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String searchSoundCloud(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://api-v2.soundcloud.com/search/tracks?q=" + encodedQuery + "&client_id=" + CLIENT_ID;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            connection.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi kết nối tới API hoặc xử lý yêu cầu.";
        }
        

    }
}
     
