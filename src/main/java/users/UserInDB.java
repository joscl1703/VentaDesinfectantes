/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package users;

/**
 *
 * @author yeiderson
 */
public class UserInDB extends User {
    public int id;
    public UserInDB(int id,String name, String email, String password) {
        super(name,email,password);
        this.id = id;
    }
}
