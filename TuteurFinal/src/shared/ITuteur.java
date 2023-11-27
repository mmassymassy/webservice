package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ITuteur extends IUtilisateur, Remote {

    public Map<IHoraire, IEtudiant> getEtudiantsAssociés() throws RemoteException;

    public List<IHoraire> getHorairesDisponibles() throws RemoteException;

    public Map<String, Double> getTarifParMatire() throws RemoteException;

    // Méthode pour associer un étudiant au tuteur
    public void associerEtudiant(IHoraire h, IEtudiant etudiant, String matiere) throws RemoteException;

    public void notifierEtudiants(IHoraire h) throws RemoteException;
    // Méthode pour libérer le tuteur et indiquer qu'il est disponible
    public void libererTuteurParHoraire(IHoraire h) throws RemoteException;

    // Méthode pour ajouter des horaires disponibles
    public void ajouterHoraireDisponible(IHoraire horaire) throws RemoteException;
    public void ajouterTarif(String matiere, Double prix) throws RemoteException;
    public List<IEtudiant> getListeAttente() throws RemoteException;

    List<String> getDomainesExpertise() throws RemoteException;
}
