package org.example;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String pathSettingFile = "src/setting_helper/settings.txt";
    public static String pathLogFile = "src/setting_helper/file.log";
    public static List<ClientHandler> clientHandlersList = new ArrayList<>();
    private static int port;
    public static void main(String[] args) {

        port = parserProt(pathSettingFile);
        if (port == -1){
            return;
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключился пользователь");
                ClientHandler cH = new ClientHandler(socket);
                clientHandlersList.add(cH);
                new Thread(cH).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void broadcastMessage(String message){
        for(ClientHandler clientHendler : clientHandlersList){
            clientHendler.sendMessage(message);
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

}
