package com.edge.core.modules.security;

import com.edge.core.modules.common.EdgeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Document(collection = "USERS")
@EqualsAndHashCode(callSuper=false)
@Data
public class EdgeUserDetails extends EdgeEntity implements UserDetails {

    @Id
    private String internalId;

    @NotNull
    @Indexed(unique = true)
    private String profileId;

    private String password;
    private String gender;
    private String verificationCode;
    private String inactivationReason;

    @NotNull
    @Indexed(unique = true)
    private String completeNumber;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean enabled = true;

    private String[] grantedAuthorities;

    private String confirmPwd;

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(grantedAuthorities);
    }

    @Override
    public String getUsername() {
        return getInternalId();
    }

}