package com.sharifpro.eurb.management.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.sharifpro.eurb.management.mapping.model.PersistableObject;
import com.sharifpro.util.SharifProApplicationContext;

/**
 * Models core user information retrieved by a {@link UserDetailsService}.
 * <p>
 * Developers may use this class directly, subclass it, or write their own {@link UserDetails} implementation from
 * scratch.
 * <p>
 * {@code equals} and {@code hashcode} implementations are based on the {@code username} property only, as the
 * intention is that lookups of the same user principal object (in a user registry, for example) will match
 * where the objects represent the same user, not just when all the properties (authorities, password for
 * example) are the same.
 * <p>
 * Note that this implementation is not immutable. It implements the {@code CredentialsContainer} interface, in order
 * to allow the password to be erased after authentication. This may cause side-effects if you are storing instances
 * in-memory and reusing them. If so, make sure you return a copy from your {@code UserDetailsService} each time it is
 * invoked.
 *
 * @author Mohammad Dashti
 */
public class User extends PersistableObject implements UserDetails, Serializable
{
	private static final long serialVersionUID = SharifProApplicationContext.SERIAL_VERSION_UID;

	/** 
	 * This attribute maps to the column username in the users table.
	 */
	protected String username;

	/** 
	 * This attribute maps to the column password in the users table.
	 */
	protected String password;

	/** 
	 * This attribute maps to the column email in the users table.
	 */
	protected String email;

	/** 
	 * This attribute maps to the column enabled in the users table.
	 */
	protected boolean enabled;
	private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;

	private final Set<GrantedAuthority> authorities;
	
	private final List<Sid> sidList;

	/**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public User(PersistableObject parent, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(parent, username, password, true, true, true, true, authorities, new ArrayList<Sid>(0));
    }
    
	/**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public User(PersistableObject parent, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this(parent, username, password, enabled, enabled, enabled, enabled, authorities, new ArrayList<Sid>(0));
    }
    
	/**
	 * Method 'Users'
	 * 
	 */
	/**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param username the username presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not
     *        expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials
     *        have not expired
     * @param accountNonLocked set to <code>true</code> if the account is not
     *        locked
     * @param authorities the authorities that should be granted to the caller
     *        if they presented the correct username and password and the user
     *        is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed
     *         either as a parameter or as an element in the
     *         <code>GrantedAuthority</code> collection
     */
    public User(PersistableObject parent, String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, List<Sid> sidList) {
    	if(parent != null) {
	    	this.setId(parent.getId());
	    	this.setObjectType(parent.getObjectType());
	    	this.setCreator(parent.getCreator());
	    	this.setCreateDate(parent.getCreateDate());
	    	this.setModifier(parent.getModifier());
	    	this.setModifyDate(parent.getModifyDate());
    	}
    	
        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        this.sidList = sidList;
    }

	/**
	 * Method 'getUsername'
	 * 
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Method 'setUsername'
	 * 
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Method 'getPassword'
	 * 
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Method 'setPassword'
	 * 
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Method 'isEnabled'
	 * 
	 * @return boolean
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Method 'setEnabled'
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing the
     * same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof User) {
            return username.equals(((User) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

	/**
	 * Method 'createPk'
	 * 
	 * @return UsersPk
	 */
	public UserPk createPk()
	{
		return new UserPk(id);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Email: ").append(this.email).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }
	
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = -4941960937492320541L;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

	public List<Sid> getSidList() {
		return sidList;
	}

}
