package com.nano.portal.portlets.poolmanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.nano.portal.tools.QueryManager;

/**
 * Manages action & resource processing for {@link PoolManagement} portlet class.
 * 
 * @author segz
 *
 */

@Stateless
public class PoolService {
	
	@Inject
	private QueryManager queryManager ;

}
