package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AggregatorService {

    //Ключ — идентификатор мероприятия; значение — мапа, где:
    //ключ — идентификатор пользователя, а значение — максимальный вес из всех действий данного пользователя с этим мероприятием.
    private final Map<Long, Map<Long, Double>> userEventWeights = new HashMap<>();

    //Ключ — идентификатор мериприятия; значение — сумма весов действий пользователей с этим мериприятием.
    private final Map<Long, Double> eventWeightSums = new HashMap<>();

    //Ключ — идентификатор первого мероприятия; значение — мапа, где:
    //ключ — идентификатор второго мероприятия, а значение — сумма минимальных весов данных мероприятий.
    private final Map<Long, Map<Long, Double>> minWeightsSum = new HashMap<>();

    /*
    пример сохранения суммы минимальных весов мероприятий A и B
    minWeightsSum.computeIfAbsent(A, new HashMap()).put(B, S_min);
     */

    /*
        1. Не забывайте: когда один пользователь совершает несколько действий с одним и тем же мероприятием, учитывается только действие с максимальным весом.
        2. Учтите, что сумма минимальных весов будет одинакова для двух мероприятий вне зависимости от их порядка,
        то есть S_min(A, B) = S_min(B, A).
        Чтобы избежать дублирования, перед сохранением или получением суммы примените упорядочивание идентификаторов мероприятий.
        3. Не пересчитывайте то, что не нужно. Например, если в результате очередного пользовательского действия его максимальный вес для мероприятия не изменился,
        то и пересчитывать сходство не требуется.
        Также помните, что сходство двух мероприятий рассчитывается на основе действий пользователя с обоими.
        Если пользователь не взаимодействовал с одним из них, то он не может сделать вклад в расчёт сходства этой пары мероприятий.
     */
    public EventSimilarityAvro createEventSimilarityMessage(UserActionAvro userAction) {

        return EventSimilarityAvro.newBuilder().build();
    }

}
