package services;

import models.Status;

/**
 * Created by fernando on 22/03/15.
 */
public class StatusTransformer {
    public static int statusToInt(Status status) {
        switch (status) {
            default:
            case NOT_STARTED:
                return 0;
            case DOING:
                return 1;
            case DONE:
                return 2;
            case STOPPED:
                return 3;
        }
    }

    public static Status intToStatus(int status) {
        switch (status) {
            default:
            case 0:
                return Status.NOT_STARTED;
            case 1:
                return Status.DOING;
            case 2:
                return Status.DONE;
            case 3:
                return Status.STOPPED;
        }
    }

    public static String statusToString(Status status) {
        switch (status) {
            default:
            case NOT_STARTED:
                return "Not started";
            case DOING:
                return "In progress";
            case DONE:
                return "Done";
            case STOPPED:
                return "Stopped";
        }
    }
}
