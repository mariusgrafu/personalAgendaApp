package com.example.personalagendaapp.util;

import com.example.personalagendaapp.exception.OnlyAuthorAllowedException;
import com.example.personalagendaapp.model.Authorable;
import com.github.slugify.Slugify;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Util {
    public static <T> Optional<T> getFirstItemFromList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            return Optional.of(list.get(0));
        }

        return Optional.empty();
    }

    public static String joinList(List<Long> list) {
        return list.stream().map(val -> val.toString()).collect(Collectors.joining(","));
    }

    public static void assertNonAuthors(Authorable entity, long userId) {
        if (entity.getAuthorId() != userId) {
            throw new OnlyAuthorAllowedException();
        }
    }

    public static Slugify slugUtil = Slugify.builder().build();

    public static String slugify(String value) {
        return Util.slugUtil.slugify(value);
    }
}
