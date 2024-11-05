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
package org.hdiv.config.annotation.struts1;

import org.hdiv.config.annotation.condition.ConditionalOnFramework;
import org.hdiv.config.annotation.condition.SupportedFramework;
import org.hdiv.config.multipart.IMultipartConfig;
import org.hdiv.config.multipart.StrutsMultipartConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains the configuration beans for Struts1 framework support.
 *
 * @since 2.1.7
 */
@Configuration
@ConditionalOnFramework(SupportedFramework.STRUTS1)
public class Struts1ConfigurationSupport {

	@Bean
	public IMultipartConfig securityMultipartConfig() {
		return new StrutsMultipartConfig();
	}
}
