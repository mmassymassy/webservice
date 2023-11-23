package main;

import classes.Plateforme;
import classes.Tuteur;
import shared.Horaire;
import shared.IHoraire;
import shared.ITuteur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1099); //creation de registry avec un num du port

        Plateforme plateforme = new Plateforme();
        List<String> exp = new ArrayList<String>();
        exp.add("Python"); exp.add("Math");

        List<String> exp2 = new ArrayList<String>();
        exp2.add("Math"); exp2.add("C");

        ITuteur tuteur1 =  new Tuteur("massi1","massi1@g.com","massi1999",exp);
        ITuteur tuteur2 =  new Tuteur("massi2","massi2@g.com","massi1999",exp);
        ITuteur tuteur3 =  new Tuteur("massi3","massi3@g.com","massi1999",exp2);

        IHoraire h1 = new Horaire("","17/06/2024",10,12);
        IHoraire h2 = new Horaire("","17/06/2024",14,18);
        IHoraire h3 = new Horaire("","19/06/2024",8,10);

        IHoraire h4 = new Horaire("","20/06/2024",8,10);




        plateforme.inscrireTuteur(tuteur1);
        plateforme.inscrireTuteur(tuteur2);
        plateforme.inscrireTuteur(tuteur3);

        tuteur1.ajouterHoraireDisponible(h4);


        tuteur2.ajouterHoraireDisponible(h1);
        tuteur2.ajouterHoraireDisponible(h2);


        tuteur3.ajouterHoraireDisponible(h2);
        tuteur3.ajouterHoraireDisponible(h3);

        //Calculator calc = new Calculator(); //creation du calculatrice
        Naming.rebind("plateforme", plateforme); // enregistrement du l'objet calculator dans le registry sous les nom calculator


    }
}