import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Column{
	
	private String columnName;
	private int index;
	private String tableName;
	private Connection con;
	
	private ArrayList<String> values = new ArrayList<String>(0);
	        /* The values within the column */
	
	/*public Pair<String, values> = new Pair<>(); */
	public Column() {}
	
	public Column(Connection con, String tableName, String columnName, int index){
		this.con = con;
		this.columnName = columnName;
		this.index = index;
		this.tableName = tableName;
	}
	
	
	public void setColumnValues(ArrayList<String> values) {
		this.values = values;
	}
	
	public ArrayList<String> getColumnValues(){
		return values;
	}
	
	public String getColumnValues(int index){
		
		return values.get(index);
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	
	public int size() {
		return values.size();
	}
	
	public String get(int index) {
		String valueAtIndex = values.get(index);
		return valueAtIndex;
	} 
	
	
	
	
}
