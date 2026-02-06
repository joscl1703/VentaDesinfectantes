/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invoices;

/**
 *
 * @author yeiderson
 */
public class InvoiceInDB extends Invoice {
    public int id;
    public InvoiceInDB(int id, String articleCode, double quantitySoldInLiters, double pricePerLiter) {
        super(articleCode,quantitySoldInLiters,pricePerLiter);
        this.id = id;
    }
}
