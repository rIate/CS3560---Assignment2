public class MessageCount implements Visitor{
    private int count = 0;

    public void incrementMessageCount(int messsageCount){
        count = count + messsageCount;
    }

    public int getMessageCount(){
        return count;
    }

    @Override
    public void visit(Group group) {

    }

    @Override
    public void visit(User user) {
        incrementMessageCount(user.getTweets().size());
    }
}
