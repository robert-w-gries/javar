/*
 *   Copyright 2005 Peter Klauser
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.codavaj.type;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 */
public class Method extends Modifiable {
    private String name;
    private String typeParameters; // generics, see http://java.sun.com/docs/books/jls/third_edition/html/classes.html#8.4.1
    private Parameter returnParameter; // ignore name
    private List parameterList = new ArrayList();
    private List throwsList = new ArrayList();
    private List comment = null;
    private String defaultValue = null;
    
    Method() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param name DOCUMENT ME!
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List getParameterList() {
        return parameterList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param parameter DOCUMENT ME!
     */
    public void addParameter(Parameter parameter) {
        parameterList.add(parameter);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Parameter getReturnParameter() {
        return returnParameter;
    }

    /**
     * DOCUMENT ME!
     *
     * @param returnParameter DOCUMENT ME!
     */
    public void setReturnParameter(Parameter returnParameter) {
        this.returnParameter = returnParameter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List getThrowsList() {
        return throwsList;
    }

    /**
     * DOCUMENT ME!
     *
     * @param typename DOCUMENT ME!
     */
    public void addThrows(String typename) {
        throwsList.add(typename);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List getComment() {
        return comment;
    }

    /**
     * DOCUMENT ME!
     *
     * @param comment DOCUMENT ME!
     */
    public void setComment(List comment) {
        this.comment = comment;
    }

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the typeParameters
	 */
	public String getTypeParameters() {
		return typeParameters;
	}

	/**
	 * @param typeParameters the typeParameters to set
	 */
	public void setTypeParameters(String typeParameters) {
		this.typeParameters = typeParameters;
	}
}
