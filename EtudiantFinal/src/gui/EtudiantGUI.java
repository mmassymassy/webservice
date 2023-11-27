package gui;

import shared.IPlateforme;
import shared.ITuteur;
import shared.IHoraire;
import shared.IEtudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class EtudiantGUI extends JFrame {

    private IEtudiant etudiant;
    private List<ITuteur> selectedTuteurs;
    private ListModel<String> domaines;
    private ITuteur selectedTuteur;
    private IHoraire selectedHoraire;
    private String selectedMatiere;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField matiereRechercheField;

    private JList<String> tuteursList;
    private JList<String> domainesList;

    private JList<IHoraire> horairesList;
    private JList<String> tarifsList;

    private DefaultListModel<String> tuteursListModel;
    private DefaultListModel<IHoraire> horairesListModel;
    private DefaultListModel<String> tarifsListModel;

    private IPlateforme plateforme;
    JPanel panel;
    String[] t;

    public EtudiantGUI() {
        setTitle("Connexion Etudiant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        matiereRechercheField = new JTextField();

        JButton connectButton = new JButton("Se Connecter");
        connectButton.addActionListener(e -> {
            try {
                etudiant = connectEtudiant(emailField.getText(), new String(passwordField.getPassword()));
                showMainInterface();
            } catch (Exception ex) {
                handleException("Erreur de connexion.", ex);
            }
        });

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Mot de Passe:"));
        add(passwordField);
        add(connectButton);

        setVisible(true);
    }

    private void showMainInterface() throws RemoteException {
        getContentPane().removeAll();
        setTitle("Interface Etudiant");
        setSize(800, 400);

        tuteursListModel = new DefaultListModel<>();
        tuteursList = new JList<>(tuteursListModel);


        domaines = new DefaultListModel<String>();
        domainesList = new JList<>(domaines);

        horairesListModel = new DefaultListModel<>();
        horairesList = new JList<>(horairesListModel);

        horairesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        horairesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                IHoraire horaire = (IHoraire) value;
                if (horaire != null) {
                    String disp;
                    try {
                        disp = horaire.estDisponible() ? "Disponible" : "Indisponible";
                        setText(disp + ", Date : " + horaire.getDate() + " de " + horaire.getHeureDebut() + "h à " + horaire.getHeureFin());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                return this;
            }
        });


        tarifsListModel = new DefaultListModel<>();
        tarifsList = new JList<>(tarifsListModel);

        JButton rechercherButton = new JButton("Rechercher Tuteurs");
        rechercherButton.addActionListener(e -> {
            try {
                String matiereRecherche = matiereRechercheField.getText();
                tuteursListModel.clear();
                selectedTuteur = null;
                horairesListModel.clear();
                tarifsListModel.clear();

                if (!matiereRecherche.isEmpty()) {
                    System.out.println("Rechercher de tuteurs pour " + matiereRecherche);
                    updateTuteursList(matiereRecherche);
                    tarifsListModel.clear();
                } else {
                    JOptionPane.showMessageDialog(EtudiantGUI.this, "Veuillez entrer le nom d'une matière.");
                }
            } catch (Exception ex) {
                handleException("Erreur lors de la recherche des tuteurs.", ex);
            }
        });




        JButton reserverButton = new JButton("Reserver Tuteurs");
        reserverButton.addActionListener(x -> {
            try {
                if (selectedTuteur != null && selectedHoraire != null && selectedMatiere != null) {
                    selectedTuteur.associerEtudiant(selectedHoraire, etudiant, selectedMatiere);
                    etudiant.setTuteur(selectedHoraire, selectedTuteur, selectedMatiere);
                    horairesListModel.clear();
                    updateHorairesList(selectedTuteur);

                } else {
                    JOptionPane.showMessageDialog(EtudiantGUI.this, "Veuillez selectionner un tuteur et un horaire");
                }
            } catch (Exception ex) {
                handleException("Erreur selection de tuteur.", ex);
            }
        });

        tuteursList.addListSelectionListener(y -> {
            if (!y.getValueIsAdjusting()) {
                try {
                    String selectedTuteurEmail = tuteursList.getSelectedValue();
                    System.out.println("Selection de tuteur " + selectedTuteurEmail);

                    if (selectedTuteurEmail != null) {
                        selectedTuteur = plateforme.getTuteurs().get(selectedTuteurEmail);
                        System.out.println("Selection de tuteur " + selectedTuteur.getNom());
                        updateHorairesList(selectedTuteur);
                        updateTarifsList(selectedTuteur);

                    }
                } catch (Exception ex) {
                    handleException("Erreur lors de la sélection du tuteur.", ex);
                }
            }
        });
        tarifsList.addListSelectionListener(z -> {
            if (!z.getValueIsAdjusting()) {
                try {
                    // Assuming the selected value is in the format "Matière : [matiereName], Tarif : [tarifValue]"
                    String selectedValue = tarifsList.getSelectedValue();
                    int startIndex = selectedValue.indexOf(":") + 1;
                    int endIndex = selectedValue.indexOf(",");
                    if (startIndex != -1 && endIndex != -1) {
                        selectedMatiere = selectedValue.substring(startIndex, endIndex).trim();
                        System.out.println("Selection de matiere " + selectedMatiere);
                    }
                } catch (Exception ex) {
                    handleException("Erreur lors de la sélection du matiere.", ex);
                }
            }
        });


        horairesList.addListSelectionListener(a -> {
            if (!a.getValueIsAdjusting()) {
                selectedHoraire = horairesList.getSelectedValue();
                try {
                    System.out.println("Selection de l'horaire " + selectedHoraire.getDate());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(new JLabel("Matière à Rechercher:"));
        add(matiereRechercheField);
        add(rechercherButton);

        add(new JLabel("Tuteurs Disponibles:"));
        add(new JScrollPane(tuteursList));

        add(new JLabel("Domaines d'expertise de tuteur:"));
        add(new JScrollPane(domainesList));

        add(new JLabel("Couts par matières du Tuteur Sélectionné:"));
        add(new JScrollPane(tarifsList));
        add(new JLabel("Horaires Disponibles du Tuteur Sélectionné:"));
        add(new JScrollPane(horairesList));


        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setLayout(new FlowLayout());  // Set the layout manager to FlowLayout
        add(panel);
        JLabel lbl = new JLabel("Sélectionner un tuteur :");
        panel.add(lbl);


        JComboBox<String> cb = new JComboBox<>(t);
        panel.add(cb);

        JButton btn = new JButton("OK");
        panel.add(btn);

// Adjust the size of the panel
        panel.setPreferredSize(new Dimension(400, 50));  // Set an appropriate size

        add(reserverButton);

        setVisible(true);
    }

    private IEtudiant connectEtudiant(String email, String password) throws RemoteException, NotBoundException, MalformedURLException {
        plateforme = (IPlateforme) Naming.lookup("plateforme");
        return plateforme.loginEtudiant(email, password);
    }

    private void updateTuteursList(String matiereRecherche) {
        try {
            tuteursListModel.clear();
            tarifsListModel.clear();
            selectedTuteurs = plateforme.getTuteursParMatiere(matiereRecherche);
            t = new String[selectedTuteurs.size()] ;


            if (selectedTuteurs != null) {
                for (ITuteur entry : selectedTuteurs) {
                    tuteursListModel.addElement(entry.getEmail());
                }
                for(int i=0;i<t.length;i++){
                    t[i] = "Tuteur " + selectedTuteurs.get(i).getNom();
                }
                panel.updateUI();
            } else {
                System.out.println("Aucun tuteur trouvé.");
            }
        } catch (Exception ex) {
            handleException("Erreur lors de la recherche des tuteurs.", ex);
        }
    }

    private void updateHorairesList(ITuteur tuteur) {
        try {
            if (tuteur != null) {
                for (IHoraire horaire : tuteur.getHorairesDisponibles()) {
                    String disp = horaire.estDisponible() ? "Disponible" : "Indisponible";
                    horairesListModel.addElement(horaire);
                }
            }
        } catch (Exception ex) {
            handleException("Erreur lors de la mise à jour des horaires.", ex);
        }
    }

    private void updateTarifsList(ITuteur tuteur) {
        try {
            tarifsListModel.clear();
            if (tuteur != null) {
                for (Map.Entry<String, Double> entry : tuteur.getTarifParMatire().entrySet()) {
                    tarifsListModel.addElement("Matière : " + entry.getKey() + ", Tarif : " + entry.getValue());
                }
            }
        } catch (Exception ex) {
            handleException("Erreur lors de la mise à jour des tarifs.", ex);
        }
    }


    private void handleException(String message, Exception ex) {
        JOptionPane.showMessageDialog(EtudiantGUI.this, message + " " + ex.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EtudiantGUI());
    }
}
