/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pertemuan_15;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class NewJFrame extends javax.swing.JFrame {

    private final Map<String, String> kamarMap = new HashMap<>();

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        loadDataKamar();
        loadDataTamu();
        loadDataPemesanan();
        loadComboBoxKamar();
    }

    private final String URL = "jdbc:postgresql://localhost:5432/PBO_Pertemuan15";
    private final String USER = "postgres";
    private final String PASS = "ZayZiya03";

    // ======================================================
    // FUNGSI KONEKSI
    // ======================================================
    private java.sql.Connection getConnection() {
        try {
            return java.sql.DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal koneksi database: " + e.getMessage());
            return null;
        }
    }

    private void loadDataKamar() {
        try (java.sql.Connection conn = getConnection()) {
            String sql = "SELECT * FROM kamar ORDER BY id_kamar ASC";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) Table_Kamar.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_kamar"),
                    rs.getString("nomor_kamar"),
                    rs.getString("tipe_kamar"),
                    rs.getString("fasilitas")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataTamu() {
        try (java.sql.Connection conn = getConnection()) {
            // pakai kolom yang sesuai di DB: nik, nama_tamu, lama_menginap, id_kamar
            String sql = "SELECT nik, nama_tamu, lama_menginap, id_kamar FROM tamu ORDER BY nik ASC";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) Table_Tamu.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nik"),
                    rs.getString("nama_tamu"),
                    rs.getString("lama_menginap"),
                    rs.getString("id_kamar") // <-- pastikan ini sesuai nama kolom di DB
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataPemesanan() {
        try (java.sql.Connection conn = getConnection()) {

            String sql = ""
                    + "SELECT "
                    + "    kt.id_kamar AS id_kamar, " // tampilkan ID kamar (K1, K2)
                    + "    t.nik || ' - ' || t.nama_tamu AS nik_nama, "
                    + "    k.nomor_kamar AS nomor_kamar "
                    + "FROM kamar_terpesan kt "
                    + "JOIN tamu t ON kt.nik = t.nik "
                    + "JOIN kamar k ON kt.id_kamar = k.id_kamar "
                    + "ORDER BY kt.id_kamar_terpesan ASC";

            java.sql.PreparedStatement st = conn.prepareStatement(sql);
            java.sql.ResultSet rs = st.executeQuery();

            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) Table_Pemesanan.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_kamar"), // sebelumnya: id_kamar_terpesan
                    rs.getString("nik_nama"),
                    rs.getString("nomor_kamar")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComboBoxKamar() {
        cb_KamarDipesan.removeAllItems();
        kamarMap.clear();

        String sql = "SELECT id_kamar, nomor_kamar FROM kamar ORDER BY id_kamar";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                String id = rs.getString("id_kamar");
                String nomor = rs.getString("nomor_kamar");
                if (id == null) {
                    continue;
                }
                nomor = (nomor == null) ? "" : nomor;
                kamarMap.put(id, nomor);
                model.addElement(id); // SIMPAN nilai id (value yang akan dikirim)
            }
            cb_KamarDipesan.setModel(model);

            // Renderer agar user melihat "K001 - 101" (atau hanya id jika nomor kosong)
            cb_KamarDipesan.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        lbl.setText("");
                    } else {
                        String id = value.toString();
                        String nomor = kamarMap.getOrDefault(id, "");
                        if (!nomor.isEmpty()) {
                            lbl.setText(id + " - " + nomor);
                        } else {
                            lbl.setText(id);
                        }
                    }
                    return lbl;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal load combo kamar: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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

        TappedPane_Kamar_Tamu_Pemesanan = new javax.swing.JTabbedPane();
        Kamar = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Kamar = new javax.swing.JTable();
        btn_Insert_Kamar = new javax.swing.JButton();
        btn_Update_Kamar = new javax.swing.JButton();
        btn_Delete_Kamar = new javax.swing.JButton();
        btn_Cetak_Kamar = new javax.swing.JButton();
        btn_Upload_Kamar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tf_IDKamar_Kamar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tf_NoKamar_Kamar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_TipeKamar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tf_Fasilitas_Kamar = new javax.swing.JTextField();
        lbl_Keluar = new java.awt.Label();
        Tamu = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Tamu = new javax.swing.JTable();
        btn_Insert_Tamu = new javax.swing.JButton();
        btn_Update_Tamu = new javax.swing.JButton();
        btn_Delete_Tamu = new javax.swing.JButton();
        btn_Cetak_Tamu = new javax.swing.JButton();
        btn_Upload_Tamu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tf_NIK = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_NamaTamu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tf_LamaMenginap = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cb_KamarDipesan = new javax.swing.JComboBox<>();
        Pemesanan = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Pemesanan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Table_Kamar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Kamar", "No Kamar", "Tipe Kamar", "Fasilitas"
            }
        ));
        Table_Kamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_KamarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Kamar);

        btn_Insert_Kamar.setText("Insert");
        btn_Insert_Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Insert_KamarActionPerformed(evt);
            }
        });

        btn_Update_Kamar.setText("Update");
        btn_Update_Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Update_KamarActionPerformed(evt);
            }
        });

        btn_Delete_Kamar.setText("Delete");
        btn_Delete_Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Delete_KamarActionPerformed(evt);
            }
        });

        btn_Cetak_Kamar.setText("Cetak");
        btn_Cetak_Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cetak_KamarActionPerformed(evt);
            }
        });

        btn_Upload_Kamar.setText("Upload");
        btn_Upload_Kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Upload_KamarActionPerformed(evt);
            }
        });

        jLabel1.setText("ID Kamar");

        jLabel2.setText("No Kamar");

        jLabel3.setText("Tipe Kamar");

        jLabel4.setText("Fasilitas");

        lbl_Keluar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lbl_Keluar.setText("Keluar");
        lbl_Keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_KeluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 91, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(tf_Fasilitas_Kamar, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(tf_TipeKamar)
                            .addComponent(tf_NoKamar_Kamar)
                            .addComponent(tf_IDKamar_Kamar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_Insert_Kamar)
                                    .addComponent(btn_Update_Kamar)
                                    .addComponent(btn_Delete_Kamar)
                                    .addComponent(btn_Cetak_Kamar)
                                    .addComponent(btn_Upload_Kamar))
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_Keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tf_IDKamar_Kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_NoKamar_Kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_TipeKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_Fasilitas_Kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_Insert_Kamar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Update_Kamar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Delete_Kamar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Cetak_Kamar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Upload_Kamar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_Keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        Kamar.addTab("Kamar", jPanel1);

        TappedPane_Kamar_Tamu_Pemesanan.addTab("Kamar", Kamar);

        Table_Tamu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NIK", "Nama Tamu", "Lama Menginap", "ID Kamar"
            }
        ));
        Table_Tamu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_TamuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_Tamu);

        btn_Insert_Tamu.setText("Insert");
        btn_Insert_Tamu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Insert_TamuActionPerformed(evt);
            }
        });

        btn_Update_Tamu.setText("Update");
        btn_Update_Tamu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Update_TamuActionPerformed(evt);
            }
        });

        btn_Delete_Tamu.setText("Delete");
        btn_Delete_Tamu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Delete_TamuActionPerformed(evt);
            }
        });

        btn_Cetak_Tamu.setText("Cetak");
        btn_Cetak_Tamu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Cetak_TamuActionPerformed(evt);
            }
        });

        btn_Upload_Tamu.setText("Upload");
        btn_Upload_Tamu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Upload_TamuActionPerformed(evt);
            }
        });

        jLabel5.setText("NIK");

        jLabel6.setText("Nama Tamu");

        jLabel7.setText("Lama Menginap");

        jLabel8.setText("Kamar Dipesan");

        cb_KamarDipesan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_KamarDipesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_KamarDipesanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 85, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_NIK, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_NamaTamu, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_LamaMenginap, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_Insert_Tamu)
                                    .addComponent(btn_Update_Tamu)
                                    .addComponent(btn_Delete_Tamu)
                                    .addComponent(btn_Cetak_Tamu)
                                    .addComponent(btn_Upload_Tamu)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cb_KamarDipesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_Insert_Tamu)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Update_Tamu)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Delete_Tamu)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Cetak_Tamu)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Upload_Tamu))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(tf_NIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_NamaTamu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_LamaMenginap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_KamarDipesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tamu.addTab("Tamu", jPanel2);

        TappedPane_Kamar_Tamu_Pemesanan.addTab("Tamu", Tamu);

        Table_Pemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Kamar Terpesan", "NIK", "Nomor Kamar"
            }
        ));
        jScrollPane3.setViewportView(Table_Pemesanan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 91, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        Pemesanan.addTab("Pemesanan", jPanel4);

        TappedPane_Kamar_Tamu_Pemesanan.addTab("Pemesanan", Pemesanan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TappedPane_Kamar_Tamu_Pemesanan)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TappedPane_Kamar_Tamu_Pemesanan)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Insert_KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Insert_KamarActionPerformed
        // TODO add your handling code here:

        String id = tf_IDKamar_Kamar.getText().trim();
        String no = tf_NoKamar_Kamar.getText().trim();
        String tipe = tf_TipeKamar.getText().trim();
        String fasilitas = tf_Fasilitas_Kamar.getText().trim();

        if (id.isEmpty() || no.isEmpty() || tipe.isEmpty() || fasilitas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!"); //Error
            return;
        }

        try (Connection conn = getConnection()) { //Error

            // ✅ CEK ID kamar duplikat
            PreparedStatement psCheckID = conn.prepareStatement( //Error
                    "SELECT COUNT(*) FROM kamar WHERE id_kamar = ?" //Error
            );
            psCheckID.setString(1, id);
            ResultSet rs1 = psCheckID.executeQuery(); //Error
            rs1.next();
            if (rs1.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, //Error
                        "ID Kamar sudah digunakan!\nGunakan ID lain.",
                        "Error", JOptionPane.ERROR_MESSAGE); //Error
                return;
            }

            // ✅ CEK Nomor kamar duplikat
            PreparedStatement psCheckNo = conn.prepareStatement( //Error
                    "SELECT COUNT(*) FROM kamar WHERE nomor_kamar = ?"
            );
            psCheckNo.setString(1, no);
            ResultSet rs2 = psCheckNo.executeQuery(); //Error
            rs2.next();
            if (rs2.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, //Error
                        "Nomor kamar sudah ada!\nGunakan nomor lain.",
                        "Error", JOptionPane.ERROR_MESSAGE); //Error
                return;
            }

            // ✅ INSERT jika lolos validasi
            PreparedStatement psInsert = conn.prepareStatement( //Error
                    "INSERT INTO kamar(id_kamar, nomor_kamar, tipe_kamar, fasilitas) VALUES (?,?,?,?)"
            );
            psInsert.setString(1, id);
            psInsert.setString(2, no);
            psInsert.setString(3, tipe);
            psInsert.setString(4, fasilitas);
            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(this, "Kamar berhasil ditambahkan!"); //Error

            loadDataKamar();
            loadComboBoxKamar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, //Error
                    "Gagal insert kamar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE); //Error
        }
    }//GEN-LAST:event_btn_Insert_KamarActionPerformed

    private void btn_Update_KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Update_KamarActionPerformed
        // TODO add your handling code here:
        String id = tf_IDKamar_Kamar.getText().trim();
        String no = tf_NoKamar_Kamar.getText().trim();
        String tipe = tf_TipeKamar.getText().trim();
        String fasilitas = tf_Fasilitas_Kamar.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Isi ID kamar yang ingin diupdate!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection()) {

            // 🔍 CEK apakah nomor kamar akan bentrok dengan kamar lain
            PreparedStatement psCheck = conn.prepareStatement(
                    "SELECT COUNT(*) FROM kamar WHERE nomor_kamar = ? AND id_kamar <> ?"
            );
            psCheck.setString(1, no);
            psCheck.setString(2, id);

            ResultSet rs = psCheck.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this,
                        "Nomor kamar sudah dipakai kamar lain!\nGunakan nomor berbeda.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 🔄 Lanjut update data jika aman
            PreparedStatement psUpdate = conn.prepareStatement(
                    "UPDATE kamar SET nomor_kamar=?, tipe_kamar=?, fasilitas=? WHERE id_kamar=?"
            );

            psUpdate.setString(1, no);
            psUpdate.setString(2, tipe);
            psUpdate.setString(3, fasilitas);
            psUpdate.setString(4, id);

            psUpdate.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data kamar berhasil diupdate!");

            loadDataKamar();
            loadComboBoxKamar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal update kamar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Update_KamarActionPerformed

    private void btn_Delete_KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Delete_KamarActionPerformed
        // TODO add your handling code here:
        String id = tf_IDKamar_Kamar.getText();

        if (id.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Isi ID kamar yang ingin dihapus!");
            return;
        }

        // Cek apakah kamar sedang dipesan
        try (java.sql.Connection c = getConnection(); java.sql.PreparedStatement p = c.prepareStatement(
                "SELECT * FROM kamar_terpesan WHERE id_kamar=?")) {

            p.setString(1, id);
            java.sql.ResultSet r = p.executeQuery();

            if (r.next()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Tidak dapat menghapus! Kamar ini sedang dipesan tamu.");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Jika aman → hapus kamar
        try (java.sql.Connection c = getConnection(); java.sql.PreparedStatement p = c.prepareStatement(
                "DELETE FROM kamar WHERE id_kamar=?")) {

            p.setString(1, id);
            p.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Kamar berhasil dihapus!");

            loadDataKamar();
            loadComboBoxKamar();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal delete kamar: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_Delete_KamarActionPerformed

    private void btn_Cetak_KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cetak_KamarActionPerformed
        // TODO add your handling code here:
        String jrxmlPath = "src/pertemuan_15/report_kamar.jrxml";
        String jasperPath = "src/pertemuan_15/report_kamar.jasper";
        String csvOutput = "report_kamar.csv";

        String url = "jdbc:postgresql://localhost:5432/PBO_Pertemuan15";
        String user = "postgres";
        String pass = "ZayZiya03";

        try {

            // 1. Compile JRXML → JASPER
            net.sf.jasperreports.engine.JasperCompileManager
                    .compileReportToFile(jrxmlPath, jasperPath);

            // 2. Koneksi ke database
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, pass);

            // 3. Fill report
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            net.sf.jasperreports.engine.JasperPrint jasperPrint
                    = net.sf.jasperreports.engine.JasperFillManager.fillReport(
                            jasperPath, params, conn);

            // 4. Tampilkan preview report
            net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);

            // 5. Export ke CSV (VERSI AMAN)
            net.sf.jasperreports.engine.export.JRCsvExporter exporter
                    = new net.sf.jasperreports.engine.export.JRCsvExporter();

            exporter.setExporterInput(
                    new net.sf.jasperreports.export.SimpleExporterInput(jasperPrint));

            exporter.setExporterOutput(
                    new net.sf.jasperreports.export.SimpleWriterExporterOutput(csvOutput));

            net.sf.jasperreports.export.SimpleCsvExporterConfiguration cfg
                    = new net.sf.jasperreports.export.SimpleCsvExporterConfiguration();

            cfg.setFieldDelimiter(",");        // pemisah kolom
            cfg.setRecordDelimiter("\n");      // baris baru rapih
            // (versi Jasper lama memang tidak punya setUseJasperLayout → aman)

            exporter.setConfiguration(cfg);
            exporter.exportReport();

            conn.close();

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Laporan ditampilkan dan CSV diekspor ke: " + csvOutput,
                    "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal cetak/ekspor laporan: " + ex.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Cetak_KamarActionPerformed

    private void btn_Upload_KamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Upload_KamarActionPerformed
        // TODO add your handling code here:
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }

        java.io.File file = chooser.getSelectedFile();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file)); java.sql.Connection c = getConnection()) {

            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] row = line.split(",");

                String sql = "INSERT INTO kamar(id_kamar, nomor_kamar, tipe_kamar, fasilitas) VALUES (?,?,?,?)";

                try (java.sql.PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, row[0]);
                    p.setString(2, row[1]);
                    p.setString(3, row[2]);
                    p.setString(4, row[3]);
                    p.executeUpdate();
                }
            }

            javax.swing.JOptionPane.showMessageDialog(this, "Upload CSV berhasil!");

            loadDataKamar();
            loadComboBoxKamar();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal upload CSV: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_Upload_KamarActionPerformed

    private void btn_Insert_TamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Insert_TamuActionPerformed
        // TODO add your handling code here:
        String nik = tf_NIK.getText().trim();
        String nama = tf_NamaTamu.getText().trim();
        String lama = tf_LamaMenginap.getText().trim();
        String idKamar = cb_KamarDipesan.getSelectedItem() == null ? "" : cb_KamarDipesan.getSelectedItem().toString();

        if (nik.isEmpty() || nama.isEmpty() || lama.isEmpty() || idKamar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        try (Connection conn = getConnection()) {
            // cek NIK sudah ada?
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM tamu WHERE nik = ?")) {
                ps.setString(1, nik);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "NIK sudah terdaftar. Gunakan NIK lain.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // cek kamar sudah dipesan?
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM kamar_terpesan WHERE id_kamar = ?")) {
                ps.setString(1, idKamar);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Kamar sudah dipesan oleh tamu lain.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // insert ke tamu
            try (PreparedStatement p = conn.prepareStatement("INSERT INTO tamu(nik, nama_tamu, lama_menginap, id_kamar) VALUES (?,?,?,?)")) {
                p.setString(1, nik);
                p.setString(2, nama);
                p.setString(3, lama);
                p.setString(4, idKamar);
                p.executeUpdate();
            }

            // insert ke kamar_terpesan
            try (PreparedStatement p2 = conn.prepareStatement("INSERT INTO kamar_terpesan(nik, id_kamar) VALUES (?,?)")) {
                p2.setString(1, nik);
                p2.setString(2, idKamar);
                p2.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Tamu & pemesanan berhasil ditambahkan!");
            loadDataTamu();
            loadDataPemesanan();
            loadComboBoxKamar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal insert tamu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_Insert_TamuActionPerformed

    private void btn_Update_TamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Update_TamuActionPerformed
        // TODO add your handling code here:
        String nik = tf_NIK.getText().trim();
        String nama = tf_NamaTamu.getText().trim();
        String lama = tf_LamaMenginap.getText().trim();
        String idKamar = cb_KamarDipesan.getSelectedItem() == null ? "" : cb_KamarDipesan.getSelectedItem().toString();

        if (nik.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIK wajib diisi untuk update!");
            return;
        }
        if (nama.isEmpty() || lama.isEmpty() || idKamar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        try (Connection conn = getConnection()) {
            // cek kamar dimiliki tamu lain?
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM kamar_terpesan WHERE id_kamar = ? AND nik <> ?")) {
                ps.setString(1, idKamar);
                ps.setString(2, nik);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Kamar sudah dipesan tamu lain. Pilih kamar lain.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // update tamu
            try (PreparedStatement ps = conn.prepareStatement("UPDATE tamu SET nama_tamu=?, lama_menginap=?, id_kamar=? WHERE nik=?")) {
                ps.setString(1, nama);
                ps.setString(2, lama);
                ps.setString(3, idKamar);
                ps.setString(4, nik);
                ps.executeUpdate();
            }

            // update kamar_terpesan jika ada
            try (PreparedStatement ps = conn.prepareStatement("UPDATE kamar_terpesan SET id_kamar = ? WHERE nik = ?")) {
                ps.setString(1, idKamar);
                ps.setString(2, nik);
                ps.executeUpdate();
            } catch (Exception ex) {
                // ignore jika belum ada record pemesanan
            }

            JOptionPane.showMessageDialog(this, "Data tamu & pemesanan berhasil diupdate!");
            loadDataTamu();
            loadDataPemesanan();
            loadComboBoxKamar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update tamu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_Update_TamuActionPerformed

    private void btn_Delete_TamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Delete_TamuActionPerformed
        // TODO add your handling code here:
        String nik = tf_NIK.getText();

        if (nik.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Masukkan NIK untuk menghapus!");
            return;
        }

        try (java.sql.Connection c = getConnection()) {

            // 1️⃣ HAPUS dari kamar_terpesan (yang bergantung pada Tamu)
            String sqlPesan = "DELETE FROM kamar_terpesan WHERE nik=?";
            try (java.sql.PreparedStatement p1 = c.prepareStatement(sqlPesan)) {
                p1.setString(1, nik);
                p1.executeUpdate();
            }

            // 2️⃣ HAPUS dari Tamu
            String sqlTamu = "DELETE FROM tamu WHERE nik=?";
            try (java.sql.PreparedStatement p2 = c.prepareStatement(sqlTamu)) {
                p2.setString(1, nik);
                p2.executeUpdate();
            }

            javax.swing.JOptionPane.showMessageDialog(this, "Tamu berhasil dihapus!");

            loadDataTamu();
            loadDataPemesanan();
            loadComboBoxKamar();

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal delete tamu: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_Delete_TamuActionPerformed

    private void btn_Cetak_TamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Cetak_TamuActionPerformed
        // TODO add your handling code here:
        String jrxmlPath = "src/pertemuan_15/report_tamu.jrxml";
        String jasperPath = "src/pertemuan_15/report_tamu.jasper";
        String csvOutput = "report_tamu.csv";

        String url = "jdbc:postgresql://localhost:5432/PBO_Pertemuan15";
        String user = "postgres";
        String pass = "ZayZiya03";

        try {
            // 1. Compile JRXML ke JASPER (hanya jika belum ada)
            net.sf.jasperreports.engine.JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            // 2. Buka koneksi JDBC
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, pass);

            // 3. Fill report (tanpa parameter)
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            net.sf.jasperreports.engine.JasperPrint jasperPrint
                    = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperPath, params, conn);

            // 4. Tampilkan JasperViewer
            net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);

            // 5. Export CSV
            net.sf.jasperreports.engine.export.JRCsvExporter exporter
                    = new net.sf.jasperreports.engine.export.JRCsvExporter();

            exporter.setExporterInput(new net.sf.jasperreports.export.SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new net.sf.jasperreports.export.SimpleWriterExporterOutput(csvOutput));

            net.sf.jasperreports.export.SimpleCsvExporterConfiguration config
                    = new net.sf.jasperreports.export.SimpleCsvExporterConfiguration();
            config.setFieldDelimiter(",");
            exporter.setConfiguration(config);

            exporter.exportReport();

            javax.swing.JOptionPane.showMessageDialog(this,
                    "Laporan ditampilkan dan CSV berhasil diekspor ke: " + csvOutput,
                    "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            conn.close();

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal cetak/ekspor: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btn_Cetak_TamuActionPerformed

    private void btn_Upload_TamuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Upload_TamuActionPerformed
        // TODO add your handling code here:
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }
        java.io.File file = chooser.getSelectedFile();

        final String csvSplitRegex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try (Connection conn = getConnection(); java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {

            conn.setAutoCommit(false);
            String line;
            boolean skipHeader = true;

            String sqlTamu = "INSERT INTO tamu(nik, nama_tamu, lama_menginap, id_kamar) VALUES (?,?,?,?)";
            String sqlPesan = "INSERT INTO kamar_terpesan(nik, id_kamar) VALUES (?,?)";
            String mapNoToId = "SELECT id_kamar FROM kamar WHERE nomor_kamar = ? LIMIT 1";

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] row = line.split(csvSplitRegex, -1);
                for (int i = 0; i < row.length; i++) {
                    String cell = row[i].trim();
                    if (cell.startsWith("\"") && cell.endsWith("\"") && cell.length() >= 2) {
                        cell = cell.substring(1, cell.length() - 1);
                    }
                    row[i] = cell;
                }
                if (row.length < 4) {
                    System.err.println("Skipping invalid CSV row: " + line);
                    continue;
                }

                String nik = row[0];
                String nama = row[1];
                String lama = row[2];
                String kamarCol = row[3];

                // jika kolom ke-4 bentuk nomor (numeric), mapping ke id_kamar
                String idKamar;
                if (kamarCol.matches("^K.*")) {
                    idKamar = kamarCol;
                } else {
                    try (PreparedStatement psMap = conn.prepareStatement(mapNoToId)) {
                        psMap.setString(1, kamarCol);
                        ResultSet rs = psMap.executeQuery();
                        if (rs.next()) {
                            idKamar = rs.getString(1);
                        } else {
                            System.err.println("Tidak menemukan id_kamar untuk nomor: " + kamarCol + " -> skip");
                            continue;
                        }
                    }
                }

                // skip jika NIK sudah ada
                try (PreparedStatement psCheck = conn.prepareStatement("SELECT COUNT(*) FROM tamu WHERE nik = ?")) {
                    psCheck.setString(1, nik);
                    ResultSet rsc = psCheck.executeQuery();
                    rsc.next();
                    if (rsc.getInt(1) > 0) {
                        System.out.println("NIK sudah ada, skip: " + nik);
                        continue;
                    }
                }

                // skip jika kamar sudah dipesan
                try (PreparedStatement psCheck2 = conn.prepareStatement("SELECT COUNT(*) FROM kamar_terpesan WHERE id_kamar = ?")) {
                    psCheck2.setString(1, idKamar);
                    ResultSet rsc2 = psCheck2.executeQuery();
                    rsc2.next();
                    if (rsc2.getInt(1) > 0) {
                        System.out.println("Kamar sudah dipesan, skip kamar: " + idKamar);
                        continue;
                    }
                }

                try (PreparedStatement p = conn.prepareStatement(sqlTamu)) {
                    p.setString(1, nik);
                    p.setString(2, nama);
                    p.setString(3, lama);
                    p.setString(4, idKamar);
                    p.executeUpdate();
                }

                try (PreparedStatement p2 = conn.prepareStatement(sqlPesan)) {
                    p2.setString(1, nik);
                    p2.setString(2, idKamar);
                    p2.executeUpdate();
                }
            }

            conn.commit();
            conn.setAutoCommit(true);

            JOptionPane.showMessageDialog(this, "Upload CSV Tamu berhasil!");
            loadDataTamu();
            loadDataPemesanan();
            loadComboBoxKamar();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal upload CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Upload_TamuActionPerformed

    private void cb_KamarDipesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_KamarDipesanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_KamarDipesanActionPerformed

    private void Table_KamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_KamarMouseClicked
        // TODO add your handling code here:
        int row = Table_Kamar.getSelectedRow();
        if (row >= 0) {
            Object v0 = Table_Kamar.getValueAt(row, 0);
            Object v1 = Table_Kamar.getValueAt(row, 1);
            Object v2 = Table_Kamar.getValueAt(row, 2);
            Object v3 = Table_Kamar.getValueAt(row, 3);

            tf_IDKamar_Kamar.setText(v0 == null ? "" : v0.toString());
            tf_NoKamar_Kamar.setText(v1 == null ? "" : v1.toString());
            tf_TipeKamar.setText(v2 == null ? "" : v2.toString());
            tf_Fasilitas_Kamar.setText(v3 == null ? "" : v3.toString());
        }
    }//GEN-LAST:event_Table_KamarMouseClicked

    private void Table_TamuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_TamuMouseClicked
        // TODO add your handling code here:
        int row = Table_Tamu.getSelectedRow();
        if (row >= 0) {
            Object v0 = Table_Tamu.getValueAt(row, 0); // nik
            Object v1 = Table_Tamu.getValueAt(row, 1); // nama_tamu
            Object v2 = Table_Tamu.getValueAt(row, 2); // lama_menginap
            Object v3 = Table_Tamu.getValueAt(row, 3); // id_kamar

            tf_NIK.setText(v0 == null ? "" : v0.toString());
            tf_NamaTamu.setText(v1 == null ? "" : v1.toString());
            tf_LamaMenginap.setText(v2 == null ? "" : v2.toString());
            // jika kamu punya combobox untuk kamar, pilih item yang sesuai:
            if (v3 != null) {
                cb_KamarDipesan.setSelectedItem(v3.toString());
            } else {
                cb_KamarDipesan.setSelectedIndex(-1);
            }
        }
    }//GEN-LAST:event_Table_TamuMouseClicked

    private void lbl_KeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_KeluarMouseClicked
        // TODO add your handling code here:
        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lbl_KeluarMouseClicked

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Kamar;
    private javax.swing.JTabbedPane Pemesanan;
    private javax.swing.JTable Table_Kamar;
    private javax.swing.JTable Table_Pemesanan;
    private javax.swing.JTable Table_Tamu;
    private javax.swing.JTabbedPane Tamu;
    private javax.swing.JTabbedPane TappedPane_Kamar_Tamu_Pemesanan;
    private javax.swing.JButton btn_Cetak_Kamar;
    private javax.swing.JButton btn_Cetak_Tamu;
    private javax.swing.JButton btn_Delete_Kamar;
    private javax.swing.JButton btn_Delete_Tamu;
    private javax.swing.JButton btn_Insert_Kamar;
    private javax.swing.JButton btn_Insert_Tamu;
    private javax.swing.JButton btn_Update_Kamar;
    private javax.swing.JButton btn_Update_Tamu;
    private javax.swing.JButton btn_Upload_Kamar;
    private javax.swing.JButton btn_Upload_Tamu;
    private javax.swing.JComboBox<String> cb_KamarDipesan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private java.awt.Label lbl_Keluar;
    private javax.swing.JTextField tf_Fasilitas_Kamar;
    private javax.swing.JTextField tf_IDKamar_Kamar;
    private javax.swing.JTextField tf_LamaMenginap;
    private javax.swing.JTextField tf_NIK;
    private javax.swing.JTextField tf_NamaTamu;
    private javax.swing.JTextField tf_NoKamar_Kamar;
    private javax.swing.JTextField tf_TipeKamar;
    // End of variables declaration//GEN-END:variables
}
