package com.miraclem4n.dropclear.types;

import com.miraclem4n.dropclear.objects.ConfigObject;
import com.miraclem4n.dropclear.util.ConfigUtil;
import com.miraclem4n.dropclear.util.MessageUtil;

public enum ConfigType {
    MESSAGE_NOT_NUMBER("message.notNumber"),
    MESSAGE_NEGATIVE_VALUE("message.negativeValue"),
    MESSAGE_TOO_FAR("message.tooFar"),
    MESSAGE_CANT_FIND("message.cantFind"),
    MESSAGE_NO_PERM("message.noPerm"),
    MESSAGE_ITEMS_CLEARED("message.itemCleared"),

    INT_MAX_KILL("format.maxKill"),
    INT_MAX_ADMIN_KILL("format.maxAdminKill");

    private final String option;

    ConfigType(String option) {
        this.option = option;
    }

    public ConfigObject getObject() {
        Object value = ConfigUtil.getConfig().get(option);

        if (value instanceof String) {
            String val = (String) value;

            value = MessageUtil.addColour(val);
        }

        return new ConfigObject(value);
    }

    public String getValue() {
        return option;
    }
}
