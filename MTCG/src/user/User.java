package user;

public class User {

    private final String Username;
    private final String Password;
    private final int ELO;
    private final int Coins;

    public User(String Username,String Password){
        this.Username=Username;
        this.Password=Password;
        this.ELO=100;
        this.Coins=20;
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
}
