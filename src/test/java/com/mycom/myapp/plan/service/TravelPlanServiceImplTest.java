package com.mycom.myapp.plan.service;

import com.mycom.myapp.plan.entity.TravelPlan;
import com.mycom.myapp.plan.reponsitory.TravelPlanRepository;
import com.mycom.myapp.plan.service.impl.TravelPlanServiceImpl;
import com.mycom.myapp.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TravelPlanServiceImplTest {

    @InjectMocks
    private TravelPlanServiceImpl travelPlanService;

    @Mock
    private TravelPlanRepository travelPlanRepository;

    @Test
    void getMyTravelPlans_success() {
        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setId(1);
        travelPlan.setCode("ABC123");
        User user = new User();
        user.setId(1);
        travelPlan.setUser(user);
        travelPlan.setStartDate(LocalDate.of(2025, 1, 1));
        travelPlan.setEndDate(LocalDate.of(2025, 1, 5));

        given(travelPlanRepository.findByUserId(1)).willReturn(List.of(travelPlan));

        var response = travelPlanService.getMyTravelPlans(1);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getData()).isNotEmpty();
        assertThat(response.getBody().getData().get(0).getCode()).isEqualTo("ABC123");
    }

    @Test
    void getTravelPlanDetailByCode_success() {
        String code = "ABC123";

        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setCode(code);
        travelPlan.setDepartureName("서울");
        travelPlan.setArrivalName("부산");
        travelPlan.setStartDate(LocalDate.of(2025, 1, 1));
        travelPlan.setEndDate(LocalDate.of(2025, 1, 5));
        travelPlan.setTransport("Train");
        travelPlan.setAccommodationName("ABC 호텔");

        given(travelPlanRepository.findByCode(code)).willReturn(Optional.of(travelPlan));

        var response = travelPlanService.getTravelPlanDetailByCode(code);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        String expectedTitle = "서울 → 부산 (2025-01-01 ~ 2025-01-05)";
        assertThat(response.getBody().getData().getTitle()).isEqualTo(expectedTitle);
    }

}
