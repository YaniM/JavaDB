package cardealer.domain.service;

import cardealer.domain.dtos.LocalSuppliersDTO;
import cardealer.domain.dtos.SupplierSeedDTO;

import java.util.List;

public interface SupplierService {
    void SeedSuppliers(SupplierSeedDTO[] supplierSeedDTOS);

    List<LocalSuppliersDTO> findLocalSuppliers();
}
