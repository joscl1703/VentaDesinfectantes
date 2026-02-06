/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invoices;

/**
 *
 * @author yeiderson
 */
public class Invoice {
    public String articleCode;
    public double quantitySoldInLiters;
    public double pricePerLiter;
    public Invoice(String articleCode, double quantitySoldInLiters, double pricePerLiter) {
        this.articleCode = articleCode;
        this.quantitySoldInLiters = quantitySoldInLiters;
        this.pricePerLiter = pricePerLiter;
    }
}
