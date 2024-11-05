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
package org.hdiv.phaseListeners;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.hdiv.logs.ComponentMessagesLog;
import org.hdiv.logs.Logger;
import org.hdiv.util.UtilsJsf;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 * Phase Listener that detects errors thrown by components of type Select (SelectOne or SelectMany) and registers them in the HDIV logger.
 * 
 * @author Gotzon Illarramendi
 */
public class ComponentMessagesPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ComponentMessagesPhaseListener.class);

	/**
	 * Utility class for managing validation messages
	 */
	private ComponentMessagesLog messagesLog;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.PROCESS_VALIDATIONS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(final PhaseEvent event) {

		if (messagesLog == null) {

			if (log.isDebugEnabled()) {
				log.debug("Initialize ComponentMessagesPhaseListener dependencies.");
			}

			WebApplicationContext wac = FacesContextUtils.getRequiredWebApplicationContext(event.getFacesContext());
			Logger logger = wac.getBean(Logger.class);
			messagesLog = new ComponentMessagesLog(logger);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(final PhaseEvent event) {

		FacesContext context = event.getFacesContext();
		boolean reqInitialized = UtilsJsf.isRequestInitialized(context);
		if (!reqInitialized) {
			return;
		}

		messagesLog.processMessages(event.getFacesContext());
	}
}
