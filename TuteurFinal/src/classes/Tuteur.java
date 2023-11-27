package classes;

import shared.IEtudiant;
import shared.IHoraire;
import shared.ITuteur;
import shared.Utilisateur;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tuteur extends Utilisateur implements Serializable,ITuteur {
    private List<String> domainesExpertise;
    private Map<IHoraire,IEtudiant> etudiantsAssociés;
    private List<IEtudiant> listeAttente;
    private List<IHoraire> horairesDisponibles;
    private	Map<String,Double> tarifParMatiere;

    public Tuteur(String nom,String email,String password, List<String> domainesExpertise) throws RemoteException {
    	super(nom,email,password);
        this.domainesExpertise = domainesExpertise;
        this.etudiantsAssociés = new HashMap<IHoraire,IEtudiant>();
        this.horairesDisponibles = new ArrayList<IHoraire>();
        this.tarifParMatiere = new HashMap<String,Double>();
        this.listeAttente = new ArrayList<IEtudiant>();
    }

    public List<String> getDomainesExpertise() {
        return domainesExpertise;
    }

    public boolean estDisponible() throws RemoteException {
        boolean check=true;
        for(IHoraire h: horairesDisponibles){
            check = check & h.estDisponible();
        }
        return check;
    }


    public Map<IHoraire,IEtudiant> getEtudiantsAssociés() {
        return etudiantsAssociés;
    }

    public List<IHoraire> getHorairesDisponibles() {
        return horairesDisponibles;
    }

    public Map<String,Double> getTarifParMatire() {
        return tarifParMatiere;
    }

    // Méthode pour associer un étudiant au tuteur
    public void associerEtudiant(IHoraire h,IEtudiant etudiant, String matiere) throws RemoteException {
        System.out.println("L'étudiant " + etudiant.getNom() + " souhaite être associé au tuteur " + this.getNom());
        if(etudiantsAssociés.get(etudiant.getEmail()) != null){
            System.out.println("L'étudiant " + etudiant.getNom() + " est dèja associé au tuteur " + this.getNom());
            return;
        }
        if(listeAttente.contains(etudiant)){
            System.out.println("L'étudiant " + etudiant.getNom() + " est dèja dans la liste d'attente  de tuteur " + this.getNom());
            return;
        }
        if (h.estDisponible()){ //si l'horaire est disponible
            System.out.println("L'horaire est disponible");
            h.setMatiere(matiere); //definir la matiere associé au chréno
            h.setIndisponible();  //marquer l'horaire cm indisponible
            etudiantsAssociés.put(h,etudiant); //affecter l'etudiant au tuteur
            System.out.println("L'étudiant " + etudiant.getNom() + " est associé au tuteur " + this.getNom()+" pour la matiere "+h.getMatiere());
        }else{
            System.out.println("L'horaire n'est pas disponible");
            listeAttente.add(etudiant);
            System.out.println("L'étudiant " + etudiant.getNom() + " est en attente de tuteur " + this.getNom());
        }

    }
    public void notifierEtudiants(IHoraire h) throws RemoteException {
        for(IEtudiant e:listeAttente){
            e.addNotification(this.getNom() + " est désormais disponible pour le "+ h.getDate());
        }
    }

    // Méthode pour libérer le tuteur et indiquer qu'il est disponible
    public void libererTuteurParHoraire(IHoraire h) throws RemoteException {
        System.out.println("Le tuteur " + this.getNom() + " est libéré pour le " + h.getDate());
        etudiantsAssociés.remove(h);
        h.setDisponible();
        this.notifierEtudiants(h);
    }

    // Méthode pour ajouter des horaires disponibles
    public void ajouterHoraireDisponible(IHoraire horaire) {
        horairesDisponibles.add(horaire);
    }
    public void ajouterTarif(String matiere, Double prix){
        tarifParMatiere.put(matiere,prix);
    }
    public List<IEtudiant> getListeAttente(){
        return this.listeAttente;
    }
}
