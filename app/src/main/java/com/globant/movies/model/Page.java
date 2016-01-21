
package com.globant.movies.model;

import java.util.HashMap;
import java.util.Map;

public class Page {

    private Page_ page;
    private Boolean cached;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The page
     */
    public Page_ getPage() {
        return page;
    }

    /**
     * 
     * @param page
     *     The page
     */
    public void setPage(Page_ page) {
        this.page = page;
    }

    /**
     * 
     * @return
     *     The cached
     */
    public Boolean getCached() {
        return cached;
    }

    /**
     * 
     * @param cached
     *     The cached
     */
    public void setCached(Boolean cached) {
        this.cached = cached;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
