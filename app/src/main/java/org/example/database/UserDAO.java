package org.example.database;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.example.database.contracts.BaseDAO;
import org.example.records.UserDTO;

public class UserDAO implements BaseDAO {
    private final ConnectionManager connectionManager;
    private static final String TABLE = "users";

    public UserDAO(
        ConnectionManager connectionManager
    ) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<UserDTO> all() {
        var users = new ArrayList<UserDTO>();

        try (
            var conn = this.connectionManager.estabelecerConexao();
            var stmt = conn.createStatement();
        ) {
            var resultSet = stmt.executeQuery(String.format("""
                SELECT * FROM %s
            """.trim(), UserDAO.TABLE));

            while (resultSet.next()) {
                users.add(new UserDTO(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("senha"),
                    resultSet.getString("token"),
                    LocalDateTime.parse(resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ));
            }
        } catch (SQLException ex) {
            System.out.println("\u001B[31mNão foi possível recuperar os usuário no banco :(\u001B[37m");
        }

        return users;
    }

    @Override
    public UserDTO find(Integer id) {
        UserDTO user = null;
        var selectUserSql = String.format("""
            SELECT * FROM %s
            WHERE id = ?
        """.trim(), UserDAO.TABLE);

        try (
            var conn = this.connectionManager.estabelecerConexao();
            var stmt = conn.prepareStatement(selectUserSql);
        ) {
            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            if (!resultSet.next()) return user;

            user = new UserDTO(
                resultSet.getInt("id"),
                resultSet.getString("nome"),
                resultSet.getString("email"),
                resultSet.getString("senha"),
                resultSet.getString("token"),
                LocalDateTime.parse(resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        } catch (SQLException ex) {
            System.out.println(String.format("\u001B[31mNão foi possível recuperar o usuário de id \"%s\" :(\u001B[37m", id));
        }

        return user;
    }

    @Override
    public void create(UserDTO user) {
        var createUserSql = String.format("""
            INSERT INTO %s (nome, email, senha, token)
            VALUES (?, ?, ?, ?)
        """.trim(), UserDAO.TABLE);

        try (
            var conn = this.connectionManager.estabelecerConexao();
            var stmt = conn.prepareStatement(createUserSql);
        ) {
            stmt.setString(1, user.nome());
            stmt.setString(2, user.email());
            stmt.setString(3, user.senha());
            stmt.setString(4, user.token());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(String.format("\u001B[31mNão foi possível criar o usuário \"%s\" :(\u001B[37m", user.email()));
        }
    }

    @Override
    public void update(UserDTO user) {
        var updateUserSql = String.format("""
            UPDATE %s
            SET
                nome = ?,
                email = ?,
                senha = ?,
                token = ?
            WHERE id = ?
        """.trim(), UserDAO.TABLE);

        try (
            var conn = this.connectionManager.estabelecerConexao();
            var stmt = conn.prepareStatement(updateUserSql);
        ) {
            stmt.setString(1, user.nome());
            stmt.setString(2, user.email());
            stmt.setString(3, user.senha());
            stmt.setString(4, user.token());

            stmt.setInt(5, user.id());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(String.format("\u001B[31mNão foi possível editar o usuário \"%s\" :(\u001B[37m", user.email()));
        }
    }

    @Override
    public void delete(Integer id) {
        var deleteUserSql = String.format("""
            DELETE FROM users
            WHERE id = ?     
        """.trim(), UserDAO.TABLE);

        try (
            var conn = this.connectionManager.estabelecerConexao();
            var stmt = conn.prepareStatement(deleteUserSql);
        ) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(String.format("\u001B[31mNão foi possível excluir o usuário de id \"%s\" :(\u001B[37m", id));
        }
    }
}
