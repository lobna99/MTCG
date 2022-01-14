package packages;
import Http.HttpStatus;
import db.getDBConnection;
import response.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageHandler implements getDBConnection, Response, PackageHandlerInterface {

    public PackageHandler() {
    }
    //Create new Package
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
        newPackage.setId(rs.getInt(1));//Get id of package so cards can be assigned to this package
    }


    public Package addPackage(){
        Package newPack=new Package();
        try {
            InsertPackage(newPack);
            respond.writeHttpResponse(HttpStatus.CREATED,"Pack created");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                respond.writeHttpResponse(HttpStatus.BAD_REQUEST,"Pack couldnt be created");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return newPack;
    }
}
