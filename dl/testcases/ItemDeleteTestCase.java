import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
public class ItemDeleteTestCase
{
public static void main(String data[])
{
int code=Integer.parseInt(data[0]);
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.delete(code);
System.out.println("Item with code : "+code+" deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}