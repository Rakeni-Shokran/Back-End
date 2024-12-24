package org.example.rakkenishokran.Authorization;



public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("ADMIN:create"),

    LOT_MANAGER_READ("lot_manager:read"),
    LOT_MANAGER_UPDATE("lot_manager:update"),
    LOT_MANAGER_DELETE("lot_manager:delete"),
    LOT_MANAGER_CREATE("lot_manager:create"),

    DRIVER_READ("driver:read"),
    DRIVER_UPDATE("driver:update"),
    DRIVER_DELETE("driver:delete"),
    DRIVER_CREATE("driver:create");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
