import com.thinking.machines.inventory.pl.ui.*;
import com.thinking.machines.inventory.pl.model.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.dao.*;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
import com.thinking.machines.inventory.pl.exceptions.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
class ItemModelTestCase extends JFrame implements ListSelectionListener,DocumentListener
{
private ItemFrame itemFrame;
private JLabel searchCaptionLabel;
private JTextField searchTextField;
private Container container;
private JTable itemTable;
private JScrollPane jsp;
private ItemModel itemModel;
ItemModelTestCase()
{ 
itemFrame=new ItemFrame();
container=getContentPane();
container.setLayout(new BorderLayout());
itemModel=new ItemModel();
JPanel p1=new JPanel();
p1.setLayout(new FlowLayout());
searchCaptionLabel=new JLabel("Search ");
searchTextField=new JTextField(30);
p1.add(searchCaptionLabel);
p1.add(searchTextField);
searchTextField.getDocument().addDocumentListener(this);
container.add(p1,BorderLayout.NORTH);
itemTable=new JTable(itemModel);
jsp=new JScrollPane(itemTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
container.add(jsp);
setLocation(10,10);
setSize(500,700);
setVisible(true);
itemTable.getSelectionModel().addListSelectionListener(this);
itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}
public void valueChanged(ListSelectionEvent ev)
{ 
int selectedRowIndex=itemTable.getSelectedRow();
try
{
ItemDTOInterface item=itemModel.getItemAt(selectedRowIndex);
System.out.println(item.getCode()+","+item.getName()+","+item.getPrice().toPlainString()+","+item.getCategory());
}catch(ModelException modelException)
{
System.out.println(modelException);
}
}
public void changedUpdate(DocumentEvent ev)
{
search();
}
public void removeUpdate(DocumentEvent ev)
{
search();
}
public void insertUpdate(DocumentEvent ev)
{
search();
}
private void search()
{
String searchWhat=searchTextField.getText().trim();
if(searchWhat.length()==0)
{
return;
}
}
public static void main(String gg[])
{
ItemModelTestCase imtc=new ItemModelTestCase();
}
}