package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IEtudiant extends IUtilisateur, Remote {
	public Map<String,ITuteur>  getTuteurs() throws RemoteException;
	public void setTuteur(String matiere,ITuteur t) throws RemoteException;
	public void addNotification(String n) throws RemoteException;
	public List<String> getNotifications() throws RemoteException;
}
