package cardealer.domain.service;

import cardealer.domain.dtos.LocalSuppliersDTO;
import cardealer.domain.dtos.SupplierSeedDTO;
import cardealer.domain.entities.Supplier;
import cardealer.domain.repository.PartRepository;
import cardealer.domain.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public SupplierServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void SeedSuppliers(SupplierSeedDTO[] supplierSeedDTOS) {
        for (SupplierSeedDTO supplierSeedDTO : supplierSeedDTOS) {
            Supplier entity = this.modelMapper.map(supplierSeedDTO, Supplier.class);
            this.supplierRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<LocalSuppliersDTO> findLocalSuppliers() {
        Set<Supplier> suppliers = this.supplierRepository.findLocalSuppliers();

        List<LocalSuppliersDTO> localSuppliersDTOS = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            LocalSuppliersDTO localSuppliersDTO = this.modelMapper.map(supplier,LocalSuppliersDTO.class);
            localSuppliersDTO.setPartsCount(this.partRepository.findPartBySupplierId(supplier.getId()).size());
            localSuppliersDTOS.add(localSuppliersDTO);
        }

        return localSuppliersDTOS;
    }

}
