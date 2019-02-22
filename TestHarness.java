import java.sql.SQLException;
import java.util.ArrayList;
public class TestHarness {
	public static void main(String[] args) throws SQLException, TableNotImportedException {
		Server local = new Server("NickNameForServer", "192.168.1.1", 3306, "root" );
		Database database = new Database(local, "DB_Name");
		database.formConnection("NewPassword");
		database.findTables();
		database.listTables();
		
		database.importTable("Course");
		database.importTable("Person");
		database.importTable("Semester");
		
		database.listImportedTables();
		
		database.listColumnNames("Person");
		database.listColumnNames("Semester");
		database.listColumnNames("Course");

		database.listColumnNames("Person");
		database.listColumnValues("Person", "FirstName");
		
		
		database.showTablesAll();
		
		
	}
	
}

