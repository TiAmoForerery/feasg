/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Some helper methods for working with Java packages and versioning.
 *
 * @version $Revision: 664624 $
 */
public final class PackageHelper {
    private static final transient Log LOG = LogFactory.getLog(PackageHelper.class);

    private PackageHelper() {
        // Utility Class
    }

    /**
     * Returns true if the version number of the given package name can be found and is greater than or equal to the minimum version.
     *
     * For package names which include multiple dots, the dots are removed. So for example a spring version of 2.5.1 is converted to
     * 2.51 so you can assert that its >= 2.51 (so above 2.50 and less than 2.52 etc).
     *
     * @param packageName the Java package name to compare
     * @param minimumVersion the minimum version number
     * @return true if the package name can be determined and if its greater than or equal to the minimum value
     */
    public static boolean isValidVersion(String packageName, double minimumVersion) {
        try {
            Package spring = Package.getPackage(packageName);
            String value = spring.getImplementationVersion();
            if (value != null) {
                // lets remove any extra dots in the string...
                int idx = value.indexOf('.');
                if (idx >= 0) {
                    StringBuffer buffer = new StringBuffer(value.substring(0, ++idx));
                    int i = idx;
                    for (int size = value.length(); i < size; i++) {
                        char ch = value.charAt(i);
                        if (Character.isDigit(ch)) {
                            buffer.append(ch);
                        }
                    }
                    value = buffer.toString();
                }
                double number = Double.parseDouble(value);
                return number >= minimumVersion;
            }
        } catch (Exception e) {
            LOG.debug("Failed to find out " + packageName + " version: " + e, e);
        }
        return true;
    }
}
