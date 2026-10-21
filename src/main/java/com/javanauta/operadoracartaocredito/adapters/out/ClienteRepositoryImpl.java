package com.javanauta.operadoracartaocredito.adapters.out;

import com.javanauta.operadoracartaocredito.adapters.mapper.IClienteMapper;
import com.javanauta.operadoracartaocredito.adapters.out.repositories.IClienteJpaRepository;
import com.javanauta.operadoracartaocredito.application.domain.ClienteDomain;
import com.javanauta.operadoracartaocredito.porters.out.IClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements IClienteRepository {

    private final IClienteJpaRepository IClienteJpaRepository;
    private final IClienteMapper IClienteMapper;

    @Override
    public ClienteDomain salvar(ClienteDomain clienteDomain) {
        return IClienteMapper.paraDomain(
                IClienteJpaRepository.save(
                        IClienteMapper.paraEntity(clienteDomain)
                )
        );
    }

    @Override
    public Boolean buscarUsuarioPorEmail(String email) {
        return IClienteJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<ClienteDomain> buscarUsuarioPorCpf(String cpf) {
        return IClienteJpaRepository.findByCpf(cpf)
                .map(IClienteMapper::paraDomain);
    }
}
