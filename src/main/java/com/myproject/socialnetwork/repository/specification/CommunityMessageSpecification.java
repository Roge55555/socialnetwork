package com.myproject.socialnetwork.repository.specification;

import com.myproject.socialnetwork.entity.CommunityMessage;
import com.myproject.socialnetwork.entity.CommunityMessage_;
import com.myproject.socialnetwork.entity.Community_;
import com.myproject.socialnetwork.entity.User_;
import com.myproject.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.myproject.socialnetwork.model.filter.CommunityMessageFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class CommunityMessageSpecification {

    private static Specification<CommunityMessage> hasCommunity(final Long community) {
        if (community == null)
            throw new NoNecessaryFieldInSpecificationException("No community!");

        return (root, query, builder) ->
                builder.equal((root.get(CommunityMessage_.COMMUNITY).get(Community_.ID)), community);
    }

    private static Specification<CommunityMessage> hasUser(final Long user) {
        if (user == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(CommunityMessage_.CREATOR).get(User_.ID)), user);
    }


    private static Specification<CommunityMessage> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(CommunityMessage_.DATE), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CommunityMessage_.DATE), from);

        return (root, query, builder) -> builder.between(root.get(CommunityMessage_.DATE), from, to);
    }

    public static Specification<CommunityMessage> getSpecification(final CommunityMessageFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(hasCommunity(request.getCommunityId()))
                    .and(hasUser(request.getUserId()))
                    .and(hasTimeInterval(request.getFrom(), request.getTo()))
                    .toPredicate(root, query, builder);
        };
    }

}
