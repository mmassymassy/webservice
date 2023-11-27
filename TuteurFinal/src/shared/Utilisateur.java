package shared;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Utilisateur extends UnicastRemoteObject implements IUtilisateur, Serializable {
	private String nom;
	private String email;
	private String password;
	public Utilisateur(String n,String e,String p) throws RemoteException {
		this.nom = n;
		this.email = e;
		this.password = p;
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public void setNom(String n) {
		// TODO Auto-generated method stub
		 this.nom=n;
	}
	@Override
	public void setEMail(String e) {
		// TODO Auto-generated method stub
		 this.email=e;
	}
	@Override
	public void setPassword(String p) {
		// TODO Auto-generated method stub
		 this.password=p;
	}

}
