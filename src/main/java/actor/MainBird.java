package actor;

import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import bestMessage.GBestMessage;
import bestMessage.PBestMessage;
import position.PosValue;

public class MainBird extends UntypedAbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
    private PosValue gBest = null;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof PBestMessage){
            PBestMessage gBestMessage = (PBestMessage) message;
            PosValue posValue = gBestMessage.getValue();
            if (gBest==null||gBest.getValue()<posValue.getValue()){
                System.out.println(message+"\n");
                gBest = posValue;
                ActorSelection actorSelection = getContext().actorSelection("/user/bird_*");
                actorSelection.tell(new GBestMessage(gBest),getSelf());
            }
        }else {
            unhandled(message);
        }
    }
}
