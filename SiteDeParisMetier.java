package siteParis;


import java.util.*;

/**
 * 
 * @author Bernard Prou et Julien Mallet
 * <br><br>
 * La classe qui contient toutes les méthodes "Métier" de la gestion du site de paris. 
 * <br><br>
 * Dans toutes les méthodes :
 * <ul>
 * <li>un paramètre de type <code>String</code> est invalide si il n'est pas instancié.</li>
 *  <li>pour la validité d'un password de gestionnaire et d'un password de joueur :
 * <ul>
 * <li>       lettres et chiffres sont les seuls caractères autorisés </li>
 * <li>       il doit avoir une longueur d'au moins 8 caractères </li>
 * </ul></li>       
 * <li>pour la validité d'un pseudo de joueur  :
 * <ul>
 * <li>        lettres et chiffres sont les seuls caractères autorisés  </li>
 * <li>       il doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un prénom de joueur et d'un nom de joueur :
 *  <ul>
 *  <li>       lettres et tiret sont les seuls caractères autorisés  </li>
 *  <li>      il  doit avoir une longueur d'au moins 1 caractère </li>
 * </ul></li>
 * <li>pour la validité d'une compétition  :       
 *  <ul>
 *  <li>       lettres, chiffres, point, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      elle  doit avoir une longueur d'au moins 4 caractères</li>
 * </ul></li>       
 * <li>pour la validité d'un compétiteur  :       
 *  <ul>
 *  <li>       lettres, chiffres, trait d'union et souligné sont les seuls caractères autorisés </li>
 *  <li>      il doit avoir une longueur d'au moins 4 caractères.</li>
 * </ul></li></ul>
 */

public class SiteDeParisMetier {


    private LinkedList<Competition> competitions;

    private String passwordGestionnaire;
     
    private LinkedList<Joueur> joueurs;
   
    private LinkedList<Mise> mises;


    
   public SiteDeParisMetier(String passwordGestionnaire) throws MetierException {
   
		
		validitePasswordGestionnaire(passwordGestionnaire);
						
		this.passwordGestionnaire = passwordGestionnaire;
		joueurs=new LinkedList<Joueur>();
		competitions=new LinkedList<Competition>();
		mises= new LinkedList<Mise>();   
	}





	// Les méthodes du gestionnaire (avec mot de passe gestionnaire)



	/**
	 * inscrire un joueur. 
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée  
	 * si le <code>passwordGestionnaire</code> proposé est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws JoueurExistantException   levée si un joueur existe avec les mêmes noms et prénoms ou le même pseudo.
	 * @throws JoueurException levée si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
	 * 
	 * @return le mot de passe (déterminé par le site) du nouveau joueur inscrit.
	 */
	public String inscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurExistantException, JoueurException {
       
	
		//MetierException
       validitePasswordGestionnaire(passwordGestionnaire);
       if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
        
       //JoueurException
       validiteName(nom);
       validiteFirstName(prenom);
       validitePseudo(pseudo);
        
     //JoueurExistanException
       for(Joueur j:joueurs)
       if(j.getPseudo().equals(pseudo) || j.getName().equals(nom) && j.getfirstName().equals(prenom)) throw new JoueurExistantException();
       Joueur joueur1 = new Joueur(nom,prenom,pseudo,0);
       String unMotdePasseUnique=this.createPassword();
       joueur1.setPass(unMotdePasseUnique);
       joueurs.add(joueur1);
	
       return unMotdePasseUnique;
         
	}

