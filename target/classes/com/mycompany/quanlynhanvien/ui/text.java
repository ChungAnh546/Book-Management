/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quanlynhanvien.ui;

import com.mycompany.quanlynhanvien.Dao.ChiTietHoaDonDao;
import com.mycompany.quanlynhanvien.Dao.HoaDonDao;
import com.mycompany.quanlynhanvien.Dao.KhachHangDao;
import com.mycompany.quanlynhanvien.Dao.NhanVienDao;
import com.mycompany.quanlynhanvien.Dao.NhomSanPhamDao;
import com.mycompany.quanlynhanvien.Dao.SanPhamDao;
import com.mycompany.quanlynhanvien.Helpers.ConnectDB;
import com.mycompany.quanlynhanvien.Helpers.DataValidator;
import com.mycompany.quanlynhanvien.Helpers.ImageHelper;
import com.mycompany.quanlynhanvien.Helpers.MessageDialogHelper;
import com.mycompany.quanlynhanvien.Helpers.SharedData;
import com.mycompany.quanlynhanvien.Thread.ClockThread;
import com.mycompany.quanlynhanvien.model.ChiTietHoaDon;
import com.mycompany.quanlynhanvien.model.HoaDon;
import com.mycompany.quanlynhanvien.model.KhachHang;
import com.mycompany.quanlynhanvien.model.NhanVien;
import com.mycompany.quanlynhanvien.model.NhomSanPham;
import com.mycompany.quanlynhanvien.model.SanPham;
import com.mycompany.quanlynhanvien.model.TaiKhoan;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author User
 */
public class text extends javax.swing.JFrame {
    private DefaultTableModel tblModel;
    private DefaultTableModel tblModel_XemCTHD;
    private DefaultTableModel tblModel_QLHD;
    private DefaultTableModel tblModel_QLKH;
    private QLSPForm QLSPForm;
    private QLNSPForm QLNSPForm;
    private TacGiaForm TacGiaForm;
    private NhaCungCapForm NhaCungCapForm;
    private byte[] personalImage;   
    Double totalAmount = 0.0;
    Double cash = 0.0;
    Double balance = 0.0;
    Double bHeight = 0.0;
    HoaDon hoaDonIn = new HoaDon();
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> subtotal = new ArrayList<>();
    List<ChiTietHoaDon> chitiethoadon = new ArrayList<>();
    /**
     * Creates new form text
     */
    public text() {
        initComponents();
       
        pn_TrangChu.setVisible(true);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
         //LoginJDialog login = new LoginJDialog(this,true);
        //login.setVisible(true);
        setTTTaiKhoan_Menu();
        initClock();
    }

    private void initClock() {
        ClockThread dongHo = new ClockThread(lbl_Clock_Menu);
        dongHo.start();
    }
  private void setTTTaiKhoan_Menu ()
  {
      NhanVien nv = new NhanVien();
      nv=SharedData.nhanVienDN;
      if(nv.getHinh()==null){
      ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg"));
            Image img = ImageHelper.resize(icon.getImage(), 80, 86);
            ImageIcon resizedIcon = new ImageIcon(img);
            lbl_Hinh_Menu.setIcon(resizedIcon);
      }else{
      ImageIcon icon = new ImageIcon(nv.getHinh());
            Image img = ImageHelper.resize(icon.getImage(), 80, 86);
            ImageIcon resizedIcon = new ImageIcon(img);
            lbl_Hinh_Menu.setIcon(resizedIcon);
      }
      lbl_TenTaiKhoan_Menu.setText("Chào, "+nv.getTenNhanVien());
      lbl_ChucVu_Menu.setText("Chức vụ: "+nv.getChucVu() );
  }
  class myTableCellRenderer implements TableCellRenderer {
    
