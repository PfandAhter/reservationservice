package intern.freedesk.reservationservice.model;

public enum Status {
    RESERVED(2),
    ACTIVE(1),
    PASSIVE(0);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}