import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import dnl.utils.text.table.TextTable;
import wagu.Block;
import wagu.Board;
import wagu.WaguTable;

public class Database extends Server {
	public Server server;
	public String server_name;
	public String database_address;
	public boolean connected = false;
	
	private String db_name;
	private Connection con;
	
	
	public HashMap<String, Table> ImportedTables = new HashMap<>();
	
	public ArrayList<String> TableNames = new ArrayList<>();
	
	
	private String[] excludedTables = new String[] {"CHARACTER_SETS", "COLLATIONS", "COLLATION_CHARACTER_SET_APPLICABILITY", "COLUMNS", "COLUMN_PRIVILEGES", "ENGINES", "EVENTS", "FILES", "GLOBAL_STATUS", "GLOBAL_VARIABLES", "KEY_COLUMN_USAGE", "OPTIMIZER_TRACE", "PARAMETERS", "PARTITIONS", "PLUGINS", "PROCESSLIST", "PROFILING", "REFERENTIAL_CONSTRAINTS", "ROUTINES", "SCHEMATA", "SCHEMA_PRIVILEGES", "SESSION_STATUS", "SESSION_VARIABLES", "STATISTICS", "TABLES", "TABLESPACES", "TABLE_CONSTRAINTS", "TABLE_PRIVILEGES", "TRIGGERS", "USER_PRIVILEGES", "VIEWS", "INNODB_LOCKS", "INNODB_TRX", "INNODB_SYS_DATAFILES", "INNODB_FT_CONFIG", "INNODB_SYS_VIRTUAL", "INNODB_CMP", "INNODB_FT_BEING_DELETED", "INNODB_CMP_RESET", "INNODB_CMP_PER_INDEX", "INNODB_CMPMEM_RESET", "INNODB_FT_DELETED", "INNODB_BUFFER_PAGE_LRU", "INNODB_LOCK_WAITS", "INNODB_TEMP_TABLE_INFO", "INNODB_SYS_INDEXES", "INNODB_SYS_TABLES", "INNODB_SYS_FIELDS", "INNODB_CMP_PER_INDEX_RESET", "INNODB_BUFFER_PAGE", "INNODB_FT_DEFAULT_STOPWORD", "INNODB_FT_INDEX_TABLE", "INNODB_FT_INDEX_CACHE", "INNODB_SYS_TABLESPACES", "INNODB_METRICS", "INNODB_SYS_FOREIGN_COLS", "INNODB_CMPMEM", "INNODB_BUFFER_POOL_STATS", "INNODB_SYS_COLUMNS", "INNODB_SYS_FOREIGN", "columns_priv", "db", "engine_cost", "event", "func", "general_log", "gtid_executed", "help_category", "help_keyword", "help_relation", "help_topic", "innodb_index_stats", "innodb_table_stats", "ndb_binlog_index", "plugin", "proc", "procs_priv", "proxies_priv", "server_cost", "servers", "slave_master_info", "slave_relay_log_info", "slave_worker_info", "slow_log", "tables_priv", "time_zone", "time_zone_leap_second", "time_zone_name", "time_zone_transition", "time_zone_transition_type", "user", "accounts", "cond_instances", "events_stages_current", "events_stages_history", "events_stages_history_long", "events_stages_summary_by_account_by_event_name", "events_stages_summary_by_host_by_event_name", "events_stages_summary_by_thread_by_event_name", "events_stages_summary_by_user_by_event_name", "events_stages_summary_global_by_event_name", "events_statements_current", "events_statements_history", "events_statements_history_long", "events_statements_summary_by_account_by_event_name", "events_statements_summary_by_digest", "events_statements_summary_by_host_by_event_name", "events_statements_summary_by_program", "events_statements_summary_by_thread_by_event_name", "events_statements_summary_by_user_by_event_name", "events_statements_summary_global_by_event_name", "events_transactions_current", "events_transactions_history", "events_transactions_history_long", "events_transactions_summary_by_account_by_event_name", "events_transactions_summary_by_host_by_event_name", "events_transactions_summary_by_thread_by_event_name", "events_transactions_summary_by_user_by_event_name", "events_transactions_summary_global_by_event_name", "events_waits_current", "events_waits_history", "events_waits_history_long", "events_waits_summary_by_account_by_event_name", "events_waits_summary_by_host_by_event_name", "events_waits_summary_by_instance", "events_waits_summary_by_thread_by_event_name", "events_waits_summary_by_user_by_event_name", "events_waits_summary_global_by_event_name", "file_instances", "file_summary_by_event_name", "file_summary_by_instance", "global_status", "global_variables", "host_cache", "hosts", "memory_summary_by_account_by_event_name", "memory_summary_by_host_by_event_name", "memory_summary_by_thread_by_event_name", "memory_summary_by_user_by_event_name", "memory_summary_global_by_event_name", "metadata_locks", "mutex_instances", "objects_summary_global_by_type", "performance_timers", "prepared_statements_instances", "replication_applier_configuration", "replication_applier_status", "replication_applier_status_by_coordinator", "replication_applier_status_by_worker", "replication_connection_configuration", "replication_connection_status", "replication_group_member_stats", "replication_group_members", "rwlock_instances", "session_account_connect_attrs", "session_connect_attrs", "session_status", "session_variables", "setup_actors", "setup_consumers", "setup_instruments", "setup_objects", "setup_timers", "socket_instances", "socket_summary_by_event_name", "socket_summary_by_instance", "status_by_account", "status_by_host", "status_by_thread", "status_by_user", "table_handles", "table_io_waits_summary_by_index_usage", "table_io_waits_summary_by_table", "table_lock_waits_summary_by_table", "threads", "user_variables_by_thread", "users", "variables_by_thread", "innodb_buffer_stats_by_schema", "innodb_buffer_stats_by_table", "innodb_lock_waits", "io_by_thread_by_latency", "io_global_by_file_by_bytes", "io_global_by_file_by_latency", "io_global_by_wait_by_bytes", "io_global_by_wait_by_latency", "latest_file_io", "memory_by_host_by_current_bytes", "memory_by_thread_by_current_bytes", "memory_by_user_by_current_bytes", "memory_global_by_current_bytes", "memory_global_total", "metrics", "processlist", "ps_check_lost_instrumentation", "schema_auto_increment_columns", "schema_index_statistics", "schema_object_overview", "schema_redundant_indexes", "schema_table_lock_waits", "schema_table_statistics", "schema_table_statistics_with_buffer", "schema_tables_with_full_table_scans", "schema_unused_indexes", "session", "session_ssl_status", "statement_analysis", "statements_with_errors_or_warnings", "statements_with_full_table_scans", "statements_with_runtimes_in_95th_percentile", "statements_with_sorting", "statements_with_temp_tables", "sys_config", "user_summary", "user_summary_by_file_io", "user_summary_by_file_io_type", "user_summary_by_stages", "user_summary_by_statement_latency", "user_summary_by_statement_type", "version", "wait_classes_global_by_avg_latency", "wait_classes_global_by_latency", "waits_by_host_by_latency", "waits_by_user_by_latency", "waits_global_by_latency", "x$host_summary", "x$host_summary_by_file_io", "x$host_summary_by_file_io_type", "x$host_summary_by_stages", "x$host_summary_by_statement_latency", "x$host_summary_by_statement_type", "x$innodb_buffer_stats_by_schema", "x$innodb_buffer_stats_by_table", "x$innodb_lock_waits", "x$io_by_thread_by_latency", "x$io_global_by_file_by_bytes", "x$io_global_by_file_by_latency", "x$io_global_by_wait_by_bytes", "x$io_global_by_wait_by_latency", "x$latest_file_io", "x$memory_by_host_by_current_bytes", "x$memory_by_thread_by_current_bytes", "x$memory_by_user_by_current_bytes", "x$memory_global_by_current_bytes", "x$memory_global_total", "x$processlist", "x$ps_digest_95th_percentile_by_avg_us", "x$ps_digest_avg_latency_distribution", "x$ps_schema_table_statistics_io", "x$schema_flattened_keys", "x$schema_index_statistics", "x$schema_table_lock_waits", "x$schema_table_statistics", "x$schema_table_statistics_with_buffer", "x$schema_tables_with_full_table_scans", "x$session", "x$statement_analysis", "x$statements_with_errors_or_warnings", "x$statements_with_full_table_scans", "x$statements_with_runtimes_in_95th_percentile", "x$statements_with_sorting", "x$statements_with_temp_tables", "x$user_summary", "x$user_summary_by_file_io", "x$user_summary_by_file_io_type", "x$user_summary_by_stages", "x$user_summary_by_statement_latency", "x$user_summary_by_statement_type", "x$wait_classes_global_by_avg_latency", "x$wait_classes_global_by_latency", "x$waits_by_host_by_latency", "x$waits_by_user_by_latency"};
	private Set<String> excludedSet = new HashSet<String>(Arrays.asList(excludedTables));
	
	
	public Database() {}
	
