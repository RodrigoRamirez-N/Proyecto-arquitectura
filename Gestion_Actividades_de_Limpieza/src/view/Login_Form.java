package view;


import java.awt.Color;

import javax.swing.JOptionPane;

import controller.AuthController;
import assets.ImagenRuta;

public class Login_Form extends javax.swing.JFrame {

    public Login_Form() {
        initComponents();
    }

                      
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        userNameTxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        passwordTxt = new javax.swing.JPasswordField();
        loginBtn = new javax.swing.JButton();
        registerLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel4.setText("INICIAR SESIÓN");
        bg.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 270, -1));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel6.setText("Usuario");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        userNameTxt.setFont(new java.awt.Font("Roboto Thin", 3, 12)); // NOI18N
        userNameTxt.setForeground(new java.awt.Color(204, 204, 204));
        userNameTxt.setText("Ingrese su nombre de usuario");
        userNameTxt.setBorder(null);
        userNameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userNameTxtMousePressed(evt);
            }
        });

        bg.add(userNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 370, 20));
        userNameTxt.getAccessibleContext().setAccessibleName("");

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));
        bg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 370, 10));

        jLabel7.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel7.setText("Contraseña");
        bg.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));
        bg.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 370, 10));

        passwordTxt.setForeground(new java.awt.Color(204, 204, 204));
        passwordTxt.setText("********");
        passwordTxt.setBorder(null);
        passwordTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                passwordTxtMousePressed(evt);
            }
        });
        bg.add(passwordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 370, -1));

        loginBtn.setBackground(new java.awt.Color(188, 4, 51));
        loginBtn.setFont(new java.awt.Font("Roboto Condensed", 1, 12)); // NOI18N
        loginBtn.setForeground(new java.awt.Color(255, 255, 255));
        loginBtn.setText("Ingresar");
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginBtnMouseClicked(evt);
            }
        });

        bg.add(loginBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 100, 30));

        registerLbl.setFont(new java.awt.Font("Roboto Medium", 2, 12)); // NOI18N
        registerLbl.setText("Registrate ahora");
        registerLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerLblMouseClicked(evt);
            }
        });
        bg.add(registerLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 90, -1));

        jPanel1.setBackground(new java.awt.Color(65, 71, 68));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("C:\\Users\\rodry\\.vscode\\Proyecto-arquitectura\\Gestion_Actividades_de_Limpieza\\src\\assets\\images\\escudo-de-coahuila-de-zaragoza.png"))); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(ImagenRuta.ESCUDO_COAH.getRuta())); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(147, 147, 147))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 290, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        
                                         

    private void userNameTxtMousePressed(java.awt.event.MouseEvent evt) {                                         
        if(userNameTxt.getText().equals("Ingrese su nombre de usuario")) {
            userNameTxt.setText("");
            userNameTxt.setForeground(Color.black);
        }
        if(String.valueOf(passwordTxt.getPassword()).isEmpty()) {
            passwordTxt.setText("********");
            passwordTxt.setForeground(Color.gray);
        }
    }                                        

    private void passwordTxtMousePressed(java.awt.event.MouseEvent evt) {                                         
        if(String.valueOf(passwordTxt.getPassword()).equals("********")) {
            passwordTxt.setText("");
            passwordTxt.setForeground(Color.black);
        }

        if(userNameTxt.getText().isEmpty()) {
            userNameTxt.setText("Ingrese su nombre de usuario");
            userNameTxt.setForeground(Color.gray);
        }
    }                                        

    private void loginBtnMouseClicked(java.awt.event.MouseEvent evt) {                                      
        System.out.println("Variblaes ingresadas: " + userNameTxt.getText() + " " + new String(passwordTxt.getPassword()));
        AuthController authController = new AuthController();
        boolean auth = authController.autenticarUsuario(userNameTxt.getText(), new String(passwordTxt.getPassword()));
        if(auth) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new Home_Menu().setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }                                     

    private void registerLblMouseClicked(java.awt.event.MouseEvent evt) {                                         
        this.dispose();
        new Register_Form().setVisible(true);
    }                                        

    // Variables declaration - do not modify                     
    private javax.swing.JPanel bg;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JLabel registerLbl;
    private javax.swing.JTextField userNameTxt;
    // End of variables declaration                   
}
