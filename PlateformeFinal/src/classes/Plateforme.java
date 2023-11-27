package classes;

import shared.IEtudiant;
import shared.IHoraire;
import shared.IPlateforme;
import shared.ITuteur;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plateforme extends UnicastRemoteObject implements IPlateforme, Serializable {
    private Map<String, ITuteur> tuteurs;
    private Map<String, IEtudiant> etudiants;
    public Plateforme() throws RemoteException {
        this.tuteurs = new HashMap<String,ITuteur>();
        this.etudiants = new HashMap<String,IEtudiant>();
    }

    // Méthode pour ajouter un tuteur à la plateforme
    public void inscrireTuteur(ITuteur tuteur) throws RemoteException {
        System.out.println("Inscription de tuteur " + tuteur.getNom() );
        tuteurs.put(tuteur.getEmail(), tuteur);
    }

    // Méthode pour inscrire un étudiant à la plateforme
    public void inscrireEtudiant(IEtudiant e) throws RemoteException {
        IEtudiant check = etudiants.get(e.getEmail());
        if(check != null){
            System.out.println("L'étudiant est déja inscri : " + e.getNom() );
            return;
        }
        System.out.println("Inscription de l'étudiant " + e.getNom() );
        etudiants.put(e.getEmail(), e);
    }
    public List<ITuteur> getTuteursParMatiere(String m) throws RemoteException {
        System.out.println("Recherche de tuteurs pour la matière " + m);
        
        ArrayList<ITuteur> tpm = new ArrayList<ITuteur>();
        for(ITuteur t : tuteurs.values()){
            for(String mat : t.getDomainesExpertise()){
                if(mat.startsWith(m)){
                    tpm.add(t);
                    System.out.println("Tuteur trouvé : " + t.getNom());
                }
            }
        }
        System.out.println("Nombre de tuteurs trouvés : " + tpm.size());

        return tpm;
    }

    // Méthode pour gérer la demande d'un étudiant
    public void demanderTuteur(IEtudiant e, String m, ITuteur t, IHoraire h) throws RemoteException {
        System.out.println("Demande de tuteur pour" + e.getNom() + ", matière " + m + " avec le tuteur " + t.getNom()+ " à l'horaire " + h.getDate() );
        t.associerEtudiant(h,e,m);
    }

    public ITuteur login(String email,String mdp) throws RemoteException {
        ITuteur t = tuteurs.get(email);
        System.out.println("Tryuing to connect "+t.getNom());
        if(t.getPassword().equals(mdp)){
            System.out.println("Connexion de "+t.getNom());
            return t;
        }
        return null;
    }
    public IEtudiant loginEtudiant(String email,String mdp) throws RemoteException {
        IEtudiant e = etudiants.get(email);
        System.out.println("Tryuing to connect "+e.getNom());
        if(e.getPassword().equals(mdp)){
            System.out.println("Connexion de "+e.getNom());
            return e;
        }
        return null;
    }
    public Map<String,ITuteur> getTuteurs() {
        return this.tuteurs;
    }



}
