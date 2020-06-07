package actor;

import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import bestMessage.GBestMessage;
import bestMessage.PBestMessage;
import fitness.Finess;
import position.PosValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Bird extends UntypedAbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);
    private PosValue pBest;
    private PosValue gBest;
    private List<Double> velocity = new ArrayList<>(5);
    private List<Double> plane = new ArrayList<>(5);
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof GBestMessage){
            this.gBest = ((GBestMessage) message).getValue();
            updateVelocity();
            updatePlane();
            validatePlane();
            double newFit = Finess.finess(this.plane);
            if (newFit>pBest.getValue()){
                pBest=new PosValue(newFit,plane);
                PBestMessage pBestMessage = new PBestMessage(pBest);
                getSender().tell(pBestMessage,getSelf());
            }
        }else {
            unhandled(message);
        }

    }

    private void validatePlane() {
        if (plane.get(1)>400){
            plane.set(1,random.nextDouble(400));
        }
        double max = 400-1.1*plane.get(1);

        if (plane.get(2)>max||plane.get(2)<0)
        plane.set(2,random.nextDouble()*max);

        max = 484-1.21*plane.get(1)-1.1*plane.get(2);
        if (plane.get(3)>max||plane.get(3)<0)
        plane.set(3,random.nextDouble()*max);

        max = 532.4-1.331*plane.get(1)-1.21*plane.get(2)-1.1*plane.get(3);
        if (plane.get(4)>max||plane.get(4)<0)
        plane.set(4,random.nextDouble()*max);
    }


    private void updatePlane() {
        for (int index=1;index<plane.size();index++){
            velocity.set(index,plane.get(index)+velocity.get(index));
        }
    }

    private void updateVelocity() {
        for (int index=1;index<velocity.size();index++){
            double v = Math.random()*velocity.get(index)
                    + 2*Math.random()*(pBest.getX().get(index)-plane.get(index))
                    + 2*Math.random()*(gBest.getX().get(index)-plane.get(index));
            v = v>0?Math.min(v,5):Math.max(v,-5);
            velocity.set(index,v);
        }

    }

    @Override
    public void preStart(){
        double max;
        for (int index=0;index<5;index++){
            velocity.add(Double.NEGATIVE_INFINITY);
            plane.add(Double.NEGATIVE_INFINITY);
        }

        plane.set(1,random.nextDouble(400));
        max = 400-1.1*plane.get(1);

        if (max<0) max =0;
        plane.set(2,random.nextDouble()*max);

        max = 484-1.21*plane.get(1)-1.1*plane.get(2);
        plane.set(3,random.nextDouble()*max);

        max = 532.4-1.331*plane.get(1)-1.21*plane.get(2)-1.1*plane.get(3);
        plane.set(4,random.nextDouble()*max);

        double newFit = Finess.finess(plane);
        this.pBest = new PosValue(newFit,plane);
        PBestMessage pBestMessage = new PBestMessage(this.pBest);
        ActorSelection selection = getContext().actorSelection("/user/masterBird");
        selection.tell(pBestMessage,getSelf());
    }




}
