/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventadesinfectantes;

import invoices.InvoiceWithSqlite;
import users.UserWithSqlite;

/**
 *
 * @author yeiderson
 */
public class Repositories {
    private static Repositories instance;
    public users.UserWithSqlite user;
    public invoices.InvoiceWithSqlite invoice;
    private Repositories () {
        user = new UserWithSqlite();
        invoice = new InvoiceWithSqlite();
    }
    public static Repositories getInstance() {
        if(instance == null) {
            instance = new  Repositories();
        }
        return instance;
    }
}
