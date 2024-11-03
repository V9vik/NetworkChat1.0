package org.example;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.Main.*;


public class ClientHandler implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Введите ваше имя:");
            username = in.readLine();
            out.println("Добро пожаловать в чат, " + username + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message){
        out.println(message);
    }
@Override
public void run() {
    String message;
    try {
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while ((message = input.readLine()) != null) {
            if (message.equalsIgnoreCase("/exit")) {
                break;
            }
            String formattedMessage = String.format("[%s] %s: %s",
                    new SimpleDateFormat("HH:mm:ss").format(new Date()),
                    username,
                    message);
            System.out.println(formattedMessage);
            logMessage(formattedMessage);
            broadcastMessage(formattedMessage);
            clientHandlersList.remove(this);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
    private static void logMessage(String message) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathLogFile, true))) {
            writer.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}