package com.elena.schoolMotivatorAppServer.model;

import com.elena.schoolMotivatorAppServer.model.user.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "ed_token")
@Data
public class EDToken extends BaseEntity {
    @OneToOne(mappedBy = "edToken")
    private User user;
    @Column(name = "ed_token")
    @Type(type = "text")
    @Lob
    private String EDToken;

    public EDToken(User user, String EDToken) {
        this.user = user;
        this.EDToken = EDToken;
    }

    public EDToken() {

    }
}
