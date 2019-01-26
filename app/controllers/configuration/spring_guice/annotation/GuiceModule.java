/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package controllers.configuration.spring_guice.annotation;


import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GuiceModuleRegistrar.class)
public @interface GuiceModule {

	/**
	 * Specifies which types are eligible for inclusion in Guice module
	 */
	Filter[] includeFilters() default {};

	/**
	 * Specifies which types are not eligible for inclusion in Guice module.
	 */
	Filter[] excludeFilters() default {};

	/**
	 * Specifies which names (by regex) are eligible for inclusion in Guice module
	 */
	String[] includePatterns() default {};

	/**
	 * Specifies which bean names (by regex) are not eligible for inclusion in Guice module.
	 */
	String[] excludePatterns() default {};

	/**
	 * Specifies which names (by simple wildcard match) are eligible for inclusion in Guice module
	 */
	String[] includeNames() default {};

	/**
	 * Specifies which bean names (by simple wildcard match) are not eligible for inclusion in Guice module.
	 */
	String[] excludeNames() default {};

}
