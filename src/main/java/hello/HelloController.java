package hello;


import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;




@RestController
@RequestMapping(path = "/servicio")
public class HelloController {
	Connection con;

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------
	@Autowired
	private UserDao userDao;


	public HelloController() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.con= DriverManager.
				getConnection("jdbc:mysql://localhost:3306/springbootdb",
						"root","1234");  
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(path = "/obtener",  method = RequestMethod.GET)	
	public String getConnection(){
		try{
			Statement stmt= this.con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from table1");
			HashMap<String, String> has = new HashMap<>();
			while(rs.next()){
				has.put("id", String.valueOf(rs.getInt("id")));
				has.put("name", String.valueOf(rs.getString("name")));
			}
			return new Gson().toJson(has);
		}catch(Exception e){ System.out.println(e);}
		return null;  
	}  

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	@ResponseBody
	public User updateUser(long id) {
		User user = userDao.findOne(id);
		return user;
	}
	
	@RequestMapping(path = "/find", method = RequestMethod.GET)
	@ResponseBody
	public List<User> find(long id, String name) {
		List<User> users = userDao.findByIdAndName(id, name);
		return users;
	}





}