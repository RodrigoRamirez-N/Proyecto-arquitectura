package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import assets.ImagenRuta;
import controller.ActividadController;
import controller.ColoniaController;
import controller.CuadrillaController;
import controller.EmpleadoController;
import controller.JefeController;
import model.Actividad;
import model.Colonia;
import model.Cuadrilla;
import model.Empleado;
import model.Jefe;
import model.Usuario;
import util.SHA1;
import util.Session;

public class Home_Menu extends javax.swing.JFrame {


    public Home_Menu() {
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        initComponents();
        refreshEmpleadoTable();
        refreshJefeTable();
        refreshCuadrillaTable();
        refreshColoniaTable();
        refreshActividadTable();
        loadComboBoxCuadrillas();
        loadComboBoxColonias();
        loadListOfEmpleados();
        loadListOfJefes();
        setupEmpleadoTableSelection();
        setupJefeTableSelection();
        setupCuadrillaTableSelection();
        setupColoniaTableTableSelection();
        setupActividadTableSelection();
        configurarPlaceholderPasswordEmp();

        mainTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = mainTabbedPane.getSelectedIndex();
                String selectedTitle = mainTabbedPane.getTitleAt(selectedIndex);
                if("Empleados".equals(selectedTitle)) {
                    // Load the list of cuadrillas when the Empleados tab is selected
                    refreshEmpleadoTable();
                    loadComboBoxCuadrillas();
                }
                if("Cuadrillas".equals(selectedTitle)) {
                    // Load the list of bosses and employees when the Cuadrillas tab is selected
                    loadListOfEmpleados();
                    loadListOfJefes();
                    refreshCuadrillaTable();
                }
                if("Colonias".equals(selectedTitle)) {
                    refreshColoniaTable();
                }
                if("Actividades".equals(selectedTitle)) {
                    refreshActividadTable();
                    loadComboBoxColonias();
                    loadComboBoxCuadrillas();
                }
                if("Jefes".equals(selectedTitle)) {
                    refreshJefeTable();
                }
            }
        });
    }
    
    // do the same with the other tables
    // this will be usefull whenever the user desires to edit a row, because the input fields will be
    // filled with the row selected
    private void setupEmpleadoTableSelection() {
        empleadoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        empleadoTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = empleadoTable.getSelectedRow();
                if (selectedRow != -1){
                    String id = empleadoTable.getValueAt(selectedRow, 0).toString();
                    String nombre = empleadoTable.getValueAt(selectedRow, 1).toString();
                    String contrasena = empleadoTable.getValueAt(selectedRow, 2).toString();
                    String telefono = empleadoTable.getValueAt(selectedRow, 4).toString();
                    
                    txt_Id_Empleado.setText(id);
                    txt_Nombre_Empleado.setText(nombre);
                    txt_Password_Empleado.setText(contrasena);
                    txt_Tel_Empleado.setText(telefono);
                }
            }
        });
    }
    
    private void setupJefeTableSelection() {
        jefeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jefeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jefeTable.getSelectedRow();
                if (selectedRow != -1){
                    String id = jefeTable.getValueAt(selectedRow, 0).toString();
                    String nombre = jefeTable.getValueAt(selectedRow, 1).toString();
                    String contrasena = jefeTable.getValueAt(selectedRow, 2).toString();
                    String telefono = jefeTable.getValueAt(selectedRow, 4).toString();
                    
                    txt_Id_Jefe.setText(id);
                    txt_Nombre_Jefe.setText(nombre);
                    txt_Password_Jefe.setText(contrasena);
                    txt_Tel_Jefe.setText(telefono);
                }
            }
        });
    }
    
    private void setupCuadrillaTableSelection() {
        cuadrillaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cuadrillaTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int selectedRow = cuadrillaTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = cuadrillaTable.getValueAt(selectedRow, 0).toString();
                    String idJefe = cuadrillaTable.getValueAt(selectedRow, 1).toString();
                    String nombre = cuadrillaTable.getValueAt(selectedRow, 2).toString();
                    
                    txt_Id_Cuad.setText(id);
                    listOfJefes.setSelectedValue(idJefe, rootPaneCheckingEnabled);
                    txt_Nombre_Cuadrilla.setText(nombre);
                }
            }
        });
    }
    
    private void setupColoniaTableTableSelection() {
        ColoniaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ColoniaTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int selectedRow = ColoniaTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = ColoniaTable.getValueAt(selectedRow, 0).toString();
                    String nombre = ColoniaTable.getValueAt(selectedRow, 1).toString();
                    
                    txt_Id_Colonia.setText(id);
                    txt_Nombre_Colonia.setText(nombre);
                }
            }
        });
    }
    
    private void setupActividadTableSelection() {
        ActividadTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ActividadTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(!e.getValueIsAdjusting()){
                int selectedRow = ActividadTable.getSelectedRow();
                if(selectedRow != -1) {
                    try {
                        String idAct = getValueAtOrDefault(ActividadTable, selectedRow, 0);
                        String detalles = getValueAtOrDefault(ActividadTable, selectedRow, 1);
                        String tipo = getValueAtOrDefault(ActividadTable,selectedRow, 2);
                        String fecha = getValueAtOrDefault(ActividadTable, selectedRow, 3);
                        String estado = getValueAtOrDefault(ActividadTable, selectedRow, 4);
                        String idCuad = getValueAtOrDefault(ActividadTable, selectedRow, 5);
                        String idCol = getValueAtOrDefault(ActividadTable, selectedRow, 6);
                        String idUser = getValueAtOrDefault(ActividadTable, selectedRow, 7);
                        String evidenciaPath = getValueAtOrDefault(ActividadTable, selectedRow, 8);
                        
                        if (evidenciaPath != null && !evidenciaPath.isEmpty()) {
                            ImageIcon icon = new ImageIcon(new ImageIcon(evidenciaPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                        
                            lbl_Image.setIcon(icon);
                        } else {
                            lbl_Image.setText("Aún no hay evidencia");
                        }
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.sql.Date fechaParsed = new java.sql.Date(sdf.parse(fecha).getTime());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaParsed);
                        
                        txt_Id_Actividad.setText(idAct);
                        txt_Detalles.setText(detalles);
                        cbTipo.setSelectedItem(tipo);
                        dateChFecha.setSelectedDate(calendar);
                        cbEstado.setSelectedItem(estado);
                        txt_Id_Cuad.setText(idCuad);
                        txt_Id_Col.setText(idCol);
                        txt_Id_User.setText(idUser);
                        
                        
                    } catch (ParseException ex) {
                        Logger.getLogger(Home_Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
    
    }

    // Helper method to get value at a specific row and column, returning a default string if null
    private String getValueAtOrDefault(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return (value != null) ? value.toString() : "";  // Returns an empty string if the value is null
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        lblCerrarSession = new javax.swing.JLabel();
        lblTitulote = new javax.swing.JLabel();
        lblUserLoggedInfo = new javax.swing.JLabel();
        btnBuscarActsPorFecha = new javax.swing.JButton();
        cbListaCuadrillas_Actividades = new javax.swing.JComboBox<>();
        cbListaColonias_Actividades = new javax.swing.JComboBox<>();
        btnBuscarActsPorCuadrilla = new javax.swing.JButton();
        btnBuscarActsPorColonia = new javax.swing.JButton();
        footerPanel = new javax.swing.JPanel();
        lblFooterIMG = new javax.swing.JLabel();
        mainTabbedPane = new javax.swing.JTabbedPane();
        EmpleadoTabPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        inputPanelEmpleado = new javax.swing.JPanel();
        buttonsPanelEmpleado = new javax.swing.JPanel();
        btnAddEmpleado = new javax.swing.JButton();
        btnReadEmpleado = new javax.swing.JButton();
        btnEditEmpleado = new javax.swing.JButton();
        btnDeleteEmpleado = new javax.swing.JButton();
        textFieldsPanelEmpleado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_Id_Empleado = new javax.swing.JTextField();
        txt_Nombre_Empleado = new javax.swing.JTextField();
        txt_Tel_Empleado = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        btnEmpleadosPorCuadrilla = new javax.swing.JButton();
        txt_Password_Empleado = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        cbListaCuadrillas = new javax.swing.JComboBox<>();
        dataPanelEmpleado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        empleadoTable = new javax.swing.JTable();
        JefeTabPanel = new javax.swing.JPanel();
        JefeSplitPane = new javax.swing.JSplitPane();
        inputPanelJefe = new javax.swing.JPanel();
        buttonsPanelJefe = new javax.swing.JPanel();
        btnAddJefe = new javax.swing.JButton();
        btnReadJefe = new javax.swing.JButton();
        btnEditJefe = new javax.swing.JButton();
        btnDeleteJefe = new javax.swing.JButton();
        textFieldsPanelJefe = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_Id_Jefe = new javax.swing.JTextField();
        txt_Nombre_Jefe = new javax.swing.JTextField();
        txt_Tel_Jefe = new javax.swing.JTextField();
        btnBuscarJefe = new javax.swing.JButton();
        txt_Password_Jefe = new javax.swing.JPasswordField();
        dataPanelJefe = new javax.swing.JPanel();
        ScrollPane = new javax.swing.JScrollPane();
        jefeTable = new javax.swing.JTable();
        CuadrillaTabPanel = new javax.swing.JPanel();
        CuadrillaSplitPane = new javax.swing.JSplitPane();
        inputPanelCuadrilla = new javax.swing.JPanel();
        buttonsPanelCuadrilla = new javax.swing.JPanel();
        btnAddCuadrilla = new javax.swing.JButton();
        btnReadCuadrilla = new javax.swing.JButton();
        btnEditCuadrilla = new javax.swing.JButton();
        btnDeleteCuadrilla = new javax.swing.JButton();
        textFieldsPanelCuadrilla = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_Id_Cuadrilla = new javax.swing.JTextField();
        txt_Nombre_Cuadrilla = new javax.swing.JTextField();
        btnBuscarCuadrilla = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        listOfEmpleados = new javax.swing.JList<>();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        listOfJefes = new javax.swing.JList<>();
        btnAsignarEmpleado = new javax.swing.JButton();
        btnRemoverEmpleado = new javax.swing.JButton();
        btnRemoverJefe = new javax.swing.JButton();
        btnAsignarJefe = new javax.swing.JButton();
        dataPanelCuadrilla = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cuadrillaTable = new javax.swing.JTable();
        ColoniaTabPanel = new javax.swing.JPanel();
        ColoniaSplitPane = new javax.swing.JSplitPane();
        inputPanelColonia = new javax.swing.JPanel();
        buttonsPanelColonia = new javax.swing.JPanel();
        btnAddColonia = new javax.swing.JButton();
        btnReadColonia = new javax.swing.JButton();
        btnEditColonia = new javax.swing.JButton();
        btnDeleteColonia = new javax.swing.JButton();
        textFieldsPanelColonia = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_Id_Colonia = new javax.swing.JTextField();
        txt_Nombre_Colonia = new javax.swing.JTextField();
        btnBuscarColonia = new javax.swing.JButton();
        cbTipoActFiltro = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        btnFiltro_ColoniasPorTipoAct = new javax.swing.JButton();
        dataPanelColonia = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ColoniaTable = new javax.swing.JTable();
        ActividadTabPanel = new javax.swing.JPanel();
        jSplitPane5 = new javax.swing.JSplitPane();
        inputPanelActividad = new javax.swing.JPanel();
        buttonsPanelActividad = new javax.swing.JPanel();
        btnAddActividad = new javax.swing.JButton();
        btnReadActividad = new javax.swing.JButton();
        btnEditActividad = new javax.swing.JButton();
        btnDeleteActividad = new javax.swing.JButton();
        textFieldsPanelActividad = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_Id_Actividad = new javax.swing.JTextField();
        btnBuscarActividad = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Detalles = new javax.swing.JTextArea();
        cbTipo = new javax.swing.JComboBox<>();
        dateChFecha = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnUploadFile = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_Id_Cuad = new javax.swing.JTextField();
        txt_Id_Col = new javax.swing.JTextField();
        txt_Id_User = new javax.swing.JTextField();
        lbl_Image = new javax.swing.JLabel();
        dataPanelActividad = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ActividadTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(326, 300));

        backgroundPanel.setBackground(new java.awt.Color(165, 127, 44));
        backgroundPanel.setLayout(new java.awt.BorderLayout());

        titlePanel.setBackground(new java.awt.Color(97, 18, 50));
        titlePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        titlePanel.setPreferredSize(new java.awt.Dimension(988, 50));

        lblCerrarSession.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblCerrarSession.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrarSession.setText("Cerrar sesión");
        lblCerrarSession.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCerrarSession.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Session.getInstance().cerrarSesion();
                Home_Menu.this.setVisible(false);
                Home_Menu.this.dispose();
                Login_Form loginForm = new Login_Form();
                loginForm.setVisible(true);
            }
        });

        lblUserLoggedInfo.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblUserLoggedInfo.setForeground(new java.awt.Color(255, 255, 255));
        lblUserLoggedInfo.setText("User: " + Session.getInstance().getUsuario().getNombre());

        lblTitulote.setFont(new java.awt.Font("Segoe UI", 0, 18));
        lblTitulote.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulote.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulote.setText("Sistema de Gestión de Actividades de Limpieza del municipio de Saltillo");
        lblTitulote.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);


        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulote, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCerrarSession, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserLoggedInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(lblUserLoggedInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCerrarSession)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        

        backgroundPanel.add(titlePanel, java.awt.BorderLayout.NORTH);

        footerPanel.setPreferredSize(new java.awt.Dimension(988, 50));

        lblFooterIMG.setIcon(new ImageIcon(ImagenRuta.FOOTER_IMAGE.getRuta())); // NOI18N

        javax.swing.GroupLayout footerPanelLayout = new javax.swing.GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFooterIMG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, footerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFooterIMG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        backgroundPanel.add(footerPanel, java.awt.BorderLayout.SOUTH);

        EmpleadoTabPanel.setBackground(titlePanel.getBackground());
        EmpleadoTabPanel.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setResizeWeight(0.3);

        inputPanelEmpleado.setBackground(new java.awt.Color(204, 255, 204));
        inputPanelEmpleado.setLayout(new java.awt.BorderLayout());

        buttonsPanelEmpleado.setBackground(new java.awt.Color(255, 255, 255));

        btnAddEmpleado.setIcon(new ImageIcon(ImagenRuta.ADD_ICON.getRuta())); // NOI18N
        btnAddEmpleado.setToolTipText("Agregar nuevo registro");
        btnAddEmpleado.setBorderPainted(false);
        btnAddEmpleado.setContentAreaFilled(false);
        btnAddEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddEmpleado.setFocusPainted(false);
        btnAddEmpleado.setMaximumSize(new java.awt.Dimension(64, 64));
        buttonsPanelEmpleado.add(btnAddEmpleado);
        btnAddEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddEmpleadoMouseClicked(evt);
            }
        });

        btnReadEmpleado.setIcon(new ImageIcon(ImagenRuta.READ_ICON.getRuta())); // NOI18N
        btnReadEmpleado.setToolTipText("Ver todos los registros");
        btnReadEmpleado.setBorderPainted(false);
        btnReadEmpleado.setContentAreaFilled(false);
        btnReadEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadEmpleado.setFocusPainted(false);
        buttonsPanelEmpleado.add(btnReadEmpleado);
        btnReadEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReadEmpleadoMouseClicked(evt);
            }
        });

        btnEditEmpleado.setIcon(new ImageIcon(ImagenRuta.EDIT_ICON.getRuta())); // NOI18N
        btnEditEmpleado.setToolTipText("Edita el registro actual");
        btnEditEmpleado.setBorderPainted(false);
        btnEditEmpleado.setContentAreaFilled(false);
        btnEditEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditEmpleado.setFocusPainted(false);
        buttonsPanelEmpleado.add(btnEditEmpleado);
        btnEditEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditEmpleadoMouseClicked(evt);
            }
        });

        btnDeleteEmpleado.setIcon(new ImageIcon(ImagenRuta.REMOVE_ICON.getRuta())); // NOI18N
        btnDeleteEmpleado.setToolTipText("Eliminar registro");
        btnDeleteEmpleado.setBorderPainted(false);
        btnDeleteEmpleado.setContentAreaFilled(false);
        btnDeleteEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteEmpleado.setFocusPainted(false);
        buttonsPanelEmpleado.add(btnDeleteEmpleado);
        btnDeleteEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteEmpleadoMouseClicked(evt);
            }
        });

        inputPanelEmpleado.add(buttonsPanelEmpleado, java.awt.BorderLayout.SOUTH);

        textFieldsPanelEmpleado.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Id");

        jLabel2.setText("Nombre");

        jLabel3.setText("Contraseña");

        jLabel4.setText("Telefóno");

        txt_Id_Empleado.setText("Ingresa el número de empleado");
        txt_Id_Empleado.setToolTipText("Este campo es únicamente para búsqueda y borrar");
        txt_Id_Empleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_EmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_EmpleadoMouseExited(evt);
            }
        });

        txt_Nombre_Empleado.setText("Ingresa el nombre del empleado");
        txt_Nombre_Empleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Nombre_EmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Nombre_EmpleadoMouseExited(evt);
            }
        });

        txt_Tel_Empleado.setText("Ingresa el número telefónico del empleado");
        txt_Tel_Empleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Tel_EmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Tel_EmpleadoMouseExited(evt);
            }
        });

        btnBuscarEmpleado.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarEmpleado.setToolTipText("Buscar");
        btnBuscarEmpleado.setBorderPainted(false);
        btnBuscarEmpleado.setContentAreaFilled(false);
        btnBuscarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarEmpleado.setFocusPainted(false);
        btnBuscarEmpleado.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarEmpleadoMouseClicked(evt);
            }
        });

        txt_Password_Empleado.setText("Ingresa la contraseña");
        txt_Password_Empleado.setToolTipText("");

        jLabel12.setText("Cuadrillas");

        btnEmpleadosPorCuadrilla.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnEmpleadosPorCuadrilla.setToolTipText("Buscar");
        btnEmpleadosPorCuadrilla.setBorderPainted(false);
        btnEmpleadosPorCuadrilla.setContentAreaFilled(false);
        btnEmpleadosPorCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmpleadosPorCuadrilla.setFocusPainted(false);
        btnEmpleadosPorCuadrilla.setPreferredSize(new java.awt.Dimension(75, 22));
        btnEmpleadosPorCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmpleadosPorCuadrillaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout textFieldsPanelEmpleadoLayout = new javax.swing.GroupLayout(textFieldsPanelEmpleado);
        textFieldsPanelEmpleado.setLayout(textFieldsPanelEmpleadoLayout);
        textFieldsPanelEmpleadoLayout.setHorizontalGroup(
            textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelEmpleadoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelEmpleadoLayout.createSequentialGroup()
                        .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Nombre_Empleado)
                            .addComponent(txt_Id_Empleado)
                            .addComponent(txt_Tel_Empleado, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addComponent(txt_Password_Empleado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(textFieldsPanelEmpleadoLayout.createSequentialGroup()
                        .addComponent(cbListaCuadrillas, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmpleadosPorCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        textFieldsPanelEmpleadoLayout.setVerticalGroup(
            textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelEmpleadoLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Id_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(24, 24, 24)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Nombre_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_Password_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_Tel_Empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cbListaCuadrillas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEmpleadosPorCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(324, Short.MAX_VALUE))
        );

        inputPanelEmpleado.add(textFieldsPanelEmpleado, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(inputPanelEmpleado);

        dataPanelEmpleado.setBackground(new java.awt.Color(165, 127, 44));
        dataPanelEmpleado.setLayout(new java.awt.BorderLayout());

        empleadoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Contraseña", "Rol", "Teléfono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false,
                false,
                false,
                false,
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        empleadoTable.setColumnSelectionAllowed(true);
        empleadoTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(empleadoTable);
        empleadoTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        dataPanelEmpleado.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(dataPanelEmpleado);

        EmpleadoTabPanel.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab("Empleados", EmpleadoTabPanel);

        JefeTabPanel.setBackground(titlePanel.getBackground());
        JefeTabPanel.setLayout(new java.awt.BorderLayout());

        JefeSplitPane.setDividerLocation(500);
        JefeSplitPane.setResizeWeight(0.3);

        inputPanelJefe.setBackground(new java.awt.Color(204, 255, 204));
        inputPanelJefe.setLayout(new java.awt.BorderLayout());

        buttonsPanelJefe.setBackground(new java.awt.Color(255, 255, 255));

        btnAddJefe.setIcon(new ImageIcon(ImagenRuta.ADD_ICON.getRuta())); // NOI18N
        btnAddJefe.setToolTipText("Agregar nuevo registro");
        btnAddJefe.setBorderPainted(false);
        btnAddJefe.setContentAreaFilled(false);
        btnAddJefe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddJefe.setFocusPainted(false);
        btnAddJefe.setMaximumSize(new java.awt.Dimension(64, 64));
        buttonsPanelJefe.add(btnAddJefe);
        btnAddJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddJefeMouseClicked(evt);
            }
        });

        btnReadJefe.setIcon(new ImageIcon(ImagenRuta.READ_ICON.getRuta())); // NOI18N
        btnReadJefe.setToolTipText("Ver todos los registros");
        btnReadJefe.setBorderPainted(false);
        btnReadJefe.setContentAreaFilled(false);
        btnReadJefe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadJefe.setFocusPainted(false);
        buttonsPanelJefe.add(btnReadJefe);
        btnReadJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReadJefeMouseClicked(evt);
            }
        });

        btnEditJefe.setIcon(new ImageIcon(ImagenRuta.EDIT_ICON.getRuta())); // NOI18N
        btnEditJefe.setToolTipText("Edita el registro actual");
        btnEditJefe.setBorderPainted(false);
        btnEditJefe.setContentAreaFilled(false);
        btnEditJefe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditJefe.setFocusPainted(false);
        buttonsPanelJefe.add(btnEditJefe);
        btnEditJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditJefeMouseClicked(evt);
            }
        });

        btnDeleteJefe.setIcon(new ImageIcon(ImagenRuta.REMOVE_ICON.getRuta())); // NOI18N
        btnDeleteJefe.setToolTipText("Eliminar registro");
        btnDeleteJefe.setBorderPainted(false);
        btnDeleteJefe.setContentAreaFilled(false);
        btnDeleteJefe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteJefe.setFocusPainted(false);
        buttonsPanelJefe.add(btnDeleteJefe);
        btnDeleteJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteJefeMouseClicked(evt);
            }
        });

        inputPanelJefe.add(buttonsPanelJefe, java.awt.BorderLayout.SOUTH);

        textFieldsPanelJefe.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Id");

        jLabel6.setText("Nombre");

        jLabel7.setText("Contraseña");

        jLabel8.setText("Telefóno");

        txt_Id_Jefe.setText("Ingresa el número del jefe");
        txt_Id_Jefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_JefeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_JefeMouseExited(evt);
            }
        });

        txt_Nombre_Jefe.setText("Ingresa el nombre del jefe");
        txt_Nombre_Jefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Nombre_JefeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Nombre_JefeMouseExited(evt);
            }
        });

        txt_Tel_Jefe.setText("Ingresa el número telefónico del jefe");
        txt_Tel_Jefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Tel_JefeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Tel_JefeMouseExited(evt);
            }
        });

        btnBuscarJefe.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarJefe.setToolTipText("Buscar");
        btnBuscarJefe.setBorderPainted(false);
        btnBuscarJefe.setContentAreaFilled(false);
        btnBuscarJefe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarJefe.setFocusPainted(false);
        btnBuscarJefe.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarJefeMouseClicked(evt);
            }
        });

        txt_Password_Jefe.setText("Ingresa la contraseña");
        txt_Password_Jefe.setToolTipText("");

        javax.swing.GroupLayout textFieldsPanelJefeLayout = new javax.swing.GroupLayout(textFieldsPanelJefe);
        textFieldsPanelJefe.setLayout(textFieldsPanelJefeLayout);
        textFieldsPanelJefeLayout.setHorizontalGroup(
            textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelJefeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Nombre_Jefe)
                    .addComponent(txt_Id_Jefe)
                    .addComponent(txt_Tel_Jefe, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(txt_Password_Jefe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarJefe, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        textFieldsPanelJefeLayout.setVerticalGroup(
            textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelJefeLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarJefe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Id_Jefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(24, 24, 24)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_Nombre_Jefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_Password_Jefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(textFieldsPanelJefeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_Tel_Jefe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(384, Short.MAX_VALUE))
        );

        inputPanelJefe.add(textFieldsPanelJefe, java.awt.BorderLayout.CENTER);

        JefeSplitPane.setLeftComponent(inputPanelJefe);

        dataPanelJefe.setBackground(new java.awt.Color(165, 127, 44));
        dataPanelJefe.setLayout(new java.awt.BorderLayout());

        jefeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Contraseña", "Rol", "Teléfono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jefeTable.getTableHeader().setReorderingAllowed(false);
        ScrollPane.setViewportView(jefeTable);
        jefeTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        dataPanelJefe.add(ScrollPane, java.awt.BorderLayout.CENTER);

        JefeSplitPane.setRightComponent(dataPanelJefe);

        JefeTabPanel.add(JefeSplitPane, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab("Jefes", JefeTabPanel);

        CuadrillaTabPanel.setBackground(titlePanel.getBackground());
        CuadrillaTabPanel.setLayout(new java.awt.BorderLayout());

        CuadrillaSplitPane.setDividerLocation(500);
        CuadrillaSplitPane.setResizeWeight(0.3);

        inputPanelCuadrilla.setBackground(new java.awt.Color(204, 255, 204));
        inputPanelCuadrilla.setLayout(new java.awt.BorderLayout());

        buttonsPanelCuadrilla.setBackground(new java.awt.Color(255, 255, 255));

        btnAddCuadrilla.setIcon(new ImageIcon(ImagenRuta.ADD_ICON.getRuta())); // NOI18N
        btnAddCuadrilla.setToolTipText("Agregar nuevo registro");
        btnAddCuadrilla.setBorderPainted(false);
        btnAddCuadrilla.setContentAreaFilled(false);
        btnAddCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCuadrilla.setFocusPainted(false);
        btnAddCuadrilla.setMaximumSize(new java.awt.Dimension(64, 64));
        buttonsPanelCuadrilla.add(btnAddCuadrilla);
        btnAddCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddCuadrillaMouseClicked(evt);
            }
        });

        btnReadCuadrilla.setIcon(new ImageIcon(ImagenRuta.READ_ICON.getRuta())); // NOI18N
        btnReadCuadrilla.setToolTipText("Ver todos los registros");
        btnReadCuadrilla.setBorderPainted(false);
        btnReadCuadrilla.setContentAreaFilled(false);
        btnReadCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadCuadrilla.setFocusPainted(false);
        buttonsPanelCuadrilla.add(btnReadCuadrilla);
        btnReadCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReadCuadrillaMouseClicked(evt);
            }
        });

        btnEditCuadrilla.setIcon(new ImageIcon(ImagenRuta.EDIT_ICON.getRuta())); // NOI18N
        btnEditCuadrilla.setToolTipText("Edita el registro actual");
        btnEditCuadrilla.setBorderPainted(false);
        btnEditCuadrilla.setContentAreaFilled(false);
        btnEditCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditCuadrilla.setFocusPainted(false);
        buttonsPanelCuadrilla.add(btnEditCuadrilla);
        btnEditCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditCuadrillaMouseClicked(evt);
            }
        });

        btnDeleteCuadrilla.setIcon(new ImageIcon(ImagenRuta.REMOVE_ICON.getRuta())); // NOI18N
        btnDeleteCuadrilla.setToolTipText("Eliminar registro");
        btnDeleteCuadrilla.setBorderPainted(false);
        btnDeleteCuadrilla.setContentAreaFilled(false);
        btnDeleteCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteCuadrilla.setFocusPainted(false);
        buttonsPanelCuadrilla.add(btnDeleteCuadrilla);
        btnDeleteCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteCuadrillaMouseClicked(evt);
            }
        });

        inputPanelCuadrilla.add(buttonsPanelCuadrilla, java.awt.BorderLayout.SOUTH);

        textFieldsPanelCuadrilla.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Id");

        jLabel10.setText("Nombre");

        txt_Id_Cuadrilla.setText("Ingresa el número de la cuadrilla");
        txt_Id_Cuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_CuadrillaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_CuadrillaMouseExited(evt);
            }
        });

        txt_Nombre_Cuadrilla.setText("Ingresa el nombre de la cuadrilla");
        txt_Nombre_Cuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Nombre_CuadrillaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Nombre_CuadrillaMouseExited(evt);
            }
        });

        btnBuscarCuadrilla.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarCuadrilla.setToolTipText("Buscar");
        btnBuscarCuadrilla.setBorderPainted(false);
        btnBuscarCuadrilla.setContentAreaFilled(false);
        btnBuscarCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarCuadrilla.setFocusPainted(false);
        btnBuscarCuadrilla.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarCuadrillaMouseClicked(evt);
            }
        });

        jLabel24.setText("Empleados");

        jScrollPane7.setViewportView(listOfEmpleados);

        jLabel25.setText("Jefes");

        listOfJefes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(listOfJefes);

        btnAsignarEmpleado.setText("Asignar");
        btnAsignarEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAsignarEmpleadoMouseClicked(evt);
            }
        });

        btnRemoverEmpleado.setText("Remover");
        btnRemoverEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemoverEmpleadoMouseClicked(evt);
            }
        });

        btnRemoverJefe.setText("Remover");
        btnRemoverJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRemoverJefeMouseClicked(evt);
            }
        });

        btnAsignarJefe.setText("Asignar");
        btnAsignarJefe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAsignarJefeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout textFieldsPanelCuadrillaLayout = new javax.swing.GroupLayout(textFieldsPanelCuadrilla);
        textFieldsPanelCuadrilla.setLayout(textFieldsPanelCuadrillaLayout);
        textFieldsPanelCuadrillaLayout.setHorizontalGroup(
            textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                                .addComponent(btnAsignarEmpleado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemoverEmpleado)))
                        .addGap(150, 150, 150)
                        .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                                .addComponent(btnAsignarJefe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemoverJefe))))
                    .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(261, 261, 261)
                        .addComponent(jLabel25))
                    .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Nombre_Cuadrilla, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addComponent(txt_Id_Cuadrilla))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        textFieldsPanelCuadrillaLayout.setVerticalGroup(
            textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelCuadrillaLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Id_Cuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addGap(24, 24, 24)
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_Nombre_Cuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsignarEmpleado)
                    .addComponent(btnRemoverEmpleado)
                    .addGroup(textFieldsPanelCuadrillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAsignarJefe)
                        .addComponent(btnRemoverJefe)))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        inputPanelCuadrilla.add(textFieldsPanelCuadrilla, java.awt.BorderLayout.CENTER);

        CuadrillaSplitPane.setLeftComponent(inputPanelCuadrilla);

        dataPanelCuadrilla.setBackground(new java.awt.Color(165, 127, 44));
        dataPanelCuadrilla.setLayout(new java.awt.BorderLayout());

        cuadrillaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id Cuadrilla",
                "Id Jefe",
                "Nombre de cuadrilla",
                "Fecha de creación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class,
                java.lang.Integer.class,
                java.lang.String.class,
                java.util.Date.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cuadrillaTable.setColumnSelectionAllowed(true);
        cuadrillaTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(cuadrillaTable);
        cuadrillaTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        dataPanelCuadrilla.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        CuadrillaSplitPane.setRightComponent(dataPanelCuadrilla);

        CuadrillaTabPanel.add(CuadrillaSplitPane, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab("Cuadrillas", CuadrillaTabPanel);

        ColoniaTabPanel.setBackground(titlePanel.getBackground());
        ColoniaTabPanel.setLayout(new java.awt.BorderLayout());

        ColoniaSplitPane.setDividerLocation(500);
        ColoniaSplitPane.setResizeWeight(0.3);

        inputPanelColonia.setBackground(new java.awt.Color(204, 255, 204));
        inputPanelColonia.setLayout(new java.awt.BorderLayout());

        buttonsPanelColonia.setBackground(new java.awt.Color(255, 255, 255));

        btnAddColonia.setIcon(new ImageIcon(ImagenRuta.ADD_ICON.getRuta())); // NOI18N
        btnAddColonia.setToolTipText("Agregar nuevo registro");
        btnAddColonia.setBorderPainted(false);
        btnAddColonia.setContentAreaFilled(false);
        btnAddColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddColonia.setFocusPainted(false);
        btnAddColonia.setMaximumSize(new java.awt.Dimension(64, 64));
        buttonsPanelColonia.add(btnAddColonia);
        btnAddColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddColoniaMouseClicked(evt);
            }
        });

        btnReadColonia.setIcon(new ImageIcon(ImagenRuta.READ_ICON.getRuta())); // NOI18N
        btnReadColonia.setToolTipText("Ver todos los registros");
        btnReadColonia.setBorderPainted(false);
        btnReadColonia.setContentAreaFilled(false);
        btnReadColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadColonia.setFocusPainted(false);
        buttonsPanelColonia.add(btnReadColonia);
        btnReadColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReadColoniaMouseClicked(evt);
            }
        });

        btnEditColonia.setIcon(new ImageIcon(ImagenRuta.EDIT_ICON.getRuta())); // NOI18N
        btnEditColonia.setToolTipText("Edita el registro actual");
        btnEditColonia.setBorderPainted(false);
        btnEditColonia.setContentAreaFilled(false);
        btnEditColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditColonia.setFocusPainted(false);
        buttonsPanelColonia.add(btnEditColonia);
        btnEditColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditColoniaMouseClicked(evt);
            }
        });

        btnDeleteColonia.setIcon(new ImageIcon(ImagenRuta.REMOVE_ICON.getRuta())); // NOI18N
        btnDeleteColonia.setToolTipText("Eliminar registro");
        btnDeleteColonia.setBorderPainted(false);
        btnDeleteColonia.setContentAreaFilled(false);
        btnDeleteColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteColonia.setFocusPainted(false);
        buttonsPanelColonia.add(btnDeleteColonia);
        btnDeleteColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteColoniaMouseClicked(evt);
            }
        });

        inputPanelColonia.add(buttonsPanelColonia, java.awt.BorderLayout.SOUTH);

        textFieldsPanelColonia.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setText("Id");

        jLabel14.setText("Nombre");

        txt_Id_Colonia.setText("Ingresa el número de la colonia");
        txt_Id_Colonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_ColoniaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_ColoniaMouseExited(evt);
            }
        });

        txt_Nombre_Colonia.setText("Ingresa el nombre de la colonia");
        txt_Nombre_Colonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Nombre_ColoniaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Nombre_ColoniaMouseExited(evt);
            }
        });

        btnBuscarColonia.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarColonia.setToolTipText("Buscar");
        btnBuscarColonia.setBorderPainted(false);
        btnBuscarColonia.setContentAreaFilled(false);
        btnBuscarColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarColonia.setFocusPainted(false);
        btnBuscarColonia.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarColoniaMouseClicked(evt);
            }
        });

        cbTipoActFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barrido manual", "Limpieza parques", "Deshierbe de banquetas", "Limpieza de calles", "Recoleccion de basura", "Limpieza de alcantarillas", "Limpieza de basureros publicos", "Limpieza general" }));
        cbTipoActFiltro.setSelectedIndex(7);

        jLabel23.setText("Tipo de actividad");

        btnFiltro_ColoniasPorTipoAct.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnFiltro_ColoniasPorTipoAct.setToolTipText("Buscar");
        btnFiltro_ColoniasPorTipoAct.setBorderPainted(false);
        btnFiltro_ColoniasPorTipoAct.setContentAreaFilled(false);
        btnFiltro_ColoniasPorTipoAct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltro_ColoniasPorTipoAct.setFocusPainted(false);
        btnFiltro_ColoniasPorTipoAct.setPreferredSize(new java.awt.Dimension(75, 22));
        btnFiltro_ColoniasPorTipoAct.addActionListener(e -> {
            System.out.println("btnFiltro_ColoniasPorTipoAct clicked");
            String tipoActividad = cbTipoActFiltro.getSelectedItem().toString().trim();
            System.out.println("Tipo de actividad: " + tipoActividad);
        
            ColoniaController coloniaController = new ColoniaController();
            List<Colonia> listColonias = coloniaController.getColoniasPorTipoActividad(tipoActividad);
            DefaultTableModel model = (DefaultTableModel) ColoniaTable.getModel();
            model.setRowCount(0);
            for (Colonia colonia : listColonias) {
                Object[] row = new Object[3];
                row[0] = colonia.getCveColonia();
                row[1] = colonia.getNombreColonia();
                model.addRow(row);
                System.out.println("Colonia: " + colonia.getNombreColonia() + " Tipo: " + tipoActividad);
            }
        });

        javax.swing.GroupLayout textFieldsPanelColoniaLayout = new javax.swing.GroupLayout(textFieldsPanelColonia);
        textFieldsPanelColonia.setLayout(textFieldsPanelColoniaLayout);
        textFieldsPanelColoniaLayout.setHorizontalGroup(
            textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelColoniaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Nombre_Colonia, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(txt_Id_Colonia)
                    .addComponent(cbTipoActFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro_ColoniasPorTipoAct, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(304, Short.MAX_VALUE))
        );
        textFieldsPanelColoniaLayout.setVerticalGroup(
            textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelColoniaLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Id_Colonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addGap(24, 24, 24)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_Nombre_Colonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelColoniaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTipoActFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addComponent(btnFiltro_ColoniasPorTipoAct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(431, Short.MAX_VALUE))
        );

        inputPanelColonia.add(textFieldsPanelColonia, java.awt.BorderLayout.CENTER);

        ColoniaSplitPane.setLeftComponent(inputPanelColonia);

        dataPanelColonia.setBackground(new java.awt.Color(165, 127, 44));
        dataPanelColonia.setLayout(new java.awt.BorderLayout());

        ColoniaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id Colonia",
                "Nombre colonia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class,
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false,
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ColoniaTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(ColoniaTable);
        ColoniaTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        dataPanelColonia.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        ColoniaSplitPane.setRightComponent(dataPanelColonia);

        ColoniaTabPanel.add(ColoniaSplitPane, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab("Colonias", ColoniaTabPanel);

        ActividadTabPanel.setBackground(titlePanel.getBackground());
        ActividadTabPanel.setLayout(new java.awt.BorderLayout());

        jSplitPane5.setDividerLocation(500);
        jSplitPane5.setResizeWeight(0.3);

        inputPanelActividad.setBackground(new java.awt.Color(204, 255, 204));
        inputPanelActividad.setLayout(new java.awt.BorderLayout());

        buttonsPanelActividad.setBackground(new java.awt.Color(255, 255, 255));

        btnAddActividad.setIcon(new ImageIcon(ImagenRuta.ADD_ICON.getRuta())); // NOI18N
        btnAddActividad.setToolTipText("Agregar nuevo registro");
        btnAddActividad.setBorderPainted(false);
        btnAddActividad.setContentAreaFilled(false);
        btnAddActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddActividad.setFocusPainted(false);
        btnAddActividad.setMaximumSize(new java.awt.Dimension(64, 64));
        buttonsPanelActividad.add(btnAddActividad);
        btnAddActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddActividadMouseClicked(evt);
            }
        });

        btnReadActividad.setIcon(new ImageIcon(ImagenRuta.READ_ICON.getRuta())); // NOI18N
        btnReadActividad.setToolTipText("Ver todos los registros");
        btnReadActividad.setBorderPainted(false);
        btnReadActividad.setContentAreaFilled(false);
        btnReadActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadActividad.setFocusPainted(false);
        buttonsPanelActividad.add(btnReadActividad);
        btnReadActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReadActividadMouseClicked(evt);
            }
        });

        btnEditActividad.setIcon(new ImageIcon(ImagenRuta.EDIT_ICON.getRuta())); // NOI18N
        btnEditActividad.setToolTipText("Edita el registro actual");
        btnEditActividad.setBorderPainted(false);
        btnEditActividad.setContentAreaFilled(false);
        btnEditActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditActividad.setFocusPainted(false);
        buttonsPanelActividad.add(btnEditActividad);
        btnEditActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditActividadMouseClicked(evt);
            }
        });

        btnDeleteActividad.setIcon(new ImageIcon(ImagenRuta.REMOVE_ICON.getRuta())); // NOI18N
        btnDeleteActividad.setToolTipText("Eliminar registro");
        btnDeleteActividad.setBorderPainted(false);
        btnDeleteActividad.setContentAreaFilled(false);
        btnDeleteActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteActividad.setFocusPainted(false);
        buttonsPanelActividad.add(btnDeleteActividad);
        btnDeleteActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteActividadMouseClicked(evt);
            }
        });

        inputPanelActividad.add(buttonsPanelActividad, java.awt.BorderLayout.SOUTH);

        textFieldsPanelActividad.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setText("Id");

        jLabel18.setText("Detalles");

        jLabel19.setText("Tipo");

        jLabel20.setText("Fecha");

        txt_Id_Actividad.setText("Ingresa el número de la actividad");
        txt_Id_Actividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_ActividadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_ActividadMouseExited(evt);
            }
        });

        btnBuscarActividad.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarActividad.setToolTipText("Buscar");
        btnBuscarActividad.setBorderPainted(false);
        btnBuscarActividad.setContentAreaFilled(false);
        btnBuscarActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarActividad.setFocusPainted(false);
        btnBuscarActividad.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarActividadMouseClicked(evt);
            }
        });

        txt_Detalles.setColumns(20);
        txt_Detalles.setLineWrap(true);
        txt_Detalles.setRows(5);
        txt_Detalles.setText("Ingresa los detalles de la actividad");
        txt_Detalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_DetallesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_DetallesMouseExited(evt);
            }
        });
        jScrollPane2.setViewportView(txt_Detalles);

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Barrido manual", "Limpieza parques", "Deshierbe de banquetas", "Limpieza de calles", "Recoleccion de basura", "Limpieza de alcantarillas", "Limpieza de basureros publicos", "Limpieza general" }));
        cbTipo.setSelectedIndex(7);

        jLabel11.setText("Estado");

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendiente", "En Proceso", "Finalizada" }));

        jLabel15.setText("Evidencia");

        btnUploadFile.setText("Subir archivo");
        btnUploadFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUploadFileMouseClicked(evt);
            }
        });

        
        jLabel16.setText("Cuadrillas");

        jLabel21.setText("Colonias");

        btnBuscarActsPorFecha.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarActsPorFecha.setToolTipText("Actividades por fecha");
        btnBuscarActsPorFecha.setBorderPainted(false);
        btnBuscarActsPorFecha.setContentAreaFilled(false);
        btnBuscarActsPorFecha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarActsPorFecha.setFocusPainted(false);
        btnBuscarActsPorFecha.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarActsPorFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarActsPorFechaMouseClicked(evt);
            }
        });

        btnBuscarActsPorCuadrilla.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarActsPorCuadrilla.setToolTipText("Actividades por cuadrilla");
        btnBuscarActsPorCuadrilla.setBorderPainted(false);
        btnBuscarActsPorCuadrilla.setContentAreaFilled(false);
        btnBuscarActsPorCuadrilla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarActsPorCuadrilla.setFocusPainted(false);
        btnBuscarActsPorCuadrilla.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarActsPorCuadrilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarActsPorCuadrillaMouseClicked(evt);
            }
        });

        btnBuscarActsPorColonia.setIcon(new ImageIcon(ImagenRuta.SEARCH_ICON.getRuta())); // NOI18N
        btnBuscarActsPorColonia.setToolTipText("Actividades por colonia");
        btnBuscarActsPorColonia.setBorderPainted(false);
        btnBuscarActsPorColonia.setContentAreaFilled(false);
        btnBuscarActsPorColonia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarActsPorColonia.setFocusPainted(false);
        btnBuscarActsPorColonia.setPreferredSize(new java.awt.Dimension(75, 22));
        btnBuscarActsPorColonia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarActsPorColoniaMouseClicked(evt);
            }
        });

        txt_Id_User.setText("Ingresa el id del usuario que registró");
        txt_Id_User.setToolTipText("Aqui puedo sacar de la session class el id del usuario que ha loggeado en la app y meterlo aca por default");
        txt_Id_User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_Id_UserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_Id_UserMouseExited(evt);
            }
        });

        javax.swing.GroupLayout textFieldsPanelActividadLayout = new javax.swing.GroupLayout(textFieldsPanelActividad);
        textFieldsPanelActividad.setLayout(textFieldsPanelActividadLayout);
        textFieldsPanelActividadLayout.setHorizontalGroup(
            textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addComponent(dateChFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarActsPorFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_Image, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUploadFile, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbEstado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_Id_Actividad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                            .addComponent(cbTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addComponent(cbListaCuadrillas_Actividades, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarActsPorCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addComponent(cbListaColonias_Actividades, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarActsPorColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        textFieldsPanelActividadLayout.setVerticalGroup(
            textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_Id_Actividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel18))
                    .addGroup(textFieldsPanelActividadLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(dateChFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarActsPorFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(cbListaCuadrillas_Actividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarActsPorCuadrilla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(cbListaColonias_Actividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarActsPorColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(textFieldsPanelActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(btnUploadFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Image, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        inputPanelActividad.add(textFieldsPanelActividad, java.awt.BorderLayout.CENTER);

        jSplitPane5.setLeftComponent(inputPanelActividad);

        dataPanelActividad.setBackground(new java.awt.Color(165, 127, 44));
        dataPanelActividad.setLayout(new java.awt.BorderLayout());

        ActividadTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Actividad",
                "Detalles",
                "Tipo Actividad",
                "Fecha",
                "Estado",
                "Cuadrilla Id",
                "Colonia Id",
                "Usuario Id",
                "Evidencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, 
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.Integer.class,
                java.lang.Integer.class,
                java.lang.Integer.class,
                java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ActividadTable.setColumnSelectionAllowed(true);
        ActividadTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(ActividadTable);
        ActividadTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ActividadTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
        ActividadTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), ActividadTable, lbl_Image));

        dataPanelActividad.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jSplitPane5.setRightComponent(dataPanelActividad);

        ActividadTabPanel.add(jSplitPane5, java.awt.BorderLayout.CENTER);

        mainTabbedPane.addTab("Actividades", ActividadTabPanel);

        backgroundPanel.add(mainTabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(backgroundPanel, java.awt.BorderLayout.CENTER);
    }

    private void txt_Nombre_EmpleadoMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Empleado.getText().equals(TXT_NOMBRE_PLACEHOLDER)){
            txt_Nombre_Empleado.setText("");
            txt_Nombre_Empleado.setForeground(inputColor);
        }
    }

    private void txt_Nombre_EmpleadoMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Empleado.getText().isEmpty()) {
            txt_Nombre_Empleado.setText(TXT_NOMBRE_PLACEHOLDER);
            txt_Nombre_Empleado.setForeground(placeholderColor);
        }
    }

    private void txt_Tel_EmpleadoMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Tel_Empleado.getText().equals(TELEPHONE_PLACEHOLDER)) {
            txt_Tel_Empleado.setText("");
            txt_Tel_Empleado.setForeground(inputColor);
        }
    }

    private void txt_Tel_EmpleadoMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Tel_Empleado.getText().isEmpty()) {
            txt_Tel_Empleado.setText(TELEPHONE_PLACEHOLDER);
            txt_Tel_Empleado.setForeground(placeholderColor);
        }
    }

    private void txt_Id_JefeMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Id_Jefe.getText().equals(TXT_ID_PLACEHOLDER2)){
            txt_Id_Jefe.setText("");
            txt_Id_Jefe.setForeground(inputColor);
        }
    }

    private void txt_Id_JefeMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_Jefe.getText().isEmpty()){
            txt_Id_Jefe.setText(TXT_ID_PLACEHOLDER2);
            txt_Id_Jefe.setForeground(placeholderColor);
        }
    }

    private void txt_Nombre_JefeMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Jefe.getText().equals(TXT_NOMBRE_PLACEHOLDER2)){
            txt_Nombre_Jefe.setText("");
            txt_Nombre_Jefe.setForeground(inputColor);
        }
    }

    private void txt_Nombre_JefeMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Jefe.getText().isEmpty()) {
            txt_Nombre_Jefe.setText(TXT_NOMBRE_PLACEHOLDER2);
            txt_Nombre_Jefe.setForeground(placeholderColor);
        }
    }

    private void txt_Tel_JefeMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Tel_Jefe.getText().equals(TELEPHONE_PLACEHOLDER2)) {
            txt_Tel_Jefe.setText("");
            txt_Tel_Jefe.setForeground(inputColor);
        }
    }

    private void txt_Tel_JefeMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Tel_Jefe.getText().isEmpty()) {
            txt_Tel_Jefe.setText(TELEPHONE_PLACEHOLDER2);
            txt_Tel_Jefe.setForeground(placeholderColor);
        }
    }

    private void txt_Id_CuadrillaMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Id_Cuadrilla.getText().equals(TXT_ID_PLACEHOLDER3)){
            txt_Id_Cuadrilla.setText("");
            txt_Id_Cuadrilla.setForeground(inputColor);
        }
    }

    private void txt_Id_CuadrillaMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_Cuadrilla.getText().isEmpty()) {
            txt_Id_Cuadrilla.setText(TXT_ID_PLACEHOLDER3);
            txt_Id_Cuadrilla.setForeground(placeholderColor);
        }
    }

    private void txt_Nombre_CuadrillaMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Cuadrilla.getText().equals(TXT_NOMBRE_PLACEHOLDER3)){
            txt_Nombre_Cuadrilla.setText("");
            txt_Nombre_Cuadrilla.setForeground(inputColor);
        }
    }

    private void txt_Nombre_CuadrillaMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Cuadrilla.getText().isEmpty()){
            txt_Nombre_Cuadrilla.setText(TXT_NOMBRE_PLACEHOLDER3);
            txt_Nombre_Cuadrilla.setForeground(placeholderColor);
        }
    }

    private void txt_Id_ColoniaMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Id_Colonia.getText().equals("Ingresa el número de la colonia")){
            txt_Id_Colonia.setText("");
            txt_Id_Colonia.setForeground(inputColor);
        }
    }

    private void txt_Id_ColoniaMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_Colonia.getText().isEmpty()){
            txt_Id_Colonia.setText("Ingresa el número de la colonia");
            txt_Id_Colonia.setForeground(placeholderColor);
        }
    }

    private void txt_Nombre_ColoniaMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Colonia.getText().equals("Ingresa el nombre de la colonia")){
            txt_Nombre_Colonia.setText("");
            txt_Nombre_Colonia.setForeground(inputColor);
        }
    }

    private void txt_Nombre_ColoniaMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Nombre_Colonia.getText().isEmpty()){
            txt_Nombre_Colonia.setText("Ingresa el nombre de la colonia");
            txt_Nombre_Colonia.setForeground(placeholderColor);
        }
    }

    private void txt_Id_ActividadMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Id_Actividad.getText().equals("Ingresa el número de la actividad")){
            txt_Id_Actividad.setText("");
            txt_Id_Actividad.setForeground(inputColor);
        }
    }

    private void txt_Id_ActividadMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_Actividad.getText().isEmpty()){
            txt_Id_Actividad.setText("Ingresa el número de la actividad");
            txt_Id_Actividad.setForeground(placeholderColor);
        }
    }

    private void txt_Id_EmpleadoMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_Empleado.getText().isEmpty()){
            txt_Id_Empleado.setText(TXT_ID_PLACEHOLDER);
            txt_Id_Empleado.setForeground(placeholderColor);
        }
    }

    private void txt_Id_EmpleadoMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Id_Empleado.getText().equals(TXT_ID_PLACEHOLDER)){
            txt_Id_Empleado.setText("");
            txt_Id_Empleado.setForeground(inputColor);
        }
    }

    private void btnUploadFileMouseClicked(java.awt.event.MouseEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        
        if(result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            //store the path on a class-level variable so later I can use it in the add button logic
            //nullable object on database
            uploadedFilePath = selectedFile.getAbsolutePath();
            //display the image on the label for visuualization
            ImageIcon icon = new ImageIcon(new ImageIcon(uploadedFilePath).getImage()
            .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lbl_Image.setIcon(icon);
        }
    }

    private void btnBuscarColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        //coloniacontroller leerColonia()
        //then refresh table
        ColoniaController coloniaController = new ColoniaController();
        Colonia col = coloniaController.obtenerColoniaPorId(Integer.parseInt(txt_Id_Colonia.getText()));
        DefaultTableModel model = (DefaultTableModel) ColoniaTable.getModel();
        model.setRowCount(0); // Clear existing rows
        Object[] row = new Object[2];
        row[0] = col.getCveColonia();
        row[1] = col.getNombreColonia();
        model.addRow(row);
    }

    private void txt_DetallesMouseEntered(java.awt.event.MouseEvent evt) {
        if(txt_Detalles.getText().equals("Ingresa los detalles de la actividad")){
            txt_Detalles.setText("");
            txt_Detalles.setForeground(inputColor);
        }
    }

    private void txt_DetallesMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Detalles.getText().isEmpty()){
            txt_Detalles.setText("Ingresa los detalles de la actividad");
            txt_Detalles.setForeground(placeholderColor);
        }
    }

    private void txt_Id_UserMouseEntered(java.awt.event.MouseEvent evt) {
        //rescatar el id del usuario en la sesión actual
        if(txt_Id_User.getText().equals("Ingresa el id del usuario que registró")){
            txt_Id_User.setText("");
            txt_Id_User.setForeground(inputColor);
        }
    }

    private void txt_Id_UserMouseExited(java.awt.event.MouseEvent evt) {
        if(txt_Id_User.getText().isEmpty()){
            txt_Id_User.setText("Ingresa el id del usuario que registró");
            txt_Id_User.setForeground(placeholderColor);
        }
    }

    private void btnAsignarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        //controller empleado.asignar
        //get selected item from the list
        
        if(txt_Id_Cuadrilla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID de la cuadrilla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<String> selectedEmpleados = listOfEmpleados.getSelectedValuesList();
        int selectedCuadrilla = Integer.parseInt(txt_Id_Cuadrilla.getText());
        EmpleadoController empleadoController = new EmpleadoController();
        
        for (String selectedEmpleado : selectedEmpleados) {
            int empleadoId = Integer.parseInt(empleadoMap.get(selectedEmpleado));
            empleadoController.asignarEmpleadoACuadrilla(empleadoId, selectedCuadrilla);
        }
    }
    
    private void btnRemoverEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        // controllerempleado.remover

        if(txt_Id_Cuadrilla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID de la cuadrilla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<String> selectedEmpleados = listOfEmpleados.getSelectedValuesList();
        int selectedCuadrilla = Integer.parseInt(txt_Id_Cuadrilla.getText());
        EmpleadoController empleadoController = new EmpleadoController();
        
        for (String selectedEmpleado : selectedEmpleados) {
            int empleadoId = Integer.parseInt(empleadoMap.get(selectedEmpleado));
            empleadoController.removerEmpleadoDeCuadrilla(empleadoId, selectedCuadrilla);
        }
    }
    
    private void btnAsignarJefeMouseClicked(java.awt.event.MouseEvent evt) {
        //controller jefe.asignar

        if(txt_Id_Cuadrilla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID de la cuadrilla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedJefe = listOfJefes.getSelectedValue();
        int selectedCuadrilla = Integer.parseInt(txt_Id_Cuadrilla.getText());
        JefeController jefeController = new JefeController();
        
        if (selectedJefe != null) {
            int jefeId = Integer.parseInt(jefeMap.get(selectedJefe));
            jefeController.asignarJefeACuadrilla(jefeId, selectedCuadrilla);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un jefe para asignarlo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void btnRemoverJefeMouseClicked(java.awt.event.MouseEvent evt) {
        // controller jefe.remover
        if(txt_Id_Cuadrilla.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID de la cuadrilla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String selectedJefe = listOfJefes.getSelectedValue();
        int selectedCuadrilla = Integer.parseInt(txt_Id_Cuadrilla.getText());
        JefeController jefeController = new JefeController();
        
        if (selectedJefe != null) {
            jefeController.removerJefeDeCuadrilla(selectedCuadrilla);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un jefe para removerlo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void refreshEmpleadoTable() {
        //empleadocontroller leerEmpleado()
        //then refresh table
        EmpleadoController empleadoController = new EmpleadoController();
        List<Empleado> listEmpleados = empleadoController.getAllEmpleados();
        DefaultTableModel model = (DefaultTableModel) empleadoTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Empleado empleado : listEmpleados) {
            Object[] row = new Object[5];
            row[0] = empleado.getId();
            row[1] = empleado.getNombre();
            row[2] = SHA1.encryptThisString(empleado.getPassword());
            row[3] = empleado.getRol();
            row[4] = empleado.getTelefono();
            model.addRow(row);
        }
    }
    
    private void refreshJefeTable() {
        //jefecontroller obtenerJefe
        //then refresh tablejefe
        JefeController jefeController = new JefeController();
        List<Jefe> listJefes = jefeController.obtenerTodosLosJefes();
        DefaultTableModel model = (DefaultTableModel) jefeTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Jefe jefe : listJefes) {
            Object[] row = new Object[5];
            row[0] = jefe.getId();
            row[1] = jefe.getNombre();
            row[2] = SHA1.encryptThisString(jefe.getPassword());
            row[3] = jefe.getRol();
            row[4] = jefe.getTelefono();
            model.addRow(row);
        }
    }
    
    private void refreshCuadrillaTable() {
        //cuadrillacontroller obtenerCuadrilla
        //then refresh tablecuadrilla
        CuadrillaController cuadrillaController = new CuadrillaController();
        List<Cuadrilla> listCuadrillas = cuadrillaController.obtenerTodasLasCuadrillas();
        DefaultTableModel model = (DefaultTableModel) cuadrillaTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Cuadrilla cuadrilla : listCuadrillas) {
            Object[] row = new Object[4];
            row[0] = cuadrilla.getIdCuadrilla();
            row[1] = cuadrilla.getIdJefeCuadrilla();
            row[2] = cuadrilla.getNombreCuadrilla();
            row[3] = cuadrilla.getFechaCreacionCuadrilla();
            model.addRow(row);
        }
    }

    private void refreshActividadTable() {
        //actividadcontroller obtenerActividad
        //then refresh tableactividad
        ActividadController actividadController = new ActividadController();
        List<Actividad> listActividades = actividadController.getAllActividades();
        DefaultTableModel model = (DefaultTableModel) ActividadTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Actividad actividad : listActividades) {
            Object[] row = new Object[9];
            row[0] = actividad.getActividad_id();
            row[1] = actividad.getDetalles();
            row[2] = actividad.getTipoActividad();
            row[3] = actividad.getFecha();
            row[4] = actividad.getEstado();
            row[5] = actividad.getCuadrilla_id();
            row[6] = actividad.getCve_colonia();
            row[7] = actividad.getUsuario_registro_id();
            row[8] = actividad.getImagenEvidencia();
            model.addRow(row);
        }
    }
    
    private void refreshColoniaTable() {
        //coloniacontroller obtenerColonia
        //then refresh tablecolonia
        ColoniaController coloniaController = new ColoniaController();
        List<Colonia> listColonias = coloniaController.obtenerTodasLasColonias();
        DefaultTableModel model = (DefaultTableModel) ColoniaTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Colonia colonia : listColonias) {
            Object[] row = new Object[3];
            row[0] = colonia.getCveColonia();
            row[1] = colonia.getNombreColonia();
            model.addRow(row);
        }
    }
    
    private void loadComboBoxCuadrillas(){
        
        String url = "jdbc:mysql://localhost:3306/gestionlimpieza?useSSL=false&useProcedureBodies=false";
        
        try (Connection cnx = DriverManager.getConnection(url, "root", "rootpsw")){
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT cuadrilla_id, NombreCuadrilla  FROM cuadrilla";

            Statement stmt = cnx.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            // Clear the existing items in the combo box
            cbListaCuadrillas.removeAllItems();
            cbListaCuadrillas_Actividades.removeAllItems();
            cuadrillaMap.clear();
            // Iterate through the result set and add items to the combo box
            while (resultSet.next()) {
                String cuadrillaId = resultSet.getString("cuadrilla_id");
                String nombreCuadrilla = resultSet.getString("NombreCuadrilla");
                cbListaCuadrillas.addItem(nombreCuadrilla); // Add the name to the combo box
                cbListaCuadrillas_Actividades.addItem(nombreCuadrilla); // Add the name to the second combo box
                cuadrillaMap.put(nombreCuadrilla, cuadrillaId); // Store the ID in the map
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Home_Menu.class.getName());
        }

    }
    
    private void btnBuscarActsPorFechaMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller leerActividad()
        //then refresh table
        ActividadController actividadController = new ActividadController();

        java.util.Date selectedDate = dateChFecha.getSelectedDate().getTime();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        List<Actividad> listActividades = actividadController.getActividadesByFecha(sqlDate);
        DefaultTableModel model = (DefaultTableModel) ActividadTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Actividad actividad : listActividades) {
            Object[] row = new Object[9];
            row[0] = actividad.getActividad_id();
            row[1] = actividad.getDetalles();
            row[2] = actividad.getTipoActividad();
            row[3] = actividad.getFecha();
            row[4] = actividad.getEstado();
            row[5] = actividad.getCuadrilla_id();
            row[6] = actividad.getCve_colonia();
            row[7] = actividad.getUsuario_registro_id();
            row[8] = actividad.getImagenEvidencia();
            model.addRow(row);
        }
    }

    private void btnBuscarActsPorCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller leerActividad()
        //then refresh table
        ActividadController actividadController = new ActividadController();
        String selectedCuadrilla = (String) cbListaCuadrillas_Actividades.getSelectedItem();
        int cuadrillaId = Integer.parseInt(cuadrillaMap.get(selectedCuadrilla));
        List<Actividad> listActividades = actividadController.getActividadesByCuadrilla(cuadrillaId);
        DefaultTableModel model = (DefaultTableModel) ActividadTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Actividad actividad : listActividades) {
            Object[] row = new Object[9];
            row[0] = actividad.getActividad_id();
            row[1] = actividad.getDetalles();
            row[2] = actividad.getTipoActividad();
            row[3] = actividad.getFecha();
            row[4] = actividad.getEstado();
            row[5] = actividad.getCuadrilla_id();
            row[6] = actividad.getCve_colonia();
            row[7] = actividad.getUsuario_registro_id();
            row[8] = actividad.getImagenEvidencia();
            model.addRow(row);
        }
    }

    private void btnBuscarActsPorColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller leerActividad()
        //then refresh table
        System.out.println("btnBuscarActsPorColoniaMouseClicked");
        ActividadController actividadController = new ActividadController();
        String selectedColonia = (String) cbListaColonias_Actividades.getSelectedItem();
        int coloniaId = Integer.parseInt(coloniaMap.get(selectedColonia));
        System.out.println("Colonia ID: " + coloniaId);
        System.out.println("Selected Colonia: " + selectedColonia);
        List<Actividad> listActividades = actividadController.getActividadesByColonia(coloniaId);
        DefaultTableModel model = (DefaultTableModel) ActividadTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Actividad actividad : listActividades) {
            Object[] row = new Object[9];
            row[0] = actividad.getActividad_id();
            row[1] = actividad.getDetalles();
            row[2] = actividad.getTipoActividad();
            row[3] = actividad.getFecha();
            row[4] = actividad.getEstado();
            row[5] = actividad.getCuadrilla_id();
            row[6] = actividad.getCve_colonia();
            row[7] = actividad.getUsuario_registro_id();
            row[8] = actividad.getImagenEvidencia();
            model.addRow(row);
        }
    }

    private void loadComboBoxColonias(){
        //coloniacontroller leerColonia()
        //then refresh table
        String url = "jdbc:mysql://localhost:3306/gestionlimpieza?useSSL=false&useProcedureBodies=false";
        
        try (Connection cnx = DriverManager.getConnection(url, "root", "rootpsw")){
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT cve_colonia, NombreColonia FROM colonia ORDER BY cve_colonia ASC";

            Statement stmt = cnx.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            // Clear the existing items in the combo box
            cbListaColonias_Actividades.removeAllItems();
            coloniaMap.clear();
            // Iterate through the result set and add items to the combo box
            while (resultSet.next()) {
                String coloniaId = resultSet.getString("cve_colonia");
                String nombreColonia = resultSet.getString("NombreColonia");
                String item = "[" + coloniaId + "] " + nombreColonia;
                cbListaColonias_Actividades.addItem(item);  // Add the formatted item to the combo box
                coloniaMap.put(item, coloniaId);  // Store the ID in the map
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Home_Menu.class.getName());
        }
    }
    
    private void loadListOfEmpleados(){
        //listOfEmpleados.add(resultSet.value)
        String url = "jdbc:mysql://localhost:3306/gestionlimpieza?useSSL=false&useProcedureBodies=false";
        
        try (Connection cnx = DriverManager.getConnection(url, "root", "rootpsw")){
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String sql = "SELECT u.Nombre, e.empleado_id FROM Usuario u INNER JOIN Empleado e ON u.usuario_id = e.empleado_id";

            Statement stmt = cnx.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            
            // Create a DefaultListModel to hold the items
            DefaultListModel<String> model = new DefaultListModel<>();
            
            // Clear the existing items in the Jlist
            listOfEmpleados.removeAll();
            empleadoMap.clear();
            // Iterate through the result set and add items to the Jlist
            while (resultSet.next()) {
                String empleadoId = resultSet.getString("empleado_id");
                String nombreEmpleado = resultSet.getString("Nombre");
                
                model.addElement(nombreEmpleado); // Add the name to the list model
                empleadoMap.put(nombreEmpleado, empleadoId);
            }
            
            listOfEmpleados.setModel(model); // Set the new model to the Jlist
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Home_Menu.class.getName());
        }
    }
    
    private void loadListOfJefes(){
    //listOfJefes.add(resultSet.value)
    String url = "jdbc:mysql://localhost:3306/gestionlimpieza?useSSL=false&useProcedureBodies=false";
    
    try (Connection cnx = DriverManager.getConnection(url, "root", "rootpsw")){
        Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "SELECT u.Nombre, j.jefe_cuadrilla_id FROM Usuario u INNER JOIN Jefe_Cuadrilla j ON u.usuario_id = j.jefe_cuadrilla_id";
            
            Statement stmt = cnx.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            // Create a DefaultListModel to hold the items
            DefaultListModel<String> model = new DefaultListModel<>();
            
            // Clear the existing items in the Jlist
            listOfJefes.removeAll();
            jefeMap.clear();
            // Iterate through the result set and add items to the Jlist
            while (resultSet.next()) {
                String jefeId = resultSet.getString("jefe_cuadrilla_id");
                String nombreJefe= resultSet.getString("Nombre");
                
                model.addElement(nombreJefe); // Add the name to the list model
                jefeMap.put(nombreJefe, jefeId); // Store the ID in the map
            }

            listOfJefes.setModel(model); // Set the new model to the Jlist
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Home_Menu.class.getName());
        }
    }
    
    private void configurarPlaceholderPasswordEmp(){
        txt_Password_Empleado.setText(PASSWORD_PLACEHOLDER);
        txt_Password_Empleado.setEchoChar((char)0);
        txt_Password_Empleado.setForeground(placeholderColor);
        
        txt_Password_Empleado.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e){
                if (String.valueOf(txt_Password_Empleado.getPassword()).equals(PASSWORD_PLACEHOLDER)) {
                    txt_Password_Empleado.setText("");
                    txt_Password_Empleado.setEchoChar('*');
                    txt_Password_Empleado.setForeground(inputColor);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e){
                if(txt_Password_Empleado.getPassword().length == 0) {
                    txt_Password_Empleado.setText(PASSWORD_PLACEHOLDER);
                    txt_Password_Empleado.setEchoChar((char)0);
                    txt_Password_Empleado.setForeground(placeholderColor);
                }
            }
        });
        
        txt_Password_Jefe.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if(String.valueOf(txt_Password_Jefe.getPassword()).equals(PASSWORD_PLACEHOLDER)){
                    txt_Password_Jefe.setText("");
                    txt_Password_Jefe.setEchoChar('*');
                    txt_Password_Jefe.setForeground(placeholderColor);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e){
                if(txt_Password_Jefe.getPassword().length == 0) {
                    txt_Password_Jefe.setText(PASSWORD_PLACEHOLDER);
                    txt_Password_Jefe.setEchoChar((char)0);
                    txt_Password_Jefe.setForeground(placeholderColor);
                }
            }
        });
        
    }
    
    public class ButtonRenderer extends JButton implements TableCellRenderer {
        
        public ButtonRenderer() {
            setText("Ver");
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    public class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private JTable table;
        private JLabel labelDestino; // el JLabel donde se mostrará la imagen
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox, JTable table, JLabel labelDestino) {
            super(checkBox);
            this.table = table;
            this.labelDestino = labelDestino;
            this.button = new JButton("Ver");
            
            // Acción del botón
            button.addActionListener((ActionEvent e) -> {
                Object valor = table.getValueAt(currentRow, 8);
                if (valor != null && !valor.toString().isEmpty() && !valor.toString().equals("Ver")) {
                    labelDestino.setText("");
                    String ruta = valor.toString();
                    System.out.println("Ruta de la imagen: " + ruta);
                    ImageIcon icon = new ImageIcon(new ImageIcon(ruta).getImage()
                    .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    labelDestino.setIcon(icon);
                } else {
                    // Limpia o muestra una advertencia
                    labelDestino.setIcon(null);
                    JOptionPane.showMessageDialog(button, "Aún no hay evidencia disponible.");
                    
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
            currentRow = row;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "Ver";
        }
    }

    private void btnBuscarEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
    //empleadocontroller leerEmpleado()
        //then refresh table
        EmpleadoController empleadoController = new EmpleadoController();
        Empleado emp = empleadoController.leerEmpleado(Integer.parseInt(txt_Id_Empleado.getText()));
        //how do i do to make only this row appear on the table?
        DefaultTableModel model = (DefaultTableModel) empleadoTable.getModel();
        model.setRowCount(0); // Clear existing rows
        Object[] row = new Object[5];
        row[0] = emp.getId();
        row[1] = emp.getNombre();
        row[2] = emp.getPassword();
        row[3] = emp.getRol();
        row[4] = emp.getTelefono();
        model.addRow(row);
    }

    private void btnAddEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        //empleadocontroller crearEmpleado
        //then refresh table
        EmpleadoController empleadoController = new EmpleadoController();
        empleadoController.crearEmpleado(
            txt_Nombre_Empleado.getText(),
            String.valueOf(txt_Password_Empleado.getPassword()),
            txt_Tel_Empleado.getText()
            );
        refreshEmpleadoTable();
    }

    private void btnReadEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        refreshEmpleadoTable();
        //ese metodo ya hace un select all y los muestra en la table
    }

    private void btnDeleteEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        //empleadocontroller eliminarEmpleado
        //then refresh table
        EmpleadoController empleadoController = new EmpleadoController();
        empleadoController.deleteEmpleado(Integer.parseInt(txt_Id_Empleado.getText()));
        refreshEmpleadoTable();
    }

    private void btnEditEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {
        //empleadocontroller editarEmpleado
        //then refresh table
        EmpleadoController empleadoController = new EmpleadoController();
        empleadoController.updateEmpleado(
            Integer.parseInt(txt_Id_Empleado.getText()),
            txt_Nombre_Empleado.getText(),
            String.valueOf(txt_Password_Empleado.getPassword()),
            "empleado",
            txt_Tel_Empleado.getText()
            );
        refreshEmpleadoTable();
    }
    
    private void btnBuscarJefeMouseClicked(java.awt.event.MouseEvent evt) {
        //jefecontroller obtenerJefe
        //then refresh tablejefe
        JefeController jefeController = new JefeController();
        Jefe jefe = jefeController.obtenerJefe(Integer.parseInt(txt_Id_Jefe.getText()));
        DefaultTableModel model = (DefaultTableModel) jefeTable.getModel();
        model.setRowCount(0); // Clear existing rows
        Object[] row = new Object[5];
        row[0] = jefe.getId();
        row[1] = jefe.getNombre();
        row[2] = jefe.getPassword();
        row[3] = jefe.getRol();
        row[4] = jefe.getTelefono();
        model.addRow(row);
    }
    
    private void btnAddJefeMouseClicked(java.awt.event.MouseEvent evt) {
        //jefecontroller crearJefe
        //then refresh table
        JefeController jefeController = new JefeController();
        jefeController.crearJefe(
            txt_Nombre_Jefe.getText(),
            String.valueOf(txt_Password_Jefe.getPassword()),
            txt_Tel_Jefe.getText()
            );
        refreshJefeTable();
    }

    private void btnReadJefeMouseClicked(java.awt.event.MouseEvent evt) {
        refreshJefeTable();
        //ese metodo ya hace un select all y los muestra en la table
    }

    private void btnDeleteJefeMouseClicked(java.awt.event.MouseEvent evt) {
        //jefecontroller eliminarJefe
        //then refresh table
        JefeController jefeController = new JefeController();
        jefeController.eliminarJefe(Integer.parseInt(txt_Id_Jefe.getText()));
        refreshJefeTable();
    }

    private void btnEditJefeMouseClicked(java.awt.event.MouseEvent evt) {
        //jefecontroller editarJefe
        //then refresh table
        JefeController jefeController = new JefeController();
        jefeController.actualizarJefe(
            Integer.parseInt(txt_Id_Jefe.getText()),
            txt_Nombre_Jefe.getText(),
            String.valueOf(txt_Password_Jefe.getPassword()),
            txt_Tel_Jefe.getText()
            );
        refreshJefeTable();
    }

    private void btnAddCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //cuadrillacontroller crearCuadrilla
        //then refresh table
        CuadrillaController cuadrillaController = new CuadrillaController();

        java.util.Date selectedDate = dateChFecha.getSelectedDate().getTime();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
        // Convert java.util.Date to java.sql.Date

        cuadrillaController.crearCuadrilla(
            txt_Nombre_Cuadrilla.getText(),
            sqlDate
        );
        refreshCuadrillaTable();
    }

    private void btnReadCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        refreshCuadrillaTable();
        //ese metodo ya hace un select all y los muestra en la table
    }

    private void btnDeleteCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //cuadrillacontroller eliminarCuadrilla
        //then refresh table
        CuadrillaController cuadrillaController = new CuadrillaController();
        cuadrillaController.eliminarCuadrilla(Integer.parseInt(txt_Id_Cuadrilla.getText()));
        refreshCuadrillaTable();
    }

    private void btnEditCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //cuadrillacontroller editarCuadrilla
        //then refresh table
        CuadrillaController cuadrillaController = new CuadrillaController();
        cuadrillaController.actualizarCuadrilla(
            Integer.parseInt(txt_Id_Cuadrilla.getText()),
            txt_Nombre_Cuadrilla.getText(),
            Integer.parseInt(txt_Id_Jefe.getText())
            );
        refreshCuadrillaTable();
    }

    private void btnBuscarCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //cuadrillacontroller leerCuadrilla
        //then refresh table
        CuadrillaController cuadrillaController = new CuadrillaController();
        Cuadrilla cuadrilla = cuadrillaController.obtenerCuadrillaPorId(Integer.parseInt(txt_Id_Cuadrilla.getText()));
        DefaultTableModel model = (DefaultTableModel) cuadrillaTable.getModel();
        model.setRowCount(0); // Clear existing rows
        Object[] row = new Object[4];
        row[0] = cuadrilla.getIdCuadrilla();
        row[1] = cuadrilla.getIdJefeCuadrilla();
        row[2] = cuadrilla.getNombreCuadrilla();
        row[3] = cuadrilla.getFechaCreacionCuadrilla();
        model.addRow(row);
    }

    private void btnAddColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        //coloniacontroller crearColonia
        //then refresh table
        ColoniaController coloniaController = new ColoniaController();
        coloniaController.crearColonia(
            txt_Nombre_Colonia.getText()
            );
        refreshColoniaTable();
    }

    private void btnReadColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        refreshColoniaTable();
        //ese metodo ya hace un select all y los muestra en la table
    }

    private void btnDeleteColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        //coloniacontroller eliminarColonia
        //then refresh table
        ColoniaController coloniaController = new ColoniaController();
        coloniaController.eliminarColonia(Integer.parseInt(txt_Id_Colonia.getText()));
        refreshColoniaTable();
    }

    private void btnEditColoniaMouseClicked(java.awt.event.MouseEvent evt) {
        //coloniacontroller editarColonia
        //then refresh table
        ColoniaController coloniaController = new ColoniaController();
        coloniaController.actualizarColonia(
            Integer.parseInt(txt_Id_Colonia.getText()),
            txt_Nombre_Colonia.getText()
            );
        refreshColoniaTable();
    }

    private void btnEmpleadosPorCuadrillaMouseClicked(java.awt.event.MouseEvent evt) {
        //empleadocontroller filtrarEmpleadosPorCuadrilla
        //then refresh table
        int cuadrillaId = Integer.parseInt(cuadrillaMap.get(cbListaCuadrillas.getSelectedItem().toString()));
        EmpleadoController empleadoController = new EmpleadoController();
        //aqui si hay que obtener el id de la cuadrilla del cuadrillamap
        List<Empleado> listEmpleados = empleadoController.getEmpleadosByCuadrilla(cuadrillaId);
        DefaultTableModel model = (DefaultTableModel) empleadoTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (Empleado empleado : listEmpleados) {
            Object[] row = new Object[5];
            row[0] = empleado.getId();
            row[1] = empleado.getNombre();
            row[2] = empleado.getPassword();
            row[3] = empleado.getRol();
            row[4] = empleado.getTelefono();
            model.addRow(row);
        }
    }

    private void btnAddActividadMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller crearActividad
        //then refresh table
        ActividadController actividadController = new ActividadController();

        java.util.Date selectedDate = dateChFecha.getSelectedDate().getTime();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        actividadController.createActividad(
            txt_Detalles.getText(),
            cbTipo.getSelectedItem().toString(),
            sqlDate,
            uploadedFilePath,
            cbEstado.getSelectedItem().toString(),
            Integer.parseInt(cuadrillaMap.get(cbListaCuadrillas_Actividades.getSelectedItem().toString())),
            Integer.parseInt(coloniaMap.get(cbListaColonias_Actividades.getSelectedItem().toString())),
            usuarioActual.getId() //rescatar el id del usuario en la sesión actual
            );
        refreshActividadTable();

    }

    private void btnReadActividadMouseClicked(java.awt.event.MouseEvent evt) {
        refreshActividadTable();
        //ese metodo ya hace un select all y los muestra en la table
    }

    private void btnDeleteActividadMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller eliminarActividad
        //then refresh table
        ActividadController actividadController = new ActividadController();
        actividadController.deleteActividad(Integer.parseInt(txt_Id_Actividad.getText()));
        refreshActividadTable();
    }

    private void btnEditActividadMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller editarActividad
        //then refresh table
        ActividadController actividadController = new ActividadController();
        actividadController.updateActividad(
            Integer.parseInt(txt_Id_Actividad.getText()),
            txt_Detalles.getText(),
            cbTipo.getSelectedItem().toString(),
            (Date) dateChFecha.getSelectedDate().getTime(),
            uploadedFilePath,
            cbEstado.getSelectedItem().toString(),
            Integer.parseInt(cuadrillaMap.get(cbListaCuadrillas_Actividades.getSelectedItem().toString())),
            Integer.parseInt(coloniaMap.get(cbListaColonias_Actividades.getSelectedItem().toString())),
            usuarioActual.getId()
            );
        refreshActividadTable();
    }

    private void btnBuscarActividadMouseClicked(java.awt.event.MouseEvent evt) {
        //actividadcontroller leerActividad
        //then refresh table
        ActividadController actividadController = new ActividadController();
        Actividad act = actividadController.getActividadById(Integer.parseInt(txt_Id_Actividad.getText()));
        DefaultTableModel model = (DefaultTableModel) ActividadTable.getModel();
        model.setRowCount(0); // Clear existing rows
        Object[] row = new Object[9];
        row[0] = act.getActividad_id();
        row[1] = act.getDetalles();
        row[2] = act.getTipoActividad();
        row[3] = act.getFecha();
        row[4] = act.getEstado();
        row[5] = act.getCuadrilla_id();
        row[6] = act.getCve_colonia();
        row[7] = act.getUsuario_registro_id();
        row[8] = act.getImagenEvidencia();
        model.addRow(row);
    }

    // Variables declaration - do not modify
    
    public Usuario usuarioActual = Session.getInstance().getUsuario();
    private String uploadedFilePath = null;
    private final String TXT_ID_PLACEHOLDER = "Ingresa el número de empleado";
    private final String TXT_NOMBRE_PLACEHOLDER = "Ingresa el nombre del empleado";
    private final String PASSWORD_PLACEHOLDER = "Ingresa la contraseña";
    private final String TELEPHONE_PLACEHOLDER = "Ingresa el número telefónico del empleado";
    private final String TXT_ID_PLACEHOLDER2 = "Ingresa el número del jefe";
    private final String TXT_NOMBRE_PLACEHOLDER2 = "Ingresa el nombre del jefe";
    private final String TELEPHONE_PLACEHOLDER2 = "Ingresa el número telefónico del jefe";
    private final String TXT_ID_PLACEHOLDER3 = "Ingresa el número de la cuadrilla";
    private final String TXT_NOMBRE_PLACEHOLDER3 = "Ingresa el nombre de la cuadrilla";
    private final Color placeholderColor = Color.GRAY;
    private final Color inputColor = Color.BLACK;
    private Map<String, String> cuadrillaMap = new HashMap<>();
    private Map<String, String> jefeMap = new HashMap<>();
    private Map<String, String> empleadoMap = new HashMap<>();
    private Map<String, String> coloniaMap = new HashMap<>();
    private javax.swing.JPanel ActividadTabPanel;
    private javax.swing.JTable ActividadTable;
    private javax.swing.JSplitPane ColoniaSplitPane;
    private javax.swing.JPanel ColoniaTabPanel;
    private javax.swing.JTable ColoniaTable;
    private javax.swing.JSplitPane CuadrillaSplitPane;
    private javax.swing.JPanel CuadrillaTabPanel;
    private javax.swing.JPanel EmpleadoTabPanel;
    private javax.swing.JSplitPane JefeSplitPane;
    private javax.swing.JPanel JefeTabPanel;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton btnAddActividad;
    private javax.swing.JButton btnAddColonia;
    private javax.swing.JButton btnAddCuadrilla;
    private javax.swing.JButton btnAddEmpleado;
    private javax.swing.JButton btnAddJefe;
    private javax.swing.JButton btnAsignarEmpleado;
    private javax.swing.JButton btnAsignarJefe;
    private javax.swing.JButton btnBuscarActividad;
    private javax.swing.JButton btnBuscarColonia;
    private javax.swing.JButton btnBuscarCuadrilla;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnEmpleadosPorCuadrilla;
    private javax.swing.JButton btnBuscarJefe;
    private javax.swing.JButton btnDeleteActividad;
    private javax.swing.JButton btnDeleteColonia;
    private javax.swing.JButton btnDeleteCuadrilla;
    private javax.swing.JButton btnDeleteEmpleado;
    private javax.swing.JButton btnDeleteJefe;
    private javax.swing.JButton btnEditActividad;
    private javax.swing.JButton btnEditColonia;
    private javax.swing.JButton btnEditCuadrilla;
    private javax.swing.JButton btnEditEmpleado;
    private javax.swing.JButton btnEditJefe;
    private javax.swing.JButton btnFiltro_ColoniasPorTipoAct;
    private javax.swing.JButton btnReadActividad;
    private javax.swing.JButton btnReadColonia;
    private javax.swing.JButton btnReadCuadrilla;
    private javax.swing.JButton btnReadEmpleado;
    private javax.swing.JButton btnReadJefe;
    private javax.swing.JButton btnRemoverEmpleado;
    private javax.swing.JButton btnRemoverJefe;
    private javax.swing.JButton btnUploadFile;
    private javax.swing.JPanel buttonsPanelActividad;
    private javax.swing.JPanel buttonsPanelColonia;
    private javax.swing.JPanel buttonsPanelCuadrilla;
    private javax.swing.JPanel buttonsPanelEmpleado;
    private javax.swing.JPanel buttonsPanelJefe;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbListaCuadrillas;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JComboBox<String> cbTipoActFiltro;
    private javax.swing.JTable cuadrillaTable;
    private javax.swing.JPanel dataPanelActividad;
    private javax.swing.JPanel dataPanelColonia;
    private javax.swing.JPanel dataPanelCuadrilla;
    private javax.swing.JPanel dataPanelEmpleado;
    private javax.swing.JPanel dataPanelJefe;
    private datechooser.beans.DateChooserCombo dateChFecha;
    private javax.swing.JTable empleadoTable;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JPanel inputPanelActividad;
    private javax.swing.JPanel inputPanelColonia;
    private javax.swing.JPanel inputPanelCuadrilla;
    private javax.swing.JPanel inputPanelEmpleado;
    private javax.swing.JPanel inputPanelJefe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblUserLoggedInfo;
    private javax.swing.JLabel lblCerrarSession;
    private javax.swing.JLabel lblTitulote;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JTable jefeTable;
    private javax.swing.JLabel lblFooterIMG;
    private javax.swing.JLabel lbl_Image;
    private javax.swing.JList<String> listOfEmpleados;
    private javax.swing.JList<String> listOfJefes;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JPanel textFieldsPanelActividad;
    private javax.swing.JPanel textFieldsPanelColonia;
    private javax.swing.JPanel textFieldsPanelCuadrilla;
    private javax.swing.JPanel textFieldsPanelEmpleado;
    private javax.swing.JPanel textFieldsPanelJefe;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JTextArea txt_Detalles;
    private javax.swing.JTextField txt_Id_Actividad;
    private javax.swing.JTextField txt_Id_Col;
    private javax.swing.JTextField txt_Id_Colonia;
    private javax.swing.JTextField txt_Id_Cuad;
    private javax.swing.JTextField txt_Id_Cuadrilla;
    private javax.swing.JTextField txt_Id_Empleado;
    private javax.swing.JTextField txt_Id_Jefe;
    private javax.swing.JTextField txt_Id_User;
    private javax.swing.JTextField txt_Nombre_Colonia;
    private javax.swing.JTextField txt_Nombre_Cuadrilla;
    private javax.swing.JTextField txt_Nombre_Empleado;
    private javax.swing.JTextField txt_Nombre_Jefe;
    private javax.swing.JPasswordField txt_Password_Empleado;
    private javax.swing.JPasswordField txt_Password_Jefe;
    private javax.swing.JTextField txt_Tel_Empleado;
    private javax.swing.JTextField txt_Tel_Jefe;

    private javax.swing.JButton btnBuscarActsPorColonia;
    private javax.swing.JButton btnBuscarActsPorCuadrilla;
    private javax.swing.JButton btnBuscarActsPorFecha;
    private javax.swing.JComboBox<String> cbListaCuadrillas_Actividades;
    private javax.swing.JComboBox<String> cbListaColonias_Actividades;
}
