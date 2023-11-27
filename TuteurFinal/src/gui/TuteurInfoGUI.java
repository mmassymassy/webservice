package gui;

import classes.Tuteur;
import shared.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class TuteurInfoGUI extends JFrame {

    private ITuteur tuteur;

    private JTextArea etudiantsAssociesTextArea;
    private JTextArea listeAttenteTextArea;
    private JTextArea horairesDisponiblesTextArea;

    private JTextField dateField;
    private JTextField matiereField;
    private JTextField debutField;
    private JTextField finField;
    private JTextField matiere;
    private JTextField cout;

    private JTextArea tarifs;


    public TuteurInfoGUI(ITuteur tuteur) throws RemoteException {
        this.tuteur = tuteur;

        setTitle("Informations du Tuteur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        etudiantsAssociesTextArea = new JTextArea();
        etudiantsAssociesTextArea.setEditable(false);

        listeAttenteTextArea = new JTextArea();
        listeAttenteTextArea.setEditable(false);

        horairesDisponiblesTextArea = new JTextArea();
        horairesDisponiblesTextArea.setEditable(false);

        dateField = new JTextField();
        matiereField = new JTextField();
        debutField = new JTextField();
        finField = new JTextField();
        matiere  = new JTextField();
        cout = new JTextField();

        tarifs = new JTextArea();
        tarifs.setEditable(false);

        JButton ajouterHoraireButton = new JButton("Ajouter Horaire");
        JButton ajouterMatiere = new JButton("Ajouter Matière");


        ajouterHoraireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterHoraire();

            }
        });
        ajouterMatiere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMatiere();

            }
        });

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateTuteurInfo();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(new JLabel("Étudiants Associés:"));
        add(etudiantsAssociesTextArea);
        add(new JLabel("Liste d'Attente:"));
        add(listeAttenteTextArea);
        add(new JLabel("Horaires Disponibles:"));
        add(horairesDisponiblesTextArea);

        add(new JLabel("Tarifs:"));
        add(tarifs);

        add(new JLabel("Ajouter une matiére:"));
        add(new JLabel("Nom de la matiére"));
        add(matiere);
        add(new JLabel("Cout/h:"));
        add(cout);
        add(ajouterMatiere);


        add(new JLabel("Ajouter un Horaire:"));
        add(new JLabel("Date (DD/MM/YYYY):"));
        add(dateField);
        add(new JLabel("Matière:"));
        add(matiereField);
        add(new JLabel("Heure de début:"));
        add(debutField);
        add(new JLabel("Heure de fin:"));
        add(finField);
        add(ajouterHoraireButton);

        add(refreshButton);

        updateTuteurInfo();

        setVisible(true);
    }

    private void ajouterHoraire() {
        try {
            String date = dateField.getText();
            String matiere = matiereField.getText();
            int debut = Integer.parseInt(debutField.getText());
            int fin = Integer.parseInt(finField.getText());

            IHoraire nouvelHoraire = new Horaire(date, matiere, debut, fin);
            tuteur.ajouterHoraireDisponible(nouvelHoraire);

            // Actualiser les informations après l'ajout de l'horaire
            updateTuteurInfo();

            // Effacer les champs après l'ajout
            dateField.setText("");
            matiereField.setText("");
            debutField.setText("");
            finField.setText("");
        } catch (NumberFormatException | RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des informations valides.");
        }
    }
    private void ajouterMatiere() {
        try {
            String m = matiere.getText();
            double c = Double.parseDouble(cout.getText());


            tuteur.ajouterTarif(m,c);

            // Actualiser les informations après l'ajout de l'horaire
            updateTuteurInfo();
            // Effacer les champs après l'ajout
            matiere.setText("");
            cout.setText("");
        } catch (NumberFormatException | RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des informations valides.");
        }
    }

    private void updateTuteurInfo() throws RemoteException {
        // Étudiants associés
        StringBuilder etudiantsAssociesInfo = new StringBuilder("Étudiants Associés:\n");
        String nom,email,info;
        for (Map.Entry<IHoraire, IEtudiant> entry : tuteur.getEtudiantsAssociés().entrySet()) {
            nom = entry.getValue().getNom();
            email = entry.getValue().getEmail();
            info = "Matiere"+entry.getKey().getMatiere()+", Date :"+entry.getKey().getDate()+", Début :"+entry.getKey().getHeureDebut();

            etudiantsAssociesInfo.append("Nom"+nom+", Email :"+email+", "+info+"\n");
        }
        etudiantsAssociesTextArea.setText(etudiantsAssociesInfo.toString());

        // Liste d'attente
        StringBuilder listeAttenteInfo = new StringBuilder("Liste d'Attente:\n");
        for (IEtudiant etudiant : tuteur.getListeAttente()) {
            listeAttenteInfo.append(etudiant.getNom()).append("\n");
        }
        listeAttenteTextArea.setText(listeAttenteInfo.toString());

        // Horaires disponibles
        StringBuilder horairesDisponiblesInfo = new StringBuilder("Horaires Disponibles:\n");
        String date,dispo;
        int start,end;
        for (IHoraire horaire : tuteur.getHorairesDisponibles()) {
            if(horaire.estDisponible()) {dispo = "disponible"; } else { dispo = "indisponible";}
            date = horaire.getDate();
            start = horaire.getHeureDebut();
            end = horaire.getHeureFin();
            horairesDisponiblesInfo.append(dispo+":"+date+" :"+" de "+start+"h à "+end+"h ").append("\n");
        }
        horairesDisponiblesTextArea.setText(horairesDisponiblesInfo.toString());

        StringBuilder t = new StringBuilder("Liste des tarifs:\n");
        double c;
        for (Map.Entry<String, Double> entry : tuteur.getTarifParMatire().entrySet()) {
            nom = entry.getKey();
            c = entry.getValue().doubleValue();

            t.append("Matiére : "+nom+", Cout/h : :"+c+"\n");
        }
        tarifs.setText(t.toString());

    }

    public static void main(String[] args) {
        // Exemple d'utilisation : créez le Tuteur dans la fenêtre de création de compte
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ITuteur tuteur = new Tuteur("tuteur 1", "Email", "Password", List.of("Math", "Physique"));
                    IPlateforme plateforme;
                    plateforme = (IPlateforme) Naming.lookup("plateforme");
                    plateforme.inscrireTuteur(tuteur);
                    new TuteurInfoGUI(tuteur);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

