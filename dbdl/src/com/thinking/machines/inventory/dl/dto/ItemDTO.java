package com.thinking.machines.inventory.dl.dto;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import java.math.*;
public class ItemDTO implements ItemDTOInterface 
{
private int code;
private String name;
private BigDecimal price;
private String category;
public ItemDTO()
{
this.code=0;
this.name="";
this.price=new BigDecimal("0.000");
this.category="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode() 
{
return this.code;
}
public void setName(String name) 
{
this.name=name;
}
public String getName() 
{
return this.name;
}
public void setPrice(BigDecimal price) 
{
this.price=price;
}
public BigDecimal getPrice() 
{
return this.price;
}
public void setCategory(String category) 
{
this.category=category;
}
public String getCategory() 
{
return this.category;
}
public boolean equals(Object otherItem)
{
if((otherItem instanceof ItemDTOInterface)==false) 
{
return false;
}
ItemDTOInterface item=(ItemDTOInterface)otherItem;
if(this.code==item.getCode()) 
{
return true;
}
return false;
}
public int compareTo(ItemDTOInterface otherItem)
{
if(this.code<otherItem.getCode()) return -1;
if(this.code>otherItem.getCode()) return 1;
return 0;
}
public int hashCode()
{
return this.code;
}
} 