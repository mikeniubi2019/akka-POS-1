package bestMessage;

import position.PosValue;

public class PBestMessage {
    final PosValue value;

    public PBestMessage(PosValue value) {
        this.value = value;
    }

    public PosValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PBestMessage{" +
                "value=" + value +
                '}';
    }
}
