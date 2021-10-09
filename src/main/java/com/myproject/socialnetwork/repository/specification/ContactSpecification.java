package com.myproject.socialnetwork.repository.specification;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Contact;
import com.myproject.socialnetwork.entity.Contact_;
import com.myproject.socialnetwork.entity.RoleList_;
import com.myproject.socialnetwork.entity.User_;
import com.myproject.socialnetwork.model.filter.ContactFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.Specification.where;

public class ContactSpecification {

    private static Specification<Contact> hasMe(String who) {
        return (root, query, builder) ->
                builder.equal((root.join(who).get(User_.LOGIN)), Utils.getLogin());
    }

    private static Specification<Contact> hasMate(final Long mateId, String who) {
        if (mateId == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(who).get(User_.ID)), mateId);
    }

    private static Specification<Contact> hasLevel(final Boolean level) {
        if (level == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Contact_.CONTACT_LEVEL)), level);
    }

    private static Specification<Contact> hasCreatorRole(final Long creatorRoleId) {
        if (creatorRoleId == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Contact_.CREATOR_ROLE).get(RoleList_.ID)), creatorRoleId);
    }

    private static Specification<Contact> hasMateRole(final Long mateRoleId) {
        if (mateRoleId == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Contact_.MATE_ROLE).get(RoleList_.ID)), mateRoleId);
    }

    private static Specification<Contact> hasTimeInterval(final LocalDate from, final LocalDate to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Contact_.DATE_CONNECTED), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Contact_.DATE_CONNECTED), from);

        return (root, query, builder) -> builder.between(root.get(Contact_.DATE_CONNECTED), from, to);
    }

    public static Specification<Contact> getSpecification(final ContactFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(
                    where(hasMe(Contact_.CREATOR))
                            .and(hasCreatorRole(request.getMyRoleId()))
                            .and(hasMate(request.getMateId(), Contact_.MATE))
                            .and(hasMateRole(request.getMateRoleId()))
                            .and(hasLevel(request.getLevel()))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .or(where(hasMate(request.getMateId(), Contact_.CREATOR))
                            .and(hasCreatorRole(request.getMateRoleId()))
                            .and(hasMe(Contact_.MATE))
                            .and(hasMateRole(request.getMyRoleId()))
                            .and(hasLevel(request.getLevel()))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .toPredicate(root, query, builder);
        };
    }

}
