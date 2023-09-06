package dean.code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

public class ServerCaSi {

    private static final int SERVER_PORT = 0103;

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

            if (query.isEmpty()) {
                String errorMessage = "Lỗi: Truy vấn từ client trống!";
                sendErrorResponse(clientSocket, errorMessage);
            } else {
                String apiResponse = searchWikipedia(query);
                if (apiResponse.isEmpty()) {
                    String errorMessage = "Lỗi: Không tìm thấy dữ liệu.";
                    sendErrorResponse(clientSocket, errorMessage);
                } else {
                    String encodedResponse = Base64.getEncoder().encodeToString(apiResponse.getBytes());
                    OutputStream outputStream = clientSocket.getOutputStream();
                    outputStream.write(encodedResponse.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }

            reader.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendErrorResponse(Socket clientSocket, String errorMessage) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.write(errorMessage.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private static String searchWikipedia(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&list=search&format=json&srsearch=" + encodedQuery;

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
