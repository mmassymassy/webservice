package classes;

import shared.IHoraire;
import shared.IListeAttente;
import shared.ITask;
import shared.ITuteur;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ListeAttente extends UnicastRemoteObject implements Serializable, IListeAttente {
    private List<ITask> fileAttente;

    public ListeAttente() throws RemoteException{
        this.fileAttente = new ArrayList<ITask>();
    }

    // Ajouter un étudiant à la liste d'attente
    public void ajouterEnAttente(ITask t) {
        fileAttente.add(t);
    }

    // Retirer le premier étudiant de la liste d'attente
    public ITask retirerDeLaListe() {
        return fileAttente.remove(0);
    }

    // Vérifier si la liste d'attente est vide
    public boolean estVide() {
        return fileAttente.isEmpty();
    }

    @Override
    public void notifier(IHoraire h, ITuteur t) throws RemoteException {
        for(ITask e:fileAttente){
            e.getEtudiant().addNotification("Le tuteur "+t.getNom() + " est désormais disponible pour  le"+h.getJour());
        }
    }

    public ITask getNextTask(IHoraire h) throws RemoteException {
        int i = 0;
        for (ITask t: fileAttente){
            i++;
            if(t.getHoraire().equals(h)){
                fileAttente.remove(i);
                return t;
            }
        }
        return null;
    }

    public String getListeAttente() throws RemoteException {
        String text = "";
        for(ITask t: fileAttente){
            text += t.getMatiere()+":"+t.getEtudiant().getNom()+":"+t.getHoraire();
        }
        return text;

    }
}
