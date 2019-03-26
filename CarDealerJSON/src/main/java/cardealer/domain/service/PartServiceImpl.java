package cardealer.domain.service;

import cardealer.domain.dtos.PartSeedDTO;
import cardealer.domain.entities.Part;
import cardealer.domain.entities.Supplier;
import cardealer.domain.repository.PartRepository;
import cardealer.domain.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(SupplierRepository repository, PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void SeedParts(PartSeedDTO[] partSeedDTOS) {
        for (PartSeedDTO partSeedDTO : partSeedDTOS) {

            Part entity = this.modelMapper.map(partSeedDTO,Part.class);
            entity.setSupplier(this.getRandomSupplier());
            this.partRepository.saveAndFlush(entity);
        }
    }

    private Supplier getRandomSupplier()
    {
        Random random = new Random();

        return this.supplierRepository.getOne(random.nextInt((int) (this.supplierRepository.count()-1))+1);
    }
}
