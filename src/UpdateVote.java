import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UpdateVote {
    public static void Update(App app)  {
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
                String regPIN = existingVote.get("voter", Document.class).getString("regPIN");
                app.pinField.setText(regPIN);

                @SuppressWarnings("unchecked")
                List<Document> rankings = (List<Document>) existingVote.get("rankings");
                for (Document ranking : rankings){
                    int rank = ranking.getInteger("rank");
                    String nomineeName = ranking.get("nominee", Document.class).getString("name");
                    app.dropdowns[rank - 1].setSelectedItem(nomineeName);
                }

                JOptionPane.showMessageDialog(null, "Ballot Retrieved Successfully!");
            }
            else {
                JOptionPane.showMessageDialog(null, "No ballot found for this Voter ID");
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(app.panel, "Database error: " + ex.getMessage());
        }
    
    }  
}
