package com.intrum.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "passwords")
@Entity
public class Credential {

    @Id
    @SequenceGenerator(name = "passwords_pwd_id_seq",
            sequenceName = "passwords_pwd_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "passwords_pwd_id_seq")
    @Column(name = "pwd_id", updatable = false)
    Long id;

    @Column(name = "passwordhash")
    String hash;

    @Column(name = "customer_id")
    Long userId;

    public Credential(String hash, Long userId) {
        this.hash = hash;
        this.userId = userId;
    }
}
