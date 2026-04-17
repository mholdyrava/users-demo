package com.epam.mh.model;

import com.epam.mh.entity.User;

public record UserSummary(int id, String fullName) {
    public static UserSummary from(User user) {
        return new UserSummary(user.getId(), user.getFullName());
    }
}