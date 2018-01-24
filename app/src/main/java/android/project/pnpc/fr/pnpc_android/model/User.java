package android.project.pnpc.fr.pnpc_android.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by stephen on 21/01/18.
 */

public class User {

    private long id;

    /**
     * Nickname of the current user..
     * Is unique.
     */
    private String nickname;

    /**
     * Email of current user.
     * Is unique.
     */
    private String email;

    /**
     * Phone number of current user.
     */
    private String phoneNumber;

    /**
     * Password of current user.
     */
    private String mdp;

    /**
     * Identification token of current user.
     * It allows the user to use all the resources of the API.
     */
    private String authToken;

    /**
     * Device token of the current user.
     * Token for push notification.
     */
    private String deviceToken;

    /**
     * The list of all the passages of the current user.
     */
    private Collection<Passage> passages;

    /**
     * Default constructor
     */
    public User() {
        this.passages = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMdp() {
        return mdp;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public Collection<Passage> getPassages() {
        return passages;
    }

    /**
     * Constructor
     * To create a builder. @see `Builder`
     * @param builder Builder object.
     */
    private User(Builder builder) {
        this.nickname = builder.nickname;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.mdp = builder.mdp;
        this.passages = new ArrayList<>();
    }

    public static class Builder {
        private String nickname;
        private String email;
        private String phoneNumber;
        private String mdp;

        public User build() {
            return new User(this);
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setMdp(String mdp) {
            this.mdp = mdp;
            return this;
        }
    }

}
