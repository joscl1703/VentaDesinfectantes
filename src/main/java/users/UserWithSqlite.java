/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yeiderson
 */
public class UserWithSqlite implements UserRepository {

    private static final String URL = "jdbc:sqlite:users.db";
    private Connection connection;

    public UserWithSqlite() {
        try {
            this.connection = DriverManager.getConnection(URL);
            String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    + ");";
            try (Statement stmt = connection.createStatement();) {
                stmt.execute(sql);
                System.out.println("Base de datos usuarios lista");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al conectarse a la base de datos de los usuarios");
        }

    }

    @Override
    public UserInDB insert(User data) {
        String sql = "INSERT INTO usuarios(name, email, password) VALUES(?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, data.name);
            stmt.setString(2, data.email);
            stmt.setString(3, data.password);
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new UserInDB(id, data.name, data.email, data.password);
                } else {
                    throw new SQLException("No se pudo obtener la id del usuario.");
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new RuntimeException("Ya existe un correo registrado.");
            } else {
                System.err.println(e.getMessage());
                throw new RuntimeException("Ocurrió un error al añadir al usuario");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al añadir al usuario");
        }
    }

    @Override
    public Optional<UserInDB> get(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserInDB user = new UserInDB(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"));
                    return Optional.of(user);
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener al usuario");
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserInDB> getByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserInDB user = new UserInDB(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"));
                    return Optional.of(user);
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener al usuario");
        }
        return Optional.empty();
    }

    @Override
    public List<UserInDB> getAll() {
        List<UserInDB> users = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserInDB user = new UserInDB(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"));
                users.add(user);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener todos los usuarios");
        }
        return users;
    }

    @Override
    public Optional<UserInDB> delete(int id) {
        Optional<UserInDB> userToDelete = get(id);
        if (userToDelete == null) {
            return Optional.empty();
        }
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return userToDelete;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al eliminar al usuario");
        }
    }

}
