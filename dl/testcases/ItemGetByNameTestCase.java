import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
public class ItemGetByNameTestCase
{
public static void main(String data[])
{
String name=data[0];
try
{
ItemDAOInterface itemDAO=new ItemDAO();
ItemDTOInterface item=itemDAO.getByName(name);
System.out.printf("Code %d, Name %s,Price %s,Category %s\n",item.getCode(),item.getName(),item.getPrice().toPlainString(),item.getCategory());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}