
import java.util.ArrayList;
import java.util.List;

public class Group implements UserComponent, Visitable{
    private String groupId;
    private List<UserComponent> components = new ArrayList<>();

    public String getGroupId() {
        return groupId;
    }

    public Group(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return groupId + " (Group)";
    }

    @Override
    public void print(){
        System.out.println("Current Tree (Group): " + groupId + " (G)");
        for(UserComponent component: components){
            component.print();
        }
    }

    @Override
    public UserComponent getComponent() {
        return this;
    }

    @Override
    public void addUserComponent(UserComponent newUserComponent) {
        // only adds groups and leafs, stores it in a data structure called components
        components.add(newUserComponent);
    }

    @Override
    public void accept(Visitor v){
        v.visit(this);
        if(components != null){
            for (UserComponent component: components){
                if (component instanceof Group){
                    ((Group) component).accept(v);
                }else{
                    ((User) component).accept(v);
                }
            }
        }
    }
}
