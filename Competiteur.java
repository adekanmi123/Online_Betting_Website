package siteParis;


public class Competiteur {

	/**
	 * @uml.property  name="name"
	 */
	private String name;
	
   public Competiteur(String name){
      
      this.name=name;
      
		}
	
   public String getName(){
      return name;
   }
   
   public void setName(String n){
      this.name=n;
   }
   public String toString(){
	   String str= "Competiteur: " +name;   
	   return str;
	   }
   
   public static void main(String args[]){
      
      Competiteur c1= new Competiteur("Fil");
      System.out.println("Le nom du competiteur est: "+c1.getName());
      
      c1.setName("Jordi");
      System.out.println("Le nouveau nom du competiteur est: "+c1.getName());
      
      System.out.println(c1.toString());
   }
}