	/**
	 * supprimer un joueur. 
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec le même <code>nom</code>, <code>prenom</code>  et <code>pseudo</code>.
	 * @throws JoueurException levée 
	 * si le joueur a des paris en cours,
	 * si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
	 * 
	 * @return le nombre de jetons à rembourser  au joueur qui vient d'être désinscrit.
	 * 
	 */
	public long desinscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurInexistantException, JoueurException {
		
		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		
		 //JoueurException
	     validiteName(nom);
	     validiteFirstName(prenom);
	     validitePseudo(pseudo);
		
	     //JoueurInexistantException
	     Joueur j= giveJoueur(nom, prenom, pseudo);
	     if(j==null)throw new JoueurInexistantException();	     
	     
	     long jetons = j.getNombreDeJeton();
	     
	     joueurs.remove(j);  
     
	     return jetons;
	}



	/**
	 * ajouter une compétition.  
	 * 
	 * @param competition le nom de la compétition
	 * @param dateCloture   la date à partir de laquelle il ne sera plus possible de miser  
	 * @param competiteurs   les noms des différents compétiteurs de la compétition 
	 * @param passwordGestionnaire  le password du gestionnaire du site 
	 * 
	 * @throws MetierException levée si le tableau des
	 * compétiteurs n'est pas instancié, si le
	 * <code>passwordGestionnaire</code> est invalide, si le
	 * <code>passwordGestionnaire</code> est incorrect.
	 * @throws CompetitionExistanteException levée si une compétition existe avec le même nom. 
	 * @throws CompetitionException levée si le nom de la
	 * compétition ou des compétiteurs sont invalides, si il y a
	 * moins de 2 compétiteurs, si un des competiteurs n'est pas instancié,
	 * si deux compétiteurs ont le même nom, si la date de clôture 
	 * n'est pas instanciée ou est dépassée.
	 */
	public void ajouterCompetition(String competition, DateFrancaise dateCloture, String [] competiteurs, String passwordGestionnaire) throws MetierException, CompetitionExistanteException, CompetitionException  {
   
		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		if(competiteurs==null) throw new MetierException();
		
		//CompetitionException
		validiteCompetition(competition);
		
		if (dateCloture==null || dateCloture.estDansLePasse()) throw new CompetitionException();
		
		if (competiteurs.length<2) throw new CompetitionException();
		
		for(String competiteur : competiteurs){
			validiteCompetiteur(competiteur);
		}
		for(int i=0;i<competiteurs.length;i++){
			for(int j=0;j<competiteurs.length;j++){  				
				if(competiteurs[i]==competiteurs[j] && i!=j) throw new CompetitionException();
			}
		}
		
		//CompetitionExistanteException
		for(Competition c : competitions)
		if (c.getName().equals(competition)) throw new CompetitionExistanteException();
	
   
		Competition c1= new Competition(competition, dateCloture);
		c1.ajouterCompetiteur(competiteurs);
		competitions.add(c1);

	}



   
	/**
	 * solder une compétition vainqueur (compétition avec vainqueur).  
	 * 
	 * Chaque joueur ayant misé sur cette compétition
	 * en choisissant ce compétiteur est crédité d'un nombre de
	 * jetons égal à :
	 * 
	 * (le montant de sa mise * la somme des
	 * jetons misés pour cette compétition) / la somme des jetons
	 * misés sur ce compétiteur.
	 *
	 * Si aucun joueur n'a trouvé le
	 * bon compétiteur, des jetons sont crédités aux joueurs ayant
	 * misé sur cette compétition (conformément au montant de
	 * leurs mises). La compétition est "supprimée" si il ne reste
	 * plus de mises suite à ce solde.
	 * 
	 * @param competition   le nom de la compétition  
	 * @param vainqueur   le nom du vainqueur de la compétition 
	 * @param passwordGestionnaire  le password du gestionnaire du site 
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom.
	 * @throws CompetitionException levée 
	 * si le nom de la compétition ou du vainqueur est invalide, 
	 * si il n'existe pas de compétiteur du nom du vainqueur dans la compétition,
	 * si la date de clôture de la compétition est dans le futur.
	 * 
	 */	
	public void solderVainqueur(String competition, String vainqueur, String passwordGestionnaire) throws MetierException,  CompetitionInexistanteException, CompetitionException  {
		
		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		
		//CompetitionException
		validiteCompetition(competition);
		validiteCompetiteur(vainqueur);
				
		//CompetitionInexistanteException
		Competition comp= giveCompetition(competition);
		if(comp==null) throw new CompetitionInexistanteException();
				
		if(!comp.getDateCloture().estDansLePasse()) throw new CompetitionException();		
		if(!comp.competiteurExistant(vainqueur)) throw new CompetitionException();
		
		
		long totalCompetition=0;
		long totalCompetiteur=0;
		boolean Flag=false;
		
		for(Mise m : mises){
			
			if(m.getCompetition().getName().equals(comp.getName())){
				totalCompetition +=m.getMise();
				m.getJoueur().debiterMise(m.getMise());
				
				if(m.getVainqueur().getName().equals(vainqueur)){
					Flag=true;
					totalCompetiteur +=m.getMise();
					//m.getJoueur().addJetons((m.getMise()*totalCompetition)/totalCompetiteur);
				}
				
			}
		}
			
		if(Flag==true){
			for(Mise m : mises){
				if(m.getCompetition().getName().equals(comp.getName()) && m.getVainqueur().getName().equals(vainqueur)){
					m.getJoueur().addJetons((m.getMise()*totalCompetition)/totalCompetiteur);
				}
			}
		}
		else{
			for(Mise m : mises){
				if(m.getCompetition().getName().equals(comp.getName())){
					m.getJoueur().addJetons(m.getMise());
				}
			}
		}
		
		competitions.remove(comp);
		
		
	}



