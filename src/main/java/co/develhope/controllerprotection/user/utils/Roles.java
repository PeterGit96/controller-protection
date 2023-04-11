package co.develhope.controllerprotection.user.utils;

import co.develhope.controllerprotection.user.entities.User;

public class Roles {

    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public final static String ADMIN = "ADMIN";
    public final static String EDITOR = "EDITOR";
    public final static String VIEWER = "VIEWER";
    public static final String OWNER = "OWNER";
    public final static String REGISTERED = "REGISTERED";

    public static boolean hasRole(User user, String roleInput) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleInput));
    }

}
