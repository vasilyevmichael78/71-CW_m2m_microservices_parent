package telran.m2m.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import telran.m2m.dto.AverageSensorData;
import telran.m2m.dto.MinMaxAvg;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class SensorsStatisticQueriesImpl implements SensorsStatisticQueries {
    @Autowired
    MongoTemplate mongoTemplate;
    private static final String SENSOR_ID = "sensorId";
    private static final String AVG_VALUE = "avgValue";
    private static final String MIN_VALUE = "minValue";
    private static final String MAX_VALUE = "maxValue";
    private static final String COLLECTION_NAME = "averageSensorData";
    private static final String VALUE = "value";
    private static final String DATE_TIME = "dateTime";


MatchOperation matchSensor = match(new Criteria(SENSOR_ID).ne(null));

    @Override
    public MinMaxAvg getMinMaxAvgSensor(int sensorId, LocalDateTime from, LocalDateTime to) {
        MatchOperation matchSensorRange = match(new Criteria(DATE_TIME).gte(from).lte(to));
        MatchOperation matchSensorId = match(new Criteria(SENSOR_ID).is(sensorId));
        GroupOperation groupOperation = getSensorDataGroupOperation();
        Aggregation aggregation = newAggregation(matchSensorId, matchSensorRange, groupOperation);
        AggregationResults<MinMaxAvg> results = mongoTemplate.aggregate(aggregation,COLLECTION_NAME, MinMaxAvg.class);
        System.out.print(results.getRawResults());
        return results.getUniqueMappedResult();
    }

    @Override
    public List<MinMaxAvg> getMinMaxAvg() {


        GroupOperation groupOperation = getSensorDataGroupOperation();
        SortOperation sortByValue = sort(Sort.Direction.DESC, AVG_VALUE );
        Aggregation aggregation = newAggregation( matchSensor,groupOperation,sortByValue);
        AggregationResults<MinMaxAvg> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME,MinMaxAvg.class);
        System.out.print(results.getMappedResults());
        return results.getMappedResults();
    }

    private GroupOperation getSensorDataGroupOperation() {
        return group(SENSOR_ID).min(VALUE).as(MIN_VALUE).max(VALUE).as(MAX_VALUE).avg(VALUE).as(AVG_VALUE);
    }
}
