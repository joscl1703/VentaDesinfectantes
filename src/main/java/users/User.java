/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

/**
 *
 * @author yeiderson
 */
public class User {
 public String name;
 public String email;
 public String password;
 public User(String name, String email, String password) {
     this.name = name;
     this.email = email;
     this.password = password;
 }
 public boolean comparePassword(String passwordToCompare) {
     return password.equals(passwordToCompare);
 }
}