	/**
	 * créditer le compte en jetons d'un joueur du site de paris.
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param sommeEnJetons   la somme en jetons à créditer  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect,
	 * si la somme en jetons est négative.
	 * @throws JoueurException levée  
	 * si <code>nom</code>, <code>prenom</code>,  <code>pseudo</code> sont invalides.
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec les mêmes nom,  prénom et pseudo.
	 */
	public void crediterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws MetierException, JoueurException, JoueurInexistantException {
		
		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		if (sommeEnJetons<0) throw new MetierException();
		
		//JoueurException
	    validiteName(nom);
	    validiteFirstName(prenom);
	    validitePseudo(pseudo);
	    
	    //JoueurInexistantException
	    Joueur j= giveJoueur(nom, prenom, pseudo);
	    if(j==null) throw new JoueurInexistantException();
	        
	    j.setNombreDeJeton(j.getNombreDeJeton()+sommeEnJetons);
	    
	    		
	}


	/**
	 * débiter le compte en jetons d'un joueur du site de paris.
	 * 
	 * @param nom   le nom du joueur 
	 * @param prenom   le prénom du joueur   
	 * @param pseudo   le pseudo du joueur  
	 * @param sommeEnJetons   la somme en jetons à débiter  
	 * @param passwordGestionnaire  le password du gestionnaire du site  
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect,
	 * si la somme en jetons est négative.
	 * @throws JoueurException levée  
	 * si <code>nom</code>, <code>prenom</code>,  <code>pseudo</code> sont invalides 
	 * si le compte en jetons du joueur est insuffisant (il deviendrait négatif).
	 * @throws JoueurInexistantException   levée si il n'y a pas de joueur  avec les mêmes nom,  prénom et pseudo.
	 * 
	 */

	public void debiterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws  MetierException, JoueurInexistantException, JoueurException {

		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
		if (sommeEnJetons<0) throw new MetierException();
				
		//JoueurException
	    validiteName(nom);
	    validiteFirstName(prenom);
	    validitePseudo(pseudo);
	    
	    
		//JoueurInexistantException
	    Joueur j= giveJoueur(nom, prenom, pseudo);
		if(j==null) throw new JoueurInexistantException();
	    
		//JoueurException
	    //System.out.println(j.getNombreDeJeton()- sommeEnJetons);
		if((j.getNombreDeJeton()-sommeEnJetons)<0) throw new JoueurException();
	    
		j.debiterJetons(sommeEnJetons);

	     }
	



