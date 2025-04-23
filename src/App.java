import javax.swing.*;



import java.awt.Color;
import java.awt.Component;


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

        submitVoteButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String pin = pinField.getText().trim();

            if (id.isEmpty() || pin.isEmpty() || id.equals("Enter your Voter ID") || pin.equals("Enter your Registration PIN")) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid Voter ID and PIN");
                return;
            }
            
            // STEP 2: BEGIN WORKING ON RANKINGS DOCUMENT
            String[] nomineeIDs = { "n1", "n2", "n3" };
            String[] selections = new String[3];
            // LOOP THROUGH ALL DROP DOWNS AND ADD NAMES TO NEW SELECTIONS LIST
            for (int i = 0; i < 3; i++) {
                selections[i] = (String) dropdowns[i].getSelectedItem();
                // CHECK THAT ALL CHOICES HAVE BEEN SELECTED
                if (selections[i].startsWith("Select")) {
                    JOptionPane.showMessageDialog(panel, "Please select all top three choices");
                    return;
                }
            }
            // STEP 3: MAP CANDIDATE TO A PARTY
            Map<String, String> partyMap = Map.of(
                "William Henry Harrison", "Whigs",
                "Taylor Swift", "Freedom",
                "Abraham Lincoln", "Unity",
                "Mr. Bean", "Comedy",
                "George Washington", "Founders"
            );

            // STEP 4: CREATE RANKINGS DOCUMENT/ARRAY
            List<Document> rankings = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String name = selections[i];
                String party = partyMap.getOrDefault(name, "Independent");
        
                rankings.add(new Document("rank", i + 1)
                    .append("nominee", new Document("nomineeID", nomineeIDs[i])
                    .append("name", name)
                    .append("party", party)));
            }            

            String uri = "mongodb+srv://sophiadronova:csce310team11@cluster0.btrged1.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase db = mongoClient.getDatabase("votingdb");
                MongoCollection<Document> votes = db.getCollection("votes");

                // check if voter has already voted with this id and pin
                Document existingVote = votes.find(new Document("id", id)).first();

                // if vote does exist, show message
                if (existingVote != null) {
                    JOptionPane.showMessageDialog(panel, "You have already voted");
                    return;
                }

                // otherwise, insert vote into db
                // STEP ONE: CREATE EMBEDDED DOCUMENT MODEL
                String electionID = "2023General", electionName = "2023 General Election", electionDate = "2023-04-03T00:00:00Z";
                Document vote = new Document("voter", new Document("voterID", id).append("regPIN", pin))
                .append("election", new Document("electionID", electionID).append("name", electionName).append("date", electionDate))
                .append("timestamp", Instant.now().toString())
                .append("rankings", rankings);
                votes.insertOne(vote);

                JOptionPane.showMessageDialog(panel, "Your vote has been recorded!");
       
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Database error: " + ex.getMessage());
            }            
        });


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
