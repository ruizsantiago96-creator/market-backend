package com.tecnm.merida.market_backend.domain.service;

import com.tecnm.merida.market_backend.domain.Purchase;
import com.tecnm.merida.market_backend.domain.PurchaseItem;
import com.tecnm.merida.market_backend.domain.PurchaseRequest;
import com.tecnm.merida.market_backend.domain.repository.ClientRepository;
import com.tecnm.merida.market_backend.domain.repository.PurchaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, ClientRepository clientRepository) {
        this.purchaseRepository = purchaseRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<Purchase> getAll() {
        return purchaseRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Optional<List<Purchase>> getByClient(String clientId) {
        return purchaseRepository.getByClient(clientId);
    }

    @Transactional
    public Purchase save(PurchaseRequest request) {
        if (request.idCliente() == null || request.idCliente().isBlank()
                || request.medioPago() == null || request.medioPago().isBlank()
                || request.productosAdquiridos() == null || request.productosAdquiridos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Client, payment method and at least one line item are required");
        }

        if (clientRepository.getById(request.idCliente()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }

        Set<Integer> productIds = new HashSet<>();
        List<PurchaseItem> items = request.productosAdquiridos().stream()
                .map(item -> toPurchaseItem(item, productIds))
                .toList();

        Purchase purchase = new Purchase();
        purchase.setClientId(request.idCliente());
        purchase.setDate(LocalDateTime.now());
        purchase.setPaymentMethod(request.medioPago());
        purchase.setComment(request.comentario());
        purchase.setState("A");
        purchase.setItems(items);
        return purchaseRepository.save(purchase);
    }

    private PurchaseItem toPurchaseItem(PurchaseRequest.Item item, Set<Integer> productIds) {
        if (item == null || item.idProducto() == null || item.idProducto() <= 0
                || item.cantidad() == null || item.cantidad() <= 0
                || item.total() == null || !Double.isFinite(item.total()) || item.total() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid purchase line item");
        }
        if (!productIds.add(item.idProducto())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate product in purchase");
        }

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setProductId(item.idProducto());
        purchaseItem.setQuantity(item.cantidad());
        purchaseItem.setTotal(item.total());
        purchaseItem.setActive(true);
        return purchaseItem;
    }
}
