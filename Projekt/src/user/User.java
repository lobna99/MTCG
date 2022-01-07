package main.user;

public class User {

    private String Username;
    private String Password;
    private int ELO;
    private int Coins;

    public User(String Username,String Password){
        this.Username=Username;
        this.Password=Password;
        this.ELO=100;
        this.Coins=20;
    }
    public void purchasePack(){
    }

    public int getCoins() {
        return Coins;
    }

    public int getELO() {
        return ELO;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }
    //requesttrade()
    //accepttrade()
    //requestbattle()
    //acceptbattle()
}
