package DesktopApp.Tools;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// Create my own listener class (if text changed)
public class MyClassWithText {
    protected PropertyChangeSupport propertyChangeSupport;
    private String text;

    public MyClassWithText () {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setText(String text) {
        String oldText = this.text;
        this.text = text;
        propertyChangeSupport.firePropertyChange("MyTextProperty", oldText, text);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
