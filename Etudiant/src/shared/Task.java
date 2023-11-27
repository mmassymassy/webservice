package shared;

import java.io.Serializable;

public class Task implements ITask , Serializable {
    private String matiere;
    private IHoraire horaire;
    private IEtudiant etudiant;

    public Task(String m,IHoraire h,IEtudiant e) {
        matiere = m;
        horaire = h;
        etudiant = e;
    }
    public void setMatiere(String m){
        this.matiere = m;
    }
    public void setHoraire(IHoraire h){
        this.horaire = h;
    }
    public void setEtudiant(IEtudiant e){
        this.etudiant = e;
    }
    public String getMatiere(){
        return this.matiere;
    }
    public IHoraire getHoraire(){
        return this.horaire;
    }
    public IEtudiant getEtudiant(){
        return this.etudiant;
    }
}
