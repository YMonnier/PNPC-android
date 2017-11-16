package android.project.pnpc.fr.pnpc_android.location;

/**
 * Created by stephen on 16/11/17.
 */

public class Coordinate {

    private double latitude;
    private double longitude;

    public Coordinate(){}

    public Coordinate(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static class Builder {
        private double latitude;
        private double longitude;
        private boolean sent = false;
        private int userId;

        public Coordinate build() {
            return new Coordinate(this);
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
