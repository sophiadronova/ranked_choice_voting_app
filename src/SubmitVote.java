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

        String first = (String) app.dropdowns[0].getSelectedItem();
        String second = (String) app.dropdowns[1].getSelectedItem();
        String third = (String) app.dropdowns[2].getSelectedItem();

        if (first.startsWith("Select") || second.startsWith("Select") || third.startsWith("Select")) {
            JOptionPane.showMessageDialog(app.panel, "Please select your top three choices");
            return;
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
            Document vote = new Document("id", id).append("pin", pin).append("firstChoice", first).append("secondChoice", second).append("thirdChoice", third);
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
