package gr.hua.it213102.ergasia;

/**
 * Created by Jepeto on 25/11/2017.
 */

public class User {

    private int id;
    private String uid, dt;
    private float lon, lat;

    public User(String uid, float lon, float lat, String dt) {
        this.uid = uid;
        this.lon = lon;
        this.lat = lat;
        this.dt = dt;
    }

    public User(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
