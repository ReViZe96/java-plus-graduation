package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.grpc.messages.UserActionProto;

import java.time.Instant;

@Component
public class UserActionAvroMapper {

    public UserActionAvro userActionToAvro(UserActionProto userActionProto) {
        return UserActionAvro.newBuilder()
                .setUserId(userActionProto.getUserId())
                .setEventId(userActionProto.getEventId())
                .setActionType(ActionTypeAvro.valueOf(userActionProto.getActionType().name()))
                .setTimestamp(Instant.ofEpochSecond(userActionProto.getTimestampOrBuilder().getSeconds()))
                .build();
    }

}
