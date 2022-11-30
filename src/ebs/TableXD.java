
package ebs;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class TableXD extends javax.swing.JFrame {
    ArrayList<ElectricBikes> list = new ArrayList<>(); //tao bien list dang object ElectricBikes
    DefaultTableModel model;
    String fURL; // lay url file
    public TableXD() {
        initComponents();
        this.setLocationRelativeTo(null);
        Object [] obj;
        model = (DefaultTableModel) ElectricBikesTable.getModel();
        model.setColumnIdentifiers(
            new Object[]{
                "Number", "ID", "Marker", "Type", "Color", "Price"
            }
        );
    }

        //      Set Textfield back empty
    public void Resetboxtext () {
        txtPrice.setText("");
        txtMarker.setText("");
        txtType.setText("");
        txtID.setText("");
        txtColor.setText("");
        txtID.requestFocus(); // Back focus ID text
    }
    
        //      Back to Table
    public void BackToTable() {
        model.setRowCount(0);
        for(int h=0;h < list.size(); h++){
        Object[] objsB = {h+1, list.get(h).getID(), list.get(h).getMarker(), list.get(h).getType(), list.get(h).getColor(),  list.get(h).getPrice()};
        model.addRow(objsB);
        }
    }
    public void AddEB () {
        if (txtID.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(ElectricBikesTable, "Input the number of ID");
            txtID.requestFocus(); // dua con tro chuôt vê ô dang nhâp
        }
        if (txtPrice.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(ElectricBikesTable, "Input the number of Price");
            txtPrice.requestFocus(); // dua con tro chuôt vê ô dang nhâp
        }
        String sID = txtID.getText();
        String sMarker = txtMarker.getText();
        String sType = txtType.getText();
        String sColor = txtColor.getText();
        String sPrice = txtPrice.getText();
            // get Text for Object
        ElectricBikes x = new ElectricBikes();
        x.setMarker(sMarker);
        x.setType(sType);
        x.setColor(sColor);
        int InputID = Integer.valueOf(sID);
        // add Obj to list
        if (CheckForm()){
        try {
            x.setID(InputID);
    //                x.setMaSoXe(Integer.parseInt(txtMaSoXe.getText())); // ép kieu Text ID sang kieu int
        } catch (NumberFormatException e) {
        };
        try {
            x.setPrice(Double.valueOf(sPrice));
    //                x.setGia(Double.parseDouble(txtGia.getText()));
        } catch (NumberFormatException e) {
        };
            list.add(x);
            showResult(); // Show on Table
            try { // ghi list vào file
                Ghifilelist (list);
            } catch (IOException ex) {
                Logger.getLogger(TableXD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        int i = 1; // addRow
    public void showResult(){
        ElectricBikes xl = list.get(list.size()-1);
        model.addRow(new Object[]{
            i++, xl.getID(), xl.getMarker(), xl.getType(), xl.getColor(), xl.getPrice()
        });
    }
    
    // CheckForm
    public boolean CheckForm () {
        if (txtID.getText().isEmpty()||txtPrice.getText().isEmpty()) {
            return false;
        } else { 
            return true;
        }
    }

    public void NoticeStatus (){
        if (CheckForm()) {
            StatusNotice.setText("Added done, saved file in D:/");
            StatusNotice.setForeground(Color.blue);
        } else {
            StatusNotice.setText("Added not done");
            StatusNotice.setForeground(Color.red);
        }
        }
    
    // Ghi file
    public void Ghifilelist (List <ElectricBikes> wxd) throws IOException {
        FileOutputStream os =null;
        ObjectOutputStream oos = null;
    try {
        File file = new File("D:/dddd.data");
        os = new FileOutputStream(file);
        oos = new ObjectOutputStream(os);
        oos.writeObject(wxd);
        oos.flush();
        oos.close();
        }
    catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("Đã ghi xong file");
    }
    
    // Ðoc 1 file
    public void Choosefile () {
        //          Choose file
        JFileChooser fileChooser = new JFileChooser(); //khoi tao chon file
        FileNameExtensionFilter filelist = new FileNameExtensionFilter("File list", "data" ); // loc loai file duoc chon, o day la *.data
        fileChooser.setFileFilter(filelist); //truyên filelist cho fileChooser
        fileChooser.setMultiSelectionEnabled(true); //true cho phep nguoi dung chon nhieu file cung luc
        int x = fileChooser.showDialog(this, "Choose File"); //set dô hoa cho chon file - this: hien thi file len jframe, "Chooser File": botton Choose File
        if (x == JFileChooser.APPROVE_OPTION) { // x == JFileChooser.APPROVE_OPTION là neu user da chon file 
            File f = fileChooser.getSelectedFile(); //thì cho phep lay; neu "file []" - lua chon nhieu file cung luc
            fURL = f.getAbsolutePath(); // lay url file vua select
        }
    }
    public void Docfilelist () {
        FileInputStream is = null;
        ObjectInputStream ois = null;
        var resultList = new ArrayList<ElectricBikes>();
        // Try catch Docfilelist()
        try{
            is = new FileInputStream(fURL);
            ois = new ObjectInputStream(is);
            resultList = (ArrayList<ElectricBikes>) ois.readObject();
                    /* in ra console resultList */
                for (var obj : resultList) {
                    System.out.println(obj);
                    System.out.println("Readed Filelist on Table");
                }
                    // addRow in TableXD
            for(int k=0;k < resultList.size(); k++){
            Object[] objs = {k+1, resultList.get(k).getID(), resultList.get(k).getMarker(), resultList.get(k).getType(), resultList.get(k).getColor(),  resultList.get(k).getPrice()};
            model.addRow(objs);
            }
        ois.close();
        is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        list = resultList;
    }

        // Search
    public ElectricBikes FindByID (String id) {
        id = txtID.getText();
        for (var findx : list){
            if (findx.getID() == Integer.valueOf(id)) {
            return findx;
            }
        }
    return null;
    }
        
    public void FindInTable (){
        if (txtID.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Input ID");
        } else {
        ElectricBikes ebFind = FindByID(txtID.getText().trim()); //trim(): trim di khoang trang thua o 2 dau
            if (ebFind != null) {
            StatusNotice.setText("Found ID: " + txtID.getText());
            StatusNotice.setForeground(Color.blue);
            // set table show eb on FindbyID
                Object[] oeb = {"Found ==>", ebFind.getID(), ebFind.getMarker(), ebFind.getType(), ebFind.getColor(),  ebFind.getPrice()};
                txtID.setText(ebFind.getID()+""); // convert int ID ==> String bang cach ' + "" '
                txtMarker.setText(ebFind.getMarker());
                txtType.setText(ebFind.getType());
                txtColor.setText(ebFind.getColor());
                txtPrice.setText(ebFind.getPrice()+"");
                model.setRowCount(0);
                model.addRow(oeb);
            } else {
            String InputSearchByID = txtID.getText();
            Resetboxtext ();
            model.setRowCount(0);
            txtID.setText(InputSearchByID);
            StatusNotice.setText("Not found ID " + InputSearchByID);
            StatusNotice.setForeground(Color.red);
            BackToTable();
            }
        }
    }
    
        // Delete Row
    public int SelRemoveEB (String id) {
        id = txtID.getText();
        ElectricBikes eb = FindByID(id);
        if (eb != null) {
            list.remove(eb);
            return 1;
        }
        return -1;
    }
    public void DelEB () {
        if (txtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Input ID for Remove");
        } else {
            if (SelRemoveEB(txtID.getText().trim())>0) {
            StatusNotice.setText("Remove done ID: " + txtID.getText());
            StatusNotice.setForeground(Color.blue);
            } else {
            StatusNotice.setText("Not Found ID: " + txtID.getText());
            StatusNotice.setForeground(Color.red);
            }
        }
    }
    
        //Get & show object from table to textfield
    public void GetEB () {
        String IDGet; 
        String MarkerGet;
        String TypeGet;
        String ColorGet;
        String PriceGet;
        int row = ElectricBikesTable.getSelectedRow();
        IDGet = ElectricBikesTable.getModel().getValueAt(row, 1).toString();
        MarkerGet = ElectricBikesTable.getModel().getValueAt(row, 2).toString();
        TypeGet = ElectricBikesTable.getModel().getValueAt(row, 3).toString();
        ColorGet = ElectricBikesTable.getModel().getValueAt(row, 4).toString();
        PriceGet = ElectricBikesTable.getModel().getValueAt(row, 5).toString();
        txtID.setText(IDGet);
        txtMarker.setText(MarkerGet);
        txtType.setText(TypeGet);
        txtColor.setText(ColorGet);
        txtPrice.setText(PriceGet);
    }
    
        //Edit Object
    public void EditEB () {
        if (txtID.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Input ID");
        } else {
        ElectricBikes ebEdit = FindByID(txtID.getText().trim()); //trim(): trim di khoang trang thua o 2 dau
            if (ebEdit != null) {
            StatusNotice.setText("Edited ID: " + txtID.getText());
            StatusNotice.setForeground(Color.blue);
                ebEdit.setID(Integer.valueOf(txtID.getText()));
                ebEdit.setMarker(txtMarker.getText());
                ebEdit.setType(txtType.getText());
                ebEdit.setColor(txtColor.getText());
                ebEdit.setPrice(Double.valueOf(txtPrice.getText()));
            } else {
            int InputSearchByID = Integer.valueOf(txtID.getText());
            Resetboxtext ();
            model.setRowCount(0);
            txtID.setText(InputSearchByID+"");
            StatusNotice.setText("Not found ID " + InputSearchByID);
            StatusNotice.setForeground(Color.red);
            }
        }        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ElectricBikesTable = new javax.swing.JTable();
        StatusNotice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtMarker = new javax.swing.JTextField();
        txtType = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        OpenFile = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ElectricBikesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ElectricBikesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ElectricBikesTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(ElectricBikesTable);

        StatusNotice.setText(" ");

        jLabel2.setText("ID");

        jLabel3.setText("Marker");

        jLabel4.setText("Tpye");

        jLabel5.setText("Color");

        jLabel6.setText("Price");

        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnDelete.setText("Del EB");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        OpenFile.setText("Open");
        OpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenFileActionPerformed(evt);
            }
        });
        jMenu1.add(OpenFile);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StatusNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID)
                            .addComponent(txtPrice)
                            .addComponent(txtColor)
                            .addComponent(txtType)
                            .addComponent(txtMarker)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExit)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnReload)
                                    .addComponent(btnDelete))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(StatusNotice)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMarker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReload)
                    .addComponent(btnReset)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        btnReset.setMnemonic(KeyEvent.VK_R);
        Resetboxtext();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        btnAdd.setMnemonic(KeyEvent.VK_ENTER);
        txtID.requestFocus(); // Focus back txtID text
        AddEB ();
        NoticeStatus ();
        BackToTable ();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        btnDelete.setMnemonic(KeyEvent.VK_D);
        txtID.requestFocus();
        DelEB();
        BackToTable ();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        btnEdit.setMnemonic(KeyEvent.VK_E);
        EditEB ();
        BackToTable ();
    }//GEN-LAST:event_btnEditActionPerformed

    private void OpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenFileActionPerformed
        OpenFile.setMnemonic(KeyEvent.VK_O);
        Choosefile();
        Docfilelist();
    }//GEN-LAST:event_OpenFileActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        btnSearch.setMnemonic(KeyEvent.VK_S);
        FindInTable();
        txtID.requestFocus();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        btnReload.setMnemonic(KeyEvent.VK_B);
        BackToTable();
        Resetboxtext();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void ElectricBikesTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ElectricBikesTableMousePressed
        GetEB ();
    }//GEN-LAST:event_ElectricBikesTableMousePressed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    public static void main(String args[]) {
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableXD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ElectricBikesTable;
    private javax.swing.JMenuItem OpenFile;
    private javax.swing.JLabel StatusNotice;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMarker;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables

}
