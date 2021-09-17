
public class Route {

	private int routeID;
	private String routeTitle;
	private String county;
	private int days;
	private int people;
	private String preference;
		
	public Route(int routeID, String county, int days, int people, String preference) {
		this.routeID = routeID;
		this.routeTitle = county + preference + days + "日遊(" + people + "人)" +routeID%10;
		this.county = county;
		this.days = days;
		this.people = people;
		this.preference = preference;
	}

	public int getRouteID() {
		return routeID;
	}
	
	public String getRouteTitle() {
		return routeTitle;
	}

	public String getCounty() {
		return county;
	}

	public int getDays() {
		return days;
	}

	public int getPeople() {
		return people;
	}

	public String getPreference() {
		return preference;
	}
	
}
