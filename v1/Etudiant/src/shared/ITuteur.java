package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ITuteur extends IUtilisateur, Remote {
	public Map<String,Double> getTarifParMatire() throws RemoteException;

    public List<String> getDomainesExpertise()throws RemoteException;

    public boolean estDisponible() throws RemoteException;

    public Map<String,IEtudiant> getEtudiantsAssociés() throws RemoteException;
    public List<IHoraire> getHorairesDisponibles() throws RemoteException;

    // Méthode pour associer un étudiant au tuteur
    public void associerEtudiant(String matiere,IHoraire h,IEtudiant etudiant) throws RemoteException ;

    // Méthode pour libérer le tuteur et indiquer qu'il est disponible
    public void libererTuteurParHoraire(IHoraire h) throws RemoteException;
    // Méthode pour ajouter des horaires disponibles
    public void ajouterHoraireDisponible(IHoraire horaire) throws RemoteException;
    public void ajouterTarif(String matiere, Double prix) throws RemoteException;
    public IListeAttente getListeAttente() throws RemoteException;

}
