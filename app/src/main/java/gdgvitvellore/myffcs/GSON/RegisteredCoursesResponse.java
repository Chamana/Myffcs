package gdgvitvellore.myffcs.GSON;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaman on 13/6/17.
 */

public class RegisteredCoursesResponse {
    Boolean status;
    String name,regno,message;
    int total_cred;
    List<AllotedCourse> allotedCourse;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal_cred() {
        return total_cred;
    }

    public void setTotal_cred(int total_cred) {
        this.total_cred = total_cred;
    }


    public List<AllotedCourse> getAllotedCourse() {
        return allotedCourse;
    }

    public void setAllotedCourse(List<AllotedCourse> allotedCourse) {
        this.allotedCourse = allotedCourse;
    }

    public class AllotedCourse{
        String _id,Faculty,Crscd,Crsnm,Slot,Venue,Type,Mode;
        int Count,Credits;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

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

        public int getCredits() {
            return Credits;
        }

        public void setCredits(int credits) {
            Credits = credits;
        }

        public String getVenue() {
            return Venue;
        }

        public void setVenue(String venue) {
            Venue = venue;
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

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }
    }
}
