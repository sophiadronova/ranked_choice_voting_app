import javax.swing.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.FocusEvent;

public class App {

    public JTextField idField;
    public JTextField pinField;
    public JPanel panel;
    public JComboBox<String> dropdowns[];

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
        App app = new App();

        Color maroon = new Color(128, 0, 0);
        Color white = Color.WHITE;

        JFrame frame = new JFrame("Voting Form");
        frame.setBackground(Color.WHITE);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = createVerticalPanel();
        app.panel = panel;
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
        app.idField = idField;
        idField.setPreferredSize(textFieldSize);
        idField.setMaximumSize(textFieldSize);
        idField.setForeground(Color.LIGHT_GRAY);
        idField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        idField.setEditable(false);
        idField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!idField.isEditable()) {
                    idField.setText("");
                    idField.setEditable(true);
                    idField.setForeground(Color.BLACK);
                    idField.requestFocus();
                }
            }
        });
        idField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (idField.getText().trim().isEmpty()) {
                    idField.setText("Enter your Voter ID");
                    idField.setForeground(Color.LIGHT_GRAY);
                    idField.setEditable(false);
                }
            }            
        });

        // REGISTRATION PIN INPUT
        JPanel pinPanel = createVerticalPanel();
        JLabel pinLabel = new JLabel("Registration PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pinLabel.setForeground(maroon);

        JTextField pinField = new JTextField("Enter your Registration PIN");
        app.pinField = pinField;
        pinField.setPreferredSize(textFieldSize);
        pinField.setMaximumSize(textFieldSize);
        pinField.setForeground(Color.LIGHT_GRAY);
        pinField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pinField.setEditable(false);
        pinField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!pinField.isEditable()) {
                    pinField.setText("");
                    pinField.setEditable(true);
                    pinField.setForeground(Color.BLACK);
                    pinField.requestFocus();
                }
            }
        });
        pinField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (pinField.getText().trim().isEmpty()) {
                    pinField.setText("Enter your Registration PIN");
                    pinField.setForeground(Color.LIGHT_GRAY);
                    pinField.setEditable(false);
                }
            }            
        });

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


        @SuppressWarnings("unchecked")
        JComboBox<String>[] dropdowns = (JComboBox<String>[]) new JComboBox[3];
        app.dropdowns = dropdowns;
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


            // grays out already selected candidates
            comboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    String val = (String) value;

                    // gray out if already selected
                    boolean isAlreadyChosen = false;
                    for (JComboBox<String> otherBox : dropdowns) {
                        if (otherBox != comboBox && val != null && val.equals(otherBox.getSelectedItem())) {
                            isAlreadyChosen = true;
                            break;
                        }
                    }

                    if (isAlreadyChosen && index > 0) {
                        label.setForeground(Color.LIGHT_GRAY);
                    } else {
                        label.setForeground(Color.BLACK);
                    }
                    return label;
                }
            });

            // disables
            comboBox.addActionListener (e -> {
                String selected = (String) comboBox.getSelectedItem();

                if (selected != null) {
                    for (JComboBox<String> otherBox : dropdowns) {
                        if (otherBox != comboBox && selected.equals(otherBox.getSelectedItem())) {
                            SwingUtilities.invokeLater(() -> comboBox.setSelectedIndex(0));
                            break;
                        } 
                    }
                }
                for (JComboBox<String> box : dropdowns) {
                    box.repaint();
                }
            });

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
        submitVoteButton.addActionListener(e -> { SubmitVote.Submit(app); });

        JButton startOverButton = new JButton("Start Over");
        startOverButton.setBackground(white);
        startOverButton.setForeground(maroon);
        startOverButton.addActionListener(e -> {
            pinField.setText("Enter your Registration PIN");
            pinField.setForeground(Color.LIGHT_GRAY);
            pinField.setEditable(false);

            idField.setText("Enter your Voter ID");
            idField.setForeground(Color.LIGHT_GRAY);
            idField.setEditable(false);

            for (JComboBox<String> box : dropdowns) {
                box.setSelectedIndex(0);
                box.repaint();
            }
        });

        buttonsPanel.add(submitVoteButton);
        buttonsPanel.add(Box.createHorizontalStrut(15));
        buttonsPanel.add(startOverButton);

        panel.add(buttonsPanel);

        // RETRIEVE, UPDATE, DELETE BUTTONS
        JPanel retrieveAndUpdatePanel = createHorizontalPanel();
        JButton retrieveButton = new JButton("Retreive Ballot");
        retrieveButton.setBackground(maroon);
        retrieveButton.setForeground(white);
        retrieveButton.addActionListener(e -> { ReadVote.Read(app); });

        JButton updateButton = new JButton("Update Ballot");
        updateButton.setBackground(maroon);
        updateButton.setForeground(white);
        updateButton.addActionListener(e -> { UpdateVote.Update(app); });

        JButton deleteButton = new JButton("Delete Ballot");
        deleteButton.setBackground(maroon);
        deleteButton.setForeground(white);
        deleteButton.addActionListener(e -> { DeleteVote.Delete(app); });

        retrieveAndUpdatePanel.add(retrieveButton);
        retrieveAndUpdatePanel.add(Box.createHorizontalStrut(15));
        retrieveAndUpdatePanel.add(updateButton);
        retrieveAndUpdatePanel.add(Box.createHorizontalStrut(15));
        retrieveAndUpdatePanel.add(deleteButton);
        panel.add(retrieveAndUpdatePanel);

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