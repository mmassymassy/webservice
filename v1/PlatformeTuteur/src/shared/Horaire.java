package shared;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Horaire extends UnicastRemoteObject implements IHoraire, Serializable {
    private String jour;
    private int heureDebut;
    private int minuteDebut;
    private int heureFin;
    private int minuteFin;
    private boolean disp;

    public Horaire(String jour, int heureDebut, int minuteDebut, int heureFin, int minuteFin) throws RemoteException {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.minuteDebut = minuteDebut;
        this.heureFin = heureFin;
        this.minuteFin = minuteFin;
        disp = true;
        
    }

    public String getJour() {
        return jour;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public int getMinuteDebut() {
        return minuteDebut;
    }
    public String getHoraireDebut() {
    	return heureDebut+":"+minuteDebut;
    }
    public String getHoraireFin() {
    	return heureFin+":"+minuteFin;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public int getMinuteFin() {
        return minuteFin;
    }
    public void setIndisponible(){
        this.disp = false;
    }

    public boolean estDisponible(){
        return this.disp;
    }
    public String toString() {
        return jour + " de " + heureDebut + ":" + minuteDebut + " à " + heureFin + ":" + minuteFin;
    }
}
