package com.consul.edu.educationconsultant.wrappers;

import java.util.List;

public class FilterWrapper {
    private List<String> filters;

    public FilterWrapper(List<String> filters) {
        this.filters = filters;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}
