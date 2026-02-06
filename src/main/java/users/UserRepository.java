/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package users;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author yeiderson
 */
public interface UserRepository {
    UserInDB insert(User data);
    Optional<UserInDB> delete(int id);
    Optional<UserInDB> get(int id);
    Optional<UserInDB> getByEmail(String email);
    List<UserInDB> getAll();
}
