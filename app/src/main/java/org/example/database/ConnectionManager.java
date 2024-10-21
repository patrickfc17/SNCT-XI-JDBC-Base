package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instancia = null;
    private static Connection conn = null;

    private ConnectionManager() {}

    public static ConnectionManager instanciar() {
        if (ConnectionManager.instancia == null) {
            ConnectionManager.instancia = new ConnectionManager();
        }

        return ConnectionManager.instancia;
    }

    public Connection estabelecerConexao() {
        if (ConnectionManager.conn != null) return conn;

        var dbQualifiedName = "org.sqlite.JDBC";
        var dbFilePath = "src/main/java/org/example/database/database.sqlite";

        try {
            Class.forName(dbQualifiedName);

            ConnectionManager.conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbFilePath));

            System.out.println("Conexão estabelecida!");
        } catch (ClassNotFoundException e) {
            System.out.println(String.format("A classe %s não foi encontrada", dbQualifiedName));
            
            System.exit(-1);
        } catch (SQLException e) {
            System.out.println(String.format("""
                Não foi possível estabelecer a conexão.
                Verifique se o arquivo %s/%s existe
            """, System.getProperty("user.dir"), dbFilePath));
        }

        return ConnectionManager.conn;
    }
}
