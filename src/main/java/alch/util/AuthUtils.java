package alch.util;

import alch.model.user.Role;
import alch.security.JwtSubject;
import alch.model.user.RoleType;
import alch.model.user.User;

import javax.servlet.http.HttpServletRequest;

public class AuthUtils {

    public static final String JWT_TOKEN_NAME = "jwtToken";
    public static final String LOGGED_IN_USER = "loggedInUser";
//    public static final String SHEET = "sheet";

    public static boolean isUserAdmin(User user) {
        return userHasRole(user, RoleType.ADMIN);
    }

    public static boolean isUserPlayer(User user) {
        return userHasRole(user, RoleType.PLAYER);
    }

    public static boolean userHasRole(User user, RoleType role) {
        for (Role userrole : user.getRoles()) {
            if (userrole.getRoleName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static long getUserId(HttpServletRequest request) {
        return ((JwtSubject)request.getAttribute(JWT_TOKEN_NAME)).getUserId();
    }

    public static User getLoggedInUser(HttpServletRequest request) {
        return ((User)request.getAttribute(LOGGED_IN_USER));
    }

//    public static Sheet getSheet(HttpServletRequest request) {
//        return ((Sheet)request.getAttribute(SHEET));
//    }
}
