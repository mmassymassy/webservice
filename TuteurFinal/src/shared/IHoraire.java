package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHoraire extends Remote {

    public String getMatiere() throws RemoteException;
    public String getDate() throws RemoteException;

    public int getHeureDebut() throws RemoteException;
    public int getHeureFin() throws RemoteException;
    public void setMatiere(String m) throws RemoteException;
    public void setIndisponible() throws RemoteException;
    public void setDisponible() throws RemoteException;
    public boolean estDisponible() throws RemoteException;
}
