package com.wildcodeschool.wizardSite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.sql.*;

@Controller
@SpringBootApplication
public class WizardSiteApplication {

	static Connection con;

	public static void main(String[] args) throws Exception {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/wild_db_quest","root","German1993");
		SpringApplication.run(WizardSiteApplication.class, args);
	}

@RequestMapping("/")
    @ResponseBody
    public String index() throws Exception {
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select * from team");
    	String output="<h1> list of teams</h1><ul>";
    	while(rs.next()){
			output +="<li><a href=team/"+rs.getInt(1)+">"+rs.getString(2)+"</a></li>";
    	}
        	output +="</ul>";
        	return output;
    }


 @RequestMapping("/team/{team_id}")
    @ResponseBody
    public String hello(@PathVariable int team_id)  throws Exception{
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select * from team where id = "+team_id);

    	if(rs.next()){
    		String output ="<h1>" + rs.getString("name") + "</h1><ul>";
    		Statement stmt2=con.createStatement();
    		ResultSet rs2=stmt2.executeQuery("select firstname, lastname from wizard join player on wizard.id = player.wizard_id where player.team_id=" +team_id);

    		while(rs2.next()) {
    			output+= "<li>" +rs2.getString("firstname")+rs2.getString("lastname") +"</li>";
    		}

   			output += "</ul>";
			return output;

			}else{
			return "No such team!";
		}
	}
}