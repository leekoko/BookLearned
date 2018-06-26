
public class Sanitation{
    public String WashHands(){
        return "Cleaned!";
    }

}

class Child{
	private Sanitation sanitation;

	public Sanitation getSanitation() {
		return sanitation;
	}

	public void setSanitation(Sanitation sanitation) {
		this.sanitation = sanitation;
	}
	
	public Child(){
		sanitation = new Sanitation();
	}

    public String WashHands(){
        return sanitation.WashHands();
    }
}
