public class GroupCount implements Visitor{
    private int count = 0;

    public GroupCount(){

    }

    public void incrementCount(){
        count++;
    }

    public int getCount(){
        return count;
    }

    @Override
    public void visit(Group group) {
        incrementCount();
    }

    @Override
    public void visit(User user) {

    }
}
