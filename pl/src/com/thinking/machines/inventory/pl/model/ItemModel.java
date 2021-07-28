package com.thinking.machines.inventory.pl.model;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.pl.exceptions.*;
import javax.swing.table.*;
import java.util.*;
public class ItemModel extends AbstractTableModel
{
private List<ItemDTOInterface> items;
private Map<String,ItemDTOInterface> nameWiseItemsMap;
private Map<Integer,ItemDTOInterface> codeWiseItemsMap;
public ItemModel()
{
populateDataStructures();
}
private void populateDataStructures()
{ 
items=null;
nameWiseItemsMap=new HashMap<>();
codeWiseItemsMap=new HashMap<>();
try
{
ItemDAOInterface itemDAO=new ItemDAO();
items=itemDAO.getAll();
Collections.sort(items,(left,right)->{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
});
// logic to sort item ends here
items.forEach((item)->{
nameWiseItemsMap.put(item.getName().toUpperCase(),item);
codeWiseItemsMap.put(new Integer(item.getCode()),item);
});
}catch(DAOException daoException)
{ 
items=new LinkedList<>();
}
} // following methods will be called by JTable to gather information for creating Table
public int getRowCount()
{
return this.items.size();
}
public int getColumnCount()
{
return 3; // serial number, item name & Price
}
public String getColumnName(int columnIndex)
{ 
if(columnIndex==0) return "S.No.";
if(columnIndex==1) return "Item";
return "Price";
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{ 
if(columnIndex==0) c=Class.forName("java.lang.Integer");
if(columnIndex==1) c=Class.forName("java.lang.String");
if(columnIndex==2) c=Class.forName("java.math.BigDecimal");
}catch(ClassNotFoundException classNotFoundException)
{
}
return c; // c contains address of an object of type Class, which contains definition of either Integer or String or BigDecimal class
}
public Object getValueAt(int rowIndex,int columnIndex)
{ 
if(columnIndex==0) return new Integer(rowIndex+1);
if(columnIndex==1) return items.get(rowIndex).getName();
return items.get(rowIndex).getPrice().toPlainString();
} 
// the following methods are related to our application
public ItemDTOInterface getItemAt(int index) throws ModelException
{ 
if(index<0 || index>=items.size()) throw new ModelException("Invalid index : "+index);
return items.get(index);
}
public int search(String leftPartOfName) throws ModelException
{ 
leftPartOfName=leftPartOfName.toUpperCase();
Iterator<ItemDTOInterface> iterator=items.iterator();
int index=0;
while(iterator.hasNext())
{ 
if(iterator.next().getName().toUpperCase().startsWith(leftPartOfName))
{
return index;
} 
index++;
} 
throw new ModelException("Not found");
}
public void add(ItemDTOInterface itemDTO) throws ModelException
{ 
if(nameWiseItemsMap.containsKey(itemDTO.getName().toUpperCase()))
{ 
throw new ModelException("Item : "+itemDTO.getName()+" exists.");
} 
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.add(itemDTO);
items.add(itemDTO);
Collections.sort(items,(left,right)->{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
});
nameWiseItemsMap.put(itemDTO.getName().toUpperCase(),itemDTO);
codeWiseItemsMap.put(new Integer(itemDTO.getCode()),itemDTO);
fireTableDataChanged();
}catch(DAOException daoException)
{ 
throw new ModelException(daoException.getMessage());
}
}
public void update(ItemDTOInterface itemDTO) throws ModelException
{ 
try
{
ItemDTOInterface oldItem=codeWiseItemsMap.get(new Integer(itemDTO.getCode()));
if(oldItem==null)
{
throw new ModelException("Invalid item code : "+itemDTO.getCode());
}
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.update(itemDTO);
items.remove(oldItem);
items.add(itemDTO);
Collections.sort(items,(left,right)->{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
});
nameWiseItemsMap.remove(oldItem.getName().toUpperCase());
codeWiseItemsMap.remove(new Integer(oldItem.getCode()));
codeWiseItemsMap.put(new Integer(itemDTO.getCode()),itemDTO);
nameWiseItemsMap.put(itemDTO.getName().toUpperCase(),itemDTO);
fireTableDataChanged();
}catch(DAOException daoException)
{ 
throw new ModelException(daoException.getMessage());
}
}
public void delete(int code) throws ModelException
{ 
ItemDTOInterface oldItem=codeWiseItemsMap.get(new Integer(code));
if(oldItem==null)
{
throw new ModelException("Invalid item code : "+code);
}
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.delete(code);
items.remove(oldItem);
nameWiseItemsMap.remove(oldItem.getName().toUpperCase());
codeWiseItemsMap.remove(new Integer(oldItem.getCode()));
fireTableDataChanged();
}catch(DAOException daoException)
{ 
throw new ModelException(daoException.getMessage());
}
}

public int getIndexOf(ItemDTOInterface itemDTO) throws ModelException
{ 
int i=0;
for(ItemDTOInterface item:items)
{ 
if(item.getCode()==itemDTO.getCode())
{
return i;
} 
i++;
} 
throw new ModelException("Invalid item : 1"+itemDTO.getName());
}
}