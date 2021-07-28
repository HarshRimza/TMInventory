package com.thinking.machines.inventory.pl.ui;
import java.math.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.thinking.machines.inventory.pl.model.*;
import com.thinking.machines.inventory.pl.exceptions.*;
import com.thinking.machines.inventory.dl.dto.*;
import com.thinking.machines.inventory.dl.interfaces.dto.*;
public class ItemPanel extends JPanel
{
private static final int ADD_MODE=1;
private static final int EDIT_MODE=2;
private static final int DELETE_MODE=3;
private static final int EXPORT_TO_PDF_MODE=4;
private static final int VIEW_MODE=5;
private int mode;
private JLabel moduleTitle;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private JTable table;
private JScrollPane jsp;
private ItemModel itemModel;
private ItemDetailsPanel itemDetailsPanel;
public ItemPanel()
{ 
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{ 
itemModel=new ItemModel();
moduleTitle=new JLabel("Items");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\clear.png"));
searchErrorLabel=new JLabel("");
table=new JTable(itemModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
itemDetailsPanel=new ItemDetailsPanel();
}
private void setAppearance()
{
setLayout(null);
// set look & feel
Font moduleTitleFont=new Font("Times New Roman",Font.BOLD,24);
moduleTitle.setFont(moduleTitleFont);
Font columnTitleFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,10);
searchLabel.setFont(dataFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
table.setFont(dataFont);
table.setRowHeight(30);
table.getColumnModel().getColumn(0).setPreferredWidth(100);
table.getColumnModel().getColumn(1).setPreferredWidth(250);
table.getColumnModel().getColumn(2).setPreferredWidth(100);
table.getTableHeader().setFont(columnTitleFont);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setResizingAllowed(false);
table.getTableHeader().setReorderingAllowed(false);
itemDetailsPanel.setBorder(BorderFactory.createLineBorder(new Color(83,83,83)));
// set bounds
int topMargin,leftMargin;
topMargin=0;
leftMargin=0;
moduleTitle.setBounds(leftMargin+10,topMargin+5,200,40);
searchLabel.setBounds(leftMargin+10,topMargin+5+40+5,55,30);
searchTextField.setBounds(leftMargin+10+55+5,topMargin+5+40+5,300,30);
clearSearchTextFieldButton.setBounds(leftMargin+10+55+5+300+3,topMargin+5+40+5,30,30);
searchErrorLabel.setBounds(leftMargin+10+55+5+300-60,topMargin+5+40+5-20,60,25);
jsp.setBounds(leftMargin+10,topMargin+5+40+5+30+5,465,250);
itemDetailsPanel.setBounds(leftMargin+10,topMargin+5+40+5+30+5+250+5,465,210);
// add component
add(moduleTitle);
add(searchLabel);
add(searchTextField);
add(clearSearchTextFieldButton);
add(searchErrorLabel);
add(jsp);
add(itemDetailsPanel);
this.setViewMode();
}
private void addListeners()
{ 
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
public void valueChanged(ListSelectionEvent ev)
{ 
int selectedRow=table.getSelectedRow();
if(selectedRow<0 || selectedRow==table.getRowCount())
{ 
itemDetailsPanel.setItem(null);
} 
else
{ 
try
{
ItemDTOInterface item;
item=itemModel.getItemAt(selectedRow);
itemDetailsPanel.setItem(item);
}catch(ModelException modelException)
{ 
itemDetailsPanel.setItem(null);
}
}
}
});
searchTextField.getDocument().addDocumentListener(new DocumentListener(){
public void insertUpdate(DocumentEvent ev)
{
search();
}
public void removeUpdate(DocumentEvent ev)
{
search();
}
public void changedUpdate(DocumentEvent ev)
{
search();
}
public void search()
{
searchErrorLabel.setText("");
String searchWhat=searchTextField.getText().trim();
if(searchWhat.length()==0) return;
try
{ 
int index=itemModel.search(searchWhat);
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
}catch(ModelException modelException)
{
searchErrorLabel.setText("Not found");
}
}
});
}
public void setViewMode()
{ 
this.mode=VIEW_MODE;
if(table.getRowCount()==0)
{
searchTextField.setText("");
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
} 
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
table.setEnabled(true);
}
}
public void setAddMode()
{ 
this.mode=ADD_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void setEditMode()
{ 
this.mode=EDIT_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void setDeleteMode()
{ 
this.mode=DELETE_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
public void setExportToPDFMode()
{ 
this.mode=EXPORT_TO_PDF_MODE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
} 

// inner class starts
class ItemDetailsPanel extends JPanel
{
private JLabel codeCaptionLabel;
private JLabel codeLabel;
private JLabel nameCaptionLabel;
private JLabel nameLabel;
private JTextField nameTextField;
private JButton clearNameTextFieldButton;
private JLabel priceCaptionLabel;
private JLabel priceLabel;
private JTextField priceTextField;
private JButton clearPriceTextFieldButton;
private JLabel categoryCaptionLabel;
private JLabel categoryLabel;
private ButtonGroup categoryButtonGroup;
private JRadioButton finishedGoodsRadioButton;
private JRadioButton consumableRadioButton;
private JRadioButton rawMaterialRadioButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private ItemDTOInterface item;
ItemDetailsPanel()
{ 
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{ 
this.item=null;
codeCaptionLabel=new JLabel("Code : ");
codeLabel=new JLabel("");
nameCaptionLabel=new JLabel("Item : ");
nameLabel=new JLabel("");
nameTextField=new JTextField();
clearNameTextFieldButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\clear.png"));
priceCaptionLabel=new JLabel("Price : ");
priceLabel=new JLabel("");
priceTextField=new JTextField();
priceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
clearPriceTextFieldButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\clear.png"));
categoryCaptionLabel=new JLabel("Category : ");
categoryLabel=new JLabel("");
categoryButtonGroup=new ButtonGroup();
finishedGoodsRadioButton=new JRadioButton("Finished Product");
rawMaterialRadioButton=new JRadioButton("Raw Material");
consumableRadioButton=new JRadioButton("Consumable");
categoryButtonGroup.add(finishedGoodsRadioButton);
categoryButtonGroup.add(rawMaterialRadioButton);
categoryButtonGroup.add(consumableRadioButton);
addButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\add.png"));
editButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\edit.png"));
cancelButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\cancel.png"));
deleteButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\delete.png"));
exportToPDFButton=new JButton(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\pdf.png"));
}
public void setItem(ItemDTOInterface item)
{ 
if(item==null)
{ 
this.codeLabel.setText("");
this.nameLabel.setText("");
this.priceLabel.setText("");
this.categoryLabel.setText("");
} 
else
{ 
this.item=item;
this.codeLabel.setText(String.valueOf(item.getCode()));
this.nameLabel.setText(item.getName());
this.priceLabel.setText(item.getPrice().toPlainString());
this.categoryLabel.setText(item.getCategory());
}
}
private void setAppearance()
{
setLayout(null);
int leftMargin,topMargin;
leftMargin=0;
topMargin=5;
codeCaptionLabel.setBounds(leftMargin+5,topMargin+5,100,30);
nameCaptionLabel.setBounds(leftMargin+5,topMargin+5+30+5,100,30);
priceCaptionLabel.setBounds(leftMargin+5,topMargin+5+30+5+30+5,100,30);
categoryCaptionLabel.setBounds(leftMargin+5,topMargin+5+30+5+30+5+30+5,100,30);
codeLabel.setBounds(leftMargin+5+100,topMargin+5,150,30);
nameLabel.setBounds(leftMargin+5+100,topMargin+5+30+5,300,30);
nameTextField.setBounds(leftMargin+5+100,topMargin+5+30+5,300,30);
clearNameTextFieldButton.setBounds(leftMargin+5+100+300+2,topMargin+5+30+5,30,30);
priceLabel.setBounds(leftMargin+5+100,topMargin+5+30+5+30+5,100,30);
priceTextField.setBounds(leftMargin+5+100,topMargin+5+30+5+30+5,100,30);
clearPriceTextFieldButton.setBounds(leftMargin+5+100+100+2,topMargin+5+30+5+30+5,30,30);
categoryLabel.setBounds(leftMargin+5+100,topMargin+5+30+5+30+5+30+5,200,30);
finishedGoodsRadioButton.setBounds(leftMargin+5+100,topMargin+5+30+5+30+5+30+5,130,30);
rawMaterialRadioButton.setBounds(leftMargin+5+100+130+5,topMargin+5+30+5+30+5+30+5,110,30);
consumableRadioButton.setBounds(leftMargin+5+100+130+5+110+5,topMargin+5+30+5+30+5+30+5,103,30);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
codeCaptionLabel.setFont(dataFont);
nameCaptionLabel.setFont(dataFont);
priceCaptionLabel.setFont(dataFont);
categoryCaptionLabel.setFont(dataFont);
nameTextField.setFont(dataFont);
nameLabel.setFont(dataFont);
priceTextField.setFont(dataFont);
priceLabel.setFont(dataFont);
categoryLabel.setFont(dataFont);
Font radioButtonFont=new Font("Verdana",Font.PLAIN,12);
finishedGoodsRadioButton.setFont(radioButtonFont);
rawMaterialRadioButton.setFont(radioButtonFont);
consumableRadioButton.setFont(radioButtonFont);
addButton.setBounds(112,topMargin+5+30+5+30+5+30+5+30+15,40,40);
editButton.setBounds(112+50,topMargin+5+30+5+30+5+30+5+30+15,40,40);
cancelButton.setBounds(112+50+50,topMargin+5+30+5+30+5+30+5+30+15,40,40);
deleteButton.setBounds(112+50+50+50,topMargin+5+30+5+30+5+30+5+30+15,40,40);
exportToPDFButton.setBounds(112+50+50+50+50,topMargin+5+30+5+30+5+30+5+30+15,40,40);
add(codeCaptionLabel);
add(nameCaptionLabel);
add(priceCaptionLabel);
add(categoryCaptionLabel);
add(codeLabel);
add(nameLabel);
add(nameTextField);
add(clearNameTextFieldButton);
add(priceLabel);
add(priceTextField);
add(clearPriceTextFieldButton);
add(categoryLabel);
add(finishedGoodsRadioButton);
add(rawMaterialRadioButton);
add(consumableRadioButton);
add(addButton);
add(editButton);
add(cancelButton);
add(deleteButton);
add(exportToPDFButton);
this.setViewMode();
}
private void addListeners()
{ 
clearNameTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
nameTextField.setText("");
nameTextField.requestFocus();
}
});

clearPriceTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
priceTextField.setText("");
priceTextField.requestFocus();
}
});

addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{ 
if(ItemPanel.this.mode==VIEW_MODE)
{
ItemPanel.this.setAddMode();
ItemDetailsPanel.this.setAddMode(); //this line is in anonymous class
} 
else
{
String vName;
BigDecimal vPrice;
String vCategory;
vName=nameTextField.getText().trim();
if(vName.length()==0)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Name required");
nameTextField.requestFocus();
return;
}
String tmpPrice=priceTextField.getText().trim();
if(tmpPrice.length()==0)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Price required");
priceTextField.requestFocus();
return;
} 
try
{
vPrice=new BigDecimal(tmpPrice);
}catch(NumberFormatException nfe)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Invalid price");
priceTextField.requestFocus();
return;
} 
if(finishedGoodsRadioButton.isSelected()==false && rawMaterialRadioButton.isSelected()==false && consumableRadioButton.isSelected()==false)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Select item category.");
return;
}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setName(vName);
itemDTO.setPrice(vPrice);
if(finishedGoodsRadioButton.isSelected())
{ 
itemDTO.setCategory("Finished Goods");
} 
if(rawMaterialRadioButton.isSelected())
{ 
itemDTO.setCategory("Raw Material");
} 
if(consumableRadioButton.isSelected())
{ 
itemDTO.setCategory("Consumable");
} 
try
{ 
itemModel.add(itemDTO);
int index=itemModel.getIndexOf(itemDTO);
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,modelException.getMessage());
return;
}
ItemPanel.this.setViewMode();
ItemDetailsPanel.this.setViewMode();
}
}
});
editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{ 
if(ItemPanel.this.mode==VIEW_MODE)
{ 
int selectedRow=table.getSelectedRow();
if(selectedRow<0 || selectedRow>=table.getRowCount())
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Select an item to edit");
return;
}
ItemPanel.this.setEditMode();
ItemDetailsPanel.this.setEditMode();
} 
else
{ // logic to update record
String vName;
BigDecimal vPrice;
String vCategory;
vName=nameTextField.getText().trim();
if(vName.length()==0)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Name required");
nameTextField.requestFocus();
return;
}
String tmpPrice=priceTextField.getText().trim();
if(tmpPrice.length()==0)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Price required");
priceTextField.requestFocus();
return;
} 
try
{
vPrice=new BigDecimal(tmpPrice);
}catch(NumberFormatException nfe)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Invalid price");
priceTextField.requestFocus();
return;
} 
if(finishedGoodsRadioButton.isSelected()==false && rawMaterialRadioButton.isSelected()==false && consumableRadioButton.isSelected()==false)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Select item category.");
return;
}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setCode(ItemDetailsPanel.this.item.getCode());
itemDTO.setName(vName);
itemDTO.setPrice(vPrice);
if(finishedGoodsRadioButton.isSelected())
{ 
itemDTO.setCategory("Finished Goods");
} 
if(rawMaterialRadioButton.isSelected())
{ 
itemDTO.setCategory("Raw Material");
} 
if(consumableRadioButton.isSelected())
{ 
itemDTO.setCategory("Consumable");
} 
try
{ 
itemModel.update(itemDTO);
int index=itemModel.getIndexOf(itemDTO);
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,modelException.getMessage());
return;
}
ItemPanel.this.setViewMode();
ItemDetailsPanel.this.setViewMode();
}
}
});
cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{ 
if(mode==ADD_MODE && ItemDetailsPanel.this.item!=null)
{ 
codeLabel.setText(String.valueOf(ItemDetailsPanel.this.item.getCode()));
}
ItemPanel.this.setViewMode();
ItemDetailsPanel.this.setViewMode();
}
});

deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
int selectedRow=table.getSelectedRow();
if(selectedRow<0 || selectedRow>=table.getRowCount())
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Select an item to delete");
return;
}
ItemDTOInterface itemDTO;
try
{
itemDTO=itemModel.getItemAt(selectedRow);
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,modelException.getMessage());
return;
}
ItemPanel.this.setDeleteMode();
ItemDetailsPanel.this.setDeleteMode();
int selectedOption=JOptionPane.showConfirmDialog(ItemDetailsPanel.this,"Delete "+itemDTO.getName()+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.YES_OPTION)
{
try
{
itemModel.delete(itemDTO.getCode());
ItemDetailsPanel.this.setItem(null);
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Item : "+itemDTO.getName()+" deleted.");
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,modelException.getMessage());
return;
}
}
ItemPanel.this.setViewMode();
ItemDetailsPanel.this.setViewMode();
}
});

exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
ItemPanel.this.setExportToPDFMode();
ItemDetailsPanel.this.setExportToPDFMode();
JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("PDF Files","pdf");
jfc.addChoosableFileFilter(fileFilter);
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(ItemPanel.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File file=jfc.getSelectedFile();
String fullPath=file.getAbsolutePath();
File parent=new File(file.getParent());
if(parent.exists()==false)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Invalid path : "+fullPath);
ItemDetailsPanel.this.setViewMode();
ItemPanel.this.setViewMode();
return;
}
if(parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(ItemDetailsPanel.this,parent.getAbsolutePath()+" is not a folder");
ItemDetailsPanel.this.setViewMode();
ItemPanel.this.setViewMode();
return;
}
if(fullPath.endsWith(".pdf")==false)
{
if(fullPath.endsWith(".")) fullPath+="pdf";
else fullPath+=".pdf";
}
// done
// code to export data to pdf starts here
try
{
com.itextpdf.text.Document document=new com.itextpdf.text.Document();
com.itextpdf.text.pdf.PdfWriter pdfWriter=com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream(fullPath));
document.open();
com.itextpdf.text.Paragraph p=new com.itextpdf.text.Paragraph();
int k=itemModel.getRowCount();
int pageNumber=0;
int pageSize=35;
boolean newPage=true;
com.itextpdf.text.Image logo;
com.itextpdf.text.pdf.PdfPTable table;
com.itextpdf.text.pdf.PdfPCell cell;
com.itextpdf.text.Paragraph paragraph;
com.itextpdf.text.Font firmNameFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,16,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font titleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,14,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font columnTitleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font dataFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.NORMAL);
String firmName="TF Retailers";
table=new PdfPTable(5);
ItemDTOInterface item;
int x=0;
while(x<k)
{
if(newPage)
{
ColumnText.showTextAligned(pdfWriter.getDirectContent(),Element.ALIGN_LEFT, new Phrase("Thinking Machines - Java 'Inventory Management System Project'",columnTitleFont),30,815,0);

paragraph=new com.itextpdf.text.Paragraph(" ");
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

logo=com.itextpdf.text.Image.getInstance("c:\\javaeg\\inventory\\pl\\classes\\images\\logo.jpg");
logo.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
logo.scalePercent(20f);
document.add(logo);

paragraph=new com.itextpdf.text.Paragraph(firmName,firmNameFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

paragraph=new com.itextpdf.text.Paragraph("Items",titleFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

paragraph=new com.itextpdf.text.Paragraph(" ");
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

// create table and add columns/titles
table=new PdfPTable(5);
table.setWidths(new int[]{10,10,30,15,20});

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
cell.setPhrase(new Phrase("S.No.",columnTitleFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
cell.setPhrase(new Phrase("Code",columnTitleFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
cell.setPhrase(new Phrase("Item",columnTitleFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
cell.setPhrase(new Phrase("Price",columnTitleFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
cell.setPhrase(new Phrase("Category",columnTitleFont));
table.addCell(cell);

pageNumber++;
newPage=false;
}
try
{
//add rows
item=itemModel.getItemAt(x);
cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
cell.setPhrase(new Phrase(String.valueOf(x+1),dataFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
cell.setPhrase(new Phrase(String.valueOf(item.getCode()),dataFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_LEFT);
cell.setPhrase(new Phrase(item.getName(),dataFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
cell.setPhrase(new Phrase(item.getPrice().toPlainString(),dataFont));
table.addCell(cell);

cell=new PdfPCell();
cell.setHorizontalAlignment(Element.ALIGN_LEFT);
cell.setPhrase(new Phrase(item.getCategory(),dataFont));
table.addCell(cell);

}catch(ModelException modelException)
{
// do nothing
}
x++;
if(x%pageSize==0 || x==k)
{
document.add(table);
// create footer
ColumnText.showTextAligned(pdfWriter.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Page "+pageNumber,columnTitleFont),550,30, 0);
if(x!=k)
{
document.newPage();
newPage=true;
}
}
}
document.close();
JOptionPane.showMessageDialog(ItemDetailsPanel.this,fullPath+" created");
}catch(Throwable t)
{
System.out.println(t.getMessage());
JOptionPane.showMessageDialog(ItemDetailsPanel.this,"Cannot create pdf, contact administrator");
}
// code to export data to pdf ends here
}
ItemDetailsPanel.this.setViewMode();
ItemPanel.this.setViewMode();
}
});
}
public void setViewMode()
{
nameTextField.setVisible(false);
priceTextField.setVisible(false);
finishedGoodsRadioButton.setVisible(false);
rawMaterialRadioButton.setVisible(false);
consumableRadioButton.setVisible(false);
clearNameTextFieldButton.setVisible(false);
clearPriceTextFieldButton.setVisible(false);
codeLabel.setVisible(true);
nameLabel.setVisible(true);
priceLabel.setVisible(true);
categoryLabel.setVisible(true);
addButton.setIcon(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\add.png"));
editButton.setIcon(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\edit.png"));
if(table.getRowCount()==0)
{ 
addButton.setEnabled(true);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
} 
else
{ 
addButton.setEnabled(true);
editButton.setEnabled(true);
cancelButton.setEnabled(false);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
}
public void setAddMode()
{ 
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
codeLabel.setText("");
nameTextField.setText("");
priceTextField.setText("");
finishedGoodsRadioButton.setSelected(true);
rawMaterialRadioButton.setSelected(false);
consumableRadioButton.setSelected(false);
nameLabel.setVisible(false);
priceLabel.setVisible(false);
categoryLabel.setVisible(false);
nameTextField.setVisible(true);
priceTextField.setVisible(true);
finishedGoodsRadioButton.setVisible(true);
consumableRadioButton.setVisible(true);
rawMaterialRadioButton.setVisible(true);
clearNameTextFieldButton.setVisible(true);
clearPriceTextFieldButton.setVisible(true);
addButton.setIcon(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\save.png"));
}
public void setEditMode()
{ 
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
codeLabel.setText(String.valueOf(this.item.getCode()));
nameTextField.setText(this.item.getName());
priceTextField.setText(this.item.getPrice().toPlainString());
if(this.item.getCategory().equals("Finished Goods"))
{
finishedGoodsRadioButton.setSelected(true);
rawMaterialRadioButton.setSelected(false);
consumableRadioButton.setSelected(false);
} 
if(this.item.getCategory().equals("Raw Material"))
{
finishedGoodsRadioButton.setSelected(false);
rawMaterialRadioButton.setSelected(true);
consumableRadioButton.setSelected(false);
} 
if(this.item.getCategory().equals("Consumable"))
{
finishedGoodsRadioButton.setSelected(false);
rawMaterialRadioButton.setSelected(false);
consumableRadioButton.setSelected(true);
}
nameLabel.setVisible(false);
priceLabel.setVisible(false);
categoryLabel.setVisible(false);
nameTextField.setVisible(true);
priceTextField.setVisible(true);
finishedGoodsRadioButton.setVisible(true);
consumableRadioButton.setVisible(true);
rawMaterialRadioButton.setVisible(true);
clearNameTextFieldButton.setVisible(true);
clearPriceTextFieldButton.setVisible(true);
editButton.setIcon(new ImageIcon("c:\\javaeg\\inventory\\pl\\classes\\images\\save.png"));
}
public void setDeleteMode()
{
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
public void setExportToPDFMode()
{
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
} // inner class ends
}