package org.example;

import org.junit.jupiter.api.Test;

import static org.example.Main.parserProt;
import static org.example.Main.pathSettingFile;
import static org.junit.jupiter.api.Assertions.*;

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
}