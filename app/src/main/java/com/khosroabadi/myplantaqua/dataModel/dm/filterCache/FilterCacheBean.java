package com.khosroabadi.myplantaqua.dataModel.dm.filterCache;

/**
 * Created by Alireza on 1/2/2017.
 */

public class FilterCacheBean {
    private Integer id;
    private Integer selectedItemId;
    private Integer groupId;

    public static final String AttrId = "id";
    public static final String AttrSelectedItemId = "selectedItemId";
    public static final String AttrGroupId = "groupId";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(Integer selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
