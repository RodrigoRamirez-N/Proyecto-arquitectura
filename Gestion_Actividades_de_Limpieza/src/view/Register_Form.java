package view;

import java.awt.Color;

import assets.ImagenRuta;
import controller.AuthController;

public class Register_Form extends javax.swing.JFrame {


    public Register_Form() {
        initComponents();
    }


    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        userNameTxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel4.setText("Registro");
        bg.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 200, -1));

        jLabel6.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel6.setText("Usuario");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));
        bg.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 370, 10));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 1, 18)); // NOI18N
        jLabel8.setText("Contraseña");
        bg.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        passwordTxt.setForeground(new java.awt.Color(204, 204, 204));
        passwordTxt.setText("********");
        passwordTxt.setBorder(null);
        passwordTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                passwordTxtMousePressed(evt);
            }
        });
        bg.add(passwordTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 370, -1));

        jSeparator3.setForeground(new java.awt.Color(102, 102, 102));
        bg.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 370, 10));

        userNameTxt.setFont(new java.awt.Font("Roboto Thin", 3, 12)); // NOI18N
        userNameTxt.setForeground(new java.awt.Color(204, 204, 204));
        userNameTxt.setText("Ingrese su nombre de usuario");
        userNameTxt.setBorder(null);
        userNameTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userNameTxtMousePressed(evt);
            }
        });

        bg.add(userNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 370, 20));

        jPanel1.setBackground(new java.awt.Color(65, 71, 68));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(ImagenRuta.ESCUDO_COAH.getRuta())); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 290, 500));

        jButton1.setBackground(new java.awt.Color(188, 4, 51));
        jButton1.setFont(new java.awt.Font("Roboto Condensed", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Registrarse");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        bg.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

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
                                

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {                                      
        // javax.swing.JOptionPane.showMessageDialog(this, "Logica de insercion de admin con variables:\nNombre: " + userNameTxt.getText() + "\nPassword: " + new String(passwordTxt.getPassword()));
        //llamado al controler insertar rol admin por default
        AuthController authController = new AuthController();
        authController.crearAdministrador(userNameTxt.getText(), new String(passwordTxt.getPassword()));
        //llamado al controler insertar admin por default
        this.dispose();
        new Login_Form().setVisible(true);
    }                                     

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JPanel bg;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JTextField userNameTxt;
    // End of variables declaration                   
}
