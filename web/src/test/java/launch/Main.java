package launch;

import static org.apache.catalina.Globals.ALT_DD_ATTR;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Created by minisha on 22/1/16.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat8081 = tomcat(8081, "cicportal-web");
        tomcat8081.getServer().await();
    }

    private static Tomcat tomcat(int port, String webappMavenModule) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        addWebapp(tomcat, webappMavenModule);
        tomcat.start();

        System.out.println("Open browser at");
        System.out.println("  http://localhost.cic.com.sg:" + port + "/" + webappMavenModule + "/login");
        System.out.println("(make sure your local hosts file redirects localhost.cic.com.sg to 127.0.0.1)");
        System.out.println();

        return tomcat;
    }

    private static void addWebapp(Tomcat tomcat, String webappMavenModule) throws ServletException {
        File file = new File(webappMavenModule + "/src/main/webapp/");
        if (!file.exists()) {
            throw new RuntimeException(
                    "Failed starting; make sure to run this from the root of the Maven project (ie. CIC/cicportal)");
        }
        tomcat.addWebapp("/" + webappMavenModule, file.getAbsolutePath()) //
                .getServletContext() //
                .setAttribute(ALT_DD_ATTR, webappMavenModule + "/src/main/webapp/WEB-INF/web.xml");
    }

}
