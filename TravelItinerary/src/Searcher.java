import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Searcher {
	
	public Searcher() {
		
	}
	
	//SELECT DISTINCT `County`, `Days`, `People`, `Preference` FROM `GP_Route_Info` WHERE `County` = '雙北' AND `Preference` = '風景'
	
	public ArrayList<Route> search(String county, String days, String people, String preference) throws SQLException {
		Connection conn = Connector.getConnection();
		ArrayList<Route> searchList = new ArrayList<Route>();
		try {
			String sql = "SELECT Route_ID FROM GP_Route_Info WHERE County = '" + county + "'";
			if(!days.equals("(請選擇天數)")) {
				sql += " AND Days = " + days;
			}
			if(!people.equals("(請選擇人數)")) {
				sql += " AND People = " + people;
			}
			sql += " AND Preference = '" + preference + "'";
			Statement stat = conn.createStatement();
			ResultSet set = stat.executeQuery(sql);
			while(set.next()) {
				int routeID = set.getInt("Route_ID");
				searchList.add(findRoute(routeID));
			}
			return searchList;
		} finally {
			conn.close();
		}
	}
	
	public Route findRoute(int routeID) throws SQLException {
		Connection conn = Connector.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM GP_Route_Info WHERE Route_ID = ?");
			stat.setInt(1, routeID);
			ResultSet set = stat.executeQuery();
			if(set.next()) {
				String county = set.getString("County");
				int days = set.getInt("Days");
				int people = set.getInt("People");
				String preference = set.getString("Preference");
				return new Route(routeID, county, days, people, preference);
			} else {
				return null;
			}
		} finally {
			conn.close();
		}
	}
	
	public Spot findSpot(String spotName) throws SQLException {
		Connection conn = Connector.getConnection();
		try {
			PreparedStatement stat = conn.prepareStatement("SELECT * FROM GP_Spot_Info WHERE Spot_Name = ?");
			stat.setString(1, spotName);
			ResultSet set = stat.executeQuery();
			if(set.next()) {
				String googleRating = set.getString("Google_Rating");
				String price = set.getString("Price");
				String dinningRecommendation = set.getString("Dinning_Recommendation");
				String spotIntro = set.getString("Spot_Intro");
				byte[] img = set.getBytes("Picture");
				if(set.wasNull()) {
					return new Spot(spotName, googleRating, price, dinningRecommendation, spotIntro);
				} else {
					ImageIcon img1 = new ImageIcon(img);
					Image img2 = img1.getImage();
					Image img3 = img2.getScaledInstance(360, 240, Image.SCALE_SMOOTH);
					ImageIcon img4 = new ImageIcon(img3);
					return new Spot(spotName, googleRating, price, dinningRecommendation, spotIntro, img4);
				}
			} else {
				return null;
			}
		} finally {
			conn.close();
		}
	}
	
	public String[][] getSpotNameArray(int routeID) throws SQLException {
		Connection conn = Connector.getConnection();
		try {
			PreparedStatement stat = conn
					.prepareStatement("SELECT Spot_Name, Spot_Order, Transportation_to_next FROM GP_Route_Spot_Link WHERE Route_ID = ? ORDER BY Spot_Order");
			stat.setInt(1, routeID);
			ResultSet set = stat.executeQuery();
			set.last();
			int count = set.getRow();
			String[][] result = new String[count][3];
			for(int i = 0; i < count; i++) {
				set.absolute(i + 1);
				String spotName = set.getString("Spot_Name");
				int order = set.getInt("Spot_Order");
				String to_next = set.getString("Transportation_to_next");
				result[i][0] = spotName;
				result[i][1] = Integer.toString(order);
				result[i][2] = to_next;
//				System.out.printf("Row: %2d, order: %d, spotName: %s%n%s%n", i, order, spotName, to_next);
			}
			return result;
		} finally {
			conn.close();
		}
	}
	
}
