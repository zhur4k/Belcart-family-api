package com.belkartspaceapi.dto.mapper;

import com.belkartspaceapi.dto.ClientWithTransactionsDTO;
import com.belkartspaceapi.dto.TransactionsToChildClientDTO;
import com.belkartspaceapi.model.ChildClient;
import com.belkartspaceapi.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class ClientWithTransactionsDTOMapper implements Function<ChildClient, ClientWithTransactionsDTO> {

    @Override
    public ClientWithTransactionsDTO apply(ChildClient childClient) {
        return new ClientWithTransactionsDTO(
                childClient.getId(),
                childClient.getName(),
                childClient.getCurrentClient().getCards()
                        .stream()
                        .flatMap(card -> card.getTransactions().stream()
                                .map(transaction -> mapToTransactionDTO(transaction, card.getCardNumber()))
                        )
                        .collect(Collectors.toList())
        );
    }

    private TransactionsToChildClientDTO mapToTransactionDTO(Transaction transaction, Long cardNumber) {
        return new TransactionsToChildClientDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getPlace().getName(),
                cardNumber
        );
    }
}
