/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dean.code;

/**
 *
 * @author tienquynh
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MusicServer {

    private static final int SERVER_PORT = 2905;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server đã khởi động và đang lắng nghe trên cổng " + SERVER_PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + clientSocket.getInetAddress().getHostAddress());

                sendMusicToClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMusicToClient(Socket clientSocket) {
        try {
            File musicFile = new File("C:\\Users\\tienquynh\\Downloads\\loc phat\\src\\dean\\code\\Em-Cua-Ngay-Hom-Qua-DJ-Rum-Barcad-Remix-Son-Tung-M-TP.wav");
            FileInputStream fis = new FileInputStream(musicFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            fis.close();
            baos.close();

            byte[] musicData = baos.toByteArray();
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(musicData);
            outputStream.flush();

            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
