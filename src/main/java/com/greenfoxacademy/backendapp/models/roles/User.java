package com.greenfoxacademy.backendapp.models.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("user")
@Data()
@AllArgsConstructor
public class User extends Visitor {

}