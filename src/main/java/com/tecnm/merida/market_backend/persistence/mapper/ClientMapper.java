package com.tecnm.merida.market_backend.persistence.mapper;

import com.tecnm.merida.market_backend.domain.Client;
import com.tecnm.merida.market_backend.persistence.entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "id", target = "clientId")
    @Mapping(source = "nombre", target = "firstName")
    @Mapping(source = "apellidos", target = "lastName")
    @Mapping(source = "celular", target = "cellPhone")
    @Mapping(source = "direccion", target = "address")
    @Mapping(source = "correoElectronico", target = "email")
    Client toClient(Cliente cliente);

    @InheritInverseConfiguration
    @Mapping(target = "compras", ignore = true)
    Cliente toCliente(Client client);
}
