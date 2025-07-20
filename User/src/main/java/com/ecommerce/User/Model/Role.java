package com.ecommerce.User.Model;

import com.ecommerce.User.Enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
public class Role {
    private Long roleId;

    @ToString.Exclude
    private UserRole roleName;
}