package com.nano.portal.portlets.support;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.nano.portal.tools.QueryManager;

/**
 * Manages action & resource processing for {@link Support} portlet class.
 * 
 * @author segz
 *
 */

@Stateless
public class SupportService {
	
	@Inject
	private QueryManager queryManager ;

}
