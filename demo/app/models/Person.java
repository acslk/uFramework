package models;

import uExt.model.Model;

public class Person extends Model {

    public String name;
    public Integer age;

    public Person (String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
