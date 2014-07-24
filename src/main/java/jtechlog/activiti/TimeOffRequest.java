package jtechlog.activiti;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeOffRequest implements Serializable {

    private String mail;

    private Date start;

    private Date end;

    public TimeOffRequest(String mail, Date start, Date end) {
        this.mail = mail;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return mail + "_" + new SimpleDateFormat("yyyy-MM-dd").format(start);
    }

    public String getMail() {
        return mail;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
