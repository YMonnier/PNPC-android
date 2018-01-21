package android.project.pnpc.fr.pnpc_android.model;

/**
 * Created by stephen on 21/01/18.
 */

public class Passage {

    /**
     * Passage identifier.
     */
    private long id;

    /**
     * The user who owns the current passage*
     */
    private User user;

    /**
     * The waypoint who owns the current passage
     */
    private Waypoint waypoint;

    /**
     * Default constructor
     * Is require when the constructor is instanciate during
     * the injection
     */
    public Passage(){}

    /**
     * Constructor
     * @param user User object.
     * @param waypoint Waypoint object.
     */
    public Passage(User user, Waypoint waypoint) {
        this.user = user;
        this.waypoint = waypoint;
    }

}
