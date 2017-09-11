package spitter;

import java.util.Date;

/**
 * Created by 陈忠意 on 2017/7/17.
 */
public class Spittle {

    private final Long id;
    private final Date time;
    private final String message;
    private Double latitude;
    private Double longitude;

    public Spittle(String message, Date time){
        this(message, time, null, null);
    }

    public Spittle(String message, Date time, Double longitude, Double latitude){
        this.id = null;
        this.message = message;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId(){
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
