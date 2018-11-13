package net.geeksh.baaraapp;

public class User {

    private double userId;
    private String username;
    private String email;
    private String tel;
    private String address;
    private String role;
    private Double company_id;



    public User(double userId, String username, String email, String tel, String address, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.role = role;

    }
}
