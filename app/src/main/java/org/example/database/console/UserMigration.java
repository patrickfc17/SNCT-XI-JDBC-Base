package org.example.database.console;

import java.util.Arrays;

import org.example.database.ConnectionManager;

public class UserMigration {
    private static final String TABLE = "users";

    private static void up() {
        try (
            var conn = ConnectionManager.instanciar().estabelecerConexao();
            var stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(String.format("""
                CREATE TABLE %s (
                    id INTEGER PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    senha VARCHAR(100) NOT NULL,
                    token VARCHAR(64),
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """.trim(), UserMigration.TABLE));

            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m: \u001B[32mcriada ✅\u001B[37m", UserMigration.TABLE));
        } catch (Exception ex) {
            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m: \u001B[31mfalhou ❌\u001B[37m", UserMigration.TABLE));
        }
    }

    private static void down() {
        try (
            var conn = ConnectionManager.instanciar().estabelecerConexao();
            var stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(String.format("""
                DROP TABLE %s
            """, UserMigration.TABLE));

            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m: \u001B[32mremovida ✅\u001B[37m", UserMigration.TABLE));
        } catch (Exception ex) {
            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m rollback: \u001B[31mfalhou ❌\u001B[37m", UserMigration.TABLE));
        }
    }

    public static void main(String ...args) {
        if (Arrays.asList(args).contains("rollback")) {
            UserMigration.down();
            System.exit(0);
        }

        UserMigration.up();
    }
}
