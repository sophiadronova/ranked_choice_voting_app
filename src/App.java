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
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        // TITLE
        JPanel titlePanel = createHorizontalPanel();
        JLabel titleLabel = new JLabel("Voting Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(maroon);
        titlePanel.add(titleLabel);
        
        panel.add(titlePanel);
        // panel.add(Box.createVerticalStrut(15));

        // INSTRUCTIONS
        JPanel instructionsPanel = createVerticalPanel();
        JTextArea instructions = new JTextArea("Please enter your Voter ID and registration PIN. These are found on the card that you were given by your local election agent.");
        instructions.setFont(new Font("Arial", Font.PLAIN, 12));
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setMaximumSize(new Dimension(600, 60));
        instructions.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        instructionsPanel.add(instructions);
        instructionsPanel.add(Box.createVerticalStrut(15));
        panel.add(instructionsPanel);


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

        // DROP DOWN PART
        String[] candidates = {
            "William Henry Harrison",
            "Taylor Swift",
            "Abraham Lincoln",
            "Mr. Bean",
            "George Washington"
        };

        String[] choices = {"1st Choice", "2nd Choice", "3rd Choice"};

        JComboBox<String>[] dropdowns = new JComboBox[3];
        for (int i = 0; i < 3; i++) {
            JPanel rowPanel = createHorizontalPanel();

            JLabel choiceLabel = new JLabel(choices[i] + ": ");
            choiceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            choiceLabel.setForeground(maroon);

            String[] initialOption = new String[candidates.length + 1];
            initialOption[0] = "Select Candidate for " + choices[i];
            System.arraycopy(candidates, 0, initialOption, 1, candidates.length);

            JComboBox<String> comboBox = new JComboBox<>(initialOption);
            comboBox.setBackground(white);
            comboBox.setPreferredSize(new Dimension(300, 30));
            comboBox.setMaximumSize(new Dimension(300, 30));
            dropdowns[i] = comboBox;

            rowPanel.add(choiceLabel);
            rowPanel.add(comboBox);
            rowPanel.add(Box.createHorizontalStrut(10));
            panel.add(rowPanel);
        }


        // BUTTONS
        JPanel buttonsPanel = createHorizontalPanel();
        JButton submitVoteButton = new JButton("Submit Vote");
        submitVoteButton.setBackground(maroon);
        submitVoteButton.setForeground(white);

        JButton startOverButton = new JButton("Start Over");
        startOverButton.setBackground(white);
        startOverButton.setForeground(maroon);

        buttonsPanel.add(submitVoteButton);
        buttonsPanel.add(Box.createHorizontalStrut(15));
        buttonsPanel.add(startOverButton);

        panel.add(buttonsPanel);


        // SUMMARY
        JPanel summaryPanel = createVerticalPanel();
        JTextArea summary = new JTextArea("After submitting your vote, please collect your ballot from the printer on your right and ensure that your ranked choices match your selections. After inspection, hand the printed ballot to the local election agent. Thank you for voting.");
        summary.setFont(new Font("Arial", Font.PLAIN, 12));
        summary.setWrapStyleWord(true);
        summary.setLineWrap(true);
        summary.setEditable(false);
        summary.setMaximumSize(new Dimension(600, 60));
        summary.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        summaryPanel.add(summary);
        summaryPanel.add(Box.createVerticalStrut(15));
        panel.add(summaryPanel);

        frame.add(panel);
        frame.setVisible(true);

    }
}
