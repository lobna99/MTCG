package packages;

import java.sql.SQLException;

public interface PackageHandlerInterface {
    public void InsertPackage(Package newPackage) throws SQLException;
    public Package addPackage();
}
