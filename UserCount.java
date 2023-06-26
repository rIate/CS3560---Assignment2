public class UserCount implements Visitor{
    private int count = 0;

    public UserCount(){

    }

    public void incrementCount(){
        count++;
    }

    public int getCount(){
        return count;
    }

    @Override
    public void visit(Group group) {

    }

    @Override
    public void visit(User user) {
        incrementCount();
    }
}