	/** 
	 * consulter les  joueurs.
	 * 
	 * @param passwordGestionnaire  le password du gestionnaire du site de paris 

	 * @throws MetierException   levée  
	 * si le <code>passwordGestionnaire</code>  est invalide,
	 * si le <code>passwordGestionnaire</code> est incorrect.
	 * 
	 * @return une liste de liste dont les éléments (de type <code>String</code>) représentent un joueur avec dans l'ordre : 
	 *  <ul>
	 *  <li>       le nom du joueur  </li>
	 *  <li>       le prénom du joueur </li>
	 *  <li>       le pseudo du joueur  </li>
	 *  <li>       son compte en jetons restant disponibles </li>
	 *  <li>       le total de jetons engagés dans ses mises en cours. </li>
	 *  </ul>
	 */
	public LinkedList <LinkedList <String>> consulterJoueurs(String passwordGestionnaire) throws MetierException {
		
		//MetierException
		validitePasswordGestionnaire(passwordGestionnaire);	
		if(!this.passwordGestionnaire.equals(passwordGestionnaire)) throw new MetierException();
	    
	    LinkedList <LinkedList <String>> totJoueurs=new LinkedList <LinkedList <String>>();
	    //LinkedList<String> s= new LinkedList<String>();
	    for(Joueur j:joueurs){
	    	totJoueurs.add(j.toList());
	    }
	    	//r.add(Long.toString(j.getNombreDeJeton()));
		return totJoueurs;
	}








	// Les méthodes avec mot de passe utilisateur



	/**
	 * miserVainqueur  (parier sur une compétition, en désignant un vainqueur).
	 * Le compte du joueur est débité du nombre de jetons misés.
	 * 
	 * @param pseudo   le pseudo du joueur  
	 * @param passwordJoueur  le password du joueur  
	 * @param miseEnJetons   la somme en jetons à miser  
	 * @param competition   le nom de la compétition  relative au pari effectué
	 * @param vainqueurEnvisage   le nom du compétiteur  sur lequel le joueur mise comme étant le  vainqueur de la compétition  
	 * 
	 * @throws MetierException levée si la somme en jetons est négative.
	 * @throws JoueurInexistantException levée si il n'y a pas de
	 * joueur avec les mêmes pseudos et password.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom. 
	 * @throws CompetitionException levée 
	 * si <code>competition</code> ou <code>vainqueurEnvisage</code> sont invalides,
	 * si il n'existe pas un compétiteur de  nom <code>vainqueurEnvisage</code> dans la compétition,
	 * si la compétition n'est plus ouverte (la date de clôture est dans le passé).
	 * @throws JoueurException   levée 
	 * si <code>pseudo</code> ou <code>password</code> sont invalides, 
	 * si le <code>compteEnJetons</code> du joueur est insuffisant (il deviendrait négatif).
	 */
    public void miserVainqueur(String pseudo, String passwordJoueur, long miseEnJetons, String competition, String vainqueurEnvisage) throws MetierException, JoueurInexistantException, CompetitionInexistanteException, CompetitionException, JoueurException  {

    	//MetierException
		if (miseEnJetons<0) throw new MetierException();
				
		//CompetitionException_CompetitionInexistanteException
		validiteCompetition(competition);
		validiteCompetiteur(vainqueurEnvisage);
		
		Competition comp=giveCompetition(competition);
		
		if(comp==null) throw new CompetitionInexistanteException();
		
		if(!comp.competiteurExistant(vainqueurEnvisage)) throw new CompetitionException();
		
		if(comp.getDateCloture().estDansLePasse()) throw new CompetitionException();
		
		//JoueurInexistantException
		Joueur j= estJoueurInexistant(pseudo, passwordJoueur);
		if (j==null) throw new JoueurInexistantException();
		
		//JoueurException
		validitePseudo(pseudo);
		validitePasswordJoueur(passwordJoueur);
		if((j.getNombreDeJeton()-miseEnJetons)<0) throw new JoueurException();
		
		
		
		//CompeteJoueur
		j.debiterJetons(miseEnJetons);
		
		
		
		Mise m=new Mise(j, comp, miseEnJetons, comp.rechercherVainqueur(vainqueurEnvisage));
		
		//TotalMisesduJoueur
		j.addMise(miseEnJetons);
		
		mises.add(m);
		
		
		
	}


    

