package siteParis;
//import java.util.*;
//import java.util.Arrays;
//import java.util.List;
import java.util.LinkedList;


public class Competition {

	private LinkedList<Competiteur> competiteurs;
	
	//private LinkedList<Mise> mise;
	
	private DateFrancaise dateCloture ;
	
    private String name;
   
	    
   public Competition(String name, DateFrancaise dateCloture){
   
   this.name=name;
   this.dateCloture=dateCloture;
   competiteurs= new LinkedList<Competiteur>();
   //mise= new LinkedList<Mise>();
 	}
   public String getName(){
	   return name;
   }
   
   public void setName(String name){
	   this.name=name;
   }
    
   public DateFrancaise getDateCloture() {
		return dateCloture;
	}
   public void setDateCloture(DateFrancaise dateFrancaise) {
		this.dateCloture = dateFrancaise;
	}
	
   public boolean rechercherCompetition(String name, DateFrancaise dateCloture){
         if(this.name.equals(name)){
        	 if(this.dateCloture.equals(dateCloture))return true;
              }
         
         return false; 
		}
  
   public LinkedList<Competiteur> getCompetiteurs(){
  		return competiteurs;
   }
   public void ajouterCompetiteur(String [] name){
         
         for(String nm : name){
         
         Competiteur c1= new Competiteur(nm);
         competiteurs.add(c1);
         
         }
        
	}
	
	public boolean competiteurExistant (String competiteur){
		for(Competiteur c : competiteurs){
			if(c.getName().equals(competiteur)) return true;
		}
		return false;
	}
					
	public Competiteur rechercherVainqueur(String name){
		for(Competiteur c : competiteurs){
			if(c.getName().equals(name)) return c;
		}
		return null;
	}
	
	public LinkedList<String> toList(){
        LinkedList<String> r =new LinkedList<String>();
        r.add(name);
        r.add(dateCloture.toString());
        return r;
     }
	
	public String toString(){
		String str="Competition: "+name+ " -date de cloture: "+dateCloture;
		return str;
	}
	
	public static void main (String[] args){
		try{
			DateFrancaise d1=new DateFrancaise(30,10,2015);
			String [] names={"david", "lise", "anas", "juanjo"};
			
			Competition Liga=new Competition("Liga",d1);
			
			Liga.ajouterCompetiteur(names);
			System.out.println(Liga.competiteurExistant("lise"));
			System.out.println(Liga.rechercherVainqueur("anas"));
			System.out.println(Liga.rechercherCompetition("Liga", d1));
			System.out.println(Liga.rechercherCompetition("Loga", d1));
			System.out.println(Liga.getName());
			System.out.println(Liga.toString());
			System.out.println(Liga.getCompetiteurs());
		}
		catch(DateFrancaiseException d)
		{
			System.out.println("Date incorrecte");
		}
		
	}

}