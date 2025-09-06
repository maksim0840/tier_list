package core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Value("${super.secret.code}")      // ← правильный синтаксис
    private String secretCode;          // ← НЕ static

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Main.class, args);
        var app = ctx.getBean(Main.class);   // получить бин из контекста
        System.out.println(app.secretCode);
    }
}
