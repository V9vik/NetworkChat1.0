package example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.example.Main.*;

class MainTestParserPort {
@Test
    public void testPort(){
    int port = parserProt(pathSettingFile);
    if(port == 8080){
        System.out.println("Сервер подключен на порт 8080!");
    }else {
        System.out.println("Была попытка подключиться к порту 8080, но она не удачна, порт подключения: "+ port);
    }
}
@Test
    public void testAdress() throws IOException {
    String adressServer = parserAdress(pathSettingFile);
    if(adressServer.equalsIgnoreCase("localhost")){
        System.out.println("Адресс сервера + "+adressServer);
    }else{
        System.out.println("Подключение к другому серверу: "+ adressServer);
    }
}

}