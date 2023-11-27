package gui;

import classes.Etudiant;
import shared.IEtudiant;
import shared.IHoraire;
import shared.IPlateforme;
import shared.ITuteur;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EtudiantG  extends JFrame {

    private IEtudiant etudiant;
    private Map<String, ITuteur> tuteursAssocies;
    private ITuteur selectedTuteur;
    private List<ITuteur> tuteursTrouves;

    private String selectedMatiere;
    private IHoraire selectedHoraire;

    private JTextField email,password;
    private IPlateforme plateforme;

    DefaultListModel l1 = new DefaultListModel<ITuteur>();
    private JList tuteursTrouvesJlist = new JList<ITuteur>(l1);

    public EtudiantG() throws MalformedURLException, NotBoundException, RemoteException {
        plateforme = (IPlateforme) Naming.lookup("plateforme");
        setTitle("Connexion Etudiant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        email = new JTextField();
        password = new JPasswordField();
        JButton connectButton = new JButton("Se Connecter");
        connectButton.addActionListener(e -> {
            try {
                etudiant = plateforme.loginEtudiant(email.getText(),password.getText());
                showMainInterface();
            } catch (Exception ex) {
                System.out.println("Erreur de connexion." + ex);
            }
        });

        add(new JLabel("Email:"));
        add(email);
        add(new JLabel("Mot de Passe:"));
        add(password);
        add(connectButton);

        setVisible(true);
    }
    private void showMainInterface() {
        getContentPane().removeAll();
        setTitle("Interface Etudiant");
        setSize(800, 400);
        JTextField matiereRechercheField = new JTextField();
        add(new JLabel("Matière à Rechercher:"));
        add(matiereRechercheField);
        JButton btnRecherche = new JButton("Rechercher");
        add(btnRecherche);
        btnRecherche.addActionListener(e -> {
            try {
                tuteursTrouves =  plateforme.getTuteursParMatiere(matiereRechercheField.getText());
                l1.clear();
                tuteursTrouvesJlist.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        ITuteur tuteur = (ITuteur) value;
                        if (tuteur != null) {
                            try {
                                setText( "Nom :  " + tuteur.getNom() + ", Email : " + tuteur.getEmail() );
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return this;
                    }
                });



                for (ITuteur t : tuteursTrouves){
                    l1.addElement("Nom :"+t.getNom()+ ", Email : " +t.getEmail() +"\n");
                }


            } catch (Exception ex) {
                System.out.println("Erreur de connexion."+ex);
            }
        });

        add(new JLabel("Tuteurs trouvés:"));
        add(tuteursTrouvesJlist);




        setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try {
                new EtudiantG();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }


}
