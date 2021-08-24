package com.senla.project.socialnetwork.repository.specification;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Contact;
import com.senla.project.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.senla.project.socialnetwork.model.dto.ContactFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class ContactSpecification {

    private static final String CREATOR = "creator";
    private static final String MATE = "mate";
    private static final String LEVEL = "contactLevel";
    private static final String CREATOR_ROLE = "creatorRole";
    private static final String MATE_ROLE = "creatorRole";
    private static final String CREATION_TIME = "dateConnected";

    private static Specification<Contact> hasMe(String who) {
        return (root, query, builder) ->
                builder.equal((root.get(who).get("login")), Utils.getLogin());
        //builder.like(builder.lower(root.get(SENDER)), "%" + sender_name.toLowerCase() + "%");
    }

    private static Specification<Contact> hasMate(final Long mate_id, String who) {
        if (mate_id == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(who).get("id")), mate_id);
    }

    private static Specification<Contact> hasLevel(final Boolean level) {
        if (level == null)
            throw new NoNecessaryFieldInSpecificationException("No mate!");

        return (root, query, builder) ->
                builder.equal((root.get(LEVEL).get("id")), level);
    }

    private static Specification<Contact> hasCreatorRole(final Long creator_role_id) {
        if (creator_role_id == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(CREATOR_ROLE).get("id")), creator_role_id);
    }

    private static Specification<Contact> hasMateRole(final Long mate_role_id) {
        if (mate_role_id == null)
            throw new NoNecessaryFieldInSpecificationException("No mate!");

        return (root, query, builder) ->
                builder.equal((root.get(MATE_ROLE).get("id")), mate_role_id);
    }


    private static Specification<Contact> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(CREATION_TIME), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CREATION_TIME), from);

        return (root, query, builder) -> builder.between(root.get(CREATION_TIME), from, to);
    }

    public static Specification<Contact> getSpecification (final ContactFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(//TODO TEST!!!!!
                    where(hasMe(CREATOR))
                            .and(hasCreatorRole(request.getMy_role_id()))
                            .and(hasMate(request.getMate_id(), MATE))
                            .and(hasMateRole(request.getMate_role_id()))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .or(where(hasMate(request.getMate_id(), CREATOR))
                            .and(hasCreatorRole(request.getMate_role_id()))
                            .and(hasMe(MATE))
                            .and(hasMateRole(request.getMy_role_id()))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .toPredicate(root, query, builder);
        };
    }

}
