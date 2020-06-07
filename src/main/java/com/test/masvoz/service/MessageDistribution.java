package com.test.masvoz.service;

import com.test.masvoz.cache.RedisRepository;
import com.test.masvoz.exceptions.PrefixNotFoundException;
import com.test.masvoz.model.Prefix;
import com.test.masvoz.model.Provider;
import com.test.masvoz.model.dto.ProviderDto;
import com.test.masvoz.model.response.ProviderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MessageDistribution {

    RedisRepository redisRepository;

    public MessageDistribution(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public void setupProvider(List<Provider> proveedores) {

        PriorityQueue<ProviderDto> providerDtos = new PriorityQueue<>();
        Map<String, PriorityQueue<ProviderDto>> listMap = new HashMap<>();

        for (Provider provider : proveedores) {
            for (Prefix prefix : provider.getPrefixes()) {
                if (listMap.size() == 0 || !listMap.containsKey(prefix.getPrefix())){
                    // Inicializo lista
                    providerDtos.add(new ProviderDto(provider.getProveedor(), 0, prefix.getCost()));
                    listMap.put(prefix.getPrefix(), providerDtos);
                } else {
                    // buscamos todos los proveedores guardados
                    for (ProviderDto provedorInList : listMap.get(prefix.getPrefix())){
                        // comparamos si el costo es menor, eliminamos el que está y agregamos el más barato
                        if( prefix.getCost() < provedorInList.getCoste() ) {
                            // vaciar toda la lista
                            listMap.get(prefix.getPrefix()).clear();
                            listMap.get(prefix.getPrefix()).add(new ProviderDto(provider.getProveedor(), 0, prefix.getCost()));
                            break;
                        }
                        // si tienen el mismo costo lo agrego
                        else if ( provedorInList.getCoste() == prefix.getCost() ){
                            listMap.get(prefix.getPrefix()).add(new ProviderDto(provider.getProveedor(), 0, prefix.getCost()));
                            break;
                        }
                    }
                }
                providerDtos = new PriorityQueue<>();
            }
        }
        redisRepository.saveProviders(listMap);
    }

    //TODO: borrar este método
    public Map<String, PriorityQueue<ProviderDto>> getProviders(){
        return redisRepository.getProvider();
    }

    public ProviderResponse processNumber(String number, String message){

        // Obtengo los provedores de redis
        Map<String, PriorityQueue<ProviderDto>> providerList = redisRepository.getProvider();

        //TODO: obtener el prefijo desde una expression regular
        String prefijo = number.substring(0, 4);

        PriorityQueue<ProviderDto> queue;
        ProviderDto providerDTO;

        queue = providerList.get(prefijo);
        try {
            providerDTO = queue.poll();
        } catch (Exception e) {
            throw new PrefixNotFoundException(prefijo);
        }

        final UUID uuid = sendMessage(providerDTO, number, message);

        queue.add(providerDTO);
        providerList.put(prefijo, queue);

        // Guardo todos los providers en redis
        redisRepository.saveProviders(providerList);

        return ProviderResponse.builder().provider(providerDTO.getProveedor()).uuid(uuid).build();

    }

    private UUID sendMessage(ProviderDto provider, String number, String message){
        final UUID uuid = UUID.randomUUID();

        Integer sentMessages = provider.getSentMessages();
            log.info("\nIdOperacion: " + UUID.randomUUID() + "\nProveedor: " + provider.getProveedor() + "\nMensaje: " + message +
                    "\nNumero: " + number + "\n");

        provider.setSentMessages(sentMessages + 1);

        return uuid;
    }



}
