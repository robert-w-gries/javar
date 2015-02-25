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

package org.codavaj.process.loader;

import org.codavaj.ProcessException;

import org.codavaj.process.AbstractProcess;
import org.codavaj.type.TypeFactory;

import java.io.File;
import java.util.jar.JarFile;


/**
 * Load the complete contents of a Jar file into a TypeFactory.
 */
public class JarLoader extends AbstractProcess {
    private String jarFileName;
    private TypeFactory typeFactory;
    
    /**
     * Creates a new JarLoader object.
     */
    public JarLoader() {
    }

    /**
     * Process a jar file by listing it's contents and loading each
     * Class and Interface into a TypeFactory.
     *
     * @throws ProcessException failure to process.
     */
    public void process() throws ProcessException {
        File jarFile = new File(jarFileName);

        if (!jarFile.exists()) {
            throw new ProcessException(jarFileName + " does not exist.");
        }

        try {
            JarFile jar = new JarFile(jarFileName);
            
            typeFactory = TypeFactory.getInstance(jar);
        } catch ( Exception e ) {
            warning( "JarLoader failed!", e );
            throw new ProcessException(e);
        }
    }

    /**
     * Return the TypeFactory generated by the loaded Jar file. Only availible
     * after JarLoader#process() has been called.
     * 
     * @return the TypeFactory generated from the input Jar file.
     */
    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    /**
     * Get the jar filename which is used for loading a TypeFactory.
     * 
     * @return the jar filename.
     */
    public String getJarFileName() {
        return jarFileName;
    }

    /**
     * Set the filename of the Jar file to use.
     * 
     * @param jarFileName the Jar file name.
     */
    public void setJarFileName(String jarFileName) {
        this.jarFileName = jarFileName;
    }
    
}