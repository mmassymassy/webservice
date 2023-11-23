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

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IPlateforme plateforme;
        plateforme = (IPlateforme) Naming.lookup("plateforme");

        //creation de l'etudiant
        IEtudiant e1 = new Etudiant("et1","et1@gm.com","massi1999","master 1");
        IEtudiant e2 = new Etudiant("et2","et2@gm.com","massi1999","master 2");

        //inscrire l'etudiant a la plateforme
        plateforme.inscrireEtudiant(e1);
        plateforme.inscrireEtudiant(e2);

        List<ITuteur> tuteurList = plateforme.getTuteursParMatiere("Math");
        System.out.println("Les tuteurs de Math");
        for (ITuteur t : tuteurList){
            System.out.println(t.getNom());
        }
        IHoraire h1 = tuteurList.get(0).getHorairesDisponibles().get(0);
        tuteurList.get(0).associerEtudiant(h1,e1,"Math");
        tuteurList.get(0).associerEtudiant(h1,e2,"Math");

        tuteurList.get(0).libererTuteurParHoraire(h1);





    }
}