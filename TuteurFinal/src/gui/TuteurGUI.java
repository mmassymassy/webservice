package gui;

import classes.Tuteur;
import shared.IPlateforme;
import shared.ITuteur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class TuteurGUI extends JFrame {

    private JTextField nomField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField domainesField;

    public TuteurGUI() {
        setTitle("Création de compte tuteur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        nomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JTextField();
        domainesField = new JTextField();

        JButton creerCompteButton = new JButton("Créer Compte");
        creerCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creerCompteTuteur();
            }
        });

        add(new JLabel("Nom:"));
        add(nomField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Mot de passe:"));
        add(passwordField);
        add(new JLabel("Domaines d'expertise (séparés par des virgules):"));
        add(domainesField);
        add(creerCompteButton);

        setVisible(true);
    }

    private void creerCompteTuteur() {
        try {
            String nom = nomField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            List<String> domainesExpertise = List.of(domainesField.getText().split(","));

            ITuteur tuteur = new Tuteur(nom, email, password, domainesExpertise);

            // Vous pouvez ajouter le tuteur à votre plateforme RMI ici
            IPlateforme plateform;
            plateform = (IPlateforme) Naming.lookup("plateforme");

            plateform.inscrireTuteur(tuteur);

            JOptionPane.showMessageDialog(this, "Compte tuteur créé avec succès!");
            clearFields();
        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du compte tuteur");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        nomField.setText("");
        emailField.setText("");
        passwordField.setText("");
        domainesField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TuteurGUI();
            }
        });
    }
}
