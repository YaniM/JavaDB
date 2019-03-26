package cardealer.domain.service;

import cardealer.domain.dtos.PartSeedDTO;

public interface PartService {
    void SeedParts(PartSeedDTO[] partSeedDTOS);
}
