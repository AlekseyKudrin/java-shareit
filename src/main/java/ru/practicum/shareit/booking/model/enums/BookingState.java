package ru.practicum.shareit.booking.model.enums;

public enum BookingState{
/*    ALL("ALL"),
    PAST("PAST"),
    CURRENT("CURRENT"),
    FUTURE("FUTURE"),
    WAITING("WAITING"),
    REJECTED("REJECTED");*/

    ALL,

    PAST,

    CURRENT,

    FUTURE,

    WAITING,

    REJECTED
/*    private final String state;

    BookingState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static BookingState getState(String state) {
        for (BookingState env : values()) {
            if (env.getState().equals(state)) {
                return env;
            }
        }
        throw new IllegalArgumentException("No enum found with state: [" + state + "]");
    }*/
}
