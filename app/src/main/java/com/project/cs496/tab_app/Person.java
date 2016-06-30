package com.project.cs496.tab_app;

public class Person {

    private String Name;
    private String PhoneNumber;

    public Person(String _Name, String _PhoneNumber) {
        this.Name = _Name;
        this.PhoneNumber = _PhoneNumber;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
