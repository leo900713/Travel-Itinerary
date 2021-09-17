import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class HomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static final int FRAME_WIDTH = 360;
	private static final int FRAME_HEIGHT = 480;
	private static final Color background_green = new Color(95, 158, 160);
	private static final Color hotel = new Color(50, 110, 160);
	
	private Searcher searcher;
	private ArrayList<Route> likeList;
	
	private JPanel contentPane;
	private CardLayout card;
	private boolean fromSearchRoutesPage;
	
	private JPanel homePage, searchPage, searchRoutesPage, likeRoutesPage, itineraryPage, spotPage;
	private JScrollPane searchRoutesScroll, likeRoutesScroll, itineraryScroll;
	private JLabel likeIti;
	
	public HomePage() {
		this.setUndecorated(true);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		searcher = new Searcher();
		likeList = new ArrayList<Route>();
		
		createGUI();
	}
	
	public void createGUI() {
		card = new CardLayout();
		contentPane = new JPanel(card);
		this.setContentPane(contentPane);
		
		createHomePage();
		createSearchPage();
		createSearchRoutesPage();
		createLikeRoutesPage();
		createItineraryPage();
	}
	
	public void createHomePage() {
		homePage = new JPanel(null);
		homePage.add(exit());
		homePage.setBackground(background_green);
		
		JLabel search = new JLabel("搜尋路線", SwingConstants.CENTER);
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(contentPane, "searchPage");
				fromSearchRoutesPage = true;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				search.setBackground(Color.darkGray);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				search.setBackground(Color.white);
			}
		});
		search.setFont(new Font("PT Mono", Font.PLAIN, 36));
		search.setBounds(100, 180, 160, 60);
		search.setBackground(Color.white);
		search.setOpaque(true);
		homePage.add(search);
		
		JLabel like = new JLabel("喜好路線", SwingConstants.CENTER);
		like.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				card.show(contentPane, "likeRoutesPage");
				updateRoutesPage(likeRoutesScroll, likeList);
				fromSearchRoutesPage = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				like.setBackground(Color.darkGray);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				like.setBackground(Color.white);
			}
		});
		like.setFont(new Font("PT Mono", Font.PLAIN, 36));
		like.setBounds(100, 300, 160, 60);
		like.setBackground(Color.white);
		like.setOpaque(true);
		homePage.add(like);
		
		JLabel toolBox = new JLabel(new ImageIcon("images/icons8-pull-down-32.png"));
		toolBox.setBounds(0, 0, 40, 40);
		toolBox.setOpaque(false);
		homePage.add(toolBox);
		
		JPanel tools = new JPanel(null);
		tools.setLocation(0, 40);
		tools.setVisible(false);
		tools.setOpaque(false);
		homePage.add(tools);
		
		JLabel tool1 = new JLabel(new ImageIcon("images/icons8-map-marker-32.png"));
		tool1.setBounds(0, 0, 40, 40);
		tool1.setBackground(background_green);
		tool1.setOpaque(true);
		tool1.setToolTipText("Google Map");
		tool1.addMouseListener(new SuperLink("https://www.google.com.tw/maps/"));
		tools.add(tool1);
		
		JLabel googlemap = new JLabel("Google Map");
		googlemap.setBounds(40, 40, 75, 15);
		googlemap.setVisible(false);
		googlemap.setOpaque(true);
		tool1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool1.setBackground(Color.white);
				googlemap.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool1.setBackground(homePage.getBackground());
				googlemap.setVisible(false);
			}
		});
		googlemap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool1.setBackground(Color.white);
				googlemap.setVisible(true);
				googlemap.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool1.setBackground(homePage.getBackground());
				googlemap.setBackground(homePage.getBackground());
				googlemap.setVisible(false);
			}
		});
		homePage.add(googlemap);
		
		JLabel tool2 = new JLabel(new ImageIcon("images/icons8-train-32.png"));
		tool2.setBounds(0, 60, 40, 40);
		tool2.setBackground(background_green);
		tool2.setOpaque(true);
		tool2.setToolTipText("交通工具");
		tools.add(tool2);
		
		JPanel tool2Panel = new JPanel(new GridLayout(8, 1));
		tool2Panel.setBounds(40, 100, 60, 120);
		tool2Panel.setVisible(false);
		tool2Panel.setOpaque(false);
		tool2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(!tool2Panel.isVisible());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(!tool2Panel.isVisible());
			}
		});
		homePage.add(tool2Panel);
		
		JLabel railway = new JLabel("台鐵");
		railway.addMouseListener(new SuperLink("https://www.railway.gov.tw/"));
		railway.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				railway.setOpaque(true);
				railway.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				railway.setOpaque(false);
			}
		});
		tool2Panel.add(railway);
		JLabel hsr = new JLabel("高鐵");
		hsr.addMouseListener(new SuperLink("https://www.thsrc.com.tw/"));
		hsr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				hsr.setOpaque(true);
				hsr.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				hsr.setOpaque(false);
			}
		});
		tool2Panel.add(hsr);
		JLabel ubus = new JLabel("統聯客運");
		ubus.addMouseListener(new SuperLink("https://www.ubus.com.tw/"));
		ubus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				ubus.setOpaque(true);
				ubus.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				ubus.setOpaque(false);
			}
		});
		tool2Panel.add(ubus);
		JLabel kingbus = new JLabel("國光客運");
		kingbus.addMouseListener(new SuperLink("https://www.kingbus.com.tw/"));
		kingbus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				kingbus.setOpaque(true);
				kingbus.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				kingbus.setOpaque(false);
			}
		});
		tool2Panel.add(kingbus);
		JLabel ebus = new JLabel("和欣客運");
		ebus.addMouseListener(new SuperLink("https://www.ebus.com.tw/"));
		ebus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				ebus.setOpaque(true);
				ebus.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				ebus.setOpaque(false);
			}
		});
		tool2Panel.add(ebus);
		JLabel easyrent = new JLabel("和欣租車");
		easyrent.addMouseListener(new SuperLink("https://www.easyrent.com.tw/"));
		easyrent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				easyrent.setOpaque(true);
				easyrent.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				easyrent.setOpaque(false);
			}
		});
		tool2Panel.add(easyrent);
		JLabel goshare = new JLabel("Go share");
		goshare.addMouseListener(new SuperLink("https://www.ridegoshare.com/tw/"));
		goshare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				goshare.setOpaque(true);
				goshare.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				goshare.setOpaque(false);
			}
		});
		tool2Panel.add(goshare);
		JLabel wemo = new JLabel("Wemo");
		wemo.addMouseListener(new SuperLink("https://www.wemoscooter.com/"));
		wemo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool2.setBackground(Color.white);
				tool2Panel.setVisible(true);
				wemo.setOpaque(true);
				wemo.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool2.setBackground(homePage.getBackground());
				tool2Panel.setVisible(false);
				wemo.setOpaque(false);
			}
		});
		tool2Panel.add(wemo);
		
		JLabel tool3 = new JLabel(new ImageIcon("images/icons8-bed-32.png"));
		tool3.setBounds(0, 120, 40, 40);
		tool3.setBackground(background_green);
		tool3.setOpaque(true);
		tool3.setToolTipText("訂房網站");
		tools.add(tool3);
		
		JPanel tool3Panel = new JPanel(new GridLayout(3, 1));
		tool3Panel.setBounds(40, 160, 60, 45);
		tool3Panel.setVisible(false);
		tool3Panel.setOpaque(false);
		tool3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool3.setBackground(Color.white);
				tool3Panel.setVisible(!tool3Panel.isVisible());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool3.setBackground(homePage.getBackground());
				tool3Panel.setVisible(!tool3Panel.isVisible());
			}
		});
		homePage.add(tool3Panel);
		
		JLabel agoda = new JLabel("Agoda");
		agoda.addMouseListener(new SuperLink("https://www.agoda.com/"));
		agoda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool3.setBackground(Color.white);
				tool3Panel.setVisible(true);
				agoda.setOpaque(true);
				agoda.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool3.setBackground(homePage.getBackground());
				tool3Panel.setVisible(false);
				agoda.setOpaque(false);
			}
		});
		tool3Panel.add(agoda);
		JLabel trivago = new JLabel("Trivago");
		trivago.addMouseListener(new SuperLink("https://www.trivago.com.tw/"));
		trivago.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool3.setBackground(Color.white);
				tool3Panel.setVisible(true);
				trivago.setOpaque(true);
				trivago.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool3.setBackground(homePage.getBackground());
				tool3Panel.setVisible(false);
				trivago.setOpaque(false);
			}
		});
		tool3Panel.add(trivago);
		JLabel booking = new JLabel("Booking");
		booking.addMouseListener(new SuperLink("https://www.booking.com/"));
		booking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool3.setBackground(Color.white);
				tool3Panel.setVisible(true);
				booking.setOpaque(true);
				booking.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool3.setBackground(homePage.getBackground());
				tool3Panel.setVisible(false);
				booking.setOpaque(false);
			}
		});
		tool3Panel.add(booking);
		
		JLabel tool4 = new JLabel(new ImageIcon("images/icons8-ticket-32.png"));
		tool4.setBounds(0, 180, 40, 40);
		tool4.setToolTipText("門票");
		tool4.setBackground(background_green);
		tool4.setOpaque(true);
		tools.add(tool4);
		
		JPanel tool4Panel = new JPanel(new GridLayout(2, 1));
		tool4Panel.setBounds(40, 220, 60, 30);
		tool4Panel.setVisible(false);
		tool4Panel.setOpaque(false);
		tool4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool4.setBackground(Color.white);
				tool4Panel.setVisible(!tool4Panel.isVisible());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool4.setBackground(homePage.getBackground());
				tool4Panel.setVisible(!tool4Panel.isVisible());
			}
		});
		homePage.add(tool4Panel);
		
		JLabel kkday = new JLabel("kkday");
		kkday.addMouseListener(new SuperLink("https://www.kkday.com/zh-tw/"));
		kkday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool4.setBackground(Color.white);
				tool4Panel.setVisible(true);
				kkday.setOpaque(true);
				kkday.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool4.setBackground(homePage.getBackground());
				tool4Panel.setVisible(false);
				kkday.setOpaque(false);
			}
		});
		tool4Panel.add(kkday);
		JLabel klook = new JLabel("KLOOK");
		klook.addMouseListener(new SuperLink("https://www.klook.com/zh-tw"));
		klook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool4.setBackground(Color.white);
				tool4Panel.setVisible(true);
				klook.setOpaque(true);
				klook.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool4.setBackground(homePage.getBackground());
				tool4Panel.setVisible(false);
				klook.setOpaque(false);
			}
		});
		tool4Panel.add(klook);
		
		JLabel tool5 = new JLabel(new ImageIcon("images/icons8-light-on-32.png"));
		tool5.setBounds(0, 240, 40, 40);
		tool5.setToolTipText("小工具");
		tool5.setBackground(background_green);
		tool5.setOpaque(true);
		tools.add(tool5);
		
		JPanel tool5Panel = new JPanel(new GridLayout(4, 1));
		tool5Panel.setBounds(40, 280, 66, 60);
		tool5Panel.setVisible(false);
		tool5Panel.setOpaque(false);
		tool5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool5.setBackground(Color.white);
				tool5Panel.setVisible(!tool5Panel.isVisible());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool5.setBackground(homePage.getBackground());
				tool5Panel.setVisible(!tool5Panel.isVisible());
			}
		});
		homePage.add(tool5Panel);
		JLabel travelking = new JLabel("TravelKing");
		travelking.addMouseListener(new SuperLink("https://www.travelking.com.tw/"));
		travelking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool5.setBackground(Color.white);
				tool5Panel.setVisible(true);
				travelking.setOpaque(true);
				travelking.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool5.setBackground(homePage.getBackground());
				tool5Panel.setVisible(false);
				travelking.setOpaque(false);
			}
		});
		tool5Panel.add(travelking);
		JLabel okgo = new JLabel("玩全台灣");
		okgo.addMouseListener(new SuperLink("https://okgo.tw"));
		okgo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool5.setBackground(Color.white);
				tool5Panel.setVisible(true);
				okgo.setOpaque(true);
				okgo.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool5.setBackground(homePage.getBackground());
				tool5Panel.setVisible(false);
				okgo.setOpaque(false);
			}
		});
		tool5Panel.add(okgo);
		JLabel baggage = new JLabel("行李清單");
		baggage.addMouseListener(new SuperLink("https://jotdownvoyage.com/packing-checklist/#%E8%A1%8C%E6%9D%8E%E6%AA%A2%E6%9F%A5%E8%A1%A8"));
		baggage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool5.setBackground(Color.white);
				tool5Panel.setVisible(true);
				baggage.setOpaque(true);
				baggage.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool5.setBackground(homePage.getBackground());
				tool5Panel.setVisible(false);
				baggage.setOpaque(false);
			}
		});
		tool5Panel.add(baggage);
		JLabel weather = new JLabel("天氣預報");
		weather.addMouseListener(new SuperLink("https://www.cwb.gov.tw/"));
		weather.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tool5.setBackground(Color.white);
				tool5Panel.setVisible(true);
				weather.setOpaque(true);
				weather.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tool5.setBackground(homePage.getBackground());
				tool5Panel.setVisible(false);
				weather.setOpaque(false);
			}
		});
		tool5Panel.add(weather);
		
		toolBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!tools.isVisible()) {
					toolBox.setBackground(Color.white);
					tools.setVisible(true);
					Thread th = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 0; i <= 280; i++) {
									tools.setSize(40, i);
									Thread.sleep(1);
								}
							} catch(Exception ex) {
								ex.getMessage();
							}
						}
					};
					th.start();
				} else {
					toolBox.setBackground(homePage.getBackground());
					Thread th = new Thread() {
						@Override
						public void run() {
							try {
								for(int i = 280; i >= 0; i--) {
									tools.setSize(40, i);
									Thread.sleep(1);
								}
								tools.setVisible(false);
							} catch(Exception ex) {
								ex.getMessage();
							}
						}
					};
					th.start();
				}
			}
		});
		
		title(homePage, "旅遊指南");
		contentPane.add(homePage, "homePage");
	}
	
	public void createSearchPage() {
		searchPage = new JPanel(null);
		searchPage.add(exit());
		searchPage.add(back());
		title(searchPage, "搜尋路線");
		searchPage.setBackground(background_green);
		
		//Area
		
		JLabel area = new JLabel("地區");
		area.setFont(new Font("PT Mono", Font.PLAIN, 24));
		area.setBounds(60, 140, 60, 40);
		searchPage.add(area);
		
		JButton areaTxt = new JButton("(請選擇地區)");
		areaTxt.setFont(new Font("PT Mono", Font.PLAIN, 18));
		areaTxt.setHorizontalAlignment(SwingConstants.LEFT);
		areaTxt.setBounds(130, 145, 170, 30);
		areaTxt.setBorderPainted(false);
		areaTxt.setBackground(Color.white);
		areaTxt.setOpaque(true);
		searchPage.add(areaTxt);
		
		String[] areas = {"(請選擇地區)", "基隆", "雙北", "桃園", "新竹", "苗栗", "台中", "彰化", "南投",
				"雲林", "嘉義", "台南", "高雄", "屏東", "宜蘭", "花蓮", "台東"};
		JList<String> areaList = new JList<String>(areas);
		areaList.setFont(new Font("PT Mono", Font.PLAIN, 18));
		areaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane areaListPane = new JScrollPane(areaList);
		areaListPane.setBounds(130, 180, 170, 119);
		areaListPane.setVisible(false);
		searchPage.add(areaListPane);
		
		//Day
		JLabel days = new JLabel("天數");
		days.setFont(new Font("PT Mono", Font.PLAIN, 24));
		days.setBounds(60, 200, 60, 40);
		searchPage.add(days);
		
		JButton daysTxt = new JButton("(請選擇天數)");
		daysTxt.setFont(new Font("PT Mono", Font.PLAIN, 18));
		daysTxt.setHorizontalAlignment(SwingConstants.LEFT);
		daysTxt.setBounds(130, 205, 170, 30);
		daysTxt.setBorderPainted(false);
		daysTxt.setBackground(Color.white);
		daysTxt.setOpaque(true);
		searchPage.add(daysTxt);
		
		String[] d = {"(請選擇天數)", "1", "2", "3"};
		JList<String> daysList = new JList<String>(d);
		daysList.setFont(new Font("PT Mono", Font.PLAIN, 18));
		daysList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane daysListPane = new JScrollPane(daysList);
		daysListPane.setBounds(130, 240, 170, 96);
		daysListPane.setVisible(false);
		searchPage.add(daysListPane);
		
		//People
		JLabel people = new JLabel("人數");
		people.setFont(new Font("PT Mono", Font.PLAIN, 24));
		people.setBounds(60, 260, 60, 40);
		searchPage.add(people);
		
		JButton peopleTxt = new JButton("(請選擇人數)");
		peopleTxt.setFont(new Font("PT Mono", Font.PLAIN, 18));
		peopleTxt.setHorizontalAlignment(SwingConstants.LEFT);
		peopleTxt.setBounds(130, 265, 170, 30);
		peopleTxt.setBorderPainted(false);
		peopleTxt.setBackground(Color.white);
		peopleTxt.setOpaque(true);
		searchPage.add(peopleTxt);
		
		String[] p = {"(請選擇人數)", "1", "2", "3"};
		JList<String> peopleList = new JList<String>(p);
		peopleList.setFont(new Font("PT Mono", Font.PLAIN, 18));
		peopleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane peopleListPane = new JScrollPane(peopleList);
		peopleListPane.setBounds(130, 300, 170, 96);
		peopleListPane.setVisible(false);
		searchPage.add(peopleListPane);
		
		
		//Type
		JLabel type = new JLabel("偏好");
		type.setFont(new Font("PT Mono", Font.PLAIN, 24));
		type.setBounds(60, 330, 60, 40);
		searchPage.add(type);
		
		ButtonGroup group = new ButtonGroup();
		
		JCheckBox nature = new JCheckBox("風景", new ImageIcon("images/icons8-checked-checkbox-32.png"), true);
		nature.setFont(new Font("PT Mono", Font.PLAIN, 18));
		nature.setBounds(125, 315, 85, 30);
		searchPage.add(nature);
		
		JCheckBox culture = new JCheckBox("人文", new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
		culture.setFont(new Font("PT Mono", Font.PLAIN, 18));
		culture.setBounds(215, 315, 85, 30);
		searchPage.add(culture);
		
		JCheckBox food = new JCheckBox("美食", new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
		food.setFont(new Font("PT Mono", Font.PLAIN, 18));
		food.setBounds(125, 355, 85, 30);
		searchPage.add(food);
		
		JCheckBox family = new JCheckBox("親子", new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
		family.setFont(new Font("PT Mono", Font.PLAIN, 18));
		family.setBounds(215, 355, 85, 30);
		searchPage.add(family);
		
		group.add(nature);
		group.add(culture);
		group.add(food);
		group.add(family);
		
		JButton search = new JButton("GO!!");
		search.setFont(new Font("PT Mono", Font.PLAIN, 24));
		search.setBounds(130, 400, 100, 40);
		search.setBorderPainted(false);
		search.setBackground(Color.white);
		search.setOpaque(true);
		searchPage.add(search);
				
		areaTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				areaListPane.setVisible(!areaListPane.isVisible());
			}
		});
		areaList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				areaTxt.setText(String.valueOf(areaList.getSelectedValue()));
				areaListPane.setVisible(false);
			}
		});
		areaList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				areaListPane.setVisible(false);
			}
		});
		
		daysTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				daysListPane.setVisible(!daysListPane.isVisible());
			}
		});
		daysList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				daysTxt.setText(String.valueOf(daysList.getSelectedValue()));
				daysListPane.setVisible(false);
			}
		});
		daysList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				daysListPane.setVisible(false);
			}
		});
		
		peopleTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				peopleListPane.setVisible(!peopleListPane.isVisible());
			}
		});
		peopleList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				peopleTxt.setText(String.valueOf(peopleList.getSelectedValue()));
				peopleListPane.setVisible(false);				
			}
		});
		peopleList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				peopleListPane.setVisible(false);
			}
		});
		
		nature.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nature.setIcon(new ImageIcon("images/icons8-checked-checkbox-32.png"));
				culture.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				food.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				family.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
			}
		});
		culture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nature.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				culture.setIcon(new ImageIcon("images/icons8-checked-checkbox-32.png"));
				food.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				family.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
			}
		});
		food.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nature.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				culture.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				food.setIcon(new ImageIcon("images/icons8-checked-checkbox-32.png"));
				family.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
			}
		});
		family.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nature.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				culture.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				food.setIcon(new ImageIcon("images/icons8-unchecked-checkbox-32.png"));
				family.setIcon(new ImageIcon("images/icons8-checked-checkbox-32.png"));
			}
		});
		
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String county = areaTxt.getText();
				String days = daysTxt.getText();
				String people = peopleTxt.getText();	
				if(county.equals("(請選擇地區)")) JOptionPane.showMessageDialog(contentPane, "請選擇地區");
				else {
					ArrayList<Route> routeList = null;
					try {
						if(nature.isSelected()) routeList = searcher.search(county, days, people, "風景");
						else if(culture.isSelected()) routeList = searcher.search(county, days, people, "人文");
						else if(food.isSelected()) routeList = searcher.search(county, days, people, "美食");
						else if(family.isSelected()) routeList = searcher.search(county, days, people, "親子");
					} catch(SQLException ex) {
						ex.getMessage();
					}
					updateRoutesPage(searchRoutesScroll, routeList);
					card.show(contentPane, "searchRoutesPage");
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				search.setBackground(Color.darkGray);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				search.setBackground(Color.white);
			}
		});
		contentPane.add(searchPage, "searchPage");
	}

	public void createSearchRoutesPage() {
		
		searchRoutesPage = new JPanel(null);
		contentPane.add(searchRoutesPage, "searchRoutesPage");
		
		searchRoutesPage.add(exit());
		searchRoutesPage.add(back());
		title(searchRoutesPage, "搜尋結果");
		searchRoutesPage.setBackground(background_green);
				
		searchRoutesScroll = new JScrollPane();
		searchRoutesScroll.setBounds(40, 120, 290, 320);
		searchRoutesScroll.setBorder(null);
		searchRoutesPage.add(searchRoutesScroll);
		
	}
	
	public void createLikeRoutesPage() {
		
		likeRoutesPage = new JPanel(null);
		contentPane.add(likeRoutesPage, "likeRoutesPage");
		
		likeRoutesPage.add(exit());
		likeRoutesPage.add(back());
		title(likeRoutesPage, "喜好路線");
		likeRoutesPage.setBackground(background_green);
				
		likeRoutesScroll = new JScrollPane();
		likeRoutesScroll.setBounds(40, 120, 290, 320);
		likeRoutesScroll.setBorder(null);
		likeRoutesPage.add(likeRoutesScroll);
		
	}
	
	public void createItineraryPage() {
		
		itineraryPage = new JPanel(null);
		contentPane.add(itineraryPage, "itineraryPage");
		
		itineraryPage.add(exit());
		itineraryPage.add(back());
		title(itineraryPage, "路線資訊");
		itineraryPage.setBackground(background_green);
		
		likeIti = new JLabel();
		likeIti.setBounds(260, 0, 40, 40);
		itineraryPage.add(likeIti);
		
		itineraryScroll = new JScrollPane();
		itineraryScroll.setBounds(40, 120, 290, 320);
		itineraryScroll.setBorder(null);
		itineraryPage.add(itineraryScroll);
				
	}
	
	public void updateRoutesPage(JScrollPane scroll, ArrayList<Route> aList) {
		
		JPanel routesPane = new JPanel();
		if(aList.size() == 0) {
			
			if(scroll == likeRoutesScroll) {
				JLabel noRoute = new JLabel("(查無路線)");
				noRoute.setFont(new Font("PT Mono", Font.PLAIN, 24));
				noRoute.setBounds(0, 0, 280, 40);
				routesPane.add(noRoute);
				
				scroll.setBounds(40, 120, 280, 40);
			} else {
				routesPane.setLayout(null);
				routesPane.setBounds(0, 0, 240, 306);
				
				ImageIcon egg = new ImageIcon("images/getU.png");
				JLabel noRoute = new JLabel(egg);
				noRoute.setBounds(0, 0, 240, 306);
				noRoute.setOpaque(false);
				routesPane.add(noRoute);
				
				scroll.setBounds(60, 120, 240, 306);
			}
		} else {
			routesPane.setLayout(new GridLayout(aList.size(), 1));
			routesPane.setPreferredSize(new Dimension(270, aList.size() * 40));
			
			for(int i = 0; i < aList.size(); i++) {
				routesPane.add(addRoute(aList.get(i)));
			}
			
			if(aList.size() < 8) scroll.setBounds(40, 120, 290, aList.size() * 40);
			else scroll.setBounds(40, 120, 290, 320);
		}
		
		scroll.setViewportView(routesPane);
	}

	public void updateItineraryPage(Route iti) {
		Icon unlike = new ImageIcon("images/unlike.png");
		Icon like = new ImageIcon("images/like.png");
		if(likeList.contains(iti)) likeIti.setIcon(like);
		else likeIti.setIcon(unlike);
		for(MouseListener m: likeIti.getMouseListeners()) {
			likeIti.removeMouseListener(m);
		}
		likeIti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(likeList.contains(iti)) {
					likeIti.setIcon(unlike);
					likeList.remove(iti);
				} else {
					likeIti.setIcon(like);
					likeList.add(iti);
				}
			}
		});
		
		String[][] spotArray = null;
		try {
			spotArray = searcher.getSpotNameArray(iti.getRouteID());
		} catch(SQLException ex) {
			ex.getMessage();
		}
		JPanel spotsPane = new JPanel();
		spotsPane.setPreferredSize(new Dimension(270, spotArray.length * 50));
		if(spotArray.length == 0) {
			JLabel noSpot = new JLabel("Error: 無景點");
			noSpot.setFont(new Font("PT Mono", Font.PLAIN, 24));
			noSpot.setBounds(0, 0, 290, 40);
			spotsPane.add(noSpot);
			itineraryScroll.setBounds(40, 120, 290, 40);
		} else {
			spotsPane.setLayout(new GridLayout(spotArray.length, 1));
			spotsPane.setPreferredSize(new Dimension(270, spotArray.length * 50));
			if(spotArray.length < 7) {
				itineraryScroll.setBounds(40, 120, 290, spotArray.length*50);
			} else {
				itineraryScroll.setBounds(40, 120, 290, 320);
			}
			for(int i = 0; i < spotArray.length; i++) {
				spotsPane.add(addSpot(spotArray[i][0], Integer.parseInt(spotArray[i][1]), spotArray[i][2]));
			}
		}
		
		
		itineraryScroll.setViewportView(spotsPane);
	}
	
	public void updateSpotPage(Spot spot) {
		
		spotPage = new JPanel(null);
		contentPane.add(spotPage, "spotPage");
		
		spotPage.add(exit());
		spotPage.add(back());
		title(spotPage, spot.getSpotName());
		spotPage.setBackground(background_green);
		
		JTextArea spotIntro = new JTextArea(spot.toString());
		spotIntro.setFont(new Font("PT Mono", Font.PLAIN, 18));
		spotIntro.setLineWrap(true);
		spotIntro.setEditable(false);
		spotIntro.setBackground(spotPage.getBackground());
		
		JScrollPane introScroll = new JScrollPane(spotIntro);
		introScroll.setBounds(40, 120, 280, 320);
		introScroll.setBorder(null);
		spotPage.add(introScroll);
		
		if(spot.getImg() != null) {
			JLabel img = new JLabel(spot.getImg());
			img.setBounds(40, 120, 280, 210);
			spotPage.add(img);
			introScroll.setBounds(40, 335, 280, 105);
		}
		
	}
	
	public JPanel addRoute(Route iti) {
		
		JPanel route = new JPanel(new BorderLayout());
		route.setOpaque(true);
		route.setBackground(background_green);
		
		String title = iti.getRouteTitle();
		JLabel routeTitle = new JLabel(title);
		routeTitle.setFont(new Font("PT Mono", Font.PLAIN, 24));
		route.add(routeTitle, BorderLayout.WEST);
				
		route.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateItineraryPage(iti);
				card.show(contentPane, "itineraryPage");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				route.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				route.setBackground(searchRoutesPage.getBackground());
			}
			@Override
			public void mousePressed(MouseEvent e) {
				route.setBackground(Color.darkGray);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				route.setBackground(searchRoutesPage.getBackground());
			}
		});
		return route;
	}
	
	public JPanel addSpot(String spotName, int order, String to_next) {
		
		JPanel route = new JPanel(new BorderLayout());
		route.setPreferredSize(new Dimension(230, 40));
		route.setOpaque(true);
		route.setBackground(background_green);
		
		JLabel spotTitle = new JLabel(spotName);
		spotTitle.setFont(new Font("PT Mono", Font.PLAIN, 24));
		if(order % 10 == 0) spotTitle.setForeground(hotel);
		spotTitle.setOpaque(true);
		spotTitle.setBackground(background_green);
		route.add(spotTitle, BorderLayout.CENTER);
		
		JLabel toNextLabel = new JLabel("-->" + to_next);
		if(to_next.equals("null") && order % 10 == 0) toNextLabel.setText("---------------" + "Day " + (order-(order%10))/10 + "--------------");
		else if(to_next.equals("null") && order % 10 != 0) toNextLabel.setText("----------------" + "End" + "---------------");
		route.add(toNextLabel, BorderLayout.SOUTH);
		
		spotTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Spot spot = null;
				try {
					spot = searcher.findSpot(spotName);
				} catch(SQLException ex) {
					ex.getMessage();
				}
				if(!spot.equals(null)) updateSpotPage(spot);
				card.show(contentPane, "spotPage");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				spotTitle.setBackground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				spotTitle.setBackground(searchRoutesPage.getBackground());
			}
			@Override
			public void mousePressed(MouseEvent e) {
				spotTitle.setBackground(Color.darkGray);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				spotTitle.setBackground(searchRoutesPage.getBackground());
			}
		});

		toNextLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(contentPane, to_next, null, JOptionPane.PLAIN_MESSAGE, null);
			}
		});
		
		return route;
	}
		
	public JLabel exit() {
		JLabel exit = new JLabel("X", SwingConstants.CENTER);
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				exit.setForeground(Color.red);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				exit.setForeground(Color.black);
			}
		});
		exit.setFont(new Font("PT Mono", Font.BOLD, 24));
		exit.setBounds(320, 0, 40, 40);
