import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

public class DeleteVote {
    public static void Delete(App app) {
        String id = app.idField.getText().trim();

        if (id.isEmpty()  || id.equals("Enter your Voter ID")) {
            JOptionPane.showMessageDialog(app.panel, "Please enter a valid Voter ID");
            return;
        }

        String uri = "mongodb+srv://sophiadronova:csce310team11@cluster0.btrged1.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("votingdb");
            MongoCollection<Document> votes = db.getCollection("votes");

            Document existingVote = votes.find(new Document("voter.voterID", id)).first();
            if (existingVote != null) {
                DeleteResult result = votes.deleteOne(existingVote);
                if (result.getDeletedCount() > 0) {
                    JOptionPane.showMessageDialog(null, "Ballot deleted successfully!");
                    app.idField.setText("");
                    app.pinField.setText("");
            
                    for (JComboBox<String> dropdown : app.dropdowns) {
                        dropdown.setSelectedIndex(0);  // Assuming index 0 is "Select Candidate"
                    }
            
                    app.idField.setEditable(true);  
                }
            } else {
                JOptionPane.showMessageDialog(app.panel, "Ballot not deleted successfully.");
                return;
            }        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }
        
    }
}