	// Les méthodes sans mot de passe


	/**
	 * connaître les compétitions en cours.
	 * 
	 * @return une liste de liste dont les éléments (de type <code>String</code>) représentent une compétition avec dans l'ordre : 
	 *  <ul>
	 *  <li>       le nom de la compétition,  </li>
	 *  <li>       la date de clôture de la compétition. </li>
	 *  </ul>
	 */
	public LinkedList <LinkedList <String>> consulterCompetitions(){
		
		LinkedList<LinkedList<String>> totalComp = new LinkedList<LinkedList<String>>();
		
		for(Competition c : competitions){
			LinkedList<String> comp_i =c.toList();
			totalComp.add(comp_i);
		}
		
		
		return totalComp;
	} 


	/**
	 * connaître  la liste des noms des compétiteurs d'une compétition.  
	 * 
	 * @param competition   le nom de la compétition  
	 * 
	 * @throws CompetitionException   levée  
	 * si le nom de la compétition est invalide.
	 * @throws CompetitionInexistanteException   levée si il n'existe pas de compétition de même nom. 
	 * 
	 * @return la liste des compétiteurs de la  compétition.
	 */
	public LinkedList <String> consulterCompetiteurs(String competition) throws CompetitionException, CompetitionInexistanteException{
		
		//CompetitionException
		validiteCompetition(competition);
		
		//CompetitionInexistanteException
		Competition compet= giveCompetition(competition);
		if(compet==null) throw new CompetitionInexistanteException();
		
		LinkedList<Competiteur> list_competiteurs= compet.getCompetiteurs();
		LinkedList<String> totalCompetiteurs= new LinkedList<String>();
		
		for(Competiteur c : list_competiteurs){
			totalCompetiteurs.add(c.getName());
		}
						
		return totalCompetiteurs;
	}

	/**
	 * vérifier la validité du password du gestionnaire.
	 * 
	 * @param passwordGestionnaire   le password du gestionnaire à vérifier
	 * 
	 * @throws MetierException   levée 
	 * si le <code>passwordGestionnaire</code> est invalide.  
	 */
	protected void validitePasswordGestionnaire(String passwordGestionnaire) throws MetierException {
	    if (passwordGestionnaire==null) throw new MetierException();
	    if (!passwordGestionnaire.matches("[0-9A-Za-z]{8,}")) throw new MetierException();
	    
	}
	protected void validiteName(String name) throws JoueurException{
		if(name==null)throw new JoueurException();
	       if(name.length()<1)throw new JoueurException();
	       for(int i=0;i<name.length();i++){
	          char c  = name.charAt(i);
	          if(!Character.isLetter(c)&&c!='-')throw new JoueurException();
	       }
		
	}
	protected void validiteFirstName(String firstName) throws JoueurException{
		 if(firstName==null)throw new JoueurException();
	       if(firstName.length()<1)throw new JoueurException();
	       for(int i=0;i<firstName.length();i++){
	          char c  = firstName.charAt(i);
	          if(!Character.isLetter(c)&&c!='-')throw new JoueurException();
	       }
		
	}
	protected void validitePseudo(String pseudo) throws JoueurException{
		 if(pseudo==null)throw new JoueurException();
	       if(pseudo.length()<4)throw new JoueurException();
	       for(int i=0;i<pseudo.length();i++){
	          char c  = pseudo.charAt(i);
	          if(!Character.isLetter(c)&&!Character.isDigit(c))throw new JoueurException();
	       }
		
	}
	protected void validitePasswordJoueur(String passwordJoueur) throws JoueurException {
	    if (passwordJoueur==null) throw new JoueurException();
	    if (!passwordJoueur.matches("[0-9A-Za-z]{8,}")) throw new JoueurException();
	}
	
