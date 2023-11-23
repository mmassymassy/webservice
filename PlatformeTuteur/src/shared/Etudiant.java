package shared;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Etudiant extends Utilisateur implements IEtudiant, Serializable {
	private String niveau;
    private Map<IHoraire,ITuteur> tuteurs;
	private List<String> notifications;


	public Etudiant(String n, String e, String p,String niv) throws RemoteException {
		super(n, e, p);
		this.niveau = niv;
		this.tuteurs = new HashMap<IHoraire,ITuteur>();  //<Matiere,Tuteur>
		this.notifications = new LinkedList<String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<IHoraire,ITuteur> getTuteurs() {
		// TODO Auto-generated method stub
		return this.tuteurs;
	}

	@Override
	public void setTuteur(IHoraire h, ITuteur t, String matiere) throws RemoteException {
		// TODO Auto-generated method stub
		t.associerEtudiant(h,this,matiere);
		this.tuteurs.put(h, t);
		
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
