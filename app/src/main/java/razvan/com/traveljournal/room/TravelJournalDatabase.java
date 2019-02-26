package razvan.com.traveljournal.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import razvan.com.traveljournal.models.Trip;

@Database(entities = {Trip.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class TravelJournalDatabase extends RoomDatabase {

    public abstract TripDao tripDao();


}