        public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected, boolean hasFocus,int row, int column)
        {try {
                TableColumn tb = tbl_DanhSachNV_QLNV.getColumn("Hình nhân viên");
            tb.setMaxWidth(150);
            tb.setMinWidth(150);
            tbl_DanhSachNV_QLNV.setRowHeight(150);
            return (Component) value;
            } catch (Exception e) {
                return  null;
            }
            
        }                            
    }
  private void initTable()
    {
        tblModel = new DefaultTableModel(){

   @Override
   public boolean isCellEditable(int row, int column) {
       //Only the third column
       return column == -1;
   }
};
        tblModel.setColumnIdentifiers(new String [] {"Mã Nhân Viên","Tên Nhân Viên","Giới Tính","Ngày Sinh","Số Điện Thoại","Chức Vụ","Tình Trạng","MaTK","Hình nhân viên"});
        tbl_DanhSachNV_QLNV.setModel(tblModel);
        tbl_DanhSachNV_QLNV.getColumn("Hình nhân viên").setCellRenderer( new myTableCellRenderer());
    }
  private void initTableXemCTHD()
    {
        tblModel_XemCTHD = new DefaultTableModel(){

   @Override
   public boolean isCellEditable(int row, int column) {
       //Only the third column
       return column == -1;
   }
};
        tblModel_XemCTHD.setColumnIdentifiers(new String [] {"Mã Sản phẩm","Tên Sản phẩm","Số Lượng","Đơn Giá","Thành Tiền"});
        tb_XemCTHD.setModel(tblModel_XemCTHD);
        
    }
    private void initTableQLHD()
    {
        tblModel_QLHD = new DefaultTableModel(){

   @Override
    public boolean isCellEditable(int row, int column) {
       //Only the third column
       return column == -1;
    }
    };
        tblModel_QLHD.setColumnIdentifiers(new String [] {"Mã Hóa Đơn","Mã Nhân Viên","Mã Khách Hàng","Ngày Lập","Tổng Tiền","Ghi Chú"});
        tbl_DanhSachHD_QLHD.setModel(tblModel_QLHD);
        
    }
    private void processLoginSuccessful()
    {
        
    }
    private void loadDataToTable()
    {
        try {
            NhanVienDao dao = new NhanVienDao();
            List<NhanVien> list = dao.findAll();
            tblModel.setRowCount(0);
            for(NhanVien it : list)
            {
                JLabel lbl_Hinh_TbNV = new JLabel();
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                ImageIcon icon =new ImageIcon();
                if(it.getHinh()!=null)
                {
                     icon = new ImageIcon(it.getHinh());
                     lbl_Hinh_TbNV.setIcon(icon);
                }else{                  
                       icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg"));
                       lbl_Hinh_TbNV.setIcon(icon);                 
                }
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel.addRow(new Object[]
                { it.getMaNhanVien(),it.getTenNhanVien(),it.getGioiTinh(),it.getNgaySinh(),it.getsDT(),it.getChucVu(),it.getTinhTrang(),it.getMaTK(),lbl_Hinh_TbNV} );
                
                tblModel.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }
    }
    private void loadDataToTable_QLHD()
    {
        try {
            HoaDonDao dao = new HoaDonDao();
            List<HoaDon> list = dao.findAll();
            tblModel_QLHD.setRowCount(0);
            for(HoaDon it : list)
            {
                
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel_QLHD.addRow(new Object[]
                { it.getMaHD(),it.getNhanVien().getMaNhanVien(),it.getKhachHang().getMaKH(),it.getNgayLap(),it.getTongTien(),it.getGhiChu()} );
                
                tblModel_QLHD.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }
    }
    private void restartTable()
    {
        tbl_DanhSachNV_QLNV.clearSelection();
        loadDataToTable();
    }
    public void loadcbxmaTK()
    {
        try {
            NhanVienDao dao = new NhanVienDao();
            List<String> list = dao.findMaTK();
            cbx_MaTK_T.removeAllItems();
            cbx_MaTK_T.addItem("");
            String tenTK;
            for(String it : list)
            {
                tenTK = dao.findTenTK(it.replaceAll("\\s",""));
                cbx_MaTK_T.addItem(it.replaceAll("\\s","") +"-"+ tenTK );
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }
    }
    private void setTT_FormTK() throws ParseException, IOException 
    {
        NhanVien nv = SharedData.nhanVienDN;
        TaiKhoan tk = SharedData.taiKhoanDN;
        if(nv!=null)
            {
                txt_MaNV_QLTK.setText(nv.getMaNhanVien().replaceAll("\\s",""));
                txt_TenNV_QLTK.setText(nv.getTenNhanVien());
                Date ns0 = new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgaySinh());
                String ns1 = new SimpleDateFormat("MM-dd-yyyy").format(ns0);
                Date ns2 = new SimpleDateFormat("MM-dd-yyyy").parse(ns1);
                dc_Ngaysinh_QLTK.setDate(ns2);
                if(nv.getGioiTinh().replaceAll("\\s","").equals("Nam"))
                {rb_Nam_QLTK.setSelected(true);}else{rb_Nu_QLTK.setSelected(true);}
                //MessageDialogHelper.showConfirmDialog(null, nv.getTinhTrang(), "Cảnh báo");
                             
                txt_SDT_QLTK.setText(nv.getsDT().replaceAll("\\s",""));
                txt_ChucVu_QLTK.setText(nv.getChucVu());
                txt_MaTK_QLTK.setText(tk.getMaTK());
                txt_TenTK_QLTK.setText(tk.getTenTK());
                if(nv.getHinh()!=null)
                {
                    Image img = ImageHelper.CreateImageFromByteArray(nv.getHinh(), "jpg");
                    lbl_Hinh_QLTK.setIcon(new ImageIcon(img));
                    personalImage = nv.getHinh();
                    
                }else{
                    personalImage = nv.getHinh();
                     ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                        lbl_Hinh_QLTK.setIcon(icon);}
            }
    }
    public String tuDongLayMa() {
		String maNV = "";
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = " DECLARE @ID VARCHAR(5)\r\n"
					+ " IF (SELECT COUNT(maNhanVien) FROM NhanVien) = 0\r\n"
					+ " SET @ID = '0'\r\n" 
                                        + " ELSE\r\n"
					+ " SELECT @ID = MAX(RIGHT(rtrim(maNhanVien), 3)) FROM NhanVien\r\n"
                                        + " SELECT @ID = CASE\r\n"
                                        + " WHEN @ID >= 0 and @ID < 9 THEN 'NV00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)\r\n"      
                                        + " WHEN @ID >= 9 THEN 'NV0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)\r\n"
                                        + " END\r\n"
                                        + " select @ID" ;       		
                                
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String ID = rs.getString(1);
				maNV = ID;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maNV;
	}
     public String tuDongLayMaQLKH() {
     String maKH = "";
		try {
			Connection con = ConnectDB.getInstance().getConnection();
			String sql = " DECLARE @ID VARCHAR(5)\r\n"
					+ " IF (SELECT COUNT(maKH) FROM KhachHang) = 0\r\n"
					+ " SET @ID = '0'\r\n" 
                                        + " ELSE\r\n"
					+ " SELECT @ID = MAX(RIGHT(rtrim(maKH), 3)) FROM KhachHang\r\n"
                                        + " SELECT @ID = CASE\r\n"
                                        + " WHEN @ID >= 0 and @ID < 9 THEN 'KH00' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)\r\n"      
                                        + " WHEN @ID >= 9 THEN 'KH0' + CONVERT(CHAR, CONVERT(INT, @ID) + 1)\r\n"
                                        + " END\r\n"
                                        + " select @ID" ;       		
                                
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String ID = rs.getString(1);
				maKH = ID;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return maKH;
     
     }
    private void NhapTTCapNhapTTable() throws HeadlessException {
        
            try {
            int row = tbl_DanhSachNV_QLNV.getSelectedRow();
            //MessageDialogHelper.showConfirmDialog(null, ""+row, "Cảnh báo");
            if(row>=0)
            {
            String id = (String) tbl_DanhSachNV_QLNV.getValueAt(row, 0);
            NhanVienDao dao = new NhanVienDao();
            NhanVien nv = dao.find(id);
            
            if(nv!=null)
            {
                txt_MaNV_CN.setText(nv.getMaNhanVien().replaceAll("\\s",""));
                txt_TenNV_CN.setText(nv.getTenNhanVien());
                Date ns0 = new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgaySinh());
                String ns1 = new SimpleDateFormat("MM-dd-yyyy").format(ns0);
                Date ns2 = new SimpleDateFormat("MM-dd-yyyy").parse(ns1);
                dc_NgaySinh_CN.setDate(ns2);
                if(nv.getGioiTinh().replaceAll("\\s","").equals("Nam"))
                {rb_GioiTinh_Nam_CN.setSelected(true);}else{rb_GioiTinh_Nu_CN.setSelected(true);}
                //MessageDialogHelper.showConfirmDialog(null, nv.getTinhTrang(), "Cảnh báo");
                if(nv.getTinhTrang().replaceAll("\\s","").equals("Vẫnlàmviệc"))
                {rb_VanLamViec_CN.setSelected(true);}
                else{rb_DaNghiViec_CN.setSelected(true);}              
                txt_SDT_CN.setText(nv.getsDT().replaceAll("\\s",""));
                cbx_ChucVu_CN.setSelectedIndex(nv.getChucVu().replaceAll("\\s","").equals("Quảnlý")?1:0);
                cbx_maTK_CN.removeAllItems();
                
                cbx_maTK_CN.addItem(nv.getMaTK()==null?"":(nv.getMaTK().replaceAll("\\s","")+"-"+dao.findTenTK(nv.getMaTK())));
                if(nv.getHinh()!=null)
                {
                    Image img = ImageHelper.CreateImageFromByteArray(nv.getHinh(), "jpg");
                    lbl_Hinh_CN.setIcon(new ImageIcon(img));
                    personalImage = nv.getHinh();
                    
                }else{
                    personalImage = nv.getHinh();
                     ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                        lbl_Hinh_CN.setIcon(icon);}
            }}else{cbx_maTK_CN.removeAllItems();}
                
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getStackTrace(),"Lỗi",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private void resetTable() {
        tblModel.setRowCount(0);
        loadDataToTable();
    }
    
    private void resetTextFiled(JDateChooser field) {
         field.setDate(null);
         field.setBackground(Color.white);//To change body of generated methods, choose Tools | Templates.
    }
    private void resetThongTinThemNVForm() throws HeadlessException {
        resetTextFiled(txt_TenNV_T);
        resetTextFiled(dc_NgaySinh_T);
        resetTextFiled(txt_SDT_T);
        txt_MaNV_T.setText(tuDongLayMa());
        rb_Nam_T.setSelected(true);
        cbx_ChucVu_T.setSelectedIndex(0);
        loadcbxmaTK();
        //resetTextFiled(txt_ChucVu_T);
    }
    private void restartFormCapNhap() throws HeadlessException {
        resetTextFiled(txt_MaNV_CN);
        resetTextFiled(txt_TenNV_CN);
        resetTextFiled(dc_NgaySinh_CN);
        resetTextFiled(txt_SDT_CN);
        //resetTextFiled(txt_ChucVu_CN);
        rb_GioiTinh_Nam_CN.setSelected(true);
        rb_VanLamViec_CN.setSelected(true);
        //String id = (String) tbl_DanhSachNV_QLNV.getValueAt(tbl_DanhSachNV_QLNV.getSelectedRow(), 7);
        NhapTTCapNhapTTable();
        loadcbxmaTK_CN();
    }
    
     private void resetTextFiled(JTextField field) throws HeadlessException {
       field.setText("");
       field.setBackground(Color.white);
    }
    private void loadcbxmaTK_CN() {
         try {
            NhanVienDao dao = new NhanVienDao();
            List<String> list = dao.findMaTK();            
            //cbx_maTK_CN.addItem(maTK);
            cbx_maTK_CN.addItem("");
            String tenTK;
            for(String it : list)
            {
                tenTK = dao.findTenTK(it.replaceAll("\\s",""));
                cbx_maTK_CN.addItem(it.replaceAll("\\s","")+"-"+tenTK);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        ThemNVForm = new javax.swing.JDialog();
        pn_TieuDe_T = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_TieuDe_T = new javax.swing.JLabel();
        pn_ChucNang_T = new javax.swing.JPanel();
        btn_ThemMoi_T = new javax.swing.JButton();
        btn_Huy_T = new javax.swing.JButton();
        lbl_ChucVu_T = new javax.swing.JLabel();
        txt_SDT_T = new javax.swing.JTextField();
        lbl_SDT_T = new javax.swing.JLabel();
        dc_NgaySinh_T = new com.toedter.calendar.JDateChooser();
        lbl_NgaySinh_T = new javax.swing.JLabel();
        rb_Nam_T = new javax.swing.JRadioButton();
        rb_Nu_T = new javax.swing.JRadioButton();
        lbl_GioiTinh_T = new javax.swing.JLabel();
        txt_TenNV_T = new javax.swing.JTextField();
        lbl_TenNV_T = new javax.swing.JLabel();
        txt_MaNV_T = new javax.swing.JTextField();
        lbl_MaNV_T = new javax.swing.JLabel();
        lbl_MaTK_T = new javax.swing.JLabel();
        cbx_ChucVu_T = new javax.swing.JComboBox<>();
        cbx_MaTK_T = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_HinhNV_T = new javax.swing.JLabel();
        lbl_Hinh_T = new javax.swing.JLabel();
        btn_ChonHinh_T = new javax.swing.JButton();
        CapNhapNVForm = new javax.swing.JDialog();
        pn_TieuDeCN = new javax.swing.JPanel();
        lbl_TieuCN = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        pn_ChucNang_CN = new javax.swing.JPanel();
        btn_CapNhap_CN = new javax.swing.JButton();
        btn_Huy_CN = new javax.swing.JButton();
        lbl_ChucVu_CN = new javax.swing.JLabel();
        lbl_TinhTrang_CN = new javax.swing.JLabel();
        rb_VanLamViec_CN = new javax.swing.JRadioButton();
        rb_DaNghiViec_CN = new javax.swing.JRadioButton();
        txt_SDT_CN = new javax.swing.JTextField();
        lbl_SDT_CN = new javax.swing.JLabel();
        lbl_NgaySinh_CN = new javax.swing.JLabel();
        lbl_GioiTinh_CN = new javax.swing.JLabel();
        rb_GioiTinh_Nam_CN = new javax.swing.JRadioButton();
        rb_GioiTinh_Nu_CN = new javax.swing.JRadioButton();
        lbl_TenNV_CN = new javax.swing.JLabel();
        txt_TenNV_CN = new javax.swing.JTextField();
        txt_MaNV_CN = new javax.swing.JTextField();
        lbl_MaNV_CN = new javax.swing.JLabel();
        dc_NgaySinh_CN = new com.toedter.calendar.JDateChooser();
        lbl_MaTK_CN = new javax.swing.JLabel();
        cbx_ChucVu_CN = new javax.swing.JComboBox<>();
        cbx_maTK_CN = new javax.swing.JComboBox<>();
        lbl_HinhNV_CN = new javax.swing.JLabel();
        lbl_Hinh_CN = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_ChonHinh_CN = new javax.swing.JButton();
        btng_NamNu_QLNV = new javax.swing.ButtonGroup();
        btng_NamNu_T = new javax.swing.ButtonGroup();
        btng_NamNu_CN = new javax.swing.ButtonGroup();
        btng_TinhTrang_CN = new javax.swing.ButtonGroup();
        btng_NamNu_QLTK = new javax.swing.ButtonGroup();
        CapNhapKHForm = new javax.swing.JDialog();
        pn_TieuDe_CNKH = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbl_TieuDe_CNKH = new javax.swing.JLabel();
        pn_ChucNang_CNKH = new javax.swing.JPanel();
        btn_CapNhap_CNKH = new javax.swing.JButton();
        btn_Huy_CNKH = new javax.swing.JButton();
        lbl_ChucVu_CNKH = new javax.swing.JLabel();
        txt_SDT_CNKH = new javax.swing.JTextField();
        lbl_SDT_CNKH = new javax.swing.JLabel();
        lbl_DiaChi_CNKH = new javax.swing.JLabel();
        txt_TenKH_CNKH = new javax.swing.JTextField();
        lbl_TenKH_CNKH = new javax.swing.JLabel();
        lbl_MaKH_CNKH = new javax.swing.JLabel();
        lbl_Tuoi_CNKH = new javax.swing.JLabel();
        txt_DiaChi_CNKH = new javax.swing.JTextField();
        txt_Email_CNKH = new javax.swing.JTextField();
        sn_Tuoi_CNKH = new javax.swing.JSpinner();
        txt_MaKH_CNKH = new javax.swing.JTextField();
        ThemKHForm = new javax.swing.JDialog();
        pn_TieuDe_TKH2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lbl_TieuDe_TKH2 = new javax.swing.JLabel();
        pn_ChucNang_TKH2 = new javax.swing.JPanel();
        btn_ThemMoi_TKH2 = new javax.swing.JButton();
        btn_Huy_TKH2 = new javax.swing.JButton();
        lbl_ChucVu_TKH2 = new javax.swing.JLabel();
        txt_SDT_TKH2 = new javax.swing.JTextField();
        lbl_SDT_TKH2 = new javax.swing.JLabel();
        lbl_DiaChi_TKH2 = new javax.swing.JLabel();
        txt_TenKH_TKH2 = new javax.swing.JTextField();
        lbl_TenKH_TKH2 = new javax.swing.JLabel();
        txt_MaKH_TKH1 = new javax.swing.JTextField();
        lbl_MaKH_TKH2 = new javax.swing.JLabel();
        lbl_Tuoi_TKH2 = new javax.swing.JLabel();
        txt_DiaChi_TKH2 = new javax.swing.JTextField();
        txt_Email_TKH2 = new javax.swing.JTextField();
        sn_Tuoi_TKH2 = new javax.swing.JSpinner();
        XemCTHDForm = new javax.swing.JDialog();
        sp_XemCTHD = new javax.swing.JScrollPane();
        tb_XemCTHD = new javax.swing.JTable();
        lbl_MaHD_XemCTHD = new javax.swing.JLabel();
        lbl_TongTien_XemCTHD = new javax.swing.JLabel();
        lbl_TTNV_XemCTHD = new javax.swing.JLabel();
        lbl_TTKH_XemCTHD = new javax.swing.JLabel();
        lbl_NgayLap_XemCTHD = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pn_TieuDe_QLNV = new javax.swing.JPanel();
        lbl_TieuDe_QLNV = new javax.swing.JLabel();
        pn_Menu_QLNV = new javax.swing.JPanel();
        btn_TrangChu_QLNV = new javax.swing.JButton();
        btn_QLKH_QLNV = new javax.swing.JButton();
        btn_QLNV_QLNV = new javax.swing.JButton();
        btn_QLSP_QLNV = new javax.swing.JButton();
        btn_QLHD_QLNV = new javax.swing.JButton();
        btn_ThongKe_QLNV = new javax.swing.JButton();
        btn_TaiKhoan_QLNV = new javax.swing.JButton();
        pn_TaiKhoan_Menu = new javax.swing.JPanel();
        lbl_Hinh_Menu = new javax.swing.JLabel();
        lbl_TenTaiKhoan_Menu = new javax.swing.JLabel();
        lbl_ChucVu_Menu = new javax.swing.JLabel();
        btn_DoiMK_Menu = new javax.swing.JButton();
        btn_DangXuat_Menu = new javax.swing.JButton();
        lbl_Clock_Menu = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pn_TrangChu = new javax.swing.JPanel();
        lbl_Hinh = new javax.swing.JLabel();
        pn_QLNV = new javax.swing.JPanel();
        pn_TenForm_QLNV = new javax.swing.JPanel();
        lbl_TenForm_QLNV = new javax.swing.JLabel();
        pn_CNTimK_QLNV = new javax.swing.JPanel();
        lbl_MaNV_QLNV = new javax.swing.JLabel();
        lbl_TenNV_QLNV = new javax.swing.JLabel();
        lbl_ChucVu_QLNV = new javax.swing.JLabel();
        lbl_GioiTinh_QLNV = new javax.swing.JLabel();
        txt_ChucVu_QLNV = new javax.swing.JTextField();
        txt_MaNV_QLNV = new javax.swing.JTextField();
        txt_TenNV_QLNV = new javax.swing.JTextField();
        rb_Nam_QLNV = new javax.swing.JRadioButton();
        rb_Nu_QLNV = new javax.swing.JRadioButton();
        scp_DanhSachNV_QLNV = new javax.swing.JScrollPane();
        tbl_DanhSachNV_QLNV = new javax.swing.JTable();
        pn_ChucNang_QLNV = new javax.swing.JPanel();
        btn_ThemNV_QLNV = new javax.swing.JButton();
        btn_Xoa_QLNV = new javax.swing.JButton();
        btn_CapNhap_QLNV = new javax.swing.JButton();
        btn_TimKiem_QLNV = new javax.swing.JButton();
        btn_CapNhapBang_QLNV = new javax.swing.JButton();
        pn_QLTK = new javax.swing.JPanel();
        lbl_Tieu_TaiKhoan = new javax.swing.JLabel();
        lbl_TTTaiKhoan = new javax.swing.JLabel();
        lbl_MaTK = new javax.swing.JLabel();
        lbl_TenNV = new javax.swing.JLabel();
        lbl_GioiTinh = new javax.swing.JLabel();
        lbl_Ngaysinh = new javax.swing.JLabel();
        lbl_SDT = new javax.swing.JLabel();
        lbl_ChucVu = new javax.swing.JLabel();
        txt_MaNV_QLTK = new javax.swing.JTextField();
        txt_TenNV_QLTK = new javax.swing.JTextField();
        txt_SDT_QLTK = new javax.swing.JTextField();
        txt_ChucVu_QLTK = new javax.swing.JTextField();
        dc_Ngaysinh_QLTK = new com.toedter.calendar.JDateChooser();
        rb_Nam_QLTK = new javax.swing.JRadioButton();
        rb_Nu_QLTK = new javax.swing.JRadioButton();
        btn_DoiMK = new javax.swing.JButton();
        btn_TaoTK = new javax.swing.JButton();
        btn_Dangxuat = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        lbl_HinhNV_QLTK = new javax.swing.JLabel();
        lbl_Hinh_QLTK = new javax.swing.JLabel();
        lbl_MaTK_QLTK = new javax.swing.JLabel();
        lbl_TenTK_QLTK = new javax.swing.JLabel();
        txt_MaTK_QLTK = new javax.swing.JTextField();
        txt_TenTK_QLTK = new javax.swing.JTextField();
        pn_QLKH = new javax.swing.JPanel();
        pn_TenForm_QLKH = new javax.swing.JPanel();
        lbl_TenForm_QLKH = new javax.swing.JLabel();
        pn_CNTimK_QLKH = new javax.swing.JPanel();
        lbl_MaKH_QLKH = new javax.swing.JLabel();
        lbl_TenKH_QLKH = new javax.swing.JLabel();
        lbl_SDT_QLKH = new javax.swing.JLabel();
        lbl_Tuoi_QLKH = new javax.swing.JLabel();
        txt_SDT_QLKH = new javax.swing.JTextField();
        txt_MaKH_QLKH = new javax.swing.JTextField();
        txt_TenKH_QLKH = new javax.swing.JTextField();
        sn_Tuoi_QLKH = new javax.swing.JSpinner();
        scp_DanhSachKH_QLKH = new javax.swing.JScrollPane();
        tbl_DanhSachKH_QLKH = new javax.swing.JTable();
        pn_ChucNang_QLKH = new javax.swing.JPanel();
        btn_ThemKH_QLKH = new javax.swing.JButton();
        btn_Xoa_QLKH = new javax.swing.JButton();
        btn_CapNhap_QLKH = new javax.swing.JButton();
        btn_TimKiem_QLKH = new javax.swing.JButton();
        btn_CapNhapBang_QLKH = new javax.swing.JButton();
        pn_QLHD = new javax.swing.JPanel();
        pn_QLHD_Form = new javax.swing.JPanel();
        pn_TenForm_QLHD = new javax.swing.JPanel();
        lbl_TenForm_QLHD = new javax.swing.JLabel();
        pn_CNTimK_QLHD = new javax.swing.JPanel();
        lbl_MaHD_QLHD = new javax.swing.JLabel();
        lbl_NgayLap_QLHD = new javax.swing.JLabel();
        lbl_MaNV_QLHD = new javax.swing.JLabel();
        lbl_MaKH_QLHD = new javax.swing.JLabel();
        txt_maNV_QLHD = new javax.swing.JTextField();
        txt_MaHD_QLHD = new javax.swing.JTextField();
        dc_NgayLap_QLHD = new com.toedter.calendar.JDateChooser();
        txt_maKH_QLHD = new javax.swing.JTextField();
        scp_DanhSachHD_QLHD = new javax.swing.JScrollPane();
        tbl_DanhSachHD_QLHD = new javax.swing.JTable();
        pn_ChucNang_QLHD = new javax.swing.JPanel();
        btn_LapHD_QLHD = new javax.swing.JButton();
        btn_XemChiTiet_QLHD = new javax.swing.JButton();
        btn_In_QLHD = new javax.swing.JButton();
        btn_TimKiem_QLHD = new javax.swing.JButton();
        btn_CapNhapBang_QLHD = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        tbpn_QLSP = new javax.swing.JTabbedPane();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        ThemNVForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ThemNVForm.setLocation(new java.awt.Point(0, 0));
        ThemNVForm.setMinimumSize(new java.awt.Dimension(730, 500));
        ThemNVForm.setModal(true);
        ThemNVForm.setName("ThemNVForm"); // NOI18N

        pn_TieuDe_T.setBackground(new java.awt.Color(51, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Trí Tuệ Việt");

        javax.swing.GroupLayout pn_TieuDe_TLayout = new javax.swing.GroupLayout(pn_TieuDe_T);
        pn_TieuDe_T.setLayout(pn_TieuDe_TLayout);
        pn_TieuDe_TLayout.setHorizontalGroup(
            pn_TieuDe_TLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TieuDe_TLayout.setVerticalGroup(
            pn_TieuDe_TLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        lbl_TieuDe_T.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_TieuDe_T.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe_T.setText("Nhập thông tin nhân viên");
        lbl_TieuDe_T.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pn_ChucNang_T.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_ThemMoi_T.setBackground(new java.awt.Color(51, 255, 51));
        btn_ThemMoi_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ThemMoi_T.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_ThemMoi_T.setText("Thêm mới");
        btn_ThemMoi_T.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemMoi_TActionPerformed(evt);
            }
        });

        btn_Huy_T.setBackground(new java.awt.Color(51, 255, 51));
        btn_Huy_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Huy_T.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-unavailable-24.png"))); // NOI18N
        btn_Huy_T.setText("Hủy");
        btn_Huy_T.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Huy_TActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_TLayout = new javax.swing.GroupLayout(pn_ChucNang_T);
        pn_ChucNang_T.setLayout(pn_ChucNang_TLayout);
        pn_ChucNang_TLayout.setHorizontalGroup(
            pn_ChucNang_TLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_TLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ThemMoi_T)
                .addGap(69, 69, 69)
                .addComponent(btn_Huy_T, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        pn_ChucNang_TLayout.setVerticalGroup(
            pn_ChucNang_TLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_TLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_ChucNang_TLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ThemMoi_T)
                    .addComponent(btn_Huy_T))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lbl_ChucVu_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu_T.setText("Chức vụ:");

        txt_SDT_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SDT_T.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDT_TActionPerformed(evt);
            }
        });

        lbl_SDT_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT_T.setText("Số điện thoại:");

        dc_NgaySinh_T.setDateFormatString("MM-dd-yyyy");

        lbl_NgaySinh_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_NgaySinh_T.setText("Ngày sinh:");

        btng_NamNu_T.add(rb_Nam_T);
        rb_Nam_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nam_T.setText("Nam");

        btng_NamNu_T.add(rb_Nu_T);
        rb_Nu_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nu_T.setText("Nữ");

        lbl_GioiTinh_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_GioiTinh_T.setText("Giới tính:");

        txt_TenNV_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbl_TenNV_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenNV_T.setText("Tên nhân viên:");

        txt_MaNV_T.setEditable(false);
        txt_MaNV_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbl_MaNV_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaNV_T.setText("Mã nhân viên:");

        lbl_MaTK_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaTK_T.setText("Mã tài khoản:");

        cbx_ChucVu_T.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên bán hàng", "Quản lý" }));

        cbx_MaTK_T.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbl_HinhNV_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_HinhNV_T.setText("Hình nhân viên");
        lbl_HinhNV_T.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbl_Hinh_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Hinh_T.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Hinh_T.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn_ChonHinh_T.setBackground(new java.awt.Color(102, 255, 102));
        btn_ChonHinh_T.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ChonHinh_T.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-imgur-24.png"))); // NOI18N
        btn_ChonHinh_T.setText("Chọn hình");
        btn_ChonHinh_T.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonHinh_TActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ThemNVFormLayout = new javax.swing.GroupLayout(ThemNVForm.getContentPane());
        ThemNVForm.getContentPane().setLayout(ThemNVFormLayout);
        ThemNVFormLayout.setHorizontalGroup(
            ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_TieuDe_T, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TieuDe_T, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_ChucNang_T, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ThemNVFormLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SDT_T)
                            .addComponent(lbl_ChucVu_T, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_SDT_T)
                            .addComponent(cbx_ChucVu_T, 0, 210, Short.MAX_VALUE)))
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_NgaySinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_MaNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_TenNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_GioiTinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dc_NgaySinh_T, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_TenNV_T, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_MaNV_T, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addGroup(ThemNVFormLayout.createSequentialGroup()
                                .addComponent(rb_Nam_T, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_Nu_T)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_MaTK_T, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbx_MaTK_T, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(lbl_HinhNV_T))
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ThemNVFormLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(btn_ChonHinh_T))
                            .addComponent(lbl_Hinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        ThemNVFormLayout.setVerticalGroup(
            ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThemNVFormLayout.createSequentialGroup()
                .addComponent(pn_TieuDe_T, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TieuDe_T, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThemNVFormLayout.createSequentialGroup()
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ThemNVFormLayout.createSequentialGroup()
                                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_MaNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_MaNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_TenNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_TenNV_T, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rb_Nu_T)
                                    .addComponent(rb_Nam_T)
                                    .addComponent(lbl_GioiTinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_NgaySinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dc_NgaySinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_SDT_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_SDT_T, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(ThemNVFormLayout.createSequentialGroup()
                                .addComponent(lbl_HinhNV_T)
                                .addGap(12, 12, 12)
                                .addComponent(lbl_Hinh_T, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_ChonHinh_T)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(ThemNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ChucVu_T, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_ChucVu_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_MaTK_T)
                            .addComponent(cbx_MaTK_T, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_ChucNang_T, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        CapNhapNVForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        CapNhapNVForm.setLocation(new java.awt.Point(0, 0));
        CapNhapNVForm.setMinimumSize(new java.awt.Dimension(730, 600));
        CapNhapNVForm.setModal(true);

        pn_TieuDeCN.setBackground(new java.awt.Color(0, 255, 255));
        pn_TieuDeCN.setForeground(new java.awt.Color(0, 255, 255));

        lbl_TieuCN.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_TieuCN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuCN.setText("Trí Tuệ Việt");

        javax.swing.GroupLayout pn_TieuDeCNLayout = new javax.swing.GroupLayout(pn_TieuDeCN);
        pn_TieuDeCN.setLayout(pn_TieuDeCNLayout);
        pn_TieuDeCNLayout.setHorizontalGroup(
            pn_TieuDeCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_TieuCN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TieuDeCNLayout.setVerticalGroup(
            pn_TieuDeCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_TieuCN, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl.setText("Nhập thông tin nhân viên");
        lbl.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pn_ChucNang_CN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_CapNhap_CN.setBackground(new java.awt.Color(51, 255, 51));
        btn_CapNhap_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhap_CN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_CapNhap_CN.setText("Cập nhập");
        btn_CapNhap_CN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhap_CNActionPerformed(evt);
            }
        });

        btn_Huy_CN.setBackground(new java.awt.Color(51, 255, 51));
        btn_Huy_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Huy_CN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-unavailable-24.png"))); // NOI18N
        btn_Huy_CN.setText("Hủy");
        btn_Huy_CN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Huy_CNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_CNLayout = new javax.swing.GroupLayout(pn_ChucNang_CN);
        pn_ChucNang_CN.setLayout(pn_ChucNang_CNLayout);
        pn_ChucNang_CNLayout.setHorizontalGroup(
            pn_ChucNang_CNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_CNLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_CapNhap_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(btn_Huy_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        pn_ChucNang_CNLayout.setVerticalGroup(
            pn_ChucNang_CNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_ChucNang_CNLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pn_ChucNang_CNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CapNhap_CN)
                    .addComponent(btn_Huy_CN))
                .addGap(20, 20, 20))
        );

        lbl_ChucVu_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu_CN.setText("Chức vụ:");

        lbl_TinhTrang_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TinhTrang_CN.setText("Tình trạng:");

        btng_TinhTrang_CN.add(rb_VanLamViec_CN);
        rb_VanLamViec_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_VanLamViec_CN.setText("Vẫn làm việc");

        btng_TinhTrang_CN.add(rb_DaNghiViec_CN);
        rb_DaNghiViec_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_DaNghiViec_CN.setText("Đã nghỉ việc");

        txt_SDT_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SDT_CN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDT_CNActionPerformed(evt);
            }
        });

        lbl_SDT_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT_CN.setText("Số điện thoại");

        lbl_NgaySinh_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_NgaySinh_CN.setText("Ngày sinh:");

        lbl_GioiTinh_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_GioiTinh_CN.setText("Giới tính:");

        btng_NamNu_CN.add(rb_GioiTinh_Nam_CN);
        rb_GioiTinh_Nam_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_GioiTinh_Nam_CN.setText("Nam");

        btng_NamNu_CN.add(rb_GioiTinh_Nu_CN);
        rb_GioiTinh_Nu_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_GioiTinh_Nu_CN.setText("Nữ ");

        lbl_TenNV_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenNV_CN.setText("Tên nhân viên:");

        txt_TenNV_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_TenNV_CN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_TenNV_CNFocusLost(evt);
            }
        });

        txt_MaNV_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_MaNV_CN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_MaNV_CNFocusLost(evt);
            }
        });
        txt_MaNV_CN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_MaNV_CNMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_MaNV_CNMouseExited(evt);
            }
        });

        lbl_MaNV_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaNV_CN.setText("Mã nhân viên:");

        dc_NgaySinh_CN.setDateFormatString("MM-dd-yyyy");
        dc_NgaySinh_CN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dc_NgaySinh_CNFocusLost(evt);
            }
        });

        lbl_MaTK_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaTK_CN.setText("Mã tài Khoản:");

        cbx_ChucVu_CN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân viên bán hàng", "Quản lý" }));

        cbx_maTK_CN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_HinhNV_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_HinhNV_CN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_HinhNV_CN.setText("Hình Nhân viên");

        lbl_Hinh_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Hinh_CN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Hinh_CN.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btn_ChonHinh_CN.setBackground(new java.awt.Color(102, 255, 102));
        btn_ChonHinh_CN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ChonHinh_CN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-imgur-24.png"))); // NOI18N
        btn_ChonHinh_CN.setText("Chọn hình");
        btn_ChonHinh_CN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonHinh_CNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CapNhapNVFormLayout = new javax.swing.GroupLayout(CapNhapNVForm.getContentPane());
        CapNhapNVForm.getContentPane().setLayout(CapNhapNVFormLayout);
        CapNhapNVFormLayout.setHorizontalGroup(
            CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_MaNV_CN)
                                .addComponent(lbl_TenNV_CN, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lbl_NgaySinh_CN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(lbl_GioiTinh_CN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                    .addComponent(rb_GioiTinh_Nam_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rb_GioiTinh_Nu_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_TenNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_MaNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dc_NgaySinh_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_TinhTrang_CN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_SDT_CN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_SDT_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addComponent(rb_VanLamViec_CN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rb_DaNghiViec_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addComponent(lbl_ChucVu_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(cbx_ChucVu_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lbl_MaTK_CN)
                                .addGap(18, 18, 18)
                                .addComponent(cbx_maTK_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(lbl_HinhNV_CN))
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(btn_ChonHinh_CN))
                                    .addComponent(lbl_Hinh_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(pn_TieuDeCN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_ChucNang_CN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CapNhapNVFormLayout.setVerticalGroup(
            CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                .addComponent(pn_TieuDeCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_MaNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_MaNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_HinhNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_TenNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_TenNV_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_GioiTinh_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rb_GioiTinh_Nam_CN)
                                    .addComponent(rb_GioiTinh_Nu_CN))
                                .addGap(33, 33, 33)
                                .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dc_NgaySinh_CN, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                    .addComponent(lbl_NgaySinh_CN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(CapNhapNVFormLayout.createSequentialGroup()
                                .addComponent(lbl_Hinh_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(btn_ChonHinh_CN)))
                        .addGap(33, 33, 33)
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_SDT_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_SDT_CN, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(lbl_ChucVu_CN, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(cbx_ChucVu_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(CapNhapNVFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_TinhTrang_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rb_VanLamViec_CN)
                            .addComponent(rb_DaNghiViec_CN)
                            .addComponent(lbl_MaTK_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_maTK_CN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_ChucNang_CN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        CapNhapKHForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        CapNhapKHForm.setLocation(new java.awt.Point(0, 0));
        CapNhapKHForm.setMinimumSize(new java.awt.Dimension(400, 550));
        CapNhapKHForm.setModal(true);
        CapNhapKHForm.setName("ThemNVForm"); // NOI18N

        pn_TieuDe_CNKH.setBackground(new java.awt.Color(51, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Trí Tuệ Việt");

        javax.swing.GroupLayout pn_TieuDe_CNKHLayout = new javax.swing.GroupLayout(pn_TieuDe_CNKH);
        pn_TieuDe_CNKH.setLayout(pn_TieuDe_CNKHLayout);
        pn_TieuDe_CNKHLayout.setHorizontalGroup(
            pn_TieuDe_CNKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TieuDe_CNKHLayout.setVerticalGroup(
            pn_TieuDe_CNKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        lbl_TieuDe_CNKH.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_TieuDe_CNKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe_CNKH.setText("Nhập thông tin khách hàng");

        btn_CapNhap_CNKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_CapNhap_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhap_CNKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_CapNhap_CNKH.setText("Cập nhập");
        btn_CapNhap_CNKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhap_CNKHActionPerformed(evt);
            }
        });

        btn_Huy_CNKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_Huy_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Huy_CNKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-unavailable-24.png"))); // NOI18N
        btn_Huy_CNKH.setText("Hủy");
        btn_Huy_CNKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Huy_CNKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_CNKHLayout = new javax.swing.GroupLayout(pn_ChucNang_CNKH);
        pn_ChucNang_CNKH.setLayout(pn_ChucNang_CNKHLayout);
        pn_ChucNang_CNKHLayout.setHorizontalGroup(
            pn_ChucNang_CNKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_CNKHLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btn_CapNhap_CNKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(btn_Huy_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        pn_ChucNang_CNKHLayout.setVerticalGroup(
            pn_ChucNang_CNKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_CNKHLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pn_ChucNang_CNKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CapNhap_CNKH)
                    .addComponent(btn_Huy_CNKH))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        lbl_ChucVu_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu_CNKH.setText("Email:");

        txt_SDT_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SDT_CNKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDT_CNKHActionPerformed(evt);
            }
        });

        lbl_SDT_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT_CNKH.setText("Số điện thoại:");

        lbl_DiaChi_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_DiaChi_CNKH.setText("Địa chỉ:");

        txt_TenKH_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbl_TenKH_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenKH_CNKH.setText("Tên khách hàng:");

        lbl_MaKH_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaKH_CNKH.setText("Mã khách hàng:");

        lbl_Tuoi_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Tuoi_CNKH.setText("Tuổi:");

        txt_DiaChi_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_DiaChi_CNKH.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txt_Email_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        sn_Tuoi_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_MaKH_CNKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout CapNhapKHFormLayout = new javax.swing.GroupLayout(CapNhapKHForm.getContentPane());
        CapNhapKHForm.getContentPane().setLayout(CapNhapKHFormLayout);
        CapNhapKHFormLayout.setHorizontalGroup(
            CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_TieuDe_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TieuDe_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_ChucNang_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CapNhapKHFormLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CapNhapKHFormLayout.createSequentialGroup()
                        .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SDT_CNKH)
                            .addComponent(lbl_ChucVu_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Tuoi_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_SDT_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_Email_CNKH)
                            .addComponent(sn_Tuoi_CNKH)))
                    .addGroup(CapNhapKHFormLayout.createSequentialGroup()
                        .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_TenKH_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_DiaChi_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_MaKH_CNKH))
                        .addGap(18, 18, 18)
                        .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_TenKH_CNKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_DiaChi_CNKH)
                            .addComponent(txt_MaKH_CNKH))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        CapNhapKHFormLayout.setVerticalGroup(
            CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CapNhapKHFormLayout.createSequentialGroup()
                .addComponent(pn_TieuDe_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TieuDe_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_MaKH_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_MaKH_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TenKH_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TenKH_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_DiaChi_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CapNhapKHFormLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_DiaChi_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_SDT_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_SDT_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Email_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lbl_ChucVu_CNKH, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CapNhapKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Tuoi_CNKH)
                    .addComponent(sn_Tuoi_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 38, Short.MAX_VALUE)
                .addComponent(pn_ChucNang_CNKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ThemKHForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ThemKHForm.setLocation(new java.awt.Point(0, 0));
        ThemKHForm.setMinimumSize(new java.awt.Dimension(400, 550));
        ThemKHForm.setModal(true);
        ThemKHForm.setName("ThemNVForm"); // NOI18N

        pn_TieuDe_TKH2.setBackground(new java.awt.Color(51, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Trí Tuệ Việt");

        javax.swing.GroupLayout pn_TieuDe_TKH2Layout = new javax.swing.GroupLayout(pn_TieuDe_TKH2);
        pn_TieuDe_TKH2.setLayout(pn_TieuDe_TKH2Layout);
        pn_TieuDe_TKH2Layout.setHorizontalGroup(
            pn_TieuDe_TKH2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TieuDe_TKH2Layout.setVerticalGroup(
            pn_TieuDe_TKH2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        lbl_TieuDe_TKH2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_TieuDe_TKH2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe_TKH2.setText("Nhập thông tin khách hàng");

        btn_ThemMoi_TKH2.setBackground(new java.awt.Color(51, 255, 51));
        btn_ThemMoi_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ThemMoi_TKH2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_ThemMoi_TKH2.setText("Thêm mới");
        btn_ThemMoi_TKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemMoi_TKH2ActionPerformed(evt);
            }
        });

        btn_Huy_TKH2.setBackground(new java.awt.Color(51, 255, 51));
        btn_Huy_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Huy_TKH2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-unavailable-24.png"))); // NOI18N
        btn_Huy_TKH2.setText("Hủy");
        btn_Huy_TKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Huy_TKH2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_TKH2Layout = new javax.swing.GroupLayout(pn_ChucNang_TKH2);
        pn_ChucNang_TKH2.setLayout(pn_ChucNang_TKH2Layout);
        pn_ChucNang_TKH2Layout.setHorizontalGroup(
            pn_ChucNang_TKH2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_TKH2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btn_ThemMoi_TKH2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(btn_Huy_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        pn_ChucNang_TKH2Layout.setVerticalGroup(
            pn_ChucNang_TKH2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_TKH2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pn_ChucNang_TKH2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ThemMoi_TKH2)
                    .addComponent(btn_Huy_TKH2))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        lbl_ChucVu_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu_TKH2.setText("Email:");

        txt_SDT_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SDT_TKH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDT_TKH2ActionPerformed(evt);
            }
        });

        lbl_SDT_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT_TKH2.setText("Số điện thoại:");

        lbl_DiaChi_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_DiaChi_TKH2.setText("Địa chỉ:");

        txt_TenKH_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbl_TenKH_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenKH_TKH2.setText("Tên khách hàng:");

        txt_MaKH_TKH1.setEditable(false);
        txt_MaKH_TKH1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lbl_MaKH_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaKH_TKH2.setText("Mã khách hàng:");

        lbl_Tuoi_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Tuoi_TKH2.setText("Tuổi:");

        txt_DiaChi_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_Email_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        sn_Tuoi_TKH2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout ThemKHFormLayout = new javax.swing.GroupLayout(ThemKHForm.getContentPane());
        ThemKHForm.getContentPane().setLayout(ThemKHFormLayout);
        ThemKHFormLayout.setHorizontalGroup(
            ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_TieuDe_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TieuDe_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_ChucNang_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ThemKHFormLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThemKHFormLayout.createSequentialGroup()
                        .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SDT_TKH2)
                            .addComponent(lbl_ChucVu_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Tuoi_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_SDT_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_Email_TKH2)
                            .addComponent(sn_Tuoi_TKH2)))
                    .addGroup(ThemKHFormLayout.createSequentialGroup()
                        .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_TenKH_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_DiaChi_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_MaKH_TKH2))
                        .addGap(18, 18, 18)
                        .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_TenKH_TKH2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_MaKH_TKH1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(txt_DiaChi_TKH2))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        ThemKHFormLayout.setVerticalGroup(
            ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThemKHFormLayout.createSequentialGroup()
                .addComponent(pn_TieuDe_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TieuDe_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_MaKH_TKH1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaKH_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TenKH_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TenKH_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_DiaChi_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ThemKHFormLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_DiaChi_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_SDT_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_SDT_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Email_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lbl_ChucVu_TKH2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ThemKHFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Tuoi_TKH2)
                    .addComponent(sn_Tuoi_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 32, Short.MAX_VALUE)
                .addComponent(pn_ChucNang_TKH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        XemCTHDForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        XemCTHDForm.setMinimumSize(new java.awt.Dimension(517, 500));

        tb_XemCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        sp_XemCTHD.setViewportView(tb_XemCTHD);

        lbl_MaHD_XemCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaHD_XemCTHD.setText("jLabel1");

        lbl_TongTien_XemCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TongTien_XemCTHD.setText("jLabel1");

        lbl_TTNV_XemCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TTNV_XemCTHD.setText("jLabel1");

        lbl_TTKH_XemCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TTKH_XemCTHD.setText("jLabel1");

        lbl_NgayLap_XemCTHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_NgayLap_XemCTHD.setText("jLabel1");

        javax.swing.GroupLayout XemCTHDFormLayout = new javax.swing.GroupLayout(XemCTHDForm.getContentPane());
        XemCTHDForm.getContentPane().setLayout(XemCTHDFormLayout);
        XemCTHDFormLayout.setHorizontalGroup(
            XemCTHDFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
            .addComponent(lbl_MaHD_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TongTien_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TTNV_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_TTKH_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_NgayLap_XemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        XemCTHDFormLayout.setVerticalGroup(
            XemCTHDFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, XemCTHDFormLayout.createSequentialGroup()
                .addComponent(lbl_MaHD_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TongTien_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TTNV_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_TTKH_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_NgayLap_XemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pn_TieuDe_QLNV.setBackground(new java.awt.Color(0, 255, 255));

        lbl_TieuDe_QLNV.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lbl_TieuDe_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TieuDe_QLNV.setText("Trí Tuệ Việt");
        lbl_TieuDe_QLNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbl_TieuDe_QLNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pn_TieuDe_QLNVLayout = new javax.swing.GroupLayout(pn_TieuDe_QLNV);
        pn_TieuDe_QLNV.setLayout(pn_TieuDe_QLNVLayout);
        pn_TieuDe_QLNVLayout.setHorizontalGroup(
            pn_TieuDe_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_TieuDe_QLNVLayout.createSequentialGroup()
                .addComponent(lbl_TieuDe_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_TieuDe_QLNVLayout.setVerticalGroup(
            pn_TieuDe_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_TieuDe_QLNVLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_TieuDe_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pn_Menu_QLNV.setPreferredSize(new java.awt.Dimension(274, 738));

        btn_TrangChu_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TrangChu_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-home-page-40.png"))); // NOI18N
        btn_TrangChu_QLNV.setText("         Trang chủ");
        btn_TrangChu_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btn_TrangChu_QLNV.setMinimumSize(new java.awt.Dimension(97, 90));
        btn_TrangChu_QLNV.setPreferredSize(new java.awt.Dimension(97, 90));
        btn_TrangChu_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TrangChu_QLNVActionPerformed(evt);
            }
        });

        btn_QLKH_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_QLKH_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-user-40.png"))); // NOI18N
        btn_QLKH_QLNV.setText("    Quản lý khách hàng");
        btn_QLKH_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_QLKH_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLKH_QLNVActionPerformed(evt);
            }
        });

        btn_QLNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_QLNV_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-manager-40.png"))); // NOI18N
        btn_QLNV_QLNV.setText("     Quản lý nhân viên");
        btn_QLNV_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_QLNV_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLNV_QLNVActionPerformed(evt);
            }
        });

        btn_QLSP_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_QLSP_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-book-40.png"))); // NOI18N
        btn_QLSP_QLNV.setText("     Quản lý sản phẩm");
        btn_QLSP_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_QLSP_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLSP_QLNVActionPerformed(evt);
            }
        });

        btn_QLHD_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_QLHD_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-file-invoice-dollar-40.png"))); // NOI18N
        btn_QLHD_QLNV.setText("     Quản lý hóa đơn");
        btn_QLHD_QLNV.setActionCommand(" Quản lý hóa đơn");
        btn_QLHD_QLNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_QLHD_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_QLHD_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QLHD_QLNVActionPerformed(evt);
            }
        });

        btn_ThongKe_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ThongKe_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-analytics-40.png"))); // NOI18N
        btn_ThongKe_QLNV.setText("         Thống kê");
        btn_ThongKe_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_ThongKe_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThongKe_QLNVActionPerformed(evt);
            }
        });

        btn_TaiKhoan_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TaiKhoan_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-user-40_1.png"))); // NOI18N
        btn_TaiKhoan_QLNV.setText("         Tài khoản");
        btn_TaiKhoan_QLNV.setHideActionText(true);
        btn_TaiKhoan_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_TaiKhoan_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaiKhoan_QLNVActionPerformed(evt);
            }
        });

        pn_TaiKhoan_Menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_Hinh_Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_Hinh_MenuMouseClicked(evt);
            }
        });

        lbl_TenTaiKhoan_Menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenTaiKhoan_Menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_ChucVu_Menu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_ChucVu_Menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btn_DoiMK_Menu.setBackground(new java.awt.Color(102, 255, 102));
        btn_DoiMK_Menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_DoiMK_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-key-12.png"))); // NOI18N
        btn_DoiMK_Menu.setText("Đổi mật khẩu");
        btn_DoiMK_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoiMK_MenuActionPerformed(evt);
            }
        });

        btn_DangXuat_Menu.setBackground(new java.awt.Color(102, 255, 102));
        btn_DangXuat_Menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_DangXuat_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-logout-12.png"))); // NOI18N
        btn_DangXuat_Menu.setText("Đăng xuất");
        btn_DangXuat_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DangXuat_MenuActionPerformed(evt);
            }
        });

        lbl_Clock_Menu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_Clock_Menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Clock_Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-clock-20.png"))); // NOI18N
        lbl_Clock_Menu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pn_TaiKhoan_MenuLayout = new javax.swing.GroupLayout(pn_TaiKhoan_Menu);
        pn_TaiKhoan_Menu.setLayout(pn_TaiKhoan_MenuLayout);
        pn_TaiKhoan_MenuLayout.setHorizontalGroup(
            pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TaiKhoan_MenuLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_TaiKhoan_MenuLayout.createSequentialGroup()
                        .addComponent(btn_DoiMK_Menu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(btn_DangXuat_Menu))
                    .addGroup(pn_TaiKhoan_MenuLayout.createSequentialGroup()
                        .addComponent(lbl_Hinh_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ChucVu_Menu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_TenTaiKhoan_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Clock_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(3, 3, 3))
        );
        pn_TaiKhoan_MenuLayout.setVerticalGroup(
            pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TaiKhoan_MenuLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Hinh_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_TaiKhoan_MenuLayout.createSequentialGroup()
                        .addComponent(lbl_TenTaiKhoan_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_ChucVu_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Clock_Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_TaiKhoan_MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_DoiMK_Menu)
                    .addComponent(btn_DangXuat_Menu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_Menu_QLNVLayout = new javax.swing.GroupLayout(pn_Menu_QLNV);
        pn_Menu_QLNV.setLayout(pn_Menu_QLNVLayout);
        pn_Menu_QLNVLayout.setHorizontalGroup(
            pn_Menu_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_TrangChu_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLNV_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLSP_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLHD_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_ThongKe_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_TaiKhoan_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_QLKH_QLNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_TaiKhoan_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_Menu_QLNVLayout.setVerticalGroup(
            pn_Menu_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_Menu_QLNVLayout.createSequentialGroup()
                .addComponent(btn_TrangChu_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_QLKH_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_QLNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_QLSP_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_QLHD_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ThongKe_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_TaiKhoan_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pn_TaiKhoan_Menu, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1341, 743));

        lbl_Hinh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_Hinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/trangchu.jpg"))); // NOI18N
        lbl_Hinh.setMinimumSize(new java.awt.Dimension(1229, 743));
        lbl_Hinh.setName(""); // NOI18N
        lbl_Hinh.setPreferredSize(new java.awt.Dimension(1229, 743));

        javax.swing.GroupLayout pn_TrangChuLayout = new javax.swing.GroupLayout(pn_TrangChu);
        pn_TrangChu.setLayout(pn_TrangChuLayout);
        pn_TrangChuLayout.setHorizontalGroup(
            pn_TrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TrangChuLayout.createSequentialGroup()
                .addComponent(lbl_Hinh, javax.swing.GroupLayout.PREFERRED_SIZE, 1275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 66, Short.MAX_VALUE))
        );
        pn_TrangChuLayout.setVerticalGroup(
            pn_TrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_Hinh, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );

        pn_QLNV.setPreferredSize(new java.awt.Dimension(1341, 743));

        lbl_TenForm_QLNV.setBackground(new java.awt.Color(255, 255, 255));
        lbl_TenForm_QLNV.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_TenForm_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TenForm_QLNV.setText("Quản Lý Nhân Viên");
        lbl_TenForm_QLNV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pn_TenForm_QLNVLayout = new javax.swing.GroupLayout(pn_TenForm_QLNV);
        pn_TenForm_QLNV.setLayout(pn_TenForm_QLNVLayout);
        pn_TenForm_QLNVLayout.setHorizontalGroup(
            pn_TenForm_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_TenForm_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TenForm_QLNVLayout.setVerticalGroup(
            pn_TenForm_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TenForm_QLNVLayout.createSequentialGroup()
                .addComponent(lbl_TenForm_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_CNTimK_QLNV.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pn_CNTimK_QLNV.setPreferredSize(new java.awt.Dimension(1145, 218));

        lbl_MaNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaNV_QLNV.setText("Mã Nhân Viên:");

        lbl_TenNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenNV_QLNV.setText("Tên Nhân Viên:");

        lbl_ChucVu_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu_QLNV.setText("Chức Vụ:");

        lbl_GioiTinh_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_GioiTinh_QLNV.setText("Giới Tính:");

        txt_ChucVu_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_ChucVu_QLNV.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_ChucVu_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChucVu_QLNVActionPerformed(evt);
            }
        });

        txt_MaNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_MaNV_QLNV.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_MaNV_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaNV_QLNVActionPerformed(evt);
            }
        });

        txt_TenNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btng_NamNu_QLNV.add(rb_Nam_QLNV);
        rb_Nam_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nam_QLNV.setText("Nam");
        rb_Nam_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_Nam_QLNVActionPerformed(evt);
            }
        });

        btng_NamNu_QLNV.add(rb_Nu_QLNV);
        rb_Nu_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nu_QLNV.setText("Nữ");

        javax.swing.GroupLayout pn_CNTimK_QLNVLayout = new javax.swing.GroupLayout(pn_CNTimK_QLNV);
        pn_CNTimK_QLNV.setLayout(pn_CNTimK_QLNVLayout);
        pn_CNTimK_QLNVLayout.setHorizontalGroup(
            pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_CNTimK_QLNVLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_MaNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_TenNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_MaNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TenNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_GioiTinh_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_ChucVu_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_ChucVu_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_CNTimK_QLNVLayout.createSequentialGroup()
                        .addComponent(rb_Nam_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(rb_Nu_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        pn_CNTimK_QLNVLayout.setVerticalGroup(
            pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_CNTimK_QLNVLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_MaNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaNV_QLNV)
                    .addComponent(txt_ChucVu_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ChucVu_QLNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(pn_CNTimK_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TenNV_QLNV)
                    .addComponent(txt_TenNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_GioiTinh_QLNV)
                    .addComponent(rb_Nam_QLNV)
                    .addComponent(rb_Nu_QLNV))
                .addGap(40, 40, 40))
        );

        tbl_DanhSachNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_DanhSachNV_QLNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên Nhân Viên", "Giới tính", "Ngày sinh", "Số điện thoại", "Chức vụ", "Tình trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_DanhSachNV_QLNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_DanhSachNV_QLNV.setName("Danh sách nhân viên\n"); // NOI18N
        tbl_DanhSachNV_QLNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbl_DanhSachNV_QLNVFocusLost(evt);
            }
        });
        tbl_DanhSachNV_QLNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DanhSachNV_QLNVMouseClicked(evt);
            }
        });
        scp_DanhSachNV_QLNV.setViewportView(tbl_DanhSachNV_QLNV);

        btn_ThemNV_QLNV.setBackground(new java.awt.Color(51, 255, 51));
        btn_ThemNV_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ThemNV_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_ThemNV_QLNV.setText("Thêm mới");
        btn_ThemNV_QLNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_ThemNV_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNV_QLNVActionPerformed(evt);
            }
        });

        btn_Xoa_QLNV.setBackground(new java.awt.Color(51, 255, 51));
        btn_Xoa_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Xoa_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-user-24.png"))); // NOI18N
        btn_Xoa_QLNV.setText("-  Xóa");
        btn_Xoa_QLNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_Xoa_QLNV.setMinimumSize(new java.awt.Dimension(93, 25));
        btn_Xoa_QLNV.setPreferredSize(new java.awt.Dimension(93, 25));
        btn_Xoa_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Xoa_QLNVActionPerformed(evt);
            }
        });

        btn_CapNhap_QLNV.setBackground(new java.awt.Color(102, 255, 102));
        btn_CapNhap_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhap_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-pencil-24.png"))); // NOI18N
        btn_CapNhap_QLNV.setText("Cập nhập");
        btn_CapNhap_QLNV.setFocusable(false);
        btn_CapNhap_QLNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_CapNhap_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhap_QLNVActionPerformed(evt);
            }
        });

        btn_TimKiem_QLNV.setBackground(new java.awt.Color(51, 255, 51));
        btn_TimKiem_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TimKiem_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-search-24.png"))); // NOI18N
        btn_TimKiem_QLNV.setText("Tìm kiếm");
        btn_TimKiem_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiem_QLNVActionPerformed(evt);
            }
        });

        btn_CapNhapBang_QLNV.setBackground(new java.awt.Color(51, 255, 51));
        btn_CapNhapBang_QLNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhapBang_QLNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-easel-24.png"))); // NOI18N
        btn_CapNhapBang_QLNV.setText("Cập nhập lại bảng");
        btn_CapNhapBang_QLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhapBang_QLNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_QLNVLayout = new javax.swing.GroupLayout(pn_ChucNang_QLNV);
        pn_ChucNang_QLNV.setLayout(pn_ChucNang_QLNVLayout);
        pn_ChucNang_QLNVLayout.setHorizontalGroup(
            pn_ChucNang_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLNVLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btn_TimKiem_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_ThemNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_Xoa_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_CapNhap_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_CapNhapBang_QLNV)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_ChucNang_QLNVLayout.setVerticalGroup(
            pn_ChucNang_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLNVLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_ChucNang_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CapNhap_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_CapNhapBang_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiem_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Xoa_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pn_QLNVLayout = new javax.swing.GroupLayout(pn_QLNV);
        pn_QLNV.setLayout(pn_QLNVLayout);
        pn_QLNVLayout.setHorizontalGroup(
            pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1341, Short.MAX_VALUE)
            .addGroup(pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLNVLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pn_ChucNang_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scp_DanhSachNV_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pn_CNTimK_QLNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1245, Short.MAX_VALUE)
                            .addComponent(pn_TenForm_QLNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(78, Short.MAX_VALUE)))
        );
        pn_QLNVLayout.setVerticalGroup(
            pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
            .addGroup(pn_QLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLNVLayout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(pn_TenForm_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_CNTimK_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_ChucNang_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scp_DanhSachNV_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        lbl_Tieu_TaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_Tieu_TaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Tieu_TaiKhoan.setText("Tài Khoản");
        lbl_Tieu_TaiKhoan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_TTTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_TTTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TTTaiKhoan.setText("Thông tin tài khoản");

        lbl_MaTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaTK.setText("Mã nhân viên:");

        lbl_TenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenNV.setText("Tên nhân viên:");

        lbl_GioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_GioiTinh.setText("Giới tính:");

        lbl_Ngaysinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Ngaysinh.setText("Ngày sinh:");

        lbl_SDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT.setText("Số điện thoại:");

        lbl_ChucVu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_ChucVu.setText("Chức vụ:");

        txt_MaNV_QLTK.setEditable(false);
        txt_MaNV_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_TenNV_QLTK.setEditable(false);
        txt_TenNV_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_SDT_QLTK.setEditable(false);
        txt_SDT_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_ChucVu_QLTK.setEditable(false);
        txt_ChucVu_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_ChucVu_QLTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChucVu_QLTKActionPerformed(evt);
            }
        });

        dc_Ngaysinh_QLTK.setDateFormatString("MM-dd-yyyy");
        dc_Ngaysinh_QLTK.setEnabled(false);

        btng_NamNu_QLTK.add(rb_Nam_QLTK);
        rb_Nam_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nam_QLTK.setText("Nam");
        rb_Nam_QLTK.setEnabled(false);

        btng_NamNu_QLTK.add(rb_Nu_QLTK);
        rb_Nu_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rb_Nu_QLTK.setText("Nữ");
        rb_Nu_QLTK.setEnabled(false);

        btn_DoiMK.setBackground(new java.awt.Color(102, 255, 102));
        btn_DoiMK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_DoiMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-key-25.png"))); // NOI18N
        btn_DoiMK.setText("Đổi mật khẩu");
        btn_DoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoiMKActionPerformed(evt);
            }
        });

        btn_TaoTK.setBackground(new java.awt.Color(102, 255, 102));
        btn_TaoTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TaoTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-add-user-male-24.png"))); // NOI18N
        btn_TaoTK.setText("Tạo tài khoản");
        btn_TaoTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoTKActionPerformed(evt);
            }
        });

        btn_Dangxuat.setBackground(new java.awt.Color(102, 255, 102));
        btn_Dangxuat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Dangxuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-logout-24.png"))); // NOI18N
        btn_Dangxuat.setText("Đăng xuất");
        btn_Dangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DangxuatActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbl_HinhNV_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_HinhNV_QLTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_HinhNV_QLTK.setText("Hình nhân viên");

        lbl_Hinh_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Hinh_QLTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Hinh_QLTK.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_MaTK_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaTK_QLTK.setText("Mã tài khoản:");

        lbl_TenTK_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenTK_QLTK.setText("Tên tài khoản:");

        txt_MaTK_QLTK.setEditable(false);
        txt_MaTK_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_TenTK_QLTK.setEditable(false);
        txt_TenTK_QLTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout pn_QLTKLayout = new javax.swing.GroupLayout(pn_QLTK);
        pn_QLTK.setLayout(pn_QLTKLayout);
        pn_QLTKLayout.setHorizontalGroup(
            pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_QLTKLayout.createSequentialGroup()
                .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(btn_DoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205)
                        .addComponent(btn_TaoTK, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205)
                        .addComponent(btn_Dangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                        .addGap(454, 454, 454)
                        .addComponent(lbl_TTTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_MaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_ChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_Ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_QLTKLayout.createSequentialGroup()
                                .addComponent(rb_Nam_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(rb_Nu_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_SDT_QLTK)
                            .addComponent(dc_Ngaysinh_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(txt_TenNV_QLTK)
                            .addComponent(txt_MaNV_QLTK)
                            .addComponent(txt_ChucVu_QLTK))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_QLTKLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                                        .addComponent(lbl_TenTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_TenTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                                        .addComponent(lbl_MaTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_MaTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pn_QLTKLayout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_HinhNV_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(lbl_Hinh_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(lbl_Tieu_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 1315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pn_QLTKLayout.setVerticalGroup(
            pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_QLTKLayout.createSequentialGroup()
                .addComponent(lbl_Tieu_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(lbl_TTTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_MaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_MaNV_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_TenNV_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_GioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(rb_Nam_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rb_Nu_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_Ngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dc_Ngaysinh_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_SDT_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_ChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ChucVu_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_QLTKLayout.createSequentialGroup()
                        .addComponent(lbl_HinhNV_QLTK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_Hinh_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_MaTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_MaTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_QLTKLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbl_TenTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_QLTKLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txt_TenTK_QLTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addGroup(pn_QLTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_DoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TaoTK, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Dangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );

        lbl_TenForm_QLKH.setBackground(new java.awt.Color(255, 255, 255));
        lbl_TenForm_QLKH.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_TenForm_QLKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TenForm_QLKH.setText("Quản Lý Khách Hàng");
        lbl_TenForm_QLKH.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pn_TenForm_QLKHLayout = new javax.swing.GroupLayout(pn_TenForm_QLKH);
        pn_TenForm_QLKH.setLayout(pn_TenForm_QLKHLayout);
        pn_TenForm_QLKHLayout.setHorizontalGroup(
            pn_TenForm_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_TenForm_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pn_TenForm_QLKHLayout.setVerticalGroup(
            pn_TenForm_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TenForm_QLKHLayout.createSequentialGroup()
                .addComponent(lbl_TenForm_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_CNTimK_QLKH.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lbl_MaKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaKH_QLKH.setText("Mã Khách Hàng:");

        lbl_TenKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_TenKH_QLKH.setText("Tên Khách Hàng:");

        lbl_SDT_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_SDT_QLKH.setText("Số Điện Thoại:");

        lbl_Tuoi_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Tuoi_QLKH.setText("Tuổi:");

        txt_SDT_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_SDT_QLKH.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_SDT_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SDT_QLKHActionPerformed(evt);
            }
        });

        txt_MaKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_MaKH_QLKH.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_MaKH_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaKH_QLKHActionPerformed(evt);
            }
        });

        txt_TenKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        sn_Tuoi_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout pn_CNTimK_QLKHLayout = new javax.swing.GroupLayout(pn_CNTimK_QLKH);
        pn_CNTimK_QLKH.setLayout(pn_CNTimK_QLKHLayout);
        pn_CNTimK_QLKHLayout.setHorizontalGroup(
            pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_CNTimK_QLKHLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_TenKH_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_MaKH_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(75, 75, 75)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_MaKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TenKH_QLKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Tuoi_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_SDT_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_SDT_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sn_Tuoi_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addGap(113, 113, 113))
        );
        pn_CNTimK_QLKHLayout.setVerticalGroup(
            pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_CNTimK_QLKHLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_MaKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaKH_QLKH)
                    .addComponent(txt_SDT_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_SDT_QLKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(pn_CNTimK_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_TenKH_QLKH)
                    .addComponent(txt_TenKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Tuoi_QLKH)
                    .addComponent(sn_Tuoi_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        tbl_DanhSachKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_DanhSachKH_QLKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên Nhân Viên", "Giới tính", "Ngày sinh", "Số điện thoại", "Chức vụ", "Tình trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_DanhSachKH_QLKH.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_DanhSachKH_QLKH.setName("Danh sách nhân viên\n"); // NOI18N
        tbl_DanhSachKH_QLKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbl_DanhSachKH_QLKHFocusLost(evt);
            }
        });
        scp_DanhSachKH_QLKH.setViewportView(tbl_DanhSachKH_QLKH);

        btn_ThemKH_QLKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_ThemKH_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ThemKH_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_ThemKH_QLKH.setText("Thêm mới");
        btn_ThemKH_QLKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_ThemKH_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemKH_QLKHActionPerformed(evt);
            }
        });

        btn_Xoa_QLKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_Xoa_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Xoa_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-user-24.png"))); // NOI18N
        btn_Xoa_QLKH.setText("-  Xóa");
        btn_Xoa_QLKH.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_Xoa_QLKH.setMinimumSize(new java.awt.Dimension(93, 25));
        btn_Xoa_QLKH.setPreferredSize(new java.awt.Dimension(93, 25));
        btn_Xoa_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Xoa_QLKHActionPerformed(evt);
            }
        });

        btn_CapNhap_QLKH.setBackground(new java.awt.Color(102, 255, 102));
        btn_CapNhap_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhap_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-pencil-24.png"))); // NOI18N
        btn_CapNhap_QLKH.setText("Cập nhập");
        btn_CapNhap_QLKH.setFocusable(false);
        btn_CapNhap_QLKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_CapNhap_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhap_QLKHActionPerformed(evt);
            }
        });

        btn_TimKiem_QLKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_TimKiem_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TimKiem_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-search-24.png"))); // NOI18N
        btn_TimKiem_QLKH.setText("Tìm kiếm");
        btn_TimKiem_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiem_QLKHActionPerformed(evt);
            }
        });

        btn_CapNhapBang_QLKH.setBackground(new java.awt.Color(51, 255, 51));
        btn_CapNhapBang_QLKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhapBang_QLKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-easel-24.png"))); // NOI18N
        btn_CapNhapBang_QLKH.setText("Cập nhập lại bảng");
        btn_CapNhapBang_QLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhapBang_QLKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_QLKHLayout = new javax.swing.GroupLayout(pn_ChucNang_QLKH);
        pn_ChucNang_QLKH.setLayout(pn_ChucNang_QLKHLayout);
        pn_ChucNang_QLKHLayout.setHorizontalGroup(
            pn_ChucNang_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLKHLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(btn_TimKiem_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_ThemKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_Xoa_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_CapNhap_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_CapNhapBang_QLKH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_ChucNang_QLKHLayout.setVerticalGroup(
            pn_ChucNang_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLKHLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_ChucNang_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_CapNhap_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_CapNhapBang_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiem_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Xoa_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pn_QLKHLayout = new javax.swing.GroupLayout(pn_QLKH);
        pn_QLKH.setLayout(pn_QLKHLayout);
        pn_QLKHLayout.setHorizontalGroup(
            pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1341, Short.MAX_VALUE)
            .addGroup(pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLKHLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pn_CNTimK_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pn_TenForm_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(scp_DanhSachKH_QLKH, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn_ChucNang_QLKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(64, Short.MAX_VALUE)))
        );
        pn_QLKHLayout.setVerticalGroup(
            pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
            .addGroup(pn_QLKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLKHLayout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(pn_TenForm_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_CNTimK_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_ChucNang_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scp_DanhSachKH_QLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        pn_QLHD_Form.setPreferredSize(new java.awt.Dimension(1341, 743));

        lbl_TenForm_QLHD.setBackground(new java.awt.Color(255, 255, 255));
        lbl_TenForm_QLHD.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_TenForm_QLHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_TenForm_QLHD.setText("Quản Lý Hóa Đơn");
        lbl_TenForm_QLHD.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pn_TenForm_QLHDLayout = new javax.swing.GroupLayout(pn_TenForm_QLHD);
        pn_TenForm_QLHD.setLayout(pn_TenForm_QLHDLayout);
        pn_TenForm_QLHDLayout.setHorizontalGroup(
            pn_TenForm_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_TenForm_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_TenForm_QLHDLayout.setVerticalGroup(
            pn_TenForm_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_TenForm_QLHDLayout.createSequentialGroup()
                .addComponent(lbl_TenForm_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_CNTimK_QLHD.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pn_CNTimK_QLHD.setPreferredSize(new java.awt.Dimension(1145, 218));

        lbl_MaHD_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaHD_QLHD.setText("Mã Hóa Đơn:");

        lbl_NgayLap_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_NgayLap_QLHD.setText("Ngày Lập:");

        lbl_MaNV_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaNV_QLHD.setText("Mã Nhân viên:");

        lbl_MaKH_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_MaKH_QLHD.setText("Mã Khách Hàng:");

        txt_maNV_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_maNV_QLHD.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_maNV_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maNV_QLHDActionPerformed(evt);
            }
        });

        txt_MaHD_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_MaHD_QLHD.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_MaHD_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaHD_QLHDActionPerformed(evt);
            }
        });

        dc_NgayLap_QLHD.setDateFormatString("MM-dd-yyyy");
        dc_NgayLap_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txt_maKH_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_maKH_QLHD.setMinimumSize(new java.awt.Dimension(7, 25));
        txt_maKH_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maKH_QLHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_CNTimK_QLHDLayout = new javax.swing.GroupLayout(pn_CNTimK_QLHD);
        pn_CNTimK_QLHD.setLayout(pn_CNTimK_QLHDLayout);
        pn_CNTimK_QLHDLayout.setHorizontalGroup(
            pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_CNTimK_QLHDLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_MaHD_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_NgayLap_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_MaHD_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(dc_NgayLap_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_MaNV_QLHD)
                    .addComponent(lbl_MaKH_QLHD))
                .addGap(39, 39, 39)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_maNV_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_maKH_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110))
        );
        pn_CNTimK_QLHDLayout.setVerticalGroup(
            pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_CNTimK_QLHDLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_MaHD_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaHD_QLHD)
                    .addComponent(txt_maNV_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_MaNV_QLHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(pn_CNTimK_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_NgayLap_QLHD)
                    .addComponent(lbl_MaKH_QLHD)
                    .addComponent(dc_NgayLap_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_maKH_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        tbl_DanhSachHD_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_DanhSachHD_QLHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên Nhân Viên", "Giới tính", "Ngày sinh", "Số điện thoại", "Chức vụ", "Tình trạng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_DanhSachHD_QLHD.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_DanhSachHD_QLHD.setName("Danh sách nhân viên\n"); // NOI18N
        tbl_DanhSachHD_QLHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tbl_DanhSachHD_QLHDFocusLost(evt);
            }
        });
        tbl_DanhSachHD_QLHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DanhSachHD_QLHDMouseClicked(evt);
            }
        });
        scp_DanhSachHD_QLHD.setViewportView(tbl_DanhSachHD_QLHD);

        btn_LapHD_QLHD.setBackground(new java.awt.Color(51, 255, 51));
        btn_LapHD_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_LapHD_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-identification-documents-24.png"))); // NOI18N
        btn_LapHD_QLHD.setText("Lập hóa đơn");
        btn_LapHD_QLHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_LapHD_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LapHD_QLHDActionPerformed(evt);
            }
        });

        btn_XemChiTiet_QLHD.setBackground(new java.awt.Color(51, 255, 51));
        btn_XemChiTiet_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_XemChiTiet_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-user-24.png"))); // NOI18N
        btn_XemChiTiet_QLHD.setText("-  Xem thông tin");
        btn_XemChiTiet_QLHD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_XemChiTiet_QLHD.setMinimumSize(new java.awt.Dimension(93, 25));
        btn_XemChiTiet_QLHD.setPreferredSize(new java.awt.Dimension(93, 25));
        btn_XemChiTiet_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemChiTiet_QLHDActionPerformed(evt);
            }
        });

        btn_In_QLHD.setBackground(new java.awt.Color(102, 255, 102));
        btn_In_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_In_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-printer-24_1.png"))); // NOI18N
        btn_In_QLHD.setText("    In");
        btn_In_QLHD.setFocusable(false);
        btn_In_QLHD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_In_QLHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_In_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_In_QLHDActionPerformed(evt);
            }
        });

        btn_TimKiem_QLHD.setBackground(new java.awt.Color(51, 255, 51));
        btn_TimKiem_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_TimKiem_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-search-24.png"))); // NOI18N
        btn_TimKiem_QLHD.setText("Tìm kiếm");
        btn_TimKiem_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiem_QLHDActionPerformed(evt);
            }
        });

        btn_CapNhapBang_QLHD.setBackground(new java.awt.Color(51, 255, 51));
        btn_CapNhapBang_QLHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_CapNhapBang_QLHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/icons8-easel-24.png"))); // NOI18N
        btn_CapNhapBang_QLHD.setText("Cập nhập lại bảng");
        btn_CapNhapBang_QLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhapBang_QLHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_ChucNang_QLHDLayout = new javax.swing.GroupLayout(pn_ChucNang_QLHD);
        pn_ChucNang_QLHD.setLayout(pn_ChucNang_QLHDLayout);
        pn_ChucNang_QLHDLayout.setHorizontalGroup(
            pn_ChucNang_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLHDLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btn_TimKiem_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_LapHD_QLHD)
                .addGap(116, 116, 116)
                .addComponent(btn_XemChiTiet_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(btn_In_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(btn_CapNhapBang_QLHD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_ChucNang_QLHDLayout.setVerticalGroup(
            pn_ChucNang_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_ChucNang_QLHDLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_ChucNang_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_In_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_CapNhapBang_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiem_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LapHD_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XemChiTiet_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout pn_QLHD_FormLayout = new javax.swing.GroupLayout(pn_QLHD_Form);
        pn_QLHD_Form.setLayout(pn_QLHD_FormLayout);
        pn_QLHD_FormLayout.setHorizontalGroup(
            pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1341, Short.MAX_VALUE)
            .addGroup(pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLHD_FormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pn_ChucNang_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scp_DanhSachHD_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pn_CNTimK_QLHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1245, Short.MAX_VALUE)
                            .addComponent(pn_TenForm_QLHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(88, Short.MAX_VALUE)))
        );
        pn_QLHD_FormLayout.setVerticalGroup(
            pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
            .addGroup(pn_QLHD_FormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLHD_FormLayout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(pn_TenForm_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_CNTimK_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pn_ChucNang_QLHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scp_DanhSachHD_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                    .addGap(5, 5, 5)))
        );

        javax.swing.GroupLayout pn_QLHDLayout = new javax.swing.GroupLayout(pn_QLHD);
        pn_QLHD.setLayout(pn_QLHDLayout);
        pn_QLHDLayout.setHorizontalGroup(
            pn_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1341, Short.MAX_VALUE)
            .addGroup(pn_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_QLHDLayout.createSequentialGroup()
                    .addComponent(pn_QLHD_Form, javax.swing.GroupLayout.DEFAULT_SIZE, 1331, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pn_QLHDLayout.setVerticalGroup(
            pn_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
            .addGroup(pn_QLHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLHD_Form, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1229, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
        );

        jLayeredPane1.setLayer(pn_TrangChu, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pn_QLNV, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pn_QLTK, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pn_QLKH, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(pn_QLHD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(tbpn_QLSP, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_TrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tbpn_QLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 1341, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_TrangChu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pn_QLHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tbpn_QLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_Menu_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pn_TieuDe_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_TieuDe_QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addComponent(pn_Menu_QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_TrangChu_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TrangChu_QLNVActionPerformed
        // TODO add your handling code here:
        pn_TrangChu.setVisible(true);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
    }//GEN-LAST:event_btn_TrangChu_QLNVActionPerformed

    private void btn_QLKH_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLKH_QLNVActionPerformed
        pn_TrangChu.setVisible(false);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(true);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
        initTableQLKH();        
        loadDataToTableQLKH();
    }//GEN-LAST:event_btn_QLKH_QLNVActionPerformed

    private void btn_QLNV_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLNV_QLNVActionPerformed
       if(SharedData.nhanVienDN.getChucVu().equals("Quản lý"))
       {}else{JOptionPane.showMessageDialog(null, "Tính năng chỉ dành cho quản lý", "Thông báo", JOptionPane.INFORMATION_MESSAGE);return;}
        pn_TrangChu.setVisible(false);
        pn_QLNV.setVisible(true);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
        rb_Nam_QLNV.setSelected(true);
        
        personalImage = null;
                
        initTable();
        
        loadDataToTable();
        
    }//GEN-LAST:event_btn_QLNV_QLNVActionPerformed

    private void btn_QLSP_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLSP_QLNVActionPerformed
        pn_TrangChu.setVisible(false);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(true);
        if(QLSPForm == null)
        {
            QLSPForm = new QLSPForm();
            tbpn_QLSP.add(QLSPForm,"Sản phẩm");
            QLNSPForm = new QLNSPForm();
            tbpn_QLSP.add(QLNSPForm,"Nhóm sản phẩm");
            NhaCungCapForm = new NhaCungCapForm();
            tbpn_QLSP.add(NhaCungCapForm,"Nhà cung cấp");
            TacGiaForm = new TacGiaForm();
            tbpn_QLSP.add(TacGiaForm,"Tác giả");
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_btn_QLSP_QLNVActionPerformed

    private void btn_QLHD_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QLHD_QLNVActionPerformed
       
        pn_TrangChu.setVisible(false);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(false);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(true);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
        initTableQLHD();        
        loadDataToTable_QLHD();
        
    }//GEN-LAST:event_btn_QLHD_QLNVActionPerformed

    private void btn_ThongKe_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThongKe_QLNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ThongKe_QLNVActionPerformed

    private void btn_TaiKhoan_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaiKhoan_QLNVActionPerformed
        pn_TrangChu.setVisible(false);
        pn_QLNV.setVisible(false);
        pn_QLTK.setVisible(true);
        pn_QLKH.setVisible(false);
        pn_QLHD.setVisible(false);
        jPanel8.setVisible(false);
        tbpn_QLSP.setVisible(false);
        try {
            setTT_FormTK();
        } catch (ParseException ex) {
            Logger.getLogger(text.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(text.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_TaiKhoan_QLNVActionPerformed

    private void btn_DoiMK_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoiMK_MenuActionPerformed
        QuenMKForm quenmk = new QuenMKForm(this,true);
        quenmk.setVisible(true);
    }//GEN-LAST:event_btn_DoiMK_MenuActionPerformed

    private void btn_DangXuat_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DangXuat_MenuActionPerformed
         String tenDN = SharedData.taiKhoanDN.getTenTK();
       this.dispose();
       LoginJDialog login = new LoginJDialog(this,true);
       login.setVisible(true);
    }//GEN-LAST:event_btn_DangXuat_MenuActionPerformed

    private void txt_ChucVu_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChucVu_QLNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChucVu_QLNVActionPerformed

    private void rb_Nam_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_Nam_QLNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_Nam_QLNVActionPerformed

    private void tbl_DanhSachNV_QLNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_DanhSachNV_QLNVFocusLost

    }//GEN-LAST:event_tbl_DanhSachNV_QLNVFocusLost

    private void btn_ThemNV_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNV_QLNVActionPerformed
        personalImage = null;
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
        lbl_Hinh_T.setIcon(icon);
        resetThongTinThemNVForm();
        
        ThemNVForm.setLocationRelativeTo(null);
        ThemNVForm.setVisible(true);

    }//GEN-LAST:event_btn_ThemNV_QLNVActionPerformed

    private void btn_Xoa_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Xoa_QLNVActionPerformed
        //StringBuilder sb = new StringBuilder();
        //Kiểm tra có chọn trong table
        String maNhanVien = "" ;
        try {
            int row = tbl_DanhSachNV_QLNV.getSelectedRow();

            if(row>=0)
            {
                String id = (String) tblModel.getValueAt(row , 0);
                maNhanVien = JOptionPane.showInputDialog("Nhập mã nhân viên muốn xóa",id);
            }else{
                maNhanVien = JOptionPane.showInputDialog("Nhập mã nhân viên muốn xóa");
            }
        } catch (Exception e) {
        }
        //Xóa nhân viên
        

        //JOptionPane.showMessageDialog(null, maNhanVien==null);
        if((maNhanVien==null)==false)
        {
            if(maNhanVien.equals("")==false)
            {if(MessageDialogHelper.showConfirmDialog(null, "Bạn có muốn xóa nhân viên không?", "Hỏi")==JOptionPane.YES_OPTION)
        {}else{return;}
                try {

                    NhanVienDao dao =new NhanVienDao();

                    if(dao.delete(maNhanVien))
                    {
                        resetTable();
                        MessageDialogHelper.showMessageDialog(null, "Xóa nhân viên thành công", "Thông báo");

                    }
                    else{
                        MessageDialogHelper.showConfirmDialog(null, "Xóa nhân viên không được do lỗi", "Cảnh báo");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDialogHelper.showErrorDialog( null, e.getMessage()
                        , "Lỗi");
                }
            }
        }
    }//GEN-LAST:event_btn_Xoa_QLNVActionPerformed

    private void btn_CapNhap_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhap_QLNVActionPerformed
        personalImage = null;
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
        lbl_Hinh_CN.setIcon(icon);
        CapNhapNVForm.setLocationRelativeTo(null);
        restartFormCapNhap();
        CapNhapNVForm.setVisible(true);

    }//GEN-LAST:event_btn_CapNhap_QLNVActionPerformed

    private void btn_TimKiem_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiem_QLNVActionPerformed
        try {
            NhanVienDao dao = new NhanVienDao();
            List<NhanVien> list = dao.findDK(txt_MaNV_QLNV.getText(), txt_TenNV_QLNV.getText(), txt_ChucVu_QLNV.getText(), rb_Nam_QLNV.isSelected()?"Nam":"Nữ");
            tblModel.setRowCount(0);
            for(NhanVien it : list)
            {
                JLabel lbl_Hinh_TbNV = new JLabel();
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                ImageIcon icon =new ImageIcon();
                if(it.getHinh()!=null)
                {
                     icon = new ImageIcon(it.getHinh());
                     lbl_Hinh_TbNV.setIcon(icon);
                }else{                  
                       icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg"));
                       lbl_Hinh_TbNV.setIcon(icon);                 
                }
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel.addRow(new Object[]
                    { it.getMaNhanVien(),it.getTenNhanVien(),it.getGioiTinh(),it.getNgaySinh(),it.getsDT(),it.getChucVu(),it.getTinhTrang(),it.getMaTK(),lbl_Hinh_TbNV} );

                tblModel.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");

        }

    }//GEN-LAST:event_btn_TimKiem_QLNVActionPerformed

    private void btn_CapNhapBang_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhapBang_QLNVActionPerformed
        personalImage = null;
                     
        resetTable();
    }//GEN-LAST:event_btn_CapNhapBang_QLNVActionPerformed

    private void txt_ChucVu_QLTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChucVu_QLTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChucVu_QLTKActionPerformed

    private void btn_DoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoiMKActionPerformed
        QuenMKForm quenmk = new QuenMKForm(this,true);
        quenmk.setVisible(true);
    }//GEN-LAST:event_btn_DoiMKActionPerformed

    private void btn_TaoTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoTKActionPerformed
         if(SharedData.nhanVienDN.getChucVu().equals("Quản lý"))
       {}else{JOptionPane.showMessageDialog(null, "Tính năng chỉ dành cho quản lý", "Thông báo", JOptionPane.INFORMATION_MESSAGE);return;}
        
        TaoTaiKoanJDialog taotk = new TaoTaiKoanJDialog(this,true);
        taotk.setVisible(true);
    }//GEN-LAST:event_btn_TaoTKActionPerformed

    private void btn_DangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DangxuatActionPerformed
       String tenDN = SharedData.taiKhoanDN.getTenTK();
       this.dispose();
       LoginJDialog login = new LoginJDialog(this,true);
       login.setVisible(true);
      
    }//GEN-LAST:event_btn_DangxuatActionPerformed

    private void btn_ThemMoi_TActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemMoi_TActionPerformed
        StringBuilder sb = new StringBuilder();
        //MessageDialogHelper.showMessageDialog(null, (String) cbx_MaTK_T.getSelectedItem(), "Thông báo");
        DataValidator.validateEmptyTenNV(txt_TenNV_T, sb);
        DataValidator.validateEmpty(dc_NgaySinh_T, sb, "Ngày sinh không được để trống và theo định dạng MM-dd-yyyy");
        try {
            DataValidator.validateEmptySDTKoTrung(txt_SDT_T, sb);
        } catch (Exception ex) {
            Logger.getLogger(text.class.getName()).log(Level.SEVERE, null, ex);
        }
        //DataValidator.validateEmptyChucVu(txt_ChucVu_T, sb);
        if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(null, sb.toString(), "Lỗi");
            return;
        }

        try {
            NhanVien nv = new NhanVien();
            nv.setMaNhanVien(txt_MaNV_T.getText());
            nv.setTenNhanVien(txt_TenNV_T.getText());
            nv.setGioiTinh(rb_Nam_T.isSelected()?"Nam":"Nữ");
            nv.setNgaySinh(((JTextField)dc_NgaySinh_T.getDateEditor().getUiComponent()).getText());//
            nv.setsDT(txt_SDT_T.getText());
            nv.setChucVu(cbx_ChucVu_T.getSelectedItem().toString());
            //
            String maTK = cbx_MaTK_T.getSelectedItem().toString();
            String[] ma = maTK.split("-");
            nv.setMaTK(ma[0]);
            //
            nv.setTinhTrang("Vẫn làm việc");
            nv.setHinh(personalImage);

            NhanVienDao dao =new NhanVienDao();
            if(dao.insert(nv))
            {
                resetTable();
                MessageDialogHelper.showMessageDialog(null, "Nhân viên thêm thành công", "Thông báo");
                resetThongTinThemNVForm();
                personalImage = null;
                ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                lbl_Hinh_T.setIcon(icon);
            }
            else{
                MessageDialogHelper.showConfirmDialog(null, "Sinh viên không lưu được do lỗi", "Cảnh báo");
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage()
                , "Lỗi");
        }
    }//GEN-LAST:event_btn_ThemMoi_TActionPerformed

    private void btn_Huy_TActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Huy_TActionPerformed
        ThemNVForm.setVisible(false);
    }//GEN-LAST:event_btn_Huy_TActionPerformed

    private void txt_SDT_TActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDT_TActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDT_TActionPerformed

    private void btn_CapNhap_CNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhap_CNActionPerformed

        //////////////////////////////////////////////////////////////////////////
        StringBuilder sb = new StringBuilder();
        NhanVienDao dao =new NhanVienDao();
        DataValidator.validateEmptyMaNV(txt_MaNV_CN, sb);
        DataValidator.validateEmptyTenNV(txt_TenNV_CN, sb);
        DataValidator.validateEmpty(dc_NgaySinh_CN, sb, "Ngày sinh không được để trống và theo định dạng MM/dd/yyyy");
        
        String sdt;
        try {
            sdt = dao.find(txt_MaNV_CN.getText()).getsDT().replaceAll("\\s","");
        } catch (Exception ex) {
            sdt="";
        }
        
            if(sdt.equals(txt_SDT_CN.getText().replaceAll("\\s","")))
            {}else{ try {
                    DataValidator.validateEmptySDTKoTrung(txt_SDT_CN, sb);
                } catch (Exception ex) {
                    Logger.getLogger(text.class.getName()).log(Level.SEVERE, null, ex);
                }}
        
        
        //DataValidator.validateEmptyChucVu(txt_ChucVu_CN, sb);
        if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(null, sb.toString(), "Lỗi");
            return;
        }
        if(MessageDialogHelper.showConfirmDialog(null, "Bạn có muốn cập nhân viên không?", "Hỏi")==JOptionPane.YES_OPTION)
        {}else{return;}
        try {
            NhanVien nv =new NhanVien();
            nv.setMaNhanVien(txt_MaNV_CN.getText());
            nv.setTenNhanVien(txt_TenNV_CN.getText());
            nv.setGioiTinh(rb_GioiTinh_Nam_CN.isSelected()?"Nam":"Nữ");
            nv.setNgaySinh(((JTextField)dc_NgaySinh_CN.getDateEditor().getUiComponent()).getText());
            nv.setsDT(txt_SDT_CN.getText());
            nv.setChucVu((String) cbx_ChucVu_CN.getSelectedItem());
            nv.setTinhTrang(rb_VanLamViec_CN.isSelected()?"Vẫn làm việc":"Đã nghỉ việc");
            //
            String maTK = cbx_maTK_CN.getSelectedItem().toString();
            String[] ma = maTK.split("-");
            nv.setMaTK(ma[0]);
            //
            nv.setHinh(personalImage);
            

            if(dao.update(nv))
            {
                resetTable();
                MessageDialogHelper.showMessageDialog(null, "Cập nhập nhân viên thành công", "Thông báo");
                restartFormCapNhap();
                personalImage = null;
                ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                lbl_Hinh_CN.setIcon(icon);
            }
            else{
                MessageDialogHelper.showConfirmDialog(null, "Cập nhập nhân viên không được do lỗi", "Cảnh báo");
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage()
                , "Lỗi");
        }
    }//GEN-LAST:event_btn_CapNhap_CNActionPerformed

    private void btn_Huy_CNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Huy_CNActionPerformed
        CapNhapNVForm.setVisible(false);
    }//GEN-LAST:event_btn_Huy_CNActionPerformed

    private void txt_SDT_CNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDT_CNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDT_CNActionPerformed

    private void txt_TenNV_CNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_TenNV_CNFocusLost

    }//GEN-LAST:event_txt_TenNV_CNFocusLost

    private void txt_MaNV_CNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_MaNV_CNFocusLost
        

    }//GEN-LAST:event_txt_MaNV_CNFocusLost

    private void dc_NgaySinh_CNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dc_NgaySinh_CNFocusLost

    }//GEN-LAST:event_dc_NgaySinh_CNFocusLost

    private void txt_SDT_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDT_QLKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDT_QLKHActionPerformed

    private void tbl_DanhSachKH_QLKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_DanhSachKH_QLKHFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DanhSachKH_QLKHFocusLost

    private void btn_ThemKH_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemKH_QLKHActionPerformed
        resetThongTinThemKHForm();

        ThemKHForm.setLocationRelativeTo(null);
        ThemKHForm.setVisible(true);
    }//GEN-LAST:event_btn_ThemKH_QLKHActionPerformed

    private void btn_Xoa_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Xoa_QLKHActionPerformed
        String maKH = "" ;
        try {
            int row = tbl_DanhSachKH_QLKH.getSelectedRow();

            if(row>=0)
            {
                String id = (String) tblModel_QLKH.getValueAt(row , 0);
                maKH = JOptionPane.showInputDialog("Nhập mã khách hàng muốn xóa",id);
            }else{
                maKH = JOptionPane.showInputDialog("Nhập mã khách hàng muốn xóa");
            }
        } catch (Exception e) {
        }
        //Xóa nhân viên
        

        //JOptionPane.showMessageDialog(null, maNhanVien==null);
        if((maKH==null)==false)
        {
            if(maKH.equals("")==false)
            {if(MessageDialogHelper.showConfirmDialog(null, "Bạn có muốn xóa khách hàng không?", "Hỏi")==JOptionPane.YES_OPTION)
        {}else{return;}
                try {

                    KhachHangDao dao =new KhachHangDao();

                    if(dao.delete(maKH))
                    {
                        resetTableQLKH();
                        MessageDialogHelper.showMessageDialog(null, "Xóa khách hàng thành công", "Thông báo");

                    }
                    else{
                        MessageDialogHelper.showConfirmDialog(null, "Xóa khách hàng không được do lỗi", "Cảnh báo");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDialogHelper.showErrorDialog( null, e.getMessage()
                        , "Lỗi");
                }
            }
        }
    }//GEN-LAST:event_btn_Xoa_QLKHActionPerformed

    private void btn_CapNhap_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhap_QLKHActionPerformed
        CapNhapKHForm.setLocationRelativeTo(null);
        restartFormCapNhapQLKH();
        CapNhapKHForm.setVisible(true);
    }//GEN-LAST:event_btn_CapNhap_QLKHActionPerformed

    private void btn_TimKiem_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiem_QLKHActionPerformed
        try {
            KhachHangDao dao = new KhachHangDao();
            List<KhachHang> list = dao.findDK(txt_MaKH_QLKH.getText(), txt_TenKH_QLKH.getText(), txt_SDT_QLKH.getText(), (int) sn_Tuoi_QLKH.getValue());
            tblModel_QLKH.setRowCount(0);
            for(KhachHang it : list)
            {
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel_QLKH.addRow(new Object[]
                    { it.getMaKH(),it.getTenKH(),it.getDiaChi(),it.getsDT(),it.getEmail(),it.getTuoi()} );

                tblModel_QLKH.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");

        }
    }//GEN-LAST:event_btn_TimKiem_QLKHActionPerformed

    private void btn_CapNhapBang_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhapBang_QLKHActionPerformed
        resetTableQLKH();
    }//GEN-LAST:event_btn_CapNhapBang_QLKHActionPerformed

    private void txt_MaKH_QLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaKH_QLKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaKH_QLKHActionPerformed

    private void btn_CapNhap_CNKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhap_CNKHActionPerformed
          StringBuilder sb = new StringBuilder();
        DataValidator.validateEmptyMaKH(txt_MaKH_CNKH, sb);
        DataValidator.validateEmptyTenNV(txt_TenKH_CNKH, sb);
       DataValidator.validateEmptyDiaChi(txt_DiaChi_CNKH, sb);
        DataValidator.validateEmptySDT(txt_SDT_CNKH, sb);
         DataValidator.validateEmptyEmail(txt_Email_CNKH, sb);
         DataValidator.validateEmptyTuoi(sn_Tuoi_CNKH, sb);
        //DataValidator.validateEmptyChucVu(txt_ChucVu_CN, sb);
        if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(null, sb.toString(), "Lỗi");
            return;
        }
        if(MessageDialogHelper.showConfirmDialog(null, "Bạn có muốn cập khách hàng không?", "Hỏi")==JOptionPane.YES_OPTION)
        {}else{return;}
        try {
            KhachHang kh =new KhachHang();
            kh.setMaKH(txt_MaKH_CNKH.getText());
            kh.setTenKH(txt_TenKH_CNKH.getText());
            kh.setDiaChi(txt_DiaChi_CNKH.getText());
            
            kh.setsDT(txt_SDT_CNKH.getText());
            kh.setEmail( txt_Email_CNKH.getText());
            kh.setTuoi((int) sn_Tuoi_CNKH.getValue());

            

            KhachHangDao dao =new KhachHangDao();

            if(dao.update(kh))
            {
                resetTableQLKH();
                MessageDialogHelper.showMessageDialog(null, "Cập nhập khách hàng thành công", "Thông báo");
                restartFormCapNhapQLKH();
            }
            else{
                MessageDialogHelper.showConfirmDialog(null, "Cập nhập khách hàng không được do lỗi", "Cảnh báo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage()
                , "Lỗi");
        }
    }//GEN-LAST:event_btn_CapNhap_CNKHActionPerformed

    private void btn_Huy_CNKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Huy_CNKHActionPerformed
        CapNhapKHForm.setVisible(false);
    }//GEN-LAST:event_btn_Huy_CNKHActionPerformed

    private void txt_SDT_CNKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDT_CNKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDT_CNKHActionPerformed

    private void btn_ThemMoi_TKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemMoi_TKH2ActionPerformed
        StringBuilder sb = new StringBuilder();
        //MessageDialogHelper.showMessageDialog(null, (String) cbx_MaTK_T.getSelectedItem(), "Thông báo");
        DataValidator.validateEmptyTenNV(txt_TenKH_TKH2, sb);
        DataValidator.validateEmpty(txt_DiaChi_TKH2, sb, "Ngày sinh không được để trống và theo định dạng MM-dd-yyyy");
        DataValidator.validateEmptySDT(txt_SDT_TKH2, sb);
         DataValidator.validateEmpty(txt_Email_TKH2, sb, "Ngày sinh không được để trống và theo định dạng MM-dd-yyyy");
          //DataValidator.validateEmpty(sn_Tuoi_TKH2, sb, "Ngày sinh không được để trống và theo định dạng MM-dd-yyyy");
        //DataValidator.validateEmptyChucVu(txt_ChucVu_T, sb);
        if(sb.length()>0)
        {
            MessageDialogHelper.showErrorDialog(null, sb.toString(), "Lỗi");
            return;
        }

        try {
            KhachHang kh = new KhachHang();
            kh.setMaKH(txt_MaKH_TKH1.getText());
            kh.setTenKH(txt_TenKH_TKH2.getText());
            kh.setDiaChi(txt_DiaChi_TKH2.getText());        
            kh.setsDT(txt_SDT_TKH2.getText());
            kh.setEmail(txt_Email_TKH2.getText());
            kh.setTuoi((int) sn_Tuoi_TKH2.getValue());
            

            KhachHangDao dao =new KhachHangDao();
            if(dao.insert(kh))
            {
                resetTableQLKH();
                MessageDialogHelper.showMessageDialog(null, "Khách hàng thêm thành công", "Thông báo");
                resetThongTinThemKHForm();
            }
            else{
                MessageDialogHelper.showConfirmDialog(null, "khách hàng không lưu được do lỗi", "Cảnh báo");
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage()
                , "Lỗi");
        }
    }//GEN-LAST:event_btn_ThemMoi_TKH2ActionPerformed

    private void btn_Huy_TKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Huy_TKH2ActionPerformed
       ThemKHForm.setVisible(false); // TODO add your handling code here:
    }//GEN-LAST:event_btn_Huy_TKH2ActionPerformed

    private void txt_SDT_TKH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SDT_TKH2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SDT_TKH2ActionPerformed

    private void btn_ChonHinh_TActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonHinh_TActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory())
                {return true;}else{
                    return file.getName().toLowerCase().endsWith(".jpg");}//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getDescription() {
                return "Image File (*.jpg)"; //To change body of generated methods, choose Tools | Templates.
            }
        });
        if(chooser.showOpenDialog(null)== JFileChooser.CANCEL_OPTION){return;}
        File file = chooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(file.getPath());
            Image img = ImageHelper.resize(icon.getImage(), 140, 150);
            ImageIcon resizedIcon = new ImageIcon(img);
            lbl_Hinh_T.setIcon(resizedIcon);
            personalImage = ImageHelper.toByteArray(img, "jpg");
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage(), "Lỗi");
                
        }
    }//GEN-LAST:event_btn_ChonHinh_TActionPerformed

    private void btn_ChonHinh_CNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonHinh_CNActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory())
                {return true;}else{
                    return file.getName().toLowerCase().endsWith(".jpg");}//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getDescription() {
                return "Image File (*.jpg)"; //To change body of generated methods, choose Tools | Templates.
            }
        });
        if(chooser.showOpenDialog(null)== JFileChooser.CANCEL_OPTION){return;}
        File file = chooser.getSelectedFile();
        try {
            ImageIcon icon = new ImageIcon(file.getPath());
            Image img = ImageHelper.resize(icon.getImage(), 140, 150);
            ImageIcon resizedIcon = new ImageIcon(img);
            lbl_Hinh_CN.setIcon(resizedIcon);
            personalImage = ImageHelper.toByteArray(img, "jpg");
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog( null, e.getMessage(), "Lỗi");
                
        }
    }//GEN-LAST:event_btn_ChonHinh_CNActionPerformed

    private void tbl_DanhSachNV_QLNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DanhSachNV_QLNVMouseClicked
        /*try {
            int row = tbl_DanhSachNV_QLNV.getSelectedRow();
            //MessageDialogHelper.showConfirmDialog(null, ""+row, "Cảnh báo");
            if(row>=0)
            {
            String id = (String) tbl_DanhSachNV_QLNV.getValueAt(row, 0);
            NhanVienDao dao = new NhanVienDao();
            NhanVien nv = dao.find(id);
            
            if(nv!=null)
            {
                
                if(nv.getHinh()!=null)
                {
                    Image img = ImageHelper.CreateImageFromByteArray(nv.getHinh(), "jpg");
                    lbl_Hinh_QLNV.setIcon(new ImageIcon(img));
                    personalImage = nv.getHinh();
                    
                }else{
                    personalImage = nv.getHinh();
                     ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                        lbl_Hinh_QLNV.setIcon(icon);}
            }}
                
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getStackTrace(),"Lỗi",JOptionPane.ERROR_MESSAGE);
        }*/
    }//GEN-LAST:event_tbl_DanhSachNV_QLNVMouseClicked

    private void txt_MaNV_CNMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_MaNV_CNMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaNV_CNMouseEntered

    private void txt_MaNV_CNMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_MaNV_CNMouseExited
        try {
            
            
            String id = txt_MaNV_CN.getText();
            NhanVienDao dao = new NhanVienDao();
            NhanVien nv = dao.find(id);
            
            if(nv!=null)
            {
                txt_MaNV_CN.setText(nv.getMaNhanVien().replaceAll("\\s",""));
                txt_TenNV_CN.setText(nv.getTenNhanVien());
                Date ns0 = new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgaySinh());
                
                String ns1 = new SimpleDateFormat("MM-dd-yyyy").format(ns0);
                Date ns2 = new SimpleDateFormat("MM-dd-yyyy").parse(ns1);
                dc_NgaySinh_CN.setDate(ns2);
                txt_SDT_CN.setText(nv.getsDT().replaceAll("\\s",""));
                if(nv.getGioiTinh().replaceAll("\\s","").equals("Nam"))
                {rb_GioiTinh_Nam_CN.setSelected(true);}else{rb_GioiTinh_Nu_CN.setSelected(true);}
                //MessageDialogHelper.showConfirmDialog(null, nv.getTinhTrang(), "Cảnh báo");
                if(nv.getTinhTrang().replaceAll("\\s","").equals("Vẫnlàmviệc"))
                {rb_VanLamViec_CN.setSelected(true);}
                else{rb_DaNghiViec_CN.setSelected(true);}                              
                cbx_ChucVu_CN.setSelectedIndex(nv.getChucVu().replaceAll("\\s","").equals("Quảnlý")?1:0);
                cbx_maTK_CN.removeAllItems();
                cbx_maTK_CN.addItem(nv.getMaTK()==null?"":(nv.getMaTK()+"-"+dao.findTenTK(nv.getMaTK())));
                loadcbxmaTK_CN();
                if(nv.getHinh()!=null)
                {
                    Image img = ImageHelper.CreateImageFromByteArray(nv.getHinh(), "jpg");
                    lbl_Hinh_CN.setIcon(new ImageIcon(img));
                    personalImage = nv.getHinh();
                    
                }else{
                    personalImage = nv.getHinh();
                     ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlynhanvien/icons/Screenshot 2021-11-17 090047.jpg")); 
                        lbl_Hinh_CN.setIcon(icon);}
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getStackTrace(),"Lỗi",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_txt_MaNV_CNMouseExited

    private void lbl_Hinh_MenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_Hinh_MenuMouseClicked
        NhanVienDao dao = new NhanVienDao();
        try {
            NhanVien nv = dao.find(SharedData.nhanVienDN.getMaNhanVien());
            SharedData.nhanVienDN = nv; 
        } catch (Exception ex) {
            Logger.getLogger(text.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setTTTaiKhoan_Menu();
    }//GEN-LAST:event_lbl_Hinh_MenuMouseClicked

    private void txt_MaNV_QLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaNV_QLNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaNV_QLNVActionPerformed

    private void txt_maNV_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maNV_QLHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maNV_QLHDActionPerformed

    private void txt_MaHD_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaHD_QLHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaHD_QLHDActionPerformed

    private void tbl_DanhSachHD_QLHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_DanhSachHD_QLHDFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DanhSachHD_QLHDFocusLost

    private void tbl_DanhSachHD_QLHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DanhSachHD_QLHDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_DanhSachHD_QLHDMouseClicked

    private void btn_LapHD_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LapHD_QLHDActionPerformed
       QLHDForm qlhd = new QLHDForm();
       qlhd.setVisible(true);
    }//GEN-LAST:event_btn_LapHD_QLHDActionPerformed

    private void btn_XemChiTiet_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XemChiTiet_QLHDActionPerformed
        try {
            String maHD = "" ;
        try {
            int row = tbl_DanhSachHD_QLHD.getSelectedRow();

            if(row>=0)
            {
                 maHD = (String) tblModel_QLHD.getValueAt(row , 0);
                
            }else{
                JOptionPane.showMessageDialog(null,"Vui lòng chọn hóa đơn","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } catch (Exception e) {
        }
            HoaDonDao daoHD = new HoaDonDao();
            HoaDon hd = new HoaDon();
            hd = daoHD.find(maHD);
            XemCTHDForm.setLocationRelativeTo(null);
            XemCTHDForm.setVisible(true);
            initTableXemCTHD();
            loadDataToTable_XemCTHD(maHD);
            lbl_MaHD_XemCTHD.setText("Mã Hóa Đơn: "+maHD);
            lbl_TongTien_XemCTHD.setText("Tổng tiền: "+hd.getTongTien());
            lbl_TTNV_XemCTHD.setText("Thu ngân: "+hd.getNhanVien().getTenNhanVien()+"  MaNV: "+hd.getNhanVien().getMaNhanVien());
            lbl_TTKH_XemCTHD.setText("Khách hàng: "+hd.getKhachHang().getTenKH()+"  MaKH: "+hd.getKhachHang().getMaKH());
            lbl_NgayLap_XemCTHD.setText("Ngày Lập: "+hd.getNgayLap());
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");

        }
    }//GEN-LAST:event_btn_XemChiTiet_QLHDActionPerformed
     public PageFormat getPageFormat(PrinterJob pj)
{
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double bodyHeight = bHeight;  
    double headerHeight = 5.0;                  
    double footerHeight = 5.0;        
    double width = cm_to_pp(8); 
    double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(0,10,width,height - cm_to_pp(1));  
            
    pf.setOrientation(PageFormat.PORTRAIT);  
    pf.setPaper(paper);    

    return pf;
}
    protected static double cm_to_pp(double cm)
    {            
	        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch)
    {            
	        return inch * 72d;            
    }
    public class BillPrintable implements Printable {
    
   
    
    
  public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
  throws PrinterException 
  {    
      
      int r= chitiethoadon.size();
      ImageIcon icon=new ImageIcon("/com/mycompany/quanlynhanvien/icons/b6508f6a62dfaa81f3ce.jpg"); 
      int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    
            double width = pageFormat.getImageableWidth();                               
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 



          //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        
        try{
            int y=20;
            int yShift = 10;
            int headerRectHeight=15;
           // int headerRectHeighta=40;
            NhanVienDao daoNV = new NhanVienDao();
            KhachHangDao daoKH = new KhachHangDao();
            g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
                ImageObserver rootPane = null;
            g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
            g2d.drawString("----------------------------------------",12,y);y+=yShift;
            g2d.drawString("         Nhà sách: Trí Tuệ Việt         ",12,y);y+=yShift;
            g2d.drawString("        115f/34b TP. Biên Hòa, ĐN       ",12,y);y+=yShift;
           // g2d.drawString("   Address Line 02 SRI LANKA ",12,y);y+=yShift;
            g2d.drawString("       www.facebook.com/TriTueViet      ",12,y);y+=yShift;
            g2d.drawString("           Phone: 0952353442            ",12,y);y+=yShift;
            
            g2d.drawString("----------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString("Mã hóa đơn: "+hoaDonIn.getMaHD()+"           ",12,y);y+=yShift;
            g2d.drawString("----------------------------------------",12,y);y+=headerRectHeight;
            g2d.drawString(" Tên sách   SL    Đơn giá     Thành tiền",10,y);y+=yShift;
            g2d.drawString("----------------------------------------",10,y);y+=headerRectHeight;
            SanPhamDao daoSP = new SanPhamDao();
            int i =0;
            for (ChiTietHoaDon it : chitiethoadon) 
            {
            //g2d.drawString(" "+daoSP.find(it.getSP().getMaSP()).getTenSP()  +"                            ",10,y)
            g2d.drawString(" "+(++i)+"."+daoSP.find(it.getSP().getMaSP()).getTenSP(),10,y);y+=yShift;g2d.drawString("   "+String.valueOf(it.getSoluong()),60,y);g2d.drawString("   "+String.valueOf(it.getDonGia()),90,y) ;g2d.drawString("   "+String.valueOf(it.getThanhTien()),160,y);y+=yShift;

            }
          
            g2d.drawString("----------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Tổng tiền: "+String.valueOf(hoaDonIn.getTongTien())+"VND",10,y);y+=yShift;
            g2d.drawString("----------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Thu ngân: "+daoNV.find(hoaDonIn.getNhanVien().getMaNhanVien()).getTenNhanVien()+"  Mã: "+hoaDonIn.getNhanVien().getMaNhanVien(),10,y);y+=yShift;
            g2d.drawString(" Khách hàng: "+daoKH.find(hoaDonIn.getKhachHang().getMaKH()).getTenKH()+"  Mã: "+hoaDonIn.getKhachHang().getMaKH(),10,y);y+=yShift;
            g2d.drawString(" Ngày: "+hoaDonIn.getNgayLap()+"   ",10,y);y+=yShift;
            //g2d.drawString("-------------------------------------",10,y);y+=yShift;
            //g2d.drawString(" Balance   :                 "+/*txtbalance.getText()+*/"   ",10,y);y+=yShift;
  
            g2d.drawString("****************************************",10,y);y+=yShift;
            g2d.drawString("      cảm ơn quý khách đã mua hàng         ",10,y);y+=yShift;
            g2d.drawString("              hẹn gặp lại                  ",10,y);y+=yShift;
            g2d.drawString("****************************************",10,y);y+=yShift;
           // g2d.drawString("       SOFTWARE BY:CODEGUID          ",10,y);y+=yShift;
            //g2d.drawString("   CONTACT: anhchung546@gmail.com    ",10,y);y+=yShift;       
           

    }
    catch(Exception e){
    e.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }

       
   }
    private void setTTIn(String maHD) throws Exception
    {
        
        HoaDonDao daoHD = new HoaDonDao();
        hoaDonIn = daoHD.find(maHD);
        ChiTietHoaDonDao cthdDao = new ChiTietHoaDonDao();
        chitiethoadon =  cthdDao.find(maHD);
        
    }
    private void XoaTTIn() throws Exception
    {
        
        
        hoaDonIn = null;
        
        chitiethoadon =  null;
        
    }
    private void btn_In_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_In_QLHDActionPerformed
        String maHD = "" ;
        try {
            int row = tbl_DanhSachHD_QLHD.getSelectedRow();

            if(row>=0)
            {
                String id = (String) tblModel_QLHD.getValueAt(row , 0);
                maHD = JOptionPane.showInputDialog("Nhập mã hóa đơn muốn in",id);
            }else{
                maHD = JOptionPane.showInputDialog("Nhập mã hóa đơn muốn in");
            }
            HoaDonDao daoHD = new HoaDonDao();
            maHD = daoHD.find(maHD).getMaHD();
        } catch (Exception e) {
        }
        //Xóa nhân viên
        

        //JOptionPane.showMessageDialog(null, maNhanVien==null);
        if((maHD==null)==false)
        {
            if(maHD.equals("")==false)
            {if(MessageDialogHelper.showConfirmDialog(null, "Bạn có muốn in hóa đơn không?", "Hỏi")==JOptionPane.YES_OPTION)
        {}else{return;}
                try {

                    try {
            setTTIn(maHD);
        } catch (Exception ex) {
            Logger.getLogger(LapHoaDonForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        bHeight = Double.valueOf(chitiethoadon.size());
        //JOptionPane.showMessageDialog(rootPane, bHeight);
        
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
             XoaTTIn();
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }

                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDialogHelper.showErrorDialog( null, e.getMessage()
                        , "Lỗi");
                }
            }
        }
    }//GEN-LAST:event_btn_In_QLHDActionPerformed

    private void btn_TimKiem_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiem_QLHDActionPerformed
       try {
            HoaDonDao dao = new HoaDonDao();
            String maHD = txt_MaHD_QLHD.getText();
            String maKH = txt_maKH_QLHD.getText();
            String maNV = txt_maNV_QLHD.getText();
            List<HoaDon> list = dao.findDK(maHD,maNV,maKH,((JTextField)dc_NgayLap_QLHD.getDateEditor().getUiComponent()).getText());
                   
            tblModel_QLHD.setRowCount(0);
            for(HoaDon it : list)
            {
                
                
                tblModel_QLHD.addRow(new Object[]
                { it.getMaHD(),it.getNhanVien().getMaNhanVien(),it.getKhachHang().getMaKH(),it.getNgayLap(),it.getTongTien(),it.getGhiChu()} );
                
                tblModel_QLHD.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");

        }
    }//GEN-LAST:event_btn_TimKiem_QLHDActionPerformed

    private void btn_CapNhapBang_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhapBang_QLHDActionPerformed
        resetTableQLHD();
    }//GEN-LAST:event_btn_CapNhapBang_QLHDActionPerformed

    private void txt_maKH_QLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maKH_QLHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maKH_QLHDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(text.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(text.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(text.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(text.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new text().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog CapNhapKHForm;
    private javax.swing.JDialog CapNhapNVForm;
    private javax.swing.JDialog ThemKHForm;
    private javax.swing.JDialog ThemNVForm;
    private javax.swing.JDialog XemCTHDForm;
    private javax.swing.JButton btn_CapNhapBang_QLHD;
    private javax.swing.JButton btn_CapNhapBang_QLKH;
    private javax.swing.JButton btn_CapNhapBang_QLNV;
    private javax.swing.JButton btn_CapNhap_CN;
    private javax.swing.JButton btn_CapNhap_CNKH;
    private javax.swing.JButton btn_CapNhap_QLKH;
    private javax.swing.JButton btn_CapNhap_QLNV;
    private javax.swing.JButton btn_ChonHinh_CN;
    private javax.swing.JButton btn_ChonHinh_T;
    private javax.swing.JButton btn_DangXuat_Menu;
    private javax.swing.JButton btn_Dangxuat;
    private javax.swing.JButton btn_DoiMK;
    private javax.swing.JButton btn_DoiMK_Menu;
    private javax.swing.JButton btn_Huy_CN;
    private javax.swing.JButton btn_Huy_CNKH;
    private javax.swing.JButton btn_Huy_T;
    private javax.swing.JButton btn_Huy_TKH2;
    private javax.swing.JButton btn_In_QLHD;
    private javax.swing.JButton btn_LapHD_QLHD;
    private javax.swing.JButton btn_QLHD_QLNV;
    private javax.swing.JButton btn_QLKH_QLNV;
    private javax.swing.JButton btn_QLNV_QLNV;
    private javax.swing.JButton btn_QLSP_QLNV;
    private javax.swing.JButton btn_TaiKhoan_QLNV;
    private javax.swing.JButton btn_TaoTK;
    private javax.swing.JButton btn_ThemKH_QLKH;
    private javax.swing.JButton btn_ThemMoi_T;
    private javax.swing.JButton btn_ThemMoi_TKH2;
    private javax.swing.JButton btn_ThemNV_QLNV;
    private javax.swing.JButton btn_ThongKe_QLNV;
    private javax.swing.JButton btn_TimKiem_QLHD;
    private javax.swing.JButton btn_TimKiem_QLKH;
    private javax.swing.JButton btn_TimKiem_QLNV;
    private javax.swing.JButton btn_TrangChu_QLNV;
    private javax.swing.JButton btn_XemChiTiet_QLHD;
    private javax.swing.JButton btn_Xoa_QLKH;
    private javax.swing.JButton btn_Xoa_QLNV;
    private javax.swing.ButtonGroup btng_NamNu_CN;
    private javax.swing.ButtonGroup btng_NamNu_QLNV;
    private javax.swing.ButtonGroup btng_NamNu_QLTK;
    private javax.swing.ButtonGroup btng_NamNu_T;
    private javax.swing.ButtonGroup btng_TinhTrang_CN;
    private javax.swing.JComboBox<String> cbx_ChucVu_CN;
    private javax.swing.JComboBox<String> cbx_ChucVu_T;
    private javax.swing.JComboBox<String> cbx_MaTK_T;
    private javax.swing.JComboBox<String> cbx_maTK_CN;
    private com.toedter.calendar.JDateChooser dc_NgayLap_QLHD;
    private com.toedter.calendar.JDateChooser dc_NgaySinh_CN;
    private com.toedter.calendar.JDateChooser dc_NgaySinh_T;
    private com.toedter.calendar.JDateChooser dc_Ngaysinh_QLTK;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl_ChucVu;
    private javax.swing.JLabel lbl_ChucVu_CN;
    private javax.swing.JLabel lbl_ChucVu_CNKH;
    private javax.swing.JLabel lbl_ChucVu_Menu;
    private javax.swing.JLabel lbl_ChucVu_QLNV;
    private javax.swing.JLabel lbl_ChucVu_T;
    private javax.swing.JLabel lbl_ChucVu_TKH2;
    private javax.swing.JLabel lbl_Clock_Menu;
    private javax.swing.JLabel lbl_DiaChi_CNKH;
    private javax.swing.JLabel lbl_DiaChi_TKH2;
    private javax.swing.JLabel lbl_GioiTinh;
    private javax.swing.JLabel lbl_GioiTinh_CN;
    private javax.swing.JLabel lbl_GioiTinh_QLNV;
    private javax.swing.JLabel lbl_GioiTinh_T;
    private javax.swing.JLabel lbl_Hinh;
    private javax.swing.JLabel lbl_HinhNV_CN;
    private javax.swing.JLabel lbl_HinhNV_QLTK;
    private javax.swing.JLabel lbl_HinhNV_T;
    private javax.swing.JLabel lbl_Hinh_CN;
    private javax.swing.JLabel lbl_Hinh_Menu;
    private javax.swing.JLabel lbl_Hinh_QLTK;
    private javax.swing.JLabel lbl_Hinh_T;
    private javax.swing.JLabel lbl_MaHD_QLHD;
    private javax.swing.JLabel lbl_MaHD_XemCTHD;
    private javax.swing.JLabel lbl_MaKH_CNKH;
    private javax.swing.JLabel lbl_MaKH_QLHD;
    private javax.swing.JLabel lbl_MaKH_QLKH;
    private javax.swing.JLabel lbl_MaKH_TKH2;
    private javax.swing.JLabel lbl_MaNV_CN;
    private javax.swing.JLabel lbl_MaNV_QLHD;
    private javax.swing.JLabel lbl_MaNV_QLNV;
    private javax.swing.JLabel lbl_MaNV_T;
    private javax.swing.JLabel lbl_MaTK;
    private javax.swing.JLabel lbl_MaTK_CN;
    private javax.swing.JLabel lbl_MaTK_QLTK;
    private javax.swing.JLabel lbl_MaTK_T;
    private javax.swing.JLabel lbl_NgayLap_QLHD;
    private javax.swing.JLabel lbl_NgayLap_XemCTHD;
    private javax.swing.JLabel lbl_NgaySinh_CN;
    private javax.swing.JLabel lbl_NgaySinh_T;
    private javax.swing.JLabel lbl_Ngaysinh;
    private javax.swing.JLabel lbl_SDT;
    private javax.swing.JLabel lbl_SDT_CN;
    private javax.swing.JLabel lbl_SDT_CNKH;
    private javax.swing.JLabel lbl_SDT_QLKH;
    private javax.swing.JLabel lbl_SDT_T;
    private javax.swing.JLabel lbl_SDT_TKH2;
    private javax.swing.JLabel lbl_TTKH_XemCTHD;
    private javax.swing.JLabel lbl_TTNV_XemCTHD;
    private javax.swing.JLabel lbl_TTTaiKhoan;
    private javax.swing.JLabel lbl_TenForm_QLHD;
    private javax.swing.JLabel lbl_TenForm_QLKH;
    private javax.swing.JLabel lbl_TenForm_QLNV;
    private javax.swing.JLabel lbl_TenKH_CNKH;
    private javax.swing.JLabel lbl_TenKH_QLKH;
    private javax.swing.JLabel lbl_TenKH_TKH2;
    private javax.swing.JLabel lbl_TenNV;
    private javax.swing.JLabel lbl_TenNV_CN;
    private javax.swing.JLabel lbl_TenNV_QLNV;
    private javax.swing.JLabel lbl_TenNV_T;
    private javax.swing.JLabel lbl_TenTK_QLTK;
    private javax.swing.JLabel lbl_TenTaiKhoan_Menu;
    private javax.swing.JLabel lbl_TieuCN;
    private javax.swing.JLabel lbl_TieuDe_CNKH;
    private javax.swing.JLabel lbl_TieuDe_QLNV;
    private javax.swing.JLabel lbl_TieuDe_T;
    private javax.swing.JLabel lbl_TieuDe_TKH2;
    private javax.swing.JLabel lbl_Tieu_TaiKhoan;
    private javax.swing.JLabel lbl_TinhTrang_CN;
    private javax.swing.JLabel lbl_TongTien_XemCTHD;
    private javax.swing.JLabel lbl_Tuoi_CNKH;
    private javax.swing.JLabel lbl_Tuoi_QLKH;
    private javax.swing.JLabel lbl_Tuoi_TKH2;
    private javax.swing.JPanel pn_CNTimK_QLHD;
    private javax.swing.JPanel pn_CNTimK_QLKH;
    private javax.swing.JPanel pn_CNTimK_QLNV;
    private javax.swing.JPanel pn_ChucNang_CN;
    private javax.swing.JPanel pn_ChucNang_CNKH;
    private javax.swing.JPanel pn_ChucNang_QLHD;
    private javax.swing.JPanel pn_ChucNang_QLKH;
    private javax.swing.JPanel pn_ChucNang_QLNV;
    private javax.swing.JPanel pn_ChucNang_T;
    private javax.swing.JPanel pn_ChucNang_TKH2;
    private javax.swing.JPanel pn_Menu_QLNV;
    private javax.swing.JPanel pn_QLHD;
    private javax.swing.JPanel pn_QLHD_Form;
    private javax.swing.JPanel pn_QLKH;
    private javax.swing.JPanel pn_QLNV;
    private javax.swing.JPanel pn_QLTK;
    private javax.swing.JPanel pn_TaiKhoan_Menu;
    private javax.swing.JPanel pn_TenForm_QLHD;
    private javax.swing.JPanel pn_TenForm_QLKH;
    private javax.swing.JPanel pn_TenForm_QLNV;
    private javax.swing.JPanel pn_TieuDeCN;
    private javax.swing.JPanel pn_TieuDe_CNKH;
    private javax.swing.JPanel pn_TieuDe_QLNV;
    private javax.swing.JPanel pn_TieuDe_T;
    private javax.swing.JPanel pn_TieuDe_TKH2;
    private javax.swing.JPanel pn_TrangChu;
    private javax.swing.JRadioButton rb_DaNghiViec_CN;
    private javax.swing.JRadioButton rb_GioiTinh_Nam_CN;
    private javax.swing.JRadioButton rb_GioiTinh_Nu_CN;
    private javax.swing.JRadioButton rb_Nam_QLNV;
    private javax.swing.JRadioButton rb_Nam_QLTK;
    private javax.swing.JRadioButton rb_Nam_T;
    private javax.swing.JRadioButton rb_Nu_QLNV;
    private javax.swing.JRadioButton rb_Nu_QLTK;
    private javax.swing.JRadioButton rb_Nu_T;
    private javax.swing.JRadioButton rb_VanLamViec_CN;
    private javax.swing.JScrollPane scp_DanhSachHD_QLHD;
    private javax.swing.JScrollPane scp_DanhSachKH_QLKH;
    private javax.swing.JScrollPane scp_DanhSachNV_QLNV;
    private javax.swing.JSpinner sn_Tuoi_CNKH;
    private javax.swing.JSpinner sn_Tuoi_QLKH;
    private javax.swing.JSpinner sn_Tuoi_TKH2;
    private javax.swing.JScrollPane sp_XemCTHD;
    private javax.swing.JTable tb_XemCTHD;
    private javax.swing.JTable tbl_DanhSachHD_QLHD;
    private javax.swing.JTable tbl_DanhSachKH_QLKH;
    private javax.swing.JTable tbl_DanhSachNV_QLNV;
    private javax.swing.JTabbedPane tbpn_QLSP;
    private javax.swing.JTextField txt_ChucVu_QLNV;
    private javax.swing.JTextField txt_ChucVu_QLTK;
    private javax.swing.JTextField txt_DiaChi_CNKH;
    private javax.swing.JTextField txt_DiaChi_TKH2;
    private javax.swing.JTextField txt_Email_CNKH;
    private javax.swing.JTextField txt_Email_TKH2;
    private javax.swing.JTextField txt_MaHD_QLHD;
    private javax.swing.JTextField txt_MaKH_CNKH;
    private javax.swing.JTextField txt_MaKH_QLKH;
    private javax.swing.JTextField txt_MaKH_TKH1;
    private javax.swing.JTextField txt_MaNV_CN;
    private javax.swing.JTextField txt_MaNV_QLNV;
    private javax.swing.JTextField txt_MaNV_QLTK;
    private javax.swing.JTextField txt_MaNV_T;
    private javax.swing.JTextField txt_MaTK_QLTK;
    private javax.swing.JTextField txt_SDT_CN;
    private javax.swing.JTextField txt_SDT_CNKH;
    private javax.swing.JTextField txt_SDT_QLKH;
    private javax.swing.JTextField txt_SDT_QLTK;
    private javax.swing.JTextField txt_SDT_T;
    private javax.swing.JTextField txt_SDT_TKH2;
    private javax.swing.JTextField txt_TenKH_CNKH;
    private javax.swing.JTextField txt_TenKH_QLKH;
    private javax.swing.JTextField txt_TenKH_TKH2;
    private javax.swing.JTextField txt_TenNV_CN;
    private javax.swing.JTextField txt_TenNV_QLNV;
    private javax.swing.JTextField txt_TenNV_QLTK;
    private javax.swing.JTextField txt_TenNV_T;
    private javax.swing.JTextField txt_TenTK_QLTK;
    private javax.swing.JTextField txt_maKH_QLHD;
    private javax.swing.JTextField txt_maNV_QLHD;
    // End of variables declaration//GEN-END:variables

    private void resetTableQLKH() {
        tblModel_QLKH.setRowCount(0);
        loadDataToTableQLKH(); //To change body of generated methods, choose Tools | Templates.
    }
    private void resetTableQLHD() {
        tblModel_QLHD.setRowCount(0);
        loadDataToTable_QLHD(); //To change body of generated methods, choose Tools | Templates.
    }
    private void loadDataToTable_XemCTHD(String maHD) {
          try {
            ChiTietHoaDonDao dao = new ChiTietHoaDonDao();
            SanPhamDao daoSP = new SanPhamDao();
            List<ChiTietHoaDon> list = dao.find(maHD);
            tblModel_XemCTHD.setRowCount(0);
            for(ChiTietHoaDon it : list)
            {
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel_XemCTHD.addRow(new Object[]
                { it.getSP().getMaSP(),daoSP.find(it.getSP().getMaSP()).getTenSP(),it.getSoluong(),it.getDonGia(),it.getThanhTien()} );
                
                tblModel_XemCTHD.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }//To change body of generated methods, choose Tools | Templates.
    }
    private void loadDataToTableQLKH() {
          try {
            KhachHangDao dao = new KhachHangDao();
            List<KhachHang> list = dao.findAll();
            tblModel_QLKH.setRowCount(0);
            for(KhachHang it : list)
            {
                //MessageDialogHelper.showMessageDialog(null, it.getTinhTrang(), "Thông báo");
                tblModel_QLKH.addRow(new Object[]
                { it.getMaKH(),it.getTenKH(),it.getDiaChi(),it.getsDT(),it.getEmail(),it.getTuoi()} );
                
                tblModel_QLKH.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(null, e.getMessage(), "Lỗi");
                 
        }//To change body of generated methods, choose Tools | Templates.
    }

    private void initTableQLKH() {
        tblModel_QLKH = new DefaultTableModel();
        tblModel_QLKH.setColumnIdentifiers(new String [] {"Mã Khách Hàng","Tên Khách Hàng","Địa Chỉ","Số Điện Thoại","Email","Tuổi"});
        tbl_DanhSachKH_QLKH.setModel(tblModel_QLKH);//To change body of generated methods, choose Tools | Templates.
    }

    private void resetThongTinThemKHForm() {
        txt_MaKH_TKH1.setText(tuDongLayMaQLKH());
        resetTextFiled(txt_TenKH_TKH2);
        resetTextFiled(txt_DiaChi_TKH2);
        resetTextFiled(txt_SDT_TKH2);
        resetTextFiled(txt_Email_TKH2);
        resetTextFiled(sn_Tuoi_TKH2);
        //resetTextFiled(txt_ChucVu_T);throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void resetTextFiled(JSpinner field) {
         field.setValue(0);
         field.setBackground(Color.white);//To change body of generated methods, choose Tools | Templates.
    }

    private void restartFormCapNhapQLKH() {
        resetTextFiled(txt_MaKH_CNKH);
        resetTextFiled(txt_TenKH_CNKH);
        resetTextFiled(txt_DiaChi_CNKH);
        resetTextFiled(txt_SDT_CNKH);
        resetTextFiled(txt_Email_CNKH);
        resetTextFiled(sn_Tuoi_CNKH);
        //resetTextFiled(txt_ChucVu_CN);
        
        //String id = (String) tbl_DanhSachNV_QLNV.getValueAt(tbl_DanhSachNV_QLNV.getSelectedRow(), 7);
        NhapTTCapNhapTTableQLKH();
         //To change body of generated methods, choose Tools | Templates.
    }

    private void NhapTTCapNhapTTableQLKH() {
       try {
            int row = tbl_DanhSachKH_QLKH.getSelectedRow();
            //MessageDialogHelper.showConfirmDialog(null, ""+row, "Cảnh báo");
            if(row>=0)
            {
            String id = (String) tbl_DanhSachKH_QLKH.getValueAt(row, 0);
            KhachHangDao dao = new KhachHangDao();
            KhachHang kh = dao.find(id);
            
            if(kh!=null)
            {
                txt_MaKH_CNKH.setText(kh.getMaKH().replaceAll("\\s",""));
                txt_TenKH_CNKH.setText(kh.getTenKH());
                txt_DiaChi_CNKH.setText(kh.getDiaChi());
                txt_SDT_CNKH.setText(kh.getsDT().replaceAll("\\s",""));               
                sn_Tuoi_CNKH.setValue(kh.getTuoi());
                try {
                     txt_Email_CNKH.setText(kh.getEmail().replaceAll("\\s",""));
                } catch (Exception e) {
                }
               
                
            }}
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getStackTrace(),"Lỗi",JOptionPane.ERROR_MESSAGE);
        } //To change body of generated methods, choose Tools | Templates.
    }
}
