package com.skeleton.account.entity;

import com.skeleton.account.entity.superclass.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Table
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account_role")
public class Role extends BasicEntity {

    @Column(name = "role", length = 200, nullable = false, unique = true)
    private String role;

    @Column(name = "read_permissions", length = 1000)
    private String readPermissions;

    @Column(name = "write_permissions", length = 1000)
    private String writePermissions;

    @Column(name = "update_permissions", length = 1000)
    private String updatePermissions;

    @Column(name = "delete_permissions", length = 1000)
    private String deletePermissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
