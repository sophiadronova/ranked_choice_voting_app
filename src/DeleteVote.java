import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class DeleteVote {
    public static void Delete(App app) {
        String id = app.idField.getText().trim();

        if (id.isEmpty() || id.equals("Enter your Voter ID")) {
            JOptionPane.showMessageDialog(app.panel, "Please enter a valid Voter ID");
            return;
        }

        String uri = "mongodb+srv://sophiadronova:csce310team11@cluster0.btrged1.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("votingdb");
            MongoCollection<Document> votes = db.getCollection("votes");

            // check if voter has already voted with this id and pin
            Document existingVote = votes.find(new Document("voter.voterID", id)).first();

            // if vote does exist, show message
            if (existingVote != null) {
                DeleteResult result = votes.deleteOne(existingVote);

                // successfully deleted
                if (result.getDeletedCount() > 0) {
                    JOptionPane.showMessageDialog(app.panel, "Vote deleted successfully!");

                    // reset input fields
                    app.idField.setText("");
                    app.pinField.setText("");

                    // reset comboboxes
                    for (JComboBox<String> dropdown : app.dropdowns) {
                        dropdown.setSelectedIndex(0);
                    }

                    app.idField.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(app.panel, "Failed to delete vote. Please try again.");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Failed to delete vote. Please try again.");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }
    }
}
