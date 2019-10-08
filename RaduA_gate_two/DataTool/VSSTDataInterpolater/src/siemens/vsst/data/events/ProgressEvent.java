/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siemens.vsst.data.events;

/**
 * Conversion Progress Event
 * 
 * @author michael
 */
public class ProgressEvent
{
    private String title;
    private float currentValue;
    private float maxValue;
    
    
    public ProgressEvent(float current, float max) {
        this.currentValue   = current;
        this.maxValue       = max;
    }
    
    
    public ProgressEvent(float current, float max, String title) {
        this.currentValue   = current;
        this.maxValue       = max;
        this.title          = title;
    }
    
    
    public float getCurrentValue() {
        return currentValue;
    }
    
    
    public float getMaxValue() {
        return maxValue;
    }
    
    
    public float getPercentage() {
        return Math.max(currentValue / maxValue, 0);
    }
    
    
    public String getTitle() {
        return title;
    }
}
