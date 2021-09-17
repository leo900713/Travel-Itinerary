import javax.swing.ImageIcon;

public class Spot {

	private String spotName;
	private String googleRating;
	private String price;
	private String dinningRecommendation;
	private String spotIntro;
	private ImageIcon img;
//	private int order;
	
	public Spot(String spotName, String googleRating, String price, String dinningRecommendation, String spotIntro) {
		this.spotName = spotName;
		this.googleRating = googleRating;
		this.price = price;
		this.dinningRecommendation = dinningRecommendation;
		this.spotIntro = spotIntro;
//		this.order = order;
		this.img = null;
	}
	
	public Spot(String spotName, String googleRating, String price, String dinningRecommendation, String spotIntro, ImageIcon img) {
		this.spotName = spotName;
		this.googleRating = googleRating;
		this.price = price;
		this.dinningRecommendation = dinningRecommendation;
		this.spotIntro = spotIntro;
		this.img = img;
//		this.order = order;
	}
	
	public String toString() {
		String s = "";
		if(!googleRating.equals(null)) s += "評價: " + googleRating + "\n";
		if(!price.equals(null)) s += "門票: " + price + "\n";
		if(!dinningRecommendation.equals(null)) s += "推薦: " + dinningRecommendation + "\n";
		s += "介紹: \n" + spotIntro;
		return s;
	}

	public String getSpotName() {
		return spotName;
	}
	
	public ImageIcon getImg() {
		return img;
	}
	
//	public int getOrder() {
//		return order;
//	}
	
}

