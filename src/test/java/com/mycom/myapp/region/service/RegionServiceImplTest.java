package com.mycom.myapp.region.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.region.repository.RegionRepository;
import com.mycom.myapp.region.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @InjectMocks
    private RegionServiceImpl regionService;
    @Mock
    private RegionRepository regionRepository;

    @Test
    void getAllRegions_success() {
        Region region1 = new Region();
        region1.setId(1);
        region1.setName("서울");

        Region region2 = new Region();
        region2.setId(2);
        region2.setName("부산");

        List<Region> regions = List.of(region1, region2);

        given(regionRepository.findAll()).willReturn(regions);

        ResponseWithStatus<List<RegionResponseDto>> response = regionService.getAllRegions();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("지역 목록 조회 성공");
        assertThat(response.getBody().getData()).hasSize(2);
        assertThat(response.getBody().getData().get(0).getName()).isEqualTo("서울");
        assertThat(response.getBody().getData().get(1).getName()).isEqualTo("부산");

        verify(regionRepository).findAll();
    }

    @Test
    void getAllRegions_noRegionsFound() {
        given(regionRepository.findAll()).willReturn(Collections.emptyList());

        ResponseWithStatus<List<RegionResponseDto>> response = regionService.getAllRegions();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMsg()).isEqualTo("등록된 지역이 없습니다.");

        verify(regionRepository).findAll();
    }
}
