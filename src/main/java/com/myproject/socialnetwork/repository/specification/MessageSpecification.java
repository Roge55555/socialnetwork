package com.myproject.socialnetwork.repository.specification;

import com.myproject.socialnetwork.Utils;
import com.myproject.socialnetwork.entity.Message;
import com.myproject.socialnetwork.entity.Message_;
import com.myproject.socialnetwork.entity.User_;
import com.myproject.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.myproject.socialnetwork.model.filter.MessageFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class MessageSpecification {

    private static Specification<Message> hasMe(String who) {
        return (root, query, builder) ->
                builder.equal((root.get(who).get(User_.LOGIN)), Utils.getLogin());
    }

    private static Specification<Message> hasMate(final Long receiverId, String who) {
        if (receiverId == null)
            throw new NoNecessaryFieldInSpecificationException("No mate!");

        return (root, query, builder) ->
                builder.equal((root.get(who).get(User_.ID)), receiverId);
    }


    private static Specification<Message> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(Message_.DATE_CREATED), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(Message_.DATE_CREATED), from);

        return (root, query, builder) -> builder.between(root.get(Message_.DATE_CREATED), from, to);
    }

    public static Specification<Message> getSpecification(final MessageFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(
                    where(hasMe(Message_.SENDER))
                            .and(hasMate(request.getMateId(), Message_.RECEIVER))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .or(where(hasMate(request.getMateId(), Message_.SENDER))
                            .and(hasMe(Message_.RECEIVER))
                            .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .toPredicate(root, query, builder);
        };
    }

}