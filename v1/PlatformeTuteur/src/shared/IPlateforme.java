package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlateforme extends Remote {
    public void inscrireTuteur(ITuteur tuteur) throws RemoteException;

    // Méthode pour inscrire un étudiant à la plateforme
    public void inscrireEtudiant(IEtudiant e) throws RemoteException ;
    public List<ITuteur> getTuteursParMatiere(String m) throws RemoteException;

    // Méthode pour gérer la demande d'un étudiant
    public void demanderTuteur(IEtudiant e, String m, ITuteur t, IHoraire h) throws RemoteException;
}
