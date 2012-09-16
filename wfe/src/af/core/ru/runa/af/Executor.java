/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.af;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.PolymorphismType;

import ru.runa.InternalApplicationException;
import ru.runa.af.util.OracleCommons;

import com.google.common.base.Objects;

/*
 * Created on 01.07.2004
 */
/**
 * Represents an Executor. Executor is an abstract object of system that could perform different actions.
 * 
 */
@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = "EXECUTORS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IS_GROUP", discriminatorType = DiscriminatorType.STRING, length = 1)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlType(name = "Executor", namespace = "http://runa.ru/workflow/webservices")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Executor extends IdentifiableBaseImpl implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://runa.ru/workflow/webservices")
    private Long id;

    @XmlElement(namespace = "http://runa.ru/workflow/webservices")
    private Long version;

    @XmlElement(namespace = "http://runa.ru/workflow/webservices")
    private String name;

    @XmlElement(namespace = "http://runa.ru/workflow/webservices")
    private String description;

    @XmlElement(namespace = "http://runa.ru/workflow/webservices")
    protected String fullName;

    protected Executor() {
    }

    protected Executor(String name, String description) {
        ArgumentsCommons.checkNotEmpty(name, "Name");
        setName(name.trim());
        setDescription(description == null ? "" : description.trim());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_EXECUTORS")
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "NAME", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = OracleCommons.fixNullString(description);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = OracleCommons.fixNullString(name);
    }

    @Version
    @Column(name = "VERSION", nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "FULL_NAME", insertable = false, updatable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Update executor by copying data from other executor data. 
     * @param updateFrom Executor to copy data from.
     */
    public void update(Executor updateFrom) {
        if (updateFrom == null) {
            throw new IllegalArgumentException("failed to update executor from null executor");
        }
        if (getClass() != updateFrom.getClass()) {
            throw new InternalApplicationException("failed to update executor of type " + getClass().getSimpleName() + " from executor of type "
                    + updateFrom.getClass().getSimpleName());
        }
        setName(updateFrom.getName());
        setFullName(updateFrom.getFullName());
        setDescription(updateFrom.getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Executor)) {
            return false;
        }
        Executor executor = (Executor) obj;
        return Objects.equal(name, executor.name) && Objects.equal(description, executor.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description);
    }
    
    @Override
    public String toString() {
        return Executor.class.getName() + ":" + name;
    }

}
