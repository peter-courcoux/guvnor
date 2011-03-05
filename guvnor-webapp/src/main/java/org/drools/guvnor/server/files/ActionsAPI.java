/**
* Copyright 2010 JBoss Inc
*
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
*/

package org.drools.guvnor.server.files;

import org.drools.guvnor.server.RepositoryPackageService;
import org.drools.guvnor.server.ServiceImplementation;
import org.drools.repository.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Basic API for executing simple actions against Guvnor:
 * compilation and snapshot creation for packages.
 *
 * Fix for GUVNOR-1080
 */
public class ActionsAPI {

    public enum Parameters {

        PackageName {
            public String toString() {
                return "package-name";
            }
        },

        SnapshotName {
            public String toString() {
                return "snapshot-name";
            }
        };

    }

    /**
     * Post is for actions.
     *
     * URL should be:  http://servername:port/action/compile
     *                 http://servername:port/action/snapshot
     *
     * parameters:  package-name
     *              snapshot-name
     *
     * @throws IOException
     * @throws RulesRepositoryException */
    public void post(RepositoryPackageService service, RulesRepository repository,
            HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        try {
            Map<String,String> parameters = request.getParameterMap();
            String[] pathstr = split (request.getPathTranslated());
            String packageName = parameters.get(Parameters.PackageName.toString());
            if (pathstr [ 0 ].equals("compile")) {
                if (repository.containsPackage(packageName)) {
                    PackageIterator iter = repository.listPackages();
                    while (iter.hasNext()) {
                        PackageItem p = iter.next();
                        if (p.getName().equals(packageName)) {
                            String uuid = p.getUUID();
                            service.buildPackage(uuid, true);
                            break;
                        }}
                    }
            } else if (pathstr [ 0 ].equals ("snapshot"))
                if(repository.containsPackage(packageName)) {
                    repository.createPackageSnapshot(packageName, (String)
                        parameters.get(Parameters.SnapshotName.toString()));
            } else {
                throw new RulesRepositoryException ("Unknown action request: "
                        + request.getContextPath());
            }
            
            response.setContentType( "text/html" );
            response.setStatus(200);
            response.getWriter().write("OK");

        } catch (Exception e) {
            throw new IOException (e.getMessage());
        }
    }

    /**
     * Split from RestAPI
     */
    String[] split(String path) throws UnsupportedEncodingException {
        if (path.indexOf("action") > -1) {
            path = path.split("action")[1];
        }
        if (path.startsWith("/")) path = path.substring(1);
        String[] bits = path.split("/");
        for (int i = 0; i < bits.length; i++) {
            bits[i] = URLDecoder.decode(bits[i], "UTF-8");
        }
        return bits;
    }
}
