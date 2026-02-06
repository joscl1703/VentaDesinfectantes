/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package invoices;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author yeiderson
 */
public interface InvoiceRepository {
    InvoiceInDB insert(Invoice data);
    Optional<InvoiceInDB> delete(int id);
    Optional<InvoiceInDB> get(int id);
    List<InvoiceInDB>paginate(int page, int count);
    List<InvoiceInDB> getAll();
    void deleteAll();
}
