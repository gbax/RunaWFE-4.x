package ru.runa.wfe.definition;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.SecuredObjectType;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

@Entity
@Table(name = "BPM_PROCESS_DEFINITION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deployment extends Identifiable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long version;
    private Language language;
    private String name;
    private String description;
    private String category;
    private byte[] content;
    private Date createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_BPM_PROCESS_DEFINITION")
    @Column(name = "ID", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VERSION", nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "LANGUAGE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CATEGORY", nullable = false)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Lob
    @Column(length = 16777216, name = "BYTES")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Column(name = "CREATE_DATE", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Transient
    @Override
    public Long getIdentifiableId() {
        return new Long(getName().hashCode());
    }

    @Transient
    @Override
    public SecuredObjectType getSecuredObjectType() {
        return SecuredObjectType.DEFINITION;
    }

    @Transient
    public String[] getCategories() {
        return category.split("/");
    }

    public void setCategories(List<String> categories) {
        category = Joiner.on("/").join(categories);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, version);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deployment) {
            Deployment d = (Deployment) obj;
            return Objects.equal(name, d.name) && Objects.equal(version, d.version);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).add("name", name).add("version", version).toString();
    }

    @Transient
    public Deployment getCopy() {
        Deployment deployment = new Deployment();
        deployment.category = category;
        deployment.content = content;
        deployment.createDate = createDate;
        deployment.description = description;
        deployment.id = id;
        deployment.language = language;
        deployment.name = name;
        deployment.version = version;
        return deployment;
    }
}
