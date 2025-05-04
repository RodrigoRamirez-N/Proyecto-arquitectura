public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new view.Login_Form().setVisible(true);
        });
    }
}
// This is the main class of the application. It initializes the GUI by creating an instance of the Login_Form class and setting it visible.