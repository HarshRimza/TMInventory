package com.thinking.machines.inventory.dl.interfaces.dao;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import java.util.*;
public interface ItemDAOInterface
{ /*
if any of the following methods face problems, then they
will raise DAOException
add will receive address of ItemDTOInterface type object
update will receive address of ItemDTOInterface type object
delete will receive code of item to be deleted
getByCode will receive code and will return address of ItemDTOInterface type Object
getByName will receive name and will return address of ItemDTOInterface type Object
getAll will return a List type Object, containing collection of addresses of ItemDTOInterface type
objects
getCount will return the count of number of Items
*/
public void add(ItemDTOInterface item) throws DAOException;
public void update(ItemDTOInterface item) throws DAOException;
public void delete(int code) throws DAOException;
public ItemDTOInterface getByCode(int code) throws DAOException;
public ItemDTOInterface getByName(String name) throws DAOException;
public List<ItemDTOInterface> getAll() throws DAOException;
public int getCount() throws DAOException;
}