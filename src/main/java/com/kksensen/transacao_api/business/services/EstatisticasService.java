package com.kksensen.transacao_api.business.services;

import com.kksensen.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.kksensen.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca) {

        log.info("Iniciada busca de estatística de transações pelo período de temopo:" + intervaloBusca);
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBusca);

        if(transacoes.isEmpty()){
            return new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        }

        DoubleSummaryStatistics estatiscticasTransacoes = transacoes.stream()
                .mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();

        log.info("Estatísticas retornadas com sucesso");
        return new EstatisticasResponseDTO(estatiscticasTransacoes.getCount(),
                estatiscticasTransacoes.getSum(),
                estatiscticasTransacoes.getAverage(),
                estatiscticasTransacoes.getMin(),
                estatiscticasTransacoes.getMax());
    }
}