	public Database(Server server, String db_name) {
		this.server = server; 
		this.db_name = db_name;
		this.server_name = server.getName();
		this.database_address = this.getFullAddress() + db_name + "/";
	}

	
	public void formConnection(String password) {
		
		/* Tries three times to connect to the server and database. I have never once had an issue. */
		for(int tries = 0; tries != 3; tries++) {
			
			 try {Class.forName("com.mysql.jdbc.Driver");}
			 catch (Exception e){ System.out.println(e);} 
			 
			 try {
				 Connection con = DriverManager.getConnection(server.getFullAddress(), server.getUsername(), password);
				 this.connected = true;
				 System.out.println("Connected: " +this.db_name + " on " + server_name);
				 this.con = con;
				 break;
			 } catch (SQLException e) {System.out.println(e);}
		
		} this.connected = false;  
		
	}
	
	/* Finds all tables and adds their names to the list of tables that were found. */
	public void findTables() {
		
		System.out.println("-------------");
		System.out.println("Tables Found:");
		
		try {
			
			Statement statement = this.con.createStatement();
			ResultSet query = statement.executeQuery("SELECT table_name FROM information_schema.tables;");
			String name;
			
			while(query.next()) {
				name = query.getString(1);
				if (excludedSet.contains(name) == false) {TableNames.add(name);}
				/*Making sure that tables are not imported are not user-added */
			}
			
		} catch(Exception e) {System.out.println(e);}
		System.out.println("-------------");
	}
	
