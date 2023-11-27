package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IListeAttente extends Remote{
    public void ajouterEnAttente(ITask t) throws RemoteException;;
    // Retirer le premier étudiant de la liste d'attente
    public ITask retirerDeLaListe() throws RemoteException;;
    // Vérifier si la liste d'attente est vide
    public boolean estVide() throws RemoteException;;
    public void notifier(IHoraire h, ITuteur t) throws RemoteException;
    public String getListeAttente() throws RemoteException;
}
