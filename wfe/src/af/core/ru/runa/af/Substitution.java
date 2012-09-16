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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.PolymorphismType;

import ru.runa.InternalApplicationException;
import ru.runa.af.util.OracleCommons;

/**
 * Created on 27.01.2006
 * 
 */
@Entity
@org.hibernate.annotations.Entity(polymorphism = PolymorphismType.EXPLICIT)
@Table(name = "SUBSTITUTIONS", uniqueConstraints = @UniqueConstraint(columnNames = { "POSITION_INDEX", "ACTOR_ID" }))
@DiscriminatorColumn(name = "IS_TERMINATOR", discriminatorType = DiscriminatorType.STRING, length = 1)
@DiscriminatorValue(value = "N")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Substitution implements Cloneable, Serializable {
    static final long serialVersionUID = -9048255704644364624L;

    private Long id;

    private SubstitutionCriteria criteria;

    private int position;

    private Long actorId;

    private String substitutionOrgFunction;

    private boolean enabled;

    private boolean external;

    private Long version;

    public Substitution() {
    }

    @Column(name = "ENABLED_FLAG", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "IS_EXTERNAL", nullable = false)
    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_SUBSTITUTIONS")
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "POSITION_INDEX", nullable = false)
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Column(name = "SUBSITUTION_ORG_FUNCTION", nullable = false)
    public String getSubstitutionOrgFunction() {
        return substitutionOrgFunction;
    }

    public void setSubstitutionOrgFunction(String substitutionOrgFunction) {
        this.substitutionOrgFunction = OracleCommons.fixNullString(substitutionOrgFunction);
    }

    @Version
    @Column(name = "VERSION", nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "ACTOR_ID", nullable = false)
    @Index(name = "ACTOR_ID_IDX")
    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    @ManyToOne(targetEntity = SubstitutionCriteria.class)
    @JoinColumn(name = "CRITERIA_ID")
    @Index(name = "CRITERIA_ID_IDX")
    @Fetch(FetchMode.JOIN)
    public SubstitutionCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SubstitutionCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Object clone() {
        try {
            Substitution clone = (Substitution) super.clone();
            clone.id = null;
            clone.version = null;
            return clone;
        } catch (CloneNotSupportedException e) {
            // should never happen
            throw new InternalApplicationException(e);
        }
    }

    @Override
    public String toString() {
        return substitutionOrgFunction;
    }
}
