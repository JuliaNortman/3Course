package app.entities;

import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private double account;
    private String phone;
    private String address;
    private String email;
    List<Ticket> tickets;

    public User(int id, String login, String password, double account, String phone, String address, String email){
        this.id = id;
        this.login = login;
        this.password = password;
        this.account = account;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public User(String login, String password, String phone, String address, String email){
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public User(int id){
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getAccount() {
        return account;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
