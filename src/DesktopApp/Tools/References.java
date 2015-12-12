package DesktopApp.Tools;

import java.net.URL;

public class References {

    public String getCss_Study(){
        URL url = this.getClass().getResource("..\\..\\GUI\\minorScenes\\style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        return url.toExternalForm();
    }

}
