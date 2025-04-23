import javax.swing.*;



import java.awt.Color;
import java.awt.Component;


public class App {    

    public static void main(String[] args) {

        JFrame frame = new JFrame("Voting Form");
        frame.setBackground(Color.WHITE);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = VotingForm.CreateVotingForm();

        frame.add(panel);
        frame.setVisible(true);

    }
}
