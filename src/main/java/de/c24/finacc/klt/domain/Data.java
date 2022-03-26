package de.c24.finacc.klt.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Class for responce of currency web API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
public class Data implements Serializable {
    private final static long serialVersionUID = 454433333L;

    @JsonIgnore
    private HashMap<String, Details> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public HashMap<String, Details> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public Details getProperty(String name) {
        return this.additionalProperties.get(name);
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Details value) {
        this.additionalProperties.put(name, value);
    }
}