//		exit.setOpaque(true);
		
		return exit;
	}
	
	public void title(JPanel panel, String t) {
		JPanel titlePanel = new JPanel(null);
		titlePanel.setBounds(40, 50, 280, 60);
		titlePanel.setOpaque(false);
		
		JLabel title = new JLabel(t, SwingConstants.CENTER);
		title.setFont(new Font("PT Mono", Font.PLAIN, 48));
		title.setLocation(0, 0);
		title.setSize(280, 60);
		titlePanel.add(title);
		
		panel.add(titlePanel);
		
		FontMetrics fm = title.getFontMetrics(title.getFont());
		int fmWidth = fm.stringWidth(t);
		if(fmWidth > 280) {
			title.setSize(fmWidth, 60);
			Thread th = new Thread() {
				@Override
				public void run() {
					try {
						do {
							System.out.println("Running");
							title.setLocation(0, 0);
							Thread.sleep(2000);
							for(int i = 0; i >= 280 - fmWidth; i--) {
								title.setLocation(i, 0);
								Thread.sleep(10);
							}
							Thread.sleep(1000);
						}
						while(panel.isShowing());
						System.out.println("Stop");
					} catch(Exception ex) {
						ex.getMessage();
					}
				}
			};
			th.start();
		}		
	}
	
	public JLabel back() {
		JLabel back = new JLabel("<", SwingConstants.CENTER);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(searchPage.isShowing() || likeRoutesPage.isShowing()) {
					card.show(contentPane, "homePage");
				} else if(searchRoutesPage.isShowing()) {
					card.show(contentPane, "searchPage");
				} else if(itineraryPage.isShowing() && fromSearchRoutesPage) {
					card.show(contentPane, "searchRoutesPage");
				} else if(itineraryPage.isShowing() && !fromSearchRoutesPage) {
					card.show(contentPane, "likeRoutesPage");
					updateRoutesPage(likeRoutesScroll, likeList);
				} else if(spotPage.isShowing()) {
					card.show(contentPane, "itineraryPage");
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				back.setBackground(Color.white);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				back.setBackground(background_green);
			}
		});
		back.setFont(new Font("PT Mono", Font.BOLD, 24));
		back.setBounds(0, 0, 40, 40);
		back.setOpaque(false);
		
		return back;
	}
	
}
