import javax.swing.*;

public class Test extends JFrame {
    public Test() {
        JButton button = new JButton("Click me");
        button.setFocusable(false); // Make the button not focusable
        add(button);

        setTitle("Focusable Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Test());
    }
}
