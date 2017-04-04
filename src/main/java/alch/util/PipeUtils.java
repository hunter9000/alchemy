package alch.util;

import alch.model.Pipe;
import alch.request.PipePlaceRequest;

public class PipeUtils {

    public static boolean isPerfectMatch(Pipe pipe, PipePlaceRequest pipePlaceRequest) {
        return pipe != null
                && pipe.matchesDirection(pipePlaceRequest.getDir1())
                && pipe.matchesDirection(pipePlaceRequest.getDir2());
    }

    public static boolean isImperfectMatch(Pipe pipe, PipePlaceRequest pipePlaceRequest) {
        return pipe != null &&
                (pipe.matchesDirection(pipePlaceRequest.getDir1())
                || pipe.matchesDirection(pipePlaceRequest.getDir2()));
    }

}
