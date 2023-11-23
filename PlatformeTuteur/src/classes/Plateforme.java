package classes;

import shared.*;

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
        tuteurs.put(tuteur.getEmail(), tuteur);
    }

    // Méthode pour inscrire un étudiant à la plateforme
    public void inscrireEtudiant(IEtudiant e) throws RemoteException {
        etudiants.put(e.getEmail(), e);
    }
    public List<ITuteur> getTuteursParMatiere(String m) throws RemoteException {
        ArrayList<ITuteur> tpm = new ArrayList<ITuteur>();
        for(ITuteur t : tuteurs.values()){
            for(String mat : t.getDomainesExpertise()){
                if(mat.equals(m)){
                    tpm.add(t);
                }
            }
        }
        return tpm;
    }

    // Méthode pour gérer la demande d'un étudiant
    public void demanderTuteur(IEtudiant e, String m, ITuteur t, IHoraire h) throws RemoteException {
        t.associerEtudiant(h,e,m);
    }


}
