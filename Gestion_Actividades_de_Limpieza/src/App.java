public class App {

    public static void main(String[] args) {
        // Create and display the login form
        
        java.awt.EventQueue.invokeLater(() -> {
            new view.Login_Form().setVisible(true);
        });
        
    }

}