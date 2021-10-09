package com.myproject.socialnetwork.repository.specification;

import com.myproject.socialnetwork.entity.ProfileComment;
import com.myproject.socialnetwork.entity.ProfileComment_;
import com.myproject.socialnetwork.entity.User_;
import com.myproject.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.myproject.socialnetwork.model.filter.ProfileCommentFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class ProfileCommentSpecification {

    private static Specification<ProfileComment> hasOwner(final Long owner) {
        if (owner == null)
            throw new NoNecessaryFieldInSpecificationException("No profile owner!");

        return (root, query, builder) ->
                builder.equal((root.get(ProfileComment_.PROFILE_OWNER).get(User_.ID)), owner);
    }

    private static Specification<ProfileComment> hasUser(final Long user) {
        if (user == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(ProfileComment_.USER).get(User_.ID)), user);
    }


    private static Specification<ProfileComment> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(ProfileComment_.DATE), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(ProfileComment_.DATE), from);

        return (root, query, builder) -> builder.between(root.get(ProfileComment_.DATE), from, to);
    }

    public static Specification<ProfileComment> getSpecification(final ProfileCommentFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(hasOwner(request.getOwnerId()))
                    .and(hasUser(request.getUserId()))
                    .and(hasTimeInterval(request.getFrom(), request.getTo()))
                    .toPredicate(root, query, builder);
        };
    }

}
