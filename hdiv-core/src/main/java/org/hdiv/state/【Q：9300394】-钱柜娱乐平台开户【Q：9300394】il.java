/**
 * Copyright 2005-2016 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.state;

import java.util.UUID;

import org.hdiv.config.HDIVConfig;
import org.hdiv.context.RequestContext;
import org.hdiv.context.RequestContextHolder;
import org.hdiv.exception.HDIVException;
import org.hdiv.session.ISession;
import org.hdiv.state.scope.StateScope;
import org.hdiv.state.scope.StateScopeManager;
import org.hdiv.util.Constants;
import org.hdiv.util.HDIVErrorCodes;
import org.hdiv.util.HDIVStateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class containing utility methods for state.
 *
 * @author Roberto Velasco
 */
public class StateUtil {

	/**
	 * Commons Logging instance.
	 */
	private static final Logger log = LoggerFactory.getLogger(StateUtil.class);

	/**
	 * Hdiv configuration for this app. Access to user defined strategy.
	 */
	protected HDIVConfig config;

	/**
	 * User session wrapper.
	 */
	protected ISession session;

	/**
	 * State scope manager.
	 */
	protected StateScopeManager stateScopeManager;

	/**
	 * StateUtil initialization.
	 */
	public void init() {
		log.debug("StateUtil instance created.");
	}

	public UUID getPageId(final String requestState) {
		// Obtain State from a StateScopes
		StateScope stateScope = stateScopeManager.getStateScope(requestState);
		if (stateScope != null) {
			return null;
		}
		return HDIVStateUtils.getPageId(requestState);
	}

	@Deprecated
	public final IState restoreState(final RequestContext context, final String requestState) {
		return restoreState((RequestContextHolder) context, requestState);
	}

	public IState restoreState(final RequestContextHolder context, final String requestState) {

		IState restoredState = restoreMemoryState(context, requestState);

		if (restoredState == null) {
			throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE);
		}
		return restoredState;
	}

	@Deprecated
	public boolean isMemoryStrategy(final String value) {
		return true;
	}

	@Deprecated
	protected final IState restoreMemoryState(final RequestContext context, final String requestState) {
		return restoreMemoryState((RequestContextHolder) context, requestState);
	}

	/**
	 * Restore a state from Memory Strategy.
	 *
	 * @param context Context holder for request-specific state.
	 * @param requestState String that contains HDIV state received in the request
	 * @return State Restore state data from <code>request</code>.
	 */
	protected IState restoreMemoryState(final RequestContextHolder context, final String requestState) {

		IState restoredState;

		// Extract pageId and stateId from the state identifier
		int firstSeparator = requestState.indexOf(Constants.STATE_ID_SEPARATOR);
		int lastSeparator = requestState.lastIndexOf(Constants.STATE_ID_SEPARATOR);
		if (firstSeparator == -1 || lastSeparator == -1) {
			throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE);
		}

		String pId;
		String sId;
		try {
			pId = requestState.substring(0, firstSeparator);
			sId = requestState.substring(firstSeparator + 1, lastSeparator);
		}
		catch (StringIndexOutOfBoundsException e) {
			throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE, e);
		}

		int stateId;
		try {
			stateId = Integer.parseInt(sId);
		}
		catch (NumberFormatException e) {
			throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE, e);
		}

		// Obtain State from a StateScopes
		StateScope stateScope = stateScopeManager.getStateScope(requestState);

		if (stateScope != null) {
			restoredState = stateScope.restoreState(context, stateId);
			if (restoredState == null) {
				throw new HDIVException(HDIVErrorCodes.INVALID_PAGE_ID);
			}
			return restoredState;
		}

		restoredState = getStateFromSession(context, HDIVStateUtils.parsePageId(pId), stateId);
		return restoredState;
	}

	@Deprecated
	protected IState getStateFromSession(final RequestContext context, final UUID pageId, final int stateId) {
		return getStateFromSession((RequestContextHolder) context, pageId, stateId);
	}

	/**
	 * Restores the state using the identifier obtained from the <code>HDIVParameter</code> of the request.
	 *
	 * @param context Context holder for request-specific state.
	 * @param pageId current {@link IPage} id
	 * @param stateId current {@link IState} id
	 * @return State with all the page data.
	 */
	protected IState getStateFromSession(final RequestContextHolder context, final UUID pageId, final int stateId) {

		final IState sessionState = session.getState(context, pageId, stateId);

		if (sessionState == null) {
			throw new HDIVException(HDIVErrorCodes.INVALID_HDIV_PARAMETER_VALUE);
		}
		return sessionState;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(final HDIVConfig config) {
		this.config = config;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(final ISession session) {
		this.session = session;
	}

	/**
	 * @param stateScopeManager the stateScopeManager to set
	 */
	public void setStateScopeManager(final StateScopeManager stateScopeManager) {
		this.stateScopeManager = stateScopeManager;
	}
}
