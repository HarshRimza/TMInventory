import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
import java.util.*;
public class ItemGetAllTestCase
{
public static void main(String data[])
{
try
{
ItemDAOInterface itemDAO=new ItemDAO();
List<ItemDTOInterface> items=itemDAO.getAll();
items.forEach((item)->{
System.out.printf("%d, %s, %s, %s\n",item.getCode(),item.getName(),item.getPrice().toPlainString(),item.getCategory());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}