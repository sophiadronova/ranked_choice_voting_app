import java.awt.Color;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ReadVote {
    public static void Read(App app) {
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
                String regPIN = existingVote.get("voter", Document.class).getString("regPIN");
                app.pinField.setText(regPIN);

                @SuppressWarnings("unchecked")
                List<Document> rankings = (List<Document>) existingVote.get("rankings");
                for (Document ranking : rankings) {
                    int rank = ranking.getInteger("rank");
                    String nomineeName = ranking.get("nominee", Document.class).getString("name");
                    app.dropdowns[rank - 1].setSelectedItem(nomineeName);
                }
                // app.idField.setEnabled(false);
                
                JOptionPane.showMessageDialog(null, "Ballot retrieved successfully!");
            } else {
                JOptionPane.showMessageDialog(app.panel, "No ballot found for this Voter ID");
                return;
            }        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }
    }
}
