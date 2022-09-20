package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(final Customer customer, final IRoom room, final Date checkInDate, final Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation reservation)) return false;
        return Objects.equals(room, reservation.room) && Objects.equals(checkInDate, reservation.checkInDate) && Objects.equals(checkOutDate, reservation.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "Customer: " + customer + "\nRoom: " + room + "\nCheck-in: " + checkInDate + "\nCheck-out: " + checkOutDate;
    }
}
