package com.elena.schoolMotivatorAppServer.model.buisness;

import com.elena.schoolMotivatorAppServer.model.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "achievement")
public class Achievement extends BaseEntity {
    private String name;
    private String description;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String base64;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "achievement")
    private List<ChildAchievement> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public List<ChildAchievement> getChildren() {
        return children;
    }

    public void setChildren(List<ChildAchievement> children) {
        this.children = children;
    }
}
