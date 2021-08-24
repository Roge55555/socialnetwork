package com.senla.project.socialnetwork.repository.specification;

import com.senla.project.socialnetwork.entity.CommunityMessage;
import com.senla.project.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.dto.CommunityMessageFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class CommunityMessageSpecification {

    private static final String COMMUNITY = "community";
    private static final String USER = "creator";
    private static final String CREATION_TIME = "date";

    private static Specification<CommunityMessage> hasCommunity(final Long community) {
        if (community == null)
            throw new NoNecessaryFieldInSpecificationException("No community!");

        return (root, query, builder) ->
                builder.equal((root.get(COMMUNITY).get("id")), community);
    }

    private static Specification<CommunityMessage> hasUser(final Long user) {
        if (user == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(USER).get("id")), user);
    }


    private static Specification<CommunityMessage> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(CREATION_TIME), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CREATION_TIME), from);

        return (root, query, builder) -> builder.between(root.get(CREATION_TIME), from, to);
    }

    public static Specification<CommunityMessage> getSpecification (final CommunityMessageFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(hasCommunity(request.getCommunity_id()))
                    .and(hasUser(request.getUser_id()))
                    .and(hasTimeInterval(request.getFrom(), request.getTo()))
                    .toPredicate(root, query, builder);
        };
    }

}
