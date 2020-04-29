package models;

import java.util.HashMap;

public class Person {
    private String userName;
    private String password;
    private String name;
    private String familyName;
    private String email;
    private String number;
    private double balance;
    private Role role;

    public Person(HashMap<String, String> information) {
        this.userName = information.get("userName");
        this.password = information.get("password");
        this.name = information.get("name");
        this.familyName = information.get("familyName");
        this.email = information.get("email");
        this.number = information.get("number");
        this.balance = Integer.parseInt(information.get("balance"));
        this.role = roleFinder(information.get("role"));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public Role getRole() {
        return role;
    }

    Role roleFinder(String roleName) {
        if ("customer".equals(roleName)) {
            return Role.CUSTOMER;
        } else if ("seller".equals(roleName)) {
            return Role.SELLER;
        } else if ("admin".equals(roleName)) {
            return Role.ADMIN;
        }
        return null;
    }

}
