package models;

import uExt.model.Model;

public class Person extends Model {

    public String name;
    public String email;
    public String password;

    public Person (String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
