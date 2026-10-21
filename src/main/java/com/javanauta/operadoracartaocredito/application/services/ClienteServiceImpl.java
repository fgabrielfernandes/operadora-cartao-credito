package com.javanauta.operadoracartaocredito.application.services;

import com.javanauta.operadoracartaocredito.adapters.in.IClienteService;
import com.javanauta.operadoracartaocredito.application.domain.CartaoDomain;
import com.javanauta.operadoracartaocredito.application.domain.ClienteDomain;
import com.javanauta.operadoracartaocredito.porters.out.IClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository IClienteRepository;
    private final GeraDadosCartaoService geraCartao;

    public ClienteServiceImpl(IClienteRepository iClienteRepository, GeraDadosCartaoService geraCartao) {
        IClienteRepository = iClienteRepository;
        this.geraCartao = geraCartao;
    }

    @Override
    public ClienteDomain solicitarCartao(ClienteDomain cliente) {
        if (IClienteRepository.buscarUsuarioPorEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Usuário já possui um cartão.");
        }

        CartaoDomain cartao = geraCartao.gerarParaCliente(cliente);
        cliente.setCartao(cartao);
        return IClienteRepository.salvar(cliente);

    }

    @Override
    public ClienteDomain buscarPorCpf(String cpf) {
        return IClienteRepository.buscarUsuarioPorCpf(cpf).
                orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado"));
    }
}