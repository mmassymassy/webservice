import classes.Etudiant;
import shared.IEtudiant;
import shared.IHoraire;
import shared.IPlateforme;
import shared.ITuteur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IPlateforme plateforme;
        plateforme = (IPlateforme) Naming.lookup("plateforme");

        //creation de l'etudiant
        IEtudiant e1 = new Etudiant("et1","et1","et1","master 1");
        IEtudiant e2 = new Etudiant("et2","et2","et2","master 2");

        //inscrire l'etudiant a la plateforme
        plateforme.inscrireEtudiant(e1);
        plateforme.inscrireEtudiant(e2);

       /* Map<String,ITuteur> tuteurList = plateforme.getTuteurs();
        ITuteur t = tuteurList.get("massi1");
        IHoraire h = t.getHorairesDisponibles().get(0);

        t.associerEtudiant(h,e1,"math");
        t.associerEtudiant(h,e2,"math");*/





    }
}