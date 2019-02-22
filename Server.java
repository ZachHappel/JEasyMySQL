import java.sql.*;

public class Server {
	private String name;
	private String address;
	private Integer port;
	private String username;
	private String full_address; /* Used as server path when establishing the connection*/
	
	public Server() {

	}
	
	public Server(String name, String address, Integer port, String username) {
		
		this.name = name;
		this.address = address;
		this.port = port;
		this.username = username;
		this.full_address = "jdbc:mysql://" + address + ":" + port + "/";
		
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
		
	}
	
	public void setPort(int port) {
		this.port = port;
		
	}
	
	public void setUsername(String username) {
		this.username = username;
		
	}
	public void setFullAddress(String full_address) {
		this.full_address = full_address;
	}
	
	
	public String getName() {
		return(this.name);
	}
	
	public String getAddress() {
		return(this.address);
		
	}
	
	public int getPort() {
		return(this.port);
		
	}
	
	public String getUsername() {
		return(this.username);
		
	}
	
	
	
	public String getFullAddress() {
		return (this.full_address);
	}

	
	/* Created for GUI interface, once all parameters are passed, 
	 * this method passes all of the newly defined variables into 
	 * the full_address variable. 
	 */
	public void updateFullUrl() {
		this.full_address = "jdbc:mysql://" + this.address + ":" + this.port + "/";
	}
	
	
}
