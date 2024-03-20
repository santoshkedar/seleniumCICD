package SeleniumTraning.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import SeleniumTraning.pageobjects.ProductCatalogue;
import SelenumTraning.testcomponents.BaseTest;

public class DatabaseTest extends BaseTest
{
	
	String host="localhost";
	String port="3306";
	String dbName="QAdb";
	
	@Test
	public void dbSampleTest() throws SQLException, InterruptedException
	{
		
		Connection con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName+"","root", "Santosh!23");
		
		Statement s = con.createStatement();
		
		ResultSet rs = s.executeQuery("select * from Credentials;");
		
		while(rs.next())
		{
			landingPage.loginToApplication(rs.getString("userName"), rs.getString("passWord"));
			System.out.println(rs.getString("userName"));
			System.out.println(rs.getString("passWord"));
		}		
		 
	}
}
