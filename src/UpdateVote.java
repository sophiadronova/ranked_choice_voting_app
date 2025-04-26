import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

                Map<String, String> partyNameMap = Map.of(
                    "Taylor Swift", "Freedom",
                    "Mr. Bean", "Comedy",
                    "George Washington", "President",
                    "Abraham Lincoln", "President",
                    "William Henry Harrison", "Freedom"
                );

                List<Document> updatedRankings = new ArrayList<>();
                for (int i = 0; i < 3; i++){
                    String nomineeName = (String) app.dropdowns[i].getSelectedItem();

                    if (nomineeName == null || nomineeName.startsWith("Select")) {
                        JOptionPane.showMessageDialog(app.panel, "Please select a valid candidate for all ranks.");
                        return;                        
                    }
                    String party = partyNameMap.get(nomineeName);
                    String nomineeID = "n" + (i + 1);
                    Document nomineeInfo = new Document("nomineeID", nomineeID)
                    .append("name", nomineeName)
                    .append("party", party);
                   
                    Document ranking = new Document("rank", i + 1).append("nominee", nomineeInfo);
                    updatedRankings.add(ranking);
                }

                votes.updateOne(new Document("voter.voterID", id), new Document("$set", new Document("rankings", updatedRankings)));

                JOptionPane.showMessageDialog(null, "Ballot Updated Successfully!");
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
