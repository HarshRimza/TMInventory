package com.thinking.machines.inventory.dl.interfaces.dto;
import java.math.*;
public interface ItemDTOInterface extends java.io.Serializable,Comparable<ItemDTOInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
public void setPrice(BigDecimal price);
public BigDecimal getPrice();
public void setCategory(String category);
public String getCategory();
public boolean equals(Object otherItem);
public int hashCode();
}