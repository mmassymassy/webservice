import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import shared.*;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        IPlateforme platform;
        platform = (IPlateforme) Naming.lookup("plateforme");

        IEtudiant et1 = new Etudiant("jamel","jamel@g.com","jamel1999","master 1");
        IEtudiant et2 = new Etudiant("jamel2","jamel2@g.com","jamel1999","master 1");

        IEtudiant et3 = new Etudiant("jamel3","jamel3@g.com","jamel1999","master 1");
        IEtudiant et4 = new Etudiant("jamel4","jamel4@g.com","jamel1999","master 1");


        platform.inscrireEtudiant(et1);
        platform.inscrireEtudiant(et2);
        IHoraire h = null;

        List<ITuteur> tutMath = platform.getTuteursParMatiere("Math");
        int i = 0;
        for (ITuteur tut:tutMath){
            if(i==0){
                for( IHoraire hh : tut.getHorairesDisponibles()){
                    tut.associerEtudiant("Math",hh,et1 ); //remplir les dispo de tuteur en lui associant le mm etudiant pour tt les chrénos
                    h = hh;
                }
               // tut.associerEtudiant("Math",tut.getHorairesDisponibles().get(1),et2 );
                //tut.associerEtudiant("Math",tut.getHorairesDisponibles().get(0),et4 );//les horaires sont tous pris, ajout à la liste d'attente
                tut.associerEtudiant("Math",h,et2 );
            }

            if(i==1){
                System.out.println("######### tuteur : "+tut.getNom());
                tut.associerEtudiant("Math",h,et1 );

            }
            System.out.println("Disbonibilité de tuteur "+tut.getNom());
            for(IHoraire horaire:tut.getHorairesDisponibles()){
                System.out.println(horaire.getJour()+":"+horaire.getHoraireDebut()+":"+horaire.estDisponible());
            }
            i++;
        }

        System.out.println("Liste des etudiants associés a chaque tuteur");

        for (ITuteur tut:tutMath){
            System.out.println("Tuteur " + tut.getNom() +" on lui associer :");
            for(IEtudiant e : tut.getEtudiantsAssociés().values()){
                System.out.println(e.getNom());
            }
        }

        System.out.println("Liste d'attente associés a chaque tuteur");

        for (ITuteur tut:tutMath){
            System.out.println("Tuteur " + tut.getNom() +" : Liste d'attente :");
                System.out.println(tut.getListeAttente().estVide());

        }



    }
}