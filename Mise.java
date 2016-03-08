package siteParis;

//import java.util.Collection;


public class Mise {

	
	private Joueur joueur;
	
	private long mise;
	
	private Competition competitionCours;
	
	private Competiteur vainqueurEnvisager;
		
		
	public Mise(Joueur joueur,Competition compCours, long mise, Competiteur vainqueur){
      
      this.joueur=joueur;
      competitionCours=compCours;
      this.mise=mise;
      vainqueurEnvisager= vainqueur;
    
      
	}
   
   public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}	 
   public Joueur getJoueur(){
      return joueur;
   }
   
   public Competition getCompetition(){
      return competitionCours;
   }
   public void setCompetition(Competition c){
      competitionCours=c;
   }

   public long getMise(){
      return mise; 
   }
   public void setMise(long m){
      mise=m;
   }
   
   public Competiteur getVainqueur(){
      return vainqueurEnvisager;
   }
   public void setVainqueur(Competiteur v){
      vainqueurEnvisager=v;
   }
   
   public static void main (String[] args){
	   try{
		   DateFrancaise d1=new DateFrancaise(30,10,2015);
		   String[] competiteurs={"david", "lise", "anas", "juanjo"};
		   Competition cmp1=new Competition("Premier",d1);
		   cmp1.ajouterCompetiteur(competiteurs);
		   
		   Joueur j1= new Joueur("Esme","Kallejon","Filfu",0);
		   Joueur j2= new Joueur("E","K","Filfu",0);
		   Joueur j3= new Joueur("E","K","Fil",50);
		   long i=50;
		   
		   Mise m1= new Mise(j1, cmp1, i, cmp1.rechercherVainqueur("juanjo"));
		   Mise m2= new Mise(j1, cmp1, i, cmp1.rechercherVainqueur("juajo"));
		   //Mise m3= new Mise(j1, cmp1, i, cmp1.rechercherVainqueur("juanjo"));
		   
		   System.out.println(j1.equals(j2));
		   System.out.println(j1.equals(j3));
		   
		   System.out.println(m2.getVainqueur());
		   System.out.println(m1.getVainqueur());
		   
		   System.out.println(m2.getMise());
		   m2.setVainqueur(cmp1.rechercherVainqueur("lise"));
		   System.out.println(m2.getVainqueur());
		   
		   System.out.println(m2.getCompetition());
		   
	   }
	   catch(DateFrancaiseException d){
		   System.out.println("Date incorrecte");
	   }
	   
	   
   }
  
   
   
   
}
