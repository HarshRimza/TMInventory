import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
public class ItemGetCountTestCase
{
public static void main(String data[])
{
try
{
ItemDAOInterface itemDAO=new ItemDAO();
System.out.println("Number of items : "+itemDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}