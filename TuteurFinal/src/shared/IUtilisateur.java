package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUtilisateur extends Remote {
	public String getNom() throws RemoteException;
	public String getEmail() throws RemoteException;
	public String getPassword() throws RemoteException;
	public void setNom(String n) throws RemoteException;
	public void setEMail(String e) throws RemoteException;
	public void setPassword(String p) throws RemoteException;

}
