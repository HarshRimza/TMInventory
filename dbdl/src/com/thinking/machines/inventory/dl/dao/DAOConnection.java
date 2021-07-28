package com.thinking.machines.inventory.dl.dao;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.sql.*;
public class DAOConnection
{
private DAOConnection()
{
}
public static Connection getConnection() throws DAOException
{
Connection c=null;
try
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
c=DriverManager.getConnection("jdbc:derby://localhost:1527/inventorydb");
}catch(Exception e)
{ 
e.printStackTrace();
throw new DAOException(e.getMessage());
}
return c;
}
}