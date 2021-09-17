import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SuperLink extends MouseAdapter {
	
	private String link;

	public SuperLink(String link) {
		this.link = link;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			Desktop desktop = Desktop.getDesktop();
			URI uri = new URI(link);
			desktop.browse(uri);
		} catch (URISyntaxException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
