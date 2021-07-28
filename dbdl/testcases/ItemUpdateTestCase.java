import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
public class ItemUpdateTestCase
{
public static void main(String data[])
{
int code=Integer.parseInt(data[0]);
String name=data[1];
BigDecimal price=new BigDecimal(data[2]);
String category=data[3];
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setCode(code);
itemDTO.setName(name);
itemDTO.setPrice(price);
itemDTO.setCategory(category);
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.update(itemDTO);
System.out.printf("Item with code %d updated\n",code);
}catch(DAOException daoException)
{
daoException.printStackTrace();
}
}
}