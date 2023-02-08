package ca.uhn.fhir.jpa.provider.r4;

/*-
 * #%L
 * HAPI FHIR JPA Server
 * %%
 * Copyright (C) 2014 - 2023 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.util.ExtensionUtil;
import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Hook for Consent pre-save additions.
 *
 * @deprecated - we just use Consumer now
 * TODO delete this.
 */
@Deprecated(since = "6.3.6", forRemoval = true)
public interface IConsentExtensionProvider extends IMemberMatchConsentHook {
	Logger ourLog = LoggerFactory.getLogger(IConsentExtensionProvider.class);

	Collection<IBaseExtension> getConsentExtension(IBaseResource theConsentResource);

	default void accept(IBaseResource theResource) {
		Collection<IBaseExtension> extensions = getConsentExtension(theResource);

		for (IBaseExtension ext : extensions) {
			IBaseExtension<?, ?> e = ExtensionUtil.addExtension(theResource, ext.getUrl());
			e.setValue(ext.getValue());
		}
		ourLog.trace("{} extension(s) added to Consent", extensions.size());
	}

}