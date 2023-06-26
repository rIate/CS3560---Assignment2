import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void register(Observer newObserver) {
        if(observers.contains(newObserver)){
            System.out.println("Already an observer !");
            return;
        }
        User x = (User)newObserver;
        System.out.println(x.getUserId() + " now registered to " + ((User)this).getUserId());
        observers.add(newObserver);
    }

    public void notifyObserver() {
        for(Observer observer: observers){
            observer.updateFeed(this);
        }
    }
}
