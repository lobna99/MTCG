package packages;

import db.DBconnection;
import server.HttpStatus;
import server.Responsebuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageHandler {
    private DBconnection Connection;

    public PackageHandler() {
        Connection=DBconnection.getInstance();
    }
    public void InsertPackage(Package newPackage) throws SQLException {

        PreparedStatement statement = Connection.getConnection().prepareStatement("""
                         INSERT INTO packages
                         (cost)
                         VALUES (?)
                         RETURNING id;   
                     """);
        statement.setInt(1, newPackage.getCost());
        ResultSet rs=statement.executeQuery();
        rs.next();
        newPackage.setId(rs.getInt(1));
    }
    public Package addPackage(){
        Responsebuilder response=Responsebuilder.getInstance();
        Package newPack=new Package();
        try {
            InsertPackage(newPack);
            response.writeHttpResponse(HttpStatus.CREATED,"Pack created");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                response.writeHttpResponse(HttpStatus.BAD_REQUEST,"Pack couldnt be created");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return newPack;
    }
}
