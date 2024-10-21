package org.example.database.console;

import java.util.Arrays;

import org.example.database.ConnectionManager;

public class UserSeeder {
    private static final String TABLE = "users";

    private static void populate() {
        var insertUsersSql = String.format("""
            INSERT INTO %s (nome, email, senha, token)
            VALUES (?, ?, ?, ?)        
        """.trim(), UserSeeder.TABLE);

        String[][] users = {
            {"Alice Silva", "alice.silva@example.com", "password1s@lt1", null},
            {"Bruno Costa", "bruno.costa@example.com", "password2s@lt2", "token2"},
            {"Carla Souza", "carla.souza@example.com", "password3s@lt3", null},
            {"Daniel Alves", "daniel.alves@example.com", "password4s@lt4", "token4"},
            {"Elisa Ferreira", "elisa.ferreira@example.com", "password5s@lt5", null},
            {"Felipe Lima", "felipe.lima@example.com", "password6s@lt6", "token6"},
            {"Gabriela Rocha", "gabriela.rocha@example.com", "password7s@lt7", null},
            {"Henrique Duarte", "henrique.duarte@example.com", "password8s@lt8", "token8"},
            {"Isabela Teixeira", "isabela.teixeira@example.com", "password9s@lt9", null},
            {"Jorge Mendes", "jorge.mendes@example.com", "password10s@lt10", "token10"},
            {"Karina Ribeiro", "karina.ribeiro@example.com", "password11s@lt11", null},
            {"Lucas Barbosa", "lucas.barbosa@example.com", "password12s@lt12", "token12"},
            {"Mariana Oliveira", "mariana.oliveira@example.com", "password13s@lt13", null},
            {"Nicolas Martins", "nicolas.martins@example.com", "password14s@lt14", "token14"},
            {"Olivia Araujo", "olivia.araujo@example.com", "password15s@lt15", null},
            {"Paulo Cardoso", "paulo.cardoso@example.com", "password16s@lt16", "token16"},
            {"Queila Santana", "queila.santana@example.com", "password17s@lt17", null},
            {"Renato Pires", "renato.pires@example.com", "password18s@lt18", "token18"},
            {"Sabrina Lopes", "sabrina.lopes@example.com", "password19s@lt19", null},
            {"Tiago Campos", "tiago.campos@example.com", "password20s@lt20", "token20"},
            {"Ursula Nogueira", "ursula.nogueira@example.com", "password21s@lt21", null},
            {"Victor Freitas", "victor.freitas@example.com", "password22s@lt22", "token22"},
            {"Wesley Faria", "wesley.faria@example.com", "password23s@lt23", null},
            {"Yara Cruz", "yara.cruz@example.com", "password24s@lt24", "token24"},
            {"Zeca Lima", "zeca.lima@example.com", "password25s@lt25", null}
        };

        try (
            var conn = ConnectionManager.instanciar().estabelecerConexao();
            var stmt = conn.prepareStatement(insertUsersSql);
        ) {
            for (var user : users) {
                stmt.setString(1, (String) user[0]);
                stmt.setString(2, (String) user[1]);
                stmt.setString(3, (String) user[2]);
                stmt.setObject(4, user[3]); 

                stmt.addBatch();
            }

            stmt.executeBatch();

            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m inserções: \u001B[32mgeradas ✅\u001B[37m", UserSeeder.TABLE));
        } catch (Exception ex) {
            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m inserções: \u001B[31mfalharam ❌\u001B[37m", UserSeeder.TABLE));
        }
    }

    private static void clean() {
        try (
            var conn = ConnectionManager.instanciar().estabelecerConexao();
            var stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(String.format("""
                DELETE FROM %s
            """.trim(), UserSeeder.TABLE));

            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m limpeza: \u001B[32mconcluída ✅\u001B[37m", UserSeeder.TABLE));
        } catch (Exception ex) {
            System.out.println(String.format("-- Tabela \u001B[34m%s\u001B[37m limpeza: \u001B[31mfalhou ❌\u001B[37m", UserSeeder.TABLE));
        }
    }

    public static void main(String ...args) {
        if (Arrays.asList(args).contains("clean")) {
            UserSeeder.clean();
            System.exit(0);
        }

        UserSeeder.populate();
    }
}
