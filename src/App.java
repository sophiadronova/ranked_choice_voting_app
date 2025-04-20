import javax.swing.*;
import java.awt.Color;
import java.awt.Font;

public class App {

    public static void main(String[] args) {
        Color maroon = new Color(128, 0, 0);


        JFrame frame = new JFrame("Voting Form");
        frame.setBackground(Color.WHITE);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Voting Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(maroon);
        titlePanel.add(titleLabel);
        panel.add(titlePanel);

        frame.add(panel);
        frame.setVisible(true);

    }
}
