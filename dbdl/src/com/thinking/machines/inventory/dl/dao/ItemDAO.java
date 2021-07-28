package com.thinking.machines.inventory.dl.dao;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.sql.*;
public class ItemDAO implements ItemDAOInterface
{
public void add(ItemDTOInterface item) throws DAOException
{ 
try
{ 
int vCode;
String vName;
BigDecimal vPrice;
String vCategory;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where upper(name)=?");
preparedStatement.setString(1,item.getName().toUpperCase());
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item : "+item.getName()+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into item (name,price,category) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,item.getName());
preparedStatement.setBigDecimal(2,item.getPrice());
preparedStatement.setString(3,item.getCategory());
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
item.setCode(resultSet.getInt(1));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException("Unable to insert item");
}
}
//add method ends

//delete method starts
public void delete(int code) throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from item where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException("Unable to insert item");
}
}
//delete method ends


//update method starts
public void update(ItemDTOInterface item) throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where code=?");
preparedStatement.setInt(1,item.getCode());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from item where upper(name)=? and code<>?");
preparedStatement.setString(1,item.getName().toUpperCase());
preparedStatement.setInt(2,item.getCode());
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item : "+item.getName()+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update item set name=?,category=?,price=? where code=?");
preparedStatement.setString(1,item.getName());
preparedStatement.setString(2,item.getCategory());
preparedStatement.setBigDecimal(3,item.getPrice());
preparedStatement.setInt(4,item.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException("Unable to insert item");
}
}
//update method ends

//getByCode method starts
public ItemDTOInterface getByCode(int code) throws DAOException
{
ItemDTOInterface item=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : "+code);
} 
item=new ItemDTO();
item.setCode(resultSet.getInt("code"));
item.setName(resultSet.getString("name").trim());
item.setPrice(resultSet.getBigDecimal("price"));
item.setCategory(resultSet.getString("category").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException(sqlException.getMessage());
}
return item;
}
//getByCode method ends

//getByName method starts
public ItemDTOInterface getByName(String name) throws DAOException
{
ItemDTOInterface item=null;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where upper(name)=?");
preparedStatement.setString(1,name.toUpperCase());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item : "+name);
} 
item=new ItemDTO();
item.setCode(resultSet.getInt("code"));
item.setName(resultSet.getString("name").trim());
item.setPrice(resultSet.getBigDecimal("price"));
item.setCategory(resultSet.getString("category").trim());
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException(sqlException.getMessage());
}
return item;
}
//getByName ends

//getAll starts
public List<ItemDTOInterface> getAll() throws DAOException
{
List<ItemDTOInterface> items=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from item");
ItemDTOInterface item;
while(resultSet.next())
{ 
item=new ItemDTO();
item.setCode(resultSet.getInt("code"));
item.setName(resultSet.getString("name").trim());
item.setPrice(resultSet.getBigDecimal("price"));
item.setCategory(resultSet.getString("category").trim());
items.add(item);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException("Unable to insert item");
}
return items;
}
//getAll ends


//getCount starts
public int getCount() throws DAOException
{ 
int count=0;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as cnt from item");
resultSet.next();
count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{ 
throw new DAOException("Unable to insert item");
}
return count;
}
//getCount ends
}