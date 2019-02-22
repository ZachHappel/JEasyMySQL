import java.util.Scanner;

public class JavaMySQL_CLI {
	
	
	
	
	public static void main(String[] args) throws TableNotFoundException, TableNotImportedException {

		String[] serverQuestions = new String[] {"Enter Server Address: \njdbc:mysql://", "Enter Port Number: ", "Enter Username: ", 
				"Enter Name For This Connection: ", "Enter (Exact) Name of Database: ", "**For security, this is not stored **\nEnter Server Password: "};
		
		Server serv = new Server();
		Database database = new Database();
		
		boolean gettingServerInfo = true;
		boolean readyForNext = true;
		int		ServerQuestionCounter = -1;
		String  address = "";
		String username = "";
		int port = 0;
		String password =  "";
		String connName = "";
		String visual_address = "";
		String  connectionName = "";
		String  databaseName = "";
		
		boolean mainMenu = false;
		boolean newSelection = false;
		
		Scanner input = new Scanner(System.in);
	
		while(gettingServerInfo) {
			
			readyForNext = true;
			ServerQuestionCounter = ServerQuestionCounter +1;
			
			while(readyForNext) {
				readyForNext = false;
				
				
				
				
				/* Address */
				if(ServerQuestionCounter == 0) {
					System.out.print(serverQuestions[0]);
					if(input.hasNext()) {
						
						address = input.nextLine();
						serv.setAddress(address);
						visual_address  = "jdbc:mysql://" + address;
						System.out.print("\n\n\n");
						
					}
				}
					/* Port */
					else if (ServerQuestionCounter == 1) {
						System.out.println(serverQuestions[1]);
						System.out.print(address + "/");
						if(input.hasNext()) {
							port = input.nextInt();
							serv.setPort(port);
							System.out.print("\n\n\n");
						}
					
					}
					
					/* Username */
					else if (ServerQuestionCounter == 2) {
						System.out.print(serverQuestions[2]);
						if(input.hasNext()) {
							username = input.next();
							serv.setUsername(username);	
							System.out.print("\n\n\n");
						}
						
					}
				
					
					/* Connection Name */
					else if (ServerQuestionCounter == 3) {
						Scanner connNameQuery = new Scanner(System.in);
						System.out.print(serverQuestions[3]);
						connName = connNameQuery.next();
						readyForNext = false;
						
						
					}
					/* Database Name */	
					else if (ServerQuestionCounter == 4) {
						Scanner dbNameScanner = new Scanner(System.in);
						System.out.print(serverQuestions[4]);
						if(dbNameScanner.hasNext()) {
							databaseName = dbNameScanner.nextLine();
							database.setName(databaseName);
				
							System.out.print("\n\n\n");
						}
						
					}
					/* Password */
					else if (ServerQuestionCounter == 5) {
	
								System.out.print(serverQuestions[5]);	
								Scanner passScanner = new Scanner(System.in);
									
								while(passScanner.hasNext()) {
									System.out.print("\n\n\n");
									password = passScanner.next();
									serv.updateFullUrl();
									database.setServer(serv);
									database.setName("Connection");
									database.setDbName(databaseName);
									database.formConnection(password);
									
									mainMenu = true;
									newSelection = true; 
									gettingServerInfo = false; 
									break;
									
						}
									
																
					}
						
						
				
				}
					
					
			}
				
			
			
		
		while (mainMenu) {
			
		
			
			if (newSelection =  true) {
					
					newSelection = false;
					
					Scanner tableSelect = new Scanner(System.in);
				
					System.out.println("\n\n\n\n\n1) Load and List Table Names \n2) List Loaded Tables\n3) Import Table \n4) List Imported Tables \n5) Display Imported Table in Table Format \n6) List Columns Within Imported Table\n7) List Values Within Column \n8) Detatch Connection");
					System.out.print("Enter Selection: ");
					
					int selection = tableSelect.nextInt();
					
					
					if (selection == 1) {
						
						database.findTables();
						database.listTables();
						newSelection =  true;
								
					} else if (selection == 2) {
						
						database.listTables();
						newSelection = true;
						
					} else if (selection == 3) {
						String tablename;
						Scanner tableScanner = new Scanner(System.in);
						System.out.print("Enter name of table to import: ");
						tablename = tableScanner.next(); 
						try {
							database.importTable(tablename);
						} catch (TableNotImportedException e) {System.out.println(e);}
						
						newSelection = true;
						
					} else if (selection == 4) {
						
						database.listImportedTables();
						newSelection = true;
						
					} else if (selection == 5) {
						
						String tablename;
						Scanner tableScanner = new Scanner(System.in);
						System.out.print("Enter table to display: ");
						tablename = tableScanner.next(); 
						try {
							database.showTable(tablename);
						} catch (NullPointerException e)
						
					    { System.out.println(e.getMessage());
						  throw new TableNotFoundException(tablename);
					    }
							
						
						
						newSelection = true;
						
					} else if (selection == 6) {
						
						String tableName;
						Scanner tableScanner = new Scanner(System.in);
						System.out.print("Enter the table that the column resides:  ");
						tableName = tableScanner.next(); 
						
						database.listColumnNames(tableName);
						newSelection = true;
						
					} else if (selection == 7) {
						
						String tableName;
						Scanner tableScanner = new Scanner(System.in);
						System.out.print("Enter the table that the column resides:  ");
						tableName = tableScanner.next(); 
						
						String columnName;
						Scanner columnScanner = new Scanner(System.in);
						System.out.print("Enter the name of the column: ");
						columnName = columnScanner.next();
						
						
						database.listColumnValues(tableName, columnName);
						newSelection = true;
						
					} else if (selection == 8) {
						
						
						System.out.println("Have a nice day.");
						database.detachConnection();
						System.exit(0);
						
						
						
					}
						
		
		
		
		
		
		
		
			}
		
		
		
		
		}
					
	}
		
	
	
}
