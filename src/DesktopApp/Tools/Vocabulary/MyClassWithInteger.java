package DesktopApp.Tools.Vocabulary;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MyClassWithInteger {
    protected PropertyChangeSupport propertyChangeSupport;
    private int digit;

    public MyClassWithInteger () {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setDigit(int digit) {
        int oldInt = this.digit;
        this.digit = digit;
        propertyChangeSupport.firePropertyChange("MyIntProperty", oldInt, digit);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
