package razvan.com.traveljournal.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import razvan.com.traveljournal.models.Trip;

@Dao
public interface TripDao {

    @Insert
    public void addTrip(Trip mTrip);

    @Delete
    public void deleteTrip(Trip mTrip);
}
