package user;

import lombok.Getter;

public class User {
    @Getter
    private String Username;
    @Getter
    private String Password;
    @Getter
    private int ELO;
    @Getter
    private int Coins;

    public User(String Username,String Password){
        this.Username=Username;
        this.Password=Password;
        this.ELO=100;
        this.Coins=20;
    }
    public void purchasePack(){
    }
    //requesttrade()
    //accepttrade()
    //requestbattle()
    //acceptbattle()
}
