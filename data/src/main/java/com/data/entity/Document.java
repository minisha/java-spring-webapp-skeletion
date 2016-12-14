package com.data.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DOCUMENT")
public class Document {

    @Id
    BigInteger id;

    @Column(name = "NAME", length = 20)
    String name;

    @Column(name = "OWNER", length = 20)
    String owner;

}
