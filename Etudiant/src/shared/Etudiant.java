package shared;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Etudiant extends Utilisateur implements IEtudiant, Serializable {
	private String niveau;
    private Map<String,ITuteur> tuteurs;
	private List<String> notifications;


	public Etudiant(String n, String e, String p,String niv) throws RemoteException {
		super(n, e, p);
		this.niveau = niv;
		this.tuteurs = new HashMap<String,ITuteur>();  //<Matiere,Tuteur>
		this.notifications = new LinkedList<String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String,ITuteur> getTuteurs() {
		// TODO Auto-generated method stub
		return this.tuteurs;
	}

	@Override
	public void setTuteur(String matiere, ITuteur t) {
		// TODO Auto-generated method stub
		this.tuteurs.put(matiere, t);
		
	}

	@Override
	public void addNotification(String n) throws RemoteException {
		this.notifications.add(n);
	}

	@Override
	public List<String> getNotifications() throws RemoteException {
		return this.notifications;
	}


}
