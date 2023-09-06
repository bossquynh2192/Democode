/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dean.code;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCaSi {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter singer name: ");
            String query = userInputReader.readLine();

            writer.println(query);
            String response = reader.readLine();
            System.out.println("Response: " + response);
                

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
