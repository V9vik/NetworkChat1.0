package org.example;

import java.io.*;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String pathSettingFile = "Client/src/setting_helper/settings.txt";
    public static String pathLogFile = "Client/src/setting_helper/file.log";
    private static int port;
    private static String adressServer;

    public static void main(String[] args) throws IOException {
        port = parserProt(pathSettingFile);
        if (port == -1){
            return;
        }
        adressServer = parserAdress(pathSettingFile);
        System.out.println(port + " " + adressServer);
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
    public static int parserProt(String pathSettingFile){
        File f = new File(pathSettingFile);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            port = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Файл настроек не найден: " + e.getMessage());
            return -1;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла настроек: " + e.getMessage());
            return -1;
        } catch (NumberFormatException e) {
            System.err.println("Некорректный формат порта в файле настроек: " + e.getMessage());
            return -1;
        }
        return port;
    }
    public static String parserAdress(String pathSettingFile) throws IOException {
        File f = new File(pathSettingFile);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // тк данные со второго
            adressServer = br.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении адреса сервера: " + e.getMessage());
            throw e;
        }
        return adressServer;
    }

}