import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SubmitVote {
    public static void Submit(App app) {
        String id = app.idField.getText().trim();
        String pin = app.pinField.getText().trim();

        if (id.isEmpty() || pin.isEmpty() || id.equals("Enter your Voter ID") || pin.equals("Enter your Registration PIN")) {
            JOptionPane.showMessageDialog(app.panel, "Please enter a valid Voter ID and PIN");
            return;
        }

        String[] nomineeIDs = {"n1", "n2", "n3"};
        String[] choices = new String[3];
        for (int i = 0; i < 3; i++) {
            choices[i] = (String) app.dropdowns[i].getSelectedItem();
            if (choices[i].startsWith("Select")) {
                JOptionPane.showMessageDialog(app.panel, "Please select all three choices");
                return;                
            }
        }

        Map<String, String> partyNameMap = Map.of(
            "Taylor Swift", "Freedom",
            "Mr. Bean", "Comedy",
            "George Washington", "President",
            "Abraham Lincoln", "President",
            "William Henry Harrison", "Freedom"
        );

        List<Document> rankings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String name = choices[i];
            String party = partyNameMap.get(name);

            rankings.add(new Document("rank", i + 1).append("nominee", new Document("nomineeID", nomineeIDs[i]).append("party", party)));
        }


        String uri = "mongodb+srv://sophiadronova:csce310team11@cluster0.btrged1.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("votingdb");
            MongoCollection<Document> votes = db.getCollection("votes");

            // check if voter has already voted with this id and pin
            Document existingVote = votes.find(new Document("id", id)).first();

            // if vote does exist, show message
            if (existingVote != null) {
                JOptionPane.showMessageDialog(app.panel, "You have already voted");
                return;
            }

            // otherwise, insert vote into db
            String electionID = "2025General", electionName = "2025 General Election", electionDate = "2023-04-03T00:00:00Z";
            Document vote = new Document("voter", new Document("voterID", id).append("regPIN", pin))
            .append("election", new Document("electionID", electionID).append("name", electionName).append("date", electionDate))
            .append("timestamp", Instant.now().toString())
            .append("rankings", rankings);
            votes.insertOne(vote);

            app.pinField.setText("Enter your Registration PIN");
            app.pinField.setForeground(Color.LIGHT_GRAY);
            app.pinField.setEditable(false);

            app.idField.setText("Enter your Voter ID");
            app.idField.setForeground(Color.LIGHT_GRAY);
            app.idField.setEditable(false);

            for (JComboBox<String> box : app.dropdowns) {
                box.setSelectedIndex(0);
                box.repaint();
            }

            JOptionPane.showMessageDialog(app.panel, "Your vote has been recorded!");
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }
    }
}
