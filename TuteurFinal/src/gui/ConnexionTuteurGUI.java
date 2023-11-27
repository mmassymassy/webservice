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

public class ConnexionTuteurGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public ConnexionTuteurGUI() {
        setTitle("Connexion Tuteur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton connexionButton = new JButton("Connexion");
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    verifierConnexion();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (NotBoundException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Mot de passe:"));
        add(passwordField);
        add(connexionButton);

        setVisible(true);
    }

    private void verifierConnexion() throws MalformedURLException, NotBoundException, RemoteException {
        String email = emailField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Implémentez la logique de vérification des informations de connexion ici
        IPlateforme plateform;
        plateform = (IPlateforme) Naming.lookup("plateforme");
        //
        System.out.println("Trying to connect");
        ITuteur t = plateform.login(email,password);
        System.out.println(t.getNom());
        // Vous pouvez utiliser ces informations pour récupérer le tuteur correspondant

        if(t != null){
            // Exemple d'utilisation
            new TuteurInfoGUI(t);
            dispose(); // Ferme la fenêtre de connexion après la connexion réussie
        }else{
            JOptionPane.showMessageDialog(this, "Erreur de connexion. Veuillez réessayer.");
        }

    }

    public static void main(String[] args) {
        // Exemple d'utilisation : créez la fenêtre de connexion
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnexionTuteurGUI();
            }
        });
    }
}
