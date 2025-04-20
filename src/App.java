import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class App {

    private static JPanel createHorizontalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private static JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    public static void main(String[] args) {
        Color maroon = new Color(128, 0, 0);
        Color white = Color.WHITE;


        JFrame frame = new JFrame("Voting Form");
        frame.setBackground(Color.WHITE);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = createVerticalPanel();


        // TITLE
        JPanel titlePanel = createHorizontalPanel();
        JLabel titleLabel = new JLabel("Voting Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(maroon);
        titlePanel.add(titleLabel);
        panel.add(titlePanel);

        // INPUTTING INFORMATION
        JPanel inputPanel = createHorizontalPanel();

        Dimension textFieldSize = new Dimension(300, 30);

        // VOTER ID INPUT
        JPanel idPanel = createVerticalPanel();
        JLabel idLabel = new JLabel("Voter ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setForeground(maroon);

        JTextField idField = new JTextField("Enter your Voter ID");
        idField.setPreferredSize(textFieldSize);
        idField.setMaximumSize(textFieldSize);

        // REGISTRATION PIN INPUT
        JPanel pinPanel = createVerticalPanel();
        JLabel pinLabel = new JLabel("Registration PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pinLabel.setForeground(maroon);

        JTextField pinField = new JTextField("Enter your Registration PIN");
        pinField.setPreferredSize(textFieldSize);
        pinField.setMaximumSize(textFieldSize);

        // add components to id panel
        idPanel.add(idLabel);
        idPanel.add(idField);

        // add components to pin panel
        pinPanel.add(pinLabel);
        pinPanel.add(pinField);

        // add id and pin panels to input panel
        inputPanel.add(idPanel);
        inputPanel.add(pinPanel);

        // add input panel to main panel
        panel.add(inputPanel);

        JPanel buttonsPanel = createHorizontalPanel();
        JButton submitVoteButton = new JButton("Submit Vote");
        JButton startOverButton = new JButton("Start Over");

        submitVoteButton.setBackground(Color.red);
        submitVoteButton.setForeground(white);
        startOverButton.setBackground(white);
        startOverButton.setForeground(maroon);

        buttonsPanel.add(submitVoteButton);
        buttonsPanel.add(startOverButton);

        panel.add(buttonsPanel);

        frame.add(panel);
        frame.setVisible(true);

    }
}
