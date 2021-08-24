package com.senla.project.socialnetwork.repository.specification;

import com.senla.project.socialnetwork.Utils;
import com.senla.project.socialnetwork.entity.Message;
import com.senla.project.socialnetwork.exeptions.NoNecessaryFieldInSpecificationException;
import com.senla.project.socialnetwork.exeptions.TryingModifyNotYourDataException;
import com.senla.project.socialnetwork.model.dto.MessageFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

public class MessageSpecification {

    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";
    private static final String CREATION_TIME = "dateCreated";

    private static Specification<Message> hasMe(String who) {
        return (root, query, builder) ->
                builder.equal((root.get(who).get("login")), Utils.getLogin());
        //builder.like(builder.lower(root.get(SENDER)), "%" + sender_name.toLowerCase() + "%");
    }

    private static Specification<Message> hasMate(final Long receiver_id, String who) {
        if (receiver_id == null)
            throw new NoNecessaryFieldInSpecificationException("No mate!");

        return (root, query, builder) ->
                builder.equal((root.get(who).get("id")), receiver_id);
    }


    private static Specification<Message> hasTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        if (from == null && to == null)
            return null;

        if (from == null)
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(CREATION_TIME), to);

        if (to == null)
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CREATION_TIME), from);

        return (root, query, builder) -> builder.between(root.get(CREATION_TIME), from, to);
    }

    public static Specification<Message> getSpecification (final MessageFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(where(hasMe(SENDER))
                    .and(hasMate(request.getMate_id(), RECEIVER))
                    .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .or(where(hasMate(request.getMate_id(), SENDER))
                    .and(hasMe(RECEIVER))
                    .and(hasTimeInterval(request.getFrom(), request.getTo())))
                    .toPredicate(root, query, builder);
        };
    }

}
