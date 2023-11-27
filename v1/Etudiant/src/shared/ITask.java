package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITask extends Remote {
    public void setMatiere(String m) throws RemoteException;
    public void setHoraire(IHoraire h) throws RemoteException;
    public String getMatiere() throws RemoteException;
    public IHoraire getHoraire() throws RemoteException;

    public IEtudiant getEtudiant() throws RemoteException;
}
