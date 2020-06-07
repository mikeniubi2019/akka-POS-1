package bestMessage;

import position.PosValue;

public class GBestMessage {
    final PosValue value;

    public GBestMessage(PosValue value) {
        this.value = value;
    }

    public PosValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GBestMessage{" +
                "value=" + value +
                '}';
    }
}
