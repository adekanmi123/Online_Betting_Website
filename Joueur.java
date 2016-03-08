package siteParis;

//import java.util.Collection;
import java.util.LinkedList;


public class Joueur {

   
	private String passwordJoueur;
   
	private String firstName;
	
	private String name;
  
	private String pseudo;
	
	private long nombreDeJeton;
	
	private long mise;
	
	
		
	public Joueur(String name, String firstName, String pseudo,long nombreDeJeton){
      
      this.name=name;
      this.firstName=firstName;
      this.pseudo=pseudo;
      this.nombreDeJeton=nombreDeJeton;
      passwordJoueur=null;
      this.mise = 0;
      
      
		}
      
	public void setName(String n){
  	  this.name=n;
    }
    public String getName(){
         return name;
      }
      
    public void setfirstName(String n){
  	  this.firstName=n;
    }
      public String getfirstName(){
         return firstName;
      }
      
      public void setPseudo(String n){
    	  this.pseudo=n;
      }
      public String getPseudo(){
         return pseudo;
      }
      
      public long getMise(){
    	  return mise;  	  
      }
      public void addMise(long m){
    	  this.mise +=m;
      }
      public void debiterMise(long m){
    	  this.mise -=m;
      }
      
      public long getNombreDeJeton() {
    	  return nombreDeJeton; 
      } 
      public void setNombreDeJeton(long j) {
    	  this.nombreDeJeton =j ;
      }
      
      public String getPass(){
    	  return passwordJoueur;
      }
      public void setPass(String s){
    	  this.passwordJoueur=s;
      }
      
      public void addJetons(long j){
    	  this.nombreDeJeton +=j;
      }
      public void debiterJetons(long j){   	 
    	  this.nombreDeJeton -=j;
    	  
      }
     
      
      public boolean equals (Object o){
         if(o instanceof Joueur ){
        	 Joueur j = (Joueur) o;
         	if ((j.getPseudo().equals(this.pseudo) && (j.getName().equals(this.name) && j.getfirstName().equals(this.firstName)))){
         		return true ;
         	}
         	return false;
         }
         return false;
      }
      
      
      public LinkedList<String> toList(){
         LinkedList<String> r =new LinkedList<String>();
         r.add(name);
         r.add(firstName);
         r.add(pseudo);
         r.add(Long.toString(nombreDeJeton));
         r.add(Long.toString(mise));
         return r;
      }

public static void main (String [] args){
    	  
    	  try{
    		  Joueur j1= new Joueur("Esme","Kallejon","Filfu",0);
        	  Joueur j2= new Joueur("Esme","Kallejon","Filfu",0);
        	  Joueur j3= new Joueur("E","K","Fil",0);
        	  
        	  System.out.println(j1.equals(j2));
        	  System.out.println(j3.getPass());
        	  System.out.println(j1.getMise());
        	  
        	  System.out.println("On va faire une mise");
        	  
        	  String[] competiteurs={"david", "lise", "anas", "juanjo"};
        	  DateFrancaise d1=new DateFrancaise(30,10,2015);
        	  
        	  Competition Liga1=new Competition("Premier",d1);
        	  Liga1.ajouterCompetiteur(competiteurs);
        	         	          	  
        	  Mise m1= new Mise(j1,Liga1, 50, Liga1.rechercherVainqueur("juanjo"));
        	  Mise m2= new Mise(j1,Liga1, 50, Liga1.rechercherVainqueur("anas"));
        	  
        	  System.out.println(m1.getMise());
        	  System.out.println(m2.toString());
        	  System.out.println(j1.getMise());
        	  
        	  System.out.println(j1.toList());
        	  System.out.println(j3.toList());
    		  
        	  System.out.println(j1.getNombreDeJeton());
        	  
    	  }
    	  catch(DateFrancaiseException d)
  		{
  			System.out.println("Date incorrecte");
  		}
    	  
    	  
    	  
    	  
      }

}
