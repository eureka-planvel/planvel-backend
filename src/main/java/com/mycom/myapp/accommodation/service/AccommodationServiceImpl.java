package com.mycom.myapp.accommodation.service;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.accommodation.entity.Accommodation;
import com.mycom.myapp.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Transactional
    @Override
    public List<AccommodationResponseDto> getAccommodationsByRegion(int regionId) {
        List<Accommodation> accommodations = accommodationRepository.findByRegionId(regionId);

        return accommodations.stream()
                .map(a -> new AccommodationResponseDto(
                        a.getId(),
                        a.getRegion().getName(),
                        a.getName(),
                        a.getAddress(),
                        a.getPricePerNight(),
                        a.getType(),
                        a.getImageUrl(),
                        a.getThumbnailUrl()
                ))
                .collect(Collectors.toList());
    }
}