	/* Outputs in the form of a list, all tables that were initially found when the connection to the database was first made */
	public void listTables() {
		for (int i = 0; i <= TableNames.size()-1; i++) {
			System.out.println(TableNames.get(i));
		}
	}
	
	/* Outputs name of each table in LinkedHashMap Imported Tables */
	public void listImportedTables() {
		System.out.println("------------------------");
		System.out.println("Listing Imported Tables: ");
		ArrayList<String> duplicatePrevention = new ArrayList<>();
		
		for(int i = 0; i <= TableNames.size()-1; i++) {
			
			String t_name =  TableNames.get(i);							/*Checks if it is within our tables list, and checks if it has been imported by seeing if the table value is null or not */
			if (ImportedTables.containsKey(t_name) && ImportedTables.get(t_name) != null && duplicatePrevention.contains(t_name) == false){
				System.out.println(t_name);	
				duplicatePrevention.add(t_name);
			}
		} System.out.println("------------------------");
	}
	
	
	public ArrayList<String> getImportedTablesNames() {
		System.out.println("------------------------");
		System.out.println("Listing Imported Tables: ");
		ArrayList<String> duplicatePrevention = new ArrayList<>();
		
		for(int i = 0; i <= TableNames.size()-1; i++) { 
			
			String t_name =  TableNames.get(i);
										/*Checks if it is within our tables list, and checks if it has been imported by seeing if the table value is null or not */
			if (ImportedTables.containsKey(t_name) && ImportedTables.get(t_name) != null && duplicatePrevention.contains(t_name) == false){
				duplicatePrevention.add(t_name);
			}
		} return duplicatePrevention;
	}
	
	
	/* User submitted SQL queries */
	public void submitSQL(String query) throws SQLException {
		
		Statement statement = this.con.createStatement();
		ResultSet userQuery = statement.executeQuery(query);
		
		while(userQuery.next()) {
			String lines = userQuery.getString(1);
			System.out.println(lines);
		}
	}
	
	
	/* Creates Table object, imports columns and the values within */
	public void importTable(String name) throws TableNotImportedException{
		
		try {
			System.out.println("Importing Table: "+ name);
			ImportedTables.put(name, new Table(name, db_name, con));
		} catch (Exception e) {System.out.println(e); throw new TableNotImportedException("Table specified does not exist!");}
	}
		

	
	/* Uses getColumnNames from class Table */
	public void listColumnNames(String tableName) {
		System.out.println("------------------------|");
		System.out.println("[Columns] "+ tableName);
		System.out.println("~~~~~~~~~~" + printMultiplier("~", tableName.length() + 1));
		try {
			if (isImportedTable(tableName)) { ImportedTables.get(tableName).listColNames();
			}
		} catch (Exception e) { System.out.println(e);}
		System.out.println("------------------------|");
	}		
		
	

	
	public void listColumnValues(String tableName, String columnName) {
		
		String header = "[Column: "+ columnName + "," + " Table: "+tableName+"]";
		
		System.out.println("\n\n" + printMultiplier("-", header.length()+2));
		System.out.println(header);
		System.out.println(" " + printMultiplier("~", header.length()-1));
		
		ImportedTables.get(tableName).listColValues(columnName);
		
		System.out.println(printMultiplier("-", header.length()+2));
	}
	
	
	

	
	public void showTable(String tableName) {
		
		ArrayList<String> colNames = ImportedTables.get(tableName).getColumnNames();
		
		HashMap<String, Column> allCols = ImportedTables.get(tableName).getColumns();
							/*Local instance of the table's columns so that it is easier to grab the values */
		List<List<String>> Rows = new ArrayList<>();
		
		int firstColSize = allCols.get(colNames.get(0)).size();
		/* First column acts as the index */
	
		int colAmount = colNames.size();
	
		for(int x = 0; x <= firstColSize-1; x++) {
													/* x = row,   iterates over first column because it should always have a value (index/PK)  */
			ArrayList<String> individualRow = new ArrayList<String>();
			
			for (int y = 0; y<= colNames.size()-1; y++) { 						/* Iterates Y-axis values */
				String val = allCols.get(colNames.get(y)).getColumnValues(x);
				if (val == null || val.equals("")) { val = "null";}
				try {
					individualRow.add(val);
				} catch (Exception e) {System.out.println(e);};
			
			}
			
			Rows.add(individualRow);
		
		}
		
		Board frame = new Board(calcBoardSize(colAmount));
		WaguTable waguTable = new WaguTable(frame, calcBoardSize(colAmount), colNames, Rows);
		Block tableBlock = waguTable.tableToBlocks();
		frame.setInitialBlock(tableBlock);
		frame.build();
		String tabularFormattedString = frame.getPreview();
		System.out.println(tabularFormattedString);
	}
	
	
	
