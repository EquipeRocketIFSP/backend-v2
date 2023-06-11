package br.vet.certvet.services.implementation;

import br.vet.certvet.contracts.apis.anvisa.MedicationAPIResponse;
import br.vet.certvet.contracts.apis.anvisa.RegisterNumberAPIResponse;
import br.vet.certvet.exceptions.specializations.anvisaApi.AnvisaAPIBadGatewayException;
import br.vet.certvet.helpers.Https;
import br.vet.certvet.services.AnvisaAPIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AnvisaAPIServiceImpl implements AnvisaAPIService {
    private static final String BASE_URL = "https://consultas.anvisa.gov.br/api/consulta/medicamento/produtos/";
    private static final ObjectMapper jsonParser = new ObjectMapper();
    private static final HashMap<String, String> headers = new HashMap<>();

    public AnvisaAPIServiceImpl() {
        AnvisaAPIServiceImpl.headers.put("Authorization", "Guest");
        AnvisaAPIServiceImpl.headers.put("Content-Type", "application/json");
        AnvisaAPIServiceImpl.headers.put("Cache-Control", "no-cache");
    }

    @Override
    public String getProcessNumberByRegisterNumber(String registerNumber) {
        try {
            String uri = AnvisaAPIServiceImpl.BASE_URL + "?&filter[numeroRegistro]=" + registerNumber + "&page=1";
            RegisterNumberAPIResponse response = AnvisaAPIServiceImpl.jsonParser.readValue(Https.get(uri, headers), RegisterNumberAPIResponse.class);

            return (String) ((HashMap<?, ?>) response.content().get(0).get("processo")).get("numero");
        } catch (Exception e) {
            throw new AnvisaAPIBadGatewayException(e);
        }
    }

    @Override
    public MedicationAPIResponse getMedicationsByProcessNumber(String processNumber) {
        try {
            String uri = AnvisaAPIServiceImpl.BASE_URL + processNumber;
            return AnvisaAPIServiceImpl.jsonParser.readValue(Https.get(uri, headers), MedicationAPIResponse.class);
        } catch (Exception e) {
            throw new AnvisaAPIBadGatewayException(e);
        }
    }
}
