/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.acls.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import com.sharifpro.eurb.DaoFactory;
import com.sharifpro.eurb.management.security.dao.GroupsDao;
import com.sharifpro.eurb.management.security.exception.GroupsDaoException;
import com.sharifpro.eurb.management.security.model.Groups;

/**
 * Basic implementation of {@link SidRetrievalStrategy} that creates a {@link Sid} for the principal, as well as
 * every granted authority the principal holds. Can optionally have a <tt>RoleHierarchy</tt> injected in order to
 * determine the extended list of authorities that the principal is assigned.
 * <p>
 * The returned array will always contain the {@link PrincipalSid} before any {@link GrantedAuthoritySid} elements.
 *
 * @author Ben Alex
 */
public class SidRetrievalStrategyImpl implements SidRetrievalStrategy {

    private GroupsDao groupManager = DaoFactory.createGroupsDao();

    public SidRetrievalStrategyImpl() {
    }

    public SidRetrievalStrategyImpl(GroupsDao groupManager) {
        Assert.notNull(groupManager, "GroupsDao must not be null");
        this.groupManager = groupManager;
    }

    //~ Methods ========================================================================================================

    public List<Sid> getSids(Authentication authentication) {
        Collection<Groups> groups;
		try {
			groups = groupManager.findCurrentGroupsForUser(authentication.getName());
	        List<Sid> sids = new ArrayList<Sid>(groups.size() + 1);
	
	        sids.add(new PrincipalSid(authentication));
	
	        for (Groups grp : groups) {
	            sids.add(new GrantedAuthoritySid(grp));
	        }
	
	        return sids;
		} catch (GroupsDaoException e) {
			throw new RuntimeException(e);
		}
    }
}
