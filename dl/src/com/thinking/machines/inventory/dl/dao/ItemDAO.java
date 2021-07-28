package com.thinking.machines.inventory.dl.dao;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;
import java.math.*;
public class ItemDAO implements ItemDAOInterface
{
public void add(ItemDTOInterface item) throws DAOException
{ 
try
{
File file=new File("item.data");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
int vCode;
String vName;
BigDecimal vPrice;
String vCategory;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vPrice=new BigDecimal(randomAccessFile.readLine());
vCategory=randomAccessFile.readLine();
if(vName.equalsIgnoreCase(item.getName()))
{
randomAccessFile.close();
throw new DAOException("Item : "+item.getName()+" exists.");
}
} 
int newCode=1;
File itemCodeFile=new File("item_code.data");
RandomAccessFile itemCodeRandomAccessFile;
itemCodeRandomAccessFile=new RandomAccessFile(itemCodeFile,"rw");
if(itemCodeRandomAccessFile.length()>0)
{
newCode=Integer.parseInt(itemCodeRandomAccessFile.readLine());
newCode++;
} 
itemCodeRandomAccessFile.seek(0);
itemCodeRandomAccessFile.writeBytes(newCode+"\n");
itemCodeRandomAccessFile.close();
randomAccessFile.writeBytes(newCode+"\n");
randomAccessFile.writeBytes(item.getName()+"\n");
randomAccessFile.writeBytes(item.getPrice().toPlainString()+"\n");
randomAccessFile.writeBytes(item.getCategory()+"\n");
randomAccessFile.close();
item.setCode(newCode);
}catch(IOException ioException)
{ 
throw new DAOException("Unable to add item");
}
}
public void delete(int code) throws DAOException
{ 
try
{
File file=new File("item.data");
if(file.exists()==false) throw new DAOException("Invalid item code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid item code : "+code);
}
boolean found=false;
int vCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
if(vCode==code)
{
found=true;
break;
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid item code : "+code);
}
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists())
{
tmpFile.delete();
}
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
if(vCode!=code)
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
} 
else
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{ 
throw new DAOException("Unable to get items");
}
}
public void update(ItemDTOInterface item) throws DAOException
{ 
try
{ 
// look for code, if not found generate exception
File file=new File("item.data");
if(file.exists()==false) throw new DAOException("Invalid item code : "+item.getCode());
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid item code : "+item.getCode());
} 
int vCode;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
if(vCode==item.getCode())
{
found=true;
break;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
} 
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid item code : "+item.getCode());
} 
// look for name, if found against another code, generate exception
// the following code could have been logically handled in the above loop,
// but I am just trying to keep it simple for avg. students
randomAccessFile.seek(0);
String vName;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
if(vName.equalsIgnoreCase(item.getName()) && item.getCode()!=vCode)
{
randomAccessFile.close();
throw new DAOException("Item : "+item.getName()+" exists.");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
} 
// open 2files, original /tmp
File tmp=new File("tmp.tmp");
if(tmp.exists()) tmp.delete();
RandomAccessFile tmpraf=new RandomAccessFile(tmp,"rw");
randomAccessFile.seek(0);
// copy all records (1 changed) to tmp
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
if(vCode!=item.getCode())
{
tmpraf.writeBytes(vCode+"\n");
tmpraf.writeBytes(randomAccessFile.readLine()+"\n");
tmpraf.writeBytes(randomAccessFile.readLine()+"\n");
tmpraf.writeBytes(randomAccessFile.readLine()+"\n");
} 
else
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
tmpraf.writeBytes(item.getCode()+"\n");
tmpraf.writeBytes(item.getName()+"\n");
tmpraf.writeBytes(item.getPrice().toPlainString()+"\n");
tmpraf.writeBytes(item.getCategory()+"\n");
}
} 
// copy back records from tmp to original
randomAccessFile.seek(0);
tmpraf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length())
{
randomAccessFile.writeBytes(tmpraf.readLine()+"\n");
} 
// set length of tmp to 0
randomAccessFile.setLength(tmpraf.length());
randomAccessFile.close();
tmpraf.close();
// updation done
}catch(IOException ioException)
{ 
throw new DAOException("Unable to update item");
}
}
public ItemDTOInterface getByName(String name) throws DAOException
{
ItemDTOInterface item;
try
{
File file=new File("item.data");
if(file.exists()==false)
{ 
throw new DAOException("Invalid item :"+name);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid item : "+name);
} 
int vCode;
String vName;
BigDecimal vPrice;
String vCategory;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vPrice=new BigDecimal(randomAccessFile.readLine());
vCategory=randomAccessFile.readLine();
if(vName.equals(name))
{
randomAccessFile.close();
item=new ItemDTO();
item.setCode(vCode);
item.setName(vName);
item.setPrice(vPrice);
item.setCategory(vCategory);
return item;
}
}
randomAccessFile.close();
throw new DAOException("Invalid item : "+name);
}catch(IOException ioException)
{ 
throw new DAOException("Unable to get item");
}
}
public ItemDTOInterface getByCode(int code) throws DAOException
{
ItemDTOInterface item;
try
{
File file=new File("item.data");
if(file.exists()==false)
{ 
throw new DAOException("Invalid item code :"+code);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid item code : "+code);
} 
int vCode;
String vName;
BigDecimal vPrice;
String vCategory;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vPrice=new BigDecimal(randomAccessFile.readLine());
vCategory=randomAccessFile.readLine();
if(vCode==code)
{
randomAccessFile.close();
item=new ItemDTO();
item.setCode(vCode);
item.setName(vName);
item.setPrice(vPrice);
item.setCategory(vCategory);
return item;
}
}
randomAccessFile.close();
throw new DAOException("Invalid item code : "+code);
}catch(IOException ioException)
{ 
throw new DAOException("Unable to get item");
}
}
public List<ItemDTOInterface> getAll() throws DAOException
{
List<ItemDTOInterface> items=new LinkedList<ItemDTOInterface>();
try
{
File file=new File("item.data");
if(file.exists()==false) return items;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return items;
}
ItemDTOInterface item;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{ 
item=new ItemDTO();
item.setCode(Integer.parseInt(randomAccessFile.readLine()));
item.setName(randomAccessFile.readLine());
item.setPrice(new BigDecimal(randomAccessFile.readLine()));
item.setCategory(randomAccessFile.readLine());
items.add(item);
}
randomAccessFile.close();
}catch(IOException ioe)
{ 
throw new DAOException("Unable to add item");
}
return items;
}
public int getCount() throws DAOException
{ 
int count=0;
try
{
File file=new File("item.data");
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
count++;
}
randomAccessFile.close();
count/=4;
}catch(IOException ioe)
{ 
throw new DAOException("Unable to add item");
}
return count;
}
}