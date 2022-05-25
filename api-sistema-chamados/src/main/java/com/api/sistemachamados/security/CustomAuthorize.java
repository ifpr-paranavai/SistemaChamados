package com.api.sistemachamados.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthorize implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permissions) {
        if(auth == null) {
            return false;
        }
        if(permissions instanceof Collection) {
            return hasPrivilege(auth, (Collection) permissions);
        }
        if(permissions instanceof String) {
//          String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
//          return hasPrivilege(auth, permissions.toString().toUpperCase());
            return false;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
//        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
        return false;
    }

    private boolean hasPrivilege(Authentication auth, Collection permissions) {
        List<String> permissionsList = (List<String>) permissions.stream().map(Object::toString).collect(Collectors.toList());
        int matches = 0;
        for(String targetTypeAndPermission : permissionsList) {
            if(matches >= permissionsList.size()) {
                break;
            }
            int positionOf_ = targetTypeAndPermission.indexOf("_");
            targetTypeAndPermission.toUpperCase();
            String targetType = targetTypeAndPermission.substring(0, positionOf_);
            for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
                String userPermission = grantedAuth.getAuthority().toUpperCase();
                String authTargetType = userPermission.substring(0, positionOf_);
                if (authTargetType.equals(targetType)) {
                    if(targetTypeAndPermission.equals(grantedAuth.getAuthority())) {
                        matches++;
                        break;
                    }
                }
            }
        }
        if(matches >= permissionsList.size()) {
            return true;
        }
        return false;
    }
}
