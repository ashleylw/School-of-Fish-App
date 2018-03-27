package edu.usc.cs401.schooloffish.Model;

/**
 * Created by Ashley Walker on 3/24/2018.
 */

import java.io.Serializable;
import java.util.UUID;

/**
 * An object with a name and unique identifier.
 */

public class NamedObject implements Serializable {
    private static final long serialVersionUID = 2L;

    private String name = null;
    private String identifier = null;

    /**
     * Create a new NamedObject with a specific name and identifier.
     * @param name The string name of the object.
     * @param identifier The string identifier of the object.
     */
    public NamedObject(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    /**
     * Create a new NamedObject with a name and automatically generate an identifier.
     * @param name The string name of the object.
     */
    public NamedObject(String name) {
        this.name = name;
        UUID id = UUID.randomUUID();
        this.identifier = "" + id;
    }

    public void setName(String name) {this.name =  name;}

    public String getName() {
        return this.name;
    }

    public String getID() {
        return this.identifier;
    }

    public void setID(String identifier) { this.identifier = identifier; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof NamedObject)) {
            return false;
        }

        NamedObject other = (NamedObject)obj;
        return (other.getID().equals(this.getID()));
    }

    @Override
    public int hashCode() {
        return this.getID().hashCode();
    }
}

