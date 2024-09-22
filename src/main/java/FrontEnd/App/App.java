package FrontEnd.App;

import FrontEnd.AccountContentUI.AccountContentPanel;
import FrontEnd.CriticismContentUI.CriticismContentPanel;
import FrontEnd.DepartmentContentUI.DepartmentContentPanel;
import FrontEnd.EmployeeContentUI.EmployeeContentPanel;
import FrontEnd.LoginUI.LoginUI;
import FrontEnd.RewardContentUI.RewardContentPanel;
import FrontEnd.SalaryContentUI.SalaryContentPanel;
import FrontEnd.StatisticsContentUI.StatisticsContentPanel;
import FrontEnd.ProjectContentUI.AssignmentContentPanel;
import FrontEnd.Redux.Redux;
import FrontEnd.UserInfoContentUI.UserInfoContentPanel;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class App extends javax.swing.JFrame implements MouseListener {

	UserInfoContentPanel userInfoContentPanel;
	EmployeeContentPanel employeeContentPanel;
	DepartmentContentPanel departmentContentPanel;
	SalaryContentPanel salaryContentPanel;
	AssignmentContentPanel travelContentPanel;
	RewardContentPanel rewardContentPanel;
	CriticismContentPanel criticismContentPanel;
	AccountContentPanel accountContentPanel;
	StatisticsContentPanel statisticsContentPanel;
	JLabel selectedMenuItem;

	public App() {
		initComponents();

		new Redux();

		Border userAvatarBorder = userAvatarLabel.getBorder();
		Border menuEmployeeBorder = menuEmployeeItem.getBorder();
		Border menuDepartmentBorder = menuDepartmentItem.getBorder();
		Border menuSalaryBorder = menuSalaryItem.getBorder();
		Border menuTravelBorder = menuTravelItem.getBorder();
		Border menuRewardBorder = menuRewardItem.getBorder();
		Border menuCriticBorder = menuCriticItem.getBorder();
		Border menuLogoutBorder = menuLogoutItem.getBorder();
		Border menuAccountBorder = menuAccountItem.getBorder();
		Border menuStatisticBorder = menuStatisticItem.getBorder();

		Border margin = new EmptyBorder(10, 10, 10, 10);

		userAvatarLabel.setBorder(new CompoundBorder(userAvatarBorder, margin));
		menuEmployeeItem.setBorder(new CompoundBorder(menuEmployeeBorder, margin));
		menuDepartmentItem.setBorder(new CompoundBorder(menuDepartmentBorder, margin));
		menuSalaryItem.setBorder(new CompoundBorder(menuSalaryBorder, margin));
		menuTravelItem.setBorder(new CompoundBorder(menuTravelBorder, margin));
		menuRewardItem.setBorder(new CompoundBorder(menuRewardBorder, margin));
		menuCriticItem.setBorder(new CompoundBorder(menuCriticBorder, margin));
		menuLogoutItem.setBorder(new CompoundBorder(menuLogoutBorder, margin));
		menuAccountItem.setBorder(new CompoundBorder(menuAccountBorder, margin));
		menuStatisticItem.setBorder(new CompoundBorder(menuStatisticBorder, margin));

		if (!Redux.isAdmin && !Redux.isDirector) {
                    jSeparator8.setVisible(false);
                    menuAccountItem.setVisible(false);
		}

                if(Redux.isAdmin){
                    menuAccountItem.setVisible(false);
                    menuStatisticItem.setVisible(false);
                }
		employeeContentPanel = new EmployeeContentPanel();
		userInfoContentPanel = new UserInfoContentPanel();
		departmentContentPanel = new DepartmentContentPanel();
		salaryContentPanel = new SalaryContentPanel();
		travelContentPanel = new AssignmentContentPanel();
		rewardContentPanel = new RewardContentPanel();
		criticismContentPanel = new CriticismContentPanel();
		accountContentPanel = new AccountContentPanel();
		statisticsContentPanel = new StatisticsContentPanel();

		userAvatarLabel.addMouseListener(this);
		menuEmployeeItem.addMouseListener(this);
		menuDepartmentItem.addMouseListener(this);
		menuSalaryItem.addMouseListener(this);
		menuTravelItem.addMouseListener(this);
		menuRewardItem.addMouseListener(this);
		menuCriticItem.addMouseListener(this);
		menuLogoutItem.addMouseListener(this);
		menuAccountItem.addMouseListener(this);
		menuStatisticItem.addMouseListener(this);
               
                
		appContentPanel.setLayout(new GridLayout(1, 1));
		welcomeLabel.setText("Xin chào " + Redux.username + " !");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel clickedItem = (JLabel) e.getSource();

		// Deselect previously selected menu item (if any)
		if (selectedMenuItem != null) {
			selectedMenuItem.setBackground(new Color(51, 51, 51)); // Set background to default
		}

		// Update selectedMenuItem and change background color to red
		selectedMenuItem = clickedItem;
		clickedItem.setBackground(new Color(30, 30, 30));

		if (clickedItem == userAvatarLabel) {
			showUserInfoContentPanel();
		} else if (clickedItem == menuEmployeeItem) {
			showEmployeeContentPanel();
		} else if (clickedItem == menuDepartmentItem) {
			showDepartmentContentPanel();
		} else if (clickedItem == menuSalaryItem) {
			showSalaryContentPanel();
		} else if (clickedItem == menuTravelItem) {
			showTravelContentPanel();
		} else if (clickedItem == menuRewardItem) {
			showRewardContentPanel();
		} else if (clickedItem == menuCriticItem) {
			showCriticContentPanel();
		} else if (clickedItem == menuLogoutItem) {
			showLogoutContentPanel();
		} else if (clickedItem == menuAccountItem) {
			showAccountContentPanel();
		} else if (clickedItem == menuStatisticItem) {
			showStatisticsContentPanel();
		}
	}

	private void clearAppContentPanel() {
		appContentPanel.removeAll();
		validate();
		repaint();
	}

	private void showUserInfoContentPanel() {
		clearAppContentPanel();
		userInfoContentPanel.formInit();
		appContentPanel.add(userInfoContentPanel);
		validate();
		repaint();
	}

	private void showEmployeeContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(employeeContentPanel);
		validate();
		repaint();
	}

	private void showDepartmentContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(departmentContentPanel);
		validate();
		repaint();
	}

	private void showSalaryContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(salaryContentPanel);
		validate();
		repaint();
	}

	private void showTravelContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(travelContentPanel);
		validate();
		repaint();
	}

	private void showRewardContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(rewardContentPanel);
		validate();
		repaint();
	}

	private void showCriticContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(criticismContentPanel);
		validate();
		repaint();
	}

	private void showLogoutContentPanel() {
		try {
			// Load the image from the specified path
			Image image = ImageIO.read(App.class.getResourceAsStream("/images/warning.png"));

			// Convert the image to a BufferedImage for further processing
			BufferedImage bufferedImage = (BufferedImage) image;

			// Create an ImageIcon from the BufferedImage
			ImageIcon icon = new ImageIcon(bufferedImage);

			// Show the confirmation dialog with the custom icon
			int response = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn đăng xuất ?",
					"ĐĂNG XUẤT NGAY ?",
					YES_NO_OPTION, QUESTION_MESSAGE, icon); // Handle the user's response
			switch (response) {
				case JOptionPane.YES_OPTION -> {
					System.out.println("Đăng xuất thành công!");
					Redux.isLoggedIn = false;
					Redux.isAdmin = false;
					Redux.userId = "";
					Redux.username = "";
					App.this.dispose();
					LoginUI loginFrame = new LoginUI();
				}
				case JOptionPane.NO_OPTION ->
					System.out.println("Đã hủy đăng xuất.");
				default ->
					System.out.println("Có lỗi xảy ra.");
			}

		} catch (HeadlessException | IOException e) {
			System.err.println("Lỗi khi tải ảnh: " + e.getMessage());
		}
	}

	private void showAccountContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(accountContentPanel);
		validate();
		repaint();
	}

	private void showStatisticsContentPanel() {
		clearAppContentPanel();
		appContentPanel.add(statisticsContentPanel);
		validate();
		repaint();
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		bodyPanel = new javax.swing.JPanel();
		menuPanel = new javax.swing.JPanel();
		jSeparator1 = new javax.swing.JSeparator();
		menuEmployeeItem = new javax.swing.JLabel();
		menuDepartmentItem = new javax.swing.JLabel();
		menuSalaryItem = new javax.swing.JLabel();
		menuTravelItem = new javax.swing.JLabel();
		menuRewardItem = new javax.swing.JLabel();
		menuCriticItem = new javax.swing.JLabel();
		menuLogoutItem = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		userAvatarLabel = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jSeparator4 = new javax.swing.JSeparator();
		jSeparator5 = new javax.swing.JSeparator();
		jSeparator6 = new javax.swing.JSeparator();
		jSeparator7 = new javax.swing.JSeparator();
		jSeparator8 = new javax.swing.JSeparator();
		menuAccountItem = new javax.swing.JLabel();
		jSeparator9 = new javax.swing.JSeparator();
		menuStatisticItem = new javax.swing.JLabel();
		jSeparator10 = new javax.swing.JSeparator();
		appContentPanel = new javax.swing.JPanel();
		welcomeLabel = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Quản Lý Nhân Viên");
		setName("appFrame"); // NOI18N
		setResizable(false);
		setSize(new java.awt.Dimension(1280, 720));

		bodyPanel.setBackground(new java.awt.Color(255, 255, 255));
		bodyPanel.setName("bodyPanel"); // NOI18N
		bodyPanel.setPreferredSize(new java.awt.Dimension(1280, 740));

		menuPanel.setBackground(new java.awt.Color(51, 51, 51));
		menuPanel.setAlignmentX(0.0F);
		menuPanel.setAlignmentY(0.0F);
		menuPanel.setName("menuPanel"); // NOI18N
		menuPanel.setPreferredSize(new java.awt.Dimension(225, 740));

		jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jSeparator1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

		menuEmployeeItem.setBackground(new java.awt.Color(51, 51, 51));
		menuEmployeeItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuEmployeeItem.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/images/user-group-icon.png"))); // NOI18N
		menuEmployeeItem.setLabelFor(menuEmployeeItem);
		menuEmployeeItem.setText("Quản Lý Nhân Viên");
		menuEmployeeItem.setToolTipText("");
		menuEmployeeItem.setAlignmentY(0.0F);
		menuEmployeeItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuEmployeeItem.setIconTextGap(19);
		menuEmployeeItem.setMaximumSize(new java.awt.Dimension(40, 40));
		menuEmployeeItem.setMinimumSize(new java.awt.Dimension(40, 40));
		menuEmployeeItem.setName("menuEmployeeItem"); // NOI18N
		menuEmployeeItem.setOpaque(true);
		menuEmployeeItem.setPreferredSize(new java.awt.Dimension(225, 48));

		menuDepartmentItem.setBackground(new java.awt.Color(51, 51, 51));
		menuDepartmentItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuDepartmentItem.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/images/tree-structure.png"))); // NOI18N
		menuDepartmentItem.setLabelFor(menuDepartmentItem);
		menuDepartmentItem.setText("Quản Lý Phòng Ban");
		menuDepartmentItem.setAlignmentY(0.0F);
		menuDepartmentItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuDepartmentItem.setIconTextGap(19);
		menuDepartmentItem.setMaximumSize(new java.awt.Dimension(48, 48));
		menuDepartmentItem.setMinimumSize(new java.awt.Dimension(48, 48));
		menuDepartmentItem.setName("menuDepartmentItem"); // NOI18N
		menuDepartmentItem.setOpaque(true);
		menuDepartmentItem.setPreferredSize(new java.awt.Dimension(225, 48));

		menuSalaryItem.setBackground(new java.awt.Color(51, 51, 51));
		menuSalaryItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuSalaryItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/money.png"))); // NOI18N
		menuSalaryItem.setLabelFor(menuSalaryItem);
		menuSalaryItem.setText("Quản Lý Lương");
		menuSalaryItem.setAlignmentY(0.0F);
		menuSalaryItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuSalaryItem.setIconTextGap(19);
		menuSalaryItem.setName("menuSalaryItem"); // NOI18N
		menuSalaryItem.setOpaque(true);

		menuTravelItem.setBackground(new java.awt.Color(51, 51, 51));
		menuTravelItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuTravelItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/travel.png"))); // NOI18N
		menuTravelItem.setLabelFor(menuTravelItem);
		menuTravelItem.setText("Quản Lý Công Tác");
		menuTravelItem.setAlignmentY(0.0F);
		menuTravelItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuTravelItem.setIconTextGap(19);
		menuTravelItem.setMaximumSize(new java.awt.Dimension(32, 32));
		menuTravelItem.setMinimumSize(new java.awt.Dimension(32, 32));
		menuTravelItem.setName("menuTravelItem"); // NOI18N
		menuTravelItem.setOpaque(true);

		menuRewardItem.setBackground(new java.awt.Color(51, 51, 51));
		menuRewardItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuRewardItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/star.png"))); // NOI18N
		menuRewardItem.setLabelFor(menuRewardItem);
		menuRewardItem.setText("Quản Lý Khen Thưởng");
		menuRewardItem.setAlignmentY(0.0F);
		menuRewardItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuRewardItem.setIconTextGap(19);
		menuRewardItem.setMaximumSize(new java.awt.Dimension(32, 32));
		menuRewardItem.setMinimumSize(new java.awt.Dimension(32, 32));
		menuRewardItem.setName("menuRewardItem"); // NOI18N
		menuRewardItem.setOpaque(true);

		menuCriticItem.setBackground(new java.awt.Color(51, 51, 51));
		menuCriticItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuCriticItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hammer.png"))); // NOI18N
		menuCriticItem.setLabelFor(menuCriticItem);
		menuCriticItem.setText("Quản Lý Kỷ Luật");
		menuCriticItem.setAlignmentY(0.0F);
		menuCriticItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuCriticItem.setIconTextGap(19);
		menuCriticItem.setName("menuCriticItem"); // NOI18N
		menuCriticItem.setOpaque(true);

		menuLogoutItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuLogoutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/power.png"))); // NOI18N
		menuLogoutItem.setText("Đăng Xuất");
		menuLogoutItem.setAlignmentY(0.0F);
		menuLogoutItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuLogoutItem.setIconTextGap(19);
		menuLogoutItem.setName("menuLogoutItem"); // NOI18N

		jSeparator2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		userAvatarLabel.setBackground(new java.awt.Color(51, 51, 51));
		userAvatarLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		userAvatarLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		userAvatarLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
		userAvatarLabel.setLabelFor(userAvatarLabel);
		userAvatarLabel.setText("Trang Cá Nhân");
		userAvatarLabel.setAlignmentY(0.0F);
		userAvatarLabel.setAutoscrolls(true);
		userAvatarLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		userAvatarLabel.setIconTextGap(19);
		userAvatarLabel.setName("userAvatarLabel"); // NOI18N
		userAvatarLabel.setOpaque(true);

		jSeparator3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		jSeparator4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		jSeparator5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		jSeparator6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		jSeparator7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		jSeparator8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		menuAccountItem.setBackground(new java.awt.Color(51, 51, 51));
		menuAccountItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuAccountItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/account.png"))); // NOI18N
		menuAccountItem.setText("Quản Lý Tài Khoản");
		menuAccountItem.setAlignmentY(0.0F);
		menuAccountItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuAccountItem.setIconTextGap(19);
		menuAccountItem.setName("menuAccountItem"); // NOI18N
		menuAccountItem.setOpaque(true);

		menuStatisticItem.setBackground(new java.awt.Color(51, 51, 51));
		menuStatisticItem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		menuStatisticItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/statistic.png"))); // NOI18N
		menuStatisticItem.setText("Thống Kê");
		menuStatisticItem.setAlignmentY(0.0F);
		menuStatisticItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		menuStatisticItem.setIconTextGap(19);
		menuStatisticItem.setName("menuStatisticItem"); // NOI18N
		menuStatisticItem.setOpaque(true);

		javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
		menuPanel.setLayout(menuPanelLayout);
		menuPanelLayout.setHorizontalGroup(
				menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jSeparator2)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout
								.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(jSeparator3,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										227,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jSeparator1)
						.addComponent(menuRewardItem,
								javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(menuSalaryItem,
								javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(menuDepartmentItem,
								javax.swing.GroupLayout.PREFERRED_SIZE, 0,
								Short.MAX_VALUE)
						.addComponent(menuEmployeeItem,
								javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.PREFERRED_SIZE, 0,
								Short.MAX_VALUE)
						.addComponent(menuTravelItem, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(menuLogoutItem, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(menuCriticItem, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jSeparator5)
						.addComponent(jSeparator6)
						.addComponent(jSeparator7)
						.addComponent(jSeparator8)
						.addComponent(userAvatarLabel,
								javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(menuAccountItem, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jSeparator9)
						.addComponent(menuStatisticItem, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jSeparator10));
		menuPanelLayout.setVerticalGroup(
				menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(menuPanelLayout.createSequentialGroup()
								.addGap(0, 0, 0)
								.addComponent(userAvatarLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										57,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										3,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuEmployeeItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator3,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuDepartmentItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator4,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuSalaryItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator5,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuTravelItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator6,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuRewardItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator7,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuCriticItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator8,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuAccountItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator9,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuStatisticItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(jSeparator10,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										68,
										Short.MAX_VALUE)
								.addComponent(jSeparator2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										3,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(menuLogoutItem,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										65,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)));

		appContentPanel.setBackground(new java.awt.Color(255, 255, 255));
		appContentPanel.setAlignmentX(0.0F);
		appContentPanel.setAlignmentY(0.0F);
		appContentPanel.setName("appContentPanel"); // NOI18N
		appContentPanel.setPreferredSize(new java.awt.Dimension(1055, 740));

		welcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
		welcomeLabel.setForeground(new java.awt.Color(51, 255, 51));
		welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		welcomeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thumb-up.png"))); // NOI18N
		welcomeLabel.setText("Xin chào Kagami !");
		welcomeLabel.setAlignmentY(0.0F);
		welcomeLabel.setIconTextGap(10);
		welcomeLabel.setName("welcomeLabel"); // NOI18N

		javax.swing.GroupLayout appContentPanelLayout = new javax.swing.GroupLayout(appContentPanel);
		appContentPanel.setLayout(appContentPanelLayout);
		appContentPanelLayout.setHorizontalGroup(
				appContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(appContentPanelLayout.createSequentialGroup()
								.addGap(158, 158, 158)
								.addComponent(welcomeLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										743,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(154, Short.MAX_VALUE)));
		appContentPanelLayout.setVerticalGroup(
				appContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(appContentPanelLayout.createSequentialGroup()
								.addGap(158, 158, 158)
								.addComponent(welcomeLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										204,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(378, Short.MAX_VALUE)));

		javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
		bodyPanel.setLayout(bodyPanelLayout);
		bodyPanelLayout.setHorizontalGroup(
				bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(bodyPanelLayout.createSequentialGroup()
								.addComponent(menuPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)
								.addComponent(appContentPanel,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, 0)));
		bodyPanelLayout.setVerticalGroup(
				bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(bodyPanelLayout.createSequentialGroup()
								.addGroup(bodyPanelLayout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(menuPanel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(appContentPanel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(0, 0, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(bodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(bodyPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	public static void main(String args[]) {
		FlatDarkLaf.setup();
		java.awt.EventQueue.invokeLater(() -> {
			new App().setVisible(true);
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel appContentPanel;
	private javax.swing.JPanel bodyPanel;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator10;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JSeparator jSeparator7;
	private javax.swing.JSeparator jSeparator8;
	private javax.swing.JSeparator jSeparator9;
	private javax.swing.JLabel menuAccountItem;
	private javax.swing.JLabel menuCriticItem;
	private javax.swing.JLabel menuDepartmentItem;
	private javax.swing.JLabel menuEmployeeItem;
	private javax.swing.JLabel menuLogoutItem;
	private javax.swing.JPanel menuPanel;
	private javax.swing.JLabel menuRewardItem;
	private javax.swing.JLabel menuSalaryItem;
	private javax.swing.JLabel menuStatisticItem;
	private javax.swing.JLabel menuTravelItem;
	private javax.swing.JLabel userAvatarLabel;
	private javax.swing.JLabel welcomeLabel;
	// End of variables declaration//GEN-END:variables

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
