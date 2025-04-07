package profiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2Database {

    // JDBC URL для H2 в embedded режиме (файловая база)
    private static final String JDBC_URL = "jdbc:h2:~/testdb";  // База будет создана в домашней директории
    private static final String USER = "h2database";
    private static final String PASSWORD = "westsata";

    public static void main(String[] args) {
        try {
            System.out.println("Starting H2 database example...");
            // 1. Загружаем драйвер H2 (не требуется с JDBC 4.0+, но лучше явно указать)
            Class.forName("org.h2.Driver");

            // 2. Устанавливаем соединение
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
                System.out.println("Connected to H2 database!");

                // 3. Создаем statement и выполняем SQL
                try (Statement statement = connection.createStatement()) {
                    // Создаем таблицу
                    statement.execute("DROP TABLE IF EXISTS users;");
                    statement.execute("CREATE TABLE users(id INT PRIMARY KEY, name VARCHAR(255));");

                    // Вставляем данные
                    statement.execute("INSERT INTO users VALUES(1, 'John Doe'), (2, 'Jane Smith');");

                    // Читаем данные
                    try (ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
                        while (resultSet.next()) {
                            System.out.printf("ID: %d, Name: %s%n",
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
