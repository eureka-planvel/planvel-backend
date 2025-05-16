package com.mycom.myapp.transport.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.transport.dto.TimetableResponseDto;
import com.mycom.myapp.transport.entity.Station;
import com.mycom.myapp.transport.entity.Timetable;
import com.mycom.myapp.transport.entity.type.TransportType;
import com.mycom.myapp.transport.repository.TimetableRepository;
import com.mycom.myapp.transport.service.impl.TimetableServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TimetableServiceImplTest {

    @Mock
    private TimetableRepository timetableRepository;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    public TimetableServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRoundTripTimetablesFiltered_shouldReturnTimetableDtos() {
        Region region = Region.builder().id(1).name("Seoul").build();
        Station departure = Station.builder().id(1).name("Station A").region(region).type(TransportType.BUS).build();
        Station arrival = Station.builder().id(2).name("Station B").region(region).type(TransportType.BUS).build();

        Timetable t1 = Timetable.builder()
                .id(1)
                .departureStation(departure)
                .arrivalStation(arrival)
                .transportType(TransportType.BUS)
                .departureTime(LocalTime.of(9, 0))
                .durationMin(60)
                .price(10000)
                .transportNumber("B123")
                .build();

        List<Timetable> timetableList = Arrays.asList(t1);

        when(timetableRepository.findRoundTripTimetablesFiltered(1, 2, TransportType.BUS)).thenReturn(timetableList);

        ResponseWithStatus<List<TimetableResponseDto>> result = timetableService.getRoundTripTimetablesFiltered(1, 2, TransportType.BUS);

        assertThat(result.getStatus().toString()).startsWith("200");
        assertThat(result.getBody().getData()).hasSize(1);
        assertThat(result.getBody().getData().get(0).getTransportNumber()).isEqualTo("B123");

        verify(timetableRepository, times(1)).findRoundTripTimetablesFiltered(1, 2, TransportType.BUS);
    }
}
