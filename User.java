import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class User extends Subject implements Observer, UserComponent, Visitable{
    private String userId;
    private List<User> followers;
    private List<User> following;

    private List<String> newsfeed;
    private List<String> tweets;
    private String incomingTweet;

    private long creationTime;
    private long lastUpdateTime;

    public User(String userId){
        this.userId = userId;
        this.creationTime = System.currentTimeMillis();
        tweets = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        newsfeed = new ArrayList<>();
    }

    public String getCreationTime(){
        Date currentDate = new Date(creationTime);
        System.out.println("Creation time stamp:");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        return dateFormat.format(currentDate);
    }

    public void tweetTimeStamp(){
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public String getTimeStamp(){
        Date currentDate = new Date(lastUpdateTime);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        return dateFormat.format(currentDate);
    }

    public String getUserId() {
        return userId;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<String> getTweets() {
        return tweets;
    }

    public List<String> getNewsfeed() {
        return newsfeed;
    }

    public void printId(){
        System.out.println("User ID: " + userId);
    }

    public boolean checkAlreadyFollowing(String user){
        for(User x: following){
            if(x.getUserId().equals(user)){
                return true;
            }
        }
        return false;
    }

    public void addToFollowers( User followerUser){
        followers.add(followerUser);
    }

    public void addToFollowing(User followingUser){
        following.add(followingUser);
    }

    public String getIncomingTweet() {
        return incomingTweet;
    }

    public void tweetMessage(String message){
        // incoming tweets from user
        tweets.add(message);
        this.incomingTweet = getUserId() + ": " + message;
        notifyObserver();
    }

    @Override
    public String toString() {
        // override to only return the ID of the object
        return getUserId();
    }

    @Override
    public void updateFeed(Subject tweet) {
        incomingTweet = ((User) tweet).getIncomingTweet();
        newsfeed.add(incomingTweet);
        tweetTimeStamp();
    }

    @Override
    public UserComponent getComponent() {
        return null;
    }

    @Override
    public void print(){
        System.out.println("Current Tree (User): " + userId);
    }

    @Override
    public void addUserComponent(UserComponent newUserComponent) {

    }

    @Override
    public void accept(Visitor v){
        v.visit(this);
    }
}
