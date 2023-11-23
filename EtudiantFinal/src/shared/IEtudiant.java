package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IEtudiant extends IUtilisateur, Remote {

	public Map<IHoraire,ITuteur> getTuteurs() throws RemoteException;
	public void setTuteur(IHoraire h, ITuteur t, String matiere) throws RemoteException;
	public void addNotification(String n) throws RemoteException ;
	public List<String> getNotifications() throws RemoteException;
}