	protected void validiteCompetition(String s) throws CompetitionException{
		if(s==null) throw new CompetitionException();
		if (!s.matches("[0-9A-Za-z.-_]{4,}")) throw new CompetitionException();
	}
	
	protected void validiteCompetiteur(String s) throws CompetitionException{
		if(s==null) throw new CompetitionException();
		if (!s.matches("[0-9A-Za-z-_]{4,}")) throw new CompetitionException();
	}

	  
	 protected Joueur giveJoueur(String name, String firstName, String pseudo){
		 Joueur j=new Joueur(name, firstName, pseudo, 0);
		 for(Joueur a: joueurs ){
			 if(a.equals(j)) return a;
		 }
		 return null;
	 }
	
	 protected Competition giveCompetition(String name){
	    	Competition c= new Competition(name,null);
	    	for(Competition c1: competitions){
	    		if(c1.getName().equals(c.getName()))return c1;
	    	}
	    	return null;
	  }
	 
	 protected Joueur estJoueurInexistant(String pseudo,String passwordJoueur ){
	    	//System.out.println(pseudo+"\n"+passwordJoueur);
	    	
			  for (Joueur j:joueurs){ 
				  //System.out.println(pseudo+' '+j.getPseudo() +' ' + passwordJoueur + ' ' +j.getPass());
				  if (pseudo.equals(j.getPseudo())&&passwordJoueur.equals(j.getPass()))
					 // System.out.println(pseudo+' '+j.getPseudo() +' ' + passwordJoueur + ' ' +j.getPass());   
					  return j; 
				  
			  		} 
			  //System.out.println(pseudo+"\n"+passwordJoueur); 
			  return null;
	    }
	 protected String createPassword(){
			
			Random r = new Random();
		    char tab_lowchar[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','o','q','r','s'};
		    char tab_highchar[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','Q','R','S'};
		    int tab_nb[] = {0,1,2,3,4,5,6,7,8,9};
		    int valeurMin = 0;
		    int valeurMax = 17;
		    int valeurMaxNb = 8;
		    int miniValuer = 3;
		    String finalPwd = "";
		    
		    for(int i=1;i<=10;i++){
		         int choix = valeurMin + r.nextInt(miniValuer - valeurMin);
		
		         if(choix == 0){
		         //pour recuperer un entier
		         int vava = valeurMin + r.nextInt(valeurMaxNb - valeurMin);          
		         finalPwd += tab_nb[vava]; 
		         }
		           
		         if(choix == 1){
		         //pour recuperer un petit caractere
		         int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
		         finalPwd += tab_lowchar[valeur]
		         ;
		         }      
		         if(choix ==2){
		         //pour recuperer un entier
		         int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);      
		         finalPwd += tab_highchar[valeur];
		         }                                               
		    }
		    return finalPwd;
		}
	 
//	  public static void main(String args[]){
//		  try{
//			  
//		  }
//		  catch {
//			  
//		  }
	  //}

	
	/** 
	 * Getter of the property <tt>joueur</tt>
	 * @return  Returns the joueur.
	 * @uml.property  name="joueur"
	 *
	*public LinkedList getJoueur() {
		return joueur;
	}*/





	/** 
	 * Setter of the property <tt>joueur</tt>
	 * @param joueur  The joueur to set.
	 * @uml.property  name="joueur"
	 */
	/*public void setJoueur(LinkedList joueur) {
		this.joueur = joueur;
	}
*/
	


}


