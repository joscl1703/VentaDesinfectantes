/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invoices;

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
public class InvoiceWithSqlite implements InvoiceRepository {

    private static final String URL = "jdbc:sqlite:invoices.db";
    private Connection connection;

    public InvoiceWithSqlite() {
        try {
            connection = DriverManager.getConnection(URL);
            String sql = "CREATE TABLE IF NOT EXISTS facturas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "articleCode TEXT NOT NULL,"
                    + "quantitySoldInLiters REAL NOT NULL,"
                    + "pricePerLiter REAL NOT NULL"
                    + ");";
            try (Statement stmt = connection.createStatement();) {
                stmt.execute(sql);
                System.out.println("Base de datos facturas lista");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al conectarse a la base de datos de las facturas.");
        }
    }

    @Override
    public InvoiceInDB insert(Invoice data) {
        String sql = "INSERT INTO facturas(articleCode, quantitySoldInLiters, pricePerLiter) VALUES(?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, data.articleCode);
            stmt.setDouble(2, data.quantitySoldInLiters);
            stmt.setDouble(3, data.pricePerLiter);
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt((1));
                    return new InvoiceInDB(id, data.articleCode, data.quantitySoldInLiters, data.pricePerLiter);
                } else {
                    throw new SQLException("Ocurrió un error al obtener la id de la factura");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al añadir la factura");
        }
    }

    @Override
    public Optional<InvoiceInDB> get(int id) {
        String sql = "SELECT * FROM facturas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    InvoiceInDB invoice = new InvoiceInDB(rs.getInt("id"),
                            rs.getString("articleCode"),
                            rs.getDouble("quantitySoldInLiters"),
                            rs.getDouble("pricePerLiter"));
                    return Optional.of(invoice);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener la factura");
        }
        return Optional.empty();
    }

    @Override
    public List<InvoiceInDB> getAll() {
        List<InvoiceInDB> invoices = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                InvoiceInDB invoice = new InvoiceInDB(rs.getInt("id"),
                        rs.getString("articleCode"),
                        rs.getDouble("quantitySoldInLiters"),
                        rs.getDouble("pricePerLiter"));
                invoices.add(invoice);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener las facturas");
        }
        return invoices;
    }

    @Override
    public Optional<InvoiceInDB> delete(int id) {
        Optional<InvoiceInDB> invoice = get(id);
        if (invoice.isEmpty()) {
            return Optional.empty();
        }
        String sql = "DELETE FROM facturas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return invoice;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al eliminar la factura");
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM facturas";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
       catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al eliminar las facturas");
        }
    }
    
    @Override
    public List<InvoiceInDB> paginate(int page, int count) {
        int offset = (page - 1) * count;
        List<InvoiceInDB> invoices = new ArrayList<>();
        String sql = "SELECT * FROM facturas LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, count);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InvoiceInDB invoice = new InvoiceInDB(rs.getInt("id"),
                            rs.getString("articleCode"),
                            rs.getDouble("quantitySoldInLiters"),
                            rs.getDouble("pricePerLiter"));
                    invoices.add(invoice);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener las facturas");
        }
        return invoices;
    }

}
