package classes;

import shared.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tuteur extends Utilisateur implements ITuteur,Serializable {
    private List<String> domainesExpertise;
    private Map<String,IEtudiant> etudiantsAssociés;
    private Map<IHoraire,IEtudiant> horairesAssociés;
    private ListeAttente listeAttente;


    private List<IHoraire> horairesDisponibles;
    private	Map<String,Double> tarifParMatiere;

    public Tuteur(String nom,String email,String password, List<String> domainesExpertise) throws RemoteException {
    	super(nom,email,password);
        this.domainesExpertise = domainesExpertise;
        this.etudiantsAssociés = new HashMap<String,IEtudiant>();
        this.horairesAssociés = new HashMap<IHoraire,IEtudiant>();
        this.horairesDisponibles = new ArrayList<IHoraire>();
        this.tarifParMatiere = new HashMap<String,Double>();
        this.listeAttente = new ListeAttente();
    }

    public List<String> getDomainesExpertise() {
        return domainesExpertise;
    }

    @Override
    public boolean estDisponible() throws RemoteException {
        return false;
    }

    public boolean estDisponible(IHoraire h) throws RemoteException {
        return h.estDisponible();
    }

    public Map<String,IEtudiant> getEtudiantsAssociés() {
        return etudiantsAssociés;
    }

    public List<IHoraire> getHorairesDisponibles() {
        return horairesDisponibles;
    }

    public Map<String,Double> getTarifParMatire() {
        return tarifParMatiere;
    }

    // Méthode pour associer un étudiant au tuteur
    public void associerEtudiant(String matiere,IHoraire h,IEtudiant etudiant) throws RemoteException {
        if(h.estDisponible()) {
            etudiantsAssociés.put(matiere, etudiant);
            horairesAssociés.put(h, etudiant);
            //int i = horairesDisponibles.indexOf(h);
            h.setIndisponible();
            //horairesDisponibles.set(i, h);
            System.out.println("L'étudiant " + etudiant.getNom() + " est associé au tuteur " + this.getNom());
        }else{
            listeAttente.ajouterEnAttente((ITask) new Task(matiere,h,etudiant)); //ajouter la tache à la liste d attente

        }

    }

    // Méthode pour libérer le tuteur et indiquer qu'il est disponible
    public void libererTuteurParHoraire(IHoraire h) throws RemoteException {
        ITask nextTask = listeAttente.getNextTask(h); //le prochaine rdv pris par l'etudiant à l'horaire h
        //int i = horairesDisponibles.indexOf(h);
        IEtudiant e = horairesAssociés.get(h); //recuperer l'etudiant qui a reservé l'horaire h
        etudiantsAssociés.values().remove(e);  // retirer l'etudiant de la liste des etudiant associés au tuteur
        h.setIndisponible(); // mettre à jour l'horaire
        //horairesDisponibles.set(i,h);

        System.out.println("Le tuteur " + this.getNom() + " est maintenant disponible.");
        if(nextTask != null){
            //Notifier les étudiants qui sont dans la liste d'attente
            this.listeAttente.notifier(h,this); //notifier les etudiants qui sont dans la liste d'attente de tuteur
            //this.associerEtudiant(nextTask.getMatiere(), nextTask.getHoraire(), nextTask.getEtudiant());
        }
    }

    // Méthode pour ajouter des horaires disponibles
    public void ajouterHoraireDisponible(IHoraire horaire) {
        horairesDisponibles.add(horaire);
    }
    public void ajouterTarif(String matiere, Double prix){
        tarifParMatiere.put(matiere,prix);
    }
    public IListeAttente getListeAttente(){
        return this.listeAttente;
    }
}
