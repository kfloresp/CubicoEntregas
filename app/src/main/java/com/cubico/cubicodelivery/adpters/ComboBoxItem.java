package com.cubico.cubicodelivery.adpters;

public class ComboBoxItem {
    private String value_data;
    private String display_data;

    public ComboBoxItem(String value_data, String display_data) {
        this.value_data = value_data;
        this.display_data = display_data;
    }

    public String getValue_data() {
        return value_data;
    }

    public void setValue_data(String value_data) {
        this.value_data = value_data;
    }

    public String getDisplay_data() {
        return display_data;
    }

    public void setDisplay_data(String display_data) {
        this.display_data = display_data;
    }
}
