package org.example;

import java.io.*;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String pathSettingFile = "/Users/vovamyzikov/IdeaProjects/CourserWork/Client/src/main/setting_helper/settings.txt";
    public static String pathLogFile = "/Users/vovamyzikov/IdeaProjects/CourserWork/Client/src/main/setting_helper/file.log";
    private static int port;
    private static String adressServer;

    public static void main(String[] args) throws IOException {
        File f = new File(pathSettingFile);
        FileReader is = new FileReader(f);

        BufferedReader br = new BufferedReader(is);

        port = Integer.parseInt(br.readLine());

        adressServer = br.readLine();

        try (Socket socket = new Socket(adressServer, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            String username = in.readLine();
                System.out.println(username);

            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                        logMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String messageToSend;
            while ((messageToSend = consoleInput.readLine()) != null) {
                if (messageToSend.equalsIgnoreCase("/exit")) {
                    out.println("/exit");
                    break;
                }
                out.println(messageToSend);
                logMessage(username + ": " + messageToSend);
            }

        } catch (IOException e) {
            e.printStackTrace();
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