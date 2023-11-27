package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHoraire extends Remote {
    public String getJour() throws RemoteException;
    public int getHeureDebut() throws RemoteException;
    public int getMinuteDebut() throws RemoteException;
    public String getHoraireDebut() throws RemoteException;
    public String getHoraireFin() throws RemoteException;
    public int getHeureFin() throws RemoteException;
    public int getMinuteFin() throws RemoteException;
    public void setIndisponible() throws RemoteException;
    public boolean estDisponible() throws RemoteException;
}
