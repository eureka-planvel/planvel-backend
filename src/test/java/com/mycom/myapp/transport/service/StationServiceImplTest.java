package com.mycom.myapp.transport.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.transport.dto.StationResponseDto;
import com.mycom.myapp.transport.entity.Station;
import com.mycom.myapp.transport.entity.type.TransportType;
import com.mycom.myapp.transport.repository.StationRepository;
import com.mycom.myapp.transport.service.impl.StationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StationServiceImplTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationServiceImpl stationService;

    public StationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getStationsByRegion_shouldReturnStationDtos() {
        Region region = Region.builder().id(1).name("Seoul").build();
        Station station1 = Station.builder().id(1).name("Station A").region(region).type(TransportType.BUS).build();
        Station station2 = Station.builder().id(2).name("Station B").region(region).type(TransportType.TRAIN).build();
        List<Station> stations = Arrays.asList(station1, station2);

        when(stationRepository.findByRegionId(1)).thenReturn(stations);

        ResponseWithStatus<List<StationResponseDto>> result = stationService.getStationsByRegion(1);

        assertThat(result.getStatus().toString()).startsWith("200");
        assertThat(result.getBody().getData()).hasSize(2);
        assertThat(result.getBody().getData().get(0).getName()).isEqualTo("Station A");

        verify(stationRepository, times(1)).findByRegionId(1);
    }
}
