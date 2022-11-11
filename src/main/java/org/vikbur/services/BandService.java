package org.vikbur.services;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vikbur.models.Band;
import org.vikbur.models.User;
import org.vikbur.models.requests.CreateBandRequest;
import org.vikbur.models.responses.CreateObjectResponse;
import org.vikbur.repositories.BandCrudRepository;
import org.vikbur.repositories.UserCrudRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BandService {
    private final BandCrudRepository bandCrudRepository;
    private final UserCrudRepository userCrudRepository;
    private final Gson gson = new Gson();
    public String createBand(CreateBandRequest request, String userLogin) throws Exception {
        log.info(String.format("Create band '%s' attempting by '%s'", request.getName(), userLogin));
        validateBand(request);
        Band band = request.toBand();
        User user = userCrudRepository.findByLogin(userLogin).orElse(null);
        if (user != null){
            band.addMember(user);
        }
        bandCrudRepository.save(band);
        return gson.toJson(new CreateObjectResponse(
                String.format("Band '%s' created", band.getName()),
                bandCrudRepository.findByName(band.getName()).orElse(new Band()).getId()));
    }

    private void validateBand(CreateBandRequest request) throws IllegalArgumentException {
        if (request.getName() == null || request.getName().isEmpty()){
            throw new IllegalArgumentException("Incorrect band name");
        }
        if (bandCrudRepository.findByName(request.getName()).orElse(null) != null){
            throw new IllegalArgumentException(String.format("Band '%s' already exist", request.getName()));
        }
    }

    public String getBandById(int id) throws SQLException, IllegalArgumentException {
        Band band = bandCrudRepository.findById(id).orElse(null);
        if (band == null) {
            throw new IllegalArgumentException(String.format("Band with id '%s' not found", id));
        }
        return gson.toJson(prepareBand(band));
    }

    public String getBandByName(String name) throws SQLException, IllegalArgumentException {
        Band band = bandCrudRepository.findByName(name).orElse(null);
        if (band == null) {
            throw new IllegalArgumentException(String.format("Band with name '%s' not found", name));
        }
        return gson.toJson(prepareBand(band));
    }
    private Band prepareBand(Band band) {
        band.setMembers(band.getMembers().stream().peek(m -> {
            m.setBands(new HashSet<>());
            m.setPassword("");
            m.setSalt("");
        }).collect(Collectors.toSet()));
        return band;
    }
}