	public void showTablesAll() {
		
		ArrayList<String> importedTableNames = getImportedTablesNames();
		
		for (int i = 0; i <= importedTableNames.size()-1; i++) {
			showTable(importedTableNames.get(i));
		}
		
	}
	
	
	public Table getTable(String tableName) {
		return ImportedTables.get(tableName);
	}
	
	
	public void detachConnection() {
		try {this.con.close();}
		catch (Exception e) { System.out.println(e);}	
		
	}
	
	
	public void setServer(Server serv) {
		this.server = serv;
	}
	
	
	public void setDbName(String name) {
		this.db_name = name;
	}
	
	
	private boolean isImportedTable(String tableName) { 
		
		if (ImportedTables.containsKey(tableName)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	/* Dynamically changes the board size relative to the amount of columns present within a table */
	private int calcBoardSize(int colAmount) {
		
		int boardSize; 
		
		
		if (colAmount <= 6) {
			boardSize = 150;
		} else { boardSize = colAmount*21;}
		
		return boardSize;
	}
	
	
	/* Returns String composed of passed String parameter x number of times 
	 * Used for making borders/underlines
	 * (Equivalent to Python, where: "String" * 3 = "StringStringString")
	 */
	private String printMultiplier(String str, int amount) {
		
		StringBuilder string = new StringBuilder();
		for (int i = 1; i < amount; i ++){
			string.append(str);
			}
		
		return string.toString(); 
	}
	
	
	
	
	
	
}
