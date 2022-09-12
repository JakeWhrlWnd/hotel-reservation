package model;

import java.util.Date;

public record Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

    public boolean isRoomReserved(Date checkInDate, Date checkOutDate) {
        return checkInDate.before(this.checkOutDate) && checkOutDate.after(this.checkInDate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return customer.equals(that.customer) && room.equals(that.room) && checkInDate.equals(that.checkInDate) && checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public String toString() {
        return "Customer: " + customer() + "\nRoom: " + room() + "\nCheck-in: " + checkInDate() + "\nCheck-out: " + checkOutDate();
    }
}
