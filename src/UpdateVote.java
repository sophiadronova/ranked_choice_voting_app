import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

public class UpdateVote {
    public static Document getNomineeInfo(Document existingVote, String nomineeName) {
        @SuppressWarnings("unchecked")
        List<Document> rankings = (List<Document>) existingVote.get("rankings");
        for (Document ranking : rankings) {
            Document nominee = ranking.get("nominee", Document.class);
            if (nominee.getString("name").equals(nomineeName)) {
                return nominee;
            }
        }
        return new Document("nomineeID", "")
                    .append("name", nomineeName)
                    .append("party", "");
    }

    public static void Update(App app) {
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
                List<Document> updatedRankings = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    String nomineeName = (String) app.dropdowns[i].getSelectedItem();
                    
                    Document nomineeInfo = getNomineeInfo(existingVote, nomineeName);
                    Document ranking = new Document("rank", i + 1).append("nominee", nomineeInfo);
                    updatedRankings.add(ranking);
                }

                Bson updates = Updates.set("rankings", updatedRankings);
                votes.updateOne(new Document("voter.voterID", id), updates);
 
                JOptionPane.showMessageDialog(null, "Ballot updated successfully!");
            } else {
                JOptionPane.showMessageDialog(app.panel, "Ballot not updated successfully.");
                return;
            }        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }

        
    }
}
