import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame implements UIBuild{

    JFrame frame;
    private JButton adduser;
    private JButton addGroup;
    private JButton userView;
    private JButton userTotal;
    private JButton messageTotal;
    private JButton groupTotal;
    private JButton positivePercentage;

    private JTextField userId;
    private JTextField groupId;
    private JLabel userIdLabel;
    private JLabel groupIdLabel;

    JScrollPane treeview;
    private JTree tree;

    UserController userController;

    private static AdminPanel INSTANCE = new AdminPanel();

    public AdminPanel(){
        userController = new UserController();
        treeManager();
        buttonManager();
        labelManager();
        textManager();
        frameManager();
    }

    public static AdminPanel getInstance(){
        if (INSTANCE == null) {
            synchronized (AdminPanel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdminPanel();
                }
            }
        }
        return INSTANCE;
    }

    public void frameManager(){
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mini Twitter");
        frame.setResizable(false);
        frame.setLayout(null);

        frame.add(treeview);
        frame.add(adduser);
        frame.add(addGroup);

        frame.add(userId);
        frame.add(groupId);

        frame.add(userIdLabel);
        frame.add(groupIdLabel);

        frame.add(userView);

        frame.add(userTotal);
        frame.add(messageTotal);

        frame.add(groupTotal);
        frame.add(positivePercentage);

        frame.setSize(715,330);
        frame.setVisible(true);

    }

    public void treeManager(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        tree = new JTree(root);
        treeview = new JScrollPane(tree);
        treeview.setBounds(5,5,245,275);
    }

    public void textManager(){
        userId = new JTextField();
        userId.setBounds(395, 5, 150, 35);

        groupId = new JTextField();
        groupId.setBounds(395, 41, 150, 35);
    }

    public void labelManager(){
        userIdLabel = new JLabel();
        userIdLabel.setText("User ID");
        userIdLabel.setBounds(340, 5, 150, 35);

        groupIdLabel = new JLabel();
        groupIdLabel.setText("Group ID");
        groupIdLabel.setBounds(337, 41, 150, 35);
    }

    public void addGroup(){
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Boolean emptyField = groupId.getText().trim().equals("");

        if(emptyField){
            JOptionPane.showMessageDialog(frame, "Error: Empty Group !");
            groupId.setText("");
            return;
        }

        if(userController.checkGroupExists(groupId.getText())){
            JOptionPane.showMessageDialog(frame, "Error: Group already exists !");
            groupId.setText("");
            return;
        }

        if(tree.getSelectionPaths() == null){
            userController.addGroup(groupId.getText(), rootNode);
            reloadField(model);
            return;
        }

        if(selectedNode.isRoot()){
            userController.addGroup(groupId.getText(), rootNode);
            reloadField(model);
            return;
        }

        if(selectedNode != null && selectedNode.getAllowsChildren()){
            userController.addGroupToGroup(groupId.getText(), selectedNode);
            reloadField(model);
        }

    }

    public void addUser(){
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Boolean emptyField = userId.getText().trim().equals("");

        if(emptyField){
            JOptionPane.showMessageDialog(frame, "Error: Empty User !");
            userId.setText("");
            return;
        }

        if(userController.checkUserExists(userId.getText())){
            JOptionPane.showMessageDialog(frame, "Error: User already exists !");
            userId.setText("");
            return;
        }

        if(tree.getSelectionPaths() == null){
            userController.addLeaf(userId.getText(), rootNode);
            reloadField(model);
            return;
        }

        if(selectedNode.isRoot()){
            userController.addLeaf(userId.getText(), rootNode);
            reloadField(model);
            return;
        }

        if(selectedNode != null){
            if(selectedNode.getAllowsChildren()){
                userController.addLeafToGroup(userId.getText(), selectedNode);
                reloadField(model);
            }
        }
    }

    public void reloadField(DefaultTreeModel model){
        model.reload();
        userId.setText("");
        groupId.setText("");
    }

    public void displayTotalUsers(){
        UserCount userVisitor = new UserCount();
        userController.iterateComponentCount(userVisitor);
        JOptionPane.showMessageDialog(frame, "Total Users: " + userVisitor.getCount());
    }

    public void displayTotalGroups(){
        GroupCount groupVisitor = new GroupCount();
        userController.iterateComponentCount(groupVisitor);
        JOptionPane.showMessageDialog(frame, "Total Groups: " + groupVisitor.getCount());
    }

    public void displayMessageTotal(){
        MessageCount messageCount = new MessageCount();
        userController.iterateComponentCount(messageCount);
        JOptionPane.showMessageDialog(frame, "Total Messages: " + messageCount.getMessageCount());
    }

    public void displayPositivePercent(){
        PositiveCount positiveVisitor = new PositiveCount();
        userController.iterateComponentCount(positiveVisitor);
        JOptionPane.showMessageDialog(frame, "Total Messages: " + positiveVisitor.calculatePercent());
    }

    public void openUserView(){
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        // if the selected node is not empty and is in fact a user (check by checking if node allows children)
        if(selectedNode != null && !selectedNode.getAllowsChildren()){
            User selectedUser = (User)selectedNode.getUserObject();
            System.out.println(selectedUser.getCreationTime());
            new UserUI(selectedUser).setVisible(true);
        }else {
            JOptionPane.showMessageDialog(frame, "Error: Select user from tree!");
        }

    }

    // public void checkValidIds(){
    //     ValidateID validateVisitor = new ValidateID();
    //     userController.iterateComponentCount(validateVisitor);
    //     System.out.println(validateVisitor.validate());
    //     JOptionPane.showMessageDialog(frame, "Users/Groups IDs : " + validateVisitor.validate());
    // }

    public void buttonManager(){
        adduser = new JButton();
        adduser.setText("Add User");
        adduser.setBounds(550, 5, 150, 35);
        adduser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        addGroup = new JButton();
        addGroup.setText("Add Group");
        addGroup.setBounds(550, 41, 150, 35);
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroup();
            }
        });

        userView = new JButton();
        userView.setText("Open User View");
        userView.setBounds(395, 85, 305, 35);
        userView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserView();
            }
        });

        userTotal = new JButton();
        userTotal.setText("User Total");
        userTotal.setBounds(390, 200, 150, 35);
        userTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTotalUsers();
            }
        });

        messageTotal = new JButton();
        messageTotal.setText("Message Total");
        messageTotal.setBounds(390, 240, 150, 35);
        messageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMessageTotal();
            }
        });

        groupTotal = new JButton();
        groupTotal.setText("Group Total");
        groupTotal.setBounds(550, 200, 150, 35);
        groupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTotalGroups();
            }
        });

        positivePercentage = new JButton();
        positivePercentage.setText("Positive Percentage");
        positivePercentage.setBounds(550, 240, 150, 35);
        positivePercentage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPositivePercent();
            }
        });

    }
}
