package com.thinking.machines.inventory.pl.ui;
import javax.swing.*;
import java.awt.*;
public class ItemFrame extends JFrame
{
private ItemPanel itemPanel;
private Container container;
private ImageIcon logoIcon;
public ItemFrame()
{
super("Inventory Management System");
initComponents();
setAppearance();
addListeners();
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
setSize(500,600);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(getWidth()/2),((d.height-60)/2)-((getHeight())/2));
setVisible(true);
}
private void initComponents()
{ 
itemPanel=new ItemPanel();
}
private void setAppearance()
{ 
logoIcon=new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\logo.jpg");
this.setIconImage(logoIcon.getImage());
container=getContentPane();
container.setLayout(new BorderLayout());
itemPanel.setBorder(BorderFactory.createLineBorder(new Color(83,83,83)));
container.add(itemPanel,BorderLayout.CENTER);
}
private void addListeners()
{ 
// nothing to do in this case
}
}