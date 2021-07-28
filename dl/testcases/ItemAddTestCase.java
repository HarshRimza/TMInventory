import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dao.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.math.*;
public class ItemAddTestCase
{
public static void main(String data[])
{
String name=data[0];
BigDecimal price=new BigDecimal(data[1]);
String category=data[2];
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setName(name);
itemDTO.setPrice(price);
itemDTO.setCategory(category);
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.add(itemDTO);
System.out.printf("%s added with code as %d\n",name,itemDTO.getCode());
}catch(DAOException daoException)
{
daoException.printStackTrace();
}
}
}