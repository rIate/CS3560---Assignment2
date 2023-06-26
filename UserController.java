import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;


public class UserController extends DefaultMutableTreeNode{
    private List<User> users;
    private List<String> groups;
    private List<UserComponent> allComponents = new ArrayList<>();

    UserController(){
        users = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void addLeaf(String userId, DefaultMutableTreeNode root){
        // adding leaf directly to the root node of the tree
        User x = new User(userId);
        // add to the gui tree
        DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(x);
        // allowing children is false since it is a leaf & add to gui tree
        newUser.setAllowsChildren(false);
        // add to gui JTree
        root.add(newUser);
        allComponents.add(x);
        users.add(x);
    }

    public void addGroup(String groupId, DefaultMutableTreeNode root){
        Group x = new Group(groupId);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(x);
        newGroup.setAllowsChildren(true);
        root.add(newGroup);
        // add to components list
        allComponents.add(x);
        groups.add(groupId);
    }

    public void addGroupToGroup(String groupId, DefaultMutableTreeNode selectedGroup){
        Group x = new Group(groupId);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(x);
        newGroup.setAllowsChildren(true);
        // add to gui JTree
        selectedGroup.add(newGroup);

        // add new group to existing group
        Group grabGroup = (Group)selectedGroup.getUserObject();
        grabGroup.addUserComponent(x);
        groups.add(groupId);

    }

    public void addLeafToGroup(String userId, DefaultMutableTreeNode selectedGroup){
        // check if the selected group has a parent pointer (subgroup)
        Group selectedGroupObject= (Group)selectedGroup.getUserObject();

        // adding a new leaf to group
        User x = new User(userId);
        // add to the gui node
        DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(x);
        // set allowing children to false, since it is a leaf
        newUser.setAllowsChildren(false);
        selectedGroup.add(newUser);

        selectedGroupObject.addUserComponent(x);
        users.add(x);
    }

    // testing purposes
    public void printAllComponents(){
        for(UserComponent component: allComponents){
            component.print();
        }
    }

    public User grabUser(String userId){
        for(User x: users){
            if(x.getUserId().equals(userId)){
                return x;
            }
        }
        return null;
    }

    public void iterateComponentCount(Visitor v){
        for(UserComponent component: allComponents){
            if(component instanceof Group){
                Group groupComponent = (Group)component;
                groupComponent.accept(v);
            }else{
                User userComponent = (User)component;
                userComponent.accept(v);
            }
        }
    }

    public boolean checkGroupExists(String groupId){
      for(String x: groups){
          if(x.equals(groupId)){
              return true;
          }
      }
      return false;
    }

    public boolean checkUserExists(String userId){
        for(User x: users){
            if(x.getUserId().equals(userId)){
                return true;
            }
        }
        return false;
    }
}
