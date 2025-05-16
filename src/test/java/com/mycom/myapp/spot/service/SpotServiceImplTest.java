package com.mycom.myapp.spot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.entity.Spot;
import com.mycom.myapp.spot.entity.type.SpotType;
import com.mycom.myapp.spot.repository.SpotRepository;
import com.mycom.myapp.spot.service.impl.SpotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

public class SpotServiceImplTest {

    @InjectMocks
    private SpotServiceImpl spotService;

    @Mock
    private SpotRepository spotRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSpotsByRegionAndType_returnsSpotList() {
        int regionId = 1;
        SpotType type = SpotType.FOOD;
        int page = 1;
        int size = 2;

        Region region = new Region();
        region.setId(regionId);
        region.setName("서울");

        Spot spot1 = Spot.builder()
                .id(101)
                .region(region)
                .spotName("맛집1")
                .address("서울 강남구")
                .type(type)
                .imageUrl("img1.jpg")
                .thumbnailUrl("thumb1.jpg")
                .build();

        Spot spot2 = Spot.builder()
                .id(102)
                .region(region)
                .spotName("맛집2")
                .address("서울 마포구")
                .type(type)
                .imageUrl("img2.jpg")
                .thumbnailUrl("thumb2.jpg")
                .build();

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spot> spotPage = new PageImpl<>(List.of(spot1, spot2), pageable, 2);

        given(spotRepository.findByRegionIdAndType(regionId, type, pageable)).willReturn(spotPage);

        ResponseWithStatus<List<SpotResponseDto>> response = spotService.getSpotsByRegionAndType(regionId, type, page, size);

        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        List<SpotResponseDto> dtos = response.getBody().getData();
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getSpotName()).isEqualTo("맛집1");
        assertThat(dtos.get(1).getSpotName()).isEqualTo("맛집2");
    }

    @Test
    void getSpotDetail_existingSpot_returnsSpot() {
        int spotId = 101;

        Region region = new Region();
        region.setId(1);
        region.setName("서울");

        Spot spot = Spot.builder()
                .id(spotId)
                .region(region)
                .spotName("맛집1")
                .address("서울 강남구")
                .type(SpotType.FOOD)
                .imageUrl("img1.jpg")
                .thumbnailUrl("thumb1.jpg")
                .build();

        given(spotRepository.findById(spotId)).willReturn(Optional.of(spot));

        ResponseWithStatus<SpotResponseDto> response = spotService.getSpotDetail(spotId);

        assertThat(response.getStatus().is2xxSuccessful()).isTrue();
        SpotResponseDto dto = response.getBody().getData();
        assertThat(dto.getId()).isEqualTo(spotId);
        assertThat(dto.getSpotName()).isEqualTo("맛집1");
        assertThat(dto.getRegionName()).isEqualTo("서울");
    }

    @Test
    void getSpotDetail_nonExistingSpot_throwsException() {
        int spotId = 999;
        given(spotRepository.findById(spotId)).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            spotService.getSpotDetail(spotId);
        });
    }
}
