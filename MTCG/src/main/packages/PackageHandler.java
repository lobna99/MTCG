package packages;

import java.io.IOException;
import java.sql.SQLException;

public interface PackageHandler {
    public boolean InsertPackage(Package newPackage) throws SQLException;

    public Package addPackage() throws IOException, SQLException;
}
