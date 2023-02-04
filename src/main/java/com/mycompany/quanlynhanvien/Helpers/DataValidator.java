/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Helpers;

import com.mycompany.quanlynhanvien.Dao.NhanVienDao;
import com.mycompany.quanlynhanvien.Dao.NhomSanPhamDao;
import com.mycompany.quanlynhanvien.model.NhomSanPham;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author User
 */
public class DataValidator {
     
       public static void validateEmpty(JTextField field, StringBuilder sb, String errorMessage)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
                /*
       {
          
           String id = field.getText();
           String reg = "";
           if(id.length()>0)
            {
                if(!id.matches(reg))
                { JOptionPane.showMessageDialog(null, "Sai định dạng, nhập lại mã nhân viên, ví dụ:NV002");}
                    
            }else
           {
               JOptionPane.showMessageDialog(null, "Mã Nhân Viên không được để trống!");
           }
       }*/
                    field.setBackground(Color.white);
           }
       
       }
       
       public static void validateEmptyMaNV(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Mã nhân viên không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "^[N][V][0-9]{3}$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại mã nhân viên, ví dụ:NV002").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptyMaSP(JTextField field, StringBuilder sb)
       {
       if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
       {
            sb.append("Mã sản phẩm không được để trống!").append("\n");
            field.setBackground(Color.red);
            field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
       }else{
            field.setBackground(Color.white);
            String id = field.getText();
            String reg = "^[S][P][0-9]{3}$";
       if(!id.matches(reg))
       { sb.append("Sai định dạng, nhập lại mã sản phẩm, ví dụ:SP002").append("\n");field.setBackground(Color.red);}
       }
       }
       
       public static void validateEmptyMaSP_TK(JTextField field, StringBuilder sb)
       {
       if(!field.getText().replaceAll("\\s","").equals("")){
            field.setBackground(Color.white);
            String id = field.getText();
            String reg = "^[Ss][Pp][0-9]{3}$";
       if(!id.matches(reg))
       { sb.append("Sai định dạng, nhập lại mã sản phẩm, ví dụ:SP002").append("\n");}
       }      
       }
       
       
       public static void validateEmptyMaTG(JTextField field, StringBuilder sb)
       {
       if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
       {
            sb.append("Mã tác giả không được để trống!").append("\n");
            field.setBackground(Color.red);
            field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
       }else{
            field.setBackground(Color.white);
            String id = field.getText();
            String reg = "^[T][G][0-9]{3}$";
       if(!id.matches(reg))
       { sb.append("Sai định dạng, nhập lại mã tác giả, ví dụ:TG002").append("\n");field.setBackground(Color.red);}
            }
       }
       
       public static void validateEmptyMaKH(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có trống ""
           {
               sb.append("Mã khách hàng không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String[] id = field.getText().split("-");
               String reg = "^[K][H][0-9]{3}$";
                if(!id[0].matches(reg))
                            { sb.append("Sai định dạng, nhập lại mã khách hàng, ví dụ:KH002").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptyDonGia(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có trống ""
           {
               sb.append("Đơn giá không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);   
               String text = field.getText();
               String id = field.getText(); 
               float donGia = 0;
               try {
               
               donGia = Float.parseFloat(text);
                   if(donGia%1==0)
                   {
                       id = text+".00";
                   }
               } catch (Exception e) {
                   String reg = "[\\d]+[.][\\d]+";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại đơn giá").append("\n");field.setBackground(Color.red);return;} 
               
               }
               
               
                
               if(donGia<=0)
                   { sb.append("Đơn giá phải lớn hơn 0").append("\n");field.setBackground(Color.red);}
           }
       }
       
       public static void validateEmptyDonGia_TK(JTextField field, StringBuilder sb)
       {
           if(!field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có trống ""
           {
              
             
               field.setBackground(Color.white);   
               String text = field.getText();
               String id = field.getText(); 
               float donGia = 0;
               try {
               
               donGia = Float.parseFloat(text);
                   if(donGia%1==0)
                   {
                       id = text+".00";
                   }
               } catch (Exception e) {
                   String reg = "[\\d]+[.][\\d]+";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại đơn giá").append("\n");return;} 
               
               }
               
               
                
               if(donGia<=0)
                   { sb.append("Đơn giá phải lớn hơn 0").append("\n");field.setBackground(Color.red);}
           }
       }
       
        public static void validateEmptyEmail(JTextField field, StringBuilder sb)
       {
              if(field.getText().replaceAll("\\s","").equals("")){return;}
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại email, ví dụ:abc34@gmail.com").append("\n");field.setBackground(Color.red);}      
        }
        
       public static void validateEmptyDiaChi(JTextField field, StringBuilder sb)
       {
              if(field.getText().replaceAll("\\s","").equals("")){return;}
               String id = field.getText();
               String reg = "^[A-Z a-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ0-9/]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại địa chỉ").append("\n");field.setBackground(Color.red);}      
        }
       
       public static void validateEmptyTenNV(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Không được để trống tên!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "^[A-Z a-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại tên").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptyTen_TK(JTextField field, StringBuilder sb)
       {
           if(!field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
                 
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "^[A-Z a-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại tên").append("\n");}      
           }
       }
       
       public static void validateEmptySDT(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Số điện thoại không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "[0-9]{10}";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại số điện thoại, ví dụ:0931241jqk").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptySDTKoTrung(JTextField field, StringBuilder sb) throws Exception
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Số điện thoại không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               NhanVienDao dao = new NhanVienDao();
               if(dao.findSDT(field.getText())!=null)
               {
                  sb.append("Đã có nhân viên sữ dụng số điện thoại này").append("\n");field.setBackground(Color.red);return; 
               }
               String reg = "[0-9]{10}";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại số điện thoại, ví dụ:0931241jqk").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptyTuoi(JSpinner field, StringBuilder sb)
       {
           int tuoi = (int) field.getValue();field.setBackground(Color.white);  
           if(tuoi<1)//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Sai định dạng, nhập lại tuổi").append("\n");field.setBackground(Color.red);      
           }
       }
       
       public static void validateEmptySoLuong(JSpinner field, StringBuilder sb)
       {
           field.setBackground(Color.white);
           int soLuong = (int) field.getValue();  
           if(soLuong<1)//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Sai định dạng, nhập lại số lượng").append("\n");field.setBackground(Color.red);      
           }
       }
       
        public static void validateEmptySDTLMK(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Số điện thoại không được để trống!").append("\n");
               field.setForeground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               Color cl = new Color(0,204,204);
               field.setBackground(cl);          
               String id = field.getText();
               String reg = "[0-9]{10}";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại số điện thoại, ví dụ:0931241000").append("\n");field.setForeground(Color.red);}      
           }
       }
        
       public static void validateEmptyChucVu(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Chức vụ không được để trống!").append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "^[A-Z a-z]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại chức vụ").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmptyMaTK(JTextField field, StringBuilder sb)
       {
           if(field.getText().replaceAll("\\s","").equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               
           }else{   
               field.setBackground(Color.white);          
               String id = field.getText();
               String reg = "[T][K][0-9]{3}";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại MaTK, ví dụ: TK002").append("\n");field.setBackground(Color.red);}      
           }
       }
       
       public static void validateEmpty(JPasswordField field, StringBuilder sb, String errorMessage)
       {
           String password = new String(field.getPassword());
           if(field.equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{
                  
                    field.setBackground(Color.white);
           }
       
       }
       
       public static void validateEmptyNhapLai(JPasswordField field, StringBuilder sb, String errorMessage)
       {
           String password = new String(field.getPassword());
           if(field.equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{
                  
                    field.setBackground(Color.white);
           }
       
       }

    public static void validateEmpty(JDateChooser field, StringBuilder sb, String errorMessage) {
        
        if(null==field.getDate())//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setBackground(Color.red);
               field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
           }else{
                    field.setBackground(Color.white);
           }
    }

    public static void validateEmptyTenDN(JTextField field, StringBuilder sb, String errorMessage) {
         //To change body of generated methods, choose Tools | Templates.
         if(field.getText().equals("")||field.getText().equals("Tên đăng nhập"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setForeground(Color.red);
               
           }else{   
                Color cl = new Color(255,255,255);
            field.setForeground(cl);
          
          
                   
           }
         
    }

    public static void validateEmptyMatKhau(JPasswordField field, StringBuilder sb, String errorMessage) {
        String password = new String(field.getPassword());
           if(password.equals("")||password.equals("Mật khẫu"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setForeground(Color.red);
               
           }else{
                  Color cl = new Color(0,204,204);
                    field.setForeground(cl);
           } //To change body of generated methods, choose Tools | Templates.
    }
    
         public static void validateEmptyMatKhau1(JPasswordField field, StringBuilder sb, String errorMessage) {
        String password = new String(field.getPassword());
           if(password.equals("")||password.equals("Mật khẫu"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append(errorMessage).append("\n");
               field.setBackground(Color.red);
               
           }else{
                  
                    field.setBackground(Color.white);
                    String id = new String(field.getPassword());
               String reg = "^[A-Za-z0-9/!@#$%^&*()_+-=.?]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại mật khẩu").append("\n");field.setBackground(Color.red);}      
           
           } //To change body of generated methods, choose Tools | Templates.
    }    

    public static void validateEmptyTenNhap(JTextField field, StringBuilder sb) {
        String tenDN = field.getText();
           if(tenDN.equals(""))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Tên đăng nhập không được để trống").append("\n");
               field.setBackground(Color.red);
               
           }else{
                  
                    field.setBackground(Color.white);
                    String id = new String(field.getText());
               String reg = "^[A-Za-z0-9/!@#$%^&*()_+-=.?]+$";
                if(!id.matches(reg))
                            { sb.append("Sai định dạng, nhập lại tên đăng nhập").append("\n");field.setBackground(Color.red);}      
           
           } //To change body of generated methods, choose Tools | Templates.
    }

    public static void validateEmptyCBXNhomSP(JComboBox<String> cbx, StringBuilder sb)  {
        cbx.setBackground(Color.white); 
        String chon = cbx.getSelectedItem().toString();
           if(chon.equals("Chọn nhóm sản phẩm"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Vui lòng chọn nhóm sản phẩm").append("\n");
               cbx.setBackground(Color.red);
               
           }/*else{
           NhomSanPhamDao dao = new NhomSanPhamDao();
           NhomSanPham nsp = dao.findMaNhomSPByTen(chon);
           if(nsp==null)
           {sb.append("Nhóm sản phẩm không tồn tại, vui lòng nhập lại nhóm sản phẩm").append("\n");
           cbx.setBackground(Color.red);}
           }*/
    }
    
    public static void validateEmptyCBXNhaCC(JComboBox<String> cbx, StringBuilder sb) {
        cbx.setBackground(Color.white); 
        String chon = cbx.getSelectedItem().toString();
           if(chon.equals("Chọn nhà cung cấp"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Vui lòng chọn nhà cung cấp").append("\n");
               cbx.setBackground(Color.red);
               
           }
    }
    
    public static void validateEmptyCBXTacGia(JComboBox<String> cbx, StringBuilder sb) {
        cbx.setBackground(Color.white); 
        String chon = cbx.getSelectedItem().toString();
           if(chon.equals("Chọn tác giả"))//kiểm tra dữ liệu của field có giống ""
           {
               sb.append("Vui lòng chọn tác giả").append("\n");
               cbx.setBackground(Color.red);
               
           }
    }

    public static void validateEmptyMaNCC(JTextField field, StringBuilder sb) {
        if(field.getText().equals(""))//kiểm tra dữ liệu của field có giống ""
       {
            sb.append("Mã nhà cung cấp không được để trống!").append("\n");
            field.setBackground(Color.red);
            field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
       }else{
            field.setBackground(Color.white);
            String id = field.getText();
            String reg = "^[N][C][C][0-9]{3}$";
       if(!id.matches(reg))
       { sb.append("Sai định dạng, nhập lại mã nhà cung cấp, ví dụ:NCC002").append("\n");field.setBackground(Color.red);}
            }//To change body of generated methods, choose Tools | Templates.
    }

    public static void validateEmptyMaNSP(JTextField field, StringBuilder sb) {
         if(field.getText().equals(""))//kiểm tra dữ liệu của field có giống ""
       {
            sb.append("Mã nhóm sản phẩm không được để trống!").append("\n");
            field.setBackground(Color.red);
            field.requestFocus();//Trỏ con trỏ đến trường đang lỗi
       }else{
            field.setBackground(Color.white);
            String id = field.getText();
            String reg = "^[N][S][P][0-9]{3}$";
       if(!id.matches(reg))
       { sb.append("Sai định dạng, nhập lại mã nhóm sản phẩm, ví dụ:NSP002").append("\n");field.setBackground(Color.red);}
            } //To change body of generated methods, choose Tools | Templates.
    }
}
