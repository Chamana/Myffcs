package gdgvitvellore.myffcs.GSON;

import java.util.List;

/**
 * Created by chaman on 11/6/17.
 */

public class CourseResponse {
   String _id,Faculty,Crscd,Crsnm,Slot,Credits,Venue,_v,Type,Mode;
    String[] Count;


    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getCrscd() {
        return Crscd;
    }

    public void setCrscd(String crscd) {
        Crscd = crscd;
    }

    public String getCrsnm() {
        return Crsnm;
    }

    public void setCrsnm(String crsnm) {
        Crsnm = crsnm;
    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String slot) {
        Slot = slot;
    }

    public String getCredits() {
        return Credits;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_v() {
        return _v;
    }

    public void set_v(String _v) {
        this._v = _v;
    }

    public String[] getCount() {
        return Count;
    }

    public void setCount(String[] count) {
        Count = count;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }
}
