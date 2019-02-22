import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap; 

public class Table extends Database {
	
	private String db_name;
	private Connection con;
	private String tableName;
	private int columnAmt;
	
	
	
	private LinkedHashMap<String, Column> columns = new LinkedHashMap<>();
	/* Holds the column objects that are within the table */
	
	
	
	public Table() {}
	
	public Table(String name, String db_name, Connection con) {
		this.con= con;
		this.tableName = name;
		this.db_name = db_name;
	
		importColumns();
	}

	
	/* Loads the columns and the values they contain*/
	/* Automatically is called when a Table object is created */ 
	private void importColumns() {
		
		
		String column =  "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tableName+"' AND TABLE_SCHEMA = '" +db_name + "';";
		
		try { Statement statement = this.con.createStatement();
		 
		  ResultSet query = statement.executeQuery(column);
		  
		  int counter = 0; 
		 
		  
		  while(query.next()) {   /* While there is another column */
			  	String columnName = query.getString(1);
			  	
			  	Column col = new Column(con, tableName, columnName, counter);
			    /* ^  Create Column object ^ */
			  	/*    Counter is index location of column within table
			  	 *    Values are auto-populated when column is initialized
			  	 */
			  	
			  	ArrayList<String> colVals = retrieveColumnValues(this.tableName, columnName);
			  	/* Creates ArrayList of the column's values */
			  	col.setColumnValues(colVals);
			  	
			  	
			  	/* Set's column's values to the Column object that was created */
			  	
			  	columns.put(col.getColumnName(), col);
			  	
			  	counter ++; 
			  	/* Adds column to ArrayList consisting of the Table object's columns */
		   }
		  this.columnAmt = this.columns.size();
		} catch(Exception e) {System.out.println(e);}
		
		
		
		
	}
	

	/* Gets the values within a column */
	
	private ArrayList<String> retrieveColumnValues(String tableName, String columnName) {
		
	
		ArrayList<String> values = new ArrayList<String>(0);
	
		try { Statement statement = this.con.createStatement();  

			 
		      ResultSet query = statement.executeQuery("SELECT " +db_name+"."+tableName+"."+columnName+" FROM " + db_name + "." + tableName + ";");
		     
		      while(query.next()) { 
		    	 
		    	  values.add(query.getString(1));
		    	  
		      }
		      
		} catch (Exception e) {System.out.println(e);};
			
		
		return values;
	}
	

	/* Returns column names */
	public ArrayList<String> getColumnNames(){
		
		ArrayList<String> colNames = new ArrayList<>();
		
		if(columns.isEmpty() == false) {
			for (String key : columns.keySet()) {
			   colNames.add(key);
			}
		}
		
		return colNames;
	}
	
	/* Lists Column Names */
	public void listColNames() {
		if(columns.isEmpty() == false) {
			for (String key : columns.keySet()) {
			    System.out.println(key);
			}
		}
		
		
			
		}
	
	public Column getColumnObject(String columnName) {
		return columns.get(columnName);
	}
	
	
	/* Returns amount of columns in the form of an int */
	public int getColumnAmount() {
		return columnAmt;
	}
	
	public int getColumnSize(String name) {
		return columns.get(name).size();
	}
	
	
	public HashMap<String, Column> getColumns(){
		
		
		return columns;
	}
	
	
	public void listColValues(String columnName) {
		
		if (columns.containsKey(columnName)) {
			ArrayList<String> values = columns.get(columnName).getColumnValues();
			
			for (int i = 0; i <= values.size() -1; i++) {
				System.out.println(values.get(i));
				
			}
		}
		
		
	}
	
}
