package packages;

import Http.HttpStatus;
import db.DBconnectionImpl;
import db.getDBConnection;
import response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageHandlerImpl implements getDBConnection, Response, PackageHandler {

    public PackageHandlerImpl() {
    }

    //Create new Package
    public boolean InsertPackage(Package newPackage) throws SQLException {
        Connection con = DBconnectionImpl.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement("""
                    INSERT INTO packages
                    (cost)
                    VALUES (?)
                    RETURNING id;
                """);
        statement.setInt(1, newPackage.getCost());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            newPackage.setId(rs.getInt(1));//Get id of package so cards can be assigned to this package
            statement.close();
            rs.close();
            con.close();
            return true;
        } else {
            statement.close();
            rs.close();
            con.close();
            return false;
        }
    }


    public Package addPackage() throws IOException, SQLException {
        Package newPack = new Package();
        if (InsertPackage(newPack)) {
            respond.writeHttpResponse(HttpStatus.CREATED, "Pack created");
        } else {
            respond.writeHttpResponse(HttpStatus.BAD_REQUEST, "Pack couldnt be created");
        }
        return newPack;
    }
}
