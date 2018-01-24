package android.project.pnpc.fr.pnpc_android.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by stephen on 21/01/18.
 */

public class Waypoint {

    /**
     * Waypoint identifier.
     */
    private long id;

    /**
     * Beacon identifier of current waypoint.
     * Identifier for identify beacon.
     */
    private String beaconId;

    /**
     * Latitude of current waypoint.
     */
    private Double latitude;

    /**
     * Longitude of current waypoint.
     */
    private Double longitude;

    /**
     * The list of all the waypoints of the current waypoint.
     */
    private Collection<Passage> passages;

    /**
     * Default constructor
     */
    public Waypoint() {
        this.passages = new ArrayList<>();
    }

    /**
     * Constructor
     * To create a builder. @see `Builder`
     * @param builder builder Builder object.
     */
    private Waypoint(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.passages = new ArrayList<>();
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;

        public Waypoint build() {
            return new Waypoint(this);
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }
    }

}

