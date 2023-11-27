import classes.Tuteur;
import shared.Horaire;
import shared.IHoraire;
import shared.IPlateforme;
import shared.ITuteur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IPlateforme plateform;
        plateform = (IPlateforme) Naming.lookup("plateforme");

        //creer les domaines d'experience du tuteur
        List<String> domaines = new ArrayList<String>();
        domaines.add("Computer science");
        domaines.add("Math");

        //creation et inscription de tuteur1 dans la plateforme
        ITuteur t1 = new Tuteur("tuteur1","t1@gm.com","massi1999",domaines);
        plateform.inscrireTuteur(t1);

        //creation et inscription de tuteur2 dans la plateforme
        domaines.add("Python");
        ITuteur t2 = new Tuteur("tuteur2","t2@gm.com","massi1999",domaines);
        plateform.inscrireTuteur(t2);

        //creation des horaires de disponibilités
        IHoraire h1 = new Horaire("","17/06/2024",10,14);
        IHoraire h2 = new Horaire("","18/06/2024",10,14);

        t1.ajouterHoraireDisponible(h1);
        t2.ajouterHoraireDisponible(h2);


    }
}