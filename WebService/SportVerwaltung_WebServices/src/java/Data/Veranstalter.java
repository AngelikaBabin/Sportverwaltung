/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Chris
 */
public class Veranstalter extends Account{
    public Veranstalter(int id, String email, String name, String password) {
        super(id, email, name, password);
    }

    @Override
    public String toString() {
        return "Veranstalter{" +
                "email='" + getEmail() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
